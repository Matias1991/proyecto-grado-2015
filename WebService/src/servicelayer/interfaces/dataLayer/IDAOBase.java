package servicelayer.interfaces.dataLayer;

import java.util.ArrayList;

import servicelayer.exceptions.DataLayerException;

public interface IDAOBase<Obj> {

	public void Insert(Obj obj) throws DataLayerException;
	
	public void Delete(int id) throws DataLayerException;
	
	public boolean Exist(int id) throws DataLayerException;
	
	public Obj GetObject(int id) throws DataLayerException;
	
	public ArrayList<Obj> GetAll() throws DataLayerException;
}
