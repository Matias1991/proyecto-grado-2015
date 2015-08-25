package views.project;

import views.BaseView;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class CreateProjectView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private TextArea txtDescription;	
	@AutoGenerated
	private TextField txtName;
	@AutoGenerated
	private Label lblTitle;
	@AutoGenerated
	private ComboBox cboSeller;
	@AutoGenerated
	private ComboBox cboManager;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public CreateProjectView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("1000px");
		mainLayout.setHeight("540px");
		
		// top-level component properties
		setWidth("1000px");
		setHeight("540px");
		
		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Crear proyecto");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");
		
		// txtName
		txtName = new TextField();
		txtName.setCaption("Nombre");
		txtName.setImmediate(true);
		txtName.setWidth("240px");
		txtName.setHeight("-1px");
		txtName.setTabIndex(1);
		txtName.setRequired(true);
		mainLayout.addComponent(txtName, "top:116.0px;left:0.0px;");
		
	
		// txtDescription
		txtDescription = new TextArea();
		txtDescription.setCaption("Descripción");
		txtDescription.setImmediate(true);
		txtDescription.setWidth("240px");
		txtDescription.setHeight("-1px");
		txtDescription.setMaxLength(240);
		mainLayout.addComponent(txtDescription, "top:180.0px;right:372.0px;left:3.0px;");
		
		//cboSeller
		cboSeller = new ComboBox();
		cboSeller.setImmediate(true);
		cboSeller.setWidth("250px");
		cboSeller.setHeight("-1px");
		cboSeller.setCaption("Vendedor");
		cboSeller.setInputPrompt("Seleccione el vendedor");
		cboSeller.setNullSelectionAllowed(false);
		cboSeller.setRequired(true);
		mainLayout.addComponent(cboSeller, "top:345.0px;right:372.0px;left:3.0px;");
		
		//cboManager
		cboManager = new ComboBox();
		cboManager.setImmediate(true);
		cboManager.setWidth("250px");
		cboManager.setHeight("-1px");
		cboManager.setCaption("Gerente");
		cboManager.setInputPrompt("Seleccione el gerente");
		cboManager.setNullSelectionAllowed(false);
		cboManager.setRequired(true);
		mainLayout.addComponent(cboManager, "top:410.0px;right:372.0px;left:3.0px;");
		
		
		return mainLayout;
	}

}
