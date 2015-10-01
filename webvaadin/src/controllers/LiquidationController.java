package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.CreateLiquidation;
import servicelayer.service.ServiceWebStub.ExistLiquidation;
import servicelayer.service.ServiceWebStub.GetCompanyLiquidation;
import servicelayer.service.ServiceWebStub.GetCompanyLiquidationPreview;
import servicelayer.service.ServiceWebStub.GetCompanyLiquidations;
import servicelayer.service.ServiceWebStub.GetProjectLiquidation;
import servicelayer.service.ServiceWebStub.GetProjectLiquidations;
import servicelayer.service.ServiceWebStub.GetProjectLiquidationsPreview;
import servicelayer.service.ServiceWebStub.GetProjectsLiquidations;
import servicelayer.service.ServiceWebStub.GetProjectsLiquidationsWithMoreEarnings;
import servicelayer.service.ServiceWebStub.GetTypeExchange;
import servicelayer.service.ServiceWebStub.VOCompanyLiquidation;
import servicelayer.service.ServiceWebStub.VOProjectLiquidation;
import utils.PopupWindow;
import entities.CompanyLiquidation;
import entities.ProjectLiquidation;

public class LiquidationController {
	
	public static ProjectLiquidation getProjectsLiquidations(Date month, int projectId, double typeExchange){
		ProjectLiquidation projectLiquidation = null;
		
		try{
			ServiceWebStub service = new ServiceWebStub();
			GetProjectLiquidationsPreview getProjectLiquidation = new GetProjectLiquidationsPreview();
			
			getProjectLiquidation.setMonth(month);
			getProjectLiquidation.setProjectId(projectId);
			getProjectLiquidation.setTypeExchange(typeExchange);
			
			projectLiquidation = new ProjectLiquidation(service.getProjectLiquidationsPreview(getProjectLiquidation).get_return());
			
		}catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return projectLiquidation;		
	}
	
	public static boolean createLiquidation(Date month, double typeExchange, int userContextId){
		boolean result = false;
		
		try{
			ServiceWebStub service = new ServiceWebStub();
			CreateLiquidation createLiquidation = new CreateLiquidation();
			
			createLiquidation.setMonth(month);
			createLiquidation.setTypeExchange(typeExchange);
			createLiquidation.setUserContextId(userContextId);
			
			result = service.createLiquidation(createLiquidation).get_return();			
			
		}catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;		
	}
	
	public static Collection<ProjectLiquidation> getProjectsLiquidations(Date month){
		Collection<ProjectLiquidation> projectsLiquidations = new ArrayList<ProjectLiquidation>();
		
		try{
			ServiceWebStub service = new ServiceWebStub();
			GetProjectsLiquidations getProjectsLiquidations = new GetProjectsLiquidations();
			
			getProjectsLiquidations.setMonth(month);
			
			VOProjectLiquidation[] voProjectsLiquidations = service.getProjectsLiquidations(getProjectsLiquidations).get_return();
			
			for(VOProjectLiquidation voProjectLiquidation : voProjectsLiquidations){
				ProjectLiquidation projectLiquidation = new ProjectLiquidation(voProjectLiquidation);
				projectsLiquidations.add(projectLiquidation);
			}
			
		}catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return projectsLiquidations;		
	}
	
	public static CompanyLiquidation getCompanyLiquidationPreview (Date month, double typeExchange, int userContextId){
		CompanyLiquidation companyLiquidation = null;
			
		try{
			ServiceWebStub service = new ServiceWebStub();
			GetCompanyLiquidationPreview companyLiquidationPreview = new GetCompanyLiquidationPreview();
			
			companyLiquidationPreview.setMonth(month);
			companyLiquidationPreview.setTypeExchange(typeExchange);
			companyLiquidationPreview.setUserContextId(userContextId);
			
			companyLiquidation = new CompanyLiquidation(service.getCompanyLiquidationPreview(companyLiquidationPreview).get_return());			
			
		}catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return companyLiquidation;		
		
	}
	
	public static boolean existLiquidation(Date month){
		boolean result = false;
		try{
			ServiceWebStub service = new ServiceWebStub();
			ExistLiquidation existLiquidation = new ExistLiquidation();
			
			existLiquidation.setMonth(month);
			
			result = service.existLiquidation(existLiquidation).get_return();
			
		}catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;		
	}

