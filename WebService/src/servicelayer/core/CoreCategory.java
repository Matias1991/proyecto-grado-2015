package servicelayer.core;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import datalayer.daos.DAOCategories;
import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.Category;
import servicelayer.entity.businessEntity.CategoryType;
import servicelayer.entity.businessEntity.IVA_Type;
import servicelayer.entity.businessEntity.User;
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
	public void insertCategory(Category category) throws ServerException,
			ClientException {

		if (category.getCategoryType() == CategoryType.COMPANY) {
			if (iDAOCategory.getCategories(category.getDescription(),
					CategoryType.COMPANY).size() > 0)
				throw new ClientException(
						"Ya existe un rubro con esta descripcion");
		}

		if (category.getCategoryType() == CategoryType.PROJECT
				&& !category.getIsRRHH()) {
			ArrayList<Category> categoriesByDescription = iDAOCategory
					.getCategories(category.getDescription(), category
							.getProject().getId());
			if (categoriesByDescription.size() > 0)
				throw new ClientException(
						"Ya existe un rubro con esta descripcion");
		}

		if (category.getCategoryType() == CategoryType.PROJECT
				&& category.getIsRRHH()) {
			ArrayList<Category> categoriesByDescription = iDAOCategory
					.getCategories(category.getDescription(), category
							.getProject().getId());

			if (categoriesByDescription.size() > 0)
				throw new ClientException(
						"Ese rubro ya esta asosicado al proyecto seleccionado");
		}

		if (category.getIsCurrencyDollar()) {
			category.setAmountPeso(category.getAmountDollar()
					* category.getTypeExchange());
		}

		iDAOCategory.insert(category);
	}

	@Override
	public void deleteCateory(int id) throws ServerException, ClientException {
		Category category = iDAOCategory.getObject(id);
		if (category == null) {
			throw new ClientException("No existe rubro con ese id");
		} else {
			iDAOCategory.delete(id);
		}

	}

	@Override
	public Category updateCategory(int id, Category category)
			throws ServerException, ClientException {
		Category categoryUpdate = category;
		Category categoryOld = iDAOCategory.getObject(id);

		if (!categoryOld.getCategoryType().equals(
				categoryUpdate.getCategoryType())
				|| categoryUpdate.getIsRRHH() != categoryOld.getIsRRHH()
				|| (categoryUpdate.getProject() != null
						&& categoryOld.getProject() != null && categoryUpdate
						.getProject().getId() != categoryOld.getProject()
						.getId())) {
			if (categoryUpdate.getCategoryType() == CategoryType.COMPANY) {
				if (iDAOCategory.getCategoriesLastVersion(
						categoryOld.getDescription(), CategoryType.COMPANY)
						.size() > 0
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
				ArrayList<Category> categoriesByDescription = iDAOCategory
						.getCategoriesLastVersion(categoryOld.getDescription(),
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
				ArrayList<Category> categoriesByDescription = iDAOCategory
						.getCategoriesLastVersion(categoryOld.getDescription(),
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
				categoryUpdate.setAmountPeso(categoryUpdate.getAmountDollar()
						* categoryUpdate.getTypeExchange());
			} else {
				categoryUpdate.setAmountDollar(0.00);
			}

			// si se modifica en el mismo dia, actualizo el registro
			// si no inserto una nueva version
			if (categoryOld.getUpdatedDateTimeUTC() != null
					&& DateFormat.getDateInstance().format(categoryOld.getUpdatedDateTimeUTC())
					.equals(DateFormat.getDateInstance().format(new Date()))) {
				iDAOCategory.update(0, categoryUpdate);
			} else {
				iDAOCategory.update(1, categoryUpdate);
			}
		}

		return getCategory(id);
	}

	@Override
	public Category getCategory(int id) throws ServerException, ClientException {
		Category category;

		DAOManager daoManager = new DAOManager();
		category = daoManager.getDAOCategories().getObject(id);
		if (category == null)
			throw new ClientException("No existe ningún rubro con ese id");
		else {
			if (category.getIsCurrencyDollar()) {
				category.setTotalAmountDollar(getTotalAmount(
						category.getAmountDollar(), category.getIvaType()));
			} else {
				category.setTotalAmountPeso(getTotalAmount(
						category.getAmountPeso(), category.getIvaType()));
			}
		}

		return category;
	}

	@Override
	public ArrayList<Category> getCategories(User userContext) throws ServerException {
		ArrayList<Category> categories;
		
		DAOManager daoManager = new DAOManager();
		try {
			categories = daoManager.getDAOCategories().getCategories(
					userContext);
			for (Category category : categories) {
				if (category.getIsCurrencyDollar()) {
					category.setTotalAmountDollar(getTotalAmount(
							category.getAmountDollar(), category.getIvaType()));
				} else {
					category.setTotalAmountPeso(getTotalAmount(
							category.getAmountPeso(), category.getIvaType()));
				}
			}
		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return categories;	
	}

	@Override
	public ArrayList<Category> getCategoriesByProject(int projectId)
			throws ServerException, ClientException {

		return iDAOCategory.getCategoriesByProject(projectId);
	}

	@Override
	public ArrayList<Category> getCategories(Date from, Date to, User userContext)
			throws ServerException {
		ArrayList<Category> categories;

		DAOManager daoManager = new DAOManager();
		try {
			categories = daoManager.getDAOCategories().getCategories(from, to, userContext);
			for (Category category : categories) {
				if (category.getIsCurrencyDollar()) {
					category.setTotalAmountDollar(getTotalAmount(
							category.getAmountDollar(), category.getIvaType()));
				} else {
					category.setTotalAmountPeso(getTotalAmount(
							category.getAmountPeso(), category.getIvaType()));
				}
			}
		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return categories;
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

		if (toUpdate.getIvaTypeId() != oldCategory.getIvaTypeId()) {
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

	double getTotalAmount(double amount, IVA_Type ivaType) {
		double totalAmount = amount;
		if (ivaType == IVA_Type.TEN || ivaType == IVA_Type.TWENTY_TWO) {
			totalAmount = amount * ivaType.getPercentage();
		}

		return totalAmount;
	}

//	@Override
//	public ArrayList<Category> getCategoriesByManager(Date from, Date to,
//			int managerId) throws ServerException {
//		ArrayList<Category> categories;
//
//		DAOManager daoManager = new DAOManager();
//		try {
//			categories = daoManager.getDAOCategories().getCategoriesByManager(
//					from, to, managerId);
//			for (Category category : categories) {
//				if (category.getIsCurrencyDollar()) {
//					category.setTotalAmountDollar(getTotalAmount(
//							category.getAmountDollar(), category.getIvaType()));
//				} else {
//					category.setTotalAmountPeso(getTotalAmount(
//							category.getAmountPeso(), category.getIvaType()));
//				}
//			}
//		} catch (ServerException e) {
//			throw e;
//		} finally {
//			daoManager.close();
//		}
//		return categories;
//	}

//	@Override
//	public ArrayList<Category> getCategoriesByManager(User userContext)
//			throws ServerException {
//		ArrayList<Category> categories;
//
//		DAOManager daoManager = new DAOManager();
//		try {
//			categories = daoManager.getDAOCategories().getCategories(
//					userContext);
//			for (Category category : categories) {
//				if (category.getIsCurrencyDollar()) {
//					category.setTotalAmountDollar(getTotalAmount(
//							category.getAmountDollar(), category.getIvaType()));
//				} else {
//					category.setTotalAmountPeso(getTotalAmount(
//							category.getAmountPeso(), category.getIvaType()));
//				}
//			}
//		} catch (ServerException e) {
//			throw e;
//		} finally {
//			daoManager.close();
//		}
//		return categories;
//	}

}
