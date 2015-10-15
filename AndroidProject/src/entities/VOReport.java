package entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import android.R.integer;

public class VOReport implements KvmSerializable {
	private static final long serialVersionUID = 1L;

	private int id;
	private int projectId;
	private Date createdDateTimeUTC;
	private Date appliedDateTimeUTC;
	private VOBill[] bills;
	private Double reserve;
	private Double sale;
	private String partner1Name;
	private String partner1Lastname;
	private String partner1Distribution;
	private String partner2Name;
	private String partner2Lastname;
	private String partner2Distribution;
	private boolean isCurrencyDollar;

	private double earnings;
	private double partner1Earning;
	private double partner2Earning;
	private double totalBills;
	private double totalCostCategoriesHuman;
	private double totalCostCategoriesMaterial;
	private double totalCostEmployees;
	private VOProject project;

	public VOProject getProject() {
		return project;
	}

	public void setProject(VOProject project) {
		this.project = project;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public Date getCreatedDateTimeUTC() {
		return createdDateTimeUTC;
	}

	public void setCreatedDateTimeUTC(Date createdDateTimeUTC) {
		this.createdDateTimeUTC = createdDateTimeUTC;
	}

	public Date getAppliedDateTimeUTC() {
		return appliedDateTimeUTC;
	}

	public void setAppliedDateTimeUTC(Date appliedDateTimeUTC) {
		this.appliedDateTimeUTC = appliedDateTimeUTC;
	}

	public VOBill[] getBills() {
		return bills;
	}

	public void setBills(VOBill[] bills) {
		this.bills = bills;
	}

	public Double getReserve() {
		return reserve;
	}

	public void setReserve(Double reserve) {
		this.reserve = reserve;
	}

	public Double getSale() {
		return sale;
	}

	public void setSale(Double sale) {
		this.sale = sale;
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

	public String getPartner1Distribution() {
		return partner1Distribution;
	}

	public void setPartner1Distribution(String partner1Distribution) {
		this.partner1Distribution = partner1Distribution;
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

	public String getPartner2Distribution() {
		return partner2Distribution;
	}

	public void setPartner2Distribution(String partner2Distribution) {
		this.partner2Distribution = partner2Distribution;
	}

	public boolean isCurrencyDollar() {
		return isCurrencyDollar;
	}

	public void setCurrencyDollar(boolean isCurrencyDollar) {
		this.isCurrencyDollar = isCurrencyDollar;
	}

	public double getEarnings() {
		return earnings;
	}

	public void setEarnings(double earnings) {
		this.earnings = earnings;
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

	public double getTotalCostCategoriesHuman() {
		return totalCostCategoriesHuman;
	}

	public void setTotalCostCategoriesHuman(double totalCostCategoriesHuman) {
		this.totalCostCategoriesHuman = totalCostCategoriesHuman;
	}

	public double getTotalCostCategoriesMaterial() {
		return totalCostCategoriesMaterial;
	}

	public void setTotalCostCategoriesMaterial(
			double totalCostCategoriesMaterial) {
		this.totalCostCategoriesMaterial = totalCostCategoriesMaterial;
	}

	public double getTotalCostEmployees() {
		return totalCostEmployees;
	}

	public void setTotalCostEmployees(double totalCostEmployees) {
		this.totalCostEmployees = totalCostEmployees;
	}

	@Override
	public Object getProperty(int arg0) {

		switch (arg0) {
		case 0:
			return id;
		case 1:
			return projectId;
		case 2:
			return createdDateTimeUTC;
		case 3:
			return appliedDateTimeUTC;
		case 4:
			return reserve;
		case 5:
			return sale;
		case 6:
			return partner1Name;
		case 7:
			return partner1Lastname;
		case 8:
			return partner2Name;
		case 9:
			return partner2Lastname;
		case 10:
			return isCurrencyDollar;
		case 11:
			return earnings;
		case 12:
			return partner1Earning;
		case 13:
			return partner2Earning;
		case 14:
			return totalBills;
		case 15:
			return totalCostCategoriesHuman;
		case 16:
			return totalCostCategoriesMaterial;
		case 17:
			return totalCostEmployees;
		case 18:
			return project;
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
			arg2.type = PropertyInfo.INTEGER_CLASS;
			arg2.name = "projectId";
			break;
		case 2:
			arg2.type = Date.class;
			arg2.name = "createdDateTimeUTC";
			break;
		case 3:
			arg2.type = Date.class;
			arg2.name = "appliedDateTimeUTC";
			break;
		case 4:
			arg2.type = Double.class;
			arg2.name = "reserve";
			break;
		case 5:
			arg2.type = Double.class;
			arg2.name = "sale";
			break;
		case 6:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "partner1Name";
			break;
		case 7:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "partner1Lastname";
			break;
		case 8:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "partner2Name";
			break;
		case 9:
			arg2.type = PropertyInfo.STRING_CLASS;
			arg2.name = "partner2Lastname";
			break;
		case 10:
			arg2.type = PropertyInfo.BOOLEAN_CLASS;
			arg2.name = "isCurrencyDollar";
			break;
		case 11:
			arg2.type = Double.class;
			arg2.name = "earnings";
			break;
		case 12:
			arg2.type = Double.class;
			arg2.name = "partner1Earning";
			break;
		case 13:
			arg2.type = Double.class;
			arg2.name = "partner2Earning";
			break;
		case 14:
			arg2.type = Double.class;
			arg2.name = "totalBills";
			break;
		case 15:
			arg2.type = Double.class;
			arg2.name = "totalCostCategoriesHuman";
			break;
		case 16:
			arg2.type = Double.class;
			arg2.name = "totalCostCategoriesMaterial";
			break;
		case 17:
			arg2.type = Double.class;
			arg2.name = "totalCostEmployees";
			break;
		case 18:
			arg2.type = VOProject.class;
			arg2.name = "project";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int arg0, Object arg1) {

		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
		
		switch (arg0) {
		case 0:
			id = Integer.parseInt(arg1.toString());
			break;
		case 1:
			projectId = Integer.parseInt(arg1.toString());
			break;
		case 2:
			try {
				createdDateTimeUTC = format.parse(arg1.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case 3:
			try {
				appliedDateTimeUTC = format.parse(arg1.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			break;
		case 4:
			reserve = Double.parseDouble(arg1.toString());
			break;
		case 5:
			sale = Double.parseDouble(arg1.toString());
			break;
		case 6:
			partner1Name = arg1.toString();
			break;
		case 7:
			partner1Lastname = arg1.toString();
			break;
		case 8:
			partner2Name = arg1.toString();
			break;
		case 9:
			partner2Lastname = arg1.toString();
			break;
		case 10:
			isCurrencyDollar = Boolean.parseBoolean(arg1.toString());
			break;
		case 11:
			earnings = Double.parseDouble(arg1.toString());
			break;
		case 12:
			partner1Earning = Double.parseDouble(arg1.toString());
			break;
		case 13:
			partner2Earning = Double.parseDouble(arg1.toString());
			break;
		case 14:
			totalBills = Double.parseDouble(arg1.toString());
			break;
		case 15:
			totalCostCategoriesHuman = Double.parseDouble(arg1.toString());
			break;
		case 16:
			totalCostCategoriesMaterial = Double.parseDouble(arg1.toString());
			break;
		case 17:
			totalCostEmployees = Double.parseDouble(arg1.toString());
			break;
		}
	}
}