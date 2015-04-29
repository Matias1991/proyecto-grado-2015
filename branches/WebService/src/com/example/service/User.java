package com.example.service;

import java.io.Serializable;

public class User implements Serializable{

	/**
	 * prueba comentario
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	
	
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
}
