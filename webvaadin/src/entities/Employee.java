package entities;

import java.util.Date;
import java.util.List;

import servicelayer.service.ServiceWebStub.VOEmployed;
import servicelayer.service.ServiceWebStub.VOSalarySummary;
import servicelayer.service.ServiceWebStub.VOUser;

public class Employee {

	private int id;
	private String name;
	private String lastName;
	private String email;
	private String address;
	private String cellPhone;
	private Date createdDateTimeUTC;
	private Date updatedDateTimeUTC;
	private int employedType;
	private VOUser user;
	private VOSalarySummary voSalarySummary;
	private List<VOSalarySummary> voSalarySummaries;
	
	public Employee(VOEmployed voEmployee)
	{
		this.id = voEmployee.getId();
		this.name = voEmployee.getName();
		this.lastName = voEmployee.getLastName();
		this.email = voEmployee.getEmail();
		this.address = voEmployee.getAddress();
		this.cellPhone = voEmployee.getCellPhone();
		this.createdDateTimeUTC = voEmployee.getCreatedDateTimeUTC();
		this.createdDateTimeUTC = voEmployee.getCreatedDateTimeUTC();
		this.updatedDateTimeUTC = voEmployee.getUpdatedDateTimeUTC();
		this.employedType = voEmployee.getEmployedType();
		this.user = voEmployee.getUser();
		this.voSalarySummary = voEmployee.getVOSalarySummary();
		this.voSalarySummaries = (List<VOSalarySummary>) voEmployee.getVOSalarySummary();
		
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
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

	//ver de casear lo tipos como en usuario
	public int getEmployedType() {
		return employedType;
	}

	public void setEmployedType(int employedType) {
		this.employedType = employedType;
	}

	public VOUser getUser() {
		return user;
	}

	public void setUser(VOUser user) {
		this.user = user;
	}

	public VOSalarySummary getVoSalarySummary() {
		return voSalarySummary;
	}

	public void setVoSalarySummary(VOSalarySummary voSalarySummary) {
		this.voSalarySummary = voSalarySummary;
	}

	public List<VOSalarySummary> getVoSalarySummaries() {
		return voSalarySummaries;
	}

	public void setVoSalarySummaries(List<VOSalarySummary> voSalarySummaries) {
		this.voSalarySummaries = voSalarySummaries;
	}
	
}
