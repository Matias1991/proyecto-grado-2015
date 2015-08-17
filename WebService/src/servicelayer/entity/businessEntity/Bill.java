package servicelayer.entity.businessEntity;

import java.util.Date;

import servicelayer.entity.valueObject.VOBill;

public class Bill {

	private int id;
	private String code;
    private String description;
    private double amount;
    private Date appliedDateTimeUTC;
    private Project project;
    private boolean isLiquidated;
    
    public Bill()
    {
    	
    }
    
    public Bill(VOBill voBill)
    {
    	this.id = voBill.getId();
    	this.code = voBill.getCode();
    	this.description = voBill.getDescription();
    	this.amount = voBill.getAmount();
    	this.appliedDateTimeUTC = voBill.getAppliedDateTimeUTC();
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
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getAppliedDateTimeUTC() {
		return appliedDateTimeUTC;
	}

	public void setAppliedDateTimeUTC(Date appliedDateTimeUTC) {
		this.appliedDateTimeUTC = appliedDateTimeUTC;
	}

	public boolean getIsLiquidated() {
		return isLiquidated;
	}

	public void setIsLiquidated(boolean isLiquidated) {
		this.isLiquidated = isLiquidated;
	}
}
