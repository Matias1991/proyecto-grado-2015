package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.DeleteCategory;
import servicelayer.service.ServiceWebStub.GetCategories;
import servicelayer.service.ServiceWebStub.InsertCategory;
import servicelayer.service.ServiceWebStub.UpdateCategory;
import servicelayer.service.ServiceWebStub.VOCategory;
import utils.PopupWindow;
import entities.Category;

public class CategoryController {

	public static boolean createCategory(Category category)
	{
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
			voCategory.setCategoryType(category.getCategoryTypeId());
			voCategory.setProjectId(category.getProjectId());
			voCategory.setIsRRHH(category.getIsRRHH());
			voCategory.setAppliedDateTimeUTC(category.getCreatedDateTimeUTC());			
			
			createCategory.setVoCategory(voCategory);
			
			result = service.insertCategory(createCategory).get_return();
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Collection<Category> getCategories()
	{
		Collection<Category> categories = new ArrayList<Category>();
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			GetCategories getCategory = new GetCategories();
			
			VOCategory [] voCategories = service.getCategories(getCategory).get_return();

			if(voCategories != null)
			{
				for(VOCategory voCategory : voCategories)
				{
					Category category = new Category(voCategory);
					categories.add(category);
				}
			}
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return categories;
	}
	
	public static boolean deleteCategory(int id)
	{
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			DeleteCategory deleteCategory = new DeleteCategory();
			
			deleteCategory.setId(id);
			
			result = service.deleteCategory(deleteCategory).get_return();
			
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static Category modifyCategory(Category cat, int idCategory)
	{
		Category category = null;
		VOCategory result = new VOCategory();
		result.setDescription(cat.getDescription());
		result.setIsCurrencyDollar(cat.isCurrencyDollar());
		result.setAmountDollar(cat.getAmountDollar());
		result.setAmountPeso(cat.getAmountPeso());
		result.setTypeExchange(cat.getTypeExchange());
		
		if(cat.getCategoryTypeId() == 1){
			result.setProjectId(0);
		}else{
			result.setProjectId(cat.getProjectId());
		}
		result.setCategoryType(cat.getCategoryTypeId());
		result.setProjectId(cat.getProjectId());
		result.setIsRRHH(cat.getIsRRHH());
		result.setAppliedDateTimeUTC(cat.getCreatedDateTimeUTC());
		
		try {
			ServiceWebStub service = new ServiceWebStub();
			UpdateCategory modifyCategory = new UpdateCategory();
			
			modifyCategory.setCategory(result);
			modifyCategory.setId(idCategory);
			result = service.updateCategory(modifyCategory).get_return();
			category = new Category(result);
		} catch (AxisFault e) {
			String error = e.getMessage().replace("<faultstring>", "");
			PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		
		return category;
	}
}