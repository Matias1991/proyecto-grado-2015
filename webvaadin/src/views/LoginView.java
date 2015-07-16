package views;

import org.apache.axis2.AxisFault;

import servicelayer.service.ServiceWebStub;
import servicelayer.service.ServiceWebStub.Login;
import servicelayer.service.ServiceWebStub.VOUser;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import controllers.UserController;
import entities.RequestContext;
import entities.UserData;


public class LoginView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Link lnkForgotPassword;
	@AutoGenerated
	private Button btnLogin;
	@AutoGenerated
	private PasswordField txtPassword;
	@AutoGenerated
	private TextField txtUsername;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public LoginView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		
		//Link de olivdar contrase�a
		lnkForgotPassword.setImmediate(true);
		lnkForgotPassword.setResource(new ExternalResource("#!" + WebvaadinUI.FORGOTPASSWORD));
		lnkForgotPassword.setCaption("�Olvid� su contrase�a?");
		
			
		btnLogin.addClickListener(new Button.ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				VOUser voUser = UserController.loginUser(txtUsername.getValue(), txtPassword.getValue());
				
				//System.out.println(voUser.getUserStatus());
				
				if(voUser != null && voUser.getUserStatus() != 0){						
					txtUsername.setValue("");
					txtPassword.setValue("");
					txtUsername.focus();
					
					//System.out.println("ACACACACACA");
					
					RequestContext.setRequestContext(new UserData(voUser.getId(), voUser.getName(), voUser.getUserType()));
										
					getUI().getNavigator().navigateTo("");
					
				}else{
					Notification.show("ERROR:", "Usuario y/o contrase�a incorrecta", Type.ERROR_MESSAGE);
					txtUsername.setValue("");
					txtPassword.setValue("");
					txtUsername.focus();
				}					
			}			
		});	
			
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		if(RequestContext.getRequestContext() == null)
		{
			WebvaadinUI.changeToLogin();
			this.txtUsername.focus();
						
		}
		else
		{
			WebvaadinUI.changeToMainMenu();
			getUI().getNavigator().navigateTo(WebvaadinUI.MAINMENU);
		}
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setStyleName("loginStyle");
		mainLayout.setImmediate(false);
		mainLayout.setWidth("280px");
		mainLayout.setHeight("260px");
		
		// top-level component properties
		setWidth("280px");
		setHeight("260px");
		
		// txtUsername
		txtUsername = new TextField();
		txtUsername.setCaption("Usuario");
		txtUsername.setImmediate(false);
		txtUsername.setWidth("220px");
		txtUsername.setHeight("-1px");
		mainLayout.addComponent(txtUsername, "top:40.0px;left:0.0px;");
		
		// txtPassword
		txtPassword = new PasswordField();
		txtPassword.setCaption("Contrasenia");
		txtPassword.setImmediate(false);
		txtPassword.setWidth("220px");
		txtPassword.setHeight("-1px");
		mainLayout.addComponent(txtPassword, "top:120.0px;left:0.0px;");
		
		// btnLogin
		btnLogin = new Button();
		btnLogin.setCaption("Ingresar");
		btnLogin.setImmediate(true);
		btnLogin.setWidth("140px");
		btnLogin.setHeight("-1px");
		mainLayout.addComponent(btnLogin, "top:194.0px;left:80.0px;");
		
		// lnkForgotPassword
		lnkForgotPassword = new Link();
		lnkForgotPassword.setCaption("Link");
		lnkForgotPassword.setImmediate(false);
		lnkForgotPassword.setWidth("-1px");
		lnkForgotPassword.setHeight("-1px");
		mainLayout.addComponent(lnkForgotPassword, "top:160.0px;left:80.0px;");
		
		return mainLayout;
	}

}
