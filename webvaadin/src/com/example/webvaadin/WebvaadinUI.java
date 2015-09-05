package com.example.webvaadin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import utils.PopupWindow;
import views.BaseView;
import views.ForgotPasswordView;
import views.LoginView;
import views.MainMenuView;
import views.bill.CatalogBillsView;
import views.bill.CreateBillView;
import views.bill.DeleteBillsView;
import views.bill.UpdateBillView;
import views.category.CatalogCategoriesView;
import views.category.CreateCategoryView;
import views.category.DeleteCategoriesView;
import views.category.ModifyCategoryView;
import views.charge.CatalogChargesView;
import views.charge.CreateChargeView;
import views.charge.DeleteChargesView;
import views.charge.UpdateChargeView;
import views.employees.CatalogEmployeesView;
import views.employees.CreateEmployeeView;
import views.employees.DeleteEmployeeView;
import views.employees.UpdateEmployeeView;
import views.profile.ChangePasswordView;
import views.profile.ModifyProfileView;
import views.project.CatalogProjectView;
import views.project.CreateProjectView;
import views.project.DeleteProjectView;
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
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import entities.Constant;
import entities.RequestContext;
import entities.Constant.View;

@SuppressWarnings("serial")
@Theme("webvaadin")
@PreserveOnRefresh
public class WebvaadinUI extends UI {

	public Navigator navigator;
	VerticalLayout layoutViews;	
	public GridLayout mainLayout;
	public MenuBar mainMenuBar;
	public MenuBar userMenuBar;
	public Label lblTitle;
	public Label lblTitle2;
	
	public static final String MAINMENU = "";
	public static final String LOGINVIEW = "LoginView";
	
	public static HashMap<Integer, Collection<String>> USERS_T0_VIEWS = new HashMap<Integer, Collection<String>>();

	@Override
	protected void init(VaadinRequest request) {
		layoutViews = new VerticalLayout();
		layoutViews.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
		ComponentContainerViewDisplay viewDisplay = new ComponentContainerViewDisplay(
				layoutViews);
		navigator = new Navigator(UI.getCurrent(), viewDisplay);
		
		buildUsersToViews();
		
		buildLayout();
	}

	public BaseView getContructorByView(String strView)
	{
		BaseView view = null;
		switch(strView)
		{
			case View.CREATEUSER:
				view = new CreateUserView();
				break;
			case View.UPDATEPROFILEUSER:
				view = new ModifyProfileView();
				break;
			case View.DELETEUSERS:
				view = new DeleteUsersView();
				break;
			case View.UPDATEUSER:
				view = new ModifyUserView();
				break;
			case View.CATALOGUSERS:
				view = new CatalogUsersView();
				break;
			case View.FORGOTPASSWORD:
				view = new ForgotPasswordView();
				break;
			case View.CHANGEPASSWORD:
				view = new ChangePasswordView();
				break;
			case View.RESETPASSWORD:
				view = new ResetPasswordView();
				break;
			case View.UNLOCKUSER:
				view = new UnlockUserView();
				break;
				
			case View.CATALOGEMPLOYEES:
				view = new CatalogEmployeesView();
				break;
			case View.CREATEEMPLOYEE:
				view = new CreateEmployeeView();
				break;
			case View.DELETEEMPLOYEE:
				view = new DeleteEmployeeView();
				break;
			case View.UPDATEEMPLOYEE:
				view = new UpdateEmployeeView();
				break;
				
			case View.CREATECATEGORY:
				view = new CreateCategoryView();
				break;
			case View.CATEGORIES:
				view = new CatalogCategoriesView();
				break;
			case View.DELETECATEGORY:
				view = new DeleteCategoriesView();
				break;
			case View.UPDATECATEGORY:
				view = new DeleteCategoriesView();
				break;
				
			case View.CREATEBILL:
				view = new CreateBillView();
				break;
			case View.BILLS:
				view = new CatalogBillsView();
				break;
			case View.UPDATEBILL:
				view = new UpdateBillView();
				break;
			case View.DELETEBILLS:
				view = new DeleteBillsView();
				break;
				
			case View.CREATECHARGE:
				view = new CreateChargeView();
				break;
			case View.CHARGES:
				view = new CatalogChargesView();
				break;
			case View.UPDATECHARGE:
				view = new UpdateChargeView();
				break;
			case View.DELETECHARGES:
				view = new DeleteChargesView();
				break;
				
			case View.CREATEPROJECT:
				view = new CreateProjectView();
				break;
			case View.CLOSEPROJECT:
				view = new DeleteProjectView();
				break;
			case View.CATALOGPROJECTS:
				view = new CatalogProjectView();
				break;
		}
		
		return view;
	}
	
