package views;

import utils.PopupWindow;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

import controllers.UserController;

public class ForgotPasswordView extends CustomComponent implements View {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private Button btnAccept;
	@AutoGenerated
	private Label lblTitle;
	@AutoGenerated
	private TextField txtEmail;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public ForgotPasswordView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		
		//Evento click Aceptar
		btnAccept.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(txtEmail.isValid()){
					if(UserController.forgotPassword(txtEmail.getValue())){
						PopupWindow popup = new PopupWindow("AVISO", "Clave enviada al correo informado");
						txtEmail.clear();
						
						getUI().getNavigator().navigateTo(WebvaadinUI.LOGINVIEW);
					}
				}else{
					txtEmail.setRequiredError("Es requerido");
				}
			}
		});
		
		
		//Evento click Cancelar
		btnCancel.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				getUI().getNavigator().navigateTo(WebvaadinUI.LOGINVIEW);
			}
		});
		
		
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		//txtEmail.clear();
		mainLayout.removeComponent(txtEmail);
		mainLayout.addComponent(txtEmail, "top:90.0px;left:0.0px;");
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setStyleName("loginStyle");
		mainLayout.setImmediate(false);
		mainLayout.setWidth("280px");
		mainLayout.setHeight("210px");
		
		// top-level component properties
		setWidth("280px");
		setHeight("210px");
		
		// txtEmail
		txtEmail = new TextField();
		txtEmail.setCaption("Ingrese direcci�n de correo");
		txtEmail.setImmediate(true);
		txtEmail.setWidth("240px");
		txtEmail.setHeight("-1px");
		txtEmail.setRequired(true);
		txtEmail.addValidator(new EmailValidator("Formato invalido"));
		mainLayout.addComponent(txtEmail, "top:90.0px;left:0.0px;");
		
		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Olvido de contrase�a");
		mainLayout.addComponent(lblTitle, "top:20.0px;left:0.0px;");
		
		// btnAccept
		btnAccept = new Button();
		btnAccept.setCaption("Enviar");
		btnAccept.setImmediate(true);
		btnAccept.setWidth("100px");
		btnAccept.setHeight("-1px");
		mainLayout.addComponent(btnAccept, "top:150.0px;left:0.0px;");
		
		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("100px");
		btnCancel.setHeight("-1px");
		mainLayout.addComponent(btnCancel, "top:150.0px;left:140.0px;");
		
		return mainLayout;
	}

}
