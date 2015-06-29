package com.example.androidproject;

import org.ksoap2.serialization.SoapObject;

public class VOUser {

	int id;
	String userName;
	String password;
	String name;
	String lastName;
	String email;
	
	public VOUser(SoapObject object){
		new Deserialization().SoapDeserialize(this,object);

		//'this' is the instance itself ,
		//object is the SoapObject received from response when we call new Person(soapobject)

		this.email = object.getProperty(0).toString();
		this.id = Integer.parseInt(object.getProperty(1).toString());
		this.lastName = object.getProperty(2).toString();
		this.name = object.getProperty(3).toString();
		this.userName = object.getProperty(5).toString();
	}
}
