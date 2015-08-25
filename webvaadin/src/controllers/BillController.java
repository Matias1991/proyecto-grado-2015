package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.GetBills;
import servicelayer.service.ServiceWebStub.GetBillsWithFilters;
import servicelayer.service.ServiceWebStub.GetCategories;
import servicelayer.service.ServiceWebStub.InsertBill;
import servicelayer.service.ServiceWebStub.UpdateBill;
import servicelayer.service.ServiceWebStub.VOBill;
import servicelayer.service.ServiceWebStub.VOCategory;
import utils.PopupWindow;
import entities.Bill;
import entities.Category;

public class BillController {

	public static boolean createBill(Bill bill)
	{
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			InsertBill createBill = new InsertBill();
			
			VOBill voBill = new VOBill();
			voBill.setDescription(bill.getDescription());
			voBill.setCode(bill.getCode());
			voBill.setAmountPeso(bill.getAmountPeso());
			voBill.setAmountDollar(bill.getAmountDollar());
			voBill.setIsCurrencyDollar(bill.getIsCurrencyDollar());
			voBill.setTypeExchange(bill.getTypeExchange());
			voBill.setAppliedDateTimeUTC(bill.getAppliedDateTimeUTC());
			voBill.setProjectId(bill.getProjectId());
			
			createBill.setVoBill(voBill);
			
			result = service.insertBill(createBill).get_return();
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Bill updateBill(int id, Bill bill)
	{
		Bill result = null;
		try {
			ServiceWebStub service = new ServiceWebStub();
			UpdateBill updateBill = new UpdateBill();
			
			VOBill voBill = new VOBill();
			voBill.setDescription(bill.getDescription());
			voBill.setCode(bill.getCode());
			voBill.setAmountPeso(bill.getAmountPeso());
			voBill.setAmountDollar(bill.getAmountDollar());
			voBill.setIsCurrencyDollar(bill.getIsCurrencyDollar());
			voBill.setTypeExchange(bill.getTypeExchange());
			voBill.setAppliedDateTimeUTC(bill.getAppliedDateTimeUTC());
			voBill.setProjectId(bill.getProjectId());
			
			updateBill.setId(id);
			updateBill.setVoBill(voBill);
			
			 result = new Bill(service.updateBill(updateBill).get_return());
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Collection<Bill> getBills(Date from, Date to, int projectId, String code, boolean isLiquidated)
	{
		Collection<Bill> bills = new ArrayList<Bill>();
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetBillsWithFilters getBillsWithFilters = new GetBillsWithFilters();
			
			getBillsWithFilters.setFrom(from);
			getBillsWithFilters.setTo(to);
			getBillsWithFilters.setProjectId(projectId);
			getBillsWithFilters.setCode(code);
			getBillsWithFilters.setIsLiquidated(isLiquidated);
			
			VOBill [] voBills = service.getBillsWithFilters(getBillsWithFilters).get_return();

			if(voBills != null)
			{
				for(VOBill voBill : voBills)
				{
					Bill bill = new Bill(voBill);
					bills.add(bill);
				}
			}
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return bills;
	}
}
