package servicelayer.entity.businessEntity;

public enum CategoryType {
	COMPANY(1),
	PROJECT(2);
	
	private final int id;
	
	CategoryType(int id) 
	{ 
		this.id = id; 
	}
	
    public int getValue() 
    { 
    	return id; 
    }
    
    public static CategoryType getEnum(int value) {
        for(CategoryType v : values())
        	if(v.getValue() == value) return v;
        throw new IllegalArgumentException();
    }
}
