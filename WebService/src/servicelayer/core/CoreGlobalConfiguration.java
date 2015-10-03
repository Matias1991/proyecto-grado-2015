package servicelayer.core;

import java.util.ArrayList;

import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.GlobalConfiguration;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreGlobalConfiguration;

public class CoreGlobalConfiguration implements ICoreGlobalConfiguration{

	private static CoreGlobalConfiguration instance = null;

	public static CoreGlobalConfiguration GetInstance() throws ServerException {
		if (instance == null) {
			instance = new CoreGlobalConfiguration();
		}
		return instance;
	}
	
	@Override
	public GlobalConfiguration update(int id,
			GlobalConfiguration globalConfiguration) throws ServerException,
			ClientException {
		DAOManager daoManager = new DAOManager();
		try {
			
			daoManager.getDAOGlobalConfigurations().update(id, globalConfiguration);
			daoManager.commit();
			
			return daoManager.getDAOGlobalConfigurations().getObject(id);
			
		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public ArrayList<GlobalConfiguration> getGlobalConfigurations()
			throws ServerException {
		DAOManager daoManager = new DAOManager();
		try {
			
			return daoManager.getDAOGlobalConfigurations().getObjects();
			
		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}
	
	@Override
	public String getConfigurationValueByCode(String code)
			throws ServerException {
		DAOManager daoManager = new DAOManager();
		try {
			
			return daoManager.getDAOGlobalConfigurations().getConfigurationValueByCode(code);
			
		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}
}
