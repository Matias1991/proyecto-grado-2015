package entities;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import servicelayer.service.ServiceWebStub.VOCategory;

public class Category {

	private int id;
	private String description;
	private double amountPeso;
	private double amountDollar;
	private String amountToShow;
	private int categoryTypeId;
	private String categoryType;
	private int projectId;
	private boolean isRRHH;
	private Date createdDateTimeUTC;
	private String createDateTimeUTCToShow;
	private String projectName;
	private boolean isCurrencyDollar;
	private double typeExchange;
	private int ivaTypeId;
	private String ivaTypeToShow;
	private String typeExchangeToShow;
	private String totalAmountToShow;
	
	public Category(){	}
	
	public Category(VOCategory voCategory)
	{
		this.id = voCategory.getId();
		this.setDescription(voCategory.getDescription());
		this.setAmountPeso(voCategory.getAmountPeso());
		this.setAmountDollar(voCategory.getAmountDollar());
		this.setCategoryTypeId(voCategory.getCategoryType());
		this.setCategoryType(getCategoryTypeToShow());
		this.createdDateTimeUTC = voCategory.getAppliedDateTimeUTC();
		if(voCategory.getProjectId() != 0)
			this.projectName = voCategory.getProjectName();			
		else
			this.projectName = "Meerkat SYS";
		this.projectId = voCategory.getProjectId();
		this.isRRHH = voCategory.getIsRRHH();
		this.isCurrencyDollar = voCategory.getIsCurrencyDollar();
		this.setCreateDateTimeUTCToShow(new SimpleDateFormat("MM-yyyy").format(createdDateTimeUTC));	
		this.setTypeExchange(voCategory.getTypeExchange());
		
		if(isCurrencyDollar){
    		this.amountToShow = new DecimalFormat("U$S ###,###.###").format(this.amountDollar);
    		this.typeExchangeToShow = String.valueOf(this.typeExchange);
    		this.totalAmountToShow = new DecimalFormat("U$S ###,###.###").format(voCategory.getTotalAmountDollar());
    		
    	}
		else {
    		this.amountToShow = new DecimalFormat("$ ###,###.###").format(this.amountPeso);
    		this.typeExchangeToShow = "N/A";
    		this.totalAmountToShow = new DecimalFormat("$ ###,###.###").format(voCategory.getTotalAmountPeso());
    	}
		
		this.setIvaTypeId(voCategory.getIvaTypeId());

    	this.ivaTypeToShow = getIvaTypeToShow(voCategory.getIvaTypeId());    
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getAmountPeso() {
		return amountPeso;
	}

	public void setAmountPeso(double amount) {
		this.amountPeso = amount;
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
			result = "Humano";
		else
			result = "Material";
		
		return result;
	}
	
	public String getIsDollarToShow(){
		String result = "";
		if(isCurrencyDollar){
			result = "Dolares";
		}else{
			result = "Pesos";
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

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	

	public double getAmountDollar() {
		return amountDollar;
	}

	public void setAmountDollar(double amountDollar) {
		this.amountDollar = amountDollar;
	}	

	public String getAmountToShow() {
		return amountToShow;
	}

	public void setAmountToShow(String amountToShow) {
		this.amountToShow = amountToShow;
	}

	public boolean isCurrencyDollar() {
		return isCurrencyDollar;
	}

	public void setIsCurrencyDollar(boolean isCurrencyDollar) {
		this.isCurrencyDollar = isCurrencyDollar;
	}

	public double getTypeExchange() {
		return typeExchange;
	}

	public void setTypeExchange(double typeExchange) {
		this.typeExchange = typeExchange;
	}
	
	public String getTypeExchangeToShow() {
		return typeExchangeToShow;
	}

	public void setTypeExchangeToShow(String typeExchangeToShow) {
		this.typeExchangeToShow = typeExchangeToShow;
	}

	public int getIvaTypeId() {
		return ivaTypeId;
	}

	public void setIvaTypeId(int ivaTypeId) {
		this.ivaTypeId = ivaTypeId;
	}
	
	public String getIvaTypeToShow(int ivaTypeId) {
		ivaTypeToShow = "";
		switch (ivaTypeId) {
			case 1:
				ivaTypeToShow = "0%";
				break;
			case 2:
				ivaTypeToShow = "10%";
				break;
			case 3:
				ivaTypeToShow = "22%";
				break;
			default:
				break;
		}
		
		return ivaTypeToShow;
	}

	public String getIvaTypeToShow() {
		return ivaTypeToShow;
	}

	public void setIvaTypeToShow(String ivaTypeToShow) {
		this.ivaTypeToShow = ivaTypeToShow;
	}

	public String getTotalAmountToShow() {
		return totalAmountToShow;
	}

	public void setTotalAmountToShow(String totalAmountToShow) {
		this.totalAmountToShow = totalAmountToShow;
	}
	
}
