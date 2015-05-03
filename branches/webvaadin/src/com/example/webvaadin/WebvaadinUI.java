package com.example.webvaadin;

import views.LoginView;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("webvaadin")
public class WebvaadinUI extends UI {
	
	public Navigator navigator;
	public static final String CUSTOMUSERSDESCRIPTION = "customUsersDescription";
	
		
	@Override
	protected void init(VaadinRequest request) {
		final VerticalLayout layout = new VerticalLayout();
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setSizeFull();
		setContent(layout);
		ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(layout);
		navigator = new Navigator(UI.getCurrent(),viewDisplay);
		navigator.addView("", new LoginView());
		navigator.addView(CUSTOMUSERSDESCRIPTION, new customUserDescription());
		
		
		//layout.setSizeFull();
		
		/*
		
		// Create the content root layout for the UI
        VerticalLayout content = new VerticalLayout();
        setContent(content);

        // Needed because the composites are full size
        content.setSizeFull();

        custom myComposite = new custom();
        content.addComponent(myComposite);
        */
	}
}