package shared.interfaces.dataLayer;

import java.util.ArrayList;

import shared.exceptions.DataLayerException;

public interface IDAOBase<Obj> {

	void insert(Obj obj) throws DataLayerException;
	
	void delete(int id) throws DataLayerException;
	
	void update(int id, Obj obj) throws DataLayerException;
	
    boolean exist(int id) throws DataLayerException;
	
	Obj getObject(int id) throws DataLayerException;
	
	ArrayList<Obj> getObjects() throws DataLayerException;
}
