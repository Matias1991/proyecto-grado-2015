package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.DeleteCharges;
import servicelayer.service.ServiceWebStub.GetCharges;
import servicelayer.service.ServiceWebStub.GetChargesByBill;
import servicelayer.service.ServiceWebStub.GetChargesByFilters;
import servicelayer.service.ServiceWebStub.InsertCharge;
import servicelayer.service.ServiceWebStub.UpdateCharge;
import servicelayer.service.ServiceWebStub.VOCharge;
import utils.PopupWindow;
import entities.Charge;
import entities.RequestContext;

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
	
	public static Charge updateCharge(int id, Charge charge)
	{
		Charge result = null;
		try {
			ServiceWebStub service = new ServiceWebStub();
			UpdateCharge updateCharge = new UpdateCharge();
			
			VOCharge voCharge = new VOCharge();
			voCharge.setDescription(charge.getDescription());
			voCharge.setAmountPeso(charge.getAmountPeso());
			voCharge.setAmountDollar(charge.getAmountDollar());
			voCharge.setIsCurrencyDollar(charge.getIsCurrencyDollar());
			voCharge.setTypeExchange(charge.getTypeExchange());
			voCharge.setId(charge.getId());
			voCharge.setBillId(charge.getBillId());
			
			updateCharge.setId(id);
			updateCharge.setVoCharge(voCharge);
			
			 result = new Charge(service.updateCharge(updateCharge).get_return());
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static boolean deleteCharges(int [] ids)
	{
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			DeleteCharges deleteCharges = new DeleteCharges();
			
			deleteCharges.setIds(ids);
			
			result = service.deleteCharges(deleteCharges).get_return();
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Collection<Charge> getCharges()
	{
		Collection<Charge> charges = new ArrayList<Charge>();
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetCharges getCharges = new GetCharges();
			
			getCharges.setUserContextId(RequestContext.getRequestContext().getId());
			
			VOCharge [] voCharges = service.getCharges(getCharges).get_return();

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
	
	public static Collection<Charge> getCharges(boolean isBillLiquidated, boolean isProjectClosed)
	{
		Collection<Charge> charges = new ArrayList<Charge>();
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetChargesByFilters getChargesByFilters = new GetChargesByFilters();
			
			getChargesByFilters.setIsBillLiquidated(isBillLiquidated);
			getChargesByFilters.setIsProjectClosed(isProjectClosed);
			getChargesByFilters.setUserContextId(RequestContext.getRequestContext().getId());
			
			VOCharge [] voCharges = service.getChargesByFilters(getChargesByFilters).get_return();

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
