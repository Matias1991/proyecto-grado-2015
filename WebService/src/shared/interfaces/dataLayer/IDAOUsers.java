package shared.interfaces.dataLayer;

import servicelayer.entity.businessEntity.User;
import shared.exceptions.DataLayerException;

public interface IDAOUsers extends IDAOBase<User>{

	User getUserByUserName(String userName) throws DataLayerException;
	
	User getUserByUserEmail(String userEmail) throws DataLayerException;
	
	User login(String userName,String password) throws DataLayerException;
	
	void updatePassword(int id, String newPassword) throws DataLayerException;
}
