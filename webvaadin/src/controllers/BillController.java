package controllers;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.InsertBill;
import servicelayer.service.ServiceWebStub.VOBill;
import utils.PopupWindow;
import entities.Bill;

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
			voBill.setAmount(bill.getAmount());
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
}
