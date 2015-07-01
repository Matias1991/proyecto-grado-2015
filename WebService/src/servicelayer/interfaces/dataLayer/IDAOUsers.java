package servicelayer.interfaces.dataLayer;

import servicelayer.entity.businessEntity.User;
import servicelayer.exceptions.DataLayerException;

public interface IDAOUsers extends IDAOBase<User>{

	boolean login(String userName,String password) throws DataLayerException;
}
