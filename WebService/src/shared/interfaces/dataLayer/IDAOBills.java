package shared.interfaces.dataLayer;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.businessEntity.UserType;
import shared.exceptions.ServerException;

public interface IDAOBills extends IDAOBase<Bill> {

	Bill getBill(String code, int projectId) throws ServerException;

	Bill getBill(String code) throws ServerException;

	ArrayList<Bill> getBills(Date from, Date to, boolean isLiquidated, User userContext)
			throws ServerException;
	
	ArrayList<Bill> getBills(Date date, int projectId)
			throws ServerException;

	ArrayList<Bill> getBills(Date from, Date to, boolean isLiquidated,
			boolean withCharges, User userContext) throws ServerException;

	ArrayList<Bill> getBills(Date from, Date to, User userContext) throws ServerException;

	ArrayList<Bill> getBillsWithCharges(Date from, Date to, User userContext) throws ServerException;
	
	void deleteBills(int[] ids) throws ServerException;

	ArrayList<Bill> getBills(int projectId) throws ServerException;
	
	boolean liquidateBill(int billId) throws ServerException;
	
	ArrayList<Bill> getBills(int projectId, Date from, Date to) throws ServerException;

	ArrayList<Bill> getNotLiquidatedBills(User contextId) throws ServerException;
}
