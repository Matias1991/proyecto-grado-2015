package shared.interfaces.dataLayer;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.Category;
import servicelayer.entity.businessEntity.CategoryType;
import servicelayer.entity.businessEntity.User;
import shared.exceptions.ServerException;

public interface IDAOCategroies extends IDAOBase<Category> {

	ArrayList<Category> getCategories(String description,
			CategoryType categoryType) throws ServerException;

	ArrayList<Category> getCategoriesByProject(int idProject)
			throws ServerException;

	ArrayList<Category> getCategories(String description, int projectId)
			throws ServerException;

	ArrayList<Category> getCategoriesLastVersion(String description,
			CategoryType categoryType) throws ServerException;

	ArrayList<Category> getCategories(String description, boolean isCurrencyDollar, Date date)
			throws ServerException;

	ArrayList<Category> getCategoriesLastVersion(String description,
			int projectId) throws ServerException;

	ArrayList<Category> getCategories(Date from, Date to, User userContext)
			throws ServerException;

	ArrayList<Category> getCategories(User userContext) throws ServerException;

	ArrayList<Category> getCategories(int projectId, Date from, Date to)
			throws ServerException;

	ArrayList<Category> getCategoriesAllVersions(int id, Date date)
			throws ServerException;

	int insertNewVersion(Category obj) throws ServerException;
}
