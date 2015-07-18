package servicelayer.entity.businessEntity;

import servicelayer.entity.valueObject.VOSalarySummary;

public class SalarySummary {

	private int id;
	private int version;
	private double nominalSalary;
	private double tickets;
	private double personalRetirementContribution;
	private double employersContributionsRetirement;
	private double personalFONASAContribution;
	private double employersFONASAContribution;
	private double personalFRLContribution;
	private double employersFRLContribution;
	private double iRPF;
	private double ticketsEmployers;
	private double bSE;
	private double totalDiscounts;
	private double totalEmployerContributions;
	private double nominalWithoutContributions;
	private double dismissalPrevention;
	private double incidenceSalary;
	private double incidenceTickets;
	private double rET;
	private double salaryToPay;
	private double costMonth;
	private double costRealHour;
	private double costSaleHour;
	private int hours;
	
	public SalarySummary()
	{
		
	}
	
	public SalarySummary(VOSalarySummary voSalarySummary)
	{
		this.nominalSalary = voSalarySummary.getNominalSalary();
		this.tickets = voSalarySummary.getTickets();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public double getNominalSalary() {
		return nominalSalary;
	}
	public void setNominalSalary(double nominalSalary) {
		this.nominalSalary = nominalSalary;
	}
	public double getTickets() {
		return tickets;
	}
	public void setTickets(double tickets) {
		this.tickets = tickets;
	}
	public double getPersonalRetirementContribution() {
		return personalRetirementContribution;
	}
	public void setPersonalRetirementContribution(
			double personalRetirementContribution) {
		this.personalRetirementContribution = personalRetirementContribution;
	}
	public double getEmployersContributionsRetirement() {
		return employersContributionsRetirement;
	}
	public void setEmployersContributionsRetirement(
			double employersContributionsRetirement) {
		this.employersContributionsRetirement = employersContributionsRetirement;
	}
	public double getPersonalFONASAContribution() {
		return personalFONASAContribution;
	}
	public void setPersonalFONASAContribution(double personalFONASAContribution) {
		this.personalFONASAContribution = personalFONASAContribution;
	}
	public double getEmployersFONASAContribution() {
		return employersFONASAContribution;
	}
	public void setEmployersFONASAContribution(double employersFONASAContribution) {
		this.employersFONASAContribution = employersFONASAContribution;
	}
	public double getPersonalFRLContribution() {
		return personalFRLContribution;
	}
	public void setPersonalFRLContribution(double personalFRLContribution) {
		this.personalFRLContribution = personalFRLContribution;
	}
	public double getEmployersFRLContribution() {
		return employersFRLContribution;
	}
	public void setEmployersFRLContribution(double employersFRLContribution) {
		this.employersFRLContribution = employersFRLContribution;
	}
	public double getiRPF() {
		return iRPF;
	}
	public void setiRPF(double iRPF) {
		this.iRPF = iRPF;
	}
	public double getbSE() {
		return bSE;
	}
	public void setbSE(double bSE) {
		this.bSE = bSE;
	}
	public double getTotalDiscounts() {
		return totalDiscounts;
	}
	public void setTotalDiscounts(double totalDiscounts) {
		this.totalDiscounts = totalDiscounts;
	}
	public double getTotalEmployerContributions() {
		return totalEmployerContributions;
	}
	public void setTotalEmployerContributions(double totalEmployerContributions) {
		this.totalEmployerContributions = totalEmployerContributions;
	}
	public double getNominalWithoutContributions() {
		return nominalWithoutContributions;
	}
	public void setNominalWithoutContributions(double nominalWithoutContributions) {
		this.nominalWithoutContributions = nominalWithoutContributions;
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
	public double getrET() {
		return rET;
	}
	public void setrET(double rET) {
		this.rET = rET;
	}
	public double getCostMonth() {
		return costMonth;
	}
	public void setCostMonth(double costMonth) {
		this.costMonth = costMonth;
	}
	public double getCostRealHour() {
		return costRealHour;
	}
	public void setCostRealHour(double costRealHour) {
		this.costRealHour = costRealHour;
	}
	public double getCostSaleHour() {
		return costSaleHour;
	}
	public void setCostSaleHour(double costSaleHour) {
		this.costSaleHour = costSaleHour;
	}

	public double getSalaryToPay() {
		return salaryToPay;
	}

	public void setSalaryToPay(double salaryToPay) {
		this.salaryToPay = salaryToPay;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public double getTicketsEmployers() {
		return ticketsEmployers;
	}

	public void setTicketsEmployers(double ticketsEmployers) {
		this.ticketsEmployers = ticketsEmployers;
	}
}
