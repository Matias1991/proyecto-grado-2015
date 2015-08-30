package servicelayer.entity.valueObject;

import java.io.Serializable;
import java.util.Date;

public class VOProjectEmployed implements Serializable {

	private static final long serialVersionUID = 1L;
	private int employedId;
	private String employedName;
	private String employedLastname;	
	private int version;
	private int hours;
	private boolean enabled;
	private Date createdDateTimeUTC;
	private Date updatedDateTimeUTC;

	public int getEmployedId() {
		return employedId;
	}

	public void setEmployedId(int employedId) {
		this.employedId = employedId;
	}

	public String getEmployedName() {
		return employedName;
	}

	public void setEmployedName(String employedName) {
		this.employedName = employedName;
	}

	public String getEmployedLastname() {
		return employedLastname;
	}

	public void setEmployedLastname(String employedLastname) {
		this.employedLastname = employedLastname;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public int getHours() {
		return hours;
	}

	public void setHours(int hours) {
		this.hours = hours;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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
}
