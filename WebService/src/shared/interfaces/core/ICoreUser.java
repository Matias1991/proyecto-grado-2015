package shared.interfaces.core;

import java.util.ArrayList;

import servicelayer.entity.valueObject.VOUser;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreUser {
	
	void insertUser(VOUser voUser) throws ServerException, ClientException;
	
	void deleteUser(int id) throws ServerException, ClientException;
	
	VOUser update(int id, VOUser voUser) throws ServerException, ClientException;
	
	VOUser getUser(int id) throws ServerException, ClientException;
	
	boolean existUser(int id) throws ServerException;
	
	ArrayList<VOUser> getUsers() throws ServerException;
	
	VOUser login(String userName, String password) throws ServerException, ClientException;
	
	void forgotPassord(String userEmail) throws ServerException, ClientException;
	
	void resetPassword(int id) throws ServerException, ClientException;
	
	void changePassword(int id, String oldPassword, String newPassword) throws ServerException, ClientException;
	
	void unlockUser(int id) throws ServerException, ClientException;
}
