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
}
