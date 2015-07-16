package shared.interfaces.dataLayer;

import java.util.ArrayList;

import shared.exceptions.ServerException;

public interface IDAOBase<Obj> {

	void insert(Obj obj) throws ServerException;
	
	void delete(int id) throws ServerException;
	
	void update(int id, Obj obj) throws ServerException;
	
    boolean exist(int id) throws ServerException;
	
	Obj getObject(int id) throws ServerException;
	
	ArrayList<Obj> getObjects() throws ServerException;
}
