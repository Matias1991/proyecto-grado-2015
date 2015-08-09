package servicelayer.entity.valueObject;

import java.io.Serializable;
import java.util.Date;

public class VOSalarySummaryVersion implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int version;
	private Date createdDateTimeUTC;
	
	
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
	
	
	

}
