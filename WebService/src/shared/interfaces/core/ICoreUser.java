package shared.interfaces.core;

import java.util.ArrayList;

import servicelayer.entity.valueObject.VOUser;
import shared.exceptions.ServiceLayerException;

public interface ICoreUser {
	
	public void insertUser(VOUser voUser) throws ServiceLayerException;
	
	public void deleteUser(int id) throws ServiceLayerException;
	
	public VOUser update(int id, VOUser voUser) throws ServiceLayerException;
	
	public VOUser getUser(int id) throws ServiceLayerException;
	
	public boolean existUser(int id) throws ServiceLayerException;
	
	public ArrayList<VOUser> getUsers() throws ServiceLayerException;
	
	public VOUser login(String userName, String password) throws ServiceLayerException;
}
