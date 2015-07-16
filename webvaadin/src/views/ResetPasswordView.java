package views;

import java.util.Collection;

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
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;

import controllers.UserController;
import entities.RequestContext;
import entities.User;

public class ResetPasswordView extends BaseView{

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button btnReset;
	@AutoGenerated
	private Label lblTitle;
	private Grid grid;
	private BeanItemContainer<User> container;
	
	public ResetPasswordView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		//buildGrid();
	}
	
	public void buildGrid()
	{
		Collection<User> users = UserController.GetUsers();

		container = new BeanItemContainer<User>(User.class, users);
		
		grid = new Grid(container);
		grid.removeColumn("id");
		grid.removeColumn("userType");
		grid.removeColumn("userStatus");
		grid.removeColumn("userStatusToShow");
		grid.setColumnOrder("name", "lastName", "userName", "email");
		grid.getColumn("name").setHeaderCaption("Nombre");
		grid.getColumn("lastName").setHeaderCaption("Apellido");
		grid.getColumn("userName").setHeaderCaption("Usuario");
		grid.getColumn("email").setHeaderCaption("Correo electrónico");
		grid.setWidth(100, Unit.PERCENTAGE);
		grid.setHeight(100, Unit.PERCENTAGE);
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.getSelectedRows().clear();
		mainLayout.addComponent(grid, "top:20%;left:0px;");
		
		grid.addSelectionListener(new SelectionListener() {

			@Override
			   public void select(SelectionEvent event) {
				btnReset.setEnabled(
				         grid.getSelectedRows().size() > 0);
			   }
		});
		
		btnReset.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				BeanItem<User> item = container.getItem(grid.getSelectedRow());
				
				if(UserController.resetPassword(item.getBean().getId())){
					Notification notif = new Notification("Contraseña reseteada correctamente",
							Notification.TYPE_HUMANIZED_MESSAGE);
					notif.setDelayMsec(200);
					notif.show(Page.getCurrent());
					getUI().getNavigator().navigateTo(WebvaadinUI.RESETPASSWORD);
				}
				
				btnReset.setEnabled(false);
			}
		});

	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if(RequestContext.getRequestContext() != null){
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
		lblTitle.setValue("Resetear contraseña");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");
		
		// btn_delete
		btnReset = new Button();
		btnReset.setCaption("Resetear contraseña");
		btnReset.setEnabled(false);
		btnReset.setImmediate(true);
		btnReset.setWidth("-1px");
		btnReset.setHeight("-1px");
		mainLayout.addComponent(btnReset, "top:50.0px;left:510.0px;");
		
		return mainLayout;
	}
}
