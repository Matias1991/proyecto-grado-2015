package shared.interfaces.core;

import java.util.ArrayList;

import servicelayer.entity.valueObject.VOCategory;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;

public interface ICoreCategory {

	void insertCategory(VOCategory voCategory) throws ServerException,
			ClientException;

	void deleteCateory(int id) throws ServerException, ClientException;

	VOCategory updateCategory(int id, VOCategory voCategory)
			throws ServerException, ClientException;

	VOCategory getCategory(int id) throws ServerException, ClientException;

	ArrayList<VOCategory> getCategories() throws ServerException;

	ArrayList<VOCategory> getCategoriesByProject(int projectId) throws ServerException, ClientException;
}
