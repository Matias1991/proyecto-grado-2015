package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.InsertProject;
import servicelayer.service.ServiceWebStub.VOEmployed;
import servicelayer.service.ServiceWebStub.VOEmployedProject;
import servicelayer.service.ServiceWebStub.VOProject;
import utils.PopupWindow;
import entities.EmployedHours;
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
	
	public static boolean createProject(Project project, List<EmployedHours> employedHours){
		boolean result = false;
		try{
			ServiceWebStub service = new ServiceWebStub();
			InsertProject createProject = new InsertProject();
			
			VOProject voProject = new VOProject();
			voProject.setName(project.getName());
			voProject.setDescription(project.getDescription());
			voProject.setSeller(project.getSeller().toVOEmployee());
			voProject.setManager(project.getManager().toVOUser());
			
			createProject.setVoProject(voProject);
			
			List<VOEmployedProject> listVOEmployedProject = new ArrayList<VOEmployedProject>();
			//VOEmployedProject[] listVOEmployedProject = new VOEmployedProject[employedHours.size()];
						
			for (EmployedHours aux : employedHours) {				
				VOEmployedProject voEmployedProject = new VOEmployedProject();
				VOEmployed voEmployed = new VOEmployed();
				
				voEmployed.setId(aux.getId());
				voEmployedProject.setEmployed(voEmployed);
				voEmployedProject.setHours(aux.getHours());
				voEmployedProject.setProject(voProject);
				
				listVOEmployedProject.add(voEmployedProject);	
				
			}				
			
			VOEmployedProject[] listToInsert = new VOEmployedProject[employedHours.size()];
			
			for(int i = 0; i<employedHours.size();i++){
				listToInsert[i] = listVOEmployedProject.get(i);
			}
			
			createProject.setListVOEmployedProject(listToInsert);
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
}
