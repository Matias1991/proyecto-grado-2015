package servicelayer.entity.businessEntity;

import java.util.Date;

import servicelayer.entity.valueObject.VOCategory;

public class Category {

	private int id;
	private int version;
	private String description;
	private double amount;
	private Date appliedDateTimeUTC;
	private Project project;
	private CategoryType categoryType;
	private boolean isRRHH;
	private Date updatedDateTimeUTC;
	
	public Category(){}
	
	public Category(VOCategory voCategory){
		this.id = voCategory.getId();
		this.version = voCategory.getVersion();
		this.description = voCategory.getDescription();
		this.amount = voCategory.getAmount();
		this.appliedDateTimeUTC = voCategory.getAppliedDateTimeUTC();
		if(voCategory.getProjectId() != 0)
			this.project = new Project(voCategory.getProjectId());
		if(voCategory.getCategoryType() == 1)
			this.project = null;
		this.categoryType = CategoryType.getEnum(voCategory.getCategoryType());
		this.isRRHH = voCategory.getIsRRHH();
		this.appliedDateTimeUTC = voCategory.getAppliedDateTimeUTC();
		this.updatedDateTimeUTC = voCategory.getUpdatedDateTimeUTC();
	}

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
	
	public CategoryType getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(CategoryType category) {
		this.categoryType = category;
	}

	public boolean getIsRRHH() {
		return isRRHH;
	}

	public void setIsRRHH(boolean isRRHH) {
		this.isRRHH = isRRHH;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Date getUpdatedDateTimeUTC() {
		return updatedDateTimeUTC;
	}

	public void setUpdatedDateTimeUTC(Date UpdatedDateTimeUTC) {
		this.updatedDateTimeUTC = UpdatedDateTimeUTC;
	}
	
}
