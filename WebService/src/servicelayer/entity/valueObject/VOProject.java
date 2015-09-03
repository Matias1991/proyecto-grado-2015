package servicelayer.entity.valueObject;

import java.io.Serializable;
import java.util.Date;

public class VOProject implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private Date createdDateTimeUTC;
	private Date updatedDateTimeUTC;
	private boolean closed;
	private String description;
	private int managerId;
	private String managerName;
	private String managerLastName;
	private int sellerId;
	private String sellerName;
	private String sellerLastname;
	private double amount;
	private boolean isCurrencyDollar;
	private VOProjectEmployed[] voEmployedProjects;
	private VOProjectPartner[] voPartnerProjects;
	
	public VOProjectEmployed[] getVoEmployedProjects() {
		return voEmployedProjects;
	}

	public void setVoEmployedProjects(VOProjectEmployed[] voEmployedProjects) {
		this.voEmployedProjects = voEmployedProjects;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreatedDateTimeUTC() {
		return createdDateTimeUTC;
	}

	public void setCreatedDateTimeUTC(Date createdDateTimeUTC) {
		this.createdDateTimeUTC = createdDateTimeUTC;
	}

	public Date getUpdatedDateTimeUTC() {
		return updatedDateTimeUTC;
	}

	public void setUpdatedDateTimeUTC(Date updatedDateTimeUTC) {
		this.updatedDateTimeUTC = updatedDateTimeUTC;
	}

	public boolean isClosed() {
		return closed;
	}

	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managetName) {
		this.managerName = managetName;
	}

	public String getManagerLastName() {
		return managerLastName;
	}

	public void setManagerLastName(String managerLastName) {
		this.managerLastName = managerLastName;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerLastname() {
		return sellerLastname;
	}

	public void setSellerLastname(String sellerLastname) {
		this.sellerLastname = sellerLastname;
	}

	public VOProjectPartner[] getVoPartnerProjects() {
		return voPartnerProjects;
	}

	public void setVoPartnerProjects(VOProjectPartner[] voPartnerProjects) {
		this.voPartnerProjects = voPartnerProjects;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public boolean getIsCurrencyDollar() {
		return isCurrencyDollar;
	}

	public void setIsCurrencyDollar(boolean isCurrencyDollar) {
		this.isCurrencyDollar = isCurrencyDollar;
	}
}
