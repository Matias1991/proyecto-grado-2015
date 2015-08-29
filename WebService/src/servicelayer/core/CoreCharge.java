package servicelayer.core;

import java.util.ArrayList;
import java.util.Date;

import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.Charge;
import servicelayer.entity.valueObject.VOCharge;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreCharge;

public class CoreCharge implements ICoreCharge {

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
	public void insertCharge(Charge charge) throws ServerException,
			ClientException {
		DAOManager daoManager = new DAOManager();
		try {

			if (daoManager.getDAOCharges().getCharge(charge.getNumber()) == null) {
				if (charge.getIsCurrencyDollar()) {
					charge.setAmountPeso(charge.getAmountDollar()
							* charge.getTypeExchange());
				}
				
				charge.setCreatedDateTimeUTC(new Date());
				daoManager.getDAOCharges().insert(charge);
			}
			else
				throw new ClientException("Ya existe un cobro con ese número de recibo");
			
			daoManager.commit();

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public void deleteCharge(int id) throws ServerException, ClientException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteBills(int[] ids) throws ServerException {
		// TODO Auto-generated method stub

	}

	@Override
	public Charge updateCharge(int id, Charge charge)
			throws ServerException, ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Charge getCharge(int id) throws ServerException, ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Charge> getCharges() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}
}
