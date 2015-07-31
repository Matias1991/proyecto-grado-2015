package entities;

import java.util.ArrayList;
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
	private String employedType;	
	private User user;
	private SalarySummary salarySummary;
	private List<SalarySummary> salarySummaries;
	
	public Employee(){
		
	}
	
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
		this.employedType = getEmployeeTypeToShow(voEmployee.getEmployedType());
		this.user = new User(voEmployee.getUser());
		this.salarySummary = new SalarySummary(voEmployee.getVOSalarySummary());
//		this.voSalarySummaries = (List<VOSalarySummary>) voEmployee.getVOSalarySummary();
		
		
	}

	/*public int getId() {
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
	public String getEmployedType() {
		return employedType;
	}

	public List<SalarySummary> getSalarySummaries() {
		return salarySummaries;
	}

	public void setSalarySummaries(List<SalarySummary> salarySummaries) {
		this.salarySummaries = salarySummaries;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setSalarySummary(SalarySummary salarySummary) {
		this.salarySummary = salarySummary;
	}

	public void setEmployedType(String employedType) {
		this.employedType = employedType;
	}

	public VOUser getUser() {
		return user.toVOUser();
	}

	public void setUser(VOUser user) {
		this.user = new User(user);
	}

	public VOSalarySummary getSalarySummary() {
		return salarySummary;
	}

	public void setSalarySummary(VOSalarySummary voSalarySummary) {
		this.salarySummary = voSalarySummary;
	}

	public List<VOSalarySummary> getVoSalarySummaries() {
		return salarySummaries;
	}

	public void setVoSalarySummaries(List<VOSalarySummary> voSalarySummaries) {
		this.voSalarySummaries = voSalarySummaries;
	}*/
	
	public String getEmployeeTypeToShow(int employeeTypeId){
		String result ="";
		switch(employeeTypeId){
		case 1: 
			result = "Empleado";
			break;
		case 2:
			result = "Socio";
			break;
		default:
			result = "No definido";
		}
		return result;		
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

	public String getEmployedType() {
		return employedType;
	}

	public void setEmployedType(String employedType) {
		this.employedType = employedType;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SalarySummary getSalarySummary() {
		return salarySummary;
	}

	public void setSalarySummary(SalarySummary salarySummary) {
		this.salarySummary = salarySummary;
	}

	public List<SalarySummary> getSalarySummaries() {
		return salarySummaries;
	}

	public void setSalarySummaries(List<SalarySummary> salarySummaries) {
		this.salarySummaries = salarySummaries;
	}

	private int getEmployedTypeId(String employedType){
		int result = 0;
		switch(employedType){
		case "Empleado":
			result = 1;
			break;
		case "Socio":
			result = 2;
			break;
		default:
				result = 0;
		}
		return result;
		
	}
	public VOEmployed toVOEmployee(){
		VOEmployed voEmployed = new VOEmployed();
		
		voEmployed.setAddress(this.address);
		voEmployed.setCellPhone(this.cellPhone);
		voEmployed.setCreatedDateTimeUTC(this.createdDateTimeUTC);
		voEmployed.setEmail(this.email);
		voEmployed.setEmployedType(getEmployedTypeId(this.employedType));
		voEmployed.setId(this.id);
		voEmployed.setLastName(this.lastName);
		voEmployed.setName(this.name);
		voEmployed.setUpdatedDateTimeUTC(this.updatedDateTimeUTC);
		if(this.user != null){
			voEmployed.setUser(this.user.toVOUser());
		}
		//voEmployed.setVOSalarySummaries(param);
		voEmployed.setVOSalarySummary(this.salarySummary.toVOSalarySummary());
		
		return voEmployed;
	}
}
