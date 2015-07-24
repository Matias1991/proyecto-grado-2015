package shared.interfaces.dataLayer;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.User;
import servicelayer.entity.businessEntity.UserStatus;
import servicelayer.entity.businessEntity.UserType;
import shared.exceptions.ServerException;

public interface IDAOUsers extends IDAOBase<User>{

	ArrayList<User> getUsersByTypeAndStatus(UserType userType, UserStatus userStatus) throws ServerException;
	
	User getUserByUserName(String userName) throws ServerException;
	
	User getUserByUserEmail(String userEmail) throws ServerException;
	
	void updatePassword(int id, String newPassword) throws ServerException;
	
	void update(int id, UserStatus userStatus, int attempts, Date lastAttemptDateTimeUTC) throws ServerException;
	
	ArrayList<User> getUsersByStatus(UserStatus userStatus) throws ServerException;
}
