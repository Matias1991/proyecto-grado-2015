package servicelayer.service.builder;

import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.EmployedType;
import servicelayer.entity.businessEntity.SalarySummary;
import servicelayer.entity.valueObject.VOEmployed;
import servicelayer.entity.valueObject.VOSalarySummary;

public class EmployedBuilder extends BaseBuilder<VOEmployed, Employed>{

	@Override
	public VOEmployed BuildVOObject(Employed businessObject) {
		
		VOEmployed voEmployed = new VOEmployed();
		voEmployed.setId(businessObject.getId());
		voEmployed.setName(businessObject.getName());
		voEmployed.setLastName(businessObject.getLastName());
		voEmployed.setEmail(businessObject.getEmail());
		voEmployed.setAddress(businessObject.getAddress());
		voEmployed.setCellPhone(businessObject.getCellPhone());
		voEmployed.setCreatedDateTimeUTC(businessObject.getCreatedDateTimeUTC());
		voEmployed.setUpdatedDateTimeUTC(businessObject.getUpdatedDateTimeUTC());
		voEmployed.setEmployedType(businessObject.getEmployedType().getValue());
		voEmployed.setDeleted(businessObject.getDeleted());
		
		return voEmployed;
	}

	@Override
	public Employed BuildBusinessObject(VOEmployed voObject) {
		
		Employed employed = new Employed();
		employed.setId(voObject.getId());
		employed.setName(voObject.getName());
		employed.setLastName(voObject.getLastName());
		employed.setEmail(voObject.getEmail());
		employed.setAddress(voObject.getAddress());
		employed.setCellPhone(voObject.getCellPhone());
		if (voObject.getEmployedType() != 0)
			employed.setEmployedType(EmployedType.getEnum(voObject
					.getEmployedType()));
		employed.setDeleted(voObject.getDeleted());
		return employed;
	}

	public SalarySummary BuildBusinessSalarySummary(VOSalarySummary voSalarySummary) {
		
		SalarySummary salarySummary = new SalarySummary();
		salarySummary.setNominalSalary(voSalarySummary.getNominalSalary());
		salarySummary.setTickets(voSalarySummary.getTickets());
		salarySummary.setiRPF(voSalarySummary.getiRPF());
		salarySummary.setbSE(voSalarySummary.getbSE());
		salarySummary.setrET(voSalarySummary.getrET());
		salarySummary.setHours(voSalarySummary.getHours());
		salarySummary.setCostSaleHour(voSalarySummary.getCostSaleHour());
		salarySummary.setPercentageTypeFONASA(voSalarySummary.getPercentageTypeFONASA());
		
		return salarySummary;
	}
	
	public VOSalarySummary BuildVOSalarySummary(SalarySummary salarySummary) {
		
		VOSalarySummary voSalarySummary = new VOSalarySummary();
		voSalarySummary.setId(salarySummary.getId());
		voSalarySummary.setVersion(salarySummary.getVersion());
		voSalarySummary.setNominalSalary(salarySummary.getNominalSalary());
		voSalarySummary.setTickets(salarySummary.getTickets());
		voSalarySummary.setPersonalRetirementContribution(salarySummary
				.getPersonalRetirementContribution());
		voSalarySummary.setEmployersContributionsRetirement(salarySummary
				.getEmployersContributionsRetirement());
		voSalarySummary.setPersonalFONASAContribution(salarySummary
				.getPersonalFONASAContribution());
		voSalarySummary.setEmployersFONASAContribution(salarySummary
				.getEmployersFONASAContribution());
		voSalarySummary.setPersonalFRLContribution(salarySummary
				.getPersonalFRLContribution());
		voSalarySummary.setEmployersFRLContribution(salarySummary
				.getEmployersFRLContribution());
		voSalarySummary.setiRPF(salarySummary.getiRPF());
		voSalarySummary
				.setTicketsEmployers(salarySummary.getTicketsEmployers());
		voSalarySummary.setbSE(salarySummary.getbSE());
		voSalarySummary.setTotalDiscounts(salarySummary.getTotalDiscounts());
		voSalarySummary.setTotalEmployerContributions(salarySummary
				.getTotalEmployerContributions());
		voSalarySummary.setNominalWithoutContributions(salarySummary
				.getNominalWithoutContributions());
		voSalarySummary.setDismissalPrevention(salarySummary
				.getDismissalPrevention());
		voSalarySummary.setIncidenceSalary(salarySummary.getIncidenceSalary());
		voSalarySummary
				.setIncidenceTickets(salarySummary.getIncidenceTickets());
		voSalarySummary.setrET(salarySummary.getrET());
		voSalarySummary.setSalaryToPay(salarySummary.getSalaryToPay());
		voSalarySummary.setCostMonth(salarySummary.getCostMonth());
		voSalarySummary.setCostRealHour(salarySummary.getCostRealHour());
		voSalarySummary.setCostSaleHour(salarySummary.getCostSaleHour());
		voSalarySummary.setHours(salarySummary.getHours());
		voSalarySummary.setPercentageTypeFONASA(voSalarySummary
				.getPersonalFONASAContribution()
				/ voSalarySummary.getNominalSalary());

		return voSalarySummary;
	}
}
