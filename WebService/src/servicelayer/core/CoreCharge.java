package servicelayer.core;

import java.util.ArrayList;
import java.util.Date;
import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.Charge;
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
					charge.setAmountPeso(charge.getAmountDollar() * charge.getTypeExchange());
				}
				
				chargeAmountExceedsBillAmountAndThrowException(charge, false);
				
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
	public Charge updateCharge(int id, Charge charge)
			throws ServerException, ClientException {
		
		DAOManager daoManager = new DAOManager();
		try {
			
			if (charge.getIsCurrencyDollar()) {
				charge.setAmountPeso(charge.getAmountDollar()
						* charge.getTypeExchange());
			}

			chargeAmountExceedsBillAmountAndThrowException(charge, true);
			
			daoManager.getDAOCharges().update(id, charge);
			daoManager.commit();

			return getCharge(id);

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public Charge getCharge(int id) throws ServerException, ClientException {
		Charge charge;

		DAOManager daoManager = new DAOManager();
		try {
			charge = daoManager.getDAOCharges().getObject(id);
			if (charge == null)
				throw new ClientException(
						"No existe ningún cobro con ese id");

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return charge;
	}

	@Override
	public ArrayList<Charge> getCharges() throws ServerException {
		
		DAOManager daoManager = new DAOManager();
		try {
			return daoManager.getDAOCharges().getObjects();

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public ArrayList<Charge> getChargesByBill(int billId)
			throws ServerException {
		
		DAOManager daoManager = new DAOManager();
		try {
			return daoManager.getDAOCharges().getChargesByBill(billId);
			
		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}
	
	@Override
	public void deleteCharges(int[] ids) throws ServerException {
		DAOManager daoManager = new DAOManager();
		try {
			if (ids.length > 0) {
				daoManager.getDAOCharges().deleteCharges(ids);
				daoManager.commit();
			}

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}
	
	@Override
	public ArrayList<Charge> getCharges(boolean isBillLiquidated,
			boolean isProjectClosed) throws ServerException {
		
		DAOManager daoManager = new DAOManager();
		try {
			return daoManager.getDAOCharges().getCharges(isBillLiquidated, isProjectClosed);

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}
	
	void chargeAmountExceedsBillAmountAndThrowException(Charge charge, boolean update) throws ServerException, ClientException
	{
		//si la factura asociada en el cobro ya no sobre pasa el valor
		Bill bill = CoreBill.GetInstance().getBill(charge.getBill().getId());
		
		double amoutCharged = CoreBill.GetInstance().getAmountChargedByBill(charge.getBill());
		if(update)
		{
			Charge currentCharge = getCharge(charge.getId());
			if(bill.getIsCurrencyDollar())
				amoutCharged = amoutCharged - currentCharge.getAmountDollar();
			else
				amoutCharged = amoutCharged - currentCharge.getAmountPeso();
		}
		
		double amountCurrentCharge = 0;
		
		if(bill.getIsCurrencyDollar())
		{
			amountCurrentCharge += charge.getAmountDollar();
			if((amoutCharged + amountCurrentCharge) > bill.getTotalAmountDollar())
				throw new ClientException("Este cobro no se puede emitir, supera el monto de la factura");
		}
		else 
		{
			amountCurrentCharge += charge.getAmountPeso();
			if((amoutCharged + amountCurrentCharge) > bill.getTotalAmountPeso())
				throw new ClientException("Este cobro no se puede emitir, supera el monto de la factura");
		}
	}
}
