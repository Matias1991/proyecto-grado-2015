package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import android.text.format.DateFormat;

public class VOBill implements KvmSerializable{

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
	
	public VOBill()
	{
		
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

	@Override
	public Object getProperty(int arg0) {
		
		switch(arg0)
        {
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
        }
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 10;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		switch(arg0)
        {
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
            arg2.type =  Double.class;
            arg2.name = "amountPeso";
            break;
        case 4:
            arg2.type =  Double.class;
            arg2.name = "amountDollar";
            break;
        case 5:
            arg2.type =  PropertyInfo.BOOLEAN_CLASS;
            arg2.name = "isCurrencyDollar";
            break;
        case 6:
            arg2.type =  Double.class;
            arg2.name = "typeExchange";
            break;
        case 7:
            arg2.type =  Date.class;
            arg2.name = "appliedDateTimeUTC";
            break;
        case 8:
            arg2.type =  PropertyInfo.INTEGER_CLASS;
            arg2.name = "projectId";
            break;
        case 9:
            arg2.type =  PropertyInfo.INTEGER_CLASS;
            arg2.name = "ivaType";
            break;
        default:break;
        }
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		  switch(arg0)
	        {
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
					// TODO Auto-generated catch block
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

/*
 * 
 * voBill.setDescription(bill.getDescription());
			voBill.setCode(bill.getCode());
			voBill.setAmountPeso(bill.getAmountPeso());
			voBill.setAmountDollar(bill.getAmountDollar());
			voBill.setIsCurrencyDollar(bill.getIsCurrencyDollar());
			voBill.setTypeExchange(bill.getTypeExchange());
			voBill.setAppliedDateTimeUTC(bill.getAppliedDateTimeUTC());
			voBill.setProjectId(bill.getProjectId());
			voBill.setIvaType(bill.getIvaType());
 */
