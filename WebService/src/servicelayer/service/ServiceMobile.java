package servicelayer.service;

import java.util.ArrayList;

import servicelayer.entity.valueObject.VOUser;
import servicelayer.exceptions.ServiceLayerException;
import servicelayer.facade.FacadeMobile;
import servicelayer.interfaces.facade.IFacadeMobile;

public class ServiceMobile {

	private IFacadeMobile IFacadeMobile;
	
	public ServiceMobile()
	{
		try {
			IFacadeMobile = FacadeMobile.GetInstance();
		} catch (ServiceLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insert(VOUser voUser)
	{
		try {
			IFacadeMobile.insertUser(voUser);
		} catch (ServiceLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public VOUser getUser(int id)
	{
		try {
			return IFacadeMobile.getUser(id);
		} catch (ServiceLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void delete(int id)
	{
		try {
			IFacadeMobile.deleteUser(id);
		} catch (ServiceLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean exist(int id)
	{
		try {
			return IFacadeMobile.existUser(id);
		} catch (ServiceLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
	
	public VOUser[] getAllUsers()
	{
		ArrayList<VOUser> voUsers;
		try {
			voUsers = IFacadeMobile.getUsers();
			VOUser [] arrayVoUser = new VOUser[voUsers.size()];
		    voUsers.toArray(arrayVoUser);
		    return arrayVoUser;
		    
		} catch (ServiceLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	    return null;
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
