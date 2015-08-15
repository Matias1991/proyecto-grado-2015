package servicelayer.entity.businessEntity;

import java.util.Date;

import servicelayer.entity.valueObject.VOBill;

public class Bill {

	private int id;
    private String description;
    private double amount;
    private Date createdDateTimeUTC;
    private Project project;
    
    public Bill()
    {
    	
    }
    
    public Bill(VOBill voBill)
    {
    	this.id = voBill.getId();
    	this.description = voBill.getDescription();
    	this.amount = voBill.getAmount();
    	this.createdDateTimeUTC = voBill.getCreatedDateTimeUTC();
    	if(voBill.getProjectId() != 0)
    		this.project = new Project(voBill.getProjectId());
    }
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Date getCreatedDateTimeUTC() {
		return createdDateTimeUTC;
	}
	public void setCreatedDateTimeUTC(Date createdDateTimeUTC) {
		this.createdDateTimeUTC = createdDateTimeUTC;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
}
