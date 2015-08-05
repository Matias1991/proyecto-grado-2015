package views.profile;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.GetUser;
import servicelayer.service.ServiceWebStub.GetUserResponse;
import servicelayer.service.ServiceWebStub.VOUser;
import utils.PopupWindow;
import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

import controllers.UserController;
import entities.RequestContext;
import entities.User;

public class ModifyProfileView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private Button btnModify;
	@AutoGenerated
	private Label lblTitle;
	@AutoGenerated
	private TextField txtEmail;
	@AutoGenerated
	private TextField txtLastName;
	@AutoGenerated
	private TextField txtName;
	private VOUser userToShow;
	private int idUser = 1;

	private static final long serialVersionUID = 1L;

	public ModifyProfileView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		btnModify.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				if (validateModifyProfileUser()) {
					ServiceWebStub modifyProfileService;

					UserController modifyUser = new UserController();

					User user = new User();
					user.setEmail(txtEmail.getValue());
					user.setLastName(txtLastName.getValue());
					user.setName(txtName.getValue());
					user.setUserTypeId(userToShow.getUserType());

					modifyUser.modifyUser(user, idUser);

					new PopupWindow("AVISO", "Perfil modificado correctamente");
					
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
			idUser = RequestContext.getRequestContext().getId();
			txtName.focus();
			ServiceWebStub uploadUser;
			try {
				uploadUser = new ServiceWebStub();
				GetUser user = new GetUser();
				user.setId(idUser);

				GetUserResponse userFromBase = uploadUser.getUser(user);

				userToShow = userFromBase.get_return();

				if(userToShow.getName() != null){
					txtName.setValue(userToShow.getName());
				} else {
					txtName.setValue("");;
				}
				
				if(userToShow.getLastName() != null){
					txtLastName.setValue(userToShow.getLastName());
				} else {
					txtLastName.setValue("");
				}
				
				if(userToShow.getEmail() != null){
					txtEmail.setValue(userToShow.getEmail());
				} else {
					txtEmail.setValue("");
				}

			} catch (AxisFault e1) {
				String error = e1.getMessage().replace("<faultstring>", "");
				PopupWindow popup = new PopupWindow("ERROR", error.replace("</faultstring>", ""));
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			
			txtName.setRequiredError(null);
			txtEmail.setRequiredError(null);
			txtLastName.setRequiredError(null);
		}
	}

	public boolean validateModifyProfileUser() {
		boolean validate = true;
		
		if(!txtName.isValid() || !txtEmail.isValid() || !txtLastName.isValid()){
			txtName.setRequiredError("Es requerido");
			txtEmail.setRequiredError("Es requerido");
			txtLastName.setRequiredError("Es requerido");
			validate = false;
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
		
		// txt_name
		txtName = new TextField();
		txtName.setCaption("Nombre");
		txtName.setImmediate(true);
		txtName.setWidth("240px");
		txtName.setHeight("-1px");
		txtName.setTabIndex(1);
		txtName.setRequired(true);
		mainLayout.addComponent(txtName, "top:120.0px;left:0.0px;");
		
		// txt_lastName
		txtLastName = new TextField();
		txtLastName.setCaption("Apellido");
		txtLastName.setImmediate(true);
		txtLastName.setWidth("241px");
		txtLastName.setHeight("-1px");
		txtLastName.setTabIndex(2);
		txtLastName.setRequired(true);
		txtLastName.setNullSettingAllowed(true);
		mainLayout.addComponent(txtLastName, "top:180.0px;left:0.0px;");
		
		// txt_email
		txtEmail = new TextField();
		txtEmail.setCaption("Email");
		txtEmail.setImmediate(true);
		txtEmail.setWidth("240px");
		txtEmail.setHeight("-1px");
		txtEmail.setTabIndex(3);
		txtEmail.setRequired(true);
		txtEmail.addValidator(new EmailValidator("Formato invalido"));
		mainLayout.addComponent(txtEmail, "top:240.0px;left:0.0px;");
		
		// label_2
		lblTitle = new Label();
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Modificar perfil");
		lblTitle.setStyleName("titleLabel");
		mainLayout.addComponent(lblTitle, "top:62.0px;left:0.0px;");
		
		// btn_modify
		btnModify = new Button();
		btnModify.setCaption("Modificar");
		btnModify.setImmediate(true);
		btnModify.setWidth("101px");
		btnModify.setHeight("-1px");
		btnModify.setTabIndex(4);
		mainLayout.addComponent(btnModify, "top:300.0px;left:0.0px;");
		
		// btn_cancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("100px");
		btnCancel.setHeight("-1px");
		btnCancel.setTabIndex(5);
		mainLayout.addComponent(btnCancel, "top:300.0px;left:140.0px;");
		
		return mainLayout;
	}

}