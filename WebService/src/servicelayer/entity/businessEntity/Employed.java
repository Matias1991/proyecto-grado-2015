package servicelayer.entity.businessEntity;

import java.util.ArrayList;
import java.util.Date;
import servicelayer.entity.valueObject.VOEmployed;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOSalarySummaries;

public class Employed {

	private int id;
	private String name;
	private String lastName;
	private String email;
	private String address;
	private String cellPhone;
	private Date createdDateTimeUTC;
	private Date updatedDateTimeUTC;
	private EmployedType employedType;
	private IDAOSalarySummaries iDAOSalarySummaries;

	public Employed() {
	}

	public Employed(int id) {
		this.id = id;
	}

	/*
	public Employed(VOEmployed voEmployed) {
		this.id = voEmployed.getId();
		this.name = voEmployed.getName();
		this.lastName = voEmployed.getLastName();
		this.email = voEmployed.getEmail();
		this.address = voEmployed.getAddress();
		this.cellPhone = voEmployed.getCellPhone();
		if (voEmployed.getEmployedType() != 0)
			this.employedType = EmployedType.getEnum(voEmployed
					.getEmployedType());
	}

	public Employed(VOEmployed voEmployed,
			IDAOSalarySummaries idaoSalarySummaries) {
		this.id = voEmployed.getId();
		this.name = voEmployed.getName();
		this.lastName = voEmployed.getLastName();
		this.email = voEmployed.getEmail();
		this.address = voEmployed.getAddress();
		this.cellPhone = voEmployed.getCellPhone();
		this.createdDateTimeUTC = voEmployed.getCreatedDateTimeUTC();
		this.updatedDateTimeUTC = voEmployed.getUpdatedDateTimeUTC();
		if (voEmployed.getEmployedType() != 0)
			this.employedType = EmployedType.getEnum(voEmployed
					.getEmployedType());
		this.iDAOSalarySummaries = idaoSalarySummaries;
	}
	*/

	public int getId() {
		return id;
	}

	public void setId(int _id) {
		this.id = _id;
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

	public EmployedType getEmployedType() {
		return employedType;
	}

	public void setEmployedType(EmployedType employedType) {
		this.employedType = employedType;
	}

	public void setIDAOSalarySummaries(IDAOSalarySummaries iDAOSalarySummaries) {
		this.iDAOSalarySummaries = iDAOSalarySummaries;
	}

	public void addNewSalarySummary(SalarySummary salarySummary)
			throws ServerException {
		iDAOSalarySummaries.insert(this.id, salarySummary);
	}

	public void updateSalarySummary(SalarySummary salarySummary)
			throws ServerException {
		iDAOSalarySummaries.update(this.id, salarySummary);
	}

	public ArrayList<SalarySummary> getSalarySummaries() throws ServerException {
		return iDAOSalarySummaries.getSalarySummaries(this.id);
	}

	public SalarySummary getLatestVersionSalarySummary() throws ServerException {
		return iDAOSalarySummaries.getLatestVersionSalarySummary(this.id);
	}

	public void deleteSalarySummaries() throws ServerException {
		iDAOSalarySummaries.deleteSalarySummaries(this.id);
	}

	public ArrayList<Integer> getAllVersionsSalarySummary()
			throws ServerException {
		return iDAOSalarySummaries.getALLVersionsSalarySummary(this.id);
	}

	public ArrayList<SalarySummary> getAllSalarySummaryVersion(int id)
			throws ServerException {
		return iDAOSalarySummaries.getAllVersionsDateSalarySummary(id);
	}

	public SalarySummary getSalarySummaryByVersion(int version)
			throws ServerException {
		return iDAOSalarySummaries.getSalarySummaryByVersion(this.id, version);
	}
	
	public SalarySummary getSalarySummaryToDate(Date to) throws ServerException{
		return iDAOSalarySummaries.getLastestSalarySummaryVersionsToDate(this.id, to);
	}
}
