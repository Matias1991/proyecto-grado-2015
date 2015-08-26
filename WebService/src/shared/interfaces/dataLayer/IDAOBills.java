package shared.interfaces.dataLayer;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.Bill;
import shared.exceptions.ServerException;

public interface IDAOBills extends IDAOBase<Bill>{

	Bill getBill(String code, int projectId) throws ServerException;
	
	Bill getBill(String code) throws ServerException;
	
	ArrayList<Bill> getBills(Date from, Date to, int projectId, String code, boolean isLiquidated) throws ServerException;
	
	void deleteBills(int [] ids) throws ServerException;
}
