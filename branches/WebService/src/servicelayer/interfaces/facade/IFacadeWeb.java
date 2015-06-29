package servicelayer.interfaces.facade;

import java.util.ArrayList;

import servicelayer.entity.valueObject.VOUser;
import servicelayer.exceptions.ServiceLayerException;

public interface IFacadeWeb {

	public void InsertUser(VOUser voUser) throws ServiceLayerException;
	
	public void DeleteUser(int id) throws ServiceLayerException;
	
	public VOUser GetUser(int id) throws ServiceLayerException;
	
	public boolean ExistUser(int id) throws ServiceLayerException;
	
	public ArrayList<VOUser> GetAllUsers() throws ServiceLayerException;
}
