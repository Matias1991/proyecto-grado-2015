package entities;

import java.util.Date;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.example.androidproject.Deserialization;

public class VOCharge implements KvmSerializable {

	private int id;
	private String chargeCode;
	private String description;
	private double amountPeso;
	private double amountDollar;
	private boolean isCurrencyDollar;
	private double typeExchange;
	private Date createdDateTimeUTC;
	private int billId;
	private String billCode;
	private String billDescription;

	public VOCharge() {

	}

	public VOCharge(SoapObject object) {
		new Deserialization().SoapDeserialize(this, object);

		this.id = Integer.parseInt(object.getProperty(0).toString());
		this.chargeCode = object.getProperty(1).toString();
		this.description = object.getProperty(2).toString();
		this.amountPeso = Double.parseDouble(object.getProperty(3).toString());
		this.amountDollar = Double
				.parseDouble(object.getProperty(4).toString());
		this.isCurrencyDollar = Boolean.parseBoolean(object.getProperty(5)
				.toString());
		this.typeExchange = Double
				.parseDouble(object.getProperty(6).toString());
		// this.createdDateTimeUTC
		this.billId = Integer.parseInt(object.getProperty(7).toString());
		// this.billCode
		// this.billDescription
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChargeCode() {
		return chargeCode;
	}

	public void setChargeCode(String code) {
		this.chargeCode = code;
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

	@Override
	public Object getProperty(int arg0) {
		switch (arg0) {
		case 0:
			return id;
		case 1:
			return chargeCode;
		case 2:
			return description;
		case 3:
			return amountPeso;
		case 4:
			return amountDollar;
		case 5:
			return isCurrencyDollar;
		case 6:
			return typeExchange;
		case 7:
			return createdDateTimeUTC;
		case 8:
			return billId;
		case 9:
			return billCode;
		case 10:
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
		case 0:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "id";
			break;
		case 1:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "chargeCode";
			break;
		case 2:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "description";
			break;
		case 3:
			arg2.type = Double.class;
			arg2.name = "amountPeso";
			break;
		case 4:
			arg2.type = Double.class;
			arg2.name = "amountDollar";
			break;
		case 5:
			arg2.type = PropertyInfo.BOOLEAN_CLASS;
			arg2.name = "isCurrencyDollar";
			break;
		case 6:
			arg2.type = Double.class;
			arg2.name = "typeExchange";
			break;
		case 7:
			arg2.type = Date.class;
			arg2.name = "createdDateTimeUTC";
			break;
		case 8:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "billId";
			break;
		case 9:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "billCode";
			break;
		case 10:
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
			chargeCode = arg1.toString();
			break;
		case 2:
			description = arg1.toString();
			break;
		case 3:
			amountPeso = Double.parseDouble(arg1.toString());
			break;
		case 4:
			amountDollar = Double.parseDouble(arg1.toString());
			break;
		case 5:
			isCurrencyDollar = Boolean.parseBoolean(arg1.toString());
			break;
		case 6:
			typeExchange = Double.parseDouble(arg1.toString());
			break;
		}
	}
}
