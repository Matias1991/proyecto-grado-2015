package servicelayer.entity.valueObject.report;

import java.io.Serializable;

import servicelayer.entity.valueObject.VOProject;

public class VOReportProfitByProject implements Serializable{

	private static final long serialVersionUID = 1L;

	private VOProject voProject;
	private double profit;
	
	public VOProject getVoProject() {
		return voProject;
	}
	public void setVoProject(VOProject voProject) {
		this.voProject = voProject;
	}
	public double getProfit() {
		return profit;
	}
	public void setProfit(double profit) {
		this.profit = profit;
	}
}
