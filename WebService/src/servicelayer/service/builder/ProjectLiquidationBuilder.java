package servicelayer.service.builder;

import servicelayer.entity.businessEntity.ProjectLiquidation;
import servicelayer.entity.valueObject.VOProjectLiquidation;

public class ProjectLiquidationBuilder extends BaseBuilder<VOProjectLiquidation, ProjectLiquidation>{

	@Override
	public VOProjectLiquidation BuildVOObject(ProjectLiquidation businessObject) {
		VOProjectLiquidation voLiquidation = new VOProjectLiquidation();
		
		voLiquidation.setAppliedDateTimeUTC(businessObject.getAppliedDateTimeUTC());		
		voLiquidation.setCreatedDateTimeUTC(businessObject.getCreatedDateTimeUTC());
		voLiquidation.setCurrencyDollar(businessObject.isCurrencyDollar());		
		voLiquidation.setId(businessObject.getId());
		if(businessObject.getPartner1() != null){
			voLiquidation.setPartner1Distribution(businessObject.getPartner1().getDistributionType().getValue());		
			voLiquidation.setPartner1Lastname(businessObject.getPartner1().getEmployed().getLastName());
			voLiquidation.setPartner1Name(businessObject.getPartner1().getEmployed().getName());
			voLiquidation.setPartner2Distribution(businessObject.getPartner2().getDistributionType().getValue());
			voLiquidation.setPartner2Lastname(businessObject.getPartner2().getEmployed().getLastName());
			voLiquidation.setPartner2Name(businessObject.getPartner2().getEmployed().getName());
		}
		if(businessObject.getEmployedPartner1() != null){
			voLiquidation.setPartner1Lastname(businessObject.getEmployedPartner1().getLastName());
			voLiquidation.setPartner1Name(businessObject.getEmployedPartner1().getName());
			voLiquidation.setPartner2Lastname(businessObject.getEmployedPartner2().getLastName());
			voLiquidation.setPartner2Name(businessObject.getEmployedPartner2().getName());
		}
		
		voLiquidation.setEarnings(businessObject.getEarnings());
		voLiquidation.setPartner1Earning(businessObject.getPartner1Earning());
		voLiquidation.setPartner2Earning(businessObject.getPartner2Earning());
		voLiquidation.setReserve(businessObject.getReserve());
		voLiquidation.setSale(businessObject.getSale());
		voLiquidation.setTotalBills(businessObject.getTotalBills());
		voLiquidation.setTotalCostCategoriesHuman(businessObject.getTotalCostCategoriesHuman());
		voLiquidation.setTotalCostCategoriesMaterial(businessObject.getTotalCostCategoriesMaterial());
		voLiquidation.setTotalCostEmployees(businessObject.getTotalCostEmployees());
			
		
		voLiquidation.setProjectId(businessObject.getProject().getId());
				
		return voLiquidation;
	}

	@Override
	public ProjectLiquidation BuildBusinessObject(VOProjectLiquidation voObject) {
	
		return null;
	}
	
	
}
