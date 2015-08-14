package servicelayer.core;

import java.util.ArrayList;

import datalayer.daos.DAOBills;
import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.valueObject.VOBill;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreBill;
import shared.interfaces.dataLayer.IDAOBills;

public class CoreBill implements ICoreBill{

	private static CoreBill instance = null;
	private IDAOBills iDAOBills;

	private CoreBill() throws ServerException {
		iDAOBills = new DAOBills();
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
		
		if(iDAOBills.getBill(voBill.getDescription(), voBill.getProjectId()) == null)
		{
			Bill bill = new Bill(voBill);
			iDAOBills.insert(bill);
		}
		else
			throw new ClientException("Ya existe una factura con esa descripcion para ese proyecto");
		
	}

	@Override
	public void deleteBill(int id) throws ServerException, ClientException {
		Bill bill = iDAOBills.getObject(id);
		if (bill == null){
			throw new ClientException("No existe una factura con ese id");
		} else {
			iDAOBills.delete(id);
		}
	}

	@Override
	public VOBill updateBill(int id, VOBill voBill) throws ServerException,
			ClientException {
		
		Bill currentBill = iDAOBills.getBill(voBill.getDescription(), voBill.getProjectId());
		if(currentBill == null || currentBill.getId() == id)
		{
			Bill bill = new Bill(voBill);
			iDAOBills.update(id, bill);
		}
		else
			throw new ClientException("Ya existe una factura con esa descripcion para ese proyecto");
		
		return getBill(id);
	}

	@Override
	public VOBill getBill(int id) throws ServerException, ClientException {
		Bill bill;
		VOBill voBill = null;
		
		bill = iDAOBills.getObject(id);
		if(bill != null){
			voBill = BuildVOBill(bill);
		} else {	
			throw new ClientException ("No existe ningúna facrura con ese id");
		}
		
		return voBill;
	}

	@Override
	public ArrayList<VOBill> getBills() throws ServerException {
		ArrayList<Bill> bills;
		ArrayList<VOBill> voBills = null;

		bills = iDAOBills.getObjects();
		voBills = new ArrayList<VOBill>();
		
		for (Bill bill : bills) {
			voBills.add(BuildVOBill(bill));
		}
		
		return voBills;
	}
	
	private VOBill BuildVOBill(Bill bill)
	{
		VOBill voBill = new VOBill();
		voBill.setId(bill.getId());
		voBill.setDescription(bill.getDescription());
		voBill.setAmount(bill.getAmount());
		voBill.setCreatedDateTimeUTC(bill.getCreatedDateTimeUTC());
		if(bill.getProject() != null)
		{
			voBill.setProjectId(bill.getProject().getId());
			voBill.setProjectName(bill.getProject().getName());
		}
		
		return voBill;
	}
}
