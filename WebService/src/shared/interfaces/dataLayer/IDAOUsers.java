package shared.interfaces.dataLayer;

import java.util.Date;

import servicelayer.entity.businessEntity.User;
import servicelayer.entity.businessEntity.UserStatus;
import shared.exceptions.DataLayerException;

public interface IDAOUsers extends IDAOBase<User>{

	User getUserByUserName(String userName) throws DataLayerException;
	
	User getUserByUserEmail(String userEmail) throws DataLayerException;
	
	User login(String userName,String password) throws DataLayerException;
	
	void updatePassword(int id, String newPassword) throws DataLayerException;
	
	void update(int id, UserStatus userStatus, int attempts, Date lastAttemptDateTimeUTC) throws DataLayerException;
}
