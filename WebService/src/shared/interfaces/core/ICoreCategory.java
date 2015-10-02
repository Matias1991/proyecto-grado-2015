package shared.interfaces.core;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.Category;
import servicelayer.entity.businessEntity.CategoryType;
import servicelayer.entity.businessEntity.IVA_Type;
import servicelayer.entity.businessEntity.User;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreCategory {

	void insertCategory(Category category) throws ServerException,
			ClientException;

	void deleteCateory(int id) throws ServerException, ClientException;

	Category updateCategory(int id, Category category) throws ServerException,
			ClientException;

	Category getCategory(int id) throws ServerException, ClientException;

	ArrayList<Category> getCategories(User userContext) throws ServerException;

	ArrayList<Category> getCategoriesByProject(int projectId)
			throws ServerException, ClientException;

	ArrayList<Category> getCategories(Date from, Date to, User userContext)
			throws ServerException;

	double getTotalAmount(double amount, IVA_Type ivaType);
	
	ArrayList<Category> getCategoriesAllVersions(int id, Date date)
			throws ServerException;
	
	ArrayList<Category> getCategories(String description, boolean isCurrencyDollar, Date date)
			throws ServerException;
}
