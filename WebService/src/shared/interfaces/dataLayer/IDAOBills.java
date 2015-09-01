package shared.interfaces.dataLayer;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.Bill;
import shared.exceptions.ServerException;

public interface IDAOBills extends IDAOBase<Bill> {

	Bill getBill(String code, int projectId) throws ServerException;

	Bill getBill(String code) throws ServerException;

	ArrayList<Bill> getBills(Date from, Date to, boolean isLiquidated)
			throws ServerException;

	ArrayList<Bill> getBills(Date from, Date to, boolean isLiquidated,
			boolean withCharges) throws ServerException;;

	ArrayList<Bill> getBills(Date from, Date to) throws ServerException;

	ArrayList<Bill> getBillsWithCharges(Date from, Date to) throws ServerException;
	
	void deleteBills(int[] ids) throws ServerException;

	ArrayList<Bill> getBills(int projectId) throws ServerException;
}
