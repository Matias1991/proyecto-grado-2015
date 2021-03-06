package com.example.webvaadin;

import utils.PopupWindow;
import views.ForgotPasswordView;
import views.LoginView;
import views.MainMenuView;
import views.bill.CatalogBillsView;
import views.bill.CreateBillView;
import views.category.CatalogCategoriesView;
import views.category.CreateCategoryView;
import views.category.DeleteCategoriesView;
import views.category.ModifyCategoryView;
import views.employees.CatalogEmployeesView;
import views.employees.CreateEmployeeView;
import views.employees.DeleteEmployeeView;
import views.employees.UpdateEmployeeView;
import views.profile.ChangePasswordView;
import views.profile.ModifyProfileView;
import views.user.CatalogUsersView;
import views.user.CreateUserView;
import views.user.DeleteUsersView;
import views.user.ModifyUserView;
import views.user.ResetPasswordView;
import views.user.UnlockUserView;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.Navigator.ComponentContainerViewDisplay;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
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

@SuppressWarnings("serial")
@Theme("webvaadin")
@PreserveOnRefresh
public class WebvaadinUI extends UI {

	public static Navigator navigator;
	public static final String MAINMENU = "";
	public static final String LOGINVIEW = "LoginView";

	// Usuarios
	public static final String CREATEUSER = "CreateUserView";
	public static final String MODIFYPROFILEUSER = "ModifyProfileView";
	public static final String DELETEUSERS = "DeleteUsersView";
	public static final String MODIFYUSER = "ModifyUserView";
	public static final String CATALOGUSERS = "CatalogUsersView";
	public static final String FORGOTPASSWORD = "ForgotPaswordView";
	public static final String CHANGEPASSWORD = "ChangePasswordView";
	public static final String RESETPASSWORD = "ResetPaswordView";
	public static final String UNLOCKUSER = "UnlockUserView";

	// Empleados
	public static final String CATALOGEMPLOYEES = "CatalogEmployees";
	public static final String CREATEEMPLOYEE = "CreateEmployeeView";
	public static final String DELETEEMPLOYEE = "DeleteEmployeeView";
	public static final String UPDATEEMPLOYEE = "UpdateEmployeeView";

	// Rubros
	public static final String CREATECATEGORY = "CreateCategoryView";
	public static final String CATEGORIES = "CategoriesView";
	public static final String DELETECATEGORY = "DeleteCategoryView";
	public static final String MODIFYCATEGORY = "UpdateCategoryView";

