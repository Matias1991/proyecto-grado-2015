package servicelayer.core;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.annotation.processing.RoundEnvironment;

import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.Charge;
import servicelayer.entity.businessEntity.User;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreCharge;
import datalayer.daos.DAOManager;

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
	public ArrayList<Charge> getCharges(User userContext) throws ServerException {
		
		DAOManager daoManager = new DAOManager();
		try {
			return daoManager.getDAOCharges().getCharges(userContext);

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
			boolean isProjectClosed, User userContext) throws ServerException {
		
		DAOManager daoManager = new DAOManager();
		try {
			return daoManager.getDAOCharges().getCharges(isBillLiquidated, isProjectClosed, userContext);

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
			amoutCharged = amoutCharged - currentCharge.getAmount();
		}
		
		double amountCurrentCharge = 0;
		
		if(bill.getIsCurrencyDollar())
		{
			amountCurrentCharge += charge.getAmount();	
			
			if((amoutCharged + amountCurrentCharge) >  roundDouble(bill.getTotalAmountDollar()))
				throw new ClientException("Este cobro no se puede emitir, supera el monto de la factura");
		}
		else 
		{
			amountCurrentCharge += charge.getAmount();
			if((amoutCharged + amountCurrentCharge) > roundDouble(bill.getTotalAmountPeso()))
				throw new ClientException("Este cobro no se puede emitir, supera el monto de la factura");
		}
	}
	
	//Redondea a dos decimales
	private double roundDouble(double value){
		BigDecimal aux = new BigDecimal(value);
		aux = aux.setScale(2,RoundingMode.HALF_UP);
		return aux.doubleValue();
	}
}
