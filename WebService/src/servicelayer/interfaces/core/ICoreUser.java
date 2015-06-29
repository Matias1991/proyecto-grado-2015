package servicelayer.interfaces.core;

import java.util.ArrayList;

import servicelayer.entity.valueObject.VOUser;
import servicelayer.exceptions.ServiceLayerException;

public interface ICoreUser {
	
	public void Insert(VOUser voUser) throws ServiceLayerException;
	
	public void Delete(int id) throws ServiceLayerException;
	
	public VOUser Get(int id) throws ServiceLayerException;
	
	public boolean Exist(int id) throws ServiceLayerException;
	
	public ArrayList<VOUser> GetAll() throws ServiceLayerException;
}
