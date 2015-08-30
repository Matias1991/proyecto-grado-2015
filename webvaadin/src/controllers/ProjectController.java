package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebCallbackHandler;
import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.GetDistributionTypes;
import servicelayer.service.ServiceWebStub.GetProject;
import servicelayer.service.ServiceWebStub.InsertProject;
import servicelayer.service.ServiceWebStub.VODistributionType;
import servicelayer.service.ServiceWebStub.VOEmployed;
import servicelayer.service.ServiceWebStub.DeleteProject;
import servicelayer.service.ServiceWebStub.GetProjects;
import servicelayer.service.ServiceWebStub.GetProjectsByStatus;
import servicelayer.service.ServiceWebStub.VOProject;
import servicelayer.service.ServiceWebStub.VOProjectEmployed;
import utils.PopupWindow;
import entities.DistributionType;
import entities.EmployedHours;
import entities.ProjectEmployed;
import entities.Employee;
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
	
	public static boolean createProject(Project project, List<ProjectEmployed> employedHours){
		boolean result = false;
		try{
			ServiceWebStub service = new ServiceWebStub();
			InsertProject createProject = new InsertProject();
			
			VOProject voProject = new VOProject();
			voProject.setName(project.getName());
			voProject.setDescription(project.getDescription());
//			voProject.setSeller(project.getSeller().toVOEmployee());
//			voProject.setManager(project.getManager().toVOUser());
			
			createProject.setVoProject(voProject);
			
			List<VOProjectEmployed> listVOEmployedProject = new ArrayList<VOProjectEmployed>();
			//VOEmployedProject[] listVOEmployedProject = new VOEmployedProject[employedHours.size()];
						
			for (ProjectEmployed aux : employedHours) {				
				VOProjectEmployed voEmployedProject = new VOProjectEmployed();
				VOEmployed voEmployed = new VOEmployed();
				
				voEmployed.setId(aux.getEmployedId());
//				voEmployedProject.setEmployed(voEmployed);
//				voEmployedProject.setHours(aux.getHours());
//				voEmployedProject.setProject(voProject);
				
				listVOEmployedProject.add(voEmployedProject);	
				
			}				
			
			VOProjectEmployed[] listToInsert = new VOProjectEmployed[employedHours.size()];
			
			for(int i = 0; i<employedHours.size();i++){
				listToInsert[i] = listVOEmployedProject.get(i);
			}
			
//			createProject.setListVOEmployedProject(listToInsert);
			//createProject.setListVOEmployedProject(listVOEmployedProject);
			
			
			result = service.insertProject(createProject).get_return();
			
		}catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
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

	public static Project getProject (int projectId){
		Project project = null;
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetProject getProject = new GetProject();
			getProject.setId(projectId);
			
			VOProject voProject = service.getProject(getProject).get_return();
			project = new Project(voProject);
			
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return project;
	}

	public static Collection<Project> getProjectsByStatus(boolean status){
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
	
	public static Collection<DistributionType> getDistributionTypes(){
		Collection<DistributionType> distributions = new ArrayList<DistributionType>();
		
		try{
			ServiceWebStub service = new ServiceWebStub();
			GetDistributionTypes getDistributions = new GetDistributionTypes();
			
			VODistributionType[] voDistributions = service.getDistributionTypes(getDistributions).get_return();
			
			if(voDistributions != null){
				for(VODistributionType voDistribution : voDistributions)
				{
					DistributionType distribution = new DistributionType(voDistribution);
					distributions.add(distribution);
				}				
			}
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return distributions;		
	}
}
