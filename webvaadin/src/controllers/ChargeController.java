package controllers;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.InsertCharge;
import servicelayer.service.ServiceWebStub.VOCharge;
import utils.PopupWindow;
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
}
