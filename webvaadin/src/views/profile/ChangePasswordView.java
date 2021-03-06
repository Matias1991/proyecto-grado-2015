package views.profile;

import utils.PopupWindow;
import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;

import controllers.UserController;
import entities.RequestContext;

public class ChangePasswordView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private Button btnModify;
	@AutoGenerated
	private PasswordField txtConfirmPassword;
	@AutoGenerated
	private PasswordField txtNewPassword;
	@AutoGenerated
	private PasswordField txtOldPassword;
	@AutoGenerated
	private Label lblTitle;
	private int idUser;


	public ChangePasswordView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
				
		btnModify.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				btnModify.setEnabled(false);
				if(!txtOldPassword.isValid() || !txtNewPassword.isValid() || !txtConfirmPassword.isValid()){
					txtOldPassword.setRequiredError("Es requerido");
					txtNewPassword.setRequiredError("Es requerido");
					txtConfirmPassword.setRequiredError("Es requerido");
					btnModify.setEnabled(true);
				}else {
					if(changePasswordValidate()){
						UserController userController = new UserController();
						if(userController.changePassword(idUser, txtOldPassword.getValue(), txtNewPassword.getValue())){
							PopupWindow popup = new PopupWindow("AVISO", "Constraseña modificada correctamente");
							//limpio los campos
							cleanInputs();
							btnModify.setEnabled(true);
							getUI().getNavigator().navigateTo(WebvaadinUI.MAINMENU);
						}
					}
				}
			}
		});
		
		btnCancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				cleanInputs();
				getUI().getNavigator().navigateTo(WebvaadinUI.MAINMENU);				
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if (RequestContext.getRequestContext() != null) {
			txtOldPassword.focus();
			idUser = RequestContext.getRequestContext().getId();
		}
	}
	
	public void cleanInputs(){
		txtOldPassword.setValue("");
		txtNewPassword.setValue("");
		txtConfirmPassword.setValue("");
		txtOldPassword.setRequiredError(null);
		txtNewPassword.setRequiredError(null);
		txtConfirmPassword.setRequiredError(null);
	}

	public boolean changePasswordValidate() {
		boolean validate = true;
		String errors = "";
		
		if(!txtNewPassword.getValue().equals(txtConfirmPassword.getValue())){
			validate = false;
			errors += "La nueva contraseña y su confirmación no coinciden\n";
		}
		
		if (!validate) {
			PopupWindow popup = new PopupWindow("ERROR", errors);
		}
		return validate;
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("682px");
		mainLayout.setHeight("570px");
		
		// top-level component properties
		setWidth("682px");
		setHeight("570px");
		
		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Cambiar contraseña");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");
		
		// oldPassword
		txtOldPassword = new PasswordField();
		txtOldPassword.setCaption("Contraseña actual");
		txtOldPassword.setImmediate(true);
		txtOldPassword.setWidth("240px");
		txtOldPassword.setHeight("-1px");
		txtOldPassword.setTabIndex(1);
		txtOldPassword.setRequired(true);
		mainLayout.addComponent(txtOldPassword, "top:120.0px;left:0.0px;");
		
		// newPassword
		txtNewPassword = new PasswordField();
		txtNewPassword.setCaption("Contraseña nueva");
		txtNewPassword.setImmediate(true);
		txtNewPassword.setWidth("240px");
		txtNewPassword.setHeight("-1px");
		txtNewPassword.setTabIndex(2);
		txtNewPassword.setRequired(true);
		mainLayout.addComponent(txtNewPassword, "top:190.0px;left:0.0px;");
		
		// confirmPassword
		txtConfirmPassword = new PasswordField();
		txtConfirmPassword.setCaption("Confirmación de la nueva contraseña");
		txtConfirmPassword.setImmediate(true);
		txtConfirmPassword.setWidth("240px");
		txtConfirmPassword.setHeight("-1px");
		txtConfirmPassword.setTabIndex(2);
		txtConfirmPassword.setRequired(true);
		mainLayout.addComponent(txtConfirmPassword, "top:260.0px;left:0.0px;");
		
		// btnModify
		btnModify = new Button();
		btnModify.setCaption("Modificar");
		btnModify.setImmediate(true);
		btnModify.setWidth("100px");
		btnModify.setHeight("-1px");
		btnModify.setTabIndex(3);
		mainLayout.addComponent(btnModify, "top:330.0px;left:0.0px;");
		
		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("100px");
		btnCancel.setHeight("-1px");
		btnCancel.setTabIndex(4);
		mainLayout.addComponent(btnCancel, "top:330.0px;left:140.0px;");
		
		return mainLayout;
	}
}
