package entities;

import servicelayer.service.ServiceWebStub.VOCategory;

public class Category {

	private int id;
	private String description;
	private double amount;
	private String distributionType;
	private int projectId;
	
	public Category(){	}
	
	public Category(VOCategory voCategory)
	{
		this.id = voCategory.getId();
		this.setDescription(voCategory.getDescription());
		this.setAmount(voCategory.getAmount());
		//tOdo: eliminar esto cuando desde BE se devuelva el tipo de rubro
		this.setDistributionType(getDistributionType(voCategory.getDistributionType()));
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDistributionType() {
		return distributionType;
	}

	public void setDistributionType(String categoryType) {
		this.distributionType = categoryType;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDistributionType(int categoryTypeId)
	{
		String result = "";
		switch(categoryTypeId)
		{
			case 1: 
				  result = "Empresa";
				  break;
			case 2:
				  result = "Project";
				  break;
		    default:
		    	  result = "Sin asignacion";
		    	  break;
		}
		
		return result;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
}
