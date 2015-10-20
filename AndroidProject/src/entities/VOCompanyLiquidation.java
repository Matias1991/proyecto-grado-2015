package entities;

import java.util.Hashtable;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import com.example.androidproject.Deserialization;

public class VOCompanyLiquidation implements KvmSerializable {

	private int id;
	private double companyCategory;
	private double contribution;
	private double salaryNotPartners;
	private double salaryPartners;
	private double irae;
	private double dismissalPrevention;
	private double incidenceSalary;
	private double incidenceTickets;
	private double employeesCost;
	private double IVASale;
	private double IVAPurchase;
	private VOEmployed partner1;
	private double partner1EarningsDollar;
	private double partner1EarningsPeso;
	private VOEmployed partner2;
	private double partner2EarningsDollar;
	private double partner2EarningsPeso;
	private double typeExchange;
	private double totalEarningsPeso;
	private double totalEarningsDollar;

	public VOCompanyLiquidation(SoapObject object) {
		new Deserialization().SoapDeserialize(this, object);

		this.id = Integer.parseInt(object.getProperty("id").toString());
		this.companyCategory = Double.parseDouble(object.getProperty(
				"companyCategory").toString());
		this.contribution = Double.parseDouble(object.getProperty(
				"contribution").toString());
		this.salaryNotPartners = Double.parseDouble(object.getProperty(
				"salaryNotPartners").toString());
		this.salaryPartners = Double.parseDouble(object.getProperty(
				"salaryPartners").toString());
		this.irae = Double.parseDouble(object.getProperty("irae").toString());
		this.dismissalPrevention = Double.parseDouble(object.getProperty(
				"dismissalPrevention").toString());
		this.incidenceSalary = Double.parseDouble(object.getProperty(
				"incidenceSalary").toString());
		this.incidenceTickets = Double.parseDouble(object.getProperty(
				"incidenceTickets").toString());
		this.employeesCost = Double.parseDouble(object.getProperty(
				"employeesCost").toString());
		this.IVASale = Double.parseDouble(object.getProperty("IVASale")
				.toString());
		this.IVAPurchase = Double.parseDouble(object.getProperty("IVAPurchase")
				.toString());
		this.partner1EarningsDollar = Double.parseDouble(object.getProperty(
				"partner1EarningsDollar").toString());
		this.partner1EarningsPeso = Double.parseDouble(object.getProperty(
				"partner1EarningsPeso").toString());
		this.partner2EarningsDollar = Double.parseDouble(object.getProperty(
				"partner2EarningsDollar").toString());
		this.partner2EarningsPeso = Double.parseDouble(object.getProperty(
				"partner2EarningsPeso").toString());
		this.typeExchange = Double.parseDouble(object.getProperty(
				"typeExchange").toString());
		this.totalEarningsPeso = Double.parseDouble(object.getProperty(
				"totalEarningsPeso").toString());
		this.totalEarningsDollar = Double.parseDouble(object.getProperty(
				"totalEarningsDollar").toString());
		this.partner1 = new VOEmployed(
				(SoapObject) object.getProperty("partner1"));
		this.partner2 = new VOEmployed(
				(SoapObject) object.getProperty("partner2"));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getCompanyCategory() {
		return companyCategory;
	}

	public void setCompanyCategory(double companyCategory) {
		this.companyCategory = companyCategory;
	}

	public double getContribution() {
		return contribution;
	}

	public void setContribution(double contribution) {
		this.contribution = contribution;
	}

	public double getSalaryNotPartners() {
		return salaryNotPartners;
	}

	public void setSalaryNotPartners(double salaryNotPartners) {
		this.salaryNotPartners = salaryNotPartners;
	}

	public double getSalaryPartners() {
		return salaryPartners;
	}

	public void setSalaryPartners(double salaryPartners) {
		this.salaryPartners = salaryPartners;
	}

	public double getIrae() {
		return irae;
	}

	public void setIrae(double irae) {
		this.irae = irae;
	}

	public double getDismissalPrevention() {
		return dismissalPrevention;
	}

	public void setDismissalPrevention(double dismissalPrevention) {
		this.dismissalPrevention = dismissalPrevention;
	}

	public double getIncidenceSalary() {
		return incidenceSalary;
	}

	public void setIncidenceSalary(double incidenceSalary) {
		this.incidenceSalary = incidenceSalary;
	}

	public double getIncidenceTickets() {
		return incidenceTickets;
	}

	public void setIncidenceTickets(double incidenceTickets) {
		this.incidenceTickets = incidenceTickets;
	}

	public double getEmployeesCost() {
		return employeesCost;
	}

	public void setEmployeesCost(double employeesCost) {
		this.employeesCost = employeesCost;
	}

	public double getIVASale() {
		return IVASale;
	}

	public void setIVASale(double iVASale) {
		IVASale = iVASale;
	}

	public double getIVAPurchase() {
		return IVAPurchase;
	}

	public void setIVAPurchase(double iVAPurchase) {
		IVAPurchase = iVAPurchase;
	}

	public double getPartner1EarningsDollar() {
		return partner1EarningsDollar;
	}

	public void setPartner1EarningsDollar(double partner1EarningsDollar) {
		this.partner1EarningsDollar = partner1EarningsDollar;
	}

	public double getPartner1EarningsPeso() {
		return partner1EarningsPeso;
	}

	public void setPartner1EarningsPeso(double partner1EarningsPeso) {
		this.partner1EarningsPeso = partner1EarningsPeso;
	}

	public double getPartner2EarningsDollar() {
		return partner2EarningsDollar;
	}

	public void setPartner2EarningsDollar(double partner2EarningsDollar) {
		this.partner2EarningsDollar = partner2EarningsDollar;
	}

	public double getPartner2EarningsPeso() {
		return partner2EarningsPeso;
	}

	public void setPartner2EarningsPeso(double partner2EarningsPeso) {
		this.partner2EarningsPeso = partner2EarningsPeso;
	}

	public double getTypeExchange() {
		return typeExchange;
	}

	public void setTypeExchange(double typeExchange) {
		this.typeExchange = typeExchange;
	}

	public double getTotalEarningsPeso() {
		return totalEarningsPeso;
	}

	public void setTotalEarningsPeso(double totalEarningsPeso) {
		this.totalEarningsPeso = totalEarningsPeso;
	}

	public double getTotalEarningsDollar() {
		return totalEarningsDollar;
	}

	public void setTotalEarningsDollar(double totalEarningsDollar) {
		this.totalEarningsDollar = totalEarningsDollar;
	}
	
	public VOEmployed getPartner1() {
		return partner1;
	}

	public void setPartner1(VOEmployed partner1) {
		this.partner1 = partner1;
	}
	
	public VOEmployed getPartner2() {
		return partner2;
	}

	public void setPartner2(VOEmployed partner2) {
		this.partner2 = partner2;
	}

	public String getPartner1FullName(){
		return this.partner1.getName() + " " + this.partner1.getLastName();
	}

	public String getPartner2FullName(){
		return this.partner2.getName() + " " + this.partner2.getLastName();
	}

	@Override
	public Object getProperty(int arg0) {

		switch (arg0) {
		case 0:
			return id;
		case 1:
			return companyCategory;
		case 2:
			return contribution;
		case 3:
			return salaryNotPartners;
		case 4:
			return salaryPartners;
		case 5:
			return irae;
		case 6:
			return dismissalPrevention;
		case 7:
			return incidenceSalary;
		case 8:
			return incidenceTickets;
		case 9:
			return employeesCost;
		case 10:
			return IVASale;
		case 11:
			return IVAPurchase;
		case 12:
			return partner1EarningsDollar;
		case 13:
			return partner1EarningsPeso;
		case 14:
			return partner2EarningsDollar;
		case 15:
			return partner2EarningsPeso;
		case 16:
			return typeExchange;
		case 17:
			return totalEarningsPeso;
		case 18:
			return totalEarningsDollar;
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
			arg2.type = Double.class;
			arg2.name = "IVAPurchase";
			break;
		case 1:
			arg2.type = Double.class;
			arg2.name = "IVASale";
			break;
		case 15:
			arg2.type = Double.class;
			arg2.name = "irae";
			break;
		case 20:
			arg2.type = Double.class;
			arg2.name = "partner2EarningsDollar";
			break;
		case 21:
			arg2.type = Double.class;
			arg2.name = "partner2EarningsPeso";
			break;
		case 24:
			arg2.type = Double.class;
			arg2.name = "totalEarningsDollar";
			break;
		case 25:
			arg2.type = Double.class;
			arg2.name = "totalEarningsPeso";
			break;
		default:
			break;
		}
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		switch (arg0) {
		case 0:
			IVAPurchase = Double.parseDouble(arg1.toString());
			break;
		case 1:
			IVASale = Double.parseDouble(arg1.toString());
			break;
		case 15:
			irae = Double.parseDouble(arg1.toString());
			break;
		case 20:
			partner2EarningsDollar = Double.parseDouble(arg1.toString());
			break;
		case 21:
			partner2EarningsPeso = Double.parseDouble(arg1.toString());
			break;
		case 24:
			totalEarningsDollar = Double.parseDouble(arg1.toString());
			break;
		case 25:
			totalEarningsPeso = Double.parseDouble(arg1.toString());
			break;
		}
	}

}
