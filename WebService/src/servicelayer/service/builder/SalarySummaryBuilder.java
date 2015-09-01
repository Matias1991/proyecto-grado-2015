package servicelayer.service.builder;

import servicelayer.entity.businessEntity.SalarySummary;
import servicelayer.entity.valueObject.VOSalarySummary;

public class SalarySummaryBuilder  extends BaseBuilder<VOSalarySummary, SalarySummary>{
	
	@Override
	public VOSalarySummary BuildVOObject(SalarySummary businessObject) {
		
		VOSalarySummary voSalarySummary = new VOSalarySummary();
		voSalarySummary.setId(businessObject.getId());
		voSalarySummary.setVersion(businessObject.getVersion());
		voSalarySummary.setNominalSalary(businessObject.getNominalSalary());
		voSalarySummary.setTickets(businessObject.getTickets());
		voSalarySummary
				.setPersonalRetirementContribution(businessObject.getPersonalRetirementContribution());
		voSalarySummary
				.setEmployersContributionsRetirement(businessObject.getEmployersContributionsRetirement());
		voSalarySummary.setPersonalFONASAContribution(businessObject.getPersonalFONASAContribution());
		voSalarySummary
				.setEmployersFONASAContribution(businessObject.getEmployersFONASAContribution());
		voSalarySummary.setPersonalFRLContribution(businessObject.getPersonalFRLContribution());
		voSalarySummary.setEmployersFRLContribution(businessObject.getEmployersFRLContribution());
		voSalarySummary.setiRPF(businessObject.getiRPF());
		voSalarySummary.setTicketsEmployers(businessObject.getTicketsEmployers());
		voSalarySummary.setbSE(businessObject.getbSE());
		voSalarySummary.setTotalDiscounts(businessObject.getTotalDiscounts());
		voSalarySummary.setTotalEmployerContributions(businessObject.getTotalEmployerContributions());
		voSalarySummary
				.setNominalWithoutContributions(businessObject.getNominalWithoutContributions());
		voSalarySummary.setDismissalPrevention(businessObject.getDismissalPrevention());
		voSalarySummary.setIncidenceSalary(businessObject.getIncidenceSalary());
		voSalarySummary.setIncidenceTickets(businessObject.getIncidenceTickets());
		voSalarySummary.setrET(businessObject.getrET());
		voSalarySummary.setSalaryToPay(businessObject.getSalaryToPay());
		voSalarySummary.setCostMonth(businessObject.getCostMonth());
		voSalarySummary.setCostRealHour(businessObject.getCostRealHour());
		voSalarySummary.setCostSaleHour(businessObject.getCostSaleHour());
		voSalarySummary.setHours(businessObject.getHours());
		voSalarySummary.setCreatedDateTimeUTC(businessObject.getCreatedDateTimeUTC());
		
		return voSalarySummary;
	}

	@Override
	public SalarySummary BuildBusinessObject(VOSalarySummary voObject) {
		
		SalarySummary salarySummary = new SalarySummary();
		salarySummary.setId(voObject.getId());
		salarySummary.setVersion(voObject.getVersion());
		salarySummary.setNominalSalary(voObject.getNominalSalary());
		salarySummary.setTickets(voObject.getTickets());
		salarySummary
				.setPersonalRetirementContribution(voObject.getPersonalRetirementContribution());
		salarySummary
				.setEmployersContributionsRetirement(voObject.getEmployersContributionsRetirement());
		salarySummary.setPersonalFONASAContribution(voObject.getPersonalFONASAContribution());
		salarySummary
				.setEmployersFONASAContribution(voObject.getEmployersFONASAContribution());
		salarySummary.setPersonalFRLContribution(voObject.getPersonalFRLContribution());
		salarySummary.setEmployersFRLContribution(voObject.getEmployersFRLContribution());
		salarySummary.setiRPF(voObject.getiRPF());
		salarySummary.setTicketsEmployers(voObject.getTicketsEmployers());
		salarySummary.setbSE(voObject.getbSE());
		salarySummary.setTotalDiscounts(voObject.getTotalDiscounts());
		salarySummary.setTotalEmployerContributions(voObject.getTotalEmployerContributions());
		salarySummary
				.setNominalWithoutContributions(voObject.getNominalWithoutContributions());
		salarySummary.setDismissalPrevention(voObject.getDismissalPrevention());
		salarySummary.setIncidenceSalary(voObject.getIncidenceSalary());
		salarySummary.setIncidenceTickets(voObject.getIncidenceTickets());
		salarySummary.setrET(voObject.getrET());
		salarySummary.setSalaryToPay(voObject.getSalaryToPay());
		salarySummary.setCostMonth(voObject.getCostMonth());
		salarySummary.setCostRealHour(voObject.getCostRealHour());
		salarySummary.setCostSaleHour(voObject.getCostSaleHour());
		salarySummary.setHours(voObject.getHours());
		salarySummary.setCreatedDateTimeUTC(voObject.getCreatedDateTimeUTC());
		
		return salarySummary;
	}
}
