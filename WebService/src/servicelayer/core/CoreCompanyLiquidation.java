package servicelayer.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.Category;
import servicelayer.entity.businessEntity.CategoryType;
import servicelayer.entity.businessEntity.CompanyLiquidation;
import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.EmployedType;
import servicelayer.entity.businessEntity.IVA_Type;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.ProjectEmployed;
import servicelayer.entity.businessEntity.ProjectLiquidation;
import servicelayer.entity.businessEntity.SalarySummary;
import servicelayer.entity.businessEntity.User;
import shared.ConfigurationProperties;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreCompanyLiquidation;

public class CoreCompanyLiquidation implements ICoreCompanyLiquidation {
	
	private static CoreCompanyLiquidation instance = null;
	
	private CoreCompanyLiquidation(){
		
	}
	
	public static CoreCompanyLiquidation GetInstance() throws ServerException{
		
		if (instance == null){
			instance = new CoreCompanyLiquidation();
		}
		return instance;
	}
		
	// Realiza la liquidacion por empresa
	//Insert en false no persiste en la base (se usa para poder ver una vista previa)
	@Override
	public CompanyLiquidation liquidationByCompany(Date month, User userContext,
			double typeExchange, boolean insert) throws ServerException, ClientException {
		DAOManager daoManager = new DAOManager();
		ArrayList<Project> projects = null;
		ArrayList<Category> categories = null;
		ArrayList<ProjectLiquidation> projectsLiquidations = new ArrayList<ProjectLiquidation>();
		ArrayList<Employed> employees = null;
		CompanyLiquidation companyLiquidation = null;
		ArrayList<Category> categoriesHumanAssociateCompany = new ArrayList<Category>();
		ArrayList<Category> categoriesMaterialAssociateCompany = new ArrayList<Category>();
		ArrayList<ProjectEmployed> employeesAssociateCompany = new ArrayList<ProjectEmployed>();
				
		try {
			// Defino el periodo desde el primer dia del mes hasta el ultimo del mes
			Calendar cal = Calendar.getInstance();
			cal.setTime(month);
			cal.set(Calendar.DAY_OF_MONTH, 01);
			Date from = cal.getTime();

			cal.setTime(month);
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
			Date to = cal.getTime();
			
			
			//Controlo si existe una liquidacion para el mes seleccionado
			if (!existLiquidation(from)) {	
				companyLiquidation = new CompanyLiquidation();
				companyLiquidation.setAppliedDateTimeUTC(from);
				companyLiquidation.setCreatedDateTimeUTC(new Date());
				companyLiquidation.setTypeExchange(typeExchange);

				// Obtengo los socios
				ArrayList<Employed> partners = CoreEmployed.GetInstance()
						.getEmployedByType(2);
				//Seteo de los socios
				companyLiquidation.setPartner1(partners.get(0));
				companyLiquidation.setPartner2(partners.get(1));				
								
				// Obtengo los proyectos a liquidar, se le pasa -1 para obtener todos los proyectos
				projects = CoreProject.GetInstance()
						.getProjectsReadyToLiquidate(from, to, -1);
				
				// Obtengo los rubros en el periodo
				categories = CoreCategory.GetInstance().getCategories(from, to,
						userContext);

				// Obtengo todos los empleados hasta el ultimo dia del mes
				employees = daoManager.getDAOEmployees().getEmployeesToDate(to);
				
				if (projects != null && projects.size() > 0) {
					// Calcular la ganancia parcial
					// (total facturado - reserva - venta - rubros materiales
					// del proyecto - rubros humanos del proyecto
					for (Project project : projects) {
						ProjectLiquidation projectLiquidation = CoreProjectLiquidation.GetInstance().getProjectLiquidationPreview(month, project.getId(), typeExchange);
						projectsLiquidations.add(projectLiquidation);
					}
					
					// Obtengo el total de costos compañia
					companyLiquidation.setCompanyCategory(getCostCategoriesCompany(categories,categoriesHumanAssociateCompany,categoriesMaterialAssociateCompany));

					// Obtengo el total de aportes, el sueldo no socios, prevision despido e incidencia ticket
					// Obtengo la diferencia de sueldo a pagar
					for(Employed employed : employees){
						employed.setIDAOSalarySummaries(daoManager.getDAOSalarySummaries());
						SalarySummary salarySummary = employed.getSalarySummaryToDate(to);
						
						//total de aportes
						companyLiquidation.setContribution(companyLiquidation.getContribution() + salarySummary.getTotalDiscounts() + salarySummary.getTotalEmployerContributions());
						if(employed.getEmployedType() != EmployedType.PARTNER){
							// Sueldos no socios
							companyLiquidation.setSalaryNotPartners(companyLiquidation.getSalaryNotPartners() + salarySummary.getSalaryToPay());
							// Prevision despido
							companyLiquidation.setDismissalPrevention(companyLiquidation.getDismissalPrevention() + salarySummary.getDismissalPrevention());
							// Incidencia sueldo
							companyLiquidation.setIncidenceSalary(companyLiquidation.getIncidenceSalary() + salarySummary.getIncidenceSalary());
							// Incidencia Ticket
							companyLiquidation.setIncidenceTickets(companyLiquidation.getIncidenceTickets()	+ salarySummary.getIncidenceTickets());
							//Diferencia de sueldo a pagar
							int differenceSalaryToPay = getDifferenceEmployedHours(salarySummary.getHours(), employed.getId(), projectsLiquidations);
							if(differenceSalaryToPay > 0){
								ProjectEmployed employedCompany = new ProjectEmployed();
								employedCompany.setEmployed(employed);
								employedCompany.setHours(differenceSalaryToPay);
								employeesAssociateCompany.add(employedCompany);
							}								
							companyLiquidation.setEmployeesCost(companyLiquidation.getEmployeesCost() + (differenceSalaryToPay * salarySummary.getCostRealHour()));
						}else{
							//sueldos de los socios
							companyLiquidation.setSalaryPartners(companyLiquidation.getSalaryPartners() + salarySummary.getNominalSalary());
						}
					}

					// Obtengo el total de la facturacion de la empresa, el total de iva venta				
					double totalBills = 0.0;					
					for(ProjectLiquidation projectLiquidation : projectsLiquidations){
						totalBills = totalBills + projectLiquidation.getTotalBillsAmountPeso();	
						companyLiquidation.setIVASale(companyLiquidation.getIVASale() + projectLiquidation.getTotalIVAAmountPeso());						
					}
					companyLiquidation.setIrae(getIRAE(totalBills, companyLiquidation.getSalaryPartners()));

					// Obtengo el IVA compra(pesos)
					for (Category category : categories) {
						if (category.getIvaType() != IVA_Type.ZERO) {
							companyLiquidation
									.setIVAPurchase(companyLiquidation
											.getIVAPurchase()
											+ ((category.getAmountPeso() * category
													.getIvaType()
													.getPercentage()) - category
													.getAmountPeso()));
						}
					}
					
					//Obtiene la ganancia en dolares y pesos del mes
					for(ProjectLiquidation projectLiquidation : projectsLiquidations){
						if(projectLiquidation.isCurrencyDollar())
							companyLiquidation.setTotalEarningsDollar(companyLiquidation.getTotalEarningsDollar() + projectLiquidation.getEarnings());
						else
							companyLiquidation.setTotalEarningsPeso(companyLiquidation.getTotalEarningsPeso() + projectLiquidation.getEarnings());
					}

					// Total de costos compañia
					double companyCosts = companyLiquidation.getCompanyCategory()
							+ companyLiquidation.getContribution()
							+ companyLiquidation.getEmployeesCost()
							+ companyLiquidation.getIrae()
							+ companyLiquidation.getDismissalPrevention()
							+ companyLiquidation.getIncidenceSalary()
							+ companyLiquidation.getIncidenceTickets();
					
										
					//Calculo del porcentaje a pagar para cada proyecto, lo descuenta al proyecto y calcula la ganancia para cada socio
					calculateProjectPartnersEarnings(totalBills, projectsLiquidations, companyCosts, typeExchange, to);
										
					//Suma la ganancia total para cada socio
					for(ProjectLiquidation projectLiquidation : projectsLiquidations){
						if(projectLiquidation.getPartner1().getEmployed().getId() == companyLiquidation.getPartner1().getId()){
							if(projectLiquidation.isCurrencyDollar())
								companyLiquidation.setPartner1EarningsDollar(companyLiquidation.getPartner1EarningsDollar() + projectLiquidation.getPartner1Earning());
							else
								companyLiquidation.setPartner1EarningsPeso(companyLiquidation.getPartner1EarningsPeso() + projectLiquidation.getPartner1Earning());											
						}
						if(projectLiquidation.getPartner1().getEmployed().getId() == companyLiquidation.getPartner2().getId()){
							if(projectLiquidation.isCurrencyDollar())
								companyLiquidation.setPartner2EarningsDollar(companyLiquidation.getPartner2EarningsDollar() + projectLiquidation.getPartner1Earning());
							else
								companyLiquidation.setPartner2EarningsPeso(companyLiquidation.getPartner2EarningsPeso() + projectLiquidation.getPartner1Earning());							
						}
						if(projectLiquidation.getPartner2().getEmployed().getId() == companyLiquidation.getPartner1().getId()){
							if(projectLiquidation.isCurrencyDollar())
								companyLiquidation.setPartner1EarningsDollar(companyLiquidation.getPartner1EarningsDollar() + projectLiquidation.getPartner2Earning());
							else
								companyLiquidation.setPartner1EarningsPeso(companyLiquidation.getPartner1EarningsPeso() + projectLiquidation.getPartner2Earning());							
						}
						if(projectLiquidation.getPartner2().getEmployed().getId() == companyLiquidation.getPartner2().getId()){
							if(projectLiquidation.isCurrencyDollar())
								companyLiquidation.setPartner2EarningsDollar(companyLiquidation.getPartner2EarningsDollar() + projectLiquidation.getPartner2Earning());
							else
								companyLiquidation.setPartner2EarningsPeso(companyLiquidation.getPartner2EarningsPeso() + projectLiquidation.getPartner2Earning());							
						}						
					}			
					
					companyLiquidation.setCategoriesHuman(categoriesHumanAssociateCompany);
					companyLiquidation.setCategoriesMaterial(categoriesMaterialAssociateCompany);
					companyLiquidation.setEmployees(employeesAssociateCompany);

					if(insert){
						// Guardo la liquidacion de la empresa
						int newCompanyLiquidationId = daoManager.getDAOCompanyLiquidation().insert(companyLiquidation);
						companyLiquidation.setId(newCompanyLiquidationId);
	
						// Guardo la liquidacion de cada proyecto y las facturas se actualizan a liquidadas
						for (ProjectLiquidation projectLiquidation : projectsLiquidations) {
							int newProjectLiquidationId = daoManager.getDAOProjectLiquidation().insert(projectLiquidation);
							projectLiquidation.setId(newProjectLiquidationId);
							
							for(Bill bill : projectLiquidation.getBills()){
								bill.setIsLiquidated(true);
								daoManager.getDAOBills().liquidateBill(bill.getId());
							}
						}
					}
					
					daoManager.commit();
				}
			} else {
				throw new ClientException(
						"Ya existe liquidacion para el mes seleccionado");
			}
			
			return companyLiquidation;
		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}
	
	//Devuelve true si existe una liquidacion en la base dada una fecha
	@Override
	public boolean existLiquidation(Date month)
			throws ServerException {
		DAOManager daoManager = new DAOManager();
		try{		
			return daoManager.getDAOCompanyLiquidation().existLiquidation(month);
		}catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}
	
	//Devuelve una liquidacion de empresa dada una fecha
	@Override
	public CompanyLiquidation getCompanyLiqudationsByDate(Date month) throws ServerException, ClientException{
		DAOManager daoManager = new DAOManager();
		CompanyLiquidation companyLiquidation = null;
		
		try{	
			Calendar cal = Calendar.getInstance();
			cal.setTime(month);
			cal.set(Calendar.DAY_OF_MONTH, 01);
			Date appliedDate = cal.getTime();
			companyLiquidation = daoManager.getDAOCompanyLiquidation().getCompanyLiquidationByDate(appliedDate);
			
			companyLiquidation.setPartner1(CoreEmployed.GetInstance().getEmployed(companyLiquidation.getPartner1().getId()));
			companyLiquidation.setPartner2(CoreEmployed.GetInstance().getEmployed(companyLiquidation.getPartner2().getId()));
			
			
		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} catch (ClientException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}		
		return companyLiquidation;		
	}
	
	//Calcula el porcentaje que le corresponde pagar a cada proyecto de los costos de la compañia.
	//Calcula la ganancia para cada socio
	private void calculateProjectPartnersEarnings(double totalBills, ArrayList<ProjectLiquidation> projectsLiquidations, double totalCompanyCost, double typeExchange, Date to) throws ServerException {
		for(ProjectLiquidation projectLiquidation : projectsLiquidations){
			projectLiquidation.setCompanyCostPercentage(((projectLiquidation.getTotalBillsAmountPeso()*100)/totalBills)/100);
			CoreProjectLiquidation.GetInstance().calculatePartnersEarnings(projectLiquidation, (totalCompanyCost * projectLiquidation.getCompanyCostPercentage()), typeExchange, to);
		}		
	}
	
	// Devuelve el aporte de los costos de rubros empresa
	private double getCostCategoriesCompany(ArrayList<Category> categories, ArrayList<Category> categoriesHuman, ArrayList<Category> categoriesMaterial) {
		double total = 0.0;

		if (categories != null && categories.size() > 0) {
			for (Category category : categories) {
				if (category.getCategoryType() == CategoryType.COMPANY) {
					total = total + category.getAmountPeso();
					if(category.getIsRRHH())
						categoriesHuman.add(category);
					else
						categoriesMaterial.add(category);
				}
			}
		}
		return total;
	}	

	// Devuelve el monto del IRAE
	private double getIRAE(double totalBills, double partnersSalary)
			throws NumberFormatException, ServerException, ClientException {
		double total = 0.0;
		double net = 0.0;
	
		net = (totalBills * Double.parseDouble(ConfigurationProperties
				.GetConfigValue("FICTA_UTILITY"))) - partnersSalary;
	
		total = net
				* Double.parseDouble(ConfigurationProperties
						.GetConfigValue("PERCENTAGE_IRAE"));
	
		if (total < Double.parseDouble(ConfigurationProperties
				.GetConfigValue("MINIMUN_IRAE"))) {
			total = Double.parseDouble(ConfigurationProperties
					.GetConfigValue("MINIMUN_IRAE"));
		}
	
		return total;
	}

	// Devuelve la diferencia entre el sueldo de los empleados y los que ya
	// pagaron los proyectos
	private int getDifferenceEmployedHours(int employedHours, int employedId, ArrayList<ProjectLiquidation> projectsLiquidations){
		int totalAssignedHours = 0;
		int differenceHours = 0;
		
		//obtengo las horas asignadas para un empleado
		for(ProjectLiquidation projectLiquidation : projectsLiquidations){
			for(ProjectEmployed projectEmployed : projectLiquidation.getEmployees()){
				if(projectEmployed.getEmployed().getId() == employedId){
					totalAssignedHours = totalAssignedHours + projectEmployed.getHours();					
				}
			}			
		}
		
		if(employedHours > totalAssignedHours)
			differenceHours = employedHours - totalAssignedHours;	
		return differenceHours;
	}
}
