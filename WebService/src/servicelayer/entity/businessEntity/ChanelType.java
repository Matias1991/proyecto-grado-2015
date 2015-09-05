package servicelayer.entity.businessEntity;

public enum ChanelType {
	WEB(1), MOBILE(2);
	
	private final int id;
	
	ChanelType(int id){
		this.id = id;
	}
	
	public int getValue(){
		return id;
	}
	
	public static ChanelType getEnum(int value){
		for(ChanelType v : values()){
			if(v.getValue() == value){
				return v;
			}
		}
		throw new IllegalArgumentException();
	}
}
