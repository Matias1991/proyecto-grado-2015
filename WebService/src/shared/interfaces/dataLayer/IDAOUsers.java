package shared.interfaces.dataLayer;

import servicelayer.entity.businessEntity.User;
import shared.exceptions.DataLayerException;

public interface IDAOUsers extends IDAOBase<User>{

	User getUserByUserName(String userName) throws DataLayerException;
	
	boolean login(String userName,String password) throws DataLayerException;
}
