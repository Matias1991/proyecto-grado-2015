package entities;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.example.androidproject.Deserialization;

public class VOProjectLiquidation implements KvmSerializable {

	private int id;
	private double reserve;
	private double earnings;
	private VOProject project;
	// Socios
	private String partner1Name;
	private String partner1Lastname;
	private String partner2Name;
	private String partner2Lastname;
	private boolean isCurrencyDollar;
	private double partner1Earning;
	private double partner2Earning;
	private double totalBills;

	public VOProjectLiquidation(int id, double reserve, double earning,
			VOProject voProject) {
		 this.id = id;
		 this.reserve = reserve;
		 this.earnings = earning;
		 this.project = voProject;

	}

	public VOProjectLiquidation(SoapObject object) {
		new Deserialization().SoapDeserialize(this, object);
	
		this.id = Integer.parseInt(object.getProperty("id").toString());
		this.earnings = Double.parseDouble(object.getProperty("earnings").toString());
		this.reserve = Double.parseDouble(object.getProperty("reserve").toString());
		this.project = new VOProject((SoapObject) object.getProperty("project"));
		if(object.getProperty("partner1Lastname") != null){
			this.partner1Name = object.getProperty("partner1Name").toString();
			this.partner1Lastname = object.getProperty("partner1Lastname").toString();
			this.partner2Name = object.getProperty("partner2Name").toString();
			this.partner2Lastname = object.getProperty("partner2Lastname").toString();
		}
		this.isCurrencyDollar = Boolean.parseBoolean(object.getProperty("currencyDollar").toString());
		this.partner1Earning = Double.parseDouble(object.getProperty("partner1Earning").toString());
		this.partner2Earning = Double.parseDouble(object.getProperty("partner2Earning").toString());
		this.totalBills = Double.parseDouble(object.getProperty("totalBills").toString());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getReserve() {
		return reserve;
	}

	public void setReserve(double reserve) {
		this.reserve = reserve;
	}

	public double getEarnings() {
		return earnings;
	}

	public void setEarnings(double earnings) {
		this.earnings = earnings;
	}

	public VOProject getProject() {
		return project;
	}

	public void setProject(VOProject project) {
		this.project = project;
	}

	public String getPartner1Name() {
		return partner1Name;
	}

	public void setPartner1Name(String partner1Name) {
		this.partner1Name = partner1Name;
	}

	public String getPartner1Lastname() {
		return partner1Lastname;
	}

	public void setPartner1Lastname(String partner1Lastname) {
		this.partner1Lastname = partner1Lastname;
	}

	public String getPartner2Name() {
		return partner2Name;
	}

	public void setPartner2Name(String partner2Name) {
		this.partner2Name = partner2Name;
	}

	public String getPartner2Lastname() {
		return partner2Lastname;
	}

	public void setPartner2Lastname(String partner2Lastname) {
		this.partner2Lastname = partner2Lastname;
	}

	public boolean isCurrencyDollar() {
		return isCurrencyDollar;
	}

	public void setCurrencyDollar(boolean isCurrencyDollar) {
		this.isCurrencyDollar = isCurrencyDollar;
	}

	public double getPartner1Earning() {
		return partner1Earning;
	}

	public void setPartner1Earning(double partner1Earning) {
		this.partner1Earning = partner1Earning;
	}

	public double getPartner2Earning() {
		return partner2Earning;
	}

	public void setPartner2Earning(double partner2Earning) {
		this.partner2Earning = partner2Earning;
	}

	public double getTotalBills() {
		return totalBills;
	}

	public void setTotalBills(double totalBills) {
		this.totalBills = totalBills;
	}

	@Override
	public Object getProperty(int arg0) {

		switch (arg0) {
		case 0:
			return id;
		case 1:
			return reserve;
		case 2:
			return earnings;
		case 3:
			return project;
		case 4:
			return partner1Name;
		case 5:
			return partner1Lastname;
		case 6:
			return partner2Name;
		case 7:
			return partner2Lastname;
		case 8:
			return isCurrencyDollar;
		case 9:
			return partner1Earning;
		case 10:
			return partner2Earning;
		case 11:
			return totalBills;
		}
		return null;
	}

	@Override
	public int getPropertyCount() {
		return 12;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		System.out.println("getPropertyInfo");
		switch (arg0) {
		case 0:
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "id";
			break;
		case 1:
			arg2.type = Double.class;
			arg2.name = "reserve";
			break;
		case 2:
			arg2.type = Double.class;
			arg2.name = "earnings";
			break;
		case 3:
			arg2.type = VOProject.class;
			arg2.name = "project";
			break;
		case 4:
			arg2.type = String.class;
			arg2.name = "partner1Name";
			break;
		case 5:
			arg2.type = String.class;
			arg2.name = "partner1Lastname";
			break;
		case 6:
			arg2.type = String.class;
			arg2.name = "partner2Name";
			break;
		case 7:
			arg2.type = String.class;
			arg2.name = "partner2Lastname";
			break;
		case 8:
			arg2.type = Boolean.class;
			arg2.name = "isCurrencyDollar";
			break;
		case 9:
			arg2.type = Double.class;
			arg2.name = "partner1Earning";
			break;
		case 10:
			arg2.type = Double.class;
			arg2.name = "partner2Earning";
			break;
		case 11:
			arg2.type = Double.class;
			arg2.name = "totalBills";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		System.out.println("setProperty");
		switch (arg0) {
		case 0:
			id = Integer.parseInt(arg1.toString());
			break;
		case 1:
			reserve = Double.parseDouble(arg1.toString());
			break;
		case 2:
			earnings = Double.parseDouble(arg1.toString());
			break;
		case 4:
			partner1Name = arg1.toString();
			break;
		case 5:
			partner1Lastname = arg1.toString();
			break;
		case 6:
			partner2Name = arg1.toString();
			break;
		case 7:
			partner2Lastname = arg1.toString();
			break;
		case 8:
			isCurrencyDollar = Boolean.getBoolean(arg1.toString());
			break;
		case 9:
			partner1Earning = Double.parseDouble(arg1.toString());
			break;
		case 10:
			partner2Earning = Double.parseDouble(arg1.toString());
			break;
		case 11:
			totalBills = Double.parseDouble(arg1.toString());
			break;
		}
	}

}
