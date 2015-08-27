package servicelayer.entity.businessEntity;

import java.util.Date;

import datalayer.daos.DAOEmployedProject;
import servicelayer.entity.valueObject.VOEmployed;
import servicelayer.entity.valueObject.VOEmployedProject;
import servicelayer.entity.valueObject.VOProject;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOEmployedProject;


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
	private IDAOEmployedProject iDAOEmployedProject;
	
	public Project() throws ServerException
	{
		iDAOEmployedProject = new DAOEmployedProject();
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
	
	public Project(VOProject voProject, IDAOEmployedProject idaoEmployedProject)
	{
		this.name = voProject.getName();
		this.description = voProject.getDescription();
		if(voProject.getSellerId() != 0){
			this.seller = new Employed(voProject.getSellerId());			
		}
		if(voProject.getManagerId() != 0){
			this.manager = new User(voProject.getManagerId());			
		}		
		this.updatedDateTimeUTC = voProject.getUpdatedDateTimeUTC();
		this.createdDateTimeUTC = voProject.getCreatedDateTimeUTC();
		this.enabled = voProject.isEnabled();
		this.iDAOEmployedProject = idaoEmployedProject;
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

	public IDAOEmployedProject getiDAOEmployedProject() {
		return iDAOEmployedProject;
	}

	public void setiDAOEmployedProject(IDAOEmployedProject iDAOEmployedProject) {
		this.iDAOEmployedProject = iDAOEmployedProject;
	}
	
	public void associateEmployed(EmployedProject employedProject) throws ServerException{
		this.iDAOEmployedProject.insertEmployedProject(this.id, employedProject);
	}
	
	

}
