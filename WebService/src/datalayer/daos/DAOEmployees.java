package datalayer.daos;

import java.sql.Connection;
import java.util.ArrayList;

import servicelayer.entity.businessEntity.Employed;
import shared.exceptions.DataLayerException;
import shared.interfaces.dataLayer.IDAOEmployees;

public class DAOEmployees implements IDAOEmployees{

	public DAOEmployees()
	{
		
	}
	
	public DAOEmployees(Connection connection)
	{
		
	}
	
	@Override
	public void insert(Employed obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exist(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Employed getObject(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Employed> getObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(int id, Employed obj) throws DataLayerException {
		// TODO Auto-generated method stub
		
	}

}
