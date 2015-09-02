package servicelayer.entity.businessEntity;

import java.util.Date;

public class DistributionType {
	
	private int id;
	private String value;
	private String description;
	
	public DistributionType() {		
	}
	
	public DistributionType(int id, String value, String description){
		this.id = id;
		this.value = value;
		this.description = description;		
	}
	

	public DistributionType(int id){
		this.id = id;		
	}
	
//	public DistributionType(VODistributionType){
	//	if(voDistributionType.getEmployed().getId() != 0){
	//		
	//	}
//	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
