package shared.interfaces.dataLayer;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.EmployedProject;
import shared.exceptions.ServerException;

public interface IDAOEmployees extends IDAOBase<Employed> {

	int getCountPartners() throws ServerException;

	ArrayList<EmployedProject> getEmployedHours() throws ServerException;

}
