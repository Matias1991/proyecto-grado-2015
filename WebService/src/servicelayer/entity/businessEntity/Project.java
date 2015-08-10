package servicelayer.entity.businessEntity;


import java.util.Date;

import datalayer.daos.DAOEmployees;
import servicelayer.entity.valueObject.VOEmployed;
import servicelayer.entity.valueObject.VOProject;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOEmployees;


public class Project {

	private int id;
	private String name;
	private String notes;
	private Date createdDateTimeUTC;
	private Date updatedDateTimeUTC;
	private boolean enabled;
	private IDAOEmployees IDAOEmployees;
	
	public Project() throws ServerException
	{
		IDAOEmployees = new DAOEmployees();
	}
	
	public Project(int id)
	{
		this.id = id;
	}
	
	public Project(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	public Project(VOProject voProject)
	{
		this.name = voProject.getName();
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
