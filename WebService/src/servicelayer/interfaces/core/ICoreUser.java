package servicelayer.interfaces.core;

import java.util.ArrayList;

import servicelayer.entity.valueObject.VOUser;
import servicelayer.exceptions.ServiceLayerException;

public interface ICoreUser {
	
	public void insert(VOUser voUser) throws ServiceLayerException;
	
	public void delete(int id) throws ServiceLayerException;
	
	public VOUser getUser(int id) throws ServiceLayerException;
	
	public boolean exist(int id) throws ServiceLayerException;
	
	public ArrayList<VOUser> getUsers() throws ServiceLayerException;
	
	public boolean login(String userName, String password) throws ServiceLayerException;
}
