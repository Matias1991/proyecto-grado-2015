package shared.interfaces.dataLayer;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.DistributionType;
import shared.exceptions.ServerException;

public interface IDAODistributionType {
	
	ArrayList<DistributionType> getOjects() throws ServerException;
}
