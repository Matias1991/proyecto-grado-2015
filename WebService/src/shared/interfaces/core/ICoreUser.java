package shared.interfaces.core;

import java.util.ArrayList;

import servicelayer.entity.valueObject.VOUser;
import shared.exceptions.ServiceLayerException;

public interface ICoreUser {
	
	void insertUser(VOUser voUser) throws ServiceLayerException;
	
	void deleteUser(int id) throws ServiceLayerException;
	
	VOUser update(int id, VOUser voUser) throws ServiceLayerException;
	
	VOUser getUser(int id) throws ServiceLayerException;
	
	boolean existUser(int id) throws ServiceLayerException;
	
	ArrayList<VOUser> getUsers() throws ServiceLayerException;
	
	VOUser login(String userName, String password) throws ServiceLayerException;
	
	void forgotPassord(String userEmail) throws ServiceLayerException;
	
	void resetPassword(int id) throws ServiceLayerException;
	
	void changePassword(int id, String oldPassword, String newPassword) throws ServiceLayerException;
	
	void unlockUser(int id) throws ServiceLayerException;
}
