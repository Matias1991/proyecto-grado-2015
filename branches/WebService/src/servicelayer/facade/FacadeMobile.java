package servicelayer.facade;

import java.util.ArrayList;

import servicelayer.core.CoreUser;
import servicelayer.entity.valueObject.VOUser;
import servicelayer.interfaces.core.ICoreUser;
import servicelayer.interfaces.facade.IFacadeMobile;

public class FacadeMobile implements IFacadeMobile{

	private ICoreUser ICoreUser = null;
	
	private static FacadeMobile instance = null;
	
	private FacadeMobile()
	{
		ICoreUser = CoreUser.GetInstance();
	}
	
	public static FacadeMobile GetInstance()
	{
		if(instance == null)
		{
			instance = new FacadeMobile();
		}
		return instance;
	}
	
	public void InsertUser(VOUser voUser)
	{
		ICoreUser.Insert(voUser);
	}

	@Override
	public void DeleteUser(int id) {
		// TODO Auto-generated method stub
		ICoreUser.Delete(id);
	}

	@Override
	public VOUser GetUser(int id) {
		// TODO Auto-generated method stub
		return ICoreUser.Get(id);
	}

	@Override
	public boolean ExistUser(int id) {
		// TODO Auto-generated method stub
		return ICoreUser.Exist(id);
	}

	@Override
	public ArrayList<VOUser> GetAllUsers() {
		// TODO Auto-generated method stub
		return ICoreUser.GetAll();
	}
}
