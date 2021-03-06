package servicelayer.entity.valueObject;

import java.io.Serializable;
import java.util.Date;

public class VOBill implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String code;
	private String description;
    private double amount;
    private Date appliedDateTimeUTC;
    private int projectId;
    private String projectName;
    private boolean isLiquidated;
    
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
	public Date getAppliedDateTimeUTC() {
		return appliedDateTimeUTC;
	}
	public void setAppliedDateTimeUTC(Date appliedDateTimeUTC) {
		this.appliedDateTimeUTC = appliedDateTimeUTC;
	}
	public boolean isLiquidated() {
		return isLiquidated;
	}
	public void setLiquidated(boolean isLiquidated) {
		this.isLiquidated = isLiquidated;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
