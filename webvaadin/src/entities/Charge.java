package entities;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import servicelayer.service.ServiceWebStub.VOCharge;

public class Charge {

	private int id;
	private String number;
	private String description;
    private double amount;
    private Date createdDateTimeUTC;
    private int billId;
    private String billCode;
    private String billDescription;
    private boolean billIsCurrencyDollar;
    private String createdDateTimeUTCToShow;
    private String amountToShow;
    
    public Charge()
    {
    	
    }
    
    public Charge(VOCharge voCharge)
    {
    	this.id = voCharge.getId();
    	this.number = voCharge.getNumber();
    	this.description = voCharge.getDescription();
    	this.amount = voCharge.getAmount();
    	this.createdDateTimeUTC = voCharge.getCreatedDateTimeUTC();
    	if(voCharge.getBillCode() != null)
    	{
    		this.billCode = voCharge.getBillCode();
    		this.billDescription = voCharge.getBillDescription();
    		this.billIsCurrencyDollar = voCharge.getBillIsCurrencyDollar();
    	}
    	
    	this.setCreatedDateTimeUTCToShow(new SimpleDateFormat("dd/MM/yyyy").format(createdDateTimeUTC));
    	
    	if(this.billIsCurrencyDollar)
    	{
    		this.amountToShow = new DecimalFormat("U$S ###,###.###").format(this.amount);
    	}
    	else
    	{
    		this.amountToShow = new DecimalFormat("$ ###,###.###").format(this.amount);
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
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
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

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}
}
