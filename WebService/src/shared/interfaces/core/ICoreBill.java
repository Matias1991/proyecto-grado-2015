package shared.interfaces.core;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.IVA_Type;
import servicelayer.entity.businessEntity.User;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreBill {

	void insertBill(Bill bill) throws ServerException, ClientException;

	void deleteBill(int id) throws ServerException, ClientException;

	void deleteBills(int[] ids) throws ServerException;

	Bill updateBill(int id, Bill voBill) throws ServerException,
			ClientException;

	Bill getBill(int id) throws ServerException, ClientException;

	ArrayList<Bill> getBills() throws ServerException;

	ArrayList<Bill> getBills(Date from, Date to, boolean isLiquidated,
			User userContext) throws ServerException;

	ArrayList<Bill> getBills(Date date, boolean isCurrencyDollar) throws ServerException;

	ArrayList<Bill> getBills(Date from, Date to, boolean isLiquidated,
			boolean withCharges, User userContext) throws ServerException;

	ArrayList<Bill> getBills(Date from, Date to, User userContext)
			throws ServerException;

	ArrayList<Bill> getBillsWithCharges(Date from, Date to, User userContext)
			throws ServerException;

	ArrayList<Bill> getBills(int projectId) throws ServerException;

	boolean liquidateBill(int billId) throws ServerException;

	double getTotalAmount(double amount, IVA_Type ivaType);

	ArrayList<Bill> getNotLiquidatedBills(User userContext)
			throws ServerException;

	double getTotalAmountBills(int projectId, Date from, Date to)
			throws Exception;
}
