package servicelayer.service.builder;

import servicelayer.entity.businessEntity.CompanyLiquidation;
import servicelayer.entity.valueObject.VOCompanyLiquidation;

public class CompanyLiquidationBuilder extends BaseBuilder<VOCompanyLiquidation, CompanyLiquidation>{

	@Override
	public VOCompanyLiquidation BuildVOObject(CompanyLiquidation businessObject) {
		VOCompanyLiquidation voCompanyLiquidation = new VOCompanyLiquidation();
		
		voCompanyLiquidation.setAppliedDateTimeUTC(businessObject.getAppliedDateTimeUTC());
		voCompanyLiquidation.setCompanyCategory(businessObject.getCompanyCategory());
		voCompanyLiquidation.setContribution(businessObject.getContribution());
		voCompanyLiquidation.setCreatedDateTimeUTC(businessObject.getCreatedDateTimeUTC());
		voCompanyLiquidation.setDismissalPrevention(businessObject.getDismissalPrevention());
		voCompanyLiquidation.setId(businessObject.getId());
		voCompanyLiquidation.setIncidenceSalary(businessObject.getIncidenceSalary());
		voCompanyLiquidation.setIncidenceTickets(businessObject.getIncidenceTickets());
		voCompanyLiquidation.setIrae(businessObject.getIrae());
		voCompanyLiquidation.setIVAPurchase(businessObject.getIVAPurchase());
		voCompanyLiquidation.setIVASale(businessObject.getIVASale());
		voCompanyLiquidation.setPartner1EarningsDollar(businessObject.getPartner1EarningsDollar());
		voCompanyLiquidation.setPartner1EarningsPeso(businessObject.getPartner1EarningsPeso());
		voCompanyLiquidation.setPartner2EarningsDollar(businessObject.getPartner2EarningsDollar());
		voCompanyLiquidation.setPartner2EarningsPeso(businessObject.getPartner2EarningsPeso());
		voCompanyLiquidation.setSalaryNotPartners(businessObject.getSalaryNotPartners());
		voCompanyLiquidation.setTypeExchange(businessObject.getTypeExchange());
		voCompanyLiquidation.setEmployeesCost(businessObject.getEmployeesCost());
		voCompanyLiquidation.setSalaryPartners(businessObject.getSalaryPartners());
		voCompanyLiquidation.setTotalEarningsPeso(businessObject.getTotalEarningsPeso());
		voCompanyLiquidation.setTotalEarningsDollar(businessObject.getTotalEarningsDollar());
		
		return voCompanyLiquidation;
	}

	@Override
	public CompanyLiquidation BuildBusinessObject(VOCompanyLiquidation voObject) {
		
		return null;
	}

}
