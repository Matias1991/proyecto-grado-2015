package servicelayer.entity.valueObject;

import java.io.Serializable;
import java.util.Date;

public class VOProject implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String notes;
	private Date createdDateTimeUTC;
	private Date updatedDateTimeUTC;
	private boolean enabled;
	private String description;
	private VOUser manager;
	private VOEmployed seller;
	
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
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
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
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public VOUser getManager() {
		return manager;
	}
	public void setManager(VOUser manager) {
		this.manager = manager;
	}
	public VOEmployed getSeller() {
		return seller;
	}
	public void setSeller(VOEmployed seller) {
		this.seller = seller;
	}
	
}
