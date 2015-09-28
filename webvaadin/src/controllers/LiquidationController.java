package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.CreateLiquidation;
import servicelayer.service.ServiceWebStub.GetProjectLiquidationsPreview;
import servicelayer.service.ServiceWebStub.GetProjectsLiquidations;
import servicelayer.service.ServiceWebStub.VOProjectLiquidation;
import utils.PopupWindow;
import entities.ProjectLiquidation;

public class LiquidationController {
	
	public static ProjectLiquidation getProjectsLiquidations(Date month, int projectId){
		ProjectLiquidation projectLiquidation = null;
		
		try{
			ServiceWebStub service = new ServiceWebStub();
			GetProjectLiquidationsPreview getProjectLiquidation = new GetProjectLiquidationsPreview();
			
			getProjectLiquidation.setMonth(month);
			getProjectLiquidation.setProjectId(projectId);
			
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

}
