package entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import servicelayer.service.ServiceWebStub.VOCategory;
import servicelayer.service.ServiceWebStub.VOCompanyLiquidation;
import servicelayer.service.ServiceWebStub.VOProjectEmployed;

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
		private Employee partner1;
		private double partner1EarningsDollar;
		private double partner1EarningsPeso;
		private Employee partner2;
		private double partner2EarningsDollar;
		private double partner2EarningsPeso;
		private double typeExchange;
		private double employeesCost;
		private Date appliedDateTimeUTC;
		private Date CreatedDateTimeUTC;
		private Collection<Category> categoriesHuman;
		private Collection<Category> categoriesMaterial;
		private Collection<ProjectEmployed> employees;
		private double totalEarningsPeso;
		private double totalEarningsDollar;
		
		public CompanyLiquidation(VOCompanyLiquidation voCompanyLiquidation){
			this.id = voCompanyLiquidation.getId();
			this.companyCategory = voCompanyLiquidation.getCompanyCategory();
			this.contribution = voCompanyLiquidation.getContribution();
			this.salaryNotPartners = voCompanyLiquidation.getSalaryNotPartners();
			this.salaryPartners = voCompanyLiquidation.getSalaryPartners();
			this.irae = voCompanyLiquidation.getIrae();
			this.dismissalPrevention = voCompanyLiquidation.getDismissalPrevention();
			this.incidenceSalary = voCompanyLiquidation.getIncidenceSalary();
			this.incidenceTickets = voCompanyLiquidation.getIncidenceTickets();
			this.IVASale = voCompanyLiquidation.getIVASale();
			this.IVAPurchase = voCompanyLiquidation.getIVAPurchase();
			this.partner1 = new Employee();
			this.partner1.setLastName(voCompanyLiquidation.getPartner1().getLastName());
			this.partner1.setName(voCompanyLiquidation.getPartner1().getName());
			this.partner1EarningsDollar = voCompanyLiquidation.getPartner1EarningsDollar();
			this.partner1EarningsPeso = voCompanyLiquidation.getPartner1EarningsPeso();
			this.partner2 = new Employee();
			this.partner2.setLastName(voCompanyLiquidation.getPartner2().getLastName());
			this.partner2.setName(voCompanyLiquidation.getPartner2().getName());
			this.partner2EarningsDollar = voCompanyLiquidation.getPartner2EarningsDollar();
			this.partner2EarningsPeso = voCompanyLiquidation.getPartner2EarningsPeso();
			this.typeExchange = voCompanyLiquidation.getTypeExchange();
			this.employeesCost = voCompanyLiquidation.getEmployeesCost();
			this.appliedDateTimeUTC = voCompanyLiquidation.getAppliedDateTimeUTC();
			this.CreatedDateTimeUTC = voCompanyLiquidation.getCreatedDateTimeUTC();
			this.totalEarningsPeso = voCompanyLiquidation.getTotalEarningsPeso();
			this.totalEarningsDollar = voCompanyLiquidation.getTotalEarningsDollar();
			
			this.categoriesHuman = new ArrayList<Category>();
			if(voCompanyLiquidation.getCategoriesHuman() != null){
				for(VOCategory voCategory : voCompanyLiquidation.getCategoriesHuman()){
					if(voCategory != null){
						categoriesHuman.add(new Category(voCategory));
					}
				}
			}
			
			this.categoriesMaterial = new ArrayList<Category>();
			if(voCompanyLiquidation.getCategoriesMaterial() != null){
				for(VOCategory voCategory : voCompanyLiquidation.getCategoriesMaterial()){
					if(voCategory != null){
						categoriesMaterial.add(new Category(voCategory));
					}
				}
			}
			
			this.employees = new ArrayList<ProjectEmployed>();
			if(voCompanyLiquidation.getEmployees() != null){
				for(VOProjectEmployed voProjectEmployed : voCompanyLiquidation.getEmployees()){
					if(voProjectEmployed != null){
						employees.add(new ProjectEmployed(voProjectEmployed));
					}
				}
			}
			
		}
		
		public CompanyLiquidation(double iVAPurchase, double iVASale, Date appliedDateTimeUTC){
			this.IVAPurchase = iVAPurchase;
			this.IVASale = iVASale;
			this.appliedDateTimeUTC = appliedDateTimeUTC;
		}
		
		public CompanyLiquidation()
		{
			
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

		public Employee getPartner1() {
			return partner1;
		}

		public void setPartner1(Employee partner1) {
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

		public Employee getPartner2() {
			return partner2;
		}

		public void setPartner2(Employee partner2) {
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

		public double getTypeExchange() {
			return typeExchange;
		}

		public void setTypeExchange(double typeExchange) {
			this.typeExchange = typeExchange;
		}

		public double getEmployeesCost() {
			return employeesCost;
		}

		public void setEmployeesCost(double employeesCost) {
			this.employeesCost = employeesCost;
		}

		public Date getAppliedDateTimeUTC() {
			return appliedDateTimeUTC;
		}

		public void setAppliedDateTimeUTC(Date appliedDateTimeUTC) {
			this.appliedDateTimeUTC = appliedDateTimeUTC;
		}

		public Date getCreatedDateTimeUTC() {
			return CreatedDateTimeUTC;
		}

		public void setCreatedDateTimeUTC(Date createdDateTimeUTC) {
			CreatedDateTimeUTC = createdDateTimeUTC;
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
