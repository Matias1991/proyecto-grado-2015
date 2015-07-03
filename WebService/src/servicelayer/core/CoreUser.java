package servicelayer.core;

import java.util.ArrayList;

import datalayer.daos.DAOUsers;
import servicelayer.Utilities;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.valueObject.VOUser;
import servicelayer.exceptions.DataLayerException;
import servicelayer.exceptions.ServiceLayerException;
import servicelayer.interfaces.core.ICoreUser;
import servicelayer.interfaces.dataLayer.IDAOUsers;

public class CoreUser implements ICoreUser {
	
	private static CoreUser instance = null;
	private IDAOUsers IDAOUsers;
	
	private CoreUser() throws ServiceLayerException
	{
		try {
			IDAOUsers = new DAOUsers();
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
	public void insert(VOUser voUser) throws ServiceLayerException {
		
		try {
			String hashPassword = Utilities.Encrypt(voUser.getPassword());
			User user = new User(voUser);
			user.setPassword(hashPassword);
			IDAOUsers.insert(user);

		} catch (DataLayerException e) {
			throw new ServiceLayerException(e);
		}
	}

	@Override
	public void delete(int id) throws ServiceLayerException {
		
		try
		{
			User user = IDAOUsers.getObject(id);
			if(user == null)
				throw new ServiceLayerException("No existe un usuario con id blabla");
			
			IDAOUsers.delete(id);
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
			user = IDAOUsers.getObject(id);
			if(user != null)
			{
				voUser = new VOUser();
				voUser.setId(user.getId());
				voUser.setName(user.getName());
				voUser.setUserName(user.getUserName());
				voUser.setLastName(user.getLastName());
				voUser.setEmail(user.getEmail());
			}
			else
			{
				throw new ServiceLayerException("No existe un usuario con ese id");
			}
			
		} catch (DataLayerException e) {
			throw new ServiceLayerException(e);
		}

		return voUser;
	}

	@Override
	public boolean exist(int id)  throws ServiceLayerException{

		try {
			return IDAOUsers.exist(id);
		} catch (DataLayerException e) {
			throw new ServiceLayerException(e);
		}
	}

	@Override
	public ArrayList<VOUser> getUsers() throws ServiceLayerException{
		
		ArrayList<User> users;
		ArrayList<VOUser> voUsers = null;
		
		try {
			users = IDAOUsers.getObjects();
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
			String hashPassword = Utilities.Encrypt(password);
			return IDAOUsers.login(userName, hashPassword);
		}catch (DataLayerException e) {
			throw new ServiceLayerException(e);
		}
	}
}
