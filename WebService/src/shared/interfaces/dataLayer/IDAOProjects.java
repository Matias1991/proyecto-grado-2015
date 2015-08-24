package shared.interfaces.dataLayer;

import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.valueObject.VOProject;
import shared.exceptions.ServerException;

public interface IDAOProjects extends IDAOBase<Project>{

	Project getProjectUByUserName(String name) throws ServerException;
	
	int insert(Project project) throws ServerException;
}
