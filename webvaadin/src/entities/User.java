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
	private int userTypeId;
	private String password;
	
	public User()
	{
		
	}
	
	public User(VOUser voUser)
	{
		if(voUser != null){
			this.name = voUser.getName();
			this.id = voUser.getId();
			this.lastName = voUser.getLastName();
			this.email = voUser.getEmail();
			this.userType = getUserType(voUser.getUserType());
			this.userName = voUser.getUserName();
			this.userSatus = voUser.getUserStatus();
			this.password = voUser.getPassword();		
		}
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
	
	public String getPassword(){
		return this.password;
	}
	
	public void setPassword (String password){
		this.password = password;
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
	
	public int getUserTypeId(String userType){
		int result = 0;
		if(userType != null){
			switch(userType){
			case "Administrador":
				result = 1;
				break;
			case "Socio":
				result = 2;
				break;
			case "Gerente":
				result = 3;
				break;
			default:
				result = 0;
				break;
			}
		}
		return result;
	}
	
	public VOUser toVOUser(){
		VOUser result = new VOUser();
		
		result.setEmail(this.email);
		result.setId(this.id);
		result.setLastName(this.lastName);
		result.setName(this.name);
		result.setPassword(this.password);
		result.setUserName(this.userName);
		result.setUserStatus(this.userSatus);
		result.setUserType(this.getUserTypeId(this.userType));
		
		return result;
	}

	public int getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(int userTypeId) {
		this.userTypeId = userTypeId;
	}
}
