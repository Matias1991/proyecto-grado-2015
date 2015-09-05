package views.user;

import java.util.Collection;

import org.vaadin.dialogs.ConfirmDialog;

import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;

import controllers.UserController;
import entities.RequestContext;
import entities.User;

public class DeleteUsersView extends BaseView{

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button btnDelete;
	@AutoGenerated
	private Label lblTitle;
	private Grid grid;
	private BeanItemContainer<User> container;
	
	public DeleteUsersView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		//buildGrid();
		
		btnDelete.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				btnDelete.setEnabled(false);
				
				ConfirmDialog.show(WebvaadinUI.getCurrent(), "Confirmación", "Desea eliminar el usuario?", "Si", "No", new ConfirmDialog.Listener() {
					
					@Override
					public void onClose(ConfirmDialog confirm) {
						if(confirm.isConfirmed()){
							BeanItem<User> item = container.getItem(grid.getSelectedRow());
							
							if(UserController.DeleteUser(item.getBean().getId())){
								grid.getContainerDataSource().removeItem(grid.getSelectedRow());	
							}
							
							btnDelete.setEnabled(false);
						}
						
					}
				});
				
				
			}
		});
	}
	
	public void buildGrid()
	{
		Collection<User> users = UserController.GetUsers();

		container = new BeanItemContainer<User>(User.class, users);
		
		grid = new Grid(container);
		grid.removeColumn("id");
		grid.removeColumn("email");
		grid.removeColumn("userStatus");
		grid.removeColumn("password");
		grid.removeColumn("userTypeId");
		grid.setColumnOrder("name", "lastName", "userName", "userType", "userStatusToShow");
		grid.getColumn("name").setHeaderCaption("Nombre");
		grid.getColumn("lastName").setHeaderCaption("Apellido");
		grid.getColumn("userName").setHeaderCaption("Usuario");
		grid.getColumn("userType").setHeaderCaption("Tipo de usuario");
		grid.getColumn("userStatusToShow").setHeaderCaption("Estado");
		grid.setWidth(100, Unit.PERCENTAGE);
		grid.setHeight(100, Unit.PERCENTAGE);
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.getSelectedRows().clear();
		mainLayout.addComponent(grid, "top:20%;left:0px;");
		
		grid.addSelectionListener(new SelectionListener() {
			
			@Override
			   public void select(SelectionEvent event) {
				btnDelete.setEnabled(
				         grid.getSelectedRows().size() > 0);
			   }
		});	
		
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
		mainLayout.setWidth("880px");
		mainLayout.setHeight("501px");
		
		// top-level component properties
		setWidth("880px");
		setHeight("501px");
		
		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Eliminar usuarios");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");
		
		// btn_delete
		btnDelete = new Button();
		btnDelete.setCaption("Eliminar usuario");
		btnDelete.setEnabled(false);
		btnDelete.setImmediate(true);
		btnDelete.setWidth("-1px");
		btnDelete.setHeight("-1px");
		mainLayout.addComponent(btnDelete, "top:50.0px;left:720.0px;");
		
		return mainLayout;
	}
}
