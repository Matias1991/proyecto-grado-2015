package shared.interfaces.core;

import java.util.ArrayList;
import servicelayer.entity.valueObject.VOProject;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreProject {

	void insertProject(VOProject voProject) throws ServerException,
			ClientException;

	ArrayList<VOProject> getProjects() throws ServerException;

	void deleteProject(int id) throws ServerException, ClientException;

	ArrayList<VOProject> getProjectByStatus(boolean projectStatus)
			throws ServerException;

	VOProject getProject(int id) throws ServerException, ClientException;
}
