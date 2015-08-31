package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.GetBillsWithFilters;
import servicelayer.service.ServiceWebStub.GetChargesByBill;
import servicelayer.service.ServiceWebStub.InsertCharge;
import servicelayer.service.ServiceWebStub.VOBill;
import servicelayer.service.ServiceWebStub.VOCharge;
import utils.PopupWindow;
import entities.Bill;
import entities.Charge;

public class ChargeController {
	
	public static boolean createCharge(Charge charge)
	{
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			InsertCharge createCharge = new InsertCharge();
			
			VOCharge voCharge = new VOCharge();
			voCharge.setDescription(charge.getDescription());
			voCharge.setNumber(charge.getNumber());
			voCharge.setAmountPeso(charge.getAmountPeso());
			voCharge.setAmountDollar(charge.getAmountDollar());
			voCharge.setIsCurrencyDollar(charge.getIsCurrencyDollar());
			voCharge.setTypeExchange(charge.getTypeExchange());
			voCharge.setCreatedDateTimeUTC(charge.getCreatedDateTimeUTC());
			voCharge.setBillId(charge.getBillId());
			
			createCharge.setVoCharge(voCharge);
			
			result = service.insertCharge(createCharge).get_return();
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Collection<Charge> getChargesByBill(int billId)
	{
		Collection<Charge> charges = new ArrayList<Charge>();
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetChargesByBill getChargesByBill = new GetChargesByBill();
			
			getChargesByBill.setBillId(billId);
			
			VOCharge [] voCharges = service.getChargesByBill(getChargesByBill).get_return();

			if(voCharges != null)
			{
				for(VOCharge voCharge : voCharges)
				{
					Charge charge = new Charge(voCharge);
					charges.add(charge);
				}
			}
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return charges;
	}
}
