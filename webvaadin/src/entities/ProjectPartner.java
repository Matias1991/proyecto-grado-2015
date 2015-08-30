package entities;

import java.util.Date;

import servicelayer.service.ServiceWebStub.VOProjectPartner;

public class ProjectPartner {
	
	int employedId;
	String employedName;
	String employedLastName;
	DistributionType distributionType;
	Date createdDateTimeUTC;
	Date updatedDateTimeUTC;
	boolean enable;
	int version;	
	
	public ProjectPartner(){
	}
	
	public ProjectPartner(VOProjectPartner voProjectPartner){
		
		this.employedId = voProjectPartner.getEmployedId();
		this.employedName = voProjectPartner.getEmployedName();
		this.employedLastName = voProjectPartner.getEmployedLastName();
		this.distributionType = new DistributionType(voProjectPartner.getDistributionType());		
		this.createdDateTimeUTC = voProjectPartner.getCreatedDateTimeUTC();		
		this.updatedDateTimeUTC = voProjectPartner.getUpdatedDateTimeUTC();
		this.enable = voProjectPartner.getEnabled();
		this.version = voProjectPartner.getVersion();		
	}

	public int getEmployedId() {
		return employedId;
	}

	public void setEmployedId(int employedId) {
		this.employedId = employedId;
	}

	public String getEmployedName() {
		return employedName;
	}

	public void setEmployedName(String employedName) {
		this.employedName = employedName;
	}

	public String getEmployedLastName() {
		return employedLastName;
	}

	public void setEmployedLastName(String employedLastName) {
		this.employedLastName = employedLastName;
	}

	public DistributionType getDistributionType() {
		return distributionType;
	}

	public void setDistributionType(DistributionType distributionType) {
		this.distributionType = distributionType;
	}

	public Date getCreatedDateTimeUTC() {
		return createdDateTimeUTC;
	}

	public void setCreatedDateTimeUTC(Date createdDateTimeUTC) {
		this.createdDateTimeUTC = createdDateTimeUTC;
	}

	public Date getUpdatedDateTimeUTC() {
		return updatedDateTimeUTC;
	}

	public void setUpdatedDateTimeUTC(Date updatedDateTimeUTC) {
		this.updatedDateTimeUTC = updatedDateTimeUTC;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}	
}
