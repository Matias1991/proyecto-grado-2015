package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.GetEmployees;
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
				Employee eployee = new Employee(voEmployee);
				employees.add(eployee);
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

}
