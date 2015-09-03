package servicelayer.entity.businessEntity;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.valueObject.VOProject;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOProjectEmployees;
import shared.interfaces.dataLayer.IDAOProjectPartners;

public class Project {

	private int id;
	private String name;
	private Date createdDateTimeUTC;
	private Date updatedDateTimeUTC;
	private boolean closed;
	private User manager;
	private Employed seller;
	private String description;
	private double amount;
	private boolean isCurrencyDollar;
	private IDAOProjectEmployees iDAOEmployedProject;
	private IDAOProjectPartners iDAOPartnerProject;

	public Project() {
	}

	public Project(int id) {
		this.id = id;
	}

	public Project(int id, String name, String description, User manager,
			Employed seller, boolean closed, Date createdDateTimeUTC,
			Date updatedDateTimeUTC) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.manager = manager;
		this.seller = seller;
		this.closed = closed;
		this.createdDateTimeUTC = createdDateTimeUTC;
		this.updatedDateTimeUTC = updatedDateTimeUTC;
	}

	public Project(VOProject voProject, IDAOProjectEmployees idaoEmployedProject, IDAOProjectPartners idaoPartnerProject) {
		this.name = voProject.getName();
		this.description = voProject.getDescription();
		if (voProject.getSellerId() != 0) {
			this.seller = new Employed(voProject.getSellerId());
		}
		if (voProject.getManagerId() != 0) {
			this.manager = new User(voProject.getManagerId());
		}
		this.updatedDateTimeUTC = voProject.getUpdatedDateTimeUTC();
		this.createdDateTimeUTC = voProject.getCreatedDateTimeUTC();
		this.closed = voProject.isClosed();
		this.iDAOEmployedProject = idaoEmployedProject;
		this.iDAOPartnerProject = idaoPartnerProject;
	}

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

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public Employed getSeller() {
		return seller;
	}

	public void setSeller(Employed seller) {
		this.seller = seller;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public boolean getClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean getIsCurrencyDollar() {
		return isCurrencyDollar;
	}

	public void setIsCurrencyDollar(boolean isCurrencyDollar) {
		this.isCurrencyDollar = isCurrencyDollar;
	}

	public IDAOProjectEmployees getiDAOEmployedProject() {
		return iDAOEmployedProject;
	}

	public void setiDAOEmployedProject(IDAOProjectEmployees iDAOEmployedProject) {
		this.iDAOEmployedProject = iDAOEmployedProject;
	}
	
	public IDAOProjectPartners getiDAOPartnerProject() {
		return iDAOPartnerProject;
	}

	public void setiDAOPartnerProject(IDAOProjectPartners iDAOPartnerProject) {
		this.iDAOPartnerProject = iDAOPartnerProject;
	}

	public void associateEmployed(ProjectEmployed employedProject)
			throws ServerException {		
		this.iDAOEmployedProject
				.insertEmployedProject(this.id, employedProject);
	}
	
	public void associateDistribution(ProjectPartner partnerProject) throws ServerException{
		this.iDAOPartnerProject.insertPartnerProject(this.id, partnerProject);
	}
	
	public ArrayList<ProjectEmployed> getProjectEmployed() throws ServerException
	{
		return iDAOEmployedProject.getEmployeesProject(this.id);
	}
}
