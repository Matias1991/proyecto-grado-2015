package views.user;

import java.util.Collection;

import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;

import controllers.UserController;
import entities.RequestContext;
import entities.User;

public class CatalogUsersView extends BaseView{

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Label lblTitle;
	private Grid catalogUsersGrid;
	private BeanItemContainer<User> beanContainer;
	private Label lblMessage;

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public CatalogUsersView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		lblMessage = new Label("");
		mainLayout.addComponent(lblMessage, "top:80.0px;left:0.0px;");
		
		// TODO add user code here
	}

	public void buildGrid(){
		Collection<User> users = UserController.GetUsers();
		
		if(users != null && users.size() > 0){
			beanContainer = new BeanItemContainer<User>(User.class,users);
					
			catalogUsersGrid = new Grid(beanContainer);
			catalogUsersGrid.removeColumn("id");
			catalogUsersGrid.removeColumn("userStatus");
			catalogUsersGrid.removeColumn("password");
			catalogUsersGrid.removeColumn("userTypeId");
			catalogUsersGrid.setColumnOrder("name", "lastName", "userName", "userType", "email");
	
			catalogUsersGrid.getColumn("name").setHeaderCaption("Nombre");
			catalogUsersGrid.getColumn("lastName").setHeaderCaption("Apellido");
			catalogUsersGrid.getColumn("userName").setHeaderCaption("Nombre de usuario");
			catalogUsersGrid.getColumn("userStatusToShow").setHeaderCaption("Estado");
			catalogUsersGrid.getColumn("userType").setHeaderCaption("Tipo de usuario");
			catalogUsersGrid.getColumn("email").setHeaderCaption("Correo electr�nico");
			catalogUsersGrid.setWidth(100, Unit.PERCENTAGE);
			catalogUsersGrid.setHeight(100, Unit.PERCENTAGE);
			catalogUsersGrid.setSelectionMode(SelectionMode.SINGLE);
			catalogUsersGrid.getSelectedRows().clear();
			mainLayout.addComponent(catalogUsersGrid, "top:20%;left:0px;");
		}else{
			lblMessage.setValue("No hay usuarios para mostrar");
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		super.enter(event);		
		if(RequestContext.getRequestContext() != null){
			// Compruebo si el usuario es de tipo administrador
			if(RequestContext.getRequestContext().getUserType() != 1){
				getUI().getNavigator().navigateTo(WebvaadinUI.MAINMENU);
			}
			if(catalogUsersGrid != null){
				mainLayout.removeComponent(catalogUsersGrid);
			}
			buildGrid();
		}
		
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("682px");
		mainLayout.setHeight("501px");
		
		// top-level component properties
		setWidth("682px");
		setHeight("501px");
		
		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Cat�logo de usuarios");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");
		
		return mainLayout;
	}

}
