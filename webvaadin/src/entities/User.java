package entities;

import servicelayer.service.ServiceWebStub.VOUser;

public class User {

	private int id;
	private String name;
	private String lastName;
	private String email;
	private String userType;
	private String userName;
	private int userSatus;
	
	public User(VOUser voUser)
	{
		this.name = voUser.getName();
		this.id = voUser.getId();
		this.lastName = voUser.getLastName();
		this.email = voUser.getEmail();
		this.userType = getUserType(voUser.getUserType());
		this.userName = voUser.getUserName();
		this.userSatus = voUser.getUserStatus();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public int getUserStatus(){
		return this.userSatus;
	}
	
	public void setUserStatus(int status){
		this.userSatus = status;
	}
	
	public String getUserStatusToShow(){
		String userStatusToShow = "";
		if(userSatus == 1){
			userStatusToShow = "Activo";
		} else {
			userStatusToShow = "Bloqueado";
		}
		return userStatusToShow;
	}
	
	
	public String getUserType(int userTypeId)
	{
		String result = "";
		switch(userTypeId)
		{
		   case 1: 
			   	 result = "Administrador";
		         break;
		   case 2: 
			   	 result = "Socio";
		         break;
		   case 3: 
			   	 result = "Gerente";
		         break;
		    default:
		    	result = "No definido";
		          break;
		}
		
		return result;
	}
}
