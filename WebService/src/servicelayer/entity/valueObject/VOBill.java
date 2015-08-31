package servicelayer.entity.valueObject;

import java.io.Serializable;
import java.util.Date;

public class VOBill implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	private String code;
	private String description;
	private double amountPeso;
	private double amountDollar;
	private boolean isCurrencyDollar;
	private double typeExchange;
	private Date appliedDateTimeUTC;
	private int projectId;
	private String projectName;
	private boolean projectClosed;
	private boolean isLiquidated;
	private double amountChargedDollar;
	private double amountChargedPeso;

	public double getAmountChargedDollar() {
		return amountChargedDollar;
	}

	public void setAmountChargedDollar(double amountChargedDollar) {
		this.amountChargedDollar = amountChargedDollar;
	}

	public double getAmountChargedPeso() {
		return amountChargedPeso;
	}

	public void setAmountChargedPeso(double amountChargedPeso) {
		this.amountChargedPeso = amountChargedPeso;
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

	public boolean isProjectClosed() {
		return projectClosed;
	}

	public void setProjectClosed(boolean projectClosed) {
		this.projectClosed = projectClosed;
	}
}
