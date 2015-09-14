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
	public void liquidationByCompany(ArrayList<Project> projects, Date month, User userContext)throws ServerException, ClientException{
		int totalProjects = 0;
		Double companyCost = 0.0;
		try{
			if(projects != null && projects.size() > 0){
				if(!existLiquidation(month)){
					totalProjects = projects.size();
					
					Calendar cal = Calendar.getInstance();
					cal.setTime(month);
					cal.set(Calendar.DAY_OF_MONTH, 01);
					Date from = cal.getTime();
					
					cal.setTime(month);
					cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));			
					Date to = cal.getTime();	
					
					companyCost = getCostCategoriesCompany(from, to, userContext)/totalProjects;
					for (Project project : projects) {
						liquidationByProject(project.getId(), month, userContext, totalProjects, companyCost);
					}	
				}else{
					throw new ClientException("Ya existe liquidacion para el mes seleccionado");
				}
			}
		}catch (ServerException e) {			
			throw e;
		} 		
	}
	
	//Realiza la liquidacion por projecto	
	private void liquidationByProject(int projectId,Date month, User userContext, int totalProjects, double companyCost) throws ServerException, ClientException{
		DAOManager daoManager = new DAOManager();
		Liquidation liquidation = new Liquidation();
		
		try{			
			liquidation.setProject(CoreProject.GetInstance().getProject(projectId));
			liquidation.setAppliedDateTimeUTC(month);
			liquidation.setCreatedDateTimeUTC(new Date());
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(month);
			cal.set(Calendar.DAY_OF_MONTH, 01);
			Date from = cal.getTime();		
			
			cal.setTime(month);
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));			
			Date to = cal.getTime();		
			liquidation.setTotalBills(getBillsToLiquidateByProject(projectId, from, to, userContext));
			
			liquidation.setTotalCostHoursEmployees(getCostHoursByProject(projectId));						
			liquidation.setTotalCostCategoriesHuman(getCostCategoriesHumanByProject(projectId, from, to, userContext));
			liquidation.setTotalCostCategoriesMaterial(getCostCategoriesMaterialByProject(projectId, from, to, userContext));
			liquidation.setTotalCostCategoriesCompany(companyCost);
			
			Double earnings = liquidation.getTotalBills() - liquidation.getTotalCostCategoriesCompany() - liquidation.getTotalCostCategoriesHuman() - liquidation.getTotalCostCategoriesMaterial() - liquidation.getTotalCostHoursEmployees();
			liquidation.setEarnings(earnings);	
			
			liquidation.setReserve(earnings * Double.parseDouble(ConfigurationProperties.GetConfigValue("PERCENTAGE_RESERVE")));
			liquidation.setSale(earnings * Double.parseDouble(ConfigurationProperties.GetConfigValue("PERCENTAGE_SALE")));	
			
			Double earningsToDivide = earnings - liquidation.getReserve() - liquidation.getSale();
			liquidation.getProject().setiDAOPartnerProject(daoManager.getDAOPartnerProjects());
			
			ArrayList<ProjectPartner> projectPartners = liquidation.getProject().getiDAOPartnerProject().getPartnersProject(projectId);
			liquidation.setPartner1(projectPartners.get(0).getEmployed());			
			liquidation.setPartner1Earning(getPartnerEarning(earningsToDivide,getDistributionType(projectPartners.get(0).getDistributionType().getId(), CoreProject.GetInstance().getDistributionTypes())));
			liquidation.setPartner2(projectPartners.get(1).getEmployed());
			liquidation.setPartner2Earning(getPartnerEarning(earningsToDivide,getDistributionType(projectPartners.get(1).getDistributionType().getId(), CoreProject.GetInstance().getDistributionTypes())));
			
			int newLiquidationId = daoManager.getDAOLiquidation().insert(liquidation);
			liquidation.setId(newLiquidationId);
			
			daoManager.commit();
			
		}catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}		
	}
	
	@Override
	public boolean existLiquidation(Date month) throws ServerException {
		DAOManager daoManager = new DAOManager();
		return daoManager.getDAOLiquidation().existLiquidation(month);
	}
		
	//Devuelve la cantidad total de las facturas realizadas en un periodo para un proyecto
	private double getBillsToLiquidateByProject(int projectId, Date from, Date to, User userContext) throws ServerException{		
		Double total = 0.0;
		ArrayList<Bill> bills = CoreBill.GetInstance().getBills(from, to, false, userContext);
		
		for (Bill bill : bills) {
			if(bill.getProject().getId() == projectId){
				total = total + bill.getTotalAmountPeso();
			}
		}						
		return total;
	}
	
	//Devuelve la cantidad total del costo de horas de los empleados asociados al proyecto
	private double getCostHoursByProject(int projectId) throws ServerException, ClientException{
		DAOManager daoManager = new DAOManager();
		double total = 0.0;
		ArrayList<ProjectEmployed> projectEmployees = CoreProject.GetInstance().getProjectEmployees(projectId);		
		
		if(projectEmployees != null && projectEmployees.size() > 0){
			for (ProjectEmployed projectEmployed : projectEmployees) {	
				Employed emp = projectEmployed.getEmployed();				
				emp.setIDAOSalarySummaries(daoManager.getDAOSalarySummaries());
				Double realCost = emp.getLatestVersionSalarySummary().getCostRealHour();				
				total = total + (realCost * projectEmployed.getHours());			
			}
		}
		
		return total;
	}
	
	//Devuelve la cantidad total de los rubros humanos asociados al proyecto
	private double getCostCategoriesHumanByProject(int projectId, Date from, Date to, User userContext) throws ServerException{		
		double total = 0.0;
		ArrayList<Category> categories = CoreCategory.GetInstance().getCategories(from, to, userContext);
		
		if(categories != null && categories.size() > 0){
			for (Category category : categories) {				
				if(category.getProject() != null && category.getProject().getId() == projectId && category.getIsRRHH()){
					total = total + category.getTotalAmountPeso();
				}			
			}
		}
	
		return total;
	}
	
	//Devuelve la cantidad total de los rubros materiales asociados al proyecto
	private double getCostCategoriesMaterialByProject(int projectId, Date from, Date to, User userContext) throws ServerException{
		double total = 0.0;
		ArrayList<Category> categories = CoreCategory.GetInstance().getCategories(from, to, userContext);
		
		if(categories != null && categories.size() > 0){
			for (Category category : categories) {
				if(category.getProject() != null && category.getProject().getId() == projectId && !category.getIsRRHH()){
					total = total + category.getTotalAmountPeso();
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
}
