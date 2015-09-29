package views.liquidation;

import java.util.Collection;
import java.util.Date;

import org.vaadin.dialogs.ConfirmDialog;

import utils.PopupWindow;
import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import controllers.LiquidationController;
import controllers.ProjectController;
import entities.Bill;
import entities.Category;
import entities.Constant;
import entities.Project;
import entities.ProjectEmployed;
import entities.ProjectLiquidation;
import entities.RequestContext;

public class CreateLiquidationView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	private Label lblTitle;
	private Button btnCreate;
	private Button btnCancel;
	private TextField txtTypeExchange;
	private TabSheet tabDetails;
	private ComboBox cboProject;
	private Grid grdBills;
	private Grid grdCategoriesHuman;
	private Grid grdCategoriesMaterial;
	private Grid grdEmployees;
	private TextField txtPartner1;
	private TextField txtPartner2;
	private TextField txtPartner1Distribution;
	private TextField txtPartner2Distribution;
	private BeanItemContainer<Category> beanCategoriesHuman;
	private BeanItemContainer<Category> beanCategoriesMaterial;
	private BeanItemContainer<Bill> beanBills;
	private BeanItemContainer<ProjectEmployed> beanEmployees;
	private ProjectLiquidation liquidation;
	private PopupDateField dateMonth;
	private Label lblMessageBills;
	private Label lblMessageCategoriesHuman;
	private Label lblMessageCategoriesMaterial;
	private Label lblMessageEmployees;
	private VerticalLayout tab1;
	private VerticalLayout tab2;
	private VerticalLayout tab3;
	private VerticalLayout tab4;
	private GridLayout tab5;
	private Button btnPreview;

	private static final long serialVersionUID = 1L;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public CreateLiquidationView() {
		super("Liquidaciones", "Crear liquidaci�n");

		buildMainLayout();
		setCompositionRoot(mainLayout);

		dateMonth.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (dateMonth.getValue() != null) {
					btnPreview.setEnabled(true);
					cleanTab();
				}
			}
		});
		

		cboProject.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (cboProject.getValue() != null && txtTypeExchange.isValid() && dateMonth.isValid()) {					
					cleanTab();
					liquidation = LiquidationController.getProjectsLiquidations(dateMonth.getValue(), Integer.parseInt(cboProject.getValue().toString()), (Double)txtTypeExchange.getConvertedValue());
					buildTab();
				}else{
					if(!txtTypeExchange.isValid() || !dateMonth.isValid())
					btnPreview.setEnabled(true);
					cboProject.setEnabled(false);
					cleanTab();
				}
			}
		});

		btnCancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator()
						.navigateTo(Constant.View.CATALOGPROJECTS);
			}
		});
		
		btnPreview.addClickListener(new Button.ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				if(dateMonth.isValid() && txtTypeExchange.isValid() && !LiquidationController.existLiquidation(dateMonth.getValue())){
					dateMonth.setValidationVisible(false);
					txtTypeExchange.setValidationVisible(false);
					cboProject.setEnabled(true);	
					btnPreview.setEnabled(false);					
					buildComboProjects();					
				}else{
					if(dateMonth.isValid() && txtTypeExchange.isValid()){
						new PopupWindow("ERROR", "Ya existe liquidacion para el mes seleccionado");
					}
					dateMonth.setValidationVisible(true);
					txtTypeExchange.setValidationVisible(true);
					dateMonth.setRequiredError("Es requerido");
					txtTypeExchange.setRequiredError("Es requerido");
					txtTypeExchange.setConversionError("Debe ser num�rico");
					cboProject.setEnabled(false);
					btnPreview.setEnabled(true);
					cleanTab();					
				}				
			}
			
		});
		
		btnCreate.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				btnCreate.setEnabled(false);				
				if(dateMonth.isValid() && txtTypeExchange.isValid()){
					dateMonth.setValidationVisible(false);
					txtTypeExchange.setValidationVisible(false);
					
					ConfirmDialog.show(WebvaadinUI.getCurrent(), "Confirmaci�n",
							"�Desea liquidar el mes seleccionado?", "Si", "No",
							new ConfirmDialog.Listener() {

								@Override
								public void onClose(ConfirmDialog confirm) {
									if (confirm.isConfirmed()) {
										boolean result = LiquidationController.createLiquidation(dateMonth.getValue(), Double.parseDouble(txtTypeExchange.getValue()), RequestContext.getRequestContext().getId());
										if(result){
											new PopupWindow("AVISO", "Liquidacion creada correctamente");						
											cleanInputs();
										}															
									}

								}
							});					
					
				}else{
					dateMonth.setValidationVisible(true);
					txtTypeExchange.setValidationVisible(true);
					dateMonth.setRequiredError("Es requerido");
					txtTypeExchange.setRequiredError("Es requerido");
					txtTypeExchange.setConversionError("Debe ser num�rico");					
				}				
				btnCreate.setEnabled(true);				
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if (RequestContext.getRequestContext() != null) {
			cleanTab();
			cleanInputs();
			btnCreate.setEnabled(true);
			dateMonth.setValidationVisible(false);
			txtTypeExchange.setValidationVisible(false);
			btnPreview.setEnabled(true);
			cboProject.setEnabled(false);
			
		}
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("540px");

		// top-level component properties
		setWidth("100.0%");
		setHeight("540px");

		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue(getBreadCrumbToShow());
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");

		// btnCreate
		btnCreate = new Button();
		btnCreate.setCaption("Crear");
		btnCreate.setImmediate(true);
		btnCreate.setWidth("120px");
		btnCreate.setHeight("-1px");
		btnCreate.setTabIndex(8);
		mainLayout.addComponent(btnCreate, "top:500.0px;left:0.0px;");
		
		//btnPreview
		btnPreview = new Button();
		btnPreview.setCaption("Vista previa");
		btnPreview.setImmediate(true);
		btnPreview.setWidth("120px");
		btnPreview.setHeight("-1px");
		mainLayout.addComponent(btnPreview, "top:242;left:0.0px;");

		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("120px");
		btnCancel.setHeight("-1px");
		btnCancel.setTabIndex(9);
		mainLayout.addComponent(btnCancel, "top:500.0px;left:140.0px;");

		// txtTypeExchange
		txtTypeExchange = new TextField();
		txtTypeExchange.setCaption("Tipo de cambio para liquidar");
		txtTypeExchange.setImmediate(true);
		txtTypeExchange.setWidth("100px");
		txtTypeExchange.setHeight("-1px");
		txtTypeExchange.setRequired(true);
		txtTypeExchange.setNullRepresentation("");
		txtTypeExchange.setConverter(new StringToDoubleConverter());
		mainLayout.addComponent(txtTypeExchange, "top:180;left:0.0px;");

		// cboProjects
		cboProject = new ComboBox();
		cboProject.setCaption("Proyecto");
		cboProject.setImmediate(true);
		cboProject.setWidth("240px");
		cboProject.setHeight("-1px");
		cboProject.setRequired(false);
		cboProject.setInputPrompt("Seleccione un proyecto");
		mainLayout.addComponent(cboProject, "top:116.0;left:270.0px");

		// dateMonth
		dateMonth = new PopupDateField();
		dateMonth.setCaption("Correspondiente al mes");
		dateMonth.setImmediate(true);
		dateMonth.setWidth("250px");
		dateMonth.setHeight("-1px");
		dateMonth.setRequired(true);
		dateMonth.setDateFormat("MM-yyyy");
		dateMonth.setResolution(Resolution.MONTH);
		dateMonth.setValue(new Date());
		dateMonth.setInputPrompt("Seleccione mes a liquidar");
		dateMonth.setDateOutOfRangeMessage("Fecha no v�lida");
		mainLayout.addComponent(dateMonth, "top:116.0px;left:0.0px;");
		
		// tabDetails
		tabDetails = new TabSheet();
		tabDetails.setCaption("Vista previa de proyectos a liquidar");
		tabDetails.setImmediate(true);
		tabDetails.setWidth("600px");
		tabDetails.setHeight("340px");
		mainLayout.addComponent(tabDetails, "top:180.0px;left:270.0px");

		// txtPartner1
		txtPartner1 = new TextField();
		txtPartner1.setCaption("Socio");
		txtPartner1.setImmediate(true);
		txtPartner1.setWidth("200px");
		txtPartner1.setHeight("-1px");
		txtPartner1.setNullRepresentation("");

		// txtPartner1Distribution
		txtPartner1Distribution = new TextField();
		txtPartner1Distribution.setCaption("Distribuci�n");
		txtPartner1Distribution.setImmediate(true);
		txtPartner1Distribution.setWidth("100px");
		txtPartner1Distribution.setHeight("-1px");
		txtPartner1Distribution.setNullRepresentation("");

		// txtPartner2
		txtPartner2 = new TextField();
		txtPartner2.setCaption("Socio");
		txtPartner2.setImmediate(true);
		txtPartner2.setWidth("200px");
		txtPartner2.setHeight("-1px");
		txtPartner2.setNullRepresentation("");

		// txtPartner2Distribution
		txtPartner2Distribution = new TextField();
		txtPartner2Distribution.setCaption("Distribuci�n");
		txtPartner2Distribution.setImmediate(true);
		txtPartner2Distribution.setWidth("100px");
		txtPartner2Distribution.setHeight("-1px");
		txtPartner2Distribution.setNullRepresentation("");
				
		//lblMessageBills
		lblMessageBills = new Label("No existen facturas asociadas al proyecto en el per�odo seleccionado");
		
		//lblMessageCategoriesHuman
		lblMessageCategoriesHuman = new Label("No existen rubros de tipo humano asociados al proyecto en el per�odo");
		
		//lblMessageCategoriesMaterial
		lblMessageCategoriesMaterial = new Label("No existen rubros de tipo material asociados al proyecto en el per�odo");
		
		//lblMessageEmployees
		lblMessageEmployees = new Label ("No existen empleados asociados al proyecto en el per�odo seleccionado");

		// TAB 1
		tab1 = new VerticalLayout();
		tab1.setSpacing(true);
		tab1.addComponent(lblMessageBills);
		tabDetails.addTab(tab1, "Facturas");

		// TAB2
		tab2 = new VerticalLayout();
		tab2.setSpacing(true);
		tab2.addComponent(lblMessageCategoriesHuman);
		tabDetails.addTab(tab2, "Rubros Humanos");

		// TAB3
		tab3 = new VerticalLayout();
		tab3.setSpacing(true);
		tab3.addComponent(lblMessageCategoriesMaterial);
		tabDetails.addTab(tab3, "Rubros Materiales");

		// TAB4
		tab4 = new VerticalLayout();
		tab4.setSpacing(true);
		tab4.addComponent(lblMessageEmployees);
		tabDetails.addTab(tab4, "Empleados");

		// TAB5
		tab5 = new GridLayout(2, 2);
		tab5.setSpacing(true);
		tab5.addComponent(txtPartner1, 0, 0);
		tab5.addComponent(txtPartner1Distribution, 1, 0);
		tab5.addComponent(txtPartner2, 0, 1);
		tab5.addComponent(txtPartner2Distribution, 1, 1);
		tabDetails.addTab(tab5, "Otros");

		return mainLayout;
	}

	private void buildComboProjects() {
		cboProject.removeAllItems();
		Collection<Project> projects = ProjectController.getProjects();
		for (Project aux : projects) {
			cboProject.addItem(aux.getId());
			cboProject.setItemCaption(aux.getId(), aux.getName());
		}
	}
	
	private void cleanTab(){
		if(grdBills != null)
			tab1.removeComponent(grdBills);
		lblMessageBills.setVisible(true);
		
		if(grdCategoriesHuman != null)
			tab2.removeComponent(grdCategoriesHuman);
		lblMessageCategoriesHuman.setVisible(true);			
		
		if(grdCategoriesMaterial != null)
			tab3.removeComponent(grdCategoriesMaterial);
		lblMessageCategoriesMaterial.setVisible(true);
		
		if(grdEmployees != null)
			tab4.removeComponent(grdEmployees);
		lblMessageEmployees.setVisible(true);
		
		readOnlyInputs(false);	
		txtPartner1.clear();
		txtPartner1Distribution.clear();
		txtPartner2.clear();
		txtPartner2Distribution.clear();
		readOnlyInputs(true);		
	}
	
	private void cleanInputs(){		
		txtTypeExchange.clear();
		dateMonth.clear();
		cboProject.removeAllItems();
		cleanTab();
		dateMonth.setValue(new Date());
	}
	
	private void readOnlyInputs(boolean readOnly){
		txtPartner1.setReadOnly(readOnly);
		txtPartner1Distribution.setReadOnly(readOnly);
		txtPartner2.setReadOnly(readOnly);
		txtPartner2Distribution.setReadOnly(readOnly);		
	}

	private void buildTab() {

		if (liquidation != null) {			
			// grdBills
			if(liquidation.getBills() != null && liquidation.getBills().size() > 0){
				if(grdBills != null)
					tab1.removeComponent(grdBills);
				
				beanBills = new BeanItemContainer<Bill>(Bill.class,
						liquidation.getBills());
				grdBills = new Grid(beanBills);
				grdBills.setHeight("300px");
				grdBills.setWidth(100, Unit.PERCENTAGE);
				grdBills.removeColumn("amountChargedToShow");
				grdBills.removeColumn("amountDollar");
				grdBills.removeColumn("amountPeso");
				grdBills.removeColumn("amountReceivableToShow");
				grdBills.removeColumn("appliedDateTimeUTC");
				grdBills.removeColumn("id");
				grdBills.removeColumn("isCurrencyDollar");
				grdBills.removeColumn("ivaType");
				grdBills.removeColumn("projectId");
				grdBills.removeColumn("typeExchange");
				grdBills.removeColumn("projectName");
				
				grdBills.setColumnOrder("code","description","appliedDateTimeUTCToShow","amountToShow","ivaTypeToShow","totalAmountToShow", "typeExchangeToShow");
	
				grdBills.getColumn("code").setHeaderCaption("C�digo");
				grdBills.getColumn("description").setHeaderCaption("Descripci�n");
				grdBills.getColumn("description").setWidth(192.05);
				grdBills.getColumn("appliedDateTimeUTCToShow").setHeaderCaption("Fecha");
				grdBills.getColumn("amountToShow").setHeaderCaption("Importe sin IVA");
				grdBills.getColumn("ivaTypeToShow").setHeaderCaption("IVA");
				grdBills.getColumn("totalAmountToShow").setHeaderCaption("Importe IVA incl.");
				grdBills.getColumn("typeExchangeToShow").setHeaderCaption("Tipo de cambio");
				
				lblMessageBills.setVisible(false);
				tab1.addComponent(grdBills);
			}else{
				if(grdBills != null)
					tab1.removeComponent(grdBills);
				lblMessageBills.setVisible(true);
			}
			
			
			// grdCategoriesHuman
			if(liquidation.getCategoriesHuman() != null && liquidation.getCategoriesHuman().size() > 0){
				if(grdCategoriesHuman != null)
					tab2.removeComponent(grdCategoriesHuman);
				
				beanCategoriesHuman = new BeanItemContainer<Category>(
						Category.class, liquidation.getCategoriesHuman());
				grdCategoriesHuman = new Grid(beanCategoriesHuman);
				grdCategoriesHuman.setHeight("300px");
				grdCategoriesHuman.setWidth(100, Unit.PERCENTAGE);
				
				grdCategoriesHuman.removeColumn("id");
				grdCategoriesHuman.removeColumn("amountPeso");
				grdCategoriesHuman.removeColumn("amountDollar");
				grdCategoriesHuman.removeColumn("categoryTypeId");
				grdCategoriesHuman.removeColumn("categoryType");
				grdCategoriesHuman.removeColumn("projectId");
				grdCategoriesHuman.removeColumn("isRRHH");
				grdCategoriesHuman.removeColumn("createdDateTimeUTC");			
				grdCategoriesHuman.removeColumn("projectName");
				grdCategoriesHuman.removeColumn("currencyDollar");
				grdCategoriesHuman.removeColumn("typeExchange");
				grdCategoriesHuman.removeColumn("ivaTypeId");	
				grdCategoriesHuman.removeColumn("categoryTypeToShow");
				grdCategoriesHuman.removeColumn("isDollarToShow");
				grdCategoriesHuman.removeColumn("isRRHHToShow");
				
				grdCategoriesHuman.setColumnOrder("description","createDateTimeUTCToShow","amountToShow","ivaTypeToShow","totalAmountToShow","typeExchangeToShow");
	
				grdCategoriesHuman.getColumn("description").setWidth(192.05);
				grdCategoriesHuman.getColumn("description").setHeaderCaption("Descripci�n");
				grdCategoriesHuman.getColumn("createDateTimeUTCToShow").setHeaderCaption("Fecha");
				grdCategoriesHuman.getColumn("amountToShow").setHeaderCaption("Importe sin IVA");
				grdCategoriesHuman.getColumn("ivaTypeToShow").setHeaderCaption("IVA");
				grdCategoriesHuman.getColumn("totalAmountToShow").setHeaderCaption("Importe IVA incl.");
				grdCategoriesHuman.getColumn("typeExchangeToShow").setHeaderCaption("Tipo de cambio");
				
				lblMessageCategoriesHuman.setVisible(false);
				tab2.addComponent(grdCategoriesHuman);
			}else{
				if(grdCategoriesHuman != null)
					tab2.removeComponent(grdCategoriesHuman);
				lblMessageCategoriesHuman.setVisible(true);				
			}
				
				// grdCategoriesMaterial
			if(liquidation.getCategoriesMaterial() != null && liquidation.getCategoriesMaterial().size() > 0){
				if(grdCategoriesMaterial != null)
					tab3.removeComponent(grdCategoriesMaterial);
			
				beanCategoriesMaterial = new BeanItemContainer<Category>(
						Category.class, liquidation.getCategoriesMaterial());
				grdCategoriesMaterial = new Grid(beanCategoriesMaterial);
				grdCategoriesMaterial.setHeight("300px");
				grdCategoriesMaterial.setWidth(100, Unit.PERCENTAGE);;
				
				grdCategoriesMaterial.removeColumn("id");
				grdCategoriesMaterial.removeColumn("amountPeso");
				grdCategoriesMaterial.removeColumn("amountDollar");
				grdCategoriesMaterial.removeColumn("categoryTypeId");
				grdCategoriesMaterial.removeColumn("categoryType");
				grdCategoriesMaterial.removeColumn("projectId");
				grdCategoriesMaterial.removeColumn("isRRHH");
				grdCategoriesMaterial.removeColumn("createdDateTimeUTC");			
				grdCategoriesMaterial.removeColumn("projectName");
				grdCategoriesMaterial.removeColumn("currencyDollar");
				grdCategoriesMaterial.removeColumn("typeExchange");
				grdCategoriesMaterial.removeColumn("ivaTypeId");	
				grdCategoriesMaterial.removeColumn("categoryTypeToShow");
				grdCategoriesMaterial.removeColumn("isDollarToShow");
				grdCategoriesMaterial.removeColumn("isRRHHToShow");
				
				grdCategoriesMaterial.setColumnOrder("description","createDateTimeUTCToShow","amountToShow","ivaTypeToShow","totalAmountToShow","typeExchangeToShow");
	
				grdCategoriesMaterial.getColumn("description").setWidth(192.05);
				grdCategoriesMaterial.getColumn("description").setHeaderCaption("Descripci�n");
				grdCategoriesMaterial.getColumn("createDateTimeUTCToShow").setHeaderCaption("Fecha");
				grdCategoriesMaterial.getColumn("amountToShow").setHeaderCaption("Importe sin IVA");
				grdCategoriesMaterial.getColumn("ivaTypeToShow").setHeaderCaption("IVA");
				grdCategoriesMaterial.getColumn("totalAmountToShow").setHeaderCaption("Importe IVA incl.");
				grdCategoriesMaterial.getColumn("typeExchangeToShow").setHeaderCaption("Tipo de cambio");
				
				lblMessageCategoriesMaterial.setVisible(false);
				tab3.addComponent(grdCategoriesMaterial);
			}else{
				if(grdCategoriesMaterial != null)
					tab3.removeComponent(grdCategoriesMaterial);
				lblMessageCategoriesMaterial.setVisible(true);
			}
			
						
			// grdEmployees
			if(liquidation.getEmployees() != null && liquidation.getEmployees().size() > 0){				
				if(grdEmployees != null)
					tab4.removeComponent(grdEmployees);
				
				beanEmployees = new BeanItemContainer<ProjectEmployed>(
						ProjectEmployed.class, liquidation.getEmployees());
				grdEmployees = new Grid(beanEmployees);
				grdEmployees.setHeight("300px");
				grdEmployees.setWidth(100, Unit.PERCENTAGE);
				
				grdEmployees.removeColumn("employedId");
				grdEmployees.removeColumn("enable");
				grdEmployees.removeColumn("updatedDateTimeUTC");
				grdEmployees.removeColumn("version");
				
				grdEmployees.setColumnOrder("employedName","employedLastName","employedHours");
				
				grdEmployees.getColumn("employedHours").setHeaderCaption("Horas asignadas");
				grdEmployees.getColumn("employedName").setHeaderCaption("Nombre");
				grdEmployees.getColumn("employedLastName").setHeaderCaption("Apellido");
				
				lblMessageEmployees.setVisible(false);
				tab4.addComponent(grdEmployees);
			}else{
				if(grdEmployees != null)
					tab4.removeComponent(grdEmployees);
				lblMessageEmployees.setVisible(true);			
			}
			
			if(liquidation.getPartner1Name() != null){
				readOnlyInputs(false);
				txtPartner1.setValue(liquidation.getPartner1Name() + " "
						+ liquidation.getPartner1Lastname());
				txtPartner1Distribution.setValue(liquidation
						.getPartner1Distribution());
				txtPartner2.setValue(liquidation.getPartner2Name() + " "
						+ liquidation.getPartner2Lastname());
				txtPartner2Distribution.setValue(liquidation
						.getPartner2Distribution());
				readOnlyInputs(true);
			}else{
				cleanInputs();
			}
		}else{
			cleanInputs();
		}
	}

}
