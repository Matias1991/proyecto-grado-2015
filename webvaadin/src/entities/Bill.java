package entities;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import servicelayer.service.ServiceWebStub.VOBill;

public class Bill {

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
    private String appliedDateTimeUTCToShow;
    private String amountToShow;
    private String typeExchangeToShow;
    
    public Bill()
    {
    	
    }
    
    public Bill(VOBill voBill)
    {
    	this.id = voBill.getId();
    	this.code = voBill.getCode();
    	this.description = voBill.getDescription();
    	this.amountDollar = voBill.getAmountDollar();
    	this.amountPeso = voBill.getAmountPeso();
    	this.isCurrencyDollar = voBill.getIsCurrencyDollar();
    	this.typeExchange = voBill.getTypeExchange();
    	this.appliedDateTimeUTC = voBill.getAppliedDateTimeUTC();
    	if(voBill.getProjectId() != 0)
    	{
    		this.projectId = voBill.getProjectId();
    		this.projectName = voBill.getProjectName();
    	}
    	
    	this.setAppliedDateTimeUTCToShow(new SimpleDateFormat("MM-yyyy").format(appliedDateTimeUTC));
    	
    	if(isCurrencyDollar)
    	{
    		this.amountToShow = new DecimalFormat("U$ ###,###.###").format(this.amountDollar);
    		this.typeExchangeToShow = String.valueOf(this.typeExchange);
    	}
    	else
    	{
    		this.amountToShow = new DecimalFormat("$ ###,###.###").format(this.amountPeso);
    		this.typeExchangeToShow = "N/A";
    	}
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

	public String getAmountToShow() {
		return amountToShow;
	}

	public void setAmountToShow(String amountToShow) {
		this.amountToShow = amountToShow;
	}

	public String getTypeExchangeToShow() {
		return typeExchangeToShow;
	}

	public void setTypeExchangeToShow(String typeExchangeToShow) {
		this.typeExchangeToShow = typeExchangeToShow;
	}
}
