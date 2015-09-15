package shared.interfaces.dataLayer;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.ProjectEmployed;
import shared.exceptions.ServerException;

public interface IDAOProjectEmployees {

	int insertEmployedProject(int projectId, ProjectEmployed employedProyect)
			throws ServerException;

	ArrayList<ProjectEmployed> getEmployeesProject(int projectId)
			throws ServerException;

	void update(int id, ProjectEmployed obj) throws ServerException;
}
