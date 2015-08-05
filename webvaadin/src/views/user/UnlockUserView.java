package views.user;

import java.util.Collection;

import org.vaadin.dialogs.ConfirmDialog;

import utils.PopupWindow;
import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;

import controllers.UserController;
import entities.RequestContext;
import entities.User;

public class UnlockUserView extends BaseView{

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button btnUnlock;
	@AutoGenerated
	private Label lblTitle;
	private Grid grid;
	private BeanItemContainer<User> container;
	private Label lblMessage;
	
	public UnlockUserView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		lblMessage = new Label("");
		mainLayout.addComponent(lblMessage, "top:80.0px;left:0.0px;");
		
		
		btnUnlock.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				ConfirmDialog.show(WebvaadinUI.getCurrent(), "Confirmación", "Desea desbloquear el usuario?", "Si", "No", 
						new ConfirmDialog.Listener() {

					@Override
					public void onClose(ConfirmDialog confirm) {
						if(confirm.isConfirmed()){
							BeanItem<User> item = container.getItem(grid.getSelectedRow());
							
							if(UserController.unlockUser(item.getBean().getId())){
								//PopupWindow popup = new PopupWindow("AVISO", "Usuario desbloqueado correctamente");
								//getUI().getNavigator().navigateTo(WebvaadinUI.UNLOCKUSER);
								mainLayout.removeComponent(grid);
								buildGrid();
							}
							
							btnUnlock.setEnabled(false);
						}
						
					}
					
				});
			}
		});
	}
	
	public void buildGrid()
	{
		// listo los usuarios con estado = 2 (Bloqueado)
		Collection<User> users = UserController.getUsersByStatus(2);

		if(users != null && users.size() > 0){
			btnUnlock.setVisible(true);
			container = new BeanItemContainer<User>(User.class, users);
			
			grid = new Grid(container);
			grid.removeColumn("id");
			grid.removeColumn("email");
			grid.removeColumn("userType");
			grid.removeColumn("userStatus");
			grid.removeColumn("password");
			grid.removeColumn("userTypeId");
			grid.setColumnOrder("name", "lastName", "userName", "userStatusToShow");
			grid.getColumn("name").setHeaderCaption("Nombre");
			grid.getColumn("lastName").setHeaderCaption("Apellido");
			grid.getColumn("userName").setHeaderCaption("Usuario");
			grid.getColumn("userStatusToShow").setHeaderCaption("Estado");
			grid.setWidth(100, Unit.PERCENTAGE);
			grid.setHeight(100, Unit.PERCENTAGE);
			grid.setSelectionMode(SelectionMode.SINGLE);
			grid.getSelectedRows().clear();
			mainLayout.addComponent(grid, "top:20%;left:0px;");
			
			grid.addSelectionListener(new SelectionListener() {
	
				@Override
				   public void select(SelectionEvent event) {
					btnUnlock.setEnabled(
					         grid.getSelectedRows().size() > 0);
				   }
			});
		} else{
			lblMessage.setValue("No hay usuarios bloqueados");
			btnUnlock.setVisible(false);
		}
			
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if(RequestContext.getRequestContext() != null){
			if(grid != null){
				mainLayout.removeComponent(grid);
			}
			buildGrid();
		}
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("700px");
		mainLayout.setHeight("501px");
		
		// top-level component properties
		setWidth("700px");
		setHeight("501px");
		
		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Desbloquear usuarios");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");
		
		// btn_delete
		btnUnlock = new Button();
		btnUnlock.setCaption("Desbloquear usuario");
		btnUnlock.setEnabled(false);
		btnUnlock.setImmediate(true);
		btnUnlock.setWidth("-1px");
		btnUnlock.setHeight("-1px");
		mainLayout.addComponent(btnUnlock, "top:50.0px;left:510.0px;");
		
		return mainLayout;
	}
}