	public static Collection<ProjectLiquidation> getProjectsLiquidationsWithMoreEarnings(Date from, Date to, boolean isCurrencyDollar, int count){
		Collection<ProjectLiquidation> projectsLiquidations = new ArrayList<ProjectLiquidation>();
		
		try{
			ServiceWebStub service = new ServiceWebStub();
			GetProjectsLiquidationsWithMoreEarnings getProjectsLiquidationsWithMoreEarnings = new GetProjectsLiquidationsWithMoreEarnings();
			
			getProjectsLiquidationsWithMoreEarnings.setFrom(from);
			getProjectsLiquidationsWithMoreEarnings.setTo(to);
			getProjectsLiquidationsWithMoreEarnings.setIsCurrencyDollar(isCurrencyDollar);
			getProjectsLiquidationsWithMoreEarnings.setCount(count);
			
			VOProjectLiquidation[] voProjectsLiquidations = service.getProjectsLiquidationsWithMoreEarnings(getProjectsLiquidationsWithMoreEarnings).get_return();
			
			if(voProjectsLiquidations != null && voProjectsLiquidations.length > 0)
			{
				for(VOProjectLiquidation voProjectLiquidation : voProjectsLiquidations){
					ProjectLiquidation projectLiquidation = new ProjectLiquidation(voProjectLiquidation.getProject().getId(), voProjectLiquidation.getProject().getName(), voProjectLiquidation.getEarnings(), voProjectLiquidation.getReserve());
					projectsLiquidations.add(projectLiquidation);
				}
			}
			
		}catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return projectsLiquidations;		
	}
	
	public static Collection<ProjectLiquidation> getProjectLiquidations(int projectId, Date date, boolean isCurrencyDollar){
		Collection<ProjectLiquidation> projectLiquidations = new ArrayList<ProjectLiquidation>();
		
		try{
			ServiceWebStub service = new ServiceWebStub();
			GetProjectLiquidations getProjectLiquidations = new GetProjectLiquidations();
			
			getProjectLiquidations.setProjectId(projectId);
			getProjectLiquidations.setDate(date);
			getProjectLiquidations.setIsCurrencyDollar(isCurrencyDollar);
			
			VOProjectLiquidation[] voProjectsLiquidations = service.getProjectLiquidations(getProjectLiquidations).get_return();
			
			if(voProjectsLiquidations != null && voProjectsLiquidations.length > 0)
			{
				for(VOProjectLiquidation voProjectLiquidation : voProjectsLiquidations){
					ProjectLiquidation projectLiquidation = new ProjectLiquidation(voProjectLiquidation.getProject().getId(), voProjectLiquidation.getProject().getName(), voProjectLiquidation.getEarnings(), voProjectLiquidation.getReserve(), voProjectLiquidation.getTotalCostEmployees(), voProjectLiquidation.getTotalCostCategoriesHuman(), voProjectLiquidation.getTotalCostCategoriesMaterial(), voProjectLiquidation.getAppliedDateTimeUTC());
					projectLiquidations.add(projectLiquidation);
				}
			}
			
		}catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return projectLiquidations;		
	}
	
	public static Collection<CompanyLiquidation> getCompanyLiquidations(Date date){
		Collection<CompanyLiquidation> companyLiquidations = new ArrayList<CompanyLiquidation>();
		
		try{
			ServiceWebStub service = new ServiceWebStub();
			GetCompanyLiquidations getCompanyLiquidations = new GetCompanyLiquidations();

			getCompanyLiquidations.setDate(date);
			
			VOCompanyLiquidation[] voCompanyLiquidations = service.getCompanyLiquidations(getCompanyLiquidations).get_return();
			
			if(voCompanyLiquidations != null && voCompanyLiquidations.length > 0)
			{
				for(VOCompanyLiquidation voCompanyLiquidation : voCompanyLiquidations){
					CompanyLiquidation companyLiquidation = new CompanyLiquidation(voCompanyLiquidation.getIVAPurchase(), voCompanyLiquidation.getIVASale(), voCompanyLiquidation.getAppliedDateTimeUTC());
					companyLiquidations.add(companyLiquidation);
				}
			}
			
		}catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return companyLiquidations;		
	}
	
	public static CompanyLiquidation getCompanyLiquidation(Date month, int userContextId){
		CompanyLiquidation companyLiquidation = null;
		
		try{
			ServiceWebStub service = new ServiceWebStub();
			GetCompanyLiquidation getCompanyLiquidation = new GetCompanyLiquidation();
			
			getCompanyLiquidation.setMonth(month);
			getCompanyLiquidation.setUserContextId(userContextId);
			
			companyLiquidation = new CompanyLiquidation(service.getCompanyLiquidation(getCompanyLiquidation).get_return());			
			
		}catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return companyLiquidation;			
	}
	
	public static ProjectLiquidation getProjectLiquidation(Date month, int projectId, int userContextId){
		ProjectLiquidation projectLiquidation = null;
		
		try{
			ServiceWebStub service = new ServiceWebStub();
			GetProjectLiquidation getProjectLiquidation = new GetProjectLiquidation();
			
			getProjectLiquidation.setMonth(month);
			getProjectLiquidation.setProjectId(projectId);
			getProjectLiquidation.setUserContextId(userContextId);
			
			projectLiquidation = new ProjectLiquidation(service.getProjectLiquidation(getProjectLiquidation).get_return());
			
		}catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
		return projectLiquidation;		
	}
	
	public static double getTypeExchange(Date month){
		double typeExchange = 0.0;
		
		try{
			ServiceWebStub service = new ServiceWebStub();
			GetTypeExchange getTypeExchange = new GetTypeExchange();
			
			getTypeExchange.setMonth(month);
			
			typeExchange = service.getTypeExchange(getTypeExchange).get_return();
			
		}catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}		
		return typeExchange;		
	}
}
