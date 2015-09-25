package shared.interfaces.core;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.DistributionType;
import servicelayer.entity.businessEntity.ProjectEmployed;
import servicelayer.entity.businessEntity.ProjectPartner;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.User;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreProject {

	void insertProject(Project project,
			ArrayList<ProjectEmployed> employedProjects,
			ArrayList<ProjectPartner> partnerProjects) throws ServerException,
			ClientException;

	ArrayList<Project> getProjects() throws ServerException;

	void deleteProject(int id) throws ServerException, ClientException;

	Project getProject(int id) throws ServerException, ClientException;

	ArrayList<DistributionType> getDistributionTypes() throws ServerException;

	ArrayList<ProjectEmployed> getProjectEmployees(int id)
			throws ServerException, ClientException;

	ArrayList<ProjectPartner> getProjectPartners(int id)
			throws ServerException, ClientException;

	ArrayList<Project> getProjects(User userContext) throws ServerException;

	ArrayList<Project> getProjectsReadyToLiquidate(Date from, Date to, int projectId)
			throws ServerException;

	Project updateProject(Project project,
			ArrayList<ProjectEmployed> employedProjects, ArrayList<ProjectPartner> partnerProjects) throws ServerException,
			ClientException;

	ArrayList<Project> getProjectByStatus(User userContext,
			boolean projectStatus) throws ServerException;
}
