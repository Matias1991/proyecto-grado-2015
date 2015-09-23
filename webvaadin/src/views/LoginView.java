package views;

import servicelayer.service.ServiceWebStub.VOUser;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Link;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import controllers.UserController;
import entities.Constant;
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
		lnkForgotPassword.setResource(new ExternalResource("#!" + Constant.View.FORGOTPASSWORD));
		lnkForgotPassword.setCaption("�Olvid� su contrase�a?");
			
		btnLogin.addClickListener(new Button.ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				txtUsername.setValidationVisible(true);
				txtPassword.setValidationVisible(true);
				
				if(!txtUsername.isValid() || !txtPassword.isValid()){
					txtUsername.setRequiredError("Es requerido");
					txtPassword.setRequiredError("Es requerido");					
				}else{	
					VOUser voUser = UserController.loginUser(txtUsername.getValue(), txtPassword.getValue());
					if(voUser != null){						
						txtUsername.setValue("");
						txtPassword.setValue("");
						txtUsername.focus();						
						
						RequestContext.setRequestContext(new UserData(voUser.getId(), voUser.getName(), voUser.getUserType()));
						((WebvaadinUI)UI.getCurrent()).buildInternalViews();
						
						((WebvaadinUI)UI.getCurrent()).changeToMainMenu();
						if(RequestContext.getRequestContext().getUserType() == 1)
							UI.getCurrent().getNavigator().navigateTo(Constant.View.CATALOGUSERS);
						else if(RequestContext.getRequestContext().getUserType() == 2)
							UI.getCurrent().getNavigator().navigateTo(Constant.View.CATALOGEMPLOYEES);
						else if(RequestContext.getRequestContext().getUserType() == 3)
							UI.getCurrent().getNavigator().navigateTo(Constant.View.CATALOGPROJECTS);
						
					}else{
						txtPassword.setValue("");
						txtPassword.focus();
					}
				}
			}			
		});	
			
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		if(RequestContext.getRequestContext() == null)
		{
			//WebvaadinUI.changeToLogin();
			((WebvaadinUI)UI.getCurrent()).changeToLogin();
			
						
		}
		else
		{
			((WebvaadinUI)UI.getCurrent()).changeToMainMenu();
			
			if(RequestContext.getRequestContext().getUserType() == 1)
				UI.getCurrent().getNavigator().navigateTo(Constant.View.CATALOGUSERS);
			else if(RequestContext.getRequestContext().getUserType() == 2)
				UI.getCurrent().getNavigator().navigateTo(Constant.View.CATALOGEMPLOYEES);
			else if(RequestContext.getRequestContext().getUserType() == 3)
				UI.getCurrent().getNavigator().navigateTo(Constant.View.CATALOGPROJECTS);
		}
		
		this.txtUsername.focus();
		txtUsername.setValidationVisible(false);
		txtPassword.setValidationVisible(false);
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
		txtUsername.setImmediate(true);
		txtUsername.setWidth("220px");
		txtUsername.setHeight("-1px");
		txtUsername.setRequired(true);
		mainLayout.addComponent(txtUsername, "top:40.0px;left:0.0px;");
		
		// txtPassword
		txtPassword = new PasswordField();
		txtPassword.setCaption("Contrase�a");
		txtPassword.setImmediate(true);
		txtPassword.setWidth("220px");
		txtPassword.setHeight("-1px");
		txtPassword.setRequired(true);
		mainLayout.addComponent(txtPassword, "top:110.0px;left:0.0px;");
		
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
		mainLayout.addComponent(lnkForgotPassword, "top:160.0px;left:55.0px;");
		
		return mainLayout;
	}

}
