package servicelayer.entity.businessEntity;

import java.util.Date;

import servicelayer.entity.valueObject.VOCategory;

public class Category {

	private int id;
	private int version;
	private String description;
	private double amountPeso;
	private double amountDollar;
	private boolean isCurrencyDollar;
	private double typeExchange;
	private int ivaTypeId;
	private Date appliedDateTimeUTC;
	private Project project;
	private CategoryType categoryType;
	private boolean isRRHH;
	private Date updatedDateTimeUTC;

	public Category() {
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

	public double getAmountPeso() {
		return amountPeso;
	}

	public void setAmountPeso(double amountPeso) {
		this.amountPeso = amountPeso;
	}

	public double getAmountDollar() {
		return amountDollar;
	}

	public void setAmountDollar(double amountDollar) {
		this.amountDollar = amountDollar;
	}

	public boolean getIsCurrencyDollar() {
		return isCurrencyDollar;
	}

	public void setIsCurrencyDollar(boolean isCurrencyDollar) {
		this.isCurrencyDollar = isCurrencyDollar;
	}

	public double getTypeExchange() {
		return typeExchange;
	}

	public void setTypeExchange(double typeExchange) {
		this.typeExchange = typeExchange;
	}
	
	public int getIvaTypeId() {
		return ivaTypeId;
	}

	public void setIvaTypeId(int ivaTypeId) {
		this.ivaTypeId = ivaTypeId;
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
