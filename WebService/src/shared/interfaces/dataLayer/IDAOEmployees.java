package shared.interfaces.dataLayer;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.ProjectEmployed;
import shared.exceptions.ServerException;

public interface IDAOEmployees extends IDAOBase<Employed> {

	ArrayList<Employed> getEmployedByType(int employedTypeId) throws ServerException;

	ArrayList<ProjectEmployed> getEmployedHours() throws ServerException;
	
	ArrayList<Employed> getEmployeesToDate(Date to) throws ServerException;

}
