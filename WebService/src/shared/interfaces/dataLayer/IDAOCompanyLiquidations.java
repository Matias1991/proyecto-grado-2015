package shared.interfaces.dataLayer;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.CompanyLiquidation;
import shared.exceptions.ServerException;

public interface IDAOCompanyLiquidations extends IDAOBase<CompanyLiquidation> {

	boolean existLiquidation(Date appliedDate) throws ServerException;

	CompanyLiquidation getCompanyLiquidationByDate(Date appliedDate)
			throws ServerException;

	ArrayList<CompanyLiquidation> getCompanyLiquidations(Date date)
			throws ServerException;

}
