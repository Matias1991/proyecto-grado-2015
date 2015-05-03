package com.example.service;

import valueObjects.User;
import valueObjects.UserDescription;

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
	
	public UserDescription getUserDescriptionFromDB(int id){
		UserDescription userDesc = DataAccess.GetUserDescription(id);
		return userDesc;
	}
	
	public boolean correctLoginFromDB(String username, String password){
		return DataAccess.CorrectLogin(username, password);
	}
	
}
