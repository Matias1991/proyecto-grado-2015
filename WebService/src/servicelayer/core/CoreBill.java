package servicelayer.core;

import java.util.ArrayList;
import java.util.Date;
import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.Charge;
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
			if (daoManager.getDAOBills().getBill(bill.getCode()) == null) {
				if (bill.getIsCurrencyDollar()) {
					bill.setAmountPeso(bill.getAmountDollar()
							* bill.getTypeExchange());
				}
				daoManager.getDAOBills().insert(bill);
				daoManager.commit();
			} else
				throw new ClientException(
						"Ya existe una factura con ese codigo");

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
			if (daoManager.getDAOBills().getBill(bill.getCode()) != null) {
				if (bill.getIsCurrencyDollar()) {
					bill.setAmountPeso(bill.getAmountDollar()
							* bill.getTypeExchange());
				}

				daoManager.getDAOBills().update(id, bill);
				daoManager.commit();
			} else
				throw new ClientException(
						"Ya existe una factura con ese codigo");

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
	public ArrayList<Bill> getBills(Date from, Date to, int projectId,
			String code, boolean isLiquidated) throws ServerException {
		ArrayList<Bill> bills;

		DAOManager daoManager = new DAOManager();
		try {
			bills = daoManager.getDAOBills().getBills(from, to, projectId,
					code, isLiquidated);

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return bills;
	}

	// retorna el monto cobrado de esa factura
	public double getAmountChargedByBill(int billId) throws ServerException {
		double amountCharged = 0;
		DAOManager daoManager = new DAOManager();
		try {
			Bill bill = daoManager.getDAOBills().getObject(billId);

			ArrayList<Charge> charges = null;// CoreCharge.GetInstance().getChargesByBill(billId);

			// obtiene todos los cobros para esa factura y suma en monto de los
			// cobros en dolares
			for (Charge charge : charges) {
				if (bill.getIsCurrencyDollar())
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
}
