package entities;

public class ReportAmountCharged {

	private int projectId;
	private String projectName;
	private double totalAmount;
	private double amountCharged;
	private double amountReceivable;
	
	public ReportAmountCharged()
	{
		
	}
	
	public ReportAmountCharged(String projectName, double totalAmount, double amountCharged, double amountReceivable)
	{
		this.projectName = projectName;
		this.totalAmount = totalAmount;
		this.amountCharged = amountCharged;
		this.amountReceivable = amountReceivable;
	}
	
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public double getAmountCharged() {
		return amountCharged;
	}
	public void setAmountCharged(double amountCharged) {
		this.amountCharged = amountCharged;
	}
	public double getAmountReceivable() {
		return amountReceivable;
	}
	public void setAmountReceivable(double amountReceivable) {
		this.amountReceivable = amountReceivable;
	}	
	
	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
}
