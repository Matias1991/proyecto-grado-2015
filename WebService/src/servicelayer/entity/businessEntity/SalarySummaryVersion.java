package servicelayer.entity.businessEntity;

import java.util.Date;

import servicelayer.entity.valueObject.VOSalarySummaryVersion;

public class SalarySummaryVersion {

	private Date createdDateTimeUTC;
	private int version;

	public SalarySummaryVersion() {

	}

	public SalarySummaryVersion(VOSalarySummaryVersion voSalarySummaryVersion) {
		this.createdDateTimeUTC = voSalarySummaryVersion
				.getCreatedDateTimeUTC();
		this.version = voSalarySummaryVersion.getVersion();
	}

	public Date getCreatedDateTimeUTC() {
		return createdDateTimeUTC;
	}

	public void setCreatedDateTimeUTC(Date createdDateTimeUTC) {
		this.createdDateTimeUTC = createdDateTimeUTC;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

}
