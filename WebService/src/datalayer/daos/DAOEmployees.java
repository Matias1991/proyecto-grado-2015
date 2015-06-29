package datalayer.daos;

import java.sql.Connection;
import java.util.ArrayList;

import servicelayer.entity.businessEntity.Employed;
import servicelayer.interfaces.dataLayer.IDAOEmployees;

public class DAOEmployees implements IDAOEmployees{

	public DAOEmployees()
	{
		
	}
	
	public DAOEmployees(Connection connection)
	{
		
	}
	
	@Override
	public void Insert(Employed obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Delete(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean Exist(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Employed GetObject(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Employed> GetAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
