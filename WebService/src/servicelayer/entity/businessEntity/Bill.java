package servicelayer.entity.businessEntity;

import java.util.Date;

import servicelayer.entity.valueObject.VOBill;

public class Bill {

	private int id;
	private String code;
    private String description;
    private double amountPeso;
    private double amountDollar;
    private boolean isCurrencyDollar;
	private double typeExchange;
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
    	this.amountPeso = voBill.getAmountPeso();
    	this.amountDollar = voBill.getAmountDollar();
    	this.isCurrencyDollar = voBill.getIsCurrencyDollar();
    	this.typeExchange = voBill.getTypeExchange();
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
}
