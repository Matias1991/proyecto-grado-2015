package servicelayer.interfaces.facade;

import java.util.ArrayList;

import servicelayer.entity.valueObject.VOUser;

public interface IFacadeWeb {

	public void InsertUser(VOUser voUser);
	
	public void DeleteUser(int id);
	
	public VOUser GetUser(int id);
	
	public boolean ExistUser(int id);
	
	public ArrayList<VOUser> GetAllUsers();
}
