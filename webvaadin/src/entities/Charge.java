package entities;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import servicelayer.service.ServiceWebStub.VOCharge;

public class Charge {

	private int id;
	private String number;
	private String description;
    private double amountPeso;
	private double amountDollar;
    private boolean isCurrencyDollar;
    private double typeExchange;
    private Date createdDateTimeUTC;
    private int billId;
    private String billCode;
    private String billDescription;
    private String createdDateTimeUTCToShow;
    private String amountToShow;
    private String typeExchangeToShow;
    
    public Charge()
    {
    	
    }
    
    public Charge(VOCharge voCharge)
    {
    	this.id = voCharge.getId();
    	this.number = voCharge.getNumber();
    	this.description = voCharge.getDescription();
    	this.amountDollar = voCharge.getAmountDollar();
    	this.amountPeso = voCharge.getAmountPeso();
    	this.isCurrencyDollar = voCharge.getIsCurrencyDollar();
    	this.typeExchange = voCharge.getTypeExchange();
    	this.createdDateTimeUTC = voCharge.getCreatedDateTimeUTC();
    	if(voCharge.getBillCode() != null)
    	{
    		this.billCode = voCharge.getBillCode();
    		this.billDescription = voCharge.getBillDescription();
    	}
    	
    	this.setCreatedDateTimeUTCToShow(new SimpleDateFormat("dd/MM/yyyy").format(createdDateTimeUTC));
    	
    	if(isCurrencyDollar)
    	{
    		this.amountToShow = new DecimalFormat("U$S ###,###.###").format(this.amountDollar);
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
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
	public Date getCreatedDateTimeUTC() {
		return createdDateTimeUTC;
	}
	public void setCreatedDateTimeUTC(Date createdDateTimeUTC) {
		this.createdDateTimeUTC = createdDateTimeUTC;
	}
	public int getBillId() {
		return billId;
	}
	public void setBillId(int billId) {
		this.billId = billId;
	}
	public String getBillDescription() {
		return billDescription;
	}
	public void setBillDescription(String billDescription) {
		this.billDescription = billDescription;
	}
	public String getCreatedDateTimeUTCToShow() {
		return createdDateTimeUTCToShow;
	}
	public void setCreatedDateTimeUTCToShow(String createdDateTimeUTCToShow) {
		this.createdDateTimeUTCToShow = createdDateTimeUTCToShow;
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

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
}