	//Facturas
	public static final String CREATEBILL = "CreateBillView";
	public static final String BILLS = "CatalogBillsView";
	
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
	private GridLayout buildLayout() {
		mainLayout = new GridLayout(12, 12);

		mainLayout.setMargin(true);
		mainLayout.setWidth("1366px");
		mainLayout.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		setContent(mainLayout);
		getPage().setTitle(":: Meerkat SYS - MSMP");

		/**
		 * Logo del sitio
		 */
		Image logo = new Image(null, new ThemeResource("./images/logoV2.png"));
		logo.setWidth(100, Unit.PERCENTAGE);
		logo.setHeight("35px");
		mainLayout.addComponent(logo, 0, 0, 1, 0);

		/**
		 * Titulo Bienvenida
		 */
		lblTitle = new Label(
				"Bienvenido al Sistema de Gestion y Liquidaciones de Proyectos");
		lblTitle.setWidth(80, Unit.PERCENTAGE);
		lblTitle.setStyleName("titleLabelHome");
		lblTitle2 = new Label("");

		/**
		 * Barra del menu principal
		 */
		mainMenuBar = new MenuBar();
		mainMenuBar.setWidth(100, Unit.PERCENTAGE);
		
		/**
		 * Barra del usuario logueado
		 */
		userMenuBar = new MenuBar();
		loadUserMenuItems(userMenuBar);

		/**
		 * Contenedor del navigator
		 */
		VerticalLayout layoutViews = new VerticalLayout();
		layoutViews.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(
				layoutViews);
		mainLayout.addComponent(layoutViews, 2, 2, 11, 2);

		navigator = new Navigator(this, viewDisplay);
		
		navigator.setErrorView(new LoginView());

		navigator.addView("", new MainMenuView());		
		navigator.addView(LOGINVIEW, new LoginView());
		// Usuarios
		navigator.addView(CREATEUSER, new CreateUserView());
		navigator.addView(DELETEUSERS, new DeleteUsersView());
		navigator.addView(MODIFYPROFILEUSER, new ModifyProfileView());
		navigator.addView(MODIFYUSER, new ModifyUserView());
		navigator.addView(CATALOGUSERS, new CatalogUsersView());
		navigator.addView(FORGOTPASSWORD, new ForgotPasswordView());
		navigator.addView(CHANGEPASSWORD, new ChangePasswordView());
		navigator.addView(RESETPASSWORD, new ResetPasswordView());
		navigator.addView(UNLOCKUSER, new UnlockUserView());
		// Empleados
		navigator.addView(CATALOGEMPLOYEES, new CatalogEmployeesView());
		navigator.addView(CREATEEMPLOYEE, new CreateEmployeeView());
		navigator.addView(DELETEEMPLOYEE, new DeleteEmployeeView());
		navigator.addView(UPDATEEMPLOYEE, new UpdateEmployeeView());
		// Rubros
		navigator.addView(CREATECATEGORY, new CreateCategoryView());
		navigator.addView(CATEGORIES, new CatalogCategoriesView());
		navigator.addView(DELETECATEGORY, new DeleteCategoriesView());
		navigator.addView(MODIFYCATEGORY, new ModifyCategoryView());
		// Facturas
		navigator.addView(CREATEBILL, new CreateBillView());
		navigator.addView(BILLS, new CatalogBillsView());
		
		if (RequestContext.getRequestContext() == null) {
			changeToLogin();
			getUI().getNavigator().navigateTo(LOGINVIEW);
		} else {
			loadMenuItems(mainMenuBar);
			changeToMainMenu();
			getUI().getNavigator().navigateTo("");
		}

		return mainLayout;
	}

