package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import servicelayer.service.ServiceWebStub.VOProject;


public class Project implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private Date createdDateTimeUTC;
	private Date updatedDateTimeUTC;
	private String enabledToShow;
	private String createDateTimeUTCToShow;	
	private User manager;
	private Employee seller;
	private String description;
	
	public Project(VOProject voProject)
	{
		this.id = voProject.getId();
		this.name = voProject.getName();
		this.createdDateTimeUTC = voProject.getCreatedDateTimeUTC();
		this.updatedDateTimeUTC = voProject.getUpdatedDateTimeUTC();
		if(voProject.getEnabled())
			setEnabledToShow("Habilitado");
		else
			setEnabledToShow("Deshabilitado");
		
		this.createDateTimeUTCToShow = new SimpleDateFormat("dd-MM-yyyy").format(createdDateTimeUTC);
		this.manager = new User(voProject.getManager());
		if(voProject.getSeller() != null){
			this.seller = new Employee(voProject.getSeller());
		}		
		this.description = voProject.getDescription();
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

	public String getEnabledToShow() {
		return enabledToShow;
	}

	public void setEnabledToShow(String enabledToShow) {
		this.enabledToShow = enabledToShow;
	}

	public String getCreateDateTimeUTCToShow() {
		return createDateTimeUTCToShow;
	}

	public void setCreateDateTimeUTCToShow(String createDateTimeUTCToShow) {
		this.createDateTimeUTCToShow = createDateTimeUTCToShow;
	}

	public User getManager() {
		return manager;
	}

	public void setManager(User manager) {
		this.manager = manager;
	}

	public Employee getSeller() {
		return seller;
	}

	public void setSeller(Employee seller) {
		this.seller = seller;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
