package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.ChangePassword;
import servicelayer.service.ServiceWebStub.DeleteUser;
import servicelayer.service.ServiceWebStub.ForgotPassword;
import servicelayer.service.ServiceWebStub.GetUsers;
import servicelayer.service.ServiceWebStub.InsertUser;
import servicelayer.service.ServiceWebStub.Login;
import servicelayer.service.ServiceWebStub.ResetPassword;
import servicelayer.service.ServiceWebStub.UnlockUser;
import servicelayer.service.ServiceWebStub.UpdateUser;
import servicelayer.service.ServiceWebStub.VOUser;
import utils.PopupWindow;
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
//			Notification notif = new Notification(error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
//			notif.setDelayMsec(2000);
//			notif.show(Page.getCurrent());
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
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
			String error = e.getMessage().replace("<faultstring>", "");
//			Notification notif = new Notification(error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
//			notif.setDelayMsec(2000);
//			notif.show(Page.getCurrent());
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
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
//			Notification notif = new Notification(error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
//			notif.setDelayMsec(2000);
//			notif.show(Page.getCurrent());
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
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
//			Notification notif = new Notification(error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
//			notif.setDelayMsec(2000);
//			notif.show(Page.getCurrent());
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
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
//			Notification notif = new Notification(error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
//			notif.setDelayMsec(2000);
//			notif.show(Page.getCurrent());
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static boolean forgotPassword(String email){
		boolean result = false;
		try{
			ServiceWebStub service = new ServiceWebStub();
			ForgotPassword forgotPassword = new ForgotPassword();
			
			forgotPassword.setUserEmail(email);
			
			result = service.forgotPassword(forgotPassword).get_return();
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
//			Notification notif = new Notification(error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
//			notif.setDelayMsec(2000);
//			notif.show(Page.getCurrent());
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;		
	}		

	public static VOUser loginUser(String username, String password){
		VOUser result = new VOUser();
	
		try{
			ServiceWebStub service = new ServiceWebStub();
			
			Login login = new Login();
			login.setUserName(username);
			login.setPassword(password);
			
			result = service.login(login).get_return();
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
//			Notification notif = new Notification(error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
//			notif.setDelayMsec(2000);
//			notif.show(Page.getCurrent());
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static boolean unlockUser(int id){
		boolean result = false;
		try{
			ServiceWebStub service = new ServiceWebStub();
			UnlockUser unlockUser = new UnlockUser();
			
			unlockUser.setId(id);
			
			result = service.unlockUser(unlockUser).get_return();
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
//			Notification notif = new Notification(error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
//			notif.setDelayMsec(2000);
//			notif.show(Page.getCurrent());
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;		
	}		

	public static boolean resetPassword(int id){
		boolean result = false;
		try{
			ServiceWebStub service = new ServiceWebStub();
			ResetPassword resetPassword = new ResetPassword();
			
			resetPassword.setId(id);
			
			result = service.resetPassword(resetPassword).get_return();
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
//			Notification notif = new Notification(error.replace("</faultstring>", ""), Notification.TYPE_ERROR_MESSAGE);
//			notif.setDelayMsec(2000);
//			notif.show(Page.getCurrent());
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;		
	}		

}
