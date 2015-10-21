package shared.interfaces.core;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.CompanyLiquidation;
import servicelayer.entity.businessEntity.User;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreCompanyLiquidation {
	
	CompanyLiquidation liquidationByCompany(Date month, User userContext, double typeExchange, boolean insert)throws ServerException, ClientException;
	CompanyLiquidation getCompanyLiqudationsByDate(Date month, User userContext) throws ServerException, ClientException;
	boolean existLiquidation(Date month) throws ServerException, ClientException;
	ArrayList<CompanyLiquidation> getCompanyLiquidations(Date date) throws ServerException;
	double getTypeExchange(Date month) throws ServerException;
}
