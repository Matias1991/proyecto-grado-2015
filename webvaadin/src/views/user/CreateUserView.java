package views.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import servicelayer.service.ServiceWebStub.VOUser;
import utils.PopupWindow;
import utils.Utils;
import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;

import controllers.UserController;

public class CreateUserView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private OptionGroup userType;
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private Button btnAdd;
	@AutoGenerated
	private Label lblTitle;
	@AutoGenerated
	private TextField txtUserName;
	@AutoGenerated
	private TextField txtEmail;
	@AutoGenerated
	private TextField txtCreateLastName;
	@AutoGenerated
	private TextField txtName;

	public CreateUserView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		userType.addItems("Socio", "Gerente", "Administrador");
		// cargo por defecto Socio
		userType.select("Socio");

		btnAdd.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				if (validateCreateUser()) {

					// Creo el ValueObjet cargandolo con los datos carados por
					// el usuario
					VOUser newUser = new VOUser();
					newUser.setUserName(txtUserName.getValue());
					newUser.setName(txtName.getValue());
					newUser.setLastName(txtCreateLastName.getValue());
					newUser.setEmail(txtEmail.getValue());
					if (userType.getValue().equals("Socio")) {
						newUser.setUserType(2);
					} else if (userType.getValue().equals("Gerente")) {
						newUser.setUserType(3);
					} else  if (userType.getValue().equals("Administrador")) {
						newUser.setUserType(1);
					}

					if (UserController.createUser(newUser)) {
//						Notification notif = new Notification("Aviso: ",
//								"Usuario creado correctamente",
//								Notification.TYPE_HUMANIZED_MESSAGE);
//						notif.setDelayMsec(2000);
//						notif.show(Page.getCurrent());
						PopupWindow popup = new PopupWindow("AVISO", "Usuario creado correctamente");
						// limpio los campos
						txtName.setValue("");
						txtEmail.setValue("");
						txtUserName.setValue("");
						txtCreateLastName.setValue("");
						userType.setValue("");
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
		txtName.focus();
	}

	private boolean validateCreateUser() {
		boolean validate = true;
		String errors = "";

		String userName = txtUserName.getValue();
		if (userName.isEmpty()) {
			validate = false;
			errors += "Debes ingresar el nombre de usuario\n";
		}

		String name = txtName.getValue();
		if (name.isEmpty()) {
			validate = false;
			errors += "Debes ingresar el nombre\n";
		}

		String lastName = txtCreateLastName.getValue();
		if (lastName.isEmpty()) {
			validate = false;
			errors += "Debes ingresar el apellido\n";
		}

		String email = txtEmail.getValue();
		if (email.isEmpty()) {
			validate = false;
			errors += "Debes ingresar el correo electrónico\n";
		} else {
			if(!Utils.validateMail(email)){
				validate = false;
				errors += "El formato del correo electrónico no es correcto\n";
			}
		}

		if (!validate) {
//			Notification notif = new Notification(errors,
//					Notification.TYPE_ERROR_MESSAGE);
//			notif.setDelayMsec(2000);
//			notif.show(Page.getCurrent());
			PopupWindow popup = new PopupWindow("ERROR", errors);
		}
		return validate;
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("360px");
		mainLayout.setHeight("501px");

		// top-level component properties
		setWidth("360px");
		setHeight("501px");

		// txtName
		txtName = new TextField();
		txtName.setCaption("Nombre");
		txtName.setImmediate(false);
		txtName.setWidth("240px");
		txtName.setHeight("-1px");
		txtName.setTabIndex(1);
		txtName.setRequired(true);
		mainLayout.addComponent(txtName, "top:120.0px;left:0.0px;");

		// txtCreateLastName
		txtCreateLastName = new TextField();
		txtCreateLastName.setCaption("Apellido");
		txtCreateLastName.setImmediate(false);
		txtCreateLastName.setWidth("241px");
		txtCreateLastName.setHeight("-1px");
		txtCreateLastName.setTabIndex(2);
		txtCreateLastName.setRequired(true);
		txtCreateLastName.setNullSettingAllowed(true);
		mainLayout.addComponent(txtCreateLastName, "top:180.0px;left:0.0px;");

		// txtEmail
		txtEmail = new TextField();
		txtEmail.setCaption("Correo electrónico");
		txtEmail.setImmediate(false);
		txtEmail.setWidth("240px");
		txtEmail.setHeight("-1px");
		txtEmail.setTabIndex(3);
		txtEmail.setRequired(true);
		mainLayout.addComponent(txtEmail, "top:240.0px;left:0.0px;");

		// txtUserName
		txtUserName = new TextField();
		txtUserName.setCaption("Nombre de usuario");
		txtUserName.setImmediate(false);
		txtUserName.setWidth("240px");
		txtUserName.setHeight("-1px");
		txtUserName.setTabIndex(4);
		txtUserName.setRequired(true);
		mainLayout.addComponent(txtUserName, "top:300.0px;left:0.0px;");

		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Crear usuario");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");

		// btnAdd
		btnAdd = new Button();
		btnAdd.setCaption("Crear");
		btnAdd.setImmediate(true);
		btnAdd.setWidth("101px");
		btnAdd.setHeight("-1px");
		btnAdd.setTabIndex(6);
		mainLayout.addComponent(btnAdd, "top:455.0px;left:0.0px;");

		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("100px");
		btnCancel.setHeight("-1px");
		btnCancel.setTabIndex(7);
		mainLayout.addComponent(btnCancel, "top:455.0px;left:139.0px;");

		// userType
		userType = new OptionGroup();
		userType.setCaption("Tipo de usuario");
		userType.setImmediate(false);
		userType.setWidth("-1px");
		userType.setHeight("-1px");
		userType.setTabIndex(5);
		mainLayout.addComponent(userType, "top:360.0px;left:0.0px;");

		return mainLayout;
	}

}
