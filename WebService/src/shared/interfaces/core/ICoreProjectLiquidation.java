package shared.interfaces.core;

import java.util.ArrayList;
import java.util.Date;

import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.ProjectLiquidation;
import servicelayer.entity.businessEntity.User;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreProjectLiquidation {
	
	
	ProjectLiquidation getProjectLiquidationPreview(Date month, int projectId,
			double typeExchange) throws ServerException, ClientException;

	ArrayList<ProjectLiquidation> getProjectsLiquidationsByDate(Date month)
			throws ServerException, ClientException;

	void calculatePartnersEarnings(ProjectLiquidation projectLiquidation,
			double companyCostToSubstract, double typeExchange, Date to)
			throws ServerException;
	

}
