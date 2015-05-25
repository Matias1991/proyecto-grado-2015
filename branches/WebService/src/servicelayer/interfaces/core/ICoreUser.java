package servicelayer.interfaces.core;

import java.util.ArrayList;

import servicelayer.entity.valueObject.VOUser;

public interface ICoreUser {
	
	public void Insert(VOUser voUser);
	
	public void Delete(int id);
	
	public VOUser Get(int id);
	
	public boolean Exist(int id);
	
	public ArrayList<VOUser> GetAll();
}
