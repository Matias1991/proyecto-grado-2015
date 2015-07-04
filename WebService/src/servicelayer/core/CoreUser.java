package servicelayer.core;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import datalayer.daos.DAOUsers;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.valueObject.VOUser;
import servicelayer.utilities.HashMD5;
import shared.exceptions.DataLayerException;
import shared.exceptions.ServiceLayerException;
import shared.interfaces.core.ICoreUser;
import shared.interfaces.dataLayer.IDAOUsers;

public class CoreUser implements ICoreUser {
	
	private static CoreUser instance = null;
	private IDAOUsers iDAOUsers;

	private CoreUser() throws ServiceLayerException
	{
		try {
			iDAOUsers = new DAOUsers();
		} catch (DataLayerException e) {
			throw new ServiceLayerException(e);
		}
	}
	
	public static CoreUser GetInstance() throws ServiceLayerException
	{
		if(instance == null)
		{
			instance = new CoreUser();
		}
		return instance;
	}
	
	@Override
	public void insertUser(VOUser voUser) throws ServiceLayerException {
		
		try {
			
			if(iDAOUsers.getUserByUserName(voUser.getUserName()) == null)
			{
				//primer contrasena es el nombre de usuario
				String hashPassword = HashMD5.Encrypt(voUser.getUserName());
				User user = new User(voUser);
				user.setPassword(hashPassword);
				iDAOUsers.insert(user);
			}
			else
				throw new ServiceLayerException("Ya existe un usuario con este nombre de usuario");

		} catch (DataLayerException e) {
			throw new ServiceLayerException(e);
		}
	}

	@Override
	public void deleteUser(int id) throws ServiceLayerException {
		
		try
		{
			User user = iDAOUsers.getObject(id);
			if(user == null)
				throw new ServiceLayerException("No existe un usuario con id blabla");
			
			iDAOUsers.delete(id);
		}
		catch(DataLayerException dataLayerEx)
		{
			throw new ServiceLayerException(dataLayerEx);
		}
	}

	@Override
	public VOUser getUser(int id) throws ServiceLayerException {
		
		User user;
		VOUser voUser = null;
		
		try {
			user = iDAOUsers.getObject(id);
			if(user != null)
			{
				voUser = new VOUser();
				voUser.setId(user.getId());
				voUser.setName(user.getName());
				voUser.setUserName(user.getUserName());
				voUser.setLastName(user.getLastName());
				voUser.setEmail(user.getEmail());
				voUser.setUserType(user.getUserType().getValue());
			}
			else
				throw new ServiceLayerException("No existe un usuario con ese id");
			
		} catch (DataLayerException e) {
			throw new ServiceLayerException(e);
		}

		return voUser;
	}

	@Override
	public boolean existUser(int id)  throws ServiceLayerException{

		try {
			return iDAOUsers.exist(id);
		} catch (DataLayerException e) {
			throw new ServiceLayerException(e);
		}
	}

	@Override
	public ArrayList<VOUser> getUsers() throws ServiceLayerException{
		
		ArrayList<User> users;
		ArrayList<VOUser> voUsers = null;
		
		try {
			users = iDAOUsers.getObjects();
			voUsers = new ArrayList<VOUser>(); 
			
			for(User user: users)
			{
				VOUser voUser = new VOUser();
				voUser.setId(user.getId());
				voUser.setName(user.getName());
				voUser.setUserName(user.getUserName());
				voUser.setLastName(user.getLastName());
				voUser.setEmail(user.getEmail());
				voUser.setUserType(user.getUserType().getValue());
				voUsers.add(voUser);
			}
		} catch (DataLayerException e) {
			throw new ServiceLayerException(e);
		}
		
		return voUsers;
	}
	
	@Override
	public boolean login(String userName, String password) throws ServiceLayerException
	{
		try {
			String hashPassword = HashMD5.Encrypt(password);
			return iDAOUsers.login(userName, hashPassword);
		}catch (DataLayerException e) {
			throw new ServiceLayerException(e);
		}
	}

	@Override
	public VOUser update(int id, VOUser voUser) throws ServiceLayerException {
		
		try {
			
			User user = new User(voUser);
			iDAOUsers.update(id, user);
		
			return getUser(id);
			
		}catch (DataLayerException e) {
			throw new ServiceLayerException(e);
		}
	}
}
