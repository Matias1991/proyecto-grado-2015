package shared.interfaces.dataLayer;

import servicelayer.entity.businessEntity.Bill;
import shared.exceptions.ServerException;

public interface IDAOBills extends IDAOBase<Bill>{

	Bill getBill(String description, int projectId) throws ServerException;
}
