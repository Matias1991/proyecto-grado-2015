package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.GetDistributionTypes;
import servicelayer.service.ServiceWebStub.GetProject;
import servicelayer.service.ServiceWebStub.InsertProject;
import servicelayer.service.ServiceWebStub.VODistributionType;
import servicelayer.service.ServiceWebStub.DeleteProject;
import servicelayer.service.ServiceWebStub.GetProjectsByStatus;
import servicelayer.service.ServiceWebStub.VOProject;
import servicelayer.service.ServiceWebStub.VOProjectEmployed;
import servicelayer.service.ServiceWebStub.VOProjectPartner;
import utils.PopupWindow;
import entities.DistributionType;
import entities.ProjectEmployed;
import entities.Project;
import entities.ProjectPartner;

public class ProjectController {

	public static Collection<Project> getProjects() {
		Collection<Project> projects = new ArrayList<Project>();

		try {
			ServiceWebStub service = new ServiceWebStub();
			servicelayer.service.ServiceWebStub.GetProjects getProjects = new servicelayer.service.ServiceWebStub.GetProjects();

			VOProject[] voProjects = service.getProjects(getProjects)
					.get_return();

			if (voProjects != null) {
				for (VOProject voProject : voProjects) {
					Project project = new Project(voProject);
					projects.add(project);
				}
			}

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace(
					"</faultstring>", ""));
		} catch (RemoteException e) {			
			e.printStackTrace();
		}

		return projects;
	}
	
	public static Collection<Project> getActiveProjects() {
		Collection<Project> projects = new ArrayList<Project>();

		try {
			ServiceWebStub service = new ServiceWebStub();
			servicelayer.service.ServiceWebStub.GetProjects getActiveProjects = new servicelayer.service.ServiceWebStub.GetProjects();

			VOProject[] voProjects = service.getProjects(getActiveProjects)
					.get_return();

			if (voProjects != null) {
				for (VOProject voProject : voProjects) {
					if(voProject.getClosed())
						continue;
					
					Project project = new Project(voProject);
					projects.add(project);
				}
			}

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace(
					"</faultstring>", ""));
		} catch (RemoteException e) {			
			e.printStackTrace();
		}

		return projects;
	}

	public static boolean createProject(Project project) {
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			InsertProject createProject = new InsertProject();

			// Project
			VOProject voProject = new VOProject();
			voProject.setName(project.getName());
			voProject.setDescription(project.getDescription());
			voProject.setAmount(project.getAmount());
			voProject.setIsCurrencyDollar(project.getIsCurrencyDollar());
			voProject.setSellerId(project.getSeller().getId());
			if(project.getManager() != null){
				voProject.setManagerId(project.getManager().getId());
			}

			// ProjectEmployed
			VOProjectEmployed[] voProjectEmployees = new VOProjectEmployed[project
					.getEmployedHours().size()];

			int i = 0;
			for (ProjectEmployed aux : project.getEmployedHours()) {
				VOProjectEmployed voEmployedProject = new VOProjectEmployed();
				voEmployedProject.setEmployedId(aux.getEmployedId());
				voEmployedProject.setHours(aux.getEmployedHours());

				voProjectEmployees[i] = voEmployedProject;
				i++;
			}
			voProject.setVoEmployedProjects(voProjectEmployees);

			// ProjectPartner
			VOProjectPartner[] voProjectPartners = new VOProjectPartner[project
					.getProjectPartners().size()];

			i = 0;
			for (ProjectPartner aux : project.getProjectPartners()) {
				VOProjectPartner voProjectPartner = new VOProjectPartner();
				VODistributionType voDistributionType = new VODistributionType();
				voDistributionType.setId(aux.getDistributionType().getId());
				voProjectPartner.setEmployedId(aux.getEmployedId());
				voProjectPartner.setDistributionType(voDistributionType);

				voProjectPartners[i] = voProjectPartner;
				i++;
			}
			voProject.setVoPartnerProjects(voProjectPartners);

			createProject.setVoProject(voProject);
			result = service.insertProject(createProject).get_return();

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace(
					"</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static boolean deleteProject(int id) {
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			DeleteProject deletedProject = new DeleteProject();

			deletedProject.setId(id);

			result = service.deleteProject(deletedProject).get_return();

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace(
					"</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static Project getProject(int projectId) {
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

	public static Collection<Project> getProjectsByStatus(boolean status) {
		Collection<Project> projects = new ArrayList<Project>();

		try {
			ServiceWebStub service = new ServiceWebStub();
			GetProjectsByStatus getProjects = new GetProjectsByStatus();
			getProjects.setProjectStatus(status);

			VOProject[] voProjects = service.getProjectsByStatus(getProjects)
					.get_return();

			if (voProjects != null) {
				for (VOProject voProject : voProjects) {
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

	public static Collection<DistributionType> getDistributionTypes() {
		Collection<DistributionType> distributions = new ArrayList<DistributionType>();

		try {
			ServiceWebStub service = new ServiceWebStub();
			GetDistributionTypes getDistributions = new GetDistributionTypes();

			VODistributionType[] voDistributions = service
					.getDistributionTypes(getDistributions).get_return();

			if (voDistributions != null) {
				for (VODistributionType voDistribution : voDistributions) {
					DistributionType distribution = new DistributionType(
							voDistribution);
					distributions.add(distribution);
				}
			}

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace(
					"</faultstring>", ""));
		} catch (RemoteException e) {			
			e.printStackTrace();
		}

		return distributions;
	}

}
