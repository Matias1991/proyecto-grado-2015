package valueObjects;

import java.io.Serializable;

public class UserDescription implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String surname;
	private String address;
	private String email;
	
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setSurname(String surname){
		this.surname = surname;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public void setEmail(String email){
		this.email = email;		
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getSurname(){
		return this.surname;
	}
	
	public String getAddress(){
		return this.address;
	}
	
	public String getEmail(){
		return this.email;
	}
	

}
