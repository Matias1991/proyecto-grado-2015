package com.example.androidproject;

import org.ksoap2.serialization.SoapObject;

public class VOUser {

	private int id;
	private String userName;
	private String password;
	private String name;
	private String lastName;
	private String email;
	private int userType;

	public VOUser(SoapObject object) {
		new Deserialization().SoapDeserialize(this, object);

		this.email = object.getProperty(0).toString();
		this.id = Integer.parseInt(object.getProperty(1).toString());
		this.lastName = object.getProperty(2).toString();
		this.name = object.getProperty(3).toString();
		this.userName = object.getProperty(5).toString();
		this.userType = Integer.parseInt(object.getProperty(7).toString());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getUserType() {
		if (this.userType == 2) {
			return "PARTNER";
		} else if (this.userType == 3) {
			return "MANAGER";
		} else
			return "";
	}
}
