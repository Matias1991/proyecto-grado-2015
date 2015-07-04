package com.example.webvaadin;

import views.CreateUser;
import views.LoginView;
import views.MainMenu;
import views.SearchUserDescription;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("webvaadin")
public class WebvaadinUI extends UI {
	
	public Navigator navigator;
	//public static final String SEARCHUSERSDESCRIPTION = "SearchUserDescription";
	private static Label lblTitle = new Label("");
	public static final String MAINMENU = "MainMenu";
	public static final String CREATEUSER = "CreateUser";
	
	public static void setTitle(String newTitle){
		lblTitle.setValue(newTitle);
	}
		
	@Override
	protected void init(VaadinRequest request) {
		final GridLayout layout = new GridLayout(12,12);
		layout.setMargin(true);
		layout.setSpacing(true);
		layout.setSizeFull();		
		setContent(layout);		
		getPage().setTitle(":: Meerkat SYS - MSMP");		
		
		/**
		 * Logo y titulo
		 */
		Image logo = new Image(null, new ThemeResource("./images/logoConFondo.png"));
		logo.setWidthUndefined();
		logo.setHeightUndefined();
		layout.addComponent(logo,0,0);
		
		this.setTitle("Sistema de Gestión y Liquidaciones de Proyectos");
		layout.addComponent(lblTitle, 2, 0, 8, 0);
		
		/**
		 * Contenedor principal		
		 */
		VerticalLayout layoutViews = new VerticalLayout();
		layoutViews.setDefaultComponentAlignment(Alignment.TOP_CENTER);
		ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(layoutViews);
		layout.addComponent(layoutViews,0,1, 11,1);
		//layout.setComponentAlignment(layoutViews, Alignment.TOP_CENTER);
		navigator = new Navigator(UI.getCurrent(), viewDisplay);
		navigator.addView("", new LoginView());
		//navigator.addView(SEARCHUSERSDESCRIPTION, new SearchUserDescription());
		navigator.addView(MAINMENU, new MainMenu());
		navigator.addView(CREATEUSER, new CreateUser());
		
		
	}
	
	
}