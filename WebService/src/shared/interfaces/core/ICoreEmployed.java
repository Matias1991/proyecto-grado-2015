package shared.interfaces.core;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.SalarySummary;
import servicelayer.entity.valueObject.VOSalarySummaryVersion;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreEmployed {

	void insertEmployed(Employed employed, SalarySummary salarySummary) throws ServerException,
			ClientException;

	ArrayList<Employed> getEmployess() throws ServerException;

	Employed getEmployed(int id) throws ServerException, ClientException;

	SalarySummary estimateSalarySummary(SalarySummary voSummarySalary)
			throws ServerException;

	void deleteEmployed(int id) throws ServerException, ClientException;

	void updateEmployed(int id, Employed employed, SalarySummary salarySummary) throws ServerException,
			ClientException;

	ArrayList<Integer> getAllVersionSalarySummary(int employedId)
			throws ServerException, ClientException;

	ArrayList<VOSalarySummaryVersion> getAllSalarySummaryVersion(int employedId)
			throws ServerException, ClientException;

	SalarySummary getSalarySummaryByVersion(int employedId, int version)
			throws ServerException, ClientException;

	// ArrayList<VOEmployedProject> getEmployedHours() throws ServerException,
	// ClientException;
}
