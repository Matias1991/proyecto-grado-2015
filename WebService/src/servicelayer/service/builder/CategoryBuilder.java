package servicelayer.service.builder;

import servicelayer.entity.businessEntity.Category;
import servicelayer.entity.businessEntity.CategoryType;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.valueObject.VOCategory;

public class CategoryBuilder extends BaseBuilder<VOCategory, Category>{

	@Override
	public VOCategory BuildVOObject(Category businessObject) {
		
		VOCategory voCategory = new VOCategory();
		voCategory.setId(businessObject.getId());
		voCategory.setDescription(businessObject.getDescription());
		voCategory.setAmountPeso(businessObject.getAmountPeso());
		voCategory.setAmountDollar(businessObject.getAmountDollar());
		voCategory.setIsCurrencyDollar(businessObject.getIsCurrencyDollar());
		voCategory.setTypeExchange(businessObject.getTypeExchange());
		voCategory.setIvaTypeId(businessObject.getIvaTypeId());
		voCategory.setAppliedDateTimeUTC(businessObject.getAppliedDateTimeUTC());
		if(businessObject.getProject() != null)
		{
			voCategory.setProjectId(businessObject.getProject().getId());
			voCategory.setProjectName(businessObject.getProject().getName());
			voCategory.setProjectClosed(businessObject.getProject().getClosed());
		}
		voCategory.setCategoryType(businessObject.getCategoryType().getValue());
		voCategory.setIsRRHH(businessObject.getIsRRHH());
		
		return voCategory;

	}

	@Override
	public Category BuildBusinessObject(VOCategory voObject) {
		
		Category category = new Category();
		category.setId(voObject.getId());
		category.setVersion(voObject.getVersion());
		category.setDescription(voObject.getDescription());
		category.setAmountPeso(voObject.getAmountPeso());
		category.setAmountDollar(voObject.getAmountDollar());
		category.setIsCurrencyDollar(voObject.getIsCurrencyDollar());
		category.setTypeExchange(voObject.getTypeExchange());
		category.setIvaTypeId(voObject.getIvaTypeId());
		category.setAppliedDateTimeUTC(voObject.getAppliedDateTimeUTC());
		if (voObject.getProjectId() != 0)
			category.setProject(new Project(voObject.getProjectId()));
		if (voObject.getCategoryType() == 1)
			category.setProject(null);
		category.setCategoryType(CategoryType.getEnum(voObject.getCategoryType()));
		category.setIsRRHH(voObject.getIsRRHH());
		category.setAppliedDateTimeUTC(voObject.getAppliedDateTimeUTC());
		category.setUpdatedDateTimeUTC(voObject.getUpdatedDateTimeUTC());
		
		return category;
	}

}
