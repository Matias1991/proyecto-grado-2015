package servicelayer.entity.valueObject;

import java.io.Serializable;
import java.util.Date;

public class VOProjectLiquidation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int projectId;
	private Date createdDateTimeUTC;
	private Date appliedDateTimeUTC;
	private VOBill[] bills;
	private VOCategory[] categoriesHuman;
	private VOCategory[] categoryMaterial;
	private VOProjectEmployed[] employees;
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
	public VOCategory[] getCategoriesHuman() {
		return categoriesHuman;
	}
	public void setCategoriesHuman(VOCategory[] categoriesHuman) {
		this.categoriesHuman = categoriesHuman;
	}
	public VOCategory[] getCategoryMaterial() {
		return categoryMaterial;
	}
	public void setCategoryMaterial(VOCategory[] categoryMaterial) {
		this.categoryMaterial = categoryMaterial;
	}
	public VOProjectEmployed[] getEmployees() {
		return employees;
	}
	public void setEmployees(VOProjectEmployed[] employees) {
		this.employees = employees;
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
	public void setTotalCostCategoriesMaterial(double totalCostCategoriesMaterial) {
		this.totalCostCategoriesMaterial = totalCostCategoriesMaterial;
	}
	public double getTotalCostEmployees() {
		return totalCostEmployees;
	}
	public void setTotalCostEmployees(double totalCostEmployees) {
		this.totalCostEmployees = totalCostEmployees;
	}	
	
	
}
