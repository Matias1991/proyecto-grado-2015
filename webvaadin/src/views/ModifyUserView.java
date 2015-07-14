package views;

import java.util.Collection;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.UpdateUser;
import servicelayer.service.ServiceWebStub.VOUser;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.Reindeer;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;

import controllers.UserController;
import entities.RequestContext;
import entities.User;

public class ModifyUserView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private ComboBox comboBoxUsers;
	@AutoGenerated
	private OptionGroup userType;
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private Button btnModify;
	@AutoGenerated
	private Label lblTitle;
	@AutoGenerated
	private TextField txtUserName;
	@AutoGenerated
	private TextField txtEmail;
	@AutoGenerated
	private TextField txtLastName;
	@AutoGenerated
	private TextField txtName;
	private BeanItemContainer<User> container;
	private int idUserSelected;
	private String userTypeSelected;

	@SuppressWarnings("deprecation")
	public ModifyUserView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
				
		
		comboBoxUsers.addValueChangeListener(new Property.ValueChangeListener() {
			
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				if (comboBoxUsers.getValue() != null) {
					Collection<User> users = UserController.GetUsers();
					for (User user : users) {
						idUserSelected = user.getId();
						if (user.getUserName().equals(comboBoxUsers.getValue())) {
							txtName.setValue(user.getName());
							txtLastName.setValue(user.getLastName());
							txtEmail.setValue(user.getEmail());
							txtUserName.setValue(user.getUserName());
							switch(user.getUserType()){
							case "SOCIO":
								userType.select("Socio");
								break;
							case "GERENTE":
								userType.select("Gerente");
								break;
							default:
								userType.select(null);
								break;
							}							
						}
					}
				} else {
					txtName.setValue("");
					txtLastName.setValue("");
					txtEmail.setValue("");
					txtUserName.setValue("");
					userType.select(null);
				}
			}
		});
		

		/*comboBoxUsers.addListener(new Property.ValueChangeListener() {

			public void valueChange(ValueChangeEvent event) {

				if (comboBoxUsers.getValue() != null) {
					Collection<User> users = UserController.GetUsers();
					for (User user : users) {
						idUserSelected = user.getId();
						if (user.getUserName().equals(comboBoxUsers.getValue())) {
							txtName.setValue(user.getName());
							txtLastName.setValue(user.getLastName());
							txtEmail.setValue(user.getEmail());
							txtUserName.setValue(user.getUserName());
							userType.select(user.getUserType());
						}
					}
				} else {
					txtName.setValue("");
					txtLastName.setValue("");
					txtEmail.setValue("");
					txtUserName.setValue("");
					userType.setValue("");
				}
			}
		});*/

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
						} else {
							voUser.setUserType(3);
						}

						modifyUser.setVoUser(voUser);
						modifyUser.setId(idUserSelected);
						modifyService.updateUser(modifyUser);

						Notification.show("Aviso: ",
								"Usuario modificado correctamente",
								Notification.TYPE_HUMANIZED_MESSAGE);
						getUI().getNavigator().navigateTo(WebvaadinUI.MAINMENU);
					} catch (java.rmi.RemoteException e) {
						String error = e.getMessage().replace("<faultstring>",
								"");
						Notification notif = new Notification(error.replace(
								"</faultstring>", ""),
								Notification.TYPE_ERROR_MESSAGE);
						notif.setDelayMsec(2000);
						notif.show(Page.getCurrent());
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

	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if (RequestContext.getRequestContext() != null) {
			// obtengo todos los usuarios
			Collection<User> users = UserController.GetUsers();
			for (User user : users) {
				comboBoxUsers.addItem(user.getUserName());
			}
			comboBoxUsers.addItems();
			
			//if(!userTypeSelected.isEmpty()){
			if(userTypeSelected != ""){
				userType.select(userTypeSelected);	
			}
		}
	}

	public boolean validateModifyUser() {
		boolean validate = true;
		String errors = "";

		if (comboBoxUsers.getValue() != null) {
			String name = txtName.getValue();
			if (name.isEmpty()) {
				validate = false;
				errors += "Debes ingresar el nombre\n";
			}

			String lastName = txtLastName.getValue();
			if (lastName.isEmpty()) {
				validate = false;
				errors += "Debes ingresar el apellido\n";
			}

			String email = txtEmail.getValue();
			if (email.isEmpty()) {
				validate = false;
				errors += "Debes ingresar el correo electrónico\n";
			}

			String userName = txtUserName.getValue();
			if (userName.isEmpty()) {
				validate = false;
				errors += "Debes ingresar el nombre de usuario\n";
			}
		} else {
			validate = false;
			errors += "Debes seleccionar un usuario";
		}

		if (!validate) {
			Notification notif = new Notification(errors,
					Notification.TYPE_ERROR_MESSAGE);
			notif.setDelayMsec(2000);
			notif.show(Page.getCurrent());
		}
		return validate;
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("360px");
		mainLayout.setHeight("600px");

		// top-level component properties
		setWidth("682px");
		setHeight("570px");

		// txtName
		txtName = new TextField();
		txtName.setCaption("Nombre");
		txtName.setImmediate(false);
		txtName.setWidth("240px");
		txtName.setHeight("-1px");
		txtName.setTabIndex(1);
		mainLayout.addComponent(txtName, "top:190.0px;left:0.0px;");

		// txtLastName
		txtLastName = new TextField();
		txtLastName.setCaption("Apellido");
		txtLastName.setImmediate(false);
		txtLastName.setWidth("241px");
		txtLastName.setHeight("-1px");
		txtLastName.setTabIndex(2);
		txtLastName.setNullSettingAllowed(true);
		mainLayout.addComponent(txtLastName, "top:260.0px;left:0.0px;");

		// txtEmail
		txtEmail = new TextField();
		txtEmail.setCaption("Correo electrónico");
		txtEmail.setImmediate(false);
		txtEmail.setWidth("240px");
		txtEmail.setHeight("-1px");
		txtEmail.setTabIndex(3);
		mainLayout.addComponent(txtEmail, "top:330.0px;left:0.0px;");

		// txtUserName
		txtUserName = new TextField();
		txtUserName.setCaption("Nombre de usuario");
		txtUserName.setImmediate(false);
		txtUserName.setWidth("240px");
		txtUserName.setHeight("-1px");
		txtUserName.setTabIndex(4);
		mainLayout.addComponent(txtUserName, "top:400.0px;left:0.0px;");

		// lblTitle
		lblTitle = new Label();
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Modificar usuario");
		lblTitle.setStyleName(Reindeer.LABEL_H2);
		mainLayout.addComponent(lblTitle, "top:42px;left:0.0px;");

		// btnAdd
		btnModify = new Button();
		btnModify.setCaption("Modificar");
		btnModify.setImmediate(true);
		btnModify.setWidth("101px");
		btnModify.setHeight("-1px");
		btnModify.setTabIndex(6);
		mainLayout.addComponent(btnModify, "top:540.0px;left:0.0px;");

		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("100px");
		btnCancel.setHeight("-1px");
		btnCancel.setTabIndex(7);
		mainLayout.addComponent(btnCancel, "top:540.0px;left:139.0px;");

		// userType
		userType = new OptionGroup();
		userType.setCaption("Tipo de usuario");
		userType.setImmediate(false);
		userType.setWidth("-1px");
		userType.setHeight("-1px");
		userType.addItems("Socio", "Gerente");
		userType.setTabIndex(5);
		mainLayout.addComponent(userType, "top:470.0px;left:0.0px;");

		// comboBoxUsers
		comboBoxUsers = new ComboBox();
		comboBoxUsers.setCaption("Seleccione el usuario a modificar");
		comboBoxUsers.setImmediate(true);
		comboBoxUsers.setWidth("160px");
		comboBoxUsers.setHeight("24px");		
		mainLayout.addComponent(comboBoxUsers, "top:120.0px;left:0.0px;");

		return mainLayout;
	}
}
