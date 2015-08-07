package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
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
}
