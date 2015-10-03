package shared.interfaces.dataLayer;

import servicelayer.entity.businessEntity.GlobalConfiguration;
import shared.exceptions.ServerException;

public interface IDAOGlobalConfigurations extends IDAOBase<GlobalConfiguration> {

	String getConfigurationValueByCode(String code) throws ServerException;
}
