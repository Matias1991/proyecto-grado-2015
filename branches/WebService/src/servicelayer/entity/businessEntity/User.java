package servicelayer.entity.businessEntity;

import servicelayer.entity.valueObject.VOUser;

public class User{

	private int id;
	private String userName;
	private String password;
	private String name;
	private String LastName;
	private String Email;

	public User()
	{
		
	}
	
	public User(VOUser voUser)
	{
		this.id = voUser.getId();
		this.name = voUser.getName();
		this.LastName = voUser.getPassword();
		this.Email = voUser.getEmail();
		this.userName = voUser.getUserName();
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
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}
}
