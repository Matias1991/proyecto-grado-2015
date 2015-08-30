package entities;

import java.util.Date;

import servicelayer.service.ServiceWebStub.VOEmployedProject;

public class EmployedProject {
	
	int employedId;
	String employedName;
	String employedLastName;
	int employedHours;
	Date createdDateTimeUTC;
	boolean enable;
	Date updatedDateTimeUTC;
	int version;
	
	public EmployedProject(){		
	}
	
	public EmployedProject(VOEmployedProject voEmployedProject){
		this.employedId = voEmployedProject.getEmployedId();
		this.employedName = voEmployedProject.getEmployedName();		
		this.employedLastName = voEmployedProject.getEmployedLastname();
		this.employedHours = voEmployedProject.getHours();
		this.enable = voEmployedProject.getEnabled();		
		this.createdDateTimeUTC = voEmployedProject.getCreatedDateTimeUTC();
		this.updatedDateTimeUTC = voEmployedProject.getUpdatedDateTimeUTC();
		this.version = voEmployedProject.getVersion();
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
