package servicelayer.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.Category;
import servicelayer.entity.businessEntity.DistributionType;
import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.ProjectEmployed;
import servicelayer.entity.businessEntity.ProjectLiquidation;
import shared.ConfigurationProperties;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreProjectLiquidation;

public class CoreProjectLiquidation implements ICoreProjectLiquidation {

	private static CoreProjectLiquidation instance = null;

	private CoreProjectLiquidation() {
	}

	public static CoreProjectLiquidation GetInstance() throws ServerException {
		if (instance == null) {
			instance = new CoreProjectLiquidation();
		}
		return instance;
	}	

	@Override
	public ProjectLiquidation getProjectLiquidationPreview(Date month,
			int projectId, double typeExchange) throws ServerException, ClientException {
		DAOManager daoManager = new DAOManager();
		Project project = null;
		ArrayList<Category> categories = null;
		ProjectLiquidation projectLiquidation = null;

		try {
			// Defino el periodo
			Calendar cal = Calendar.getInstance();
			cal.setTime(month);
			cal.set(Calendar.DAY_OF_MONTH, 01);
			Date from = cal.getTime();

			cal.setTime(month);
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
			Date to = cal.getTime();

			// Obtengo el proyectos a liquidar
			project = CoreProject.GetInstance()
					.getProjectsReadyToLiquidate(from, to, projectId).get(0);
			
			project.setiDAOBills(daoManager.getDAOBills());
			project.setiDAOCategories(daoManager.getDAOCategories());
			project.setiDAOPartnerProject(daoManager.getDAOPartnerProjects());
			project.setiDAOProjectEmployees(daoManager.getDAOEmployedProjects());
								
			projectLiquidation = new ProjectLiquidation(projectId);
			Employed seller = CoreEmployed.GetInstance().getEmployed(project.getId());
			project.setSeller(seller);
			projectLiquidation.setProject(project);
			projectLiquidation.setAppliedDateTimeUTC(month);
			projectLiquidation.setCreatedDateTimeUTC(new Date());
			
			// Cargo los socios
			projectLiquidation.setPartner1(project.getProjectPartner().get(0));
			projectLiquidation.getPartner1().setEmployed(CoreEmployed.GetInstance().getEmployed(project.getProjectPartner().get(0).getEmployed().getId()));
			
			projectLiquidation.setPartner2(project.getProjectPartner().get(1));	
			projectLiquidation.getPartner2().setEmployed(CoreEmployed.GetInstance().getEmployed(project.getProjectPartner().get(1).getEmployed().getId()));
			
			// Cargo facturas asociadas al proyecto
			ArrayList<Bill> associatedBills = project.getBills(from, to);
			if(associatedBills != null){
				for(Bill bill : associatedBills){					
					if(bill.getIsCurrencyDollar()){						
						projectLiquidation.setTotalBills(projectLiquidation.getTotalBills() + bill.getAmountDollar());
						projectLiquidation.setTotalBillsAmountPeso(projectLiquidation.getTotalBillsAmountPeso() + (bill.getAmountDollar() * bill.getTypeExchange()));
						bill.setTotalAmountDollar(CoreBill.GetInstance().getTotalAmount(bill.getAmountDollar(), bill.getIvaType()));
					}else{						
						projectLiquidation.setTotalBills(projectLiquidation.getTotalBills() + bill.getAmountPeso());
						projectLiquidation.setTotalBillsAmountPeso(projectLiquidation.getTotalBillsAmountPeso() + bill.getAmountPeso());
						bill.setTotalAmountPeso(CoreBill.GetInstance().getTotalAmount(bill.getAmountPeso(), bill.getIvaType()));
					}
					projectLiquidation.setTotalIVAAmountPeso(projectLiquidation.getTotalIVAAmountPeso() + (bill.getAmountPeso() * bill.getIvaType().getPercentage()) - bill.getAmountPeso());
				}
			}
			projectLiquidation.setBills(associatedBills);

			// Cargo rubros humanos y rubros materiales asociados al proyecto
			ArrayList<Category> associatedCategoriesHuman = new ArrayList<Category>();
			ArrayList<Category> associatedCategoriesMaterial = new ArrayList<Category>();
			categories = project.getCategories(from, to);
			if (categories != null && categories.size() > 0) {
				for (Category category : categories) {
					if (category.getIsRRHH()) {
						associatedCategoriesHuman.add(category);
					} else {
						associatedCategoriesMaterial.add(category);
					}
					if(category.getIsCurrencyDollar()){
						category.setTotalAmountDollar(CoreCategory.GetInstance().getTotalAmount(category.getAmountDollar(), category.getIvaType()));
						if(category.getIsRRHH())
							projectLiquidation.setTotalCostCategoriesHuman(projectLiquidation.getTotalCostCategoriesHuman() + category.getAmountDollar());
						else
							projectLiquidation.setTotalCostCategoriesMaterial(projectLiquidation.getTotalCostCategoriesMaterial() + category.getAmountDollar());
					}else{
						category.setTotalAmountPeso(CoreCategory.GetInstance().getTotalAmount(category.getAmountPeso(), category.getIvaType()));
						if(category.getIsRRHH())
							projectLiquidation.setTotalCostCategoriesHuman(projectLiquidation.getTotalCostCategoriesHuman() + category.getAmountPeso());
						else
							projectLiquidation.setTotalCostCategoriesMaterial(projectLiquidation.getTotalCostCategoriesMaterial() + category.getAmountPeso());					
					}
				}
			}
			projectLiquidation.setCategoriesHuman(associatedCategoriesHuman);
			projectLiquidation.setCategoriesMaterial(associatedCategoriesMaterial);

			// Cargo si es dolares
			projectLiquidation.setCurrencyDollar(project.getIsCurrencyDollar());

			// Cargo los empleados asociados al proyecto
			ArrayList<ProjectEmployed> associatedEmployees = project.getProjectEmployees();
			if(associatedEmployees != null && associatedEmployees.size() > 0){
				for(ProjectEmployed projectEmployed : associatedEmployees){
					Employed employed = CoreEmployed.GetInstance().getEmployed(projectEmployed.getEmployed().getId());
					projectEmployed.setEmployed(employed);
					employed.setIDAOSalarySummaries(daoManager.getDAOSalarySummaries());
					double costEmployee = employed.getSalarySummaryToDate(to).getCostRealHour() * projectEmployed.getHours();
					projectLiquidation.setTotalCostEmployees(projectLiquidation.getTotalCostEmployees() + costEmployee);					
				}
			}
			projectLiquidation.setEmployees(associatedEmployees);			
			
			//Calcula la ganancia intermedia del proyecto
			if(projectLiquidation.getTotalBills() == 0){
				projectLiquidation.setEarnings(projectLiquidation.getTotalBills() - Math.abs(projectLiquidation.getTotalCostCategoriesHuman()) - Math.abs(projectLiquidation.getTotalCostCategoriesMaterial()));
			}else{
				if(projectLiquidation.isCurrencyDollar()){
					projectLiquidation.setEarnings(projectLiquidation.getTotalBills() - Math.abs(projectLiquidation.getTotalCostCategoriesHuman()) - Math.abs(projectLiquidation.getTotalCostCategoriesMaterial()) - Math.abs((projectLiquidation.getTotalCostEmployees() / typeExchange)));
				}else{
					projectLiquidation.setEarnings(projectLiquidation.getTotalBills() - Math.abs(projectLiquidation.getTotalCostCategoriesHuman()) - Math.abs(projectLiquidation.getTotalCostCategoriesMaterial()) - Math.abs(projectLiquidation.getTotalCostEmployees()));
				}
			}			
			
			//Calcula el importe de la reserva
			projectLiquidation.setReserve(projectLiquidation.getEarnings() * Double.parseDouble(ConfigurationProperties.GetConfigValue("PERCENTAGE_RESERVE")));
			projectLiquidation.setEarnings(projectLiquidation.getEarnings() - Math.abs(projectLiquidation.getReserve()));
			
			//Calcula el importe de la venta
			projectLiquidation.setSale(projectLiquidation.getEarnings() * Double.parseDouble(ConfigurationProperties.GetConfigValue("PERCENTAGE_SALE")));
			projectLiquidation.setEarnings(projectLiquidation.getEarnings() - Math.abs(projectLiquidation.getSale()));
			
			calculatePartnersEarnings(projectLiquidation, 0, typeExchange, to);
			
		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
		return projectLiquidation;
	}	
		
	@Override
	public ArrayList<ProjectLiquidation> getProjectsLiquidationsByDate(Date month) throws ServerException, ClientException{
		DAOManager daoManager = new DAOManager();
		ArrayList<ProjectLiquidation> projectsLiquidations = new ArrayList<ProjectLiquidation>();
		
		try{	
			Calendar cal = Calendar.getInstance();
			cal.setTime(month);
			cal.set(Calendar.DAY_OF_MONTH, 01);
			Date appliedDate = cal.getTime();
			projectsLiquidations = daoManager.getDAOProjectLiquidation().getProjectsLiquidationsByDate(appliedDate);
			
			for(ProjectLiquidation projectLiquidation : projectsLiquidations){
				projectLiquidation.setEmployedPartner1(CoreEmployed.GetInstance().getEmployed(projectLiquidation.getEmployedPartner1().getId()));
				projectLiquidation.setEmployedPartner2(CoreEmployed.GetInstance().getEmployed(projectLiquidation.getEmployedPartner2().getId()));
				projectLiquidation.setProject(CoreProject.GetInstance().getProject(projectLiquidation.getProject().getId()));
			}
			
		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} catch (ClientException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}		
		return projectsLiquidations;
	}	
	
	@Override
	public void calculatePartnersEarnings(ProjectLiquidation projectLiquidation, double companyCostToSubstract, double typeExchange, Date to) throws ServerException{
		DAOManager daoManager = new DAOManager();
		try{
						
			projectLiquidation.getProject().setiDAOProjectEmployees(daoManager.getDAOEmployedProjects());
			
			//obtener la ganancia
			if(projectLiquidation.isCurrencyDollar())
				projectLiquidation.setEarnings(projectLiquidation.getEarnings() - (companyCostToSubstract/typeExchange));
			else
				projectLiquidation.setEarnings(projectLiquidation.getEarnings() - companyCostToSubstract);
			
			//Calcula la distribucion de la ganancias para cada unoa de los socios
			if(projectLiquidation.getEarnings() > 0){			
				projectLiquidation.setPartner1Earning(getPartnerEarning(projectLiquidation.getEarnings(), projectLiquidation.getPartner1().getDistributionType()));
				projectLiquidation.setPartner2Earning(getPartnerEarning(projectLiquidation.getEarnings(), projectLiquidation.getPartner2().getDistributionType()));
			}else{
				DistributionType distribution = new DistributionType(0,"50",null);
				projectLiquidation.setPartner1Earning(getPartnerEarning(projectLiquidation.getEarnings(), distribution ));
				projectLiquidation.setPartner2Earning(getPartnerEarning(projectLiquidation.getEarnings(), distribution));
			}
			
			//suma la venta a la ganancia en caso de ser socio
			if(projectLiquidation.getProject().getSeller() != null){
				if(projectLiquidation.getProject().getSeller().getId() == projectLiquidation.getPartner1().getEmployed().getId())
					projectLiquidation.setPartner1Earning(projectLiquidation.getPartner1Earning() + projectLiquidation.getSale());
				if(projectLiquidation.getProject().getSeller().getId() == projectLiquidation.getPartner2().getEmployed().getId())
					projectLiquidation.setPartner2Earning(projectLiquidation.getPartner2Earning() + projectLiquidation.getSale());			
			}
			
			//suma las horas de trabajo a los socios en caso de haber estado asignado al proyecto
			if(projectLiquidation.getProject().getProjectEmployees() != null){			
				for(ProjectEmployed projectEmployed : projectLiquidation.getProject().getProjectEmployees()){				
					projectEmployed.getEmployed().setIDAOSalarySummaries(daoManager.getDAOSalarySummaries());
					if(projectEmployed.getEmployed().getId() == projectLiquidation.getPartner1().getEmployed().getId()){
						double totalHoursCost = projectEmployed.getHours() * projectEmployed.getEmployed().getSalarySummaryToDate(to).getCostRealHour();				
						if(projectLiquidation.isCurrencyDollar())
							projectLiquidation.setPartner1Earning(projectLiquidation.getPartner1Earning() + (totalHoursCost / typeExchange));
						else
							projectLiquidation.setPartner1Earning(projectLiquidation.getPartner1Earning() + totalHoursCost);					
					}
					if(projectEmployed.getEmployed().getId() == projectLiquidation.getPartner2().getEmployed().getId()){
						double totalHoursCost = projectEmployed.getHours() * projectEmployed.getEmployed().getSalarySummaryToDate(to).getCostRealHour();				
						if(projectLiquidation.isCurrencyDollar())
							projectLiquidation.setPartner2Earning(projectLiquidation.getPartner2Earning() + (totalHoursCost / typeExchange));
						else
							projectLiquidation.setPartner2Earning(projectLiquidation.getPartner2Earning() + totalHoursCost);					
					}
				}
			}	
		}catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}		
	}
	
	//Reports 
	@Override
	public ArrayList<ProjectLiquidation> getProjectsWithMoreEarnings(Date from,
			Date to, boolean isCurrencyDollar, int count) throws ServerException {
		
		DAOManager daoManager = new DAOManager();
		
		try
		{
			return daoManager.getDAOProjectLiquidation().getProjectsWithMoreEarnings(from, to, isCurrencyDollar, count);
			
		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}
	
	// Devuelve la ganancia para el socio segun el tipo de distribucion
	private double getPartnerEarning(double projectEarning,
			DistributionType distribution) {
		double total = 0.0;
	
		switch (distribution.getValue()) {
		case "50":
			total = projectEarning * 0.5;
			break;
		case "2/3":
			total = projectEarning * 0.67;
			break;
		case "1/3":
			total = projectEarning * 0.33;
			break;
		default:
			break;
		}
		return total;
	}
}
