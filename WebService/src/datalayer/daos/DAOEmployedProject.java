package datalayer.daos;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.EmployedProject;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOEmployedProject;

public class DAOEmployedProject implements IDAOEmployedProject{

	@Override
	public int insert(EmployedProject obj) throws ServerException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int id) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int id, EmployedProject obj) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exist(int id) throws ServerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EmployedProject getObject(int id) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<EmployedProject> getObjects() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

}
