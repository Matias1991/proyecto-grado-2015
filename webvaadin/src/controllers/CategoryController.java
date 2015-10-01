package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.DeleteCategory;
import servicelayer.service.ServiceWebStub.GetCategories;
import servicelayer.service.ServiceWebStub.GetCategoriesAllVersions;
import servicelayer.service.ServiceWebStub.GetCategoriesByDate;
import servicelayer.service.ServiceWebStub.GetCategoriesByDescriptionAndCurrency;
import servicelayer.service.ServiceWebStub.GetCategoriesByProject;
import servicelayer.service.ServiceWebStub.InsertCategory;
import servicelayer.service.ServiceWebStub.UpdateCategory;
import servicelayer.service.ServiceWebStub.VOCategory;
import utils.PopupWindow;
import entities.Category;
import entities.RequestContext;

public class CategoryController {

	public static boolean createCategory(Category category) {
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			InsertCategory createCategory = new InsertCategory();

			VOCategory voCategory = new VOCategory();
			voCategory.setDescription(category.getDescription());
			voCategory.setAmountPeso(category.getAmountPeso());
			voCategory.setAmountDollar(category.getAmountDollar());
			voCategory.setIsCurrencyDollar(category.isCurrencyDollar());
			voCategory.setTypeExchange(category.getTypeExchange());
			voCategory.setIvaTypeId(category.getIvaTypeId());
			voCategory.setCategoryType(category.getCategoryTypeId());
			voCategory.setProjectId(category.getProjectId());
			voCategory.setIsRRHH(category.getIsRRHH());
			voCategory.setAppliedDateTimeUTC(category.getAppliedDateTimeUTC());

			createCategory.setVoCategory(voCategory);

			result = service.insertCategory(createCategory).get_return();

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static Collection<Category> getCategories() {
		Collection<Category> categories = new ArrayList<Category>();

		try {
			ServiceWebStub service = new ServiceWebStub();
			GetCategories getCategory = new GetCategories();
						
			getCategory.setUserContextId(RequestContext.getRequestContext().getId());

			VOCategory[] voCategories = service.getCategories(getCategory)
					.get_return();

			if (voCategories != null) {
				for (VOCategory voCategory : voCategories) {
					Category category = new Category(voCategory);
					categories.add(category);
				}
			}

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return categories;
	}

	public static Collection<Category> getCategoriesByActiveProjects() {
		Collection<Category> categories = new ArrayList<Category>();

		try {
			ServiceWebStub service = new ServiceWebStub();
			GetCategories getCategory = new GetCategories();
			getCategory.setUserContextId(RequestContext.getRequestContext().getId());

			VOCategory[] voCategories = service.getCategories(getCategory)
					.get_return();

			if (voCategories != null) {
				for (VOCategory voCategory : voCategories) {
					if (voCategory.getProjectId() != 0) {
						if (voCategory.getProjectClosed())
							continue;
					}
					Category category = new Category(voCategory);
					categories.add(category);
				}
			}

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return categories;
	}

	public static Collection<Category> getCategoriesByDate(Date from, Date to) {
		Collection<Category> categories = new ArrayList<Category>();

		try {
			ServiceWebStub service = new ServiceWebStub();
			GetCategoriesByDate getCategory = new GetCategoriesByDate();

			getCategory.setFrom(from);
			getCategory.setTo(to);		
			getCategory.setUserContextId(RequestContext.getRequestContext().getId());

			VOCategory[] voCategories = service
					.getCategoriesByDate(getCategory).get_return();

			if (voCategories != null) {
				for (VOCategory voCategory : voCategories) {
					Category category = new Category(voCategory);
					categories.add(category);
				}
			}

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return categories;
	}
	
	public static Collection<Category> getCategoriesAllVersions(int id, Date date) {
		Collection<Category> categories = new ArrayList<Category>();

		try {
			ServiceWebStub service = new ServiceWebStub();
			GetCategoriesAllVersions getCategoriesAllVersions = new GetCategoriesAllVersions();

			getCategoriesAllVersions.setId(id);		
			getCategoriesAllVersions.setDate(date);
			
			VOCategory[] voCategories = service
					.getCategoriesAllVersions(getCategoriesAllVersions).get_return();

			if (voCategories != null) {
				for (VOCategory voCategory : voCategories) {
					Category category = new Category(voCategory);
					categories.add(category);
				}
			}

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return categories;
	}

	public static boolean deleteCategory(int id) {
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			DeleteCategory deleteCategory = new DeleteCategory();

			deleteCategory.setId(id);

			result = service.deleteCategory(deleteCategory).get_return();

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static Category modifyCategory(Category cat, int idCategory) {
		Category category = null;
		VOCategory result = new VOCategory();
		result.setDescription(cat.getDescription());
		result.setIsCurrencyDollar(cat.isCurrencyDollar());
		result.setAmountDollar(cat.getAmountDollar());
		result.setAmountPeso(cat.getAmountPeso());
		result.setTypeExchange(cat.getTypeExchange());
		result.setIvaTypeId(cat.getIvaTypeId());
		if (cat.getCategoryTypeId() == 1) {
			result.setProjectId(0);
		} else {
			result.setProjectId(cat.getProjectId());
		}
		result.setCategoryType(cat.getCategoryTypeId());
		result.setProjectId(cat.getProjectId());
		result.setIsRRHH(cat.getIsRRHH());
		result.setAppliedDateTimeUTC(cat.getAppliedDateTimeUTC());

		try {
			ServiceWebStub service = new ServiceWebStub();
			UpdateCategory modifyCategory = new UpdateCategory();

			modifyCategory.setVoCategory(result);
			modifyCategory.setId(idCategory);
			result = service.updateCategory(modifyCategory).get_return();
			category = new Category(result);
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return category;
	}

	public static Collection<Category> getCategoriesByProject(int id) {
		Collection<Category> categories = new ArrayList<Category>();

		try {
			ServiceWebStub service = new ServiceWebStub();
			GetCategoriesByProject getCategory = new GetCategoriesByProject();
			getCategory.setId(id);

			VOCategory[] voCategories = service.getCategoriesByProject(
					getCategory).get_return();

			if (voCategories != null) {
				for (VOCategory voCategory : voCategories) {
					Category category = new Category(voCategory);
					categories.add(category);
				}
			}

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return categories;
	}
	
	public static Collection<Category> getCategories(String description, boolean isCurrencyDollar) {
		Collection<Category> categories = new ArrayList<Category>();

		try {
			ServiceWebStub service = new ServiceWebStub();
			GetCategoriesByDescriptionAndCurrency getCategoriesByDescriptionAndCurrency = new GetCategoriesByDescriptionAndCurrency();
			
			getCategoriesByDescriptionAndCurrency.setDescription(description);
			getCategoriesByDescriptionAndCurrency.setIsCurrencyDollar(isCurrencyDollar);
			
			VOCategory[] voCategories = service.getCategoriesByDescriptionAndCurrency(
					getCategoriesByDescriptionAndCurrency).get_return();

			if (voCategories != null) {
				for (VOCategory voCategory : voCategories) {
					Category category = new Category(voCategory);
					categories.add(category);
				}
			}

		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		return categories;
	}

}