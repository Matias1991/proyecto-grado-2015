package shared.interfaces.dataLayer;

import servicelayer.entity.businessEntity.Bill;
import shared.exceptions.ServerException;

public interface IDAOBills extends IDAOBase<Bill>{

	Bill getBill(String code, int projectId) throws ServerException;
	
	Bill getBill(String code) throws ServerException;
}
