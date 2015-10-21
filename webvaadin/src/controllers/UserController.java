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
import servicelayer.service.ServiceWebStub.GetUsersByStatus;
import servicelayer.service.ServiceWebStub.InsertUser;
import servicelayer.service.ServiceWebStub.Login;
import servicelayer.service.ServiceWebStub.ResetPassword;
import servicelayer.service.ServiceWebStub.UnlockUser;
import servicelayer.service.ServiceWebStub.UpdateUser;
import servicelayer.service.ServiceWebStub.VOUser;
import utils.PopupWindow;
import entities.User;

public class UserController {

	public static Collection<User> getUsers()
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
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return users;
	}

	public static boolean deleteUser(int id)
	{
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			DeleteUser deleteUser = new DeleteUser();
			
			deleteUser.setId(id);
			
			result = service.deleteUser(deleteUser).get_return();
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static boolean createUser(User user)
	{
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			InsertUser createUser = new InsertUser();
			
			VOUser newUser = new VOUser();
			newUser.setUserName(user.getUserName());
			newUser.setName(user.getName());
			newUser.setLastName(user.getLastName());
			newUser.setEmail(user.getEmail());
			newUser.setUserType(user.getUserTypeId());
			
			createUser.setVoUser(newUser);
			
			result = service.insertUser(createUser).get_return();
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void modifyUser(User user, int idUser)
	{
		try {
			ServiceWebStub service = new ServiceWebStub();
			UpdateUser modifyUser = new UpdateUser();
			
			VOUser voUser = new VOUser();
			voUser.setEmail(user.getEmail());
			voUser.setLastName(user.getLastName());
			voUser.setName(user.getName());
			voUser.setUserType(user.getUserTypeId());
			
			modifyUser.setVoUser(voUser);
			modifyUser.setId(idUser);
			
			service.updateUser(modifyUser).get_return();
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
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
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
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
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;		
	}		

	public static VOUser loginUser(String username, String password){
		VOUser result = null;
	
		try{
			ServiceWebStub service = new ServiceWebStub();
			
			Login login = new Login();
			login.setUserName(username);
			login.setPassword(password);
			
			result = service.login(login).get_return();
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
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
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
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
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;		
	}		

	public static Collection<User> getUsersByStatus(int status){
		Collection<User> users = new ArrayList<User>();
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetUsersByStatus getUsers = new GetUsersByStatus();
			getUsers.setUserStatusId(status);
			
			VOUser [] voUsers = service.getUsersByStatus(getUsers).get_return();

			if(voUsers != null){
				for(VOUser voUser : voUsers){
					User user = new User(voUser);
					users.add(user);
				}
			}
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return users;
	}
	
	public static Collection<User> getUsersByStatusAndType(int status, int type){

		return null;		
	}
	
	public static Collection<User> getUsersByType(int type){
		Collection<User> users = new ArrayList<User>();
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetUsersByStatus getUsers = new GetUsersByStatus();
			getUsers.setUserStatusId(1);
						
			VOUser [] voUsers = service.getUsersByStatus(getUsers).get_return();

			if(voUsers != null){
				for(VOUser voUser : voUsers){
					if(voUser.getUserType() == type){
						User user = new User(voUser);
						users.add(user);
					}
				}
			}
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	
		return users;		
	}
}
