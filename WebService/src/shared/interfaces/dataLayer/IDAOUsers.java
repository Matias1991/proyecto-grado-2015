package shared.interfaces.dataLayer;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.User;
import servicelayer.entity.businessEntity.UserStatus;
import servicelayer.entity.businessEntity.UserType;
import shared.exceptions.DataLayerException;

public interface IDAOUsers extends IDAOBase<User>{

	ArrayList<User> getUsersByTypeAndStatus(UserType userType, UserStatus userStatus) throws DataLayerException;
	
	User getUserByUserName(String userName) throws DataLayerException;
	
	User getUserByUserEmail(String userEmail) throws DataLayerException;
	
	void updatePassword(int id, String newPassword) throws DataLayerException;
	
	void update(int id, UserStatus userStatus, int attempts, Date lastAttemptDateTimeUTC) throws DataLayerException;
}
