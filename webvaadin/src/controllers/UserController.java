package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.axis2.AxisFault;

import com.vaadin.server.Page;
import com.vaadin.ui.Notification;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.ChangePassword;
import servicelayer.service.ServiceWebStub.DeleteUser;
import servicelayer.service.ServiceWebStub.GetUser;
import servicelayer.service.ServiceWebStub.GetUsers;
import servicelayer.service.ServiceWebStub.InsertUser;
import servicelayer.service.ServiceWebStub.UpdateUser;
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
			String error = e.getMessage().replace("<faultstring>", "");
			Notification notif = new Notification(error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
			notif.setDelayMsec(2000);
			notif.show(Page.getCurrent());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			String error = e.getMessage().replace("<faultstring>", "");
			Notification notif = new Notification(error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
			notif.setDelayMsec(2000);
			notif.show(Page.getCurrent());
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
			String error = e.getMessage().replace("<faultstring>", "");
			Notification notif = new Notification(error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
			notif.setDelayMsec(2000);
			notif.show(Page.getCurrent());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			String error = e.getMessage().replace("<faultstring>", "");
			Notification notif = new Notification(error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
			notif.setDelayMsec(2000);
			notif.show(Page.getCurrent());
		}
		
		return result;
	}
	
	public static boolean createUser(VOUser newUser)
	{
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			InsertUser createUser = new InsertUser();
			
			createUser.setVoUser(newUser);
			
			result = service.insertUser(createUser).get_return();
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			Notification notif = new Notification(error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
			notif.setDelayMsec(2000);
			notif.show(Page.getCurrent());
		} catch (RemoteException e) {
			String error = e.getMessage().replace("<faultstring>", "");
			Notification notif = new Notification (error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
			notif.setDelayMsec(2000);
			notif.show(Page.getCurrent());
		}
		
		return result;
	}
	
	public static VOUser modifyUser(VOUser newUser, int idUser)
	{
		VOUser result = new VOUser();
		try {
			ServiceWebStub service = new ServiceWebStub();
			UpdateUser modifyUser = new UpdateUser();
			
			modifyUser.setVoUser(newUser);
			modifyUser.setId(idUser);
			
			result = service.updateUser(modifyUser).get_return();
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			Notification notif = new Notification(error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
			notif.setDelayMsec(2000);
			notif.show(Page.getCurrent());
		} catch (RemoteException e) {
			String error = e.getMessage().replace("<faultstring>", "");
			Notification notif = new Notification (error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
			notif.setDelayMsec(2000);
			notif.show(Page.getCurrent());
		}
		
		return result;
	}
	
	public static boolean changePassword(int idUser, String oldPassword, String newPassword)
	{
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			ChangePassword changePassword = new ChangePassword();
			
			changePassword.setId(idUser);
			changePassword.setOldPassword(oldPassword);
			changePassword.setNewPassword(newPassword);
			
			result = service.changePassword(changePassword).get_return();
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			Notification notif = new Notification(error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
			notif.setDelayMsec(2000);
			notif.show(Page.getCurrent());
		} catch (RemoteException e) {
			String error = e.getMessage().replace("<faultstring>", "");
			Notification notif = new Notification (error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
			notif.setDelayMsec(2000);
			notif.show(Page.getCurrent());
		}
		
		return result;
	}
	
}
