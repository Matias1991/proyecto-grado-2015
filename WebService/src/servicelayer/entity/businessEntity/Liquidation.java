package servicelayer.entity.businessEntity;

import java.util.Date;

public class Liquidation implements Comparable{
	
	private int id;
	private Project project;
	private Date createdDateTimeUTC;
	private Date appliedDateTimeUTC;
	private Double totalBills;
	private Double totalCostHoursEmployees;
	private Double totalCostCategoriesHuman;
	private Double totalCostCategoriesMaterial;
	private Double totalCostCategoriesCompany;
	private Double earnings;
	private Double reserve;
	private Double sale;
	private Employed partner1;
	private Double partner1Earning;
	private Employed partner2;
	private Double partner2Earning;
	private Double typeExchange;
		
	public Liquidation(){
	}
	
	public Liquidation(int id){
		this.id = id;
	}

	public Liquidation(int id, Project project, Date createdDateTimeUTC,
			Date appliedDateTimeUTC, Double totalBills,
			Double totalCostHoursEmpoyees, Double totalCostCategoriesHuman,
			Double totalCostCategoriesMaterial,
			Double totalCostCategoriesCompany, Double earnings,
			Double reserve, Double sale, Employed partner1,
			Double partner1Earning, Employed partner2, Double partner2Earning) {		
		this.id = id;
		this.project = project;
		this.createdDateTimeUTC = createdDateTimeUTC;
		this.appliedDateTimeUTC = appliedDateTimeUTC;
		this.totalBills = totalBills;
		this.totalCostHoursEmployees = totalCostHoursEmpoyees;
		this.totalCostCategoriesHuman = totalCostCategoriesHuman;
		this.totalCostCategoriesMaterial = totalCostCategoriesMaterial;
		this.totalCostCategoriesCompany = totalCostCategoriesCompany;
		this.earnings = earnings;
		this.reserve = reserve;
		this.sale = sale;
		this.partner1 = partner1;
		this.partner1Earning = partner1Earning;
		this.partner2 = partner2;
		this.partner2Earning = partner2Earning;
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

	public Double getTotalBills() {
		return totalBills;
	}

	public void setTotalBills(Double totalBills) {
		this.totalBills = totalBills;
	}

	public Double getTotalCostHoursEmployees() {
		return totalCostHoursEmployees;
	}

	public void setTotalCostHoursEmployees(Double totalCostHoursEmployees) {
		this.totalCostHoursEmployees = totalCostHoursEmployees;
	}

	public Double getTotalCostCategoriesHuman() {
		return totalCostCategoriesHuman;
	}

	public void setTotalCostCategoriesHuman(Double totalCostCategoriesHuman) {
		this.totalCostCategoriesHuman = totalCostCategoriesHuman;
	}

	public Double getTotalCostCategoriesMaterial() {
		return totalCostCategoriesMaterial;
	}

	public void setTotalCostCategoriesMaterial(Double totalCostCategoriesMaterial) {
		this.totalCostCategoriesMaterial = totalCostCategoriesMaterial;
	}

	public Double getTotalCostCategoriesCompany() {
		return totalCostCategoriesCompany;
	}

	public void setTotalCostCategoriesCompany(Double totalCostCategoriesCompany) {
		this.totalCostCategoriesCompany = totalCostCategoriesCompany;
	}

	public Double getEarnings() {
		return earnings;
	}

	public void setEarnings(Double earnings) {
		this.earnings = earnings;
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

	public Employed getPartner1() {
		return partner1;
	}

	public void setPartner1(Employed partner1) {
		this.partner1 = partner1;
	}

	public Double getPartner1Earning() {
		return partner1Earning;
	}

	public void setPartner1Earning(Double partner1Earning) {
		this.partner1Earning = partner1Earning;
	}

	public Employed getPartner2() {
		return partner2;
	}

	public void setPartner2(Employed partner2) {
		this.partner2 = partner2;
	}

	public Double getPartner2Earning() {
		return partner2Earning;
	}

	public void setPartner2Earning(Double partner2Earning) {
		this.partner2Earning = partner2Earning;
	}
	
	public Double getTypeExchange() {
		return typeExchange;
	}

	public void setTypeExchange(Double typeExchange) {
		this.typeExchange = typeExchange;
	}

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return (int) (((Liquidation)arg0).getEarnings() - this.earnings);
	}	
}
