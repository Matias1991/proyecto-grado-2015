package shared.interfaces.core;

import java.util.ArrayList;
import servicelayer.entity.valueObject.VOCharge;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreCharge {

	void insertCharge(VOCharge voCharge) throws ServerException,
			ClientException;

	void deleteCharge(int id) throws ServerException, ClientException;

	void deleteBills(int[] ids) throws ServerException;

	VOCharge updateCharge(int id, VOCharge voCharge) throws ServerException,
			ClientException;

	VOCharge getCharge(int id) throws ServerException, ClientException;

	ArrayList<VOCharge> getCharges() throws ServerException;
}
