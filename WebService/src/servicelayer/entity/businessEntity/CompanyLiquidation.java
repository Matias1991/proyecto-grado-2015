package servicelayer.entity.businessEntity;

import java.util.ArrayList;
import java.util.Date;

public class CompanyLiquidation {
	private int id;
	private double companyCategory;
	private double contribution;
	private double salaryNotPartners;
	private double salaryPartners;
	private double irae;
	private double dismissalPrevention;
	private double incidenceSalary;
	private double incidenceTickets;
	private double IVASale;
	private double IVAPurchase;
	private Employed partner1;
	private double partner1EarningsDollar;
	private double partner1EarningsPeso;
	private Employed partner2;
	private double partner2EarningsDollar;
	private double partner2EarningsPeso;
	private double typeExchange;
	private double employeesCost;
	private Date appliedDateTimeUTC;
	private Date createdDateTimeUTC;
	private double totalEarningsPeso;
	private double totalEarningsDollar;
	
	private ArrayList<Category> categoriesHuman;
	private ArrayList<Category> categoriesMaterial;
	private ArrayList<ProjectEmployed> employees;
	
	
	public CompanyLiquidation(){		
	}
	
	public CompanyLiquidation(int id){
		this.id = id;
	}

	public CompanyLiquidation(int id, double companyCategory,
			double contribution, double salaryNotPartners, double irae,
			double dismissalPrevention, double incidenceSalary, double incidenceTickets,
			double iVASale, double iVAPurchase, Employed partner1,
			double partner1EarningsDollar, double partner1EarningsPeso,
			Employed partner2, double partner2EarningsDollar,
			double partner2EarningsPeso, double typeExchange, Date appliedDateTimeUTC,
			Date createdDateTimeUTC) {
		this.id = id;
		this.companyCategory = companyCategory;
		this.contribution = contribution;
		this.salaryNotPartners = salaryNotPartners;
		this.irae = irae;
		this.dismissalPrevention = dismissalPrevention;
		this.incidenceSalary = incidenceSalary;
		this.incidenceTickets = incidenceTickets;
		this.IVASale = iVASale;
		this.IVAPurchase = iVAPurchase;
		this.partner1 = partner1;
		this.partner1EarningsDollar = partner1EarningsDollar;
		this.partner1EarningsPeso = partner1EarningsPeso;
		this.partner2 = partner2;
		this.partner2EarningsDollar = partner2EarningsDollar;
		this.partner2EarningsPeso = partner2EarningsPeso;
		this.typeExchange = typeExchange;
		this.appliedDateTimeUTC = appliedDateTimeUTC;
		this.createdDateTimeUTC = createdDateTimeUTC;
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

	public double getIrae() {
		return irae;
	}

	public void setIrae(double irae) {
		this.irae = irae;
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

	public Employed getPartner1() {
		return partner1;
	}

	public void setPartner1(Employed partner1) {
		this.partner1 = partner1;
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

	public Employed getPartner2() {
		return partner2;
	}

	public void setPartner2(Employed partner2) {
		this.partner2 = partner2;
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

	public Date getAppliedDateTimeUTC() {
		return appliedDateTimeUTC;
	}

	public void setAppliedDateTimeUTC(Date appliedDateTimeUTC) {
		this.appliedDateTimeUTC = appliedDateTimeUTC;
	}

	public Date getCreatedDateTimeUTC() {
		return createdDateTimeUTC;
	}

	public void setCreatedDateTimeUTC(Date createdDateTimeUTC) {
		this.createdDateTimeUTC = createdDateTimeUTC;
	}

	public double getTypeExchange() {
		return typeExchange;
	}

	public void setTypeExchange(double typeExchange) {
		this.typeExchange = typeExchange;
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

	public double getSalaryPartners() {
		return salaryPartners;
	}

	public void setSalaryPartners(double salaryPartners) {
		this.salaryPartners = salaryPartners;
	}

	public double getEmployeesCost() {
		return employeesCost;
	}

	public void setEmployeesCost(double employeesCost) {
		this.employeesCost = employeesCost;
	}

	public ArrayList<Category> getCategoriesHuman() {
		return categoriesHuman;
	}

	public void setCategoriesHuman(ArrayList<Category> categoriesHuman) {
		this.categoriesHuman = categoriesHuman;
	}

	public ArrayList<Category> getCategoriesMaterial() {
		return categoriesMaterial;
	}

	public void setCategoriesMaterial(ArrayList<Category> categoriesMaterial) {
		this.categoriesMaterial = categoriesMaterial;
	}

	public ArrayList<ProjectEmployed> getEmployees() {
		return employees;
	}

	public void setEmployees(ArrayList<ProjectEmployed> employees) {
		this.employees = employees;
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
	
}
