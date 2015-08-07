package entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import servicelayer.service.ServiceWebStub.VOProject;

public class Project {

	private int id;
	private String name;
	private Date createdDateTimeUTC;
	private Date updatedDateTimeUTC;
	private String enabledToShow;
	private String createDateTimeUTCToShow;
	
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
}
