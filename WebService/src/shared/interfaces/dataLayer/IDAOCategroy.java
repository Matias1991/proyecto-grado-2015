package shared.interfaces.dataLayer;

import servicelayer.entity.businessEntity.Category;
import shared.exceptions.ServerException;

public interface IDAOCategroy extends IDAOBase<Category> {
	
	Category getCategoryByDescription(String description) throws ServerException;

}
