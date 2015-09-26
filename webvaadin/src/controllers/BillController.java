package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.DeleteBills;
import servicelayer.service.ServiceWebStub.GetAllBillsByFilters;
import servicelayer.service.ServiceWebStub.GetBills;
import servicelayer.service.ServiceWebStub.GetBillsByFilters;
import servicelayer.service.ServiceWebStub.GetBillsByFiltersWithCharges;
import servicelayer.service.ServiceWebStub.GetBillsByProject;
import servicelayer.service.ServiceWebStub.InsertBill;
import servicelayer.service.ServiceWebStub.UpdateBill;
import servicelayer.service.ServiceWebStub.VOBill;
import utils.PopupWindow;
import entities.Bill;
import entities.RequestContext;

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
			voBill.setIvaType(bill.getIvaType());
			
			createBill.setVoBill(voBill);
			
			result = service.insertBill(createBill).get_return();
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
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
			voBill.setId(bill.getId());
			voBill.setDescription(bill.getDescription());
			voBill.setCode(bill.getCode());
			voBill.setAmountPeso(bill.getAmountPeso());
			voBill.setAmountDollar(bill.getAmountDollar());
			voBill.setIsCurrencyDollar(bill.getIsCurrencyDollar());
			voBill.setTypeExchange(bill.getTypeExchange());
			voBill.setAppliedDateTimeUTC(bill.getAppliedDateTimeUTC());
			voBill.setProjectId(bill.getProjectId());
			voBill.setIvaType(bill.getIvaType());
			
			updateBill.setId(id);
			updateBill.setVoBill(voBill);
			
			 result = new Bill(service.updateBill(updateBill).get_return());
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Collection<Bill> getBills(int projectId)
	{
		Collection<Bill> bills = new ArrayList<Bill>();
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetBillsByProject getBillsByProject = new GetBillsByProject();
			
			getBillsByProject.setProjectId(projectId);
			
			VOBill [] voBills = service.getBillsByProject(getBillsByProject).get_return();

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
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return bills;
	}
	
	public static Collection<Bill> getAllBillsByFilters(Date from, Date to, boolean isLiquidated)
	{
		Collection<Bill> bills = new ArrayList<Bill>();
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetAllBillsByFilters getAllBillsByFilters = new GetAllBillsByFilters();
			
			getAllBillsByFilters.setFrom(from);
			getAllBillsByFilters.setTo(to);
			getAllBillsByFilters.setIsLiquidated(isLiquidated);
			getAllBillsByFilters.setUserContextId(RequestContext.getRequestContext().getId());
			
			VOBill [] voBills = service.getAllBillsByFilters(getAllBillsByFilters).get_return();

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
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return bills;
	}
	
	public static Collection<Bill> getBillsByFiltersAndActiveProjects(Date from, Date to, boolean isLiquidated, boolean withCharges)
	{
		Collection<Bill> bills = new ArrayList<Bill>();
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetBillsByFilters getBillsByFilters = new GetBillsByFilters();
			
			getBillsByFilters.setFrom(from);
			getBillsByFilters.setTo(to);
			getBillsByFilters.setIsLiquidated(isLiquidated);
			getBillsByFilters.setWithCharges(withCharges);
			getBillsByFilters.setUserContextId(RequestContext.getRequestContext().getId());
			
			VOBill [] voBills = service.getBillsByFilters(getBillsByFilters).get_return();

			if(voBills != null)
			{
				for(VOBill voBill : voBills)
				{
					if(voBill.getProjectClosed())
						continue;
					
					Bill bill = new Bill(voBill);
					bills.add(bill);
				}
			}
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return bills;
	}
	
	public static Collection<Bill> getBillsByFiltersAndActiveProjects(Date from, Date to)
	{
		Collection<Bill> bills = new ArrayList<Bill>();
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetBills getBills = new GetBills();
			
			getBills.setFrom(from);
			getBills.setTo(to);
			getBills.setUserContextId(RequestContext.getRequestContext().getId());
			
			VOBill [] voBills = service.getBills(getBills).get_return();

			if(voBills != null)
			{
				for(VOBill voBill : voBills)
				{
					if(voBill.getProjectClosed())
						continue;
					
					Bill bill = new Bill(voBill);
					bills.add(bill);
				}
			}
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return bills;
	}
	
	public static Collection<Bill> getBillsByFiltersWithChargesAndActiveProjects(Date from, Date to)
	{
		Collection<Bill> bills = new ArrayList<Bill>();
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetBillsByFiltersWithCharges getBillsByFiltersWithCharges = new GetBillsByFiltersWithCharges();
			
			getBillsByFiltersWithCharges.setFrom(from);
			getBillsByFiltersWithCharges.setTo(to);
			getBillsByFiltersWithCharges.setUserContextId(RequestContext.getRequestContext().getId());
			
			VOBill [] voBills = service.getBillsByFiltersWithCharges(getBillsByFiltersWithCharges).get_return();

			if(voBills != null)
			{
				for(VOBill voBill : voBills)
				{
					if(voBill.getProjectClosed())
						continue;
					
					Bill bill = new Bill(voBill);
					bills.add(bill);
				}
			}
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return bills;
	}
	
	public static boolean deleteBills(int [] ids)
	{
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			DeleteBills deleteBills = new DeleteBills();
			
			deleteBills.setIds(ids);
			
			result = service.deleteBills(deleteBills).get_return();
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
