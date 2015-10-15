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
   	private double amountCharged;
   	private double totalAmount;
    private String totalAmountToShow;
    private String ivaTypeToShow;
    private String amountReceivableToShow;
    private double amountReceivable;
    
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
    		this.amountCharged = voBill.getAmountChargedDollar();
    		this.amountChargedToShow = new DecimalFormat("U$S ###,###.###").format(voBill.getAmountChargedDollar());
    		this.typeExchangeToShow = String.valueOf(this.typeExchange);
    		
    		this.totalAmount = voBill.getTotalAmountDollar();
    		this.totalAmountToShow = new DecimalFormat("U$S ###,###.###").format(voBill.getTotalAmountDollar());
    		
    		this.amountReceivable = voBill.getTotalAmountDollar() - voBill.getAmountChargedDollar();
    		this.amountReceivableToShow = new DecimalFormat("U$S ###,###.###").format(voBill.getTotalAmountDollar() - voBill.getAmountChargedDollar());
    	}
    	else
    	{
    		this.amountToShow = new DecimalFormat("$ ###,###.###").format(this.amountPeso);
    		this.amountCharged = voBill.getAmountChargedPeso();
    		this.amountChargedToShow = new DecimalFormat("$ ###,###.###").format(voBill.getAmountChargedPeso());
    		this.typeExchangeToShow = "N/A";
    		
    		this.totalAmount = voBill.getTotalAmountPeso();
    		this.totalAmountToShow = new DecimalFormat("$ ###,###.###").format(voBill.getTotalAmountPeso());
    		
    		this.amountReceivable = voBill.getTotalAmountPeso() - voBill.getAmountChargedPeso();
    		this.amountReceivableToShow = new DecimalFormat("$ ###,###.###").format(voBill.getTotalAmountPeso() - voBill.getAmountChargedPeso());
    	}
    	
    	this.ivaType = voBill.getIvaType();
    	this.ivaTypeToShow = getIvaTypeToShow(voBill.getIvaType());    	
    }
    
	public double getAmountCharged() {
		return amountCharged;
	}

	public void setAmountCharged(double amountCharged) {
		this.amountCharged = amountCharged;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public double getAmountReceivable() {
		return amountReceivable;
	}

	public void setAmountReceivable(double amountReceivable) {
		this.amountReceivable = amountReceivable;
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
