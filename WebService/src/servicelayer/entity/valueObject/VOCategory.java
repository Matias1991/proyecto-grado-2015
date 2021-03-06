package servicelayer.entity.valueObject;

import java.io.Serializable;
import java.util.Date;

import servicelayer.entity.businessEntity.CategoryType;

public class VOCategory implements Serializable{

	private int id;
	private int version;
	private String description;
	private double amount;
	private Date appliedDateTimeUTC;
	private int projectId;
	private String projectName;
	private int categoryType;
	private boolean isRRHH;
	private static final long serialVersionUID = 1L;
	private Date updatedDateTimeUTC;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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

	public int getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(int categoryType) {
		this.categoryType = categoryType;
	}

	public boolean getIsRRHH() {
		return isRRHH;
	}

	public void setIsRRHH(boolean isRRHH) {
		this.isRRHH = isRRHH;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Date getUpdatedDateTimeUTC() {
		return updatedDateTimeUTC;
	}

	public void setUpdatedDateTimeUTC(Date updatedDateTimeUTC) {
		this.updatedDateTimeUTC = updatedDateTimeUTC;
	}
}
