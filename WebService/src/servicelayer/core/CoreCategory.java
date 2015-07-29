package servicelayer.core;

import java.util.ArrayList;
import java.util.Date;

import datalayer.daos.DAOCategories;
import servicelayer.entity.businessEntity.Category;
import servicelayer.entity.valueObject.VOCategory;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreCategory;
import shared.interfaces.dataLayer.IDAOCategroy;

public class CoreCategory implements ICoreCategory {

	private static CoreCategory instance = null;
	private IDAOCategroy iDAOCategory;

	private CoreCategory() throws ServerException {
		iDAOCategory = new DAOCategories();
	}

	public static CoreCategory GetInstance() throws ServerException {
		if (instance == null) {
			instance = new CoreCategory();
		}
		return instance;
	}

	@Override
	public void insertCategory(VOCategory voCategory) throws ServerException,
			ClientException {

		Category category = new Category(voCategory);
		category.setCreateDateTimeUTC(new Date());
		iDAOCategory.insert(category);
	}

	@Override
	public void deleteCateory(int id) throws ServerException, ClientException {
		Category category = iDAOCategory.getObject(id);
		if (category == null){
			throw new ClientException("No existe rubro con ese id");
		} else {
			iDAOCategory.delete(id);
		}
		
	}

	@Override
	public VOCategory updateCategory(int id, VOCategory voCategory)
			throws ServerException, ClientException {
		Category category = new Category(voCategory);
		iDAOCategory.update(id, category);
		return getCategory(id);
	}

	@Override
	public VOCategory getCategory(int id) throws ServerException,
			ClientException {
		Category category;
		VOCategory voCategory = null;
		
		category = iDAOCategory.getObject(id);
		if(category != null){
			voCategory = BuildVOCategory(category);
		} else {
			throw new ClientException ("No existe ningún rubro con ese id");
		}
		
		return voCategory;
	}

	@Override
	public ArrayList<VOCategory> getCategories() throws ServerException {
		ArrayList<Category> categories;
		ArrayList<VOCategory> voCategories = null;

		categories = iDAOCategory.getObjects();
		voCategories = new ArrayList<VOCategory>();
		
		for (Category category : categories) {
			voCategories.add(BuildVOCategory(category));
		}
		
		return voCategories;
	}
	
	private VOCategory BuildVOCategory(Category category)
	{
		VOCategory voCategory = new VOCategory();
		voCategory.setId(category.getId());
		voCategory.setDescription(category.getDescription());
		voCategory.setAmount(category.getAmount());
		voCategory.setCreateDateTimeUTC(category.getCreateDateTimeUTC());
		voCategory.setProjectId(category.getProjectId());
		voCategory.setDistributionType(category.getDistributionType());
		
		return voCategory;
	}

}
