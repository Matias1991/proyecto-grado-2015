package servicelayer.core;

import java.util.ArrayList;
import java.util.Date;
import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.valueObject.VOBill;
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
	public void insertBill(VOBill voBill) throws ServerException,
			ClientException {

		DAOManager daoManager = new DAOManager();
		try {
			if (daoManager.getDAOBills().getBill(voBill.getCode()) == null) {
				if (voBill.getIsCurrencyDollar()) {
					voBill.setAmountPeso(voBill.getAmountDollar()
							* voBill.getTypeExchange());
				}

				Bill bill = new Bill(voBill);
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
	public VOBill updateBill(int id, VOBill voBill) throws ServerException,
			ClientException {

		DAOManager daoManager = new DAOManager();
		try {
			if (daoManager.getDAOBills().getBill(voBill.getCode()) != null) {
				if (voBill.getIsCurrencyDollar()) {
					voBill.setAmountPeso(voBill.getAmountDollar()
							* voBill.getTypeExchange());
				}
				Bill bill = new Bill(voBill);
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
	public VOBill getBill(int id) throws ServerException, ClientException {
		Bill bill;
		VOBill voBill = null;

		DAOManager daoManager = new DAOManager();
		try {
			bill = daoManager.getDAOBills().getObject(id);
			if (bill != null) {
				voBill = BuildVOBill(bill);
			} else {
				throw new ClientException(
						"No existe ningúna facrura con ese id");
			}

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return voBill;
	}

	@Override
	public ArrayList<VOBill> getBills() throws ServerException {
		ArrayList<Bill> bills;
		ArrayList<VOBill> voBills = null;

		DAOManager daoManager = new DAOManager();
		try {
			bills = daoManager.getDAOBills().getObjects();
			voBills = new ArrayList<VOBill>();

			for (Bill bill : bills) {
				voBills.add(BuildVOBill(bill));
			}

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return voBills;
	}

	@Override
	public ArrayList<VOBill> getBills(Date from, Date to, int projectId,
			String code, boolean isLiquidated) throws ServerException {
		ArrayList<Bill> bills;
		ArrayList<VOBill> voBills = null;

		DAOManager daoManager = new DAOManager();
		try {
			bills = daoManager.getDAOBills().getBills(from, to, projectId,
					code, isLiquidated);
			voBills = new ArrayList<VOBill>();

			for (Bill bill : bills) {
				voBills.add(BuildVOBill(bill));
			}

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return voBills;
	}

	private VOBill BuildVOBill(Bill bill) {
		VOBill voBill = new VOBill();
		voBill.setId(bill.getId());
		voBill.setCode(bill.getCode());
		voBill.setDescription(bill.getDescription());
		voBill.setAmountDollar(bill.getAmountDollar());
		voBill.setAmountPeso(bill.getAmountPeso());
		voBill.setIsCurrencyDollar(bill.getIsCurrencyDollar());
		voBill.setTypeExchange(bill.getTypeExchange());
		voBill.setAppliedDateTimeUTC(bill.getAppliedDateTimeUTC());
		voBill.setLiquidated(bill.getIsLiquidated());
		if (bill.getProject() != null) {
			voBill.setProjectId(bill.getProject().getId());
			voBill.setProjectName(bill.getProject().getName());
		}

		return voBill;
	}
}
