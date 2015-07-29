package entities;

import servicelayer.service.ServiceWebStub.VOCategory;

public class Category {

	private int id;
	private String description;
	private double amount;
	private int distributionType;
	private int projectId;
	
	public Category(){	}
	
	public Category(VOCategory voCategory)
	{
		this.id = voCategory.getId();
		this.setDescription(voCategory.getDescription());
		this.setAmount(voCategory.getAmount());
		this.setDistributionType(voCategory.getDistributionType());
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

	public int getDistributionType() {
		return distributionType;
	}

	public void setDistributionType(int categoryType) {
		this.distributionType = categoryType;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDistributionTypeToShow()
	{
		String result = "";
		switch(distributionType)
		{
			case 1: 
				  result = "Empresa";
				  break;
			case 2:
				  result = "Proyecto";
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
