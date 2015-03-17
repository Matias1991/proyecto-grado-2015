package com.example.service;

public class TestWS {

	public User getUser(int id)
	{
		User user = new User();
		
		user.setId(id);
		user.setName("matias");
		
		return user;
	}
	
	public User getUserFromDB(int id)
	{
		User user = DataAccess.GetUser(id);
		return user;
	}
}
