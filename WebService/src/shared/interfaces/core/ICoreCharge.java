package shared.interfaces.core;

import java.util.ArrayList;
import servicelayer.entity.businessEntity.Charge;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreCharge {

	void insertCharge(Charge charge) throws ServerException,
			ClientException;

	void deleteCharge(int id) throws ServerException, ClientException;

	Charge updateCharge(int id, Charge charge) throws ServerException,
			ClientException;

	Charge getCharge(int id) throws ServerException, ClientException;

	ArrayList<Charge> getCharges() throws ServerException;
	
	ArrayList<Charge> getChargesByBill(int billId) throws ServerException;
	
	void deleteCharges(int[] ids) throws ServerException;
	
	ArrayList<Charge> getCharges(boolean isBillLiquidated, boolean isProjectClosed) throws ServerException;
}
