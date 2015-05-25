package servicelayer.interfaces.dataLayer;

import java.util.ArrayList;

public interface IDAOBase<Obj> {

	public void Insert(Obj obj);
	
	public void Delete(int id);
	
	public boolean Exist(int id);
	
	public Obj GetObject(int id);
	
	public ArrayList<Obj> GetAll();
}
