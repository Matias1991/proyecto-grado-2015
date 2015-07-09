package views;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;

import entities.RequestContext;

public class BaseView extends CustomComponent implements View {

	private static final long serialVersionUID = 1L;

	@Override
	public void enter(ViewChangeEvent event) {
		if(RequestContext.getRequestContext() == null)
		{
			WebvaadinUI.changeToLogin();
			getUI().getNavigator().navigateTo(WebvaadinUI.LOGINVIEW);
		}
		else
		{
			WebvaadinUI.changeToMainMenu();
		}
	}
}
