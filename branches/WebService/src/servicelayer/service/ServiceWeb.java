package servicelayer.service;

import java.util.ArrayList;

import servicelayer.entity.valueObject.VOUser;
import servicelayer.facade.FacadeWeb;
import servicelayer.interfaces.facade.IFacadeWeb;

public class ServiceWeb {

	private IFacadeWeb IFacadeWeb = FacadeWeb.GetInstance();
	
	public void insert(VOUser voUser)
	{
		IFacadeWeb.InsertUser(voUser);
	}
	
	public VOUser getUser(int id)
	{
		return IFacadeWeb.GetUser(id);
	}
	
	public void delete(int id)
	{
		IFacadeWeb.DeleteUser(id);
	}
	
	public boolean exist(int id)
	{
		return IFacadeWeb.ExistUser(id);
	}
	
	public boolean login(String userName,String password)
	{
		VOUser [] users = getAllUsers();
		for(VOUser user : users)
		{
			if(user.getUserName() == userName && user.getPassword() == password)
			{
				
				return true;
				
			}
		}
		return false;
	}
	
	public VOUser[] getAllUsers()
	{
		ArrayList<VOUser> voUsers = IFacadeWeb.GetAllUsers();
		
		VOUser [] arrayVoUser = new VOUser[voUsers.size()];
	    voUsers.toArray(arrayVoUser);
	    
	    return arrayVoUser;
	}
}
