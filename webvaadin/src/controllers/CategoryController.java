package controllers;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.axis2.AxisFault;

import entities.Category;
import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.GetCategories;
import servicelayer.service.ServiceWebStub.InsertCategory;
import servicelayer.service.ServiceWebStub.DeleteCategory;
import servicelayer.service.ServiceWebStub.VOCategory;
import utils.PopupWindow;

public class CategoryController {

	public static boolean createCategory(Category category)
	{
		boolean result = false;
		try {
			ServiceWebStub service = new ServiceWebStub();
			InsertCategory createCategory = new InsertCategory();
			
			VOCategory voCategory = new VOCategory();
			voCategory.setDescription(category.getDescription());
			voCategory.setAmount(category.getAmount());
			
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
	
	public static boolean DeleteCategory(int id)
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
	
}