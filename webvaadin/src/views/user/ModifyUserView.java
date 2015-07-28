package views.user;

import java.util.Collection;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.UpdateUser;
import servicelayer.service.ServiceWebStub.VOUser;
import utils.PopupWindow;
import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;

import controllers.UserController;
import entities.RequestContext;
import entities.User;

public class ModifyUserView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private AbsoluteLayout userLayout;
	@AutoGenerated
	private TextField txtName;
	@AutoGenerated
	private TextField txtLastName;
	@AutoGenerated
	private TextField txtEmail;
	@AutoGenerated
	private TextField txtUserName;
	@AutoGenerated
	private OptionGroup userType;
	@AutoGenerated
	private Button btnModify;
	@AutoGenerated
	private Label lblTitle;
	private BeanItemContainer<User> container;
	private int idUserSelected;
	private Grid modifyUserGrid;
	private Label lblMessage;
	
	

	
	public ModifyUserView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		lblMessage = new Label("");
		mainLayout.addComponent(lblMessage, "top:80.0px;left:0.0px;");
		
		userType.addItems("Socio", "Gerente", "Administrador");

		
		btnModify.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				ServiceWebStub modifyService;
				if (validateModifyUser()) {
					try {
						modifyService = new ServiceWebStub();

						UpdateUser modifyUser = new UpdateUser();
						VOUser voUser = new VOUser();
						voUser.setEmail(txtEmail.getValue());
						voUser.setLastName(txtLastName.getValue());
						voUser.setName(txtName.getValue());
						voUser.setUserName(txtUserName.getValue());
						if (userType.getValue().equals("Socio")) {
							voUser.setUserType(2);
						} else if (userType.getValue().equals("Gerente")) {
							voUser.setUserType(3);
						} else if (userType.getValue().equals("Administrador")) {
							voUser.setUserType(1);
						}

						modifyUser.setVoUser(voUser);
						modifyUser.setId(idUserSelected);
						modifyService.updateUser(modifyUser);

						PopupWindow popup = new PopupWindow("AVISO", "Usuario modificado correctamente");
						mainLayout.removeComponent(modifyUserGrid);
						buildGrid();
						userLayout.setVisible(false);
						
					} catch (java.rmi.RemoteException e) {
						String error = e.getMessage().replace("<faultstring>",
								"");
						PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
					}
				}
			}

		});

		btnCancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(WebvaadinUI.MAINMENU);
			}
		});
	}
	
	public void buildGrid(){
		Collection<User> users = UserController.GetUsers();
		
		if(users != null && users.size() > 0){
			container = new BeanItemContainer<User>(User.class,users);
			
			modifyUserGrid = new Grid(container);
			modifyUserGrid.removeColumn("id");
			modifyUserGrid.removeColumn("userStatus");
			modifyUserGrid.removeColumn("userName");
			modifyUserGrid.removeColumn("userType");
			modifyUserGrid.removeColumn("email");
			modifyUserGrid.removeColumn("userStatusToShow");
			
			modifyUserGrid.setColumnOrder("name", "lastName");	
			modifyUserGrid.getColumn("name").setHeaderCaption("Nombre");
			modifyUserGrid.getColumn("lastName").setHeaderCaption("Apellido");
			modifyUserGrid.setWidth(373, Unit.PIXELS);
			modifyUserGrid.setHeight(310, Unit.PIXELS);
			modifyUserGrid.setSelectionMode(SelectionMode.SINGLE);
			modifyUserGrid.getSelectedRows().clear();			
			mainLayout.addComponent(modifyUserGrid, "top:120.0px;left:0.0px;");
			
			modifyUserGrid.addSelectionListener(new SelectionListener() {
				@Override
				public void select(SelectionEvent event) {
					BeanItem<User> item = container.getItem(modifyUserGrid.getSelectedRow());
					if(item != null){
						User selectedUser = item.getBean();
						loadUser(selectedUser);
						userLayout.setVisible(true);
						btnModify.setEnabled(true);
					}else{
						userLayout.setVisible(false);
						btnModify.setEnabled(false);
					}
					
				}
			});
			
		}else{
			lblMessage.setValue("No hay usuarios para mostrar");
		}		
	}
	
	private void loadUser(User selectedUser){
		if(selectedUser.getName() != null){
			txtName.setValue(selectedUser.getName());
		}else{
			txtName.setValue("");
		}
		if(selectedUser.getEmail() != null){
			txtEmail.setValue(selectedUser.getEmail());
		}else{
			txtEmail.setValue("");
		}
		if(selectedUser.getLastName() != null){
			txtLastName.setValue(selectedUser.getLastName());
		}else{
			txtLastName.setValue("");
		}
		if(selectedUser.getUserName() != null){
			txtUserName.setReadOnly(false);
			txtUserName.setValue(selectedUser.getUserName());
			txtUserName.setReadOnly(true);
		}else{
			txtUserName.setValue("");
		}
		userType.select(selectedUser.getUserType());	
		idUserSelected = selectedUser.getId();
	}

	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		userLayout.setVisible(false);			
		if (RequestContext.getRequestContext() != null) {			
			if(modifyUserGrid != null){
				mainLayout.removeComponent(modifyUserGrid);
			}
			buildGrid();
		}
	}

	public boolean validateModifyUser() {
		boolean validate = true;
			
		if(!txtName.isValid() || !txtLastName.isValid() || !txtEmail.isValid()){
			txtName.setRequiredError("Es requerido");
			txtLastName.setRequiredError("Es requerido");
			txtEmail.setRequiredError("Es requerido");
			validate = false;
		}
		
		return validate;
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("1083px");
		mainLayout.setHeight("500px");
		
		// top-level component properties
		setWidth("1083px");
		setHeight("500px");
		
		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Modificar usuario");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");
		
		// btnModify
		btnModify = new Button();
		btnModify.setCaption("Modificar");
		btnModify.setImmediate(true);
		btnModify.setWidth("101px");
		btnModify.setHeight("-1px");
		btnModify.setTabIndex(6);
		mainLayout.addComponent(btnModify, "top:450.0px;left:0.0px;");
		
		// userLayout
		userLayout = buildUserLayout();
		mainLayout.addComponent(userLayout, "top:115.0px;left:400.0px;");
		
		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("100px");
		btnCancel.setHeight("-1px");
		btnCancel.setTabIndex(7);
		mainLayout.addComponent(btnCancel, "top:450.0px;left:120.0px;");
		
		return mainLayout;
	}

	@AutoGenerated
	private AbsoluteLayout buildUserLayout() {
		// common part: create layout
		userLayout = new AbsoluteLayout();
		userLayout.setCaption("Datos del usuario");
		userLayout.setImmediate(false);
		userLayout.setWidth("710px");
		userLayout.setHeight("380px");
		
		// userType
		userType = new OptionGroup();
		userType.setCaption("Tipo de usuario");
		userType.setImmediate(true);
		userType.setWidth("-1px");
		userType.setHeight("-1px");
		userType.setTabIndex(5);
		userType.setRequired(true);
		userLayout.addComponent(userType, "top:290.0px;left:0.0px;");
		
		// txtUserName
		txtUserName = new TextField();
		txtUserName.setCaption("Nombre de usuario");
		txtUserName.setImmediate(true);
		txtUserName.setReadOnly(true);
		txtUserName.setWidth("390px");
		txtUserName.setHeight("-1px");
		txtUserName.setTabIndex(4);		
		userLayout.addComponent(txtUserName, "top:225.0px;left:0.0px;");
		
		// txtEmail
		txtEmail = new TextField();
		txtEmail.setCaption("Correo electrónico");
		txtEmail.setImmediate(true);
		txtEmail.setWidth("390px");
		txtEmail.setHeight("-1px");
		txtEmail.setTabIndex(3);
		txtEmail.setRequired(true);
		txtEmail.addValidator(new EmailValidator("Correo electrónico inválido"));
		userLayout.addComponent(txtEmail, "top:156.0px;left:0.0px;");
		
		// txtLastName
		txtLastName = new TextField();
		txtLastName.setCaption("Apellido");
		txtLastName.setImmediate(true);
		txtLastName.setWidth("390px");
		txtLastName.setHeight("-1px");
		txtLastName.setTabIndex(2);
		txtLastName.setNullSettingAllowed(true);
		txtLastName.setRequired(true);
		userLayout.addComponent(txtLastName, "top:88.0px;left:0.0px;");
		
		// txtName
		txtName = new TextField();
		txtName.setCaption("Nombre");
		txtName.setImmediate(true);
		txtName.setWidth("390px");
		txtName.setHeight("-1px");
		txtName.setTabIndex(1);
		txtName.setRequired(true);
		userLayout.addComponent(txtName, "top:21.0px;left:0.0px;");
		
		return userLayout;
	}
}
