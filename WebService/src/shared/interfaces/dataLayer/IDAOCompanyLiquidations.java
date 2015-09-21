package shared.interfaces.dataLayer;

import java.util.Date;

import servicelayer.entity.businessEntity.CompanyLiquidation;
import shared.exceptions.ServerException;

public interface IDAOCompanyLiquidations extends IDAOBase<CompanyLiquidation>  {
	
	boolean existLiquidation(Date appliedDate) throws ServerException;

}
