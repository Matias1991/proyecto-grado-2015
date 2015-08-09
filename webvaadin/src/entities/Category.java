package entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import servicelayer.service.ServiceWebStub.VOCategory;

public class Category {

	private int id;
	private String description;
	private double amount;
	private int categoryTypeId;
	private String categoryType;
	private int projectId;
	private boolean isRRHH;
	private Date createdDateTimeUTC;
	private String createDateTimeUTCToShow;
	
	public Category(){	}
	
	public Category(VOCategory voCategory)
	{
		this.id = voCategory.getId();
		this.setDescription(voCategory.getDescription());
		this.setAmount(voCategory.getAmount());
		this.setCategoryTypeId(voCategory.getCategoryType());
		this.setCategoryType(getCategoryTypeToShow());
		this.createdDateTimeUTC = voCategory.getCreateDateTimeUTC();
		
		this.setCreateDateTimeUTCToShow(new SimpleDateFormat("dd-MM-yyyy").format(createdDateTimeUTC));
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
	
	public String getCategoryTypeToShow()
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
	
	public String getIsRRHHToShow()
	{
		String result = "";
		if(isRRHH)
			result = "Recurso humando";
		else
			result = "Recurso material";
		
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

	public boolean getIsRRHH() {
		return isRRHH;
	}

	public void setIsRRHH(boolean isRRHH) {
		this.isRRHH = isRRHH;
	}

	public Date getCreatedDateTimeUTC() {
		return createdDateTimeUTC;
	}

	public void setCreatedDateTimeUTC(Date createdDateTimeUTC) {
		this.createdDateTimeUTC = createdDateTimeUTC;
	}

	public String getCreateDateTimeUTCToShow() {
		return createDateTimeUTCToShow;
	}

	public void setCreateDateTimeUTCToShow(String createDateTimeUTCToShow) {
		this.createDateTimeUTCToShow = createDateTimeUTCToShow;
	}
}
