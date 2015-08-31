package shared.interfaces.dataLayer;

import java.util.ArrayList;
import servicelayer.entity.businessEntity.Project;
import shared.exceptions.ServerException;

public interface IDAOProjects extends IDAOBase<Project> {

	Project getProjectByName(String name) throws ServerException;

	int insert(Project project) throws ServerException;

	ArrayList<Project> getProjectsByStatus(boolean projectStatus)
			throws ServerException;
}
