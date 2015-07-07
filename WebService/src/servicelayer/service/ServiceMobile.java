package servicelayer.service;

import java.util.ArrayList;

import servicelayer.core.CoreUser;
import servicelayer.entity.valueObject.VOUser;
import servicelayer.utilities.Constants;
import shared.exceptions.ServiceLayerException;
import shared.interfaces.core.ICoreUser;

public class ServiceMobile {

	private ICoreUser iCoreUser = null;
	
	public ServiceMobile()
	{
		try {
			iCoreUser = CoreUser.GetInstance();
		} catch (ServiceLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void insert(VOUser voUser)
	{
		try {
			iCoreUser.insertUser(voUser);
		} catch (ServiceLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public VOUser getUser(int id)
	{
		try {
			return iCoreUser.getUser(id);
		} catch (ServiceLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void delete(int id)
	{
		try {
			iCoreUser.deleteUser(id);
		} catch (ServiceLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean exist(int id)
	{
		try {
			return iCoreUser.existUser(id);
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
			voUsers = iCoreUser.getUsers();
			VOUser [] arrayVoUser = new VOUser[voUsers.size()];
		    voUsers.toArray(arrayVoUser);
		    return arrayVoUser;
		    
		} catch (ServiceLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	    return null;
	}
	
	public VOUser login(String userName,String password)
	{
		try {
			return iCoreUser.login(userName, password);
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(Constants.GENERIC_ERROR);
		}
	}	
}
