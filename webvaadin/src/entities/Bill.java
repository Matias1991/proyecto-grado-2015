package entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import servicelayer.service.ServiceWebStub.VOBill;

public class Bill {

	private int id;
	private String code;
	private String description;
    private double amount;
    private Date appliedDateTimeUTC;
    private int projectId;
    private String projectName;
    private String appliedDateTimeUTCToShow;
    
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
    		this.projectName = voBill.getProjectName();
    	
    	this.setAppliedDateTimeUTCToShow(new SimpleDateFormat("MM-yyyy").format(appliedDateTimeUTC));
    }
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
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
	public Date getAppliedDateTimeUTC() {
		return appliedDateTimeUTC;
	}
	public void setAppliedDateTimeUTC(Date appliedDateTimeUTC) {
		this.appliedDateTimeUTC = appliedDateTimeUTC;
	}
	public int getProjectId() {
		return projectId;
	}
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getAppliedDateTimeUTCToShow() {
		return appliedDateTimeUTCToShow;
	}

	public void setAppliedDateTimeUTCToShow(String appliedDateTimeUTCToShow) {
		this.appliedDateTimeUTCToShow = appliedDateTimeUTCToShow;
	}
}
