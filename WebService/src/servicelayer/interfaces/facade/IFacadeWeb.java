package servicelayer.interfaces.facade;

import java.util.ArrayList;

import servicelayer.entity.valueObject.VOUser;
import servicelayer.exceptions.DataLayerException;
import servicelayer.exceptions.ServiceLayerException;

public interface IFacadeWeb {

	public void insertUser(VOUser voUser) throws ServiceLayerException;
	
	public void deleteUser(int id) throws ServiceLayerException;
	
	public VOUser getUser(int id) throws ServiceLayerException;
	
	public boolean existUser(int id) throws ServiceLayerException;
	
	public ArrayList<VOUser> getUsers() throws ServiceLayerException;
	
	boolean login(String userName,String password) throws ServiceLayerException;
}
