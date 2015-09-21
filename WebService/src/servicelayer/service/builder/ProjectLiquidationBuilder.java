package servicelayer.service.builder;

import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.ProjectLiquidation;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.valueObject.VOProjectLiquidation;

public class ProjectLiquidationBuilder extends BaseBuilder<VOProjectLiquidation, ProjectLiquidation>{

	@Override
	public VOProjectLiquidation BuildVOObject(ProjectLiquidation businessObject) {
		VOProjectLiquidation voLiquidation = new VOProjectLiquidation();
		
		voLiquidation.setAppliedDateTimeUTC(businessObject.getAppliedDateTimeUTC());
		voLiquidation.setCreatedDateTimeUTC(businessObject.getCreatedDateTimeUTC());
		voLiquidation.setEarnings(businessObject.getEarnings());
		voLiquidation.setId(businessObject.getId());
		voLiquidation.setPartner1Id(businessObject.getPartner1().getId());
		voLiquidation.setPartner1Earning(businessObject.getPartner1Earning());
		voLiquidation.setPartner2Id(businessObject.getPartner2().getId());
		voLiquidation.setPartner2Earning(businessObject.getPartner2Earning());
		voLiquidation.setProjectId(businessObject.getProject().getId());
		voLiquidation.setReserve(businessObject.getReserve());
		voLiquidation.setSale(businessObject.getSale());
		voLiquidation.setTotalBills(businessObject.getTotalBills());
		voLiquidation.setTotalCostCategoriesHuman(businessObject.getTotalCostCategoriesHuman());
		voLiquidation.setTotalCostCategoriesMaterial(businessObject.getTotalCostCategoriesMaterial());
		
		return voLiquidation;
	}

	@Override
	public ProjectLiquidation BuildBusinessObject(VOProjectLiquidation voObject) {
		ProjectLiquidation liquidation = new ProjectLiquidation();
		
		liquidation.setAppliedDateTimeUTC(voObject.getAppliedDateTimeUTC());
		liquidation.setCreatedDateTimeUTC(voObject.getCreatedDateTimeUTC());
		liquidation.setEarnings(voObject.getEarnings());
		liquidation.setId(voObject.getId());
		liquidation.setPartner1(new Employed(voObject.getId()));
		liquidation.setPartner1Earning(voObject.getPartner1Earning());
		liquidation.setPartner2(new Employed(voObject.getId()));
		liquidation.setPartner2Earning(voObject.getPartner2Earning());
		liquidation.setProject(new Project(voObject.getProjectId()));
		liquidation.setReserve(voObject.getReserve());
		liquidation.setSale(voObject.getSale());
		liquidation.setTotalBills(voObject.getTotalBills());
		liquidation.setTotalCostCategoriesHuman(voObject.getTotalCostCategoriesHuman());
		liquidation.setTotalCostCategoriesMaterial(voObject.getTotalCostCategoriesMaterial());

		return liquidation;
	}

}
