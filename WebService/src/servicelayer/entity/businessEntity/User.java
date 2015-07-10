package servicelayer.entity.businessEntity;

import servicelayer.entity.valueObject.VOUser;

public class User{

	private int id;
	private String userName;
	private String password;
	private String name;
	private String lastName;
	private String email;
    private UserType userType;
    private UserStatus userStatus;
    
	public User()
	{
		
	}
	
	public User(VOUser voUser)
	{
		this.id = voUser.getId();
		this.name = voUser.getName();
		this.lastName = voUser.getLastName();
		this.email = voUser.getEmail();
		this.userName = voUser.getUserName();
		this.password = voUser.getPassword();
		this.userType =  UserType.getEnum(voUser.getUserType());
		this.userStatus = UserStatus.getEnum(voUser.getUserStatus());
	}
	
	public void setId(int i)
	{
		id = i;
	}
	
	public void setName(String n)
	{
		name = n;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserType getUserType() {
		return userType;
	}

	public void setUserType(UserType userType) {
		this.userType = userType;
	}

	public UserStatus getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(UserStatus userStatus) {
		this.userStatus = userStatus;
	}
}
