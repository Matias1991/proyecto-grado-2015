package shared.interfaces.dataLayer;

import servicelayer.entity.businessEntity.Charge;
import shared.exceptions.ServerException;

public interface IDAOCharges extends IDAOBase<Charge> {

	Charge getCharge(String number) throws ServerException;
}
