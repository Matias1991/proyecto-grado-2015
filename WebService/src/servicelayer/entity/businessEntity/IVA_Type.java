package servicelayer.entity.businessEntity;

public enum IVA_Type {
	ZERO(1), TEN(2), TWENTY_TWO(3);

	private final int id;

	IVA_Type(int id) {
		this.id = id;
	}

	public int getValue() {
		return id;
	}

	public static IVA_Type getEnum(int value) {
		for (IVA_Type v : values())
			if (v.getValue() == value)
				return v;
		throw new IllegalArgumentException();
	}
	
	public double getPercentage() {
		
		double value = 0;
		switch(getEnum(id))
		{
			case TEN:
				value = 1.10;
				break;
			case TWENTY_TWO:
				value = 1.22;
				break;
			default:
				break;
		}
			
		return value;
	} 
}
