package servicelayer.entity.businessEntity;

public enum UserType {
	//ESTOS VALORES SE DEBE CORRESPONDER CON LOS VALORES DE LOS ID's DE LA TABLE USERTYPE EN LA BASE DE DATOS
	ADMINISTRATOR(1),
	PARTNER(2),
	MANAGER(3);
	
	private final int id;
	
	UserType(int id) 
	{ 
		this.id = id; 
	}
	
    public int getValue() 
    { 
    	return id; 
    }
    
    public static UserType getEnum(int value) {
        for(UserType v : values())
        	if(v.getValue() == value) return v;
        throw new IllegalArgumentException();
    }
}
