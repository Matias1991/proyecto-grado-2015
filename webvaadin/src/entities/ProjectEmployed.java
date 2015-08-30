package entities;

import java.util.Date;

import servicelayer.service.ServiceWebStub.VOProjectEmployed;

public class ProjectEmployed {
	
	int employedId;
	String employedName;
	String employedLastName;
	int employedHours;
	Date createdDateTimeUTC;
	boolean enable;
	Date updatedDateTimeUTC;
	int version;
	
	public ProjectEmployed(){		
	}
	
	public ProjectEmployed(VOProjectEmployed voProjectEmployed){
		this.employedId = voProjectEmployed.getEmployedId();
		this.employedName = voProjectEmployed.getEmployedName();		
		this.employedLastName = voProjectEmployed.getEmployedLastname();
		this.employedHours = voProjectEmployed.getHours();
		this.enable = voProjectEmployed.getEnabled();		
		this.createdDateTimeUTC = voProjectEmployed.getCreatedDateTimeUTC();
		this.updatedDateTimeUTC = voProjectEmployed.getUpdatedDateTimeUTC();
		this.version = voProjectEmployed.getVersion();
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

	public int getEmployedHours() {
		return employedHours;
	}

	public void setEmployedHours(int employedHours) {
		this.employedHours = employedHours;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Date getUpdatedDateTimeUTC() {
		return updatedDateTimeUTC;
	}

	public void setUpdatedDateTimeUTC(Date updatedDateTimeUTC) {
		this.updatedDateTimeUTC = updatedDateTimeUTC;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}	
}
