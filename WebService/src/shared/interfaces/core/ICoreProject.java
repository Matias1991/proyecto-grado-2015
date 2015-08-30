package shared.interfaces.core;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.DistributionType;
import servicelayer.entity.businessEntity.EmployedProject;
import servicelayer.entity.businessEntity.PartnerProject;
import servicelayer.entity.businessEntity.Project;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreProject {

	void insertProject(Project project, ArrayList<EmployedProject> employedProjects,
			ArrayList<PartnerProject> partnerProjects) throws ServerException,
			ClientException;

	ArrayList<Project> getProjects() throws ServerException;

	void deleteProject(int id) throws ServerException, ClientException;

	ArrayList<Project> getProjectByStatus(boolean projectStatus)
			throws ServerException;

	Project getProject(int id) throws ServerException, ClientException;
	
	ArrayList<DistributionType> getDistributionTypes() throws ServerException;
}
