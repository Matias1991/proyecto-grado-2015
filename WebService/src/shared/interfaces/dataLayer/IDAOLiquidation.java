package shared.interfaces.dataLayer;

import java.util.Date;

import servicelayer.entity.businessEntity.Liquidation;
import shared.exceptions.ServerException;

public interface IDAOLiquidation extends IDAOBase<Liquidation> {
	
	boolean existLiquidation(Date appliedDate) throws ServerException;
	
	
}
