package servicelayer.entity.valueObject;

import java.io.Serializable;

public class VOUser implements Serializable{

	/**
	 * prueba comentario
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String userName;
	private String password;
	private String name;
	private String LastName;
	private String Email;
	
	
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
