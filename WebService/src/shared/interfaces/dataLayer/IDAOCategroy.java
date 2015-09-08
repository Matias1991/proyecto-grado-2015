package shared.interfaces.dataLayer;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.Category;
import servicelayer.entity.businessEntity.CategoryType;
import shared.exceptions.ServerException;

public interface IDAOCategroy extends IDAOBase<Category> {

	ArrayList<Category> getCategories(String description,
			CategoryType categoryType) throws ServerException;

	ArrayList<Category> getCategoriesByProject(int idProject)
			throws ServerException;

	ArrayList<Category> getCategories(String description, int projectId)
			throws ServerException;

	ArrayList<Category> getCategoriesLastVersion(String description,
			CategoryType categoryType) throws ServerException;

	ArrayList<Category> getCategoriesLastVersion(String description,
			int projectId) throws ServerException;

	ArrayList<Category> getCategories(Date from, Date to)
			throws ServerException;
}
