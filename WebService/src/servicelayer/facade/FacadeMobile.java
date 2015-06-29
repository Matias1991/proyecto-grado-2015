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
	public void InsertUser(VOUser voUser) throws ServiceLayerException
	{
		ICoreUser.Insert(voUser);
	}

	@Override
	public void DeleteUser(int id) throws ServiceLayerException {
		ICoreUser.Delete(id);
	}

	@Override
	public VOUser GetUser(int id) throws ServiceLayerException {
		return ICoreUser.Get(id);
	}

	@Override
	public boolean ExistUser(int id) throws ServiceLayerException {
		return ICoreUser.Exist(id);
	}

	@Override
	public ArrayList<VOUser> GetAllUsers() throws ServiceLayerException {
		return ICoreUser.GetAll();
	}
}
