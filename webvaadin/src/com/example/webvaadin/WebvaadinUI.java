package com.example.webvaadin;

import views.CreateUserView;
import views.LoginView;
import views.SearchUserDescription;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("webvaadin")
public class WebvaadinUI extends UI {
	
	public Navigator navigator;
//	public static final String SEARCHUSERSDESCRIPTION = "SearchUserDescription";
	public static final String CREATE_USER = "CreateUserView";
	
		
	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setSizeFull();
		layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		setContent(layout);
		
							
		ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(layout);
		navigator = new Navigator(UI.getCurrent(),viewDisplay);
		navigator.addView("", new CreateUserView());	
		navigator.addView(CREATE_USER, new CreateUserView());
		
		
	}
}