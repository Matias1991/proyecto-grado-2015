package servicelayer.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.Category;
import servicelayer.entity.businessEntity.CategoryType;
import servicelayer.entity.businessEntity.DistributionType;
import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.Liquidation;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.ProjectEmployed;
import servicelayer.entity.businessEntity.ProjectPartner;
import servicelayer.entity.businessEntity.SalarySummary;
import servicelayer.entity.businessEntity.User;
import shared.ConfigurationProperties;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreLiquidation;
import shared.interfaces.core.ICoreProject;


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
	public void liquidationByCompany(Date month, User userContext, Double typeExchange)throws ServerException, ClientException{
		DAOManager daoManager = new DAOManager();
		Double totalBills = 0.0;
		ArrayList<Project> projects = null;
	
		try{
			if(!existLiquidation(month)){
				projects = CoreProject.GetInstance().getProjectsToLiquidate(month);
				if(projects != null && projects.size() > 0){									
						Calendar cal = Calendar.getInstance();
						cal.setTime(month);
						cal.set(Calendar.DAY_OF_MONTH, 01);
						Date from = cal.getTime();
						
						cal.setTime(month);
						cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));			
						Date to = cal.getTime();	
						
						//Total de facturacion para el período
						totalBills = getTotalBillsToLiquidate(from, to, userContext);
						
						for (Project project : projects) {
							liquidationByProject(project, month, userContext, totalBills, typeExchange, daoManager);
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
	
	private void liquidationByProject(Project project,Date month, User userContext, double totalBills, double typeExchange, DAOManager daoManager) throws ServerException, ClientException{
		//DAOManager daoManager = new DAOManager();
		Liquidation liquidation = new Liquidation();
		
		try{			
			//Project project = CoreProject.GetInstance().getProject(projectId);
			liquidation.setProject(project);
			liquidation.setAppliedDateTimeUTC(month);
			liquidation.setCreatedDateTimeUTC(new Date());
			liquidation.setTypeExchange(typeExchange);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(month);
			cal.set(Calendar.DAY_OF_MONTH, 01);
			Date from = cal.getTime();		
			
			cal.setTime(month);
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));			
			Date to = cal.getTime();		
			
			//Total de facturación en el período para el projecto
			liquidation.setTotalBills(getBillsToLiquidateByProject(project, from, to, userContext));
			
			//Total de rubros humanos asociados al proyecto en el periodo
			liquidation.setTotalCostCategoriesHuman(getCostCategoriesHumanByProject(project, from, to, userContext));			
			
			//Total de rubros materiales asociados al proyecto en el periodo
			liquidation.setTotalCostCategoriesMaterial(getCostCategoriesMaterialByProject(project, from, to, userContext));
						
			//Total de rubros empresa que le toca pagar al proyecto (paga el porcentaje de facturacion con respecto al total de facturacion)
			liquidation.setTotalCostCategoriesCompany(getCostCategoriesCompany(from, to, userContext)*getPercentage(project, totalBills, from, to, userContext));
			
			//Para cada empleado asociado, el costo empresa * cantidad de horas asignadas al proyecto
			liquidation.setTotalCostHoursEmployees(getCostHoursByProject(project, typeExchange));
			
			//Ganancia = total de facturacion menos el total de los rubros mas los costos de empleado
			double earning = liquidation.getTotalBills() - liquidation.getTotalCostCategoriesCompany() - liquidation.getTotalCostCategoriesHuman() - liquidation.getTotalCostCategoriesMaterial() - liquidation.getTotalCostHoursEmployees();
			liquidation.setEarnings(earning);	
			
			//Reserva segun porcentaje en las properties
			liquidation.setReserve(earning * Double.parseDouble(ConfigurationProperties.GetConfigValue("PERCENTAGE_RESERVE")));							
			
			//Venta segun porcentaje en las properties
			liquidation.setSale(earning * Double.parseDouble(ConfigurationProperties.GetConfigValue("PERCENTAGE_SALE")));
			
			//Ganancia a dividir = ganancia - reserva - venta
			Double earningsToDivide = earning - liquidation.getReserve() - liquidation.getSale();
			
			liquidation.getProject().setiDAOPartnerProject(daoManager.getDAOPartnerProjects());
			ArrayList<ProjectPartner> projectPartners = liquidation.getProject().getiDAOPartnerProject().getPartnersProject(project.getId());
			//Total ganancia socio 1 = Ganancia a dividir * distribucion			
			liquidation.setPartner1(projectPartners.get(0).getEmployed());			
			liquidation.setPartner1Earning(getPartnerEarning(earningsToDivide,getDistributionType(projectPartners.get(0).getDistributionType().getId(), CoreProject.GetInstance().getDistributionTypes())));
			
		
			//Total ganancia socio 2 = Ganancia a diviidir * distribucion
			liquidation.setPartner2Earning(getPartnerEarning(earningsToDivide,getDistributionType(projectPartners.get(1).getDistributionType().getId(), CoreProject.GetInstance().getDistributionTypes())));
			liquidation.setPartner2(projectPartners.get(1).getEmployed());		
			
		
			int newLiquidationId = daoManager.getDAOLiquidation().insert(liquidation);
			liquidation.setId(newLiquidationId);
			
			//daoManager.commit();
			
		}catch (ServerException e) {
//			daoManager.rollback();
//			throw e;
		} finally {
//			daoManager.close();
		}		
	}
	

	
	@Override
	public boolean existLiquidation(Date month) throws ServerException {
		DAOManager daoManager = new DAOManager();
		return daoManager.getDAOLiquidation().existLiquidation(month);
	}
		
	//Devuelve el total de facutracion para el periodo
	private double getTotalBillsToLiquidate(Date from, Date to, User userContext)throws ServerException{
		Double total = 0.0;
		ArrayList<Bill> bills = CoreBill.GetInstance().getBills(from, to, false, userContext);		
		
		for (Bill bill : bills){
			total = total + bill.getAmountPeso();
		}
		
		return total;
	}
	
	//Devuelve la cantidad total de las facturas realizadas en un periodo para un proyecto
	private double getBillsToLiquidateByProject(Project project, Date from, Date to, User userContext) throws ServerException{		
		Double total = 0.0;
		ArrayList<Bill> bills = CoreBill.GetInstance().getBills(from, to, false, userContext);
		
		for (Bill bill : bills) {
			if(bill.getProject().getId() == project.getId()){
				if(project.getIsCurrencyDollar()){
					total = total + bill.getTotalAmountDollar();
				}else{
					total = total + bill.getTotalAmountPeso();
				}
			}
		}						
		return total;
	}
	
	//Devuelve la cantidad total del costo de horas de los empleados asociados al proyecto
	private double getCostHoursByProject(Project project, double typeExchange) throws ServerException, ClientException{
		DAOManager daoManager = new DAOManager();
		double total = 0.0;
		ArrayList<ProjectEmployed> projectEmployees = CoreProject.GetInstance().getProjectEmployees(project.getId());		
		
		if(projectEmployees != null && projectEmployees.size() > 0){
			for (ProjectEmployed projectEmployed : projectEmployees) {	
				Employed emp = projectEmployed.getEmployed();				
				emp.setIDAOSalarySummaries(daoManager.getDAOSalarySummaries());
				Double realCost = emp.getLatestVersionSalarySummary().getCostRealHour();
				total = total + (realCost * projectEmployed.getHours());				
			}
		}
		//convierto a dolares
		if(project.getIsCurrencyDollar()){
			total = total / typeExchange;
		}		
		return total;
	}
	
	//Devuelve la cantidad total de los rubros humanos asociados al proyecto
	private double getCostCategoriesHumanByProject(Project project, Date from, Date to, User userContext) throws ServerException{		
		double total = 0.0;
		ArrayList<Category> categories = CoreCategory.GetInstance().getCategories(from, to, userContext);
		
		if(categories != null && categories.size() > 0){
			for (Category category : categories) {				
				if(category.getProject() != null && category.getProject().getId() == project.getId() && category.getIsRRHH()){
					if(project.getIsCurrencyDollar()){
						total = total + category.getTotalAmountDollar();
					}else{
						total = total + category.getTotalAmountPeso();
					}
				}			
			}
		}
	
		return total;
	}
	
	//Devuelve la cantidad total de los rubros materiales asociados al proyecto
	private double getCostCategoriesMaterialByProject(Project project, Date from, Date to, User userContext) throws ServerException{
		double total = 0.0;
		ArrayList<Category> categories = CoreCategory.GetInstance().getCategories(from, to, userContext);
		
		if(categories != null && categories.size() > 0){
			for (Category category : categories) {
				if(category.getProject() != null && category.getProject().getId() == project.getId() && !category.getIsRRHH()){
					if(project.getIsCurrencyDollar()){
						total = total + category.getAmountDollar();
					}else{
						total = total + category.getTotalAmountPeso();
					}
				}
			}
		}
		
		return total;		
	}
	
	//Devuelve el aporte de los costos de rubros empresa
	private double getCostCategoriesCompany(Date from, Date to, User userContext) throws ServerException{
		double total = 0.0;
		ArrayList<Category> categories = CoreCategory.GetInstance().getCategories(from, to, userContext);
		
		if(categories != null && categories.size() > 0){
			for (Category category : categories) {
				if(category.getCategoryType() == CategoryType.COMPANY){
					total = total + category.getTotalAmountPeso();
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
	private double getPartnerEarning(double projectEarning, DistributionType distribution){
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
	
	//Devuelve el porcentaje del monto facturado con respecto al monto total de facturacion del mes
	private double getPercentage(Project project, double totalBills, Date from, Date to, User userContext) throws ServerException{
		Double total = 0.0;
		Double percentage = 0.0;
		ArrayList<Bill> bills = CoreBill.GetInstance().getBills(from, to, false, userContext);
		
		for (Bill bill : bills) {
			if(bill.getProject().getId() == project.getId()){				
				total = total + bill.getTotalAmountPeso();				
			}
		}	
		
		percentage = ((total * 100)/totalBills)/100;
		return percentage;
	}
	
	//Realiza la liquidacion por projecto	
//	private void liquidationByProject(int projectId,Date month, User userContext, int totalProjects, double companyCost) throws ServerException, ClientException{
//		DAOManager daoManager = new DAOManager();
//		Liquidation liquidation = new Liquidation();
//		
//		try{			
//			liquidation.setProject(CoreProject.GetInstance().getProject(projectId));
//			liquidation.setAppliedDateTimeUTC(month);
//			liquidation.setCreatedDateTimeUTC(new Date());
//			
//			Calendar cal = Calendar.getInstance();
//			cal.setTime(month);
//			cal.set(Calendar.DAY_OF_MONTH, 01);
//			Date from = cal.getTime();		
//			
//			cal.setTime(month);
//			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));			
//			Date to = cal.getTime();		
//			liquidation.setTotalBills(getBillsToLiquidateByProject(projectId, from, to, userContext));
//			
//			liquidation.setTotalCostHoursEmployees(getCostHoursByProject(projectId));						
//			liquidation.setTotalCostCategoriesHuman(getCostCategoriesHumanByProject(projectId, from, to, userContext));
//			liquidation.setTotalCostCategoriesMaterial(getCostCategoriesMaterialByProject(projectId, from, to, userContext));
//			liquidation.setTotalCostCategoriesCompany(companyCost);
//			
//			Double earnings = liquidation.getTotalBills() - liquidation.getTotalCostCategoriesCompany() - liquidation.getTotalCostCategoriesHuman() - liquidation.getTotalCostCategoriesMaterial() - liquidation.getTotalCostHoursEmployees();
//			liquidation.setEarnings(earnings);	
//			
//			liquidation.setReserve(earnings * Double.parseDouble(ConfigurationProperties.GetConfigValue("PERCENTAGE_RESERVE")));
//			liquidation.setSale(earnings * Double.parseDouble(ConfigurationProperties.GetConfigValue("PERCENTAGE_SALE")));	
//			
//			Double earningsToDivide = earnings - liquidation.getReserve() - liquidation.getSale();
//			liquidation.getProject().setiDAOPartnerProject(daoManager.getDAOPartnerProjects());
//			
//			ArrayList<ProjectPartner> projectPartners = liquidation.getProject().getiDAOPartnerProject().getPartnersProject(projectId);
//			liquidation.setPartner1(projectPartners.get(0).getEmployed());			
//			liquidation.setPartner1Earning(getPartnerEarning(earningsToDivide,getDistributionType(projectPartners.get(0).getDistributionType().getId(), CoreProject.GetInstance().getDistributionTypes())));
//			liquidation.setPartner2(projectPartners.get(1).getEmployed());
//			liquidation.setPartner2Earning(getPartnerEarning(earningsToDivide,getDistributionType(projectPartners.get(1).getDistributionType().getId(), CoreProject.GetInstance().getDistributionTypes())));
//			
//			int newLiquidationId = daoManager.getDAOLiquidation().insert(liquidation);
//			liquidation.setId(newLiquidationId);
//			
//			daoManager.commit();
//			
//		}catch (ServerException e) {
//			daoManager.rollback();
//			throw e;
//		} finally {
//			daoManager.close();
//		}		
//	}
}
