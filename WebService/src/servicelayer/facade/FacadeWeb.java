package servicelayer.facade;
   
import java.util.ArrayList;

import servicelayer.core.CoreUser;
import servicelayer.entity.valueObject.VOUser;
import servicelayer.exceptions.DataLayerException;
import servicelayer.exceptions.ServiceLayerException;
import servicelayer.interfaces.core.ICoreUser;
import servicelayer.interfaces.facade.IFacadeWeb;

public class FacadeWeb implements IFacadeWeb{

	private ICoreUser ICoreUser = null;
	
	private static FacadeWeb instance = null;
	
	private FacadeWeb() throws ServiceLayerException
	{
		ICoreUser = CoreUser.GetInstance();
	}
	
	public static FacadeWeb GetInstance() throws ServiceLayerException
	{
		if(instance == null)
		{
			instance = new FacadeWeb();
		}
		return instance;
	}
	
	@Override
	public void insertUser(VOUser voUser) throws ServiceLayerException
	{
		ICoreUser.insert(voUser);
	}

	@Override
	public void deleteUser(int id) throws ServiceLayerException {
		ICoreUser.delete(id);
	}

	@Override
	public VOUser getUser(int id) throws ServiceLayerException {
		return ICoreUser.getUser(id);
	}

	@Override
	public boolean existUser(int id) throws ServiceLayerException {
		return ICoreUser.exist(id);
	}

	@Override
	public ArrayList<VOUser> getUsers() throws ServiceLayerException {
		return ICoreUser.getUsers();
	}
	
	@Override
	public boolean login(String userName,String password) throws ServiceLayerException
	{
		return ICoreUser.login(userName, password);
	}
}
