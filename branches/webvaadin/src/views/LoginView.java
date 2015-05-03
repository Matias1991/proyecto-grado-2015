package views;

import org.apache.axis2.AxisFault;

import com.example.service.TestWSStub;
import com.example.service.TestWSStub.CorrectLoginFromDB;
import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;


public class LoginView extends CustomComponent implements View, Button.ClickListener {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private Button btnLogin;
	@AutoGenerated
	private PasswordField txtPassword;
	@AutoGenerated
	private TextField txtUsername;
	@AutoGenerated
	private Label lblPassword;
	@AutoGenerated
	private Label lblUsername;
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
		btnLogin.addClickListener(new Button.ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				TestWSStub usersDescriptionService;
				
				try{
					usersDescriptionService = new TestWSStub();
					
					CorrectLoginFromDB correctLogin = new CorrectLoginFromDB();
					correctLogin.setUsername(txtUsername.getValue());
					correctLogin.setPassword(txtPassword.getValue());
					
					if(usersDescriptionService.correctLoginFromDB(correctLogin).get_return()){
						System.out.println("Inicio correcto");
						getUI().getNavigator().navigateTo(WebvaadinUI.CUSTOMUSERSDESCRIPTION);
					}else{
						System.out.println("Inicio INCORRECTO!!!");
					}
				}catch (AxisFault e) {
					e.printStackTrace();
				} catch (java.rmi.RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}			
		});		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// lblUsername
		lblUsername = new Label();
		lblUsername.setImmediate(false);
		lblUsername.setWidth("-1px");
		lblUsername.setHeight("-1px");
		lblUsername.setValue("Usuario:");
		mainLayout.addComponent(lblUsername, "top:82.0px;left:80.0px;");
		
		// lblPassword
		lblPassword = new Label();
		lblPassword.setImmediate(false);
		lblPassword.setWidth("-1px");
		lblPassword.setHeight("-1px");
		lblPassword.setValue("Password: ");
		mainLayout.addComponent(lblPassword, "top:122.0px;left:80.0px;");
		
		// txtUsername
		txtUsername = new TextField();
		txtUsername.setImmediate(false);
		txtUsername.setWidth("-1px");
		txtUsername.setHeight("-1px");
		mainLayout.addComponent(txtUsername, "top:76.0px;left:180.0px;");
		
		// txtPassword
		txtPassword = new PasswordField();
		txtPassword.setImmediate(false);
		txtPassword.setWidth("-1px");
		txtPassword.setHeight("-1px");
		mainLayout.addComponent(txtPassword, "top:116.0px;left:180.0px;");
		
		// btnLogin
		btnLogin = new Button();
		btnLogin.setCaption("Ingresar");
		btnLogin.setImmediate(true);
		btnLogin.setWidth("100px");
		btnLogin.setHeight("-1px");
		mainLayout.addComponent(btnLogin, "top:158.0px;left:80.0px;");
		
		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("100px");
		btnCancel.setHeight("-1px");
		mainLayout.addComponent(btnCancel, "top:158.0px;left:220.0px;");
		
		return mainLayout;
	}

}
