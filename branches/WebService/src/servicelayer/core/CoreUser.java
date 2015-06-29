package servicelayer.core;

import java.util.ArrayList;

import datalayer.daos.DAOUsers;
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
	public void Insert(VOUser voUser) throws ServiceLayerException {
		
		try {
			User user = new User(voUser);
			IDAOUsers.Insert(user);

		} catch (DataLayerException e) {
			throw new ServiceLayerException(e);
		}
	}

	@Override
	public void Delete(int id) throws ServiceLayerException {
		
		try
		{
			User user = IDAOUsers.GetObject(id);
			
			if(user == null)
			{
				throw new ServiceLayerException("No existe un usuario con id blabla");
			}
			IDAOUsers.Delete(id);
		}
		catch(DataLayerException dataLayerEx)
		{
			throw new ServiceLayerException(dataLayerEx);
		}
	}

	@Override
	public VOUser Get(int id) throws ServiceLayerException {
		
		User user;
		VOUser voUser = null;
		
		try {
			user = IDAOUsers.GetObject(id);
			if(user != null)
			{
				voUser = new VOUser();
				voUser.setId(user.getId());
				voUser.setName(user.getName());
				voUser.setUserName(user.getUserName());
				voUser.setPassword(user.getPassword());
				voUser.setLastName(user.getLastName());
				voUser.setEmail(user.getEmail());
			}
			else
			{
				throw new ServiceLayerException("No existe un usuario con ese id");
			}
			
		} catch (DataLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return voUser;
	}

	@Override
	public boolean Exist(int id)  throws ServiceLayerException{

		try {
			return IDAOUsers.Exist(id);
		} catch (DataLayerException e) {
			throw new ServiceLayerException(e);
		}
	}

	@Override
	public ArrayList<VOUser> GetAll() throws ServiceLayerException{
		
		ArrayList<User> users;
		ArrayList<VOUser> voUsers = null;
		
		try {
			users = IDAOUsers.GetAll();
			voUsers = new ArrayList<VOUser>(); 
			
			for(User user: users)
			{
				VOUser voUser = new VOUser();
				voUser.setId(user.getId());
				voUser.setName(user.getName());
				voUser.setUserName(user.getUserName());
				voUser.setPassword(user.getPassword());
				voUser.setLastName(user.getLastName());
				voUser.setEmail(user.getEmail());
				
				voUsers.add(voUser);
			}
		} catch (DataLayerException e) {
			throw new ServiceLayerException(e);
		}
		
		return voUsers;
	}
}
