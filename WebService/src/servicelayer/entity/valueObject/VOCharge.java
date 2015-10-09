package servicelayer.entity.valueObject;

import java.io.Serializable;
import java.util.Date;

public class VOCharge implements Serializable{

	private static final long serialVersionUID = 1L;
	private int id;
	private String number;
	private String description;
	private double amount;
	private Date createdDateTimeUTC;
	private int billId;
	private String billCode;
	private String billDescription;
	private boolean billIsCurrencyDollar;
	
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
	
	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getBillDescription() {
		return billDescription;
	}

	public void setBillDescription(String billDescription) {
		this.billDescription = billDescription;
	}

	public int getBillId() {
		return billId;
	}

	public void setBillId(int billId) {
		this.billId = billId;
	}

	public boolean getBillIsCurrencyDollar() {
		return billIsCurrencyDollar;
	}

	public void setBillIsCurrencyDollar(boolean billIsCurrencyDollar) {
		this.billIsCurrencyDollar = billIsCurrencyDollar;
	}
}
