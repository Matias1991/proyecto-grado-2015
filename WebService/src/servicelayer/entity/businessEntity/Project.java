package servicelayer.entity.businessEntity;

import java.util.Date;

import servicelayer.entity.valueObject.VOEmployed;
import servicelayer.entity.valueObject.VOProject;
import shared.exceptions.ServerException;


public class Project {

	private int id;
	private String name;
	private String notes;	
	private Date createdDateTimeUTC;
	private Date updatedDateTimeUTC;
	private boolean enabled;
	private User manager;
	private Employed seller;
	private String description;
	
	public Project() throws ServerException
	{
	}
	
	public Project(int id)
	{
		this.id = id;
	}
	
	public Project(int id, String name, String description, User manager, Employed seller, boolean enabled, Date createdDateTimeUTC, Date updatedDateTimeUTC)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.manager = manager;
		this.seller = seller;
		this.enabled = enabled;
		this.createdDateTimeUTC = createdDateTimeUTC;
		this.updatedDateTimeUTC = updatedDateTimeUTC;
	}
	
	public Project(VOProject voProject)
	{
		this.name = voProject.getName();
		this.description = voProject.getDescription();
		if(voProject.getSeller() != null){
			this.seller = new Employed(voProject.getSeller());
		}
		if(voProject.getManager() != null){
			this.manager = new User(voProject.getManager());
		}		
		this.updatedDateTimeUTC = voProject.getUpdatedDateTimeUTC();
		this.createdDateTimeUTC = voProject.getCreatedDateTimeUTC();
		this.enabled = voProject.isEnabled();
		
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
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

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
