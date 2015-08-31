package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.EstimateSalarySummary;
import servicelayer.service.ServiceWebStub.GetAllSalarySummaryVersion;
import servicelayer.service.ServiceWebStub.GetEmployees;
import servicelayer.service.ServiceWebStub.GetSalarySummaryByVersion;
import servicelayer.service.ServiceWebStub.InsertEmployed;
import servicelayer.service.ServiceWebStub.VOEmployed;
import servicelayer.service.ServiceWebStub.DeleteEmployed;
import servicelayer.service.ServiceWebStub.UpdatedEmployed;
import servicelayer.service.ServiceWebStub.VOSalarySummaryVersion;
import utils.PopupWindow;
import entities.EmployedHours;
import entities.Employee;
import entities.SalarySummary;
import entities.SalarySummaryVersion;

public class EmployeeController {

	public static Collection<Employee> GetEmployees() {
		Collection<Employee> employees = new ArrayList<Employee>();

		try {
			ServiceWebStub service = new ServiceWebStub();
			GetEmployees getEmployees = new GetEmployees();

			VOEmployed[] voEmployees = service.getEmployees(getEmployees)
					.get_return();

			if (voEmployees != null) {
				for (VOEmployed voEmployee : voEmployees) {
					Employee employee = new Employee(voEmployee);
					employees.add(employee);
				}
			}

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace(
					"</faultstring>", ""));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return employees;
	}

	public static boolean createEmployee(Employee newEmployee) {
		boolean result = false;

		try {
			ServiceWebStub service = new ServiceWebStub();
			InsertEmployed createEmployee = new InsertEmployed();

			createEmployee.setVoEmployed(newEmployee.toVOEmployee());

			result = service.insertEmployed(createEmployee).get_return();

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace(
					"</faultstring>", ""));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static SalarySummary estimateEmployee(SalarySummary salarySummary) {

		SalarySummary result = null;

		try {
			ServiceWebStub service = new ServiceWebStub();
			EstimateSalarySummary estimateSalarySummary = new EstimateSalarySummary();

			estimateSalarySummary.setVoSalarySummary(salarySummary
					.toVOSalarySummary());
			result = new SalarySummary(service.estimateSalarySummary(
					estimateSalarySummary).get_return());

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace(
					"</faultstring>", ""));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static boolean DeleteEmployee(int id) {
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			DeleteEmployed deleteEmployee = new DeleteEmployed();

			deleteEmployee.setId(id);

			result = service.deleteEmployed(deleteEmployee).get_return();

		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace(
					"</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static boolean UpdateEmployee(int id, Employee employee) {
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			UpdatedEmployed updateEmployee = new UpdatedEmployed();

			updateEmployee.setId(id);
			updateEmployee.setVoEmployed(employee.toVOEmployee());

			result = service.updatedEmployed(updateEmployee).get_return();

		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace(
					"</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static Collection<SalarySummaryVersion> GetVersions(int idEmployee) {
		Collection<SalarySummaryVersion> result = new ArrayList<SalarySummaryVersion>();
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetAllSalarySummaryVersion version = new GetAllSalarySummaryVersion();

			version.setEmployedId(idEmployee);
			VOSalarySummaryVersion[] aux = service.getAllSalarySummaryVersion(
					version).get_return();

			for (VOSalarySummaryVersion voSalarySummaryVersion : aux) {
				SalarySummaryVersion ssv = new SalarySummaryVersion(
						voSalarySummaryVersion);
				result.add(ssv);
			}
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace(
					"</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static SalarySummary GetVersionEmployee(int idEmployee, int idVersion) {
		SalarySummary aux = null;

		try {
			ServiceWebStub service = new ServiceWebStub();
			GetSalarySummaryByVersion salarySummary = new GetSalarySummaryByVersion();

			salarySummary.setEmployedId(idEmployee);
			salarySummary.setVersion(idVersion);

			aux = new SalarySummary(service.getSalarySummaryByVersion(
					salarySummary).get_return());
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace(
					"</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return aux;
	}

	public static Collection<Employee> GetEmployeesByType(int type) {
		Collection<Employee> employees = new ArrayList<Employee>();

		try {
			ServiceWebStub service = new ServiceWebStub();
			GetEmployees getEmployees = new GetEmployees();

			VOEmployed[] voEmployees = service.getEmployees(getEmployees)
					.get_return();

			if (voEmployees != null) {
				for (VOEmployed voEmployee : voEmployees) {
					if (voEmployee.getEmployedType() == type) {
						Employee employee = new Employee(voEmployee);
						employees.add(employee);
					}
				}
			}

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace(
					"</faultstring>", ""));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return employees;
	}

	public static Collection<EmployedHours> GetEmployedHours() {
		Collection<EmployedHours> employedHours = new ArrayList<EmployedHours>();

		try {
			ServiceWebStub service = new ServiceWebStub();
			// //GetEmployedHours getEmployedHours = new GetEmployedHours();
			//
			// VOEmployedProject [] voEmployedProject =
			// service.getEmployedHours(null).get_return();
			//
			// if(voEmployedProject != null){
			// for(VOEmployedProject voAux : voEmployedProject)
			// {
			// EmployedHours auxEmployedHours = new EmployedHours(voAux);
			// employedHours.add(auxEmployedHours);
			// }
			// }

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace(
					"</faultstring>", ""));
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return employedHours;
	}
}
