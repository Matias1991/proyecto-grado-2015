package shared.interfaces.dataLayer;

import servicelayer.entity.businessEntity.Employed;
import shared.exceptions.ServerException;

public interface IDAOEmployees extends IDAOBase<Employed>{
	public int getCountPartners () throws ServerException;
}
