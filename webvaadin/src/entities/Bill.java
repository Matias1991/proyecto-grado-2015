package entities;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import servicelayer.service.ServiceWebStub.VOBill;

public class Bill {

	private int id;
	private String code;
	private String description;
    private boolean isCurrencyDollar;
    private double typeExchange;
    private Date appliedDateTimeUTC;
    private int projectId;
    private String projectName;
    private String appliedDateTimeUTCToShow;
    
    private int ivaType;
    
    private double amountPeso;
   	private double amountDollar;
    
    private String amountToShow;
    private String typeExchangeToShow;
   	private String amountChargedToShow;
    private String totalAmountToShow;
    private String ivaTypeToShow;
    private String amountReceivableToShow;
    
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
    	
    	this.appliedDateTimeUTCToShow = new SimpleDateFormat("MM-yyyy").format(appliedDateTimeUTC);
    	
    	if(isCurrencyDollar)
    	{
    		this.amountToShow = new DecimalFormat("U$S ###,###.###").format(this.amountDollar);
    		this.amountChargedToShow = new DecimalFormat("U$S ###,###.###").format(voBill.getAmountChargedDollar());
    		this.typeExchangeToShow = String.valueOf(this.typeExchange);
    		
    		this.totalAmountToShow = new DecimalFormat("U$S ###,###.###").format(voBill.getTotalAmountDollar());
    		
    		this.amountReceivableToShow = new DecimalFormat("U$S ###,###.###").format(voBill.getTotalAmountDollar() - voBill.getAmountChargedDollar());
    	}
    	else
    	{
    		this.amountToShow = new DecimalFormat("$ ###,###.###").format(this.amountPeso);
    		this.amountChargedToShow = new DecimalFormat("$ ###,###.###").format(voBill.getAmountChargedPeso());
    		this.typeExchangeToShow = "N/A";
    		
    		this.totalAmountToShow = new DecimalFormat("$ ###,###.###").format(voBill.getTotalAmountPeso());
    		
    		this.amountReceivableToShow = new DecimalFormat("$ ###,###.###").format(voBill.getTotalAmountPeso() - voBill.getAmountChargedPeso());
    	}
    	
    	this.ivaType = voBill.getIvaType();
    	this.ivaTypeToShow = getIvaTypeToShow(voBill.getIvaType());    	
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

	public String getTypeExchangeToShow() {
		return typeExchangeToShow;
	}

	public String getAmountChargedToShow() {
		return amountChargedToShow;
	}

	public int getIvaType() {
		return ivaType;
	}
	
	public void setIvaType(int ivaType) {
		this.ivaType = ivaType;
	}

	public String getTotalAmountToShow() {
		return totalAmountToShow;
	}

	public String getIvaTypeToShow() {
		return ivaTypeToShow;
	}
	
	String getIvaTypeToShow(int ivaTypeId) {
		
		String result = "";
		switch (ivaTypeId) {
			case 1:
				result = "0%";
				break;
			case 2:
				result = "10%";
				break;
			case 3:
				result = "22%";
				break;
			default:
				break;
		}
		
		return result;
	}

	public String getAmountReceivableToShow() {
		return amountReceivableToShow;
	}
}
