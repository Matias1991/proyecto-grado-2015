package servicelayer.entity.businessEntity;

import java.util.Date;

import servicelayer.entity.valueObject.VOCategory;

public class Category {

	private int id;
	private String description;
	private double amount;
	private Date createDateTimeUTC;
	private int projectId;
	private int distributionType;
	
	public Category(){}
	
	public Category(VOCategory voCategory){
		this.id = voCategory.getId();
		this.description = voCategory.getDescription();
		this.amount = voCategory.getAmount();
		this.createDateTimeUTC = voCategory.getCreateDateTimeUTC();
		this.projectId = voCategory.getProjectId();
		this.distributionType = voCategory.getDistributionType();
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

	public Date getCreateDateTimeUTC() {
		return createDateTimeUTC;
	}

	public void setCreateDateTimeUTC(Date createDateTimeUTC) {
		this.createDateTimeUTC = createDateTimeUTC;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getDistributionType() {
		return distributionType;
	}

	public void setDistributionType(int distributionType) {
		this.distributionType = distributionType;
	}
}
