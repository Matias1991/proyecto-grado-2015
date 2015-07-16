package com.example.webvaadin;

import views.CatalogUsersView;
import views.ChangePasswordView;
import views.CreateUserView;
import views.DeleteUsersView;
import views.ForgotPasswordView;
import views.LoginView;
import views.MainMenuView;
import views.ModifyProfileView;
import views.ModifyUserView;
import views.ResetPasswordView;
import views.UnlockUserView;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import entities.RequestContext;
import entities.UserData;

@SuppressWarnings("serial")
@Theme("webvaadin")
@PreserveOnRefresh
public class WebvaadinUI extends UI {
	
	public static Navigator navigator;
	public static final String MAINMENU = "";
	public static final String LOGINVIEW = "LoginView";
	public static final String CREATEUSER = "CreateUserView";
	public static final String MODIFYPROFILEUSER = "ModifyProfileView";
	public static final String DELETEUSERS = "DeleteUsersView";
	public static final String MODIFYUSER = "ModifyUserView";
	public static final String CATALOGUSERS = "CatalogUsersView";
	public static final String FORGOTPASSWORD = "ForgotPaswordView";
	public static final String CHANGEPASSWORD = "ChangePasswordView";
	public static final String RESETPASSWORD = "ResetPaswordView";
	public static final String UNLOCKUSER = "UnlockUserView";
	
	private static GridLayout mainLayout;
	private static MenuBar mainMenuBar;	
	private static MenuBar userMenuBar; 
	private static Label lblTitle; 
	private static Label lblTitle2;
	
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
		lblTitle = new Label("Bienvenido al Sistema de Gestion y Liquidaciones de Proyectos");
		lblTitle.setWidth(80, Unit.PERCENTAGE);
		lblTitle.setStyleName("titleLabel");
		lblTitle2 = new Label("");
		
		/**
		 * Barra del menu principal
		 */		
		mainMenuBar = new MenuBar();
		mainMenuBar.setWidth(100, Unit.PERCENTAGE);
		//loadMenuItems(mainMenuBar);
		
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
		
		navigator = new Navigator(this, viewDisplay);
		
		navigator.addView("", new MainMenuView());
		navigator.addView(LOGINVIEW, new LoginView());		
		navigator.addView(CREATEUSER, new CreateUserView());
		navigator.addView(DELETEUSERS, new DeleteUsersView());
		navigator.addView(MODIFYPROFILEUSER, new ModifyProfileView());
		navigator.addView(MODIFYUSER, new ModifyUserView());
		navigator.addView(CATALOGUSERS, new CatalogUsersView());
		navigator.addView(FORGOTPASSWORD, new ForgotPasswordView());
		navigator.addView(CHANGEPASSWORD, new ChangePasswordView());
		navigator.addView(RESETPASSWORD, new ResetPasswordView());
		navigator.addView(UNLOCKUSER,new UnlockUserView());
		
		if(RequestContext.getRequestContext() == null)
		{
			changeToLogin();
			getUI().getNavigator().navigateTo(LOGINVIEW);
		}
		else
		{
			loadMenuItems(mainMenuBar);
			changeToMainMenu();			
			getUI().getNavigator().navigateTo("");
		}
		
		return mainLayout;		
	}
	
	
	
	/**
	 * Carga del menu principal segun el rol del sistema
	 */
	private static void loadMenuItems(MenuBar menuBar){
		
		/**
		 * Acciones al seleccionar un item en el menu
		 */
		MenuBar.Command mainMenuBarCommand = new MenuBar.Command() {
			
			@Override
			public void menuSelected(MenuItem selectedItem) {
				// TODO Auto-generated method stub
				switch(selectedItem.getText()){
					case "Crear usuario":	
						navigator.navigateTo(CREATEUSER);
						break;
					case "Modificar usuario":
						navigator.navigateTo(MODIFYUSER);
						break;
					case "Eliminar usuario":
						navigator.navigateTo(DELETEUSERS);
						break;
					case "Catalogo usuarios":
						navigator.navigateTo(CATALOGUSERS);
						break;	
					case "Resetear contraseña":
						navigator.navigateTo(RESETPASSWORD);
						break;
					case "Desbloquear usuario":
						navigator.navigateTo(UNLOCKUSER);
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
		menuBar.removeItems();
		
		switch(RequestContext.getRequestContext().getUserType()){
			case 1:
				//Rol Administrador
				MenuItem users = menuBar.addItem("Usuarios",null,null);
				MenuItem createUser = users.addItem("Crear usuario",null,mainMenuBarCommand);
				MenuItem updateUser = users.addItem("Modificar usuario",null,mainMenuBarCommand);
				MenuItem deleteUser = users.addItem("Eliminar usuario", null,mainMenuBarCommand);
				MenuItem getUsers = users.addItem("Catalogo usuarios", null,mainMenuBarCommand);
				MenuItem resetUserPassword = users.addItem("Resetear contraseña",null,mainMenuBarCommand);
				MenuItem unlockUserAccess = users.addItem("Desbloquear usuario", null,mainMenuBarCommand);
				break;
			case 2:
				//Rol Socio
				MenuItem employeed = menuBar.addItem("Empleados", null,null);
				MenuItem createEmployeed = employeed.addItem("Crear empleado",null,mainMenuBarCommand);
				MenuItem updateEmployeed = employeed.addItem("Modificar empleado",null,mainMenuBarCommand);
				break;
			case 3:
				//Rol Gerente
				break;
			default:
				break;				
		}	
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
						RequestContext.setRequestContext(null);
						getSession().close();
						getUI().getNavigator().navigateTo("LoginView");
					break;
					case "Perfil":
						getUI().getNavigator().navigateTo(WebvaadinUI.MODIFYPROFILEUSER);						
					break;
					case "Cambiar contrasenia":
						getUI().getNavigator().navigateTo(WebvaadinUI.CHANGEPASSWORD);
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
		MenuItem changePassword = userItem.addItem("Cambiar contrasenia", null,userMenuBarCommand);	
	
	}
	
		
	/**
	 * Cambia del login para el menu principal.
	 * Remueve el label y/o los dos menuBar
	 * Agrega dos menuBar
	 */
	public static void changeToMainMenu(){
		mainLayout.removeComponent(2, 0);
		mainLayout.removeComponent(10,0);
		mainLayout.addComponent(mainMenuBar, 2, 0, 9, 0);
		mainLayout.addComponent(userMenuBar,10,0,11,0);	
		loadMenuItems(mainMenuBar);
		
	}
	
	/**
	 * Cambia del menu princial para el login
	 * Remueve los dos menuBar
	 * Agrega el label del titulo
	 */
	public static void changeToLogin(){
		mainLayout.removeComponent(2, 0);
		mainLayout.removeComponent(10,0);		
		mainLayout.addComponent(lblTitle, 2, 0, 9, 0);	
		mainLayout.addComponent(lblTitle2, 10,0,11,0);
	}
	
	
}