	void buildUsersToViews()
	{
		ArrayList<String> userTypeAdminToViews = new ArrayList<String>();
		ArrayList<String> userTypePartnerToViews = new ArrayList<String>();
		ArrayList<String> userTypeManagerToViews = new ArrayList<String>();
		
		//Carga las vistas a usuario Administrador
		userTypeAdminToViews.add(Constant.View.CREATEUSER);
		userTypeAdminToViews.add(Constant.View.UPDATEPROFILEUSER);
		userTypeAdminToViews.add(Constant.View.DELETEUSERS);
		userTypeAdminToViews.add(Constant.View.UPDATEUSER);
		userTypeAdminToViews.add(Constant.View.CATALOGUSERS);
		userTypeAdminToViews.add(Constant.View.FORGOTPASSWORD);
		userTypeAdminToViews.add(Constant.View.CHANGEPASSWORD);
		userTypeAdminToViews.add(Constant.View.RESETPASSWORD);
		userTypeAdminToViews.add(Constant.View.UNLOCKUSER);
	
		//Carga las vistas a usuario Socio
		userTypePartnerToViews.add(Constant.View.CATALOGEMPLOYEES);
		userTypePartnerToViews.add(Constant.View.CREATEEMPLOYEE);
		userTypePartnerToViews.add(Constant.View.DELETEEMPLOYEE);
		userTypePartnerToViews.add(Constant.View.UPDATEEMPLOYEE);
		
		userTypePartnerToViews.add(Constant.View.CREATECATEGORY);
		userTypePartnerToViews.add(Constant.View.CATEGORIES);
		userTypePartnerToViews.add(Constant.View.DELETECATEGORY);
		userTypePartnerToViews.add(Constant.View.UPDATECATEGORY);
		
		userTypePartnerToViews.add(Constant.View.CREATEBILL);
		userTypePartnerToViews.add(Constant.View.BILLS);
		userTypePartnerToViews.add(Constant.View.UPDATEBILL);
		userTypePartnerToViews.add(Constant.View.DELETEBILLS);

		userTypePartnerToViews.add(Constant.View.CREATECHARGE);
		userTypePartnerToViews.add(Constant.View.UPDATECHARGE);
		userTypePartnerToViews.add(Constant.View.DELETECHARGES);
		userTypePartnerToViews.add(Constant.View.CHARGES);
		
		userTypePartnerToViews.add(Constant.View.CATALOGPROJECTS);
		userTypePartnerToViews.add(Constant.View.CREATEPROJECT);
		userTypePartnerToViews.add(Constant.View.CLOSEPROJECT);
		
		//Carga las vistas a usuario Gerente
		userTypeManagerToViews.add(Constant.View.CREATECATEGORY);
		userTypeManagerToViews.add(Constant.View.CATEGORIES);
		userTypeManagerToViews.add(Constant.View.DELETECATEGORY);
		userTypeManagerToViews.add(Constant.View.UPDATECATEGORY);
		
		userTypeManagerToViews.add(Constant.View.CREATEBILL);
		userTypeManagerToViews.add(Constant.View.BILLS);
		userTypeManagerToViews.add(Constant.View.UPDATEBILL);
		userTypeManagerToViews.add(Constant.View.DELETEBILLS);

		userTypeManagerToViews.add(Constant.View.CREATECHARGE);
		userTypeManagerToViews.add(Constant.View.UPDATECHARGE);
		userTypeManagerToViews.add(Constant.View.DELETECHARGES);
		userTypeManagerToViews.add(Constant.View.CHARGES);
		
		userTypeManagerToViews.add(Constant.View.CATALOGPROJECTS);
		
		USERS_T0_VIEWS.put(Constant.UserType.USER_TYPE_ADMIN, userTypeAdminToViews);
		USERS_T0_VIEWS.put(Constant.UserType.USER_TYPE_PARTNER, userTypePartnerToViews);
		USERS_T0_VIEWS.put(Constant.UserType.USER_TYPE_MANAGER, userTypeManagerToViews);
	}
	
