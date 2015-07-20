package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.GetEmployees;
import servicelayer.service.ServiceWebStub.InsertEmployed;
import servicelayer.service.ServiceWebStub.VOEmployed;
import utils.PopupWindow;
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

			for(VOEmployed voEmployee : voEmployees)
			{
				Employee employee = new Employee(voEmployee);
				employees.add(employee);
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
	
}
