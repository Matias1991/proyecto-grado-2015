package shared.interfaces.dataLayer;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.Charge;
import shared.exceptions.ServerException;

public interface IDAOCharges extends IDAOBase<Charge> {

	Charge getCharge(String number) throws ServerException;
	
	ArrayList<Charge> getChargesByBill(int billId) throws ServerException;
	
	void deleteCharges(int[] ids) throws ServerException;
	
	ArrayList<Charge> getCharges(boolean isBillLiquidated, boolean isProjectClosed) throws ServerException;
}
