package shared.interfaces.core;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.GlobalConfiguration;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreGlobalConfiguration {

	GlobalConfiguration update(int id, GlobalConfiguration globalConfiguration)
			throws ServerException, ClientException;

	ArrayList<GlobalConfiguration> getGlobalConfigurations()
			throws ServerException;
	
	String getConfigurationValueByCode(String code)
			throws ServerException;
}
