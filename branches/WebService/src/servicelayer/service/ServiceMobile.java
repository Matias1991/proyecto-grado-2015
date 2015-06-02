package servicelayer.service;

import java.util.ArrayList;

import servicelayer.entity.valueObject.VOUser;
import servicelayer.facade.FacadeMobile;
import servicelayer.interfaces.facade.IFacadeMobile;

public class ServiceMobile {

	private IFacadeMobile IFacadeMobile = FacadeMobile.GetInstance();
	
	public void insert(VOUser voUser)
	{
		IFacadeMobile.InsertUser(voUser);
	}
	
	public VOUser getUser(int id)
	{
		return IFacadeMobile.GetUser(id);
	}
	
	public void delete(int id)
	{
		IFacadeMobile.DeleteUser(id);
	}
	
	public boolean exist(int id)
	{
		return IFacadeMobile.ExistUser(id);
	}
	
	public VOUser[] getAllUsers()
	{
		ArrayList<VOUser> voUsers = IFacadeMobile.GetAllUsers();
		
		VOUser [] arrayVoUser = new VOUser[voUsers.size()];
	    voUsers.toArray(arrayVoUser);
	    
	    return arrayVoUser;
	}
	
	public boolean login(String userName,String password)
	{
		VOUser [] users = getAllUsers();
		for(VOUser user : users)
		{
			if(user.getUserName().equals(userName) && user.getPassword().equals(password))
			{
				return true;
			}
		}
		return false;
	}	
}
