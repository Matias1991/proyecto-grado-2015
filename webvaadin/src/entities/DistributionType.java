package entities;

public class DistributionType {
	int id;	
	int value;
	String description;
	int partnerId;
	String partnerName;
	String partnerLastname;
	
	public DistributionType(){		
	}
	
	//public DistributionType(VODistributionType voDistribution){}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(int partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPartnerLastname() {
		return partnerLastname;
	}

	public void setPartnerLastname(String partnerLastname) {
		this.partnerLastname = partnerLastname;
	}
}
