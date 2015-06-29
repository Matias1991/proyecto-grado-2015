package servicelayer.facade;
   
import java.util.ArrayList;

import servicelayer.core.CoreUser;
import servicelayer.entity.valueObject.VOUser;
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
	public void InsertUser(VOUser voUser) throws ServiceLayerException
	{
		ICoreUser.Insert(voUser);
	}

	@Override
	public void DeleteUser(int id) throws ServiceLayerException {
		// TODO Auto-generated method stub
		ICoreUser.Delete(id);
	}

	@Override
	public VOUser GetUser(int id) throws ServiceLayerException {
		// TODO Auto-generated method stub
		return ICoreUser.Get(id);
	}

	@Override
	public boolean ExistUser(int id) throws ServiceLayerException {
		// TODO Auto-generated method stub
		return ICoreUser.Exist(id);
	}

	@Override
	public ArrayList<VOUser> GetAllUsers() throws ServiceLayerException {
		// TODO Auto-generated method stub
		return ICoreUser.GetAll();
	}
}
