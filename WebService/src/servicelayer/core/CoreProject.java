package servicelayer.core;

import java.util.ArrayList;
import java.util.Date;
import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.EmployedProject;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.valueObject.VOEmployedProject;
import servicelayer.entity.valueObject.VOProject;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreProject;

public class CoreProject implements ICoreProject {

	private static CoreProject instance = null;

	private CoreProject() {
	}

	public static CoreProject GetInstance() throws ServerException {
		if (instance == null) {
			instance = new CoreProject();
		}
		return instance;
	}

	@Override
	public void insertProject(VOProject voProject) throws ServerException,
			ClientException {
		DAOManager daoManager = new DAOManager();
		try {
			if (daoManager.getDAOProjects().getProjectUByUserName(
					voProject.getName()) == null) {
				// Datos propios del proyecto
				Project project = new Project(voProject,
						daoManager.getDAOEmployedProjects());
				project.setCreatedDateTimeUTC(new Date());
				project.setUpdatedDateTimeUTC(new Date());
				project.setEnabled(true);
				int newProjectId = daoManager.getDAOProjects().insert(project);

				project.setId(newProjectId);

				// Todos los usuarios asociados al proyecto
				for (VOEmployedProject voEmployedProject : voProject
						.getVoEmployedProjects()) {
					EmployedProject employedProject = new EmployedProject(
							voEmployedProject);
					employedProject.setCreatedDateTimeUTC(new Date());
					employedProject.setUpdatedDateTimeUTC(new Date());
					employedProject.setEnabled(true);
					project.associateEmployed(employedProject);
				}

				// La distribucion asociada al proyecto

			} else
				throw new ClientException(
						"Ya existe un proyecto con ese nombre");

			daoManager.commit();

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public void deleteProject(int id) throws ServerException, ClientException {
		DAOManager daoManager = new DAOManager();
		try {
			Project project = daoManager.getDAOProjects().getObject(id);
			if (project == null) {
				throw new ClientException("No existe proyecto con ese id");
			} else {
				daoManager.getDAOProjects().delete(id);
				daoManager.commit();
			}

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public ArrayList<VOProject> getProjects() throws ServerException {
		DAOManager daoManager = new DAOManager();
		try {
			ArrayList<Project> projects;
			ArrayList<VOProject> voProjects = null;

			projects = daoManager.getDAOProjects().getObjects();
			voProjects = new ArrayList<VOProject>();

			for (Project project : projects) {
				voProjects.add(BuildVOProject(project));
			}

			return voProjects;
		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}

	VOProject BuildVOProject(Project project) {
		VOProject voProject = new VOProject();
		voProject.setId(project.getId());
		voProject.setName(project.getName());
		voProject.setCreatedDateTimeUTC(project.getCreatedDateTimeUTC());
		voProject.setUpdatedDateTimeUTC(project.getUpdatedDateTimeUTC());
		voProject.setEnabled(project.getEnabled());

		return voProject;
	}

	@Override
	public ArrayList<VOProject> getProjectByStatus(boolean projectStatus)
			throws ServerException {
		DAOManager daoManager = new DAOManager();
		try {
			ArrayList<Project> projects;
			ArrayList<VOProject> voProjects = null;

			projects = daoManager.getDAOProjects().getProjectsByStatus(
					projectStatus);
			voProjects = new ArrayList<VOProject>();

			for (Project project : projects) {
				voProjects.add(BuildVOProject(project));
			}

			return voProjects;
		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}
}
