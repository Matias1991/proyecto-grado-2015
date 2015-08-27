package servicelayer.entity.valueObject;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class VOEmployed implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private String lastName;
	private String email;
	private String address;
	private String cellPhone;
	private Date createdDateTimeUTC;
	private Date updatedDateTimeUTC;
	private int employedType;
	private VOSalarySummary vOSalarySummary;
	private List<VOSalarySummary> vOSalarySummaries;

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

	public int getEmployedType() {
		return employedType;
	}

	public void setEmployedType(int employedType) {
		this.employedType = employedType;
	}

	public VOSalarySummary getvOSalarySummary() {
		return vOSalarySummary;
	}

	public void setvOSalarySummary(VOSalarySummary vOSalarySummary) {
		this.vOSalarySummary = vOSalarySummary;
	}

	public List<VOSalarySummary> getvOSalarySummaries() {
		return vOSalarySummaries;
	}

	public void setvOSalarySummaries(List<VOSalarySummary> vOSalarySummaries) {
		this.vOSalarySummaries = vOSalarySummaries;
	}
}
