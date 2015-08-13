package shared.interfaces.dataLayer;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.Category;
import shared.exceptions.ServerException;

public interface IDAOCategroy extends IDAOBase<Category> {
	
	ArrayList<Category> getCategoriesByDescription(String description) throws ServerException;
	
	ArrayList<Category> getCategories(String description, int projectId) throws ServerException;;

}
