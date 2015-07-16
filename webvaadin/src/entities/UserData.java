package entities;

public class UserData {

	private int id;
	private String name;
	private int userType;
	
	public UserData(int id, String name, int userType)
	{
		this.id = id;
		this.name = name;
		this.userType = userType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserType(){
		return userType;
	}
	
	public void setUserType(int userType){
		this.userType = userType;
	}
}
