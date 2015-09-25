package servicelayer.entity.businessEntity;

import java.util.ArrayList;
import java.util.Date;

public class ProjectLiquidation implements Comparable{
	
	private int id;
	private Project project;
	private Date createdDateTimeUTC;
	private Date appliedDateTimeUTC;
	private double totalBills;
	private double totalCostCategoriesHuman;
	private double totalCostCategoriesMaterial;
	private double totalCostEmployees;
	private double earnings;
	private double reserve;
	private double sale;
	private Employed employedPartner1;
	private ProjectPartner partner1;
	private double partner1Earning;
	private Employed employedPartner2;
	private ProjectPartner partner2;
	private double partner2Earning;
	private boolean isCurrencyDollar;
	
	private ArrayList<Bill> bills;
	private ArrayList<Category> categoriesHuman;
	private ArrayList<Category> categoriesMaterial;
	private ArrayList<ProjectEmployed> employees;
	//private ArrayList<ProjectPartner> partners;
		
	public ProjectLiquidation(){
	}
	
	public ProjectLiquidation(int projectId){
		this.project = new Project(projectId);
	}
	
	public ProjectLiquidation(int id, Project project, Date createdDateTimeUTC,
			Date appliedDateTimeUTC, double totalBills,
			double totalCostCategoriesHuman,
			double totalCostCategoriesMaterial, double totalCostEmployees,
			double earnings, double reserve, double sale, ProjectPartner partner1,
			double partner1Earning, ProjectPartner partner2, double partner2Earning,
			boolean isCurrencyDollar, ArrayList<Bill> bills,
			ArrayList<Category> categoriesHuman,
			ArrayList<Category> categoriesMaterial,
			ArrayList<ProjectEmployed> employees,
			ArrayList<ProjectPartner> partners) {
		this.id = id;
		this.project = project;
		this.createdDateTimeUTC = createdDateTimeUTC;
		this.appliedDateTimeUTC = appliedDateTimeUTC;
		this.totalBills = totalBills;
		this.totalCostCategoriesHuman = totalCostCategoriesHuman;
		this.totalCostCategoriesMaterial = totalCostCategoriesMaterial;
		this.totalCostEmployees = totalCostEmployees;
		this.earnings = earnings;
		this.reserve = reserve;
		this.sale = sale;
		this.partner1 = partner1;
		this.partner1Earning = partner1Earning;
		this.partner2 = partner2;
		this.partner2Earning = partner2Earning;
		this.isCurrencyDollar = isCurrencyDollar;
		this.bills = bills;
		this.categoriesHuman = categoriesHuman;
		this.categoriesMaterial = categoriesMaterial;
		this.employees = employees;
		//this.partners = partners;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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

	public void setTotalCostCategoriesMaterial(double totalCostCategoriesMaterial) {
		this.totalCostCategoriesMaterial = totalCostCategoriesMaterial;
	}

	public double getEarnings() {
		return earnings;
	}

	public void setEarnings(double earnings) {
		this.earnings = earnings;
	}

	public double getReserve() {
		return reserve;
	}

	public void setReserve(double reserve) {
		this.reserve = reserve;
	}

	public double getSale() {
		return sale;
	}

	public void setSale(double sale) {
		this.sale = sale;
	}

	public ProjectPartner getPartner1() {
		return partner1;
	}

	public void setPartner1(ProjectPartner partner1) {
		this.partner1 = partner1;
	}

	public double getPartner1Earning() {
		return partner1Earning;
	}

	public void setPartner1Earning(double partner1Earning) {
		this.partner1Earning = partner1Earning;
	}

	public ProjectPartner getPartner2() {
		return partner2;
	}

	public void setPartner2(ProjectPartner partner2) {
		this.partner2 = partner2;
	}

	public double getPartner2Earning() {
		return partner2Earning;
	}

	public void setPartner2Earning(double partner2Earning) {
		this.partner2Earning = partner2Earning;
	}	

	public boolean isCurrencyDollar() {
		return isCurrencyDollar;
	}

	public void setCurrencyDollar(boolean isCurrencyDollar) {
		this.isCurrencyDollar = isCurrencyDollar;
	}
	
	public double getTotalCostEmployees() {
		return totalCostEmployees;
	}

	public void setTotalCostEmployees(double totalCostEmployees) {
		this.totalCostEmployees = totalCostEmployees;
	}
	
	public ArrayList<Bill> getBills() {
		return bills;
	}

	public void setBills(ArrayList<Bill> bills) {
		this.bills = bills;
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
	
	public Employed getEmployedPartner1() {
		return employedPartner1;
	}

	public void setEmployedPartner1(Employed employedPartner1) {
		this.employedPartner1 = employedPartner1;
	}

	public Employed getEmployedPartner2() {
		return employedPartner2;
	}

	public void setEmployedPartner2(Employed employedPartner2) {
		this.employedPartner2 = employedPartner2;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return (int) (((ProjectLiquidation)arg0).getEarnings() - this.earnings);
	}	
}
