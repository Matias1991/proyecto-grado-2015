package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.DeleteProject;
import servicelayer.service.ServiceWebStub.GetProjects;
import servicelayer.service.ServiceWebStub.GetProjectsByStatus;
import servicelayer.service.ServiceWebStub.VOProject;
import utils.PopupWindow;
import entities.Project;

public class ProjectController {

	public static Collection<Project> getProjects()
	{
		Collection<Project> projects = new ArrayList<Project>();
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			servicelayer.service.ServiceWebStub.GetProjects getProjects = new servicelayer.service.ServiceWebStub.GetProjects();
			
			VOProject [] voProjects = service.getProjects(getProjects).get_return();
			
			if(voProjects != null){
				for(VOProject voProject : voProjects)
				{
					Project project = new Project(voProject);
					projects.add(project);
				}				
			}
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return projects;
	}
	
	public static boolean deleteProject(int id)
	{
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			DeleteProject deletedProject = new DeleteProject();
			
			deletedProject.setId(id);
			
			result = service.deleteProject(deletedProject).get_return();
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	public static Collection<Project> GetProjectsByStatus(boolean status){
		Collection <Project> projects = new ArrayList<Project>();
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetProjectsByStatus getProjects = new GetProjectsByStatus();
			getProjects.setProjectStatus(status);
			
			VOProject [] voProjects = service.getProjectsByStatus(getProjects).get_return();
			
			if(voProjects != null){
				for(VOProject voProject : voProjects)
				{
					Project project = new Project(voProject);
					projects.add(project);
				}				
			}
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return projects;
	}
}
