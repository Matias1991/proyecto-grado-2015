package entities;

import java.util.Date;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.example.androidproject.Deserialization;

public class VOCharge implements KvmSerializable {

	private int id;
	private String number;
	private String description;
	private double amount;	
	private Date createdDateTimeUTC;
	private int billId;
	private String billCode;
	private String billDescription;
	private boolean billIsCurrencyDollar;

	public VOCharge() {

	}

	public VOCharge(SoapObject object) {
		new Deserialization().SoapDeserialize(this, object);

		this.id = Integer.parseInt(object.getProperty("id").toString());
		this.number = object.getProperty("number").toString();
		this.description = object.getProperty("description").toString();
		this.amount = Double.parseDouble(object.getProperty("amount").toString());
		this.billIsCurrencyDollar = Boolean.parseBoolean(object.getProperty("billIsCurrencyDollar")
				.toString());
		this.billId = Integer.parseInt(object.getProperty("billId").toString());
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

	public void setNumber(String code) {
		this.number = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public boolean getBillIsCurrencyDollar() {
		return billIsCurrencyDollar;
	}

	public void setBillIsCurrencyDollar(boolean billIsCurrencyDollar) {
		this.billIsCurrencyDollar = billIsCurrencyDollar;
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

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public Object getProperty(int arg0) {
		switch (arg0) {
		case 7:
			return id;
		case 0:
			return amount;
		case 8:
			return number;
		case 6:
			return description;		
		case 4:
			return billIsCurrencyDollar;
		case 5:
			return createdDateTimeUTC;
		case 3:
			return billId;
		case 1:
			return billCode;
		case 2:
			return billDescription;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 11;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		switch (arg0) {
		case 7:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "id";
			break;
		case 8:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "number";
			break;
		case 6:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "description";
			break;
		case 0:
			arg2.type = Double.class;
			arg2.name = "amount";
			break;		
		case 4:
			arg2.type = PropertyInfo.BOOLEAN_CLASS;
			arg2.name = "billIsCurrencyDollar";
			break;		
		case 5:
			arg2.type = Date.class;
			arg2.name = "createdDateTimeUTC";
			break;
		case 3:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "billId";
			break;
		case 1:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "billCode";
			break;
		case 2:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "billDescription";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		switch (arg0) {
		case 0:
			id = Integer.parseInt(arg1.toString());
			break;
		case 1:
			number = arg1.toString();
			break;
		case 2:
			description = arg1.toString();
			break;	
		case 5:
			billIsCurrencyDollar = Boolean.parseBoolean(arg1.toString());
			break;		
		}
	}
}
