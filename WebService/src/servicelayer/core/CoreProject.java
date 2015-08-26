package servicelayer.core;

import java.util.ArrayList;
import java.util.Date;

import datalayer.daos.DAOProjects;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.valueObject.VOProject;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreProject;
import shared.interfaces.dataLayer.IDAOProjects;

public class CoreProject implements ICoreProject {

	private static CoreProject instance = null;
    private static IDAOProjects iDAOProject;
    
	private CoreProject() throws ServerException
	{
		iDAOProject = new DAOProjects();
	}
	
	public static CoreProject GetInstance() throws ServerException
	{
		if(instance == null)
		{
			instance = new CoreProject();
		}
		return instance;
	}
	
	@Override
	public void insertProject(VOProject voProject) throws ServerException, ClientException {
		
		if(iDAOProject.getProjectUByUserName(voProject.getName()) == null)
		{
			Project project = new Project(voProject);
			project.setCreatedDateTimeUTC(new Date());
			project.setUpdatedDateTimeUTC(new Date());
			project.setEnabled(true);
			iDAOProject.insert(project);
		}
		else
			throw new ClientException("Ya existe un proyecto con ese nombre");
	}
	
	@Override
	public void deleteProject(int id) throws ServerException, ClientException {
		Project project = iDAOProject.getObject(id);
		if (project == null){
			throw new ClientException("No existe proyecto con ese id");
		} else {
			iDAOProject.delete(id);
		}
		
	}

	@Override
	public ArrayList<VOProject> getProjects() throws ServerException {
		ArrayList<Project> projects;
		ArrayList<VOProject> voProjects = null;

		projects = iDAOProject.getObjects();
		voProjects = new ArrayList<VOProject>(); 
		
		for(Project project: projects)
		{
			voProjects.add(BuildVOProject(project));
		}
		
		return voProjects;
	}
	
	VOProject BuildVOProject(Project project)
	{
		VOProject voProject = new VOProject();
		voProject.setId(project.getId());
		voProject.setName(project.getName());
		voProject.setCreatedDateTimeUTC(project.getCreatedDateTimeUTC());
		voProject.setUpdatedDateTimeUTC(project.getUpdatedDateTimeUTC());
		voProject.setEnabled(project.getEnabled());
		
		return voProject;
	}

	@Override
	public ArrayList<VOProject> getProjectByStatus(boolean projectStatus) throws ServerException
	{
		ArrayList<Project> projects;
		ArrayList<VOProject> voProjects = null;

		projects = iDAOProject.getProjectsByStatus(projectStatus);
		voProjects = new ArrayList<VOProject>(); 
		
		for(Project project: projects)
		{
			voProjects.add(BuildVOProject(project));
		}
		
		return voProjects;
	}
}
