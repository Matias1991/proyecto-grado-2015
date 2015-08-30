package shared.interfaces.dataLayer;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.ProjectPartner;
import shared.exceptions.ServerException;

public interface IDAOProjectPartners {
	
	int insertPartnerProject(int projectId, ProjectPartner partnerProject) throws ServerException;

	ArrayList<ProjectPartner> getPartnersProject(int projectId) throws ServerException;
}
