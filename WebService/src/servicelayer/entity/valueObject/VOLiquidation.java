package servicelayer.entity.valueObject;

import java.io.Serializable;
import java.util.Date;

public class VOLiquidation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private int projectId;
	private Date createdDateTimeUTC;
	private Date appliedDateTimeUTC;
	private Double totalBills;
	private Double totalCostHoursEmpoyees;
	private Double totalCostCategoriesHuman;
	private Double totalCostCategoriesMaterial;
	private Double totalCostCategoriesCompany;
	private Double earnings;
	private Double reserve;
	private Double sale;
	private int partner1Id;
	private Double partner1Earning;
	private int partner2Id;
	private Double partner2Earning;
	
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
	public Double getTotalBills() {
		return totalBills;
	}
	public void setTotalBills(Double totalBills) {
		this.totalBills = totalBills;
	}
	public Double getTotalCostHoursEmpoyees() {
		return totalCostHoursEmpoyees;
	}
	public void setTotalCostHoursEmpoyees(Double totalCostHoursEmpoyees) {
		this.totalCostHoursEmpoyees = totalCostHoursEmpoyees;
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
	public int getPartner1Id() {
		return partner1Id;
	}
	public void setPartner1Id(int partner1Id) {
		this.partner1Id = partner1Id;
	}
	public Double getPartner1Earning() {
		return partner1Earning;
	}
	public void setPartner1Earning(Double partner1Earning) {
		this.partner1Earning = partner1Earning;
	}
	public int getPartner2Id() {
		return partner2Id;
	}
	public void setPartner2Id(int partner2Id) {
		this.partner2Id = partner2Id;
	}
	public Double getPartner2Earning() {
		return partner2Earning;
	}
	public void setPartner2Earning(Double partner2Earning) {
		this.partner2Earning = partner2Earning;
	}	

}
