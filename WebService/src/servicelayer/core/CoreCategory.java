package servicelayer.core;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import datalayer.daos.DAOCategories;
import servicelayer.entity.businessEntity.Category;
import servicelayer.entity.businessEntity.CategoryType;
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
	public void insertCategory(VOCategory voCategory) throws ServerException, ClientException {

		Category category = new Category(voCategory);
		if(category.getCategoryType() == CategoryType.COMPANY)
		{	
			if(iDAOCategory.getCategories(category.getDescription(), CategoryType.COMPANY).size() > 0)
				throw new ClientException("Ya existe un rubro con esta descripcion");
		}
		
		if(category.getCategoryType() == CategoryType.PROJECT && !category.getIsRRHH())
		{
			ArrayList<Category> categoriesByDescription = iDAOCategory.getCategories(category.getDescription(), category.getProject().getId());
			if(categoriesByDescription.size() > 0)
				throw new ClientException("Ya existe un rubro con esta descripcion");
		}
		
		if(category.getCategoryType() == CategoryType.PROJECT && category.getIsRRHH())
		{
			ArrayList<Category> categoriesByDescription = iDAOCategory.getCategories(category.getDescription(), category.getProject().getId());
			
			if(categoriesByDescription.size() > 0)
					throw new ClientException("Ese rubro ya esta asosicado al proyecto seleccionado");
		}
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
		Category categoryUpdate = new Category(voCategory);
		Category categoryOld = iDAOCategory.getObject(id);
		// Si el rubro era de tipo RRHH y lo cambio a tipo Material, 
		// valido que no exista uno con el mismo nombre
		int categoriesEquals = iDAOCategory.getCategories(categoryOld.getDescription(), CategoryType.COMPANY).size();
		if(categoryOld.getIsRRHH() && !categoryUpdate.getIsRRHH() && categoriesEquals > 1){
			throw new ClientException("Ya existe un rubro con esta descripcion");
		}
		// Si es de tipo recurso humano y ascociado a un proyecto
		// verifico que ya no esté asociado
		if(categoryUpdate.getCategoryType() == CategoryType.PROJECT && categoryUpdate.getIsRRHH())
		{
			ArrayList<Category> categoriesByDescription = iDAOCategory.getCategories(categoryOld.getDescription(), categoryUpdate.getProject().getId());
			if(categoriesByDescription.size() > 0){
					throw new ClientException("Ese rubro ya esta asociado al proyecto seleccionado");
			}
		}
		
		if(changeCategory(categoryOld, categoryUpdate)){
			categoryUpdate.setDescription(categoryOld.getDescription());
			categoryUpdate.setId(categoryOld.getId());
			categoryUpdate.setVersion(categoryOld.getVersion());
			// si se modifica en el mismo dia, actualizo el registro
			// si no inserto una nueva version
			if(categoryOld.getModifyDateTimeUTC() != null && 
					DateFormat.getDateInstance().format(categoryOld.getModifyDateTimeUTC()).equals(DateFormat.getDateInstance().format(new Date()))){
				iDAOCategory.update(0, categoryUpdate);
			} else {
				iDAOCategory.update(1, categoryUpdate);	
			}
		}
		
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
		voCategory.setAppliedDateTimeUTC(category.getAppliedDateTimeUTC());
		if(category.getProject() != null)
		{
			voCategory.setProjectId(category.getProject().getId());
			voCategory.setProjectName(category.getProject().getName());
		}
		voCategory.setCategoryType(category.getCategoryType().getValue());
		voCategory.setIsRRHH(category.getIsRRHH());
		
		return voCategory;
	}
	
	private boolean changeCategory(Category toUpdate, Category oldCategory){
		boolean change = false;
		
		if(toUpdate.getAmount() != oldCategory.getAmount()){
			change = true;
		}
		
		if (toUpdate.getIsRRHH() != oldCategory.getIsRRHH()){
			change = true;
		}
		
		if (toUpdate.getCategoryType() != oldCategory.getCategoryType()){
			change = true;
		}
		
		if (toUpdate.getProject() != oldCategory.getProject()){
			change = true;
		}
		
		if (!DateFormat.getDateInstance().format(toUpdate.getAppliedDateTimeUTC()).equals(DateFormat.getDateInstance().format(oldCategory.getAppliedDateTimeUTC()))){
			change = true;
		}
		
		return change;
	}

}
