package shared.interfaces.dataLayer;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.DistributionType;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.User;
import shared.exceptions.ServerException;

public interface IDAOProjects extends IDAOBase<Project> {

	Project getProjectByName(String name) throws ServerException;

	int insert(Project project) throws ServerException;

	ArrayList<Project> getProjectsByStatus(boolean projectStatus)
			throws ServerException;

	ArrayList<DistributionType> getDistributionTypes() throws ServerException;

	ArrayList<Project> getProjects(User userContext) throws ServerException;

	ArrayList<Project> getProjectToLiquidate(Date from, Date to) throws ServerException;
}
