package shared.interfaces.core;

import java.util.ArrayList;
import java.util.List;

import servicelayer.entity.valueObject.VOEmployedProject;
import servicelayer.entity.valueObject.VOProject;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreProject {

	void insertProject(VOProject voProject) throws ServerException, ClientException;
	
	ArrayList<VOProject> getProjects() throws ServerException;
}
