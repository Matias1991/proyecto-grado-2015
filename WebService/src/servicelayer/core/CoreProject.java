package servicelayer.core;

import java.util.ArrayList;
import java.util.Date;

import datalayer.daos.DAOManager;
import datalayer.daos.DAOProjects;
import servicelayer.entity.businessEntity.EmployedProject;
import servicelayer.entity.businessEntity.PartnerProject;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.valueObject.VOEmployedProject;
import servicelayer.entity.valueObject.VOPartnerProject;
import servicelayer.entity.valueObject.VOProject;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreProject;
import shared.interfaces.dataLayer.IDAOProjects;
import datalayer.daos.DAOManager;

public class CoreProject implements ICoreProject {

	private static CoreProject instance = null;
	private IDAOProjects iDAOProject;

	private CoreProject()throws ServerException {
		iDAOProject = new DAOProjects();
	}

	public static CoreProject GetInstance() throws ServerException {
		if (instance == null) {
			instance = new CoreProject();
		}
		return instance;
	}

	@Override
	public void insertProject(Project project, ArrayList<EmployedProject> employedProjects,
			ArrayList<PartnerProject> partnerProjects) throws ServerException,
			ClientException {
		DAOManager daoManager = new DAOManager();
		try {
			if (daoManager.getDAOProjects().getProjectUByUserName(
					project.getName()) == null) {
				// Datos propios del proyecto
				project.setCreatedDateTimeUTC(new Date());
				project.setUpdatedDateTimeUTC(new Date());
				project.setEnabled(true);
				int newProjectId = daoManager.getDAOProjects().insert(project);

				project.setId(newProjectId);

				// Todos los usuarios asociados al proyecto
				for (EmployedProject empProject : employedProjects) {
					empProject.setCreatedDateTimeUTC(new Date());
					empProject.setUpdatedDateTimeUTC(new Date());
					empProject.setEnabled(true);
					project.associateEmployed(empProject);					
				}
				
				// La distribucion asociada al proyecto
				for (PartnerProject partProject : partnerProjects) {
					partProject.setCreatedDateTimeUTC(new Date());
					partProject.setUpdatedDateTimeUTC(new Date());
					partProject.setEnabled(true);
					project.associateDistribution(partProject);					
				}
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
	public Project getProject(int id) throws ServerException, ClientException {
		DAOManager daoManager = new DAOManager();
		try {
			return daoManager.getDAOProjects().getObject(id);

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public ArrayList<Project> getProjects() throws ServerException {
		DAOManager daoManager = new DAOManager();
		try {
			return daoManager.getDAOProjects().getObjects();

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public ArrayList<Project> getProjectByStatus(boolean projectStatus)
			throws ServerException {
		DAOManager daoManager = new DAOManager();
		try {
			return daoManager.getDAOProjects().getProjectsByStatus(
					projectStatus);

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}
}
