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
	public void insertCharge(VOCharge voCharge) throws ServerException,
			ClientException {
		DAOManager daoManager = new DAOManager();
		try {

			if (daoManager.getDAOCharges().getCharge(voCharge.getNumber()) == null) {
				if (voCharge.getIsCurrencyDollar()) {
					voCharge.setAmountPeso(voCharge.getAmountDollar()
							* voCharge.getTypeExchange());
				}
				
				Charge charge = new Charge(voCharge);
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
	public VOCharge updateCharge(int id, VOCharge voCharge)
			throws ServerException, ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VOCharge getCharge(int id) throws ServerException, ClientException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<VOCharge> getCharges() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

}
