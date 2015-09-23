package shared.interfaces.core;

import java.util.Date;

import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.User;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreLiquidation {
	
	void liquidationByCompany(Date month, User userContext, double typeExchange)throws ServerException, ClientException;
	boolean existLiquidation(Date month, DAOManager daoManager)throws ServerException;
	

}
