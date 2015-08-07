package shared.interfaces.dataLayer;

import servicelayer.entity.businessEntity.Project;
import shared.exceptions.ServerException;

public interface IDAOProjects extends IDAOBase<Project>{

	Project getProjectUByUserName(String name) throws ServerException;
}
