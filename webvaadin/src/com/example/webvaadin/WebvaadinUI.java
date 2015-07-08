package com.example.webvaadin;

import views.CreateUserView;
import views.LoginView;
import views.MainMenuView;
import views.ModifyProfileView;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.client.ui.Icon;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("webvaadin")
public class WebvaadinUI extends UI {
	
	public Navigator navigator;
	//public static final String SEARCHUSERSDESCRIPTION = "SearchUserDescription";
	public static final String MAINMENU = "MainMenu";
	public static final String CREATEUSER = "CreateUserView";
	public static final String MODIFYPROFILEUSER = "ModifyProfileView";
	public static final String DELETEUSERS = "DeleteUsersView";
	private static GridLayout mainLayout;
	private static MenuBar mainMenuBar; 
	private static MenuBar userMenuBar; 
	private static Label lblTitle; 
	
	
	@Override
	protected void init(VaadinRequest request) {
		
		buildLayout();				
	
	}
	
	
	/**
	 * Armado del contenedor principal
	 */
	private GridLayout buildLayout(){
		mainLayout = new GridLayout(12,12);
		
		mainLayout.setMargin(true);
		mainLayout.setWidth("1366px");
		mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		setContent(mainLayout);		
		getPage().setTitle(":: Meerkat SYS - MSMP");		
		
		/**
		 * Logo del sitio
		 */
		Image logo = new Image(null, new ThemeResource("./images/logo.png"));
		logo.setWidth(100, Unit.PERCENTAGE);
		logo.setHeight("35px");
		mainLayout.addComponent(logo,0,0,1,0);
		
		/**
		 * Titulo Bienvenida
		 */
		lblTitle = new Label("Bienvenido al Sistema de Gesti�n y Liquidaciones de Proyectos");
		lblTitle.setWidth(80, Unit.PERCENTAGE);
		lblTitle.setStyleName("titleLabel");
		changeToLogin();
		
		/**
		 * Barra del men� principal
		 */		
		mainMenuBar = new MenuBar();
		mainMenuBar.setWidth(100, Unit.PERCENTAGE);
		loadMenuItems(mainMenuBar);
		
		/**
		 * Barra del usuario logueado
		 */	
		userMenuBar = new MenuBar();
		//userMenuBar.setWidth(100,Unit.PERCENTAGE);
		loadUserMenuItems(userMenuBar);
				
		/**
		 * Contenedor del navigator	
		 */
		VerticalLayout layoutViews = new VerticalLayout();
		layoutViews.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(layoutViews);
		mainLayout.addComponent(layoutViews,2,2, 11,2);
		
		navigator = new Navigator(UI.getCurrent(), viewDisplay);
		navigator.addView("", new LoginView());		
		navigator.addView(MAINMENU, new MainMenuView());
		navigator.addView(CREATEUSER, new CreateUserView());	
		navigator.addView(MODIFYPROFILEUSER, new ModifyProfileView());
		navigator.addView(DELETEUSERS, new DeleteUsersView());	
		
		return mainLayout;		
	}
	
	
	
	/**
	 * Carga del menu principal segun el rol del sistema
	 */
	private void loadMenuItems(MenuBar menuBar){
		
		/**
		 * Acciones al seleccionar un item en el menu
		 */
		MenuBar.Command mainMenuBarCommand = new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				// TODO Auto-generated method stub
				switch(selectedItem.getText()){
					case "Crear usuario":						
						getUI().getNavigator().navigateTo(WebvaadinUI.CREATEUSER);
					break;
					case "Modificar usuario":
						getUI().getNavigator().navigateTo(WebvaadinUI.MODIFYPROFILEUSER);
					break;
					case "Eliminar usuario":
//						getUI().getNavigator().navigateTo(WebvaadinUI.DELETEUSERS);
					break;
					case "Cat�logo usuarios":
						Notification.show(selectedItem.getText());
					break;	
					default:
						Notification.show("No hay configuracion para el item: " + selectedItem.getText());
					break;
				}					
			}
		};
		
		/**
		 * Carga del menu principal rol Administrador
		 */
		MenuItem users = menuBar.addItem("Usuarios",null,null);
		MenuItem createUser = users.addItem("Crear usuario",null,mainMenuBarCommand);
		MenuItem updateUser = users.addItem("Modificar usuario",null,mainMenuBarCommand);
		MenuItem deleteUser = users.addItem("Eliminar usuario", null,mainMenuBarCommand);
		MenuItem getUsers = users.addItem("Cat�logo usuarios", null,mainMenuBarCommand);
		
		/**
		 * Carga del menu principal rol Socio
		 */
		MenuItem employeed = menuBar.addItem("Empleados", null,null);
		MenuItem createEmployeed = employeed.addItem("Crear empleado",null,mainMenuBarCommand);
		MenuItem updateEmployeed = employeed.addItem("Modificar empleado",null,mainMenuBarCommand);
			
	}
	
	/**
	 * Carga de los items del MenuBar de usuario
	 */
	private void loadUserMenuItems(MenuBar menuBar){
		
		/**
		 * Acciones al seleccionar un item en el menu
		 */
		MenuBar.Command userMenuBarCommand = new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				// TODO Auto-generated method stub
				switch(selectedItem.getText()){
					case "Salir":
						getUI().getNavigator().navigateTo("");
					break;
					case "Perfil":
						Notification.show(selectedItem.getText());
					break;
					case "Cambiar contrase�a":
						Notification.show(selectedItem.getText());
					break;
					default:
						Notification.show("No hay configuracion para el item: " + selectedItem.getText());
					break;
				}
			}
		};
		
		MenuItem userItem = userMenuBar.addItem("", new ThemeResource("./images/userIcon.png"),null);
		MenuItem logout = userItem.addItem("Salir", null,userMenuBarCommand);
		MenuItem profile = userItem.addItem("Perfil",null,userMenuBarCommand);
		MenuItem changePassword = userItem.addItem("Cambiar contrase�a", null,userMenuBarCommand);	
	
	}
	
		
	/**
	 * Cambia del login para el men� principal
	 */
	public static void changeToMainMenu(){
		mainLayout.removeComponent(2, 0);
		mainLayout.addComponent(mainMenuBar, 2, 0, 9, 0);
		mainLayout.addComponent(userMenuBar,10,0,11,0);
	}
	
	/**
	 * Cambia del menu princial para el login
	 */
	public static void changeToLogin(){
		mainLayout.removeComponent(2, 0);
		mainLayout.removeComponent(10,0);
		mainLayout.addComponent(lblTitle, 2, 0, 11, 0);		
	}
	
	
}