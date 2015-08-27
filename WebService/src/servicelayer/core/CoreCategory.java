package servicelayer.core;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.Category;
import servicelayer.entity.businessEntity.CategoryType;
import servicelayer.entity.valueObject.VOCategory;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreCategory;

public class CoreCategory implements ICoreCategory {

	private static CoreCategory instance = null;

	private CoreCategory() {
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

		DAOManager daoManager = new DAOManager();
		try {
			Category category = new Category(voCategory);
			if (category.getCategoryType() == CategoryType.COMPANY) {
				if (daoManager
						.getDAOCategories()
						.getCategories(category.getDescription(),
								CategoryType.COMPANY).size() > 0)
					throw new ClientException(
							"Ya existe un rubro con esta descripcion");
			}

			if (category.getCategoryType() == CategoryType.PROJECT
					&& !category.getIsRRHH()) {
				ArrayList<Category> categoriesByDescription = daoManager
						.getDAOCategories().getCategories(
								category.getDescription(),
								category.getProject().getId());
				if (categoriesByDescription.size() > 0)
					throw new ClientException(
							"Ya existe un rubro con esta descripcion");
			}

			if (category.getCategoryType() == CategoryType.PROJECT
					&& category.getIsRRHH()) {
				ArrayList<Category> categoriesByDescription = daoManager
						.getDAOCategories().getCategories(
								category.getDescription(),
								category.getProject().getId());

				if (categoriesByDescription.size() > 0)
					throw new ClientException(
							"Ese rubro ya esta asosicado al proyecto seleccionado");
			}

			if (category.getIsCurrencyDollar()) {
				category.setAmountPeso(category.getAmountDollar()
						* category.getTypeExchange());
			}

			daoManager.getDAOCategories().insert(category);
			daoManager.commit();

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public void deleteCateory(int id) throws ServerException, ClientException {
		DAOManager daoManager = new DAOManager();
		try {
			Category category = daoManager.getDAOCategories().getObject(id);
			if (category == null) {
				throw new ClientException("No existe rubro con ese id");
			} else {
				daoManager.getDAOCategories().delete(id);
				daoManager.commit();
			}

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public VOCategory updateCategory(int id, VOCategory voCategory)
			throws ServerException, ClientException {

		DAOManager daoManager = new DAOManager();
		try {
			Category categoryUpdate = new Category(voCategory);
			Category categoryOld = daoManager.getDAOCategories().getObject(id);

			if (!categoryOld.getCategoryType().equals(
					categoryUpdate.getCategoryType())
					|| categoryUpdate.getIsRRHH() != categoryOld.getIsRRHH()
					|| (categoryUpdate.getProject() != null
							&& categoryOld.getProject() != null && categoryUpdate
							.getProject().getId() != categoryOld.getProject()
							.getId())) {
				if (categoryUpdate.getCategoryType() == CategoryType.COMPANY) {
					if (daoManager
							.getDAOCategories()
							.getCategoriesLastVersion(
									categoryOld.getDescription(),
									CategoryType.COMPANY).size() > 0
							&& !categoryUpdate.getCategoryType().equals(
									categoryOld.getCategoryType()))
						throw new ClientException(
								"Ya existe un rubro con esta descripcion");
				}

				if (categoryUpdate.getCategoryType() == CategoryType.PROJECT
						&& categoryOld.getProject() != null
						&& !categoryUpdate.getIsRRHH()
						&& categoryOld.getProject().getId() != categoryUpdate
								.getProject().getId()) {
					ArrayList<Category> categoriesByDescription = daoManager
							.getDAOCategories().getCategoriesLastVersion(
									categoryOld.getDescription(),
									categoryUpdate.getProject().getId());
					if (categoriesByDescription.size() > 0)
						throw new ClientException(
								"Ya existe un rubro con esta descripcion");
				}

				if (categoryUpdate.getCategoryType() == CategoryType.PROJECT
						&& categoryOld.getProject() != null
						&& categoryUpdate.getIsRRHH()
						&& categoryOld.getProject().getId() != categoryUpdate
								.getProject().getId()) {
					ArrayList<Category> categoriesByDescription = daoManager
							.getDAOCategories().getCategoriesLastVersion(
									categoryOld.getDescription(),
									categoryUpdate.getProject().getId());
					if (categoriesByDescription.size() > 0)
						throw new ClientException(
								"Ese rubro ya esta asosicado al proyecto seleccionado");
				}
			}

			if (changeCategory(categoryOld, categoryUpdate)) {
				categoryUpdate.setDescription(categoryOld.getDescription());
				categoryUpdate.setId(categoryOld.getId());
				categoryUpdate.setVersion(categoryOld.getVersion());
				if (categoryUpdate.getIsCurrencyDollar()) {
					categoryUpdate.setAmountPeso(categoryUpdate
							.getAmountDollar()
							* categoryUpdate.getTypeExchange());
				} else {
					categoryUpdate.setAmountDollar(0.00);
				}

				// si se modifica en el mismo dia, actualizo el registro
				// si no inserto una nueva version
				if (categoryOld.getUpdatedDateTimeUTC() != null
						&& DateFormat
								.getDateInstance()
								.format(categoryOld.getUpdatedDateTimeUTC())
								.equals(DateFormat.getDateInstance().format(
										new Date()))) {
					daoManager.getDAOCategories().update(0, categoryUpdate);
				} else {
					daoManager.getDAOCategories().update(1, categoryUpdate);
				}
			}

			daoManager.commit();

			return getCategory(id);

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public VOCategory getCategory(int id) throws ServerException,
			ClientException {
		Category category;
		VOCategory voCategory = null;

		DAOManager daoManager = new DAOManager();
		try {
			category = daoManager.getDAOCategories().getObject(id);
			if (category != null) {
				voCategory = BuildVOCategory(category);
			} else {
				throw new ClientException("No existe ning�n rubro con ese id");
			}

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return voCategory;
	}

	@Override
	public ArrayList<VOCategory> getCategories() throws ServerException {
		ArrayList<Category> categories;
		ArrayList<VOCategory> voCategories = null;

		DAOManager daoManager = new DAOManager();
		try {
			categories = daoManager.getDAOCategories().getObjects();
			voCategories = new ArrayList<VOCategory>();

			for (Category category : categories) {
				voCategories.add(BuildVOCategory(category));
			}

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return voCategories;
	}

	private VOCategory BuildVOCategory(Category category) {
		VOCategory voCategory = new VOCategory();
		voCategory.setId(category.getId());
		voCategory.setDescription(category.getDescription());
		voCategory.setAmountPeso(category.getAmountPeso());
		voCategory.setAmountDollar(category.getAmountDollar());
		voCategory.setIsCurrencyDollar(category.getIsCurrencyDollar());
		voCategory.setTypeExchange(category.getTypeExchange());
		voCategory.setAppliedDateTimeUTC(category.getAppliedDateTimeUTC());
		if (category.getProject() != null) {
			voCategory.setProjectId(category.getProject().getId());
			voCategory.setProjectName(category.getProject().getName());
		}
		voCategory.setCategoryType(category.getCategoryType().getValue());
		voCategory.setIsRRHH(category.getIsRRHH());

		return voCategory;
	}

	private boolean changeCategory(Category toUpdate, Category oldCategory) {
		boolean change = false;

		if (toUpdate.getAmountPeso() != oldCategory.getAmountPeso()) {
			change = true;
		}

		if (toUpdate.getIsCurrencyDollar() != oldCategory.getIsCurrencyDollar()) {
			change = true;
		}

		if (toUpdate.getTypeExchange() != oldCategory.getTypeExchange()) {
			change = true;
		}

		if (toUpdate.getIsRRHH() != oldCategory.getIsRRHH()) {
			change = true;
		}

		if (toUpdate.getCategoryType() != oldCategory.getCategoryType()) {
			change = true;
		}

		if (toUpdate.getProject() != null && oldCategory.getProject() != null) {
			if (toUpdate.getProject().getId() != oldCategory.getProject()
					.getId()) {
				change = true;
			}
		} else if ((toUpdate.getProject() == null && oldCategory.getProject() != null)
				|| (toUpdate.getProject() != null && oldCategory.getProject() == null)) {
			change = true;
		}

		if (!DateFormat
				.getDateInstance()
				.format(toUpdate.getAppliedDateTimeUTC())
				.equals(DateFormat.getDateInstance().format(
						oldCategory.getAppliedDateTimeUTC()))) {
			change = true;
		}

		return change;
	}

}
