package shared.interfaces.core;

import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.Category;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreCategory {

	void insertCategory(Category category) throws ServerException,
			ClientException;

	void deleteCateory(int id) throws ServerException, ClientException;

	Category updateCategory(int id, Category category)
			throws ServerException, ClientException;

	Category getCategory(int id) throws ServerException, ClientException;

	ArrayList<Category> getCategories() throws ServerException;

	ArrayList<Category> getCategoriesByProject(int projectId) throws ServerException, ClientException;

	ArrayList<Category> getCategories(Date from, Date to) throws ServerException;
	
	ArrayList<Category> getCategoriesByManager(Date from, Date to, int managerId) throws ServerException;
}
