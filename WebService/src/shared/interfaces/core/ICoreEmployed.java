package shared.interfaces.core;

import java.util.ArrayList;

import servicelayer.entity.valueObject.VOEmployed;
import servicelayer.entity.valueObject.VOSalarySummary;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreEmployed {

	void insertEmployed(VOEmployed voEmployed) throws ServerException;
	
	ArrayList<VOEmployed> getEmployess() throws ServerException;
	
    VOEmployed getEmployed(int id) throws ServerException, ClientException;
    
    VOSalarySummary estimateSalarySummary(VOSalarySummary voSummarySalary) throws ServerException;
    
    void deleteEmployed(int id) throws ServerException, ClientException;
    
    void updateEmployed(int id, VOEmployed voEmployed) throws ServerException, ClientException;
    
    ArrayList<Integer> getAllVersionSalarySummary(int employedId) throws ServerException, ClientException;
    
    VOSalarySummary getSalarySummaryByVersion(int employedId, int version) throws ServerException, ClientException;
}
