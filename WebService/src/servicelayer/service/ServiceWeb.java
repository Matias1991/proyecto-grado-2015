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
	private String errorGeneric = "Ocurrio un error en el sistema, consulte con el administrador";
	
	public ServiceWeb()
	{
		try {
			IFacadeWeb = FacadeWeb.GetInstance();
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(errorGeneric);
		}
	}
	
	public boolean insertUser(VOUser voUser)
	{
		try {
			IFacadeWeb.insertUser(voUser);
			return true;
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(errorGeneric);
		}
	}
	
	public VOUser getUser(int id)
	{
		try {
			return IFacadeWeb.getUser(id);
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(errorGeneric);
		}
	}
	
	public boolean deleteUser(int id)
	{
		try {
			IFacadeWeb.deleteUser(id);
			return true;
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(errorGeneric);
		}
	}
	
	public boolean existUser(int id)
	{
		try {
			return IFacadeWeb.existUser(id);
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(errorGeneric);
		}
	}
	
	public boolean login(String userName,String password)
	{
		try {
			return IFacadeWeb.login(userName, password);
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(errorGeneric);
		}
	}
	
	public VOUser[] getUsers()
	{
		ArrayList<VOUser> voUsers;
		try {
			voUsers = IFacadeWeb.getUsers();
			VOUser [] arrayVoUser = new VOUser[voUsers.size()];
		    voUsers.toArray(arrayVoUser);
		    return arrayVoUser;
		    
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(errorGeneric);
		}
	}
}
