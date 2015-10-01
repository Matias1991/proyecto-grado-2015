package shared.interfaces.dataLayer;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.ProjectLiquidation;
import shared.exceptions.ServerException;

public interface IDAOProjectsLiquidations extends IDAOBase<ProjectLiquidation> {

	boolean existLiquidation(Date appliedDate) throws ServerException;

	ArrayList<ProjectLiquidation> getProjectsLiquidationsByDate(Date appliedDate)
			throws ServerException;

	ArrayList<ProjectLiquidation> getProjectsWithMoreEarnings(Date from,
			Date to, boolean isCurrencyDollar, int count)
			throws ServerException;

	ArrayList<ProjectLiquidation> getProjectLiquidations(int projectId,
			Date date, boolean isCurrencyDollar) throws ServerException;

	ProjectLiquidation getProjectLiquidationByDate(Date appliedDate, int projectId)
			throws ServerException;
}
