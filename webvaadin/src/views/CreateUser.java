package views;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

public class CreateUser extends CustomComponent implements View {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Label label_2;
	@AutoGenerated
	private Button btn_add;
	@AutoGenerated
	private TextField txt_userName;
	@AutoGenerated
	private TextField txt_email;
	@AutoGenerated
	private TextField txt_createLastName;
	@AutoGenerated
	private TextField txt_name;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public CreateUser() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		WebvaadinUI.setTitle("Crear Usuario");
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("380px");
		mainLayout.setHeight("414px");
		
		// top-level component properties
		setWidth("380px");
		setHeight("414px");
		
		// txt_name
		txt_name = new TextField();
		txt_name.setCaption("Nombre");
		txt_name.setImmediate(false);
		txt_name.setWidth("240px");
		txt_name.setHeight("-1px");
		mainLayout.addComponent(txt_name, "top:120.0px;left:60.0px;");
		
		// txt_createLastName
		txt_createLastName = new TextField();
		txt_createLastName.setCaption("Apellido");
		txt_createLastName.setImmediate(false);
		txt_createLastName.setWidth("241px");
		txt_createLastName.setHeight("-1px");
		txt_createLastName.setNullSettingAllowed(true);
		mainLayout.addComponent(txt_createLastName, "top:176.0px;left:59.0px;");
		
		// txt_email
		txt_email = new TextField();
		txt_email.setCaption("Email");
		txt_email.setImmediate(false);
		txt_email.setWidth("240px");
		txt_email.setHeight("-1px");
		mainLayout.addComponent(txt_email, "top:236.0px;left:60.0px;");
		
		// txt_userName
		txt_userName = new TextField();
		txt_userName.setCaption("Nombre de usuario");
		txt_userName.setImmediate(false);
		txt_userName.setWidth("240px");
		txt_userName.setHeight("-1px");
		mainLayout.addComponent(txt_userName, "top:300.0px;left:60.0px;");
		
		// btn_add
		btn_add = new Button();
		btn_add.setCaption("Crear");
		btn_add.setImmediate(true);
		btn_add.setWidth("-1px");
		btn_add.setHeight("-1px");
		mainLayout.addComponent(btn_add, "top:360.0px;left:59.0px;");
		
		// label_2
		label_2 = new Label();
		label_2.setImmediate(false);
		label_2.setWidth("-1px");
		label_2.setHeight("-1px");
		label_2.setValue("Crear usuario");
		mainLayout.addComponent(label_2, "top:62.0px;left:60.0px;");
		
		return mainLayout;
	}

}
