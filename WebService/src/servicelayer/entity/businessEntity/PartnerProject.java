package servicelayer.entity.businessEntity;

import java.util.Date;

import servicelayer.entity.valueObject.VOPartnerProject;

public class PartnerProject {
	private Project project;
	private Employed employed;
	private int distributionType;
	private int version;
	private boolean enabled;
	private Date createdDateTimeUTC;
	private Date updatedDateTimeUTC;

	public PartnerProject(){
		
	}
	
	public PartnerProject(VOPartnerProject voPartnerProject){
		this.project = new Project(voPartnerProject.getProject());
		this.employed = new Employed(voPartnerProject.getEmployed());
		this.distributionType = voPartnerProject.getDistributionType();
		this.version = voPartnerProject.getVersion();
		this.enabled = voPartnerProject.isEnabled();
		this.createdDateTimeUTC = voPartnerProject.getCreatedDateTimeUTC();
		this.updatedDateTimeUTC = voPartnerProject.getUpdatedDateTimeUTC();		
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
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
