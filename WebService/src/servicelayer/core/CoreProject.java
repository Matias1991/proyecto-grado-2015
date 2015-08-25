package servicelayer.core;

import java.util.ArrayList;
import java.util.Date;

import datalayer.daos.DAOProjects;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.valueObject.VOProject;
import servicelayer.entity.valueObject.VOUser;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreProject;
import shared.interfaces.dataLayer.IDAOProjects;

public class CoreProject implements ICoreProject {

	private static CoreProject instance = null;
    private static IDAOProjects iDAOProjects;
    
	private CoreProject() throws ServerException
	{
		iDAOProjects = new DAOProjects();
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
		
		if(iDAOProjects.getProjectUByUserName(voProject.getName()) == null)
		{
			Project project = new Project(voProject);
			project.setCreatedDateTimeUTC(new Date());
			project.setUpdatedDateTimeUTC(new Date());
			project.setEnabled(true);
			iDAOProjects.insert(project);
			
			//ACA VA LA LOGICA PARA INSERTAR EN PARTNERPROJECT Y EMPLOYEDPROJECT
		}
		else
			throw new ClientException("Ya existe un proyecto con ese nombre");
	}

	@Override
	public ArrayList<VOProject> getProjects() throws ServerException {
		ArrayList<Project> projects;
		ArrayList<VOProject> voProjects = null;

		projects = iDAOProjects.getObjects();
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

}