	/**
	 * Carga del menu principal segun el rol del sistema
	 */
	private static void loadMenuItems(MenuBar menuBar) {

		/**
		 * Acciones al seleccionar un item en el menu
		 */
		MenuBar.Command mainMenuBarCommand = new MenuBar.Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				// TODO Auto-generated method stub
				switch (selectedItem.getText()) {
				case "Crear usuario":
					navigator.navigateTo(CREATEUSER);
					break;
				case "Modificar usuarios":
					navigator.navigateTo(MODIFYUSER);
					break;
				case "Eliminar usuarios":
					navigator.navigateTo(DELETEUSERS);
					break;
				case "Cat�logo usuarios":
					navigator.navigateTo(CATALOGUSERS);
					break;
				case "Resetear contrase�a":
					navigator.navigateTo(RESETPASSWORD);
					break;
				case "Desbloquear usuarios":
					navigator.navigateTo(UNLOCKUSER);
					break;
				case "Cat�logo empleados":
					navigator.navigateTo(CATALOGEMPLOYEES);
					break;
				case "Crear empleado":
					navigator.navigateTo(CREATEEMPLOYEE);
					break;
				case "Eliminar empleados":
					navigator.navigateTo(DELETEEMPLOYEE);
					break;
				case "Modificar empleados":
					navigator.navigateTo(UPDATEEMPLOYEE);
					break;
				case "Crear rubro":
					navigator.navigateTo(CREATECATEGORY);
					break;
				case "Eliminar rubros":
					navigator.navigateTo(DELETECATEGORY);
					break;
				case "Modificar rubros":
					navigator.navigateTo(MODIFYCATEGORY);
					break;
				case "Cat�logo rubros":
					navigator.navigateTo(CATEGORIES);
					break;
				case "Crear factura":
					navigator.navigateTo(CREATEBILL);
					break;
				case "Cat�logo facturas":
					navigator.navigateTo(BILLS);
					break;
				default:
					new PopupWindow("AVISO",
							"No hay configuracion para el item: "
									+ selectedItem.getText());
					break;
				}
			}
		};

		/**
		 * Carga del menu principal rol Administrador
		 */
		menuBar.removeItems();

		switch (RequestContext.getRequestContext().getUserType()) {
		case 1:
			// Rol Administrador
			MenuItem users = menuBar.addItem("Usuarios", null, null);
			users.addItem("Crear usuario", null, mainMenuBarCommand);
			users.addItem("Modificar usuarios", null, mainMenuBarCommand);
			users.addItem("Eliminar usuarios", null, mainMenuBarCommand);
			users.addItem("Cat�logo usuarios", null, mainMenuBarCommand);
			users.addItem("Resetear contrase�a", null, mainMenuBarCommand);
			users.addItem("Desbloquear usuarios", null, mainMenuBarCommand);
			break;
		case 2:
			// Rol Socio
			MenuItem employeed = menuBar.addItem("Empleados", null, null);
			employeed.addItem("Crear empleado", null, mainMenuBarCommand);
			employeed.addItem("Modificar empleados", null, mainMenuBarCommand);
			employeed.addItem("Eliminar empleados", null,mainMenuBarCommand);
			employeed.addItem("Cat�logo empleados", null, mainMenuBarCommand);
			// Rubros
			MenuItem category = menuBar.addItem("Rubros", null, null);
			category.addItem("Crear rubro", null, mainMenuBarCommand);
			category.addItem("Modificar rubros", null, mainMenuBarCommand);
			category.addItem("Eliminar rubros", null, mainMenuBarCommand);
			category.addItem("Cat�logo rubros", null, mainMenuBarCommand);
			// Facturas
			MenuItem bill = menuBar.addItem("Facturas", null, null);
			bill.addItem("Crear factura", null, mainMenuBarCommand);
			bill.addItem("Cat�logo facturas", null, mainMenuBarCommand);
			break;
		case 3:
			// Rol Gerente
			break;
		default:
			break;
		}
	}

	/**
	 * Carga de los items del MenuBar de usuario
	 */
	private void loadUserMenuItems(MenuBar menuBar) {

		/**
		 * Acciones al seleccionar un item en el menu
		 */
		MenuBar.Command userMenuBarCommand = new MenuBar.Command() {

			@Override
			public void menuSelected(MenuItem selectedItem) {
				// TODO Auto-generated method stub
				switch (selectedItem.getText()) {
				case "Salir":
					RequestContext.setRequestContext(null);
					getSession().close();
					getUI().getNavigator().navigateTo("LoginView");
					break;
				case "Perfil":
					getUI().getNavigator().navigateTo(
							WebvaadinUI.MODIFYPROFILEUSER);
					break;
				case "Cambiar contrase�a":
					getUI().getNavigator().navigateTo(
							WebvaadinUI.CHANGEPASSWORD);
					break;
				default:					
					PopupWindow popup = new PopupWindow("AVISO",
							"No hay configuraci�n para el item: "
									+ selectedItem.getText());
					break;
				}
			}
		};

		MenuItem userItem = userMenuBar.addItem("", new ThemeResource(
				"./images/userIcon.png"), null);
		userItem.addItem("Salir", null, userMenuBarCommand);
		userItem.addItem("Perfil", null, userMenuBarCommand);
		userItem.addItem("Cambiar contrase�a", null,userMenuBarCommand);

	}

	/**
	 * Cambia del login para el menu principal. Remueve el label y/o los dos
	 * menuBar Agrega dos menuBar
	 */
	public static void changeToMainMenu() {
		if (mainLayout.getComponent(2, 0) != null) {
			mainLayout.removeComponent(2, 0);
		}
		if (mainLayout.getComponent(10, 0) != null) {
			mainLayout.removeComponent(10, 0);
		}
		mainLayout.addComponent(mainMenuBar, 2, 0, 9, 0);
		mainLayout.addComponent(userMenuBar, 10, 0, 11, 0);
		loadMenuItems(mainMenuBar);

	}

	/**
	 * Cambia del menu princial para el login Remueve los dos menuBar Agrega el
	 * label del titulo
	 */
	public static void changeToLogin() {
		if (mainLayout.getComponent(2, 0) != null) {
			mainLayout.removeComponent(2, 0);
		}
		if (mainLayout.getComponent(10, 0) != null) {
			mainLayout.removeComponent(10, 0);
		}
		mainLayout.addComponent(lblTitle, 2, 0, 9, 0);
		mainLayout.addComponent(lblTitle2, 10, 0, 11, 0);
	}

}