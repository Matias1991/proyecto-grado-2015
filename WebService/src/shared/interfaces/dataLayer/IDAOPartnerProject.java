package shared.interfaces.dataLayer;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.PartnerProject;
import shared.exceptions.ServerException;

public interface IDAOPartnerProject {
	
	int insertPartnerProject(int projectId, PartnerProject partnerProject) throws ServerException;

	ArrayList<PartnerProject> getPartnersProject(int projectId) throws ServerException;
}
