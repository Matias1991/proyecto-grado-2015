package entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import servicelayer.service.ServiceWebStub.VOBill;
import servicelayer.service.ServiceWebStub.VOCategory;
import servicelayer.service.ServiceWebStub.VOProjectEmployed;
import servicelayer.service.ServiceWebStub.VOProjectLiquidation;


public class ProjectLiquidation {

	private int id;
	private int projectId;
	private Date createdDateTimeUTC;
	private Date appliedDateTimeUTC;
	private Collection<Bill> bills;
	private Collection<Category> categoriesHuman;
	private Collection<Category> categoriesMaterial;
	private Collection<ProjectEmployed> employees;
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
	private Project project;
	
	public ProjectLiquidation(){
		
	}
	
	public ProjectLiquidation(VOProjectLiquidation voLiquidation){
		this.id = voLiquidation.getId();
		this.projectId = voLiquidation.getProjectId();
		this.createdDateTimeUTC = voLiquidation.getCreatedDateTimeUTC();
		this.appliedDateTimeUTC = voLiquidation.getAppliedDateTimeUTC();
		
		this.bills = new ArrayList<Bill>();
		if(voLiquidation.getBills() != null){
			for(VOBill voBill : voLiquidation.getBills()){
				if(voBill != null){
					bills.add(new Bill(voBill));
				}				
			}			
		}
		
		this.categoriesHuman = new ArrayList<Category>();
		if(voLiquidation.getCategoriesHuman() != null){
			for(VOCategory voCategory : voLiquidation.getCategoriesHuman()){
				if(voCategory != null){
					categoriesHuman.add(new Category(voCategory));
				}
			}
		}
		
		this.categoriesMaterial = new ArrayList<Category>();
		if(voLiquidation.getCategoryMaterial() != null){
			for(VOCategory voCategory : voLiquidation.getCategoryMaterial()){
				if(voCategory != null){
					categoriesMaterial.add(new Category(voCategory));
				}
			}
		}
		
		this.employees = new ArrayList<ProjectEmployed>();
		if(voLiquidation.getEmployees() != null){
			for(VOProjectEmployed voProjectEmployed : voLiquidation.getEmployees()){
				if(voProjectEmployed != null){
					employees.add(new ProjectEmployed(voProjectEmployed));
				}
			}
		}
		
		this.reserve = voLiquidation.getReserve();
		this.sale = voLiquidation.getSale();
		this.partner1Distribution = voLiquidation.getPartner1Distribution();
		this.partner1Lastname = voLiquidation.getPartner1Lastname();
		this.partner1Name = voLiquidation.getPartner1Name();
		this.partner2Distribution = voLiquidation.getPartner2Distribution();
		this.partner2Lastname = voLiquidation.getPartner2Lastname();
		this.partner2Name = voLiquidation.getPartner2Name();	
		this.isCurrencyDollar = voLiquidation.getCurrencyDollar();
		
		this.earnings = voLiquidation.getEarnings();
		this.partner1Earning = voLiquidation.getPartner1Earning();
		this.partner2Earning = voLiquidation.getPartner2Earning();
		this.totalBills = voLiquidation.getTotalBills();
		this.totalCostCategoriesHuman = voLiquidation.getTotalCostCategoriesHuman();
		this.totalCostCategoriesMaterial = voLiquidation.getTotalCostCategoriesMaterial();
		this.totalCostEmployees = voLiquidation.getTotalCostEmployees();
		if(voLiquidation.getProject() != null){
			this.project = new Project(voLiquidation.getProject());
		}
		
	}

	public ProjectLiquidation(int projectId, String projectName, double enarnings, double reserve)
	{
		this.earnings = enarnings;
		this.reserve = reserve;
		this.project = new Project();
		this.project.setId(projectId);
		this.project.setName(projectName);
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

	public Collection<Bill> getBills() {
		return bills;
	}

	public void setBills(Collection<Bill> bills) {
		this.bills = bills;
	}

	public Collection<Category> getCategoriesHuman() {
		return categoriesHuman;
	}

	public void setCategoriesHuman(Collection<Category> categoriesHuman) {
		this.categoriesHuman = categoriesHuman;
	}

	public Collection<Category> getCategoriesMaterial() {
		return categoriesMaterial;
	}

	public void setCategoriesMaterial(Collection<Category> categoriesMaterial) {
		this.categoriesMaterial = categoriesMaterial;
	}

	public Collection<ProjectEmployed> getEmployees() {
		return employees;
	}

	public void setEmployees(Collection<ProjectEmployed> employees) {
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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}	
	
}
