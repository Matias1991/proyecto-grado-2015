package shared.interfaces.dataLayer;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.EmployedProject;
import shared.exceptions.ServerException;

public interface IDAOEmployedProject {
	
	int insertEmployedProject(int projectId, EmployedProject employedProyect) throws ServerException;
	
	ArrayList<EmployedProject> getEmployeesProject(int projectId) throws ServerException;
}
