package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.EstimateSalarySummary;
import servicelayer.service.ServiceWebStub.GetAllVersionsSalarySummary;
import servicelayer.service.ServiceWebStub.GetEmployees;
import servicelayer.service.ServiceWebStub.GetSalarySummaryByVersion;
import servicelayer.service.ServiceWebStub.InsertEmployed;
import servicelayer.service.ServiceWebStub.VOEmployed;
import servicelayer.service.ServiceWebStub.VOSalarySummary;
import servicelayer.service.ServiceWebStub.DeleteEmployed;
import servicelayer.service.ServiceWebStub.UpdatedEmployed;
import utils.PopupWindow;
import views.employees.DeleteEmployeeView;
import entities.Employee;
import entities.User;

public class EmployeeController {

	public static Collection<Employee> GetEmployees()
	{
		Collection<Employee> employees = new ArrayList<Employee>();
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetEmployees getEmployees = new GetEmployees();
			
			VOEmployed [] voEmployees = service.getEmployees(getEmployees).get_return();
			
			if(voEmployees != null){
				for(VOEmployed voEmployee : voEmployees)
				{
					Employee employee = new Employee(voEmployee);
					employees.add(employee);
				}				
			}
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return employees;
	}

	public static boolean createEmployee(VOEmployed newEmployee){
		boolean result = false;
		
		try{
			ServiceWebStub service = new ServiceWebStub();
			InsertEmployed createEmployee = new InsertEmployed();
			
			createEmployee.setVoEmployed(newEmployee);
			
			result = service.insertEmployed(createEmployee).get_return();
			
		}catch(AxisFault e){
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static VOSalarySummary estimateEmployee(VOSalarySummary voSalarySummary){
			
		VOSalarySummary result = null;
		
		try{
			ServiceWebStub service = new ServiceWebStub();
			EstimateSalarySummary estimateSalarySummary = new EstimateSalarySummary();
			
			estimateSalarySummary.setVoSalarySummary(voSalarySummary);			
			result = service.estimateSalarySummary(estimateSalarySummary).get_return();
						
		}catch(AxisFault e){
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static boolean DeleteEmployee(int id){
		boolean result = false;
		try{
			ServiceWebStub service = new ServiceWebStub();
			DeleteEmployed deleteEmployee = new DeleteEmployed();
			
			deleteEmployee.setId(id);
			
			result = service.deleteEmployed(deleteEmployee).get_return();
			
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static boolean UpdateEmployee (int id, VOEmployed voEmployee){
		boolean result = false;
		try{
			ServiceWebStub service = new ServiceWebStub();
			UpdatedEmployed updateEmployee = new UpdatedEmployed();
						
			updateEmployee.setId(id);
			updateEmployee.setVoEmployed(voEmployee);
			
			result = service.updatedEmployed(updateEmployee).get_return();
			
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static int[] GetVersions (int idEmployee){
		int[] result = null;
		try{
			ServiceWebStub service = new ServiceWebStub();
			GetAllVersionsSalarySummary version = new GetAllVersionsSalarySummary();
			
			version.setEmployedId(idEmployee);
			result = service.getAllVersionsSalarySummary(version).get_return();			
		}catch (AxisFault e) {
			// TODO Auto-generated catch block
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;	
	}
	
	public static VOSalarySummary GetVersionEmployee(int idEmployee, int idVersion){
		VOSalarySummary aux = null;
		
		try{
			ServiceWebStub service = new ServiceWebStub();
			GetSalarySummaryByVersion salarySummary = new GetSalarySummaryByVersion();
			
			salarySummary.setEmployedId(idEmployee);
			salarySummary.setVersion(idVersion);
			
			aux = service.getSalarySummaryByVersion(salarySummary).get_return();
		}catch (AxisFault e) {
			// TODO Auto-generated catch block
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return aux;	
		
		
	}
}
