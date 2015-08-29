package servicelayer.entity.valueObject;

import java.io.Serializable;
import java.util.Date;

public class VOPartnerProject implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private int employedId;
	private String employedName;
	private String employedLastName;
	private int distributionType;
	private int version;
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

	public String getEmployedLastName() {
		return employedLastName;
	}

	public void setEmployedLastName(String employedLastName) {
		this.employedLastName = employedLastName;
	}

	public int getDistributionType() {
		return distributionType;
	}

	public void setDistributionType(int distributionType) {
		this.distributionType = distributionType;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
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
