package datalayer.daos;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.PartnerProject;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOParnerProject;

public class DAOPartnerProject implements IDAOParnerProject {

	@Override
	public int insert(PartnerProject obj) throws ServerException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int id) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int id, PartnerProject obj) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exist(int id) throws ServerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PartnerProject getObject(int id) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<PartnerProject> getObjects() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

}
