package shared.interfaces.core;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.ChanelType;
import servicelayer.entity.businessEntity.User;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreUser {

	void insertUser(User user) throws ServerException, ClientException;

	void deleteUser(int id) throws ServerException, ClientException;

	User update(int id, User user) throws ServerException,
			ClientException;

	User getUser(int id) throws ServerException, ClientException;

	boolean existUser(int id) throws ServerException;

	ArrayList<User> getUsers() throws ServerException;

	User login(String userName, String password, ChanelType chanel) throws ServerException,
			ClientException;

	void forgotPassord(String userEmail) throws ServerException,
			ClientException;

	void resetPassword(int id) throws ServerException, ClientException;

	void changePassword(int id, String oldPassword, String newPassword)
			throws ServerException, ClientException;

	void unlockUser(int id) throws ServerException, ClientException;

	ArrayList<User> getUsersByStatus(int userStatusId) throws ServerException;

	ArrayList<User> getUsersByTypeIdAndStatus(int userStatusId,
			int usersTypeId) throws ServerException;

	ArrayList<User> getUsersByType(int userTypeId) throws ServerException;
}
