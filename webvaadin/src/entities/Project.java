package entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import servicelayer.service.ServiceWebStub.VOEmployed;
import servicelayer.service.ServiceWebStub.VOEmployedProject;
import servicelayer.service.ServiceWebStub.VOProject;


public class Project{
		
	private int id;
	private String name;
	private Date createdDateTimeUTC;
	private Date updatedDateTimeUTC;
	private String enabledToShow;
	private String createDateTimeUTCToShow;	
	private User manager;
	private Employee seller;
	private String description;
	private List<EmployedProject> employedHours;
	private List<DistributionType> distributions;
		
	public Project()
	{
		
	}
	
	public Project(VOProject voProject)
	{
		this.id = voProject.getId();
		this.name = voProject.getName();
		this.description = voProject.getDescription();
		this.createdDateTimeUTC = voProject.getCreatedDateTimeUTC();
		this.updatedDateTimeUTC = voProject.getUpdatedDateTimeUTC();
		if(voProject.getEnabled())
			setEnabledToShow("Habilitado");
		else
			setEnabledToShow("Deshabilitado");		
		this.createDateTimeUTCToShow = new SimpleDateFormat("dd-MM-yyyy").format(createdDateTimeUTC);
		this.manager = new User();
		if(voProject.getManagerId() != 0){		
			this.manager.setId(voProject.getManagerId());
			this.manager.setName(voProject.getManagerName());
			this.manager.setLastName(voProject.getManagerLastName());
		}
		this.seller = new Employee();
		this.seller.setId(voProject.getSellerId());
		this.seller.setName(voProject.getSellerName());
		this.seller.setLastName(voProject.getSellerLastname());
		
	//QUEDA LA LISTA DE EMPLEADOS Y LA LISTA DE DISTRIBUCIONES
		for (VOEmployedProject voEmployedProject : voProject.getVoEmployedProjects()) {
			EmployedProject emp = new EmployedProject(voEmployedProject);
			employedHours.add(emp);			
		}
		
//		for (VODistributionType voDistributionType : distributions) {
//			
//		}
		
		
		
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

	public List<EmployedProject> getEmployedHours() {
		return employedHours;
	}	

	public List<DistributionType> getDistributions() {
		return distributions;
	}

	public void setDistributions(List<DistributionType> distributions) {
		this.distributions = distributions;
	}

	public void setEmployedHours(List<EmployedProject> employedHours) {
		this.employedHours = employedHours;
	}	
}
