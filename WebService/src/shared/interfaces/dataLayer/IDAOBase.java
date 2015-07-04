package shared.interfaces.dataLayer;

import java.util.ArrayList;

import shared.exceptions.DataLayerException;

public interface IDAOBase<Obj> {

	public void insert(Obj obj) throws DataLayerException;
	
	public void delete(int id) throws DataLayerException;
	
	public void update(int id, Obj obj) throws DataLayerException;
	
	public boolean exist(int id) throws DataLayerException;
	
	public Obj getObject(int id) throws DataLayerException;
	
	public ArrayList<Obj> getObjects() throws DataLayerException;
}
