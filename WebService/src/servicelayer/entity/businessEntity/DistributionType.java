package servicelayer.entity.businessEntity;

public enum DistributionType {
	FIFTY_FIFTY(1),
	TWO_THIRD(2),
	ONE_THIRD(3);
	
	private final int id;
	
	DistributionType(int id) 
	{ 
		this.id = id; 
	}
	
    public int getValue() 
    { 
    	return id; 
    }
    
    public static DistributionType getEnum(int value) {
        for(DistributionType v : values())
        	if(v.getValue() == value) return v;
        throw new IllegalArgumentException();
    }
	
}
