package entities;

import servicelayer.service.ServiceWebStub.VOGlobalConfiguration;

public class GlobalConfiguration {

	private int id;
	private String code;
	private String value;
	private String description;
	
	public GlobalConfiguration()
	{
		
	}
	
	public GlobalConfiguration(VOGlobalConfiguration voGlobalConfiguration)
	{
		this.id = voGlobalConfiguration.getId();
		this.code = voGlobalConfiguration.getCode();
		this.value = voGlobalConfiguration.getValue();
		this.description = voGlobalConfiguration.getDescription();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
