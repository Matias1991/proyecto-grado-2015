package servicelayer.facade;

import java.util.ArrayList;

import servicelayer.core.CoreUser;
import servicelayer.entity.valueObject.VOUser;
import servicelayer.exceptions.ServiceLayerException;
import servicelayer.interfaces.core.ICoreUser;
import servicelayer.interfaces.facade.IFacadeMobile;

public class FacadeMobile implements IFacadeMobile{

	private ICoreUser ICoreUser = null;
	
	private static FacadeMobile instance = null;
	
	private FacadeMobile() throws ServiceLayerException
	{
		ICoreUser = CoreUser.GetInstance();
	}
	
	public static FacadeMobile GetInstance() throws ServiceLayerException
	{
		if(instance == null)
		{
			instance = new FacadeMobile();
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
}
