package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.axis2.AxisFault;

import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.DeleteUser;
import servicelayer.service.ServiceWebStub.GetUsers;
import servicelayer.service.ServiceWebStub.VOUser;
import entities.User;

public class UserController {

	public static Collection<User> GetUsers()
	{
		Collection<User> users = new ArrayList<User>();
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetUsers getUsers = new GetUsers();
			
			VOUser [] voUsers = service.getUsers(getUsers).get_return();

			for(VOUser voUser : voUsers)
			{
				User user = new User(voUser);
				users.add(user);
			}
			
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			Notification.show("Error: ", e.getMessage(), Type.ERROR_MESSAGE);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return users;
	}
	
	public static boolean DeleteUser(int id)
	{
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			DeleteUser deleteUser = new DeleteUser();
			
			deleteUser.setId(id);
			
			result = service.deleteUser(deleteUser).get_return();
			
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
}
