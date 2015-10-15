package servicelayer.entity.valueObject;

import java.io.Serializable;
import java.util.Date;

public class VOCategory implements Serializable {

	private int id;
	private int version;
	private String name;
	private String description;
	private boolean isCurrencyDollar;
	private double typeExchange;
	private int ivaTypeId;
	private Date appliedDateTimeUTC;
	private int projectId;
	private String projectName;
	private boolean projectClosed;
	private int categoryType;
	private boolean isRRHH;
	private static final long serialVersionUID = 1L;
	private Date updatedDateTimeUTC;

	private double amountPeso;
	private double amountDollar;
	private double totalAmountDollar;
	private double totalAmountPeso;
	
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

	public boolean isProjectClosed() {
		return projectClosed;
	}

	public void setProjectClosed(boolean projectClosed) {
		this.projectClosed = projectClosed;
	}

	public double getTotalAmountDollar() {
		return totalAmountDollar;
	}

	public void setTotalAmountDollar(double totalAmountDollar) {
		this.totalAmountDollar = totalAmountDollar;
	}

	public double getTotalAmountPeso() {
		return totalAmountPeso;
	}

	public void setTotalAmountPeso(double totalAmountPeso) {
		this.totalAmountPeso = totalAmountPeso;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
}
