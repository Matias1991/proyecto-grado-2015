package entities;

import servicelayer.service.ServiceWebStub.VOCategory;

public class Category {

	private int id;
	private String description;
	private double amount;
	private int categoryTypeId;
	private String categoryType;
	private int projectId;
	
	public Category(){	}
	
	public Category(VOCategory voCategory)
	{
		this.id = voCategory.getId();
		this.setDescription(voCategory.getDescription());
		this.setAmount(voCategory.getAmount());
		this.setCategoryTypeId(voCategory.getDistributionType());
		this.setCategoryType(getDistributionTypeToShow());
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getDistributionTypeToShow()
	{
		String result = "";
		switch(categoryTypeId)
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

	public String getCategoryType() {
		return categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}

	public int getCategoryTypeId() {
		return categoryTypeId;
	}

	public void setCategoryTypeId(int categoryTypeId) {
		this.categoryTypeId = categoryTypeId;
	}
}