	public void buildInternalViews()
	{
		for(String view : USERS_T0_VIEWS.get(RequestContext.getRequestContext().getUserType()))
		{
			navigator.addView(view, getContructorByView(view));
		}
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
		
		mainLayout.addComponent(layoutViews, 2, 2, 11, 2);

		navigator.setErrorView(new LoginView());

		navigator.addView(MAINMENU, new MainMenuView());		
		navigator.addView(LOGINVIEW, new LoginView());
		
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
	private void loadMenuItems(MenuBar menuBar) {

		/**
		 * Acciones al seleccionar un item en el menu
		 */
		MenuBar.Command mainMenuBarCommand = new MenuBar.Command() {
		
			@Override
			public void menuSelected(MenuItem selectedItem) {
				// TODO Auto-generated method stub
				switch (selectedItem.getText()) {
				case "Crear usuario":					
					UI.getCurrent().getNavigator().navigateTo(Constant.View.CREATEUSER);
					break;
				case "Modificar usuarios":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.UPDATEUSER);
					break;
				case "Eliminar usuarios":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.DELETEUSERS);
					break;
				case "Catálogo usuarios":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.CATALOGUSERS);
					break;
				case "Resetear contraseña":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.RESETPASSWORD);
					break;
				case "Desbloquear usuarios":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.UNLOCKUSER);
					break;
				case "Catálogo empleados":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.CATALOGEMPLOYEES);
					break;
				case "Crear empleado":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.CREATEEMPLOYEE);
					break;
				case "Eliminar empleados":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.DELETEEMPLOYEE);
					break;
				case "Modificar empleados":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.UPDATEEMPLOYEE);
					break;
				case "Crear rubro":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.CREATECATEGORY);
					break;
				case "Eliminar rubros":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.DELETECATEGORY);
					break;
				case "Modificar rubros":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.UPDATECATEGORY);
					break;
				case "Catálogo rubros":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.CATEGORIES);
					break;
				case "Crear factura":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.CREATEBILL);
					break;
				case "Modificar facturas":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.UPDATEBILL);
					break;
				case "Eliminar facturas":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.DELETEBILLS);
					break;
				case "Catálogo facturas":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.BILLS);
					break;
				case "Crear cobro":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.CREATECHARGE);
					break;
				case "Modificar cobros":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.UPDATECHARGE);
					break;
				case "Eliminar cobros":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.DELETECHARGES);
					break;
				case "Catálogo cobros":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.CHARGES);
					break;
				case "Crear proyecto":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.CREATEPROJECT);
					break;
				case "Catálogo proyectos":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.CATALOGPROJECTS);
					break;
				case "Cerrar proyectos":
					UI.getCurrent().getNavigator().navigateTo(Constant.View.CLOSEPROJECT);
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

		MenuItem project = null;
		
		if(RequestContext.getRequestContext().getUserType() == 1)
		{
			// Rol Administrador
			MenuItem usersAdmin = menuBar.addItem("Usuarios", null, null);
			usersAdmin.addItem("Crear usuario", null, mainMenuBarCommand);
			usersAdmin.addItem("Modificar usuarios", null, mainMenuBarCommand);
			usersAdmin.addItem("Eliminar usuarios", null, mainMenuBarCommand);
			usersAdmin.addItem("Catálogo usuarios", null, mainMenuBarCommand);
			usersAdmin.addItem("Resetear contraseña", null, mainMenuBarCommand);
			usersAdmin.addItem("Desbloquear usuarios", null, mainMenuBarCommand);
		}
		if(RequestContext.getRequestContext().getUserType() == 2)
		{
			// Rol Socio
			MenuItem employeed = menuBar.addItem("Empleados", null, null);
			employeed.addItem("Crear empleado", null, mainMenuBarCommand);
			employeed.addItem("Modificar empleados", null, mainMenuBarCommand);
			employeed.addItem("Eliminar empleados", null,mainMenuBarCommand);
			employeed.addItem("Catálogo empleados", null, mainMenuBarCommand);
		}
		if(RequestContext.getRequestContext().getUserType() == 2 || RequestContext.getRequestContext().getUserType() == 3) // todo lo compartido entre socio y gerente
		{
			// Rubros
			MenuItem category = menuBar.addItem("Rubros", null, null);
			category.addItem("Crear rubro", null, mainMenuBarCommand);
			category.addItem("Modificar rubros", null, mainMenuBarCommand);
			category.addItem("Eliminar rubros", null, mainMenuBarCommand);
			category.addItem("Catálogo rubros", null, mainMenuBarCommand);
			// Facturas
			MenuItem bill = menuBar.addItem("Facturas", null, null);
			bill.addItem("Crear factura", null, mainMenuBarCommand);
			bill.addItem("Modificar facturas", null, mainMenuBarCommand);
			bill.addItem("Eliminar facturas", null, mainMenuBarCommand);
			bill.addItem("Catálogo facturas", null, mainMenuBarCommand);
			// Cobros
			MenuItem charge = menuBar.addItem("Cobros", null, null);
			charge.addItem("Crear cobro", null, mainMenuBarCommand);
			charge.addItem("Modificar cobros", null, mainMenuBarCommand);
			charge.addItem("Eliminar cobros", null, mainMenuBarCommand);
			charge.addItem("Catálogo cobros", null, mainMenuBarCommand);
			
			// Proyectos
			project = menuBar.addItem("Proyectos", null, null);
			if(RequestContext.getRequestContext().getUserType() == 2)
				project.addItem("Crear proyecto", null, mainMenuBarCommand);
			if(RequestContext.getRequestContext().getUserType() == 2)
				project.addItem("Cerrar proyectos", null, mainMenuBarCommand);
			project.addItem("Catálogo proyectos", null, mainMenuBarCommand);
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
					UI.getCurrent().getNavigator().navigateTo("LoginView");
					break;
				case "Perfil":
					UI.getCurrent().getNavigator().navigateTo(
							Constant.View.UPDATEPROFILEUSER);
					break;
				case "Cambiar contraseña":
					UI.getCurrent().getNavigator().navigateTo(
							Constant.View.CHANGEPASSWORD);
					break;
				default:					
					new PopupWindow("AVISO",
							"No hay configuración para el item: "
									+ selectedItem.getText());
					break;
				}
			}
		};

		MenuItem userItem = userMenuBar.addItem("", new ThemeResource(
				"./images/userIcon.png"), null);
		userItem.addItem("Salir", null, userMenuBarCommand);
		userItem.addItem("Perfil", null, userMenuBarCommand);
		userItem.addItem("Cambiar contraseña", null,userMenuBarCommand);

	}

	/**
	 * Cambia del login para el menu principal. Remueve el label y/o los dos
	 * menuBar Agrega dos menuBar
	 */
	 public void changeToMainMenu() {
		if (((WebvaadinUI)UI.getCurrent()).mainLayout.getComponent(2, 0) != null) {
			((WebvaadinUI)UI.getCurrent()).mainLayout.removeComponent(2, 0);
		}
		if (((WebvaadinUI)UI.getCurrent()).mainLayout.getComponent(10, 0) != null) {
			((WebvaadinUI)UI.getCurrent()).mainLayout.removeComponent(10, 0);
		}
		((WebvaadinUI)UI.getCurrent()).mainLayout.addComponent(((WebvaadinUI)UI.getCurrent()).mainMenuBar, 2, 0, 9, 0);
		((WebvaadinUI)UI.getCurrent()).mainLayout.addComponent(((WebvaadinUI)UI.getCurrent()).userMenuBar, 10, 0, 11, 0);
		loadMenuItems(((WebvaadinUI)UI.getCurrent()).mainMenuBar);

	}

	/**
	 * Cambia del menu princial para el login Remueve los dos menuBar Agrega el
	 * label del titulo
	 */
	public void changeToLogin() {		
		if (((WebvaadinUI)UI.getCurrent()).mainLayout.getComponent(2, 0) != null) {
			((WebvaadinUI)UI.getCurrent()).mainLayout.removeComponent(2, 0);
		}
		if (((WebvaadinUI)UI.getCurrent()).mainLayout.getComponent(10, 0) != null) {
			((WebvaadinUI)UI.getCurrent()).mainLayout.removeComponent(10, 0);
		}
		((WebvaadinUI)UI.getCurrent()).mainLayout.addComponent(((WebvaadinUI)UI.getCurrent()).lblTitle, 2, 0, 9, 0);
		((WebvaadinUI)UI.getCurrent()).mainLayout.addComponent(((WebvaadinUI)UI.getCurrent()).lblTitle2, 10, 0, 11, 0);
	}

}