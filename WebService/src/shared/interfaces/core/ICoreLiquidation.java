package shared.interfaces.core;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.User;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreLiquidation {
	
	void liquidationByCompany(ArrayList<Project> projects, Date month, User userContext)throws ServerException, ClientException;
	boolean existLiquidation(Date month)throws ServerException;
	

}
