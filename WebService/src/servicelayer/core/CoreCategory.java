package servicelayer.core;

import java.util.ArrayList;

import datalayer.daos.DAOCategory;
import servicelayer.entity.valueObject.VOCategory;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreCategory;
import shared.interfaces.dataLayer.IDAOCategroy;

public class CoreCategory implements ICoreCategory {
	
	private static CoreCategory instance = null;
	private IDAOCategroy iDAOCategory;
	
	private CoreCategory() throws ServerException{
		iDAOCategory = new DAOCategory();
	}

	public static CoreCategory GetInstance() throws ServerException	{
		if(instance == null){
			instance = new CoreCategory();
		}
		return instance;
	}
	
	@Override
	public void insertCategory(VOCategory voCategory) throws ServerException,
			ClientException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteCateory(int id) throws ServerException, ClientException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public VOCategory updateCategory(int id, VOCategory voCategory)
			throws ServerException, ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VOCategory getCategory(int id) throws ServerException,
			ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<VOCategory> getCategories() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}
	

}
