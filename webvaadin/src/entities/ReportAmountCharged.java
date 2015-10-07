package entities;

public class ReportAmountCharged {

	private double totalAmount;
	private double amountCharged;
	private double amountReceivable;
	
	public ReportAmountCharged()
	{
		
	}
	
	public ReportAmountCharged(double totalAmount, double amountCharged, double amountReceivable)
	{
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
}
