package entities;

import java.util.Date;

import servicelayer.service.ServiceWebStub.VOEmployed;
import servicelayer.service.ServiceWebStub.VOEmployedProject;


public class EmployedHours {
	
	private int id;
	private String name;
	private String lastName;
	private int hours;
	private Date createdDateTimeUTC;
	private Date updatedDateTimeUTC;
	private boolean enabled;
	private int version;
	
	public EmployedHours(){
		
	}
	
	public EmployedHours(VOEmployedProject voEmployedProject){
//		this.id = voEmployedProject.getEmployed().getId();
//		this.name = voEmployedProject.getEmployed().getName();
//		this.lastName = voEmployedProject.getEmployed().getLastName();
//		this.hours = voEmployedProject.getHours();
//		this.createdDateTimeUTC = voEmployedProject.getCreatedDateTimeUTC();
//		this.updatedDateTimeUTC = voEmployedProject.getUpdatedDateTimeUTC();
//		this.enabled = voEmployedProject.getEnabled();
//		this.version = voEmployedProject.getVersion();
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public VOEmployedProject toVOEmployedProject(){
		VOEmployedProject voEmployedProject = new VOEmployedProject();
		VOEmployed voEmployed = new VOEmployed();
		
		voEmployed.setId(this.id);
		
		//voEmployedProject.setEmployed(voEmployed);			
		voEmployedProject.setCreatedDateTimeUTC(this.createdDateTimeUTC);
		voEmployedProject.setUpdatedDateTimeUTC(this.updatedDateTimeUTC);
		voEmployedProject.setHours(this.hours);		
		voEmployedProject.setVersion(this.version);
		voEmployedProject.setEnabled(this.enabled);
		
		
		return voEmployedProject;
		
	}
	

}
