package servicelayer.core;

import java.util.ArrayList;

import datalayer.daos.DAOUsers;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.valueObject.VOUser;
import servicelayer.interfaces.core.ICoreUser;
import servicelayer.interfaces.dataLayer.IDAOUsers;

public class CoreUser implements ICoreUser {
	
	private static CoreUser instance = null;
	private IDAOUsers IDAOUsers;
	
	private CoreUser()
	{
		IDAOUsers = new DAOUsers();
	}
	
	public static CoreUser GetInstance()
	{
		if(instance == null)
		{
			instance = new CoreUser();
		}
		return instance;
	}
	
	@Override
	public void Insert(VOUser voUser) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setId(voUser.getId());
		user.setUserName(voUser.getUserName());
		user.setPassword(voUser.getPassword());
		user.setName(voUser.getName());
		user.setLastName(voUser.getLastName());
		user.setEmail(voUser.getEmail());
		IDAOUsers.Insert(user);
	}

	@Override
	public void Delete(int id) {
		// TODO Auto-generated method stub
		IDAOUsers.Delete(id);
	}

	@Override
	public VOUser Get(int id) {
		// TODO Auto-generated method stub
		User user = IDAOUsers.GetObject(id);
		VOUser voUser = new VOUser();
		voUser.setId(user.getId());
		voUser.setName(user.getName());
		voUser.setUserName(user.getUserName());
		voUser.setPassword(user.getPassword());
		voUser.setLastName(user.getLastName());
		voUser.setEmail(user.getEmail());
		
		return voUser;
	}

	@Override
	public boolean Exist(int id) {
		// TODO Auto-generated method stub
		return IDAOUsers.Exist(id);
	}

	@Override
	public ArrayList<VOUser> GetAll() {
		// TODO Auto-generated method stub
		ArrayList<User> users = IDAOUsers.GetAll();
		ArrayList<VOUser> voUsers = new ArrayList<VOUser>(); 
		
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

		return voUsers;
	}
}
