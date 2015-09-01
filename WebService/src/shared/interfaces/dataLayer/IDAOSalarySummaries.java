package shared.interfaces.dataLayer;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.SalarySummary;
import shared.exceptions.ServerException;

public interface IDAOSalarySummaries {

	int insert(int employedId, SalarySummary salarySummary)
			throws ServerException;

	void delete(int employedId, int salarySummaryId) throws ServerException;

	void deleteSalarySummaries(int employedId) throws ServerException;

	int update(int employedId, SalarySummary salarySummary)
			throws ServerException;

	SalarySummary getLatestVersionSalarySummary(int employedId)
			throws ServerException;

	ArrayList<SalarySummary> getSalarySummaries(int employedId)
			throws ServerException;

	ArrayList<Integer> getALLVersionsSalarySummary(int employedId)
			throws ServerException;

	SalarySummary getSalarySummaryByVersion(int employedId, int version)
			throws ServerException;

	ArrayList<SalarySummary> getAllVersionsDateSalarySummary(
			int employedId) throws ServerException;
}
