package servicelayer.core;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.DistributionType;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.ProjectEmployed;
import servicelayer.entity.businessEntity.ProjectPartner;
import servicelayer.entity.businessEntity.User;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreProject;
import datalayer.daos.DAOManager;

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
	public void insertProject(Project project,
			ArrayList<ProjectEmployed> employedProjects,
			ArrayList<ProjectPartner> partnerProjects) throws ServerException,
			ClientException {
		DAOManager daoManager = new DAOManager();
		try {
			if (daoManager.getDAOProjects().getProjectByName(
					project.getName()) == null) {
				project.setiDAOProjectEmployees(daoManager
						.getDAOEmployedProjects());
				project.setiDAOPartnerProject(daoManager
						.getDAOPartnerProjects());

				// Datos propios del proyecto
				project.setCreatedDateTimeUTC(new Date());
				project.setUpdatedDateTimeUTC(new Date());
				project.setClosed(false);
				int newProjectId = daoManager.getDAOProjects().insert(project);

				project.setId(newProjectId);

				// Todos los usuarios asociados al proyecto
				for (ProjectEmployed empProject : employedProjects) {
					empProject.setCreatedDateTimeUTC(new Date());
					empProject.setUpdatedDateTimeUTC(new Date());
					empProject.setEnabled(true);
					project.associateEmployed(empProject);
				}

				// La distribucion asociada al proyecto
				for (ProjectPartner partProject : partnerProjects) {
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
			Project project = daoManager.getDAOProjects().getObject(id);
			// Manager
			if(project.getManager() != null)
				project.setManager(CoreUser.GetInstance().getUser(project.getManager().getId()));
			// Seller
			project.setSeller(CoreEmployed.GetInstance().getEmployed(project.getSeller().getId()));
			
			return project;
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

	@Override
	public ArrayList<DistributionType> getDistributionTypes()
			throws ServerException {
		DAOManager daoManager = new DAOManager();
		try {
			return daoManager.getDAOProjects().getDistributionTypes();
		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}
	
	@Override
	public ArrayList<ProjectEmployed> getProjectEmployees (int id) throws ServerException, ClientException{
		DAOManager daoManager = new DAOManager();
		try {
			Project project = getProject(id);
			project.setiDAOProjectEmployees(daoManager.getDAOEmployedProjects());
			return project.getProjectEmployees();
		} catch (ServerException e) {
			throw e;
		} catch (ClientException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}
	
	@Override
	public ArrayList<ProjectPartner> getProjectPartners (int id) throws ServerException, ClientException {
		DAOManager daoManager = new DAOManager();
		try {
			Project project = getProject(id);
			project.setiDAOPartnerProject(daoManager.getDAOPartnerProjects());
			return project.getProjectPartner();			
		} catch (ServerException e) {
			throw e;
		} catch (ClientException e) {
			throw e;
		} finally {
			daoManager.close();
		}	
	}	

	@Override
	public ArrayList<Project> getProjects(User userContext)
			throws ServerException {
		
		DAOManager daoManager = new DAOManager();
		try {
			return daoManager.getDAOProjects().getProjects(userContext);
		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public Project updateProject(Project project,
			ArrayList<ProjectEmployed> employedProjects,
			ArrayList<ProjectPartner> partnerProjects) throws ServerException, ClientException {

		DAOManager daoManager = new DAOManager();
		try {
			/* DISTRIBUCION */
			ArrayList<ProjectPartner> projectPartnersOld = daoManager.getDAOPartnerProjects().getPartnersProject(project.getId());	
			// si se modifica la distribucion recorro el arreglo
			if(changedProjectPartner(projectPartnersOld, partnerProjects)){
				// Si la ultima modificación fue hoy, modifico la última version SiNo inserto una nueva versión
				if(changedProjectPartnerToday(projectPartnersOld)){
					for (ProjectPartner partner : partnerProjects) {
						partner.setEnabled(true);
						project.updateAssociateDistribution(partner);
					}
				} else {
					for (ProjectPartner partner : partnerProjects) {
						partner.setEnabled(true);
						project.associateDistribution(partner);
					}
				}
			}
			
			/* EMPLEADOS ASOCIADOS */
			for (ProjectEmployed empProject : employedProjects) {
				ProjectEmployed projectEmployedOld = daoManager.getDAOEmployedProjects().getEmployeed(project.getId(), empProject.getEmployed().getId());
				if(changedProjectEmployee(projectEmployedOld, empProject)){
					empProject.setUpdatedDateTimeUTC(new Date());
					if(changedProjectEmployeeToday(projectEmployedOld)){
						project.updateAssociateEmployed(empProject);
					} else {
						project.associateEmployed(empProject);						
					}					
				}
			}

			/* PROYECTO */
			daoManager.getDAOProjects().update(project.getId(), project);
			daoManager.commit();
			
			return getProject(project.getId());
			
		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}

	
	@Override
	public ArrayList<Project> getProjectsToLiquidate(Date from, Date to)
			throws ServerException {
		
		DAOManager daoManager = new DAOManager();
		try {
			return daoManager.getDAOProjects().getProjectToLiquidate(from, to);
		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}
	
	// Dados dos tipos de distribuciones, verifica si son iguales
	private boolean changedProjectPartner (ArrayList<ProjectPartner> projectPartnersOld, ArrayList<ProjectPartner> projectPartnersNew){
		boolean changed = false;
		for (ProjectPartner projectPartner : projectPartnersNew) {
			for (ProjectPartner projectPartnerOld : projectPartnersOld) {
				if(projectPartnerOld.getEmployed().getId() == projectPartner.getEmployed().getId() &&
						!projectPartnerOld.getDistributionType().equals(projectPartner.getDistributionType())){
					changed = true;
				}
			}
		}
		return changed;
	}
	
	// Dados dos tipos de distribuciones, verifica si se modificaron hoy
	private boolean changedProjectPartnerToday (ArrayList<ProjectPartner> projectPartnersOld){
		boolean atSameDay = false;
		for (ProjectPartner projectPartnerOld : projectPartnersOld) {
			if(projectPartnerOld.getUpdatedDateTimeUTC() != null && 
					DateFormat.getInstance().format(projectPartnerOld).equals(DateFormat.getInstance().format(new Date()))){
				atSameDay = true;
			}
		}
		return atSameDay;
	}
	
	// Dados dos projectEmployees verifico si son iguales
	private boolean changedProjectEmployee (ProjectEmployed projectEmployedOld, ProjectEmployed projectEmployedNew){
		return (projectEmployedOld.getHours() != projectEmployedNew.getHours() || projectEmployedOld.isEnabled() != projectEmployedNew.isEnabled());
	}
	
	// Dados dos projectEmployees, verifica si se modificaron hoy
	private boolean changedProjectEmployeeToday (ProjectEmployed projectEmployedOld){
		return (projectEmployedOld.getUpdatedDateTimeUTC() != null && 
				DateFormat.getInstance().format(projectEmployedOld).equals(DateFormat.getInstance().format(new Date())));
	}
}
