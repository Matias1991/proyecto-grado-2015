package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.example.androidproject.Deserialization;

public class VOBill implements KvmSerializable {

	private int id;
	private String code;
	private String description;
	private double amountPeso;
	private double amountDollar;
	private boolean isCurrencyDollar;
	private double typeExchange;
	private Date appliedDateTimeUTC;
	private int projectId;
	private int ivaType;
	private double amountChargedDollar;
	private double amountChargedPeso;

	public VOBill() {
	}

	public VOBill(SoapObject object) {
		new Deserialization().SoapDeserialize(this, object);

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");

		this.id = Integer.parseInt(object.getProperty(7).toString());
		this.isCurrencyDollar = Boolean.parseBoolean(object.getProperty(8)
				.toString());
		this.code = object.getProperty(5).toString();
		this.description = object.getProperty(5).toString();
		this.amountPeso = Double.parseDouble(object.getProperty(3).toString());
		this.amountDollar = Double
				.parseDouble(object.getProperty(2).toString());
		this.typeExchange = Double.parseDouble(object.getProperty(16)
				.toString());
		this.projectId = Integer.parseInt(object.getProperty(12).toString());
		this.ivaType = Integer.parseInt(object.getProperty(9).toString());

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

	public int getIvaType() {
		return ivaType;
	}

	public void setIvaType(int ivaType) {
		this.ivaType = ivaType;
	}

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

	@Override
	public Object getProperty(int arg0) {

		switch (arg0) {
		case 0:
			return id;
		case 1:
			return code;
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
			return appliedDateTimeUTC;
		case 8:
			return projectId;
		case 9:
			return ivaType;
		case 10:
			return amountChargedDollar;
		case 11:
			return amountChargedPeso;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 10;
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
			arg2.name = "code";
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
			arg2.name = "appliedDateTimeUTC";
			break;
		case 8:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "projectId";
			break;
		case 9:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "ivaType";
			break;
		case 10:
			arg2.type = Double.class;
			arg2.name = "amountChargedDollar";
			break;
		case 11:
			arg2.type = Double.class;
			arg2.name = "amountChargedPeso";
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
			code = arg1.toString();
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
		case 7:
			SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
			try {
				appliedDateTimeUTC = format.parse(arg1.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case 8:
			projectId = Integer.parseInt(arg1.toString());
			break;
		case 9:
			ivaType = Integer.parseInt(arg1.toString());
			break;
		}
	}
}