package entities;

import java.util.Date;

import servicelayer.service.ServiceWebStub.VOPartnerProject;

public class PartnerProject {
	
	int employedId;
	String employedName;
	String employedLastName;
	DistributionType distributionType;
	Date createdDateTimeUTC;
	Date updatedDateTimeUTC;
	boolean enable;
	int version;	
	
	public PartnerProject(){
	}
	
	public PartnerProject(VOPartnerProject voPartnerProject){
		
		this.employedId = voPartnerProject.getEmployedId();
		this.employedName = voPartnerProject.getEmployedName();
		this.employedLastName = voPartnerProject.getEmployedLastName();
		this.distributionType = new DistributionType(voPartnerProject.getDistributionType());		
		this.createdDateTimeUTC = voPartnerProject.getCreatedDateTimeUTC();		
		this.updatedDateTimeUTC = voPartnerProject.getUpdatedDateTimeUTC();
		this.enable = voPartnerProject.getEnabled();
		this.version = voPartnerProject.getVersion();		
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
