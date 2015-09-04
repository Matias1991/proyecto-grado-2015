package views;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Link;
import com.vaadin.ui.UI;

import entities.RequestContext;

public class BaseView extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;

	private String module;
	private String viewName;
	
	public BaseView()
	{
		
	}
	
	public BaseView(String module, String viewName)
	{
		this.module = module;
		this.viewName = viewName;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		if(RequestContext.getRequestContext() == null)
		{
			//WebvaadinUI.changeToLogin();
			((WebvaadinUI)UI.getCurrent()).changeToLogin();
			getUI().getNavigator().navigateTo(WebvaadinUI.LOGINVIEW);
		}
		else
		{
			//WebvaadinUI.changeToMainMenu();
			((WebvaadinUI)UI.getCurrent()).changeToMainMenu();
		}
	}

	public String getBreadCrumbToShow()
	{
		return module + " / " + viewName; 
	}
}
