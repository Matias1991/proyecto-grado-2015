package servicelayer.service;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import servicelayer.entity.valueObject.VOUser;
import servicelayer.exceptions.ServiceLayerException;
import servicelayer.facade.FacadeWeb;
import servicelayer.interfaces.facade.IFacadeWeb;

public class ServiceWeb {

	private IFacadeWeb IFacadeWeb;
	
	public ServiceWeb()
	{
		try {
			IFacadeWeb = FacadeWeb.GetInstance();
		} catch (ServiceLayerException e) {
			throw new RuntimeException("Error when try to connect db");
		} catch (Exception e) {
			throw new RuntimeException("Error generico, no controlado");
		}
	}
	
	public void insert(VOUser voUser)
	{
		try {
			IFacadeWeb.InsertUser(voUser);
		} catch (ServiceLayerException e) {
			throw new RuntimeException("Error in opertion insert");
		} catch (Exception e) {
			throw new RuntimeException("Error generico, no controlado");
		}
	}
	
	public VOUser getUser(int id)
	{
		try {
			return IFacadeWeb.GetUser(id);
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException("Error generico, no controlado");
		}
	}
	
	public boolean delete(int id)
	{
		try {
			IFacadeWeb.DeleteUser(id);
			
			return true;
			
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException("Error generico, no controlado");
		}
	}
	
	public boolean exist(int id)
	{
		try {
			return IFacadeWeb.ExistUser(id);
		} catch (ServiceLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
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
	
	public VOUser[] getAllUsers()
	{
		ArrayList<VOUser> voUsers;
		try {
			voUsers = IFacadeWeb.GetAllUsers();
			VOUser [] arrayVoUser = new VOUser[voUsers.size()];
		    voUsers.toArray(arrayVoUser);
		    return arrayVoUser;
		    
		} catch (ServiceLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    return null;
	}
}
