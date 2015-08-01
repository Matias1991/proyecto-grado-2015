package entities;

import java.util.List;

import servicelayer.service.ServiceWebStub.VOSalarySummary;

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
	private double percentageTypeFONASA;
	private int hours;
	
	public SalarySummary(){
		
	}
	
	public SalarySummary(VOSalarySummary voSalarySummary){
		this.id = voSalarySummary.getId();
		this.version = voSalarySummary.getVersion();
		this.nominalSalary = voSalarySummary.getNominalSalary();
		this.tickets = voSalarySummary.getTickets();
		this.personalRetirementContribution = voSalarySummary.getPersonalRetirementContribution();
		this.employersContributionsRetirement = voSalarySummary.getEmployersContributionsRetirement();
		this.personalFONASAContribution = voSalarySummary.getPersonalFONASAContribution();
		this.employersFONASAContribution = voSalarySummary.getEmployersFONASAContribution();
		this.personalFRLContribution = voSalarySummary.getPersonalFRLContribution();
		this.employersFRLContribution = voSalarySummary.getEmployersFRLContribution();
		this.iRPF = voSalarySummary.getIRPF();
		this.ticketsEmployers = voSalarySummary.getTicketsEmployers();
		this.bSE = voSalarySummary.getBSE();
		this.totalDiscounts = voSalarySummary.getTotalDiscounts();
		this.totalEmployerContributions = voSalarySummary.getTotalEmployerContributions();
		this.nominalWithoutContributions = voSalarySummary.getNominalWithoutContributions();
		this.dismissalPrevention = voSalarySummary.getDismissalPrevention();
		this.incidenceSalary = voSalarySummary.getIncidenceSalary();
		this.incidenceTickets = voSalarySummary.getIncidenceTickets();
		this.rET = voSalarySummary.getRET();
		this.salaryToPay = voSalarySummary.getSalaryToPay();
		this.costMonth = voSalarySummary.getCostMonth();
		this.costRealHour = voSalarySummary.getCostRealHour();
		this.costSaleHour = voSalarySummary.getCostSaleHour();
		this.percentageTypeFONASA = voSalarySummary.getPercentageTypeFONASA();
		this.hours = voSalarySummary.getHours();	
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

	public double getIRPF() {
		return iRPF;
	}

	public void setIRPF(double iRPF) {
		this.iRPF = iRPF;
	}

	public double getTicketsEmployers() {
		return ticketsEmployers;
	}

	public void setTicketsEmployers(double ticketsEmployers) {
		this.ticketsEmployers = ticketsEmployers;
	}

	public double getBSE() {
		return bSE;
	}

	public void setBSE(double bSE) {
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

	public double getRET() {
		return rET;
	}

	public void setRET(double rET) {
		this.rET = rET;
	}

	public double getSalaryToPay() {
		return salaryToPay;
	}

	public void setSalaryToPay(double salaryToPay) {
		this.salaryToPay = salaryToPay;
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

	public double getPercentageTypeFONASA() {
		return percentageTypeFONASA;
	}

	public void setPercentageTypeFONASA(double percentageTypeFONASA) {
		this.percentageTypeFONASA = percentageTypeFONASA;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}
	
	public VOSalarySummary toVOSalarySummary(){
		VOSalarySummary voSalarySummary = new VOSalarySummary();
		voSalarySummary.setBSE(this.bSE);
		voSalarySummary.setCostMonth(this.costMonth);
		voSalarySummary.setCostRealHour(this.costRealHour);
		voSalarySummary.setCostSaleHour(this.costSaleHour);
		voSalarySummary.setDismissalPrevention(this.dismissalPrevention);
		voSalarySummary.setEmployersContributionsRetirement(this.employersContributionsRetirement);
		voSalarySummary.setEmployersFONASAContribution(this.employersFONASAContribution);
		voSalarySummary.setEmployersFRLContribution(this.employersFRLContribution);
		voSalarySummary.setHours(this.hours);
		voSalarySummary.setId(this.id);
		voSalarySummary.setIncidenceSalary(this.incidenceSalary);
		voSalarySummary.setIncidenceTickets(this.incidenceTickets);
		voSalarySummary.setIRPF(this.iRPF);
		voSalarySummary.setNominalSalary(this.nominalSalary);
		voSalarySummary.setNominalWithoutContributions(this.nominalWithoutContributions);
		voSalarySummary.setPercentageTypeFONASA(this.percentageTypeFONASA);
		voSalarySummary.setPersonalFONASAContribution(this.personalFONASAContribution);
		voSalarySummary.setPersonalFRLContribution(this.personalFRLContribution);
		voSalarySummary.setPersonalRetirementContribution(this.personalRetirementContribution);
		voSalarySummary.setRET(this.rET);
		voSalarySummary.setSalaryToPay(this.salaryToPay);
		voSalarySummary.setTickets(this.tickets);
		voSalarySummary.setTicketsEmployers(this.ticketsEmployers);
		voSalarySummary.setTotalDiscounts(this.totalDiscounts);
		voSalarySummary.setTotalEmployerContributions(this.totalEmployerContributions);
		voSalarySummary.setVersion(this.version);
		
		return voSalarySummary;
		
	}		

}
