package shared.interfaces.core;

import java.util.ArrayList;
import java.util.Date;

import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.CompanyLiquidation;
import servicelayer.entity.businessEntity.User;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreCompanyLiquidation {
	
	void liquidationByCompany(Date month, User userContext, double typeExchange)throws ServerException, ClientException;
	CompanyLiquidation getCompanyLiqudationsByDate(Date month) throws ServerException, ClientException;
	boolean existLiquidation(Date month) throws ServerException, ClientException;
}
