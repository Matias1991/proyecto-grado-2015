package servicelayer.core;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.Charge;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOCharges;

public class CoreCharge implements IDAOCharges{

	private static CoreCharge instance = null;

	private CoreCharge() {
	}

	public static CoreCharge GetInstance() throws ServerException {
		if (instance == null) {
			instance = new CoreCharge();
		}
		return instance;
	}
	
	@Override
	public int insert(Charge obj) throws ServerException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int id) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int id, Charge obj) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exist(int id) throws ServerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Charge getObject(int id) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Charge> getObjects() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}
}
