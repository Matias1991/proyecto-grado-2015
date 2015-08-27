package shared.interfaces.core;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.valueObject.VOBill;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreBill {

	void insertBill(VOBill voBill) throws ServerException, ClientException;

	void deleteBill(int id) throws ServerException, ClientException;

	void deleteBills(int[] ids) throws ServerException;

	VOBill updateBill(int id, VOBill voBill) throws ServerException,
			ClientException;

	VOBill getBill(int id) throws ServerException, ClientException;

	ArrayList<VOBill> getBills() throws ServerException;

	ArrayList<VOBill> getBills(Date from, Date to, int projectId, String code,
			boolean isLiquidated) throws ServerException;
}
