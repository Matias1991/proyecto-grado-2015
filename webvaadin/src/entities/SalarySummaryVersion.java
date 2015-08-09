package entities;


import java.util.Date;

import servicelayer.service.ServiceWebStub.VOSalarySummaryVersion;



public class SalarySummaryVersion {
	private int version;
	private Date createdDateTimeUTC;
	
	
	public SalarySummaryVersion(){
		
	}	
	
	public SalarySummaryVersion(VOSalarySummaryVersion voSalarySummaryVersion){
		this.createdDateTimeUTC = voSalarySummaryVersion.getCreatedDateTimeUTC();
		this.version = voSalarySummaryVersion.getVersion();
	}	
	
	public int getVersion() {
		return version;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
	
	public Date getCreatedDateTimeUTC() {
		return createdDateTimeUTC;
	}
	
	public void setCreatedDateTimeUTC(Date createdDateTimeUTC) {
		this.createdDateTimeUTC = createdDateTimeUTC;
	}	

	public VOSalarySummaryVersion toVOSalarySummaryVersion(){
		VOSalarySummaryVersion voSalarySummaryVersion = new VOSalarySummaryVersion();
		voSalarySummaryVersion.setCreatedDateTimeUTC(this.createdDateTimeUTC);
		voSalarySummaryVersion.setVersion(this.version);
		
		return voSalarySummaryVersion;
	}

}
