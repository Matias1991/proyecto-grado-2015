package shared.interfaces.dataLayer;

import java.util.Date;

import servicelayer.entity.businessEntity.ProjectLiquidation;
import shared.exceptions.ServerException;

public interface IDAOProjectsLiquidations extends IDAOBase<ProjectLiquidation> {
	
	boolean existLiquidation(Date appliedDate) throws ServerException;
	
	
}
