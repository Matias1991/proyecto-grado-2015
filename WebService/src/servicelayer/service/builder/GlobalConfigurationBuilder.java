package servicelayer.service.builder;

import servicelayer.entity.businessEntity.GlobalConfiguration;
import servicelayer.entity.valueObject.VOGlobalConfiguration;

public class GlobalConfigurationBuilder extends BaseBuilder<VOGlobalConfiguration, GlobalConfiguration>{

	@Override
	public VOGlobalConfiguration BuildVOObject(
			GlobalConfiguration businessObject) {
		
		VOGlobalConfiguration voGlobalConfiguration = new VOGlobalConfiguration();
		voGlobalConfiguration.setId(businessObject.getId());
		voGlobalConfiguration.setCode(businessObject.getCode());
		voGlobalConfiguration.setValue(businessObject.getValue());
		voGlobalConfiguration.setDescription(businessObject.getDescription());
		
		return voGlobalConfiguration;
	}

	@Override
	public GlobalConfiguration BuildBusinessObject(
			VOGlobalConfiguration voObject) {
		
		GlobalConfiguration globalConfiguration = new GlobalConfiguration();
		globalConfiguration.setId(voObject.getId());
		globalConfiguration.setCode(voObject.getCode());
		globalConfiguration.setValue(voObject.getValue());
		globalConfiguration.setDescription(voObject.getDescription());
		
		return globalConfiguration;
	}
}
