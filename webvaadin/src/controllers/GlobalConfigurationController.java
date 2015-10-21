package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.GetGlobalConfigurations;
import servicelayer.service.ServiceWebStub.UpdateGlobalConfiguration;
import servicelayer.service.ServiceWebStub.VOGlobalConfiguration;
import utils.PopupWindow;
import entities.GlobalConfiguration;

public class GlobalConfigurationController {

	public static Collection<GlobalConfiguration> getGlobalConfigurations()
	{
		Collection<GlobalConfiguration> globalConfigurations = new ArrayList<GlobalConfiguration>();
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetGlobalConfigurations getGlobalConfigurations = new GetGlobalConfigurations();
			
			VOGlobalConfiguration [] voGlobalConfigurations = service.getGlobalConfigurations(getGlobalConfigurations).get_return();

			for(VOGlobalConfiguration voGlobalConfiguration : voGlobalConfigurations)
			{
				GlobalConfiguration globalConfiguration = new GlobalConfiguration(voGlobalConfiguration);
				globalConfigurations.add(globalConfiguration);
			}
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return globalConfigurations;
	}
	
	public static GlobalConfiguration updateConfiguration(int id, GlobalConfiguration globalConfiguration)
	{
		GlobalConfiguration result = null;
		try {
			ServiceWebStub service = new ServiceWebStub();
			UpdateGlobalConfiguration updateGlobalConfiguration = new UpdateGlobalConfiguration();
			
			VOGlobalConfiguration voGlobalConfiguration = new VOGlobalConfiguration();
			
			voGlobalConfiguration.setValue(globalConfiguration.getValue());
			voGlobalConfiguration.setDescription(globalConfiguration.getDescription());
			
			updateGlobalConfiguration.setId(id);
			updateGlobalConfiguration.setVoGlobalConfiguration(voGlobalConfiguration);
			
			 result = new GlobalConfiguration(service.updateGlobalConfiguration(updateGlobalConfiguration).get_return());
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
