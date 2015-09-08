package servicelayer.core;

import java.util.ArrayList;
import java.util.Date;

import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.Charge;
import servicelayer.entity.businessEntity.IVA_Type;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreBill;

public class CoreBill implements ICoreBill {

	private static CoreBill instance = null;

	private CoreBill() {
	}

	public static CoreBill GetInstance() throws ServerException {
		if (instance == null) {
			instance = new CoreBill();
		}
		return instance;
	}

	@Override
	public void insertBill(Bill bill) throws ServerException, ClientException {

		DAOManager daoManager = new DAOManager();
		try {
			if (bill.getIsCurrencyDollar()) {
				bill.setAmountPeso(bill.getAmountDollar()
						* bill.getTypeExchange());
			}
			daoManager.getDAOBills().insert(bill);
			daoManager.commit();

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public void deleteBill(int id) throws ServerException, ClientException {

		DAOManager daoManager = new DAOManager();
		try {
			Bill bill = daoManager.getDAOBills().getObject(id);
			if (bill == null) {
				throw new ClientException("No existe una factura con ese id");
			} else {
				daoManager.getDAOBills().delete(id);
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
	public void deleteBills(int[] ids) throws ServerException {
		DAOManager daoManager = new DAOManager();
		try {
			if (ids.length > 0) {
				daoManager.getDAOBills().deleteBills(ids);
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
	public Bill updateBill(int id, Bill bill) throws ServerException,
			ClientException {

		DAOManager daoManager = new DAOManager();
		try {
			if (bill.getIsCurrencyDollar()) {
				bill.setAmountPeso(bill.getAmountDollar()
						* bill.getTypeExchange());
			}

			daoManager.getDAOBills().update(id, bill);
			daoManager.commit();

			return getBill(id);

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public Bill getBill(int id) throws ServerException, ClientException {
		Bill bill;

		DAOManager daoManager = new DAOManager();
		try {
			bill = daoManager.getDAOBills().getObject(id);
			
			if (bill == null)
				throw new ClientException(
						"No existe ningúna facrura con ese id");
			else
			{
				if (bill.getIsCurrencyDollar())
					bill.setTotalAmountDollar(getTotalAmount(bill.getAmountDollar(), bill.getIvaType()));
				else
					bill.setTotalAmountPeso(getTotalAmount(bill.getAmountPeso(), bill.getIvaType()));
			}
			
		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return bill;
	}

	@Override
	public ArrayList<Bill> getBills() throws ServerException {
		ArrayList<Bill> bills;

		DAOManager daoManager = new DAOManager();
		try {
			bills = daoManager.getDAOBills().getObjects();

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return bills;
	}

	@Override
	public ArrayList<Bill> getBills(Date from, Date to, boolean isLiquidated)
			throws ServerException {
		ArrayList<Bill> bills;

		DAOManager daoManager = new DAOManager();
		try {
			bills = daoManager.getDAOBills().getBills(from, to, isLiquidated);

			for (Bill bill : bills) {
				buildTotalAmountAndTotalCharged(bill);
			}

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return bills;
	}

	@Override
	public ArrayList<Bill> getBills(Date from, Date to, boolean isLiquidated,
			boolean withCharges) throws ServerException {
		ArrayList<Bill> bills;

		DAOManager daoManager = new DAOManager();
		try {
			bills = daoManager.getDAOBills().getBills(from, to, isLiquidated,
					withCharges);

			if(withCharges)
			{
				for (Bill bill : bills) {
					if (bill.getIsCurrencyDollar())
						bill.setAmountChargedDollar(getAmountChargedByBill(bill));
					else
						bill.setAmountChargedPeso(getAmountChargedByBill(bill));
				}
			}
			else
			{
				for (Bill bill : bills) {
					if (bill.getIsCurrencyDollar())
						bill.setTotalAmountDollar(getTotalAmount(bill.getAmountDollar(), bill.getIvaType()));
					else
						bill.setTotalAmountPeso(getTotalAmount(bill.getAmountPeso(), bill.getIvaType()));
				}
			}

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return bills;
	}

	@Override
	public ArrayList<Bill> getBills(Date from, Date to) throws ServerException {
		ArrayList<Bill> bills;

		DAOManager daoManager = new DAOManager();
		try {
			bills = daoManager.getDAOBills().getBills(from, to);

			for (Bill bill : bills) {
				buildTotalAmountAndTotalCharged(bill);
			}
			
		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return bills;
	}

	@Override
	public ArrayList<Bill> getBillsWithCharges(Date from, Date to)
			throws ServerException {
		ArrayList<Bill> bills;

		DAOManager daoManager = new DAOManager();
		try {
			bills = daoManager.getDAOBills().getBillsWithCharges(from, to);

			for (Bill bill : bills) {
				buildTotalAmountAndTotalCharged(bill);
			}

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return bills;
	}

	@Override
	public ArrayList<Bill> getBills(int projectId) throws ServerException {
		ArrayList<Bill> bills;

		DAOManager daoManager = new DAOManager();
		try {
			bills = daoManager.getDAOBills().getBills(projectId);

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return bills;
	}

	double getTotalAmount(double amount, IVA_Type ivaType)
	{
		double totalAmount = amount;
		if(ivaType == IVA_Type.TEN || ivaType == IVA_Type.TWENTY_TWO)
		{
			totalAmount = amount * ivaType.getPercentage();
		}
		
		return totalAmount;
	}
	
	// retorna el monto cobrado de esa factura
	double getAmountChargedByBill(Bill bill) throws ServerException {
		double amountCharged = 0;
		DAOManager daoManager = new DAOManager();
		try {

			bill.setIDAOCharges(daoManager.getDAOCharges());

			// obtener los cobros de la factura
			ArrayList<Charge> charges = bill.getCharges();

			// obtiene todos los cobros para esa factura y suma en monto de los
			// cobros en dolares
			for (Charge charge : charges) {
				if (charge.getIsCurrencyDollar())
					amountCharged += charge.getAmountDollar();
				else
					amountCharged += charge.getAmountPeso();
			}

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}

		return amountCharged;
	}
	
	//Carga el monto total de la factura con iva incl.
	//Carga el monto total cobrado de la factura
	void buildTotalAmountAndTotalCharged(Bill bill) throws ServerException
	{
		if (bill.getIsCurrencyDollar())
		{
			bill.setAmountChargedDollar(getAmountChargedByBill(bill));
			bill.setTotalAmountDollar(getTotalAmount(bill.getAmountDollar(), bill.getIvaType()));
		}
		else
		{
			bill.setAmountChargedPeso(getAmountChargedByBill(bill));
			bill.setTotalAmountPeso(getTotalAmount(bill.getAmountPeso(), bill.getIvaType()));
		}
	}
}
