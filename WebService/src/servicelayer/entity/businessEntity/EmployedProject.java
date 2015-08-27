package servicelayer.entity.businessEntity;

import java.util.Date;

import servicelayer.entity.valueObject.VOEmployedProject;

public class EmployedProject {

	private Employed employed;
	private int distributionType;
	private int version;
	private int hours;
	private boolean enabled;
	private Date createdDateTimeUTC;
	private Date updatedDateTimeUTC;

	public EmployedProject() {

	}

	public EmployedProject(VOEmployedProject voEmployedProject) {
		this.employed = new Employed(voEmployedProject.getEmployedId());
		this.distributionType = voEmployedProject.getDistributionType();
		this.version = voEmployedProject.getVersion();
		this.hours = voEmployedProject.getHours();
		this.enabled = voEmployedProject.isEnabled();
	}

	public Employed getEmployed() {
		return employed;
	}

	public void setEmployed(Employed employed) {
		this.employed = employed;
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
