package servicelayer.entity.businessEntity;

import java.util.ArrayList;
import java.util.Date;

import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOCharges;

public class Bill {

	private int id;
	private String code;
	private String description;
	private boolean isCurrencyDollar;
	private double typeExchange;
	private Date appliedDateTimeUTC;
	private Project project;
	private boolean isLiquidated;
	
	private double amountPeso;
	private double amountDollar;
	
	private double totalAmountDollar;
	private double totalAmountPeso;
	
	private double amountChargedDollar;
	private double amountChargedPeso;
	
	private double totalAmountChargedDollar;
	private double totalAmountChargedPeso;
	
	private IVA_Type ivaType;
	
	private IDAOCharges iDAOCharges;

	public Bill() {

	}

	public Bill(int id)
	{
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getAppliedDateTimeUTC() {
		return appliedDateTimeUTC;
	}

	public void setAppliedDateTimeUTC(Date appliedDateTimeUTC) {
		this.appliedDateTimeUTC = appliedDateTimeUTC;
	}

	public boolean getIsLiquidated() {
		return isLiquidated;
	}

	public void setIsLiquidated(boolean isLiquidated) {
		this.isLiquidated = isLiquidated;
	}

	public boolean getIsCurrencyDollar() {
		return isCurrencyDollar;
	}

	public void setIsCurrencyDollar(boolean isCurrencyDollar) {
		this.isCurrencyDollar = isCurrencyDollar;
	}

	public double getTypeExchange() {
		return typeExchange;
	}

	public void setTypeExchange(double typeExchange) {
		this.typeExchange = typeExchange;
	}

	public double getAmountPeso() {
		return amountPeso;
	}

	public void setAmountPeso(double amountPeso) {
		this.amountPeso = amountPeso;
	}

	public double getAmountDollar() {
		return amountDollar;
	}

	public void setAmountDollar(double amountDollar) {
		this.amountDollar = amountDollar;
	}

	public double getAmountChargedDollar() {
		return amountChargedDollar;
	}

	public void setAmountChargedDollar(double amountChargedDollar) {
		this.amountChargedDollar = amountChargedDollar;
	}

	public double getAmountChargedPeso() {
		return amountChargedPeso;
	}

	public void setAmountChargedPeso(double amountChargedPeso) {
		this.amountChargedPeso = amountChargedPeso;
	}
	
	public double getTotalAmountDollar() {
		return totalAmountDollar;
	}

	public void setTotalAmountDollar(double totalAmountDollar) {
		this.totalAmountDollar = totalAmountDollar;
	}

	public double getTotalAmountPeso() {
		return totalAmountPeso;
	}

	public void setTotalAmountPeso(double totalAmountPeso) {
		this.totalAmountPeso = totalAmountPeso;
	}

	public double getTotalAmountChargedDollar() {
		return totalAmountChargedDollar;
	}

	public void setTotalAmountChargedDollar(double totalAmountChargedDollar) {
		this.totalAmountChargedDollar = totalAmountChargedDollar;
	}

	public double getTotalAmountChargedPeso() {
		return totalAmountChargedPeso;
	}

	public void setTotalAmountChargedPeso(double totalAmountChargedPeso) {
		this.totalAmountChargedPeso = totalAmountChargedPeso;
	}

	public IVA_Type getIvaType() {
		return ivaType;
	}

	public void setIvaType(IVA_Type ivaType) {
		this.ivaType = ivaType;
	}
	
	public void setIDAOCharges(IDAOCharges iDAOCharges) {
		this.iDAOCharges = iDAOCharges;
	}
	
	public ArrayList<Charge> getCharges() throws ServerException
	{
		return iDAOCharges.getChargesByBill(this.id);
	}
}
