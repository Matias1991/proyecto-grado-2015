package shared.interfaces.dataLayer;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.SalarySummary;
import shared.exceptions.ServerException;

public interface IDAOSalarySummaries {
	
	int insert(int employedId, SalarySummary salarySummary) throws ServerException;
	
	void delete(int employedId, int salarySummaryId) throws ServerException;

    SalarySummary getSalarySummary(int employedId, int version) throws ServerException;
    
    SalarySummary getLatestVersionSalarySummary(int employedId) throws ServerException;
	
	ArrayList<SalarySummary> getSalarySummaries(int employedId) throws ServerException;
}