package servicelayer.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.Category;
import servicelayer.entity.businessEntity.CategoryType;
import servicelayer.entity.businessEntity.CompanyLiquidation;
import servicelayer.entity.businessEntity.DistributionType;
import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.EmployedType;
import servicelayer.entity.businessEntity.IVA_Type;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.ProjectEmployed;
import servicelayer.entity.businessEntity.ProjectLiquidation;
import servicelayer.entity.businessEntity.ProjectPartner;
import servicelayer.entity.businessEntity.SalarySummary;
import servicelayer.entity.businessEntity.User;
import shared.ConfigurationProperties;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreLiquidation;


public class CoreLiquidation implements ICoreLiquidation{
	
	private static CoreLiquidation instance = null;
	
	private CoreLiquidation(){		
	}
	
	public static CoreLiquidation GetInstance() throws ServerException {
		if(instance == null){
			instance = new CoreLiquidation();
		}
		return instance;
	}
	
	//Realiza la liquidacion por empresa
	@Override
	public void liquidationByCompany(Date month, User userContext, double typeExchange)throws ServerException, ClientException{
		DAOManager daoManager = new DAOManager();		
		ArrayList<Project> projects = null;
		ArrayList<Category> categories = null;
		ArrayList<Bill> bills = null;
		ArrayList<ProjectLiquidation> liquidations = new ArrayList<ProjectLiquidation>();
		ArrayList<Employed> employees = null; 
		CompanyLiquidation companyLiquidation = new CompanyLiquidation();
			
		try{
			if(!existLiquidation(month, daoManager)){
				companyLiquidation.setAppliedDateTimeUTC(month);
				companyLiquidation.setCreatedDateTimeUTC(new Date());
				companyLiquidation.setTypeExchange(typeExchange);
				
				//Obtengo los socios
				ArrayList<Employed> partners = CoreEmployed.GetInstance().getEmployedByType(2);
				companyLiquidation.setPartner1(partners.get(0));
				companyLiquidation.setPartner2(partners.get(1));
				
				//Defino el periodo
				Calendar cal = Calendar.getInstance();
				cal.setTime(month);
				cal.set(Calendar.DAY_OF_MONTH, 01);
				Date from = cal.getTime();
				
				cal.setTime(month);
				cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));			
				Date to = cal.getTime();	
				
				//Obtengo los rubros en el periodo
				categories = CoreCategory.GetInstance().getCategories(from, to, userContext);
				
				//Obtengo las facturas en el periodo
				bills = CoreBill.GetInstance().getBills(from, to, false, userContext);
				
				//Obtengo los proyectos a liquidar
				projects = CoreProject.GetInstance().getProjectsToLiquidate(from, to);
				
				//Obtengo todos los empleados a la fecha
				employees = CoreEmployed.GetInstance().getEmployeesToDate(to);
				
				if(projects != null && projects.size() > 0){					
						//Calcular la ganancia parcial 
						//(total facturado - reserva - venta - rubros materiales del proyecto - rubros humanos del proyecto
						for (Project project : projects) {
							ProjectLiquidation liquidation = new ProjectLiquidation();
							liquidation.setProject(project);
							liquidation.setAppliedDateTimeUTC(month);
							liquidation.setCreatedDateTimeUTC(new Date());
							liquidation.setCurrencyDollar(project.getIsCurrencyDollar());
							liquidations.add(partialLiquidationByProject(liquidation, project, categories, bills, typeExchange, to, daoManager));							
						}	
						
						//Obtengo el total de costos compañia
						companyLiquidation.setCompanyCategory(getCostCategoriesCompany(categories));
						
						//Obtengo el total de aportes
						for (SalarySummary salarySummary : CoreEmployed.GetInstance().getSalarySummariesLatestVersionUpToDate(to)) {
							companyLiquidation.setContribution(companyLiquidation.getContribution() + salarySummary.getTotalDiscounts() + salarySummary.getTotalEmployerContributions());							
						}						
								
						//Obtengo el total de sueldos no socios, prevision despido, incidencia sueldo e incidencia ticket
						for(Employed employed :CoreEmployed.GetInstance().getEmployess()){
							if(employed.getEmployedType() != EmployedType.PARTNER){
								employed.setIDAOSalarySummaries(daoManager.getDAOSalarySummaries());
								SalarySummary salary = employed.getSalarySummaryToDate(to);
								//Sueldos no socios
								companyLiquidation.setSalaryNotPartners(companyLiquidation.getSalaryNotPartners() + salary.getSalaryToPay());
								//Prevision despido
								companyLiquidation.setDismissalPrevention(companyLiquidation.getDismissalPrevention() + salary.getDismissalPrevention());
								//Incidencia sueldo
								companyLiquidation.setIncidenceSalary(companyLiquidation.getIncidenceSalary() + salary.getIncidenceSalary());
								//Incidencia Ticket
								companyLiquidation.setIncidenceTickets(companyLiquidation.getIncidenceTickets() + salary.getIncidenceTickets());
							}
						}					
						
						//Obtengo el total del IRAE
						double  totalBills = 0.0;
						for (Bill bill : bills) {
							totalBills = totalBills + bill.getAmountPeso();
						}
						companyLiquidation.setIrae(getIRAE(totalBills, to, daoManager));
						
						//Obtengo el IVA venta (pesos)
						for (Bill bill : bills){
							if(bill.getIvaType() != IVA_Type.ZERO){
								companyLiquidation.setIVASale(companyLiquidation.getIVASale() + ((bill.getAmountPeso() * bill.getIvaType().getPercentage())-bill.getAmountPeso()));
							}							
						}
						
						//Obtengo el IVA compra(pesos)
						for(Category category : categories){
							if(category.getIvaType() != IVA_Type.ZERO){
								companyLiquidation.setIVAPurchase(companyLiquidation.getIVAPurchase() + ((category.getAmountPeso() * category.getIvaType().getPercentage())- category.getAmountPeso()));
							}
						}
						
						//Diferencia de horas que debe pagar la empresa de los empleados
						double difEmployeesCost = 0.0;
						for(Employed employed : employees){
							if(employed.getEmployedType() != EmployedType.PARTNER){
								difEmployeesCost = difEmployeesCost + getDiferenceEmployedCost(projects, employed, to, daoManager);
							}
						}
												
						//Total de costos compañia
						double  companyCosts = 	companyLiquidation.getCompanyCategory() + 
												companyLiquidation.getContribution() + 
												difEmployeesCost + 
												companyLiquidation.getIrae() + 
												companyLiquidation.getDismissalPrevention() + 
												companyLiquidation.getIncidenceSalary() + 
												companyLiquidation.getIncidenceTickets();
												
						//Ordenar el arraylist por ganancia de mayor a menor
						Collections.sort(liquidations);						
						
						//Descuento los costos compañia a las liquidaciones de mayor ganancia 
						//hasta que se descuente todo los costos o se recorran todas las liquidaciones												 
						for (ProjectLiquidation liquidation : liquidations) {
							if (companyCosts > 0){
								if(liquidation.isCurrencyDollar()){
									//Dolares
									if(companyCosts >= (liquidation.getEarnings() * typeExchange)){
										companyCosts = companyCosts - (liquidation.getEarnings() * typeExchange);
										liquidation.setEarnings(0.0);
									}else{
										liquidation.setEarnings(((liquidation.getEarnings() * typeExchange) - companyCosts) / typeExchange);
										companyCosts = 0.0;		
									}
								}else{
									//Pesos
									if(companyCosts >= liquidation.getEarnings()){
										companyCosts = companyCosts - liquidation.getEarnings();
										liquidation.setEarnings(0.0);
									}else{
										liquidation.setEarnings(liquidation.getEarnings() - companyCosts);
										companyCosts = 0.0;
									}
								}							
							}
						}
						
						//Ganancias del proyecto para los socios
						for (ProjectLiquidation liquidation : liquidations) {
							liquidation.getProject().setiDAOPartnerProject(daoManager.getDAOPartnerProjects());
							ArrayList<ProjectPartner> projectPartners = liquidation.getProject().getiDAOPartnerProject().getPartnersProject(liquidation.getProject().getId());
							
							//Total ganancia socio 1 = Ganancia a dividir * distribucion			
							liquidation.setPartner1(projectPartners.get(0).getEmployed());			
							liquidation.setPartner1Earning(getPartnerEarning(liquidation.getEarnings(),getDistributionType(projectPartners.get(0).getDistributionType().getId(), CoreProject.GetInstance().getDistributionTypes())));
							//Sumo la venta a la ganancia del socio
							if(liquidation.getProject().getSeller().getId() == liquidation.getPartner1().getId()){
								liquidation.setPartner1Earning(liquidation.getPartner1Earning() + liquidation.getSale());
							}
						
							//Total ganancia socio 2 = Ganancia a dividir * distribucion
							liquidation.setPartner2(projectPartners.get(1).getEmployed());	
							liquidation.setPartner2Earning(getPartnerEarning(liquidation.getEarnings(),getDistributionType(projectPartners.get(1).getDistributionType().getId(), CoreProject.GetInstance().getDistributionTypes())));
							//Sumo la venta a la ganancia del socio
							if(liquidation.getProject().getSeller().getId() == liquidation.getPartner2().getId()){
								liquidation.setPartner2Earning(liquidation.getPartner2Earning() + liquidation.getSale());
							}
							
							//Sumo a la ganancia el total de las horas trabajadas en proyectos asingados
							liquidation.setPartner1Earning(liquidation.getPartner1Earning() + getCostHoursPartner(projects, liquidation.getPartner1().getId(), to, daoManager));
							liquidation.setPartner2Earning(liquidation.getPartner2Earning() + getCostHoursPartner(projects, liquidation.getPartner2().getId(), to, daoManager));
							
							
							//Cargo el total de ganancias en la compañia
							if(companyLiquidation.getPartner1().getId() == liquidation.getPartner1().getId()){
								if(liquidation.getProject().getIsCurrencyDollar()){
									companyLiquidation.setPartner1EarningsDollar(companyLiquidation.getPartner1EarningsDollar() +  liquidation.getPartner1Earning());
									companyLiquidation.setPartner2EarningsDollar(companyLiquidation.getPartner2EarningsDollar() +  liquidation.getPartner2Earning());
								}else{
									companyLiquidation.setPartner1EarningsPeso(companyLiquidation.getPartner1EarningsPeso() +  liquidation.getPartner1Earning());
									companyLiquidation.setPartner2EarningsPeso(companyLiquidation.getPartner2EarningsPeso() +  liquidation.getPartner2Earning());						
								}
							}else{
								if(liquidation.getProject().getIsCurrencyDollar()){
									companyLiquidation.setPartner2EarningsDollar(companyLiquidation.getPartner1EarningsDollar() +  liquidation.getPartner1Earning());
									companyLiquidation.setPartner1EarningsDollar(companyLiquidation.getPartner2EarningsDollar() +  liquidation.getPartner2Earning());
								}else{
									companyLiquidation.setPartner2EarningsPeso(companyLiquidation.getPartner1EarningsPeso() +  liquidation.getPartner1Earning());
									companyLiquidation.setPartner1EarningsPeso(companyLiquidation.getPartner2EarningsPeso() +  liquidation.getPartner2Earning());						
								}
							}							
								
						}
						
						//Guardo la liquidacion de la empresa
						int newCompanyLiquidationId = daoManager.getDAOCompanyLiquidation().insert(companyLiquidation);
						companyLiquidation.setId(newCompanyLiquidationId);
						
						//Guardo cada liquidacion en la tabla Liquidation
						for (ProjectLiquidation liquidation : liquidations) {
							int newProjectLiquidationId = daoManager.getDAOProjectLiquidation().insert(liquidation);
							liquidation.setId(newProjectLiquidationId);							
						}
						
						//Actualizo las facturas a liquidated = true
						for (Bill bill : bills) {
							bill.setIsLiquidated(true);
							CoreBill.GetInstance().liquidateBill(bill.getId());
						}
						
					daoManager.commit();
				}
			}else{
				throw new ClientException("Ya existe liquidacion para el mes seleccionado");
			}
		}catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}		
	}
	
	@Override
	public boolean existLiquidation(Date month, DAOManager daoManager) throws ServerException {				
		return daoManager.getDAOCompanyLiquidation().existLiquidation(month);
	}
	
	//Devuelve el proyecto con el calculo parcial de la ganancia
	private ProjectLiquidation partialLiquidationByProject(ProjectLiquidation liquidation, Project project, ArrayList<Category> categories, ArrayList<Bill> bills, double  typeExchange, Date to, DAOManager daoManager) throws NumberFormatException, ServerException{
		double partialEarning = 0.0;		
		
		//Total de facturacion				
		liquidation.setTotalBills(getBillsToLiquidateByProject(project, bills));
		
		//Total de rubros humanos asociados al proyecto
		liquidation.setTotalCostCategoriesHuman(getCostCategoriesHumanByProject(project, categories));
		
		//Total de rubros materiales asociados al proyecto
		liquidation.setTotalCostCategoriesMaterial(getCostCategoriesMaterialByProject(project, categories));
		
		//Total del costo de horas de los empleados asociados al proyecto
		liquidation.setTotalCostEmployees(getCostHoursEmployeeByProject(project, to, daoManager, typeExchange));
				
		partialEarning = liquidation.getTotalBills() - (liquidation.getTotalCostCategoriesHuman() + liquidation.getTotalCostCategoriesMaterial() - liquidation.getTotalCostEmployees());
		
		//Reserva
		liquidation.setReserve(partialEarning * Double.parseDouble(ConfigurationProperties.GetConfigValue("PERCENTAGE_RESERVE")));
		partialEarning = partialEarning - liquidation.getReserve();
		
		//Venta
		liquidation.setSale(partialEarning * Double.parseDouble(ConfigurationProperties.GetConfigValue("PERCENTAGE_SALE")));
		partialEarning = partialEarning - liquidation.getSale();
				
		//Ganancia parcial del proyecto
		//double earnings = totalBills - liquidation.getReserve() - liquidation.getSale() - liquidation.getTotalCostCategoriesHuman() - liquidation.getTotalCostCategoriesMaterial();
		liquidation.setEarnings(partialEarning);
		
		return liquidation;		
	}
	
	//Devuelve la cantidad total de las facturas realizadas en un periodo para un proyecto
	private double  getBillsToLiquidateByProject(Project project, ArrayList<Bill> bills){		
		double total = 0.0;
		
		for (Bill bill : bills) {
			if(bill.getProject().getId() == project.getId()){
				if(project.getIsCurrencyDollar()){
					total = total + bill.getAmountDollar();
				}else{
					total = total + bill.getAmountPeso();
				}
			}
		}						
		return total;
	}
	
	//Devuelve la cantidad total de los rubros humanos asociados al proyecto
	private double  getCostCategoriesHumanByProject(Project project, ArrayList<Category> categories){		
		double  total = 0.0;
		
		if(categories != null && categories.size() > 0){
			for (Category category : categories) {				
				if(category.getProject() != null && category.getProject().getId() == project.getId() && category.getIsRRHH()){
					if(project.getIsCurrencyDollar()){
						total = total + category.getAmountDollar();
					}else{
						total = total + category.getAmountPeso();
					}
				}			
			}
		}	
		return total;
	}
	
	//Devuelve la cantidad total de los rubros materiales asociados al proyecto
	private double  getCostCategoriesMaterialByProject(Project project, ArrayList<Category> categories){
		double  total = 0.0;
				
		if(categories != null && categories.size() > 0){
			for (Category category : categories) {
				if(category.getProject() != null && category.getProject().getId() == project.getId() && !category.getIsRRHH()){
					if(project.getIsCurrencyDollar()){
						total = total + category.getAmountDollar();
					}else{
						total = total + category.getAmountPeso();
					}
				}
			}
		}		
		return total;		
	}
	
	//Devuelve el costo total de los empleados asociados al proyecto
	private double getCostHoursEmployeeByProject(Project project, Date to, DAOManager daoManager, double typeExchange) throws ServerException{
		double total = 0.0;
		
		if(project != null){
			project.setiDAOProjectEmployees(daoManager.getDAOEmployedProjects());
			
			for(ProjectEmployed projectEmployed : project.getiDAOProjectEmployees().getEmployeesProject(project.getId())){
				projectEmployed.getEmployed().setIDAOSalarySummaries(daoManager.getDAOSalarySummaries());
				SalarySummary salary = projectEmployed.getEmployed().getLatestVersionSalarySummary();			
				while (salary.getCreatedDateTimeUTC().compareTo(to) > 0){
					salary = projectEmployed.getEmployed().getSalarySummaryByVersion(salary.getVersion() - 1);
				}				
				total = total + (salary.getCostRealHour() * projectEmployed.getHours());				
			}
			if(project.getIsCurrencyDollar()){
				total = total / typeExchange;
			}
		}
		return total;
	}
	
	//Devuelve el aporte de los costos de rubros empresa
	private double  getCostCategoriesCompany(ArrayList<Category> categories){
		double  total = 0.0;
		
		if(categories != null && categories.size() > 0){
			for (Category category : categories) {
				if(category.getCategoryType() == CategoryType.COMPANY){
					total = total + category.getAmountPeso();
				}
			}
		}	
		return total;	
	}
	
	//Devuelve la distribucion correspondiente
	private DistributionType getDistributionType(int id, ArrayList<DistributionType> distributions){
		DistributionType result = null;
		
		for (DistributionType distributionType : distributions) {
			if(distributionType.getId() == id){
				result = distributionType;
			}			
		}		
		return result;		
	}
	
	//Devuelve la ganancia para el socio segun el tipo de distribucion
	private double  getPartnerEarning(double  projectEarning, DistributionType distribution){
		double  total = 0.0;	
		
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
	
	//Devuelve el monto del IRAE
	private double  getIRAE(double  totalBills, Date to, DAOManager daoManager) throws NumberFormatException, ServerException, ClientException{
		double  total = 0.0;
		double  net = 0.0;
		double  partnersSalary = 0.0;
		
		ArrayList<Employed> partners = CoreEmployed.GetInstance().getEmployedByType(2);
		
		for (Employed employed : partners) {			
			employed.setIDAOSalarySummaries(daoManager.getDAOSalarySummaries());
			SalarySummary salary = employed.getLatestVersionSalarySummary();
			while (salary.getCreatedDateTimeUTC().compareTo(to) > 0){
				salary = employed.getSalarySummaryByVersion(salary.getVersion() - 1);
			}
			partnersSalary = partnersSalary + salary.getNominalSalary();
		}
		
		net = (totalBills * Double.parseDouble(ConfigurationProperties.GetConfigValue("FICTA_UTILITY"))) - partnersSalary;
		
		total = net * Double.parseDouble(ConfigurationProperties.GetConfigValue("PERCENTAGE_IRAE"));
		
		if(total < Double.parseDouble(ConfigurationProperties.GetConfigValue("MINIMUN_IRAE"))){
			total = Double.parseDouble(ConfigurationProperties.GetConfigValue("MINIMUN_IRAE"));
		}
		
		return total;
	}
	
	//Devuelve el costo total de los empleados asociados al proyecto
	private double getCostHoursPartner(ArrayList<Project> projects, int partnerId, Date to, DAOManager daoManager) throws ServerException{
		double total = 0.0;
		
		if(projects != null && projects.size() > 0){
			for(Project project : projects){
				project.setiDAOProjectEmployees(daoManager.getDAOEmployedProjects());
			
				for(ProjectEmployed projectEmployed : project.getiDAOProjectEmployees().getEmployeesProject(project.getId())){
					if(projectEmployed.getEmployed().getId() == partnerId){
						projectEmployed.getEmployed().setIDAOSalarySummaries(daoManager.getDAOSalarySummaries());
						SalarySummary salary = projectEmployed.getEmployed().getLatestVersionSalarySummary();			
						while (salary.getCreatedDateTimeUTC().compareTo(to) > 0){
							salary = projectEmployed.getEmployed().getSalarySummaryByVersion(salary.getVersion() - 1);
						}					
					total = total + (salary.getCostRealHour() * projectEmployed.getHours());
					}
				}
			}
		}
		return total;
	}
	
	//Devuelve la diferencia entre el sueldo de los empleados y los que ya pagaron los proyectos
	private double getDiferenceEmployedCost(ArrayList <Project> projects, Employed employed, Date to, DAOManager daoManager) throws ServerException{
		int totalHoursAssigned = 0;
		double differenceToPay = 0.0;
				
		//Obtengo el resumen del salario que corresponde al periodo
		employed.setIDAOSalarySummaries(daoManager.getDAOSalarySummaries());
		SalarySummary salary = employed.getLatestVersionSalarySummary();			
		while (salary.getCreatedDateTimeUTC().compareTo(to) > 0){
			salary = employed.getSalarySummaryByVersion(salary.getVersion() - 1);
		}
		
		if(projects != null && projects.size() > 0){			
			for(Project project : projects){
				project.setiDAOProjectEmployees(daoManager.getDAOEmployedProjects());
			
				for(ProjectEmployed projectEmployed : project.getiDAOProjectEmployees().getEmployeesProject(project.getId())){
					if(projectEmployed.getEmployed().getId() == employed.getId()){											
						totalHoursAssigned = totalHoursAssigned + projectEmployed.getHours();
					}
				}
			}
		}		
		if(totalHoursAssigned < salary.getHours()){
			differenceToPay = (salary.getHours() - totalHoursAssigned) * salary.getCostRealHour();			
		}
		return differenceToPay;
	}
	
}
