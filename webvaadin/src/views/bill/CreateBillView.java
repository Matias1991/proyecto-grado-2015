package views.bill;

import java.util.Collection;
import java.util.Date;

import utils.PopupWindow;
import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.sun.org.apache.bcel.internal.classfile.ConstantObject;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import controllers.BillController;
import controllers.ProjectController;
import entities.Bill;
import entities.Constant;
import entities.Project;
import entities.RequestContext;

public class CreateBillView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private TextField txtTypeExchange;
	@AutoGenerated
	private ComboBox cboxProject;
	@AutoGenerated
	private TextField txtAmount;
	@AutoGenerated
	private TextArea txtDescription;
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private Button btnCreate;
	@AutoGenerated
	private PopupDateField popupDateFieldAppliedDate;
	@AutoGenerated
	private Label lblTitle;
	@AutoGenerated
	private TextField txtCode;
	private Collection<Project> projects;
	private Label lblInfo;
	private ComboBox cboxIVA_Types;
	private TextField txtTotalAmount;
	private static final long serialVersionUID = 1L;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	
	public CreateBillView(){
		
		super("Facturas", "Crear factura");
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		cboxProject.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (cboxProject.getValue() != null) {
					int id = (int) cboxProject.getValue();
					Project project = getProjectById(id);
					buildBillCurrency(project.getIsCurrencyDollar());
				}
			}
		});
		
		txtAmount.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				buildTotalAmount();
			}
		});
		
		cboxIVA_Types.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				buildTotalAmount();
			}
		});
		
		btnCreate.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				btnCreate.setEnabled(false);
				txtDescription.setValidationVisible(true);
				txtAmount.setValidationVisible(true);
				txtCode.setValidationVisible(true);
				cboxProject.setValidationVisible(true);
				txtTypeExchange.setValidationVisible(true);
				txtTotalAmount.setValidationVisible(true);
				cboxIVA_Types.setValidationVisible(true);
				
				boolean valid = true;
				
				if(txtTypeExchange.isVisible() && !txtTypeExchange.isValid())
				{
					txtTypeExchange.setRequiredError("Es requerido");
					valid = false;
				}
				
				if(!txtTotalAmount.isValid() || !cboxIVA_Types.isValid())
				{
					txtTotalAmount.setRequiredError("Es requerido");
					cboxIVA_Types.setRequiredError("Es requerido");
					valid = false;
				}
				
				if(!txtAmount.isValid() || !txtDescription.isValid() || !txtCode.isValid() || !popupDateFieldAppliedDate.isValid() || !cboxProject.isValid()){
					txtAmount.setRequiredError("Es requerido");
					txtDescription.setRequiredError("Es requerido");
					txtAmount.setConversionError("Debe ser num�rico");
					popupDateFieldAppliedDate.setRequiredError("Es requerido");
					txtCode.setRequiredError("Es requerido");
					cboxProject.setRequiredError("Es requerido");
					valid = false;
				}
				
				if(valid)
				{
					Bill bill = new Bill();
					bill.setCode(txtCode.getValue());
					bill.setDescription(txtDescription.getValue());
					bill.setProjectId(Integer.parseInt(cboxProject.getValue().toString()));
					bill.setAppliedDateTimeUTC(popupDateFieldAppliedDate.getValue());
					bill.setIvaType((int)cboxIVA_Types.getValue());
					
					if(txtTypeExchange.isVisible() && txtTypeExchange.getValue() != null)
					{
						bill.setAmountDollar((Double)txtAmount.getConvertedValue());
						bill.setTypeExchange((Double)txtTypeExchange.getConvertedValue());
						bill.setIsCurrencyDollar(true);
					}
					else
					{
						bill.setAmountPeso((Double)txtAmount.getConvertedValue());
						bill.setIsCurrencyDollar(false);
					}
					
					boolean result = BillController.createBill(bill);
					
					if(result)
					{
						new PopupWindow("AVISO", "Factura creada correctamente");
						
						cleanInputs();
					}
				}
				
				btnCreate.setEnabled(true);
			}
		
		});
		
		btnCancel.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(Constant.View.BILLS);
			}
		});
	}
	
	void buildTotalAmount()
	{
		if (cboxIVA_Types.getValue() != null && txtAmount.getValue() != null) {
			
			if((int)cboxIVA_Types.getValue() == 1)// - 0%
			{
				txtTotalAmount.setConvertedValue((Double)txtAmount.getConvertedValue());
			}
			else if((int)cboxIVA_Types.getValue() == 2)// - 10%
			{
				txtTotalAmount.setConvertedValue((Double)txtAmount.getConvertedValue() * 1.10);
			}
			else if((int)cboxIVA_Types.getValue() == 3)// - 22%
			{
				txtTotalAmount.setConvertedValue((Double)txtAmount.getConvertedValue() * 1.22);
			}
		}
		else
			txtTotalAmount.clear();
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if(RequestContext.getRequestContext() != null){
			projects = ProjectController.getActiveProjects();
			cboxProject.removeAllItems();
			for(Project project : projects)
			{
				cboxProject.addItem(project.getId());
				cboxProject.setItemCaption(project.getId(), project.getName());
			}
			
			cboxIVA_Types.removeAllItems();
			
			cboxIVA_Types.addItem(1);
			cboxIVA_Types.setItemCaption(1, "0%");
			cboxIVA_Types.addItem(2);
			cboxIVA_Types.setItemCaption(2, "10%");
			cboxIVA_Types.addItem(3);
			cboxIVA_Types.setItemCaption(3, "22%");
			
			cboxIVA_Types.setValue(3);
			
			cleanInputs();
		}
	}
	
	void cleanInputs()
	{
		txtDescription.clear();
		txtAmount.clear();
		txtCode.clear();
		txtTypeExchange.clear();
		cboxProject.clear();
		txtTotalAmount.clear();
		
		txtDescription.setValidationVisible(false);
		txtAmount.setValidationVisible(false);
		txtCode.setValidationVisible(false);
		txtTypeExchange.setValidationVisible(false);
		cboxProject.setValidationVisible(false);
		cboxIVA_Types.setValidationVisible(false);
		txtTotalAmount.setValidationVisible(false);
		
		popupDateFieldAppliedDate.setValue(new Date());
		
		txtTypeExchange.setVisible(false);
		
		txtAmount.setCaption("Importe");
	}

	void buildBillCurrency(boolean isCurrencyDollar)
	{	
		if(isCurrencyDollar)
		{
			txtTypeExchange.setVisible(true);
			txtAmount.setCaption("Importe sin IVA (U$S)");
			txtTotalAmount.setCaption("Importe IVA incl. (U$S)");
		}
		else
		{
			txtTypeExchange.setVisible(false);
			txtAmount.setCaption("Importe sin IVA ($)");
			txtTotalAmount.setCaption("Importe IVA incl. ($)");
		}
	}
	
	Project getProjectById(int id)
	{
		for(Project p : projects)
		{
			if(p.getId() == id)
			{
				return p;
			}
		}
		return null;
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
		
		// txtCode
		txtCode = new TextField();
		txtCode.setCaption("C�digo");
		txtCode.setImmediate(false);
		txtCode.setWidth("240px");
		txtCode.setHeight("-1px");
		txtCode.setRequired(true);
		txtCode.setTabIndex(1);
		mainLayout.addComponent(txtCode, "top:116.0px;left:0.0px;");
		
		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue(getBreadCrumbToShow());
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");
		
		// popupDateFieldAppliedDate
		popupDateFieldAppliedDate = new PopupDateField();
		popupDateFieldAppliedDate.setCaption("Mes");
		popupDateFieldAppliedDate.setImmediate(false);
		popupDateFieldAppliedDate.setWidth("-1px");
		popupDateFieldAppliedDate.setHeight("-1px");
		popupDateFieldAppliedDate.setRequired(true);
		popupDateFieldAppliedDate.setDateFormat("MM-yyyy");
		popupDateFieldAppliedDate.setValue(new Date());
		popupDateFieldAppliedDate.setResolution(Resolution.MONTH);
		popupDateFieldAppliedDate.setTabIndex(7);
		mainLayout.addComponent(popupDateFieldAppliedDate,
				"top:439.0px;left:0.0px;");
		
		// btnCreate
		btnCreate = new Button();
		btnCreate.setCaption("Crear");
		btnCreate.setImmediate(true);
		btnCreate.setWidth("120px");
		btnCreate.setHeight("-1px");
		btnCreate.setTabIndex(8);
		mainLayout.addComponent(btnCreate, "top:500.0px;left:0.0px;");
		
		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("120px");
		btnCancel.setHeight("-1px");
		btnCancel.setTabIndex(9);
		mainLayout.addComponent(btnCancel, "top:500.0px;left:140.0px;");
		
		// txtDescription
		txtDescription = new TextArea();
		txtDescription.setCaption("Descripci�n");
		txtDescription.setImmediate(false);
		txtDescription.setWidth("300px");
		txtDescription.setHeight("-1px");
		txtDescription.setRequired(true);
		txtDescription.setMaxLength(240);
		txtDescription.setRows(4);
		txtDescription.setTabIndex(2);
		txtDescription.setNullRepresentation("");
		mainLayout.addComponent(txtDescription, "top:116.0px;left:270.0px;");
		
		// txtAmount
		txtAmount = new TextField();
		txtAmount.setCaption("Importe");
		txtAmount.setImmediate(true);
		txtAmount.setWidth("155px");
		txtAmount.setHeight("-1px");
		txtAmount.setRequired(true);
		txtAmount.setNullRepresentation("");
		txtAmount.setConverter(new StringToDoubleConverter());
		txtAmount.setTabIndex(4);
		mainLayout.addComponent(txtAmount, "top:249.0px;left:0.0px;");
		
		// txtTotalAmount
		txtTotalAmount = new TextField();
		txtTotalAmount.setCaption("Importe");
		txtTotalAmount.setImmediate(true);
		txtTotalAmount.setWidth("155px");
		txtTotalAmount.setHeight("-1px");
		txtTotalAmount.setRequired(true);
		txtTotalAmount.setEnabled(false);
		txtTotalAmount.setNullRepresentation("");
		txtTotalAmount.setConverter(new StringToDoubleConverter());
		txtTotalAmount.setTabIndex(6);
		mainLayout.addComponent(txtTotalAmount, "top:379.0px;left:0.0px;");
		
		// cboxProject
		cboxProject = new ComboBox();
		cboxProject.setCaption("Proyecto");
		cboxProject.setImmediate(true);
		cboxProject.setWidth("245px");
		cboxProject.setHeight("-1px");
		cboxProject.setRequired(true);
		cboxProject.setTabIndex(3);
		mainLayout.addComponent(cboxProject, "top:180.0px;left:0.0px;");
		
		// cboxIVA_Types
		cboxIVA_Types = new ComboBox();
		cboxIVA_Types.setCaption("IVA");
		cboxIVA_Types.setImmediate(true);
		cboxIVA_Types.setWidth("120px");
		cboxIVA_Types.setHeight("-1px");
		cboxIVA_Types.setRequired(true);
		cboxIVA_Types.setTabIndex(5);
		cboxIVA_Types.setNullSelectionAllowed(false);
		mainLayout.addComponent(cboxIVA_Types, "top:314.0px;left:0.0px;");
		
		// txtTypeExchange
		txtTypeExchange = new TextField();
		txtTypeExchange.setCaption("Tipo de cambio");
		txtTypeExchange.setImmediate(true);
		txtTypeExchange.setVisible(false);
		txtTypeExchange.setWidth("100px");
		txtTypeExchange.setHeight("-1px");
		txtTypeExchange.setRequired(true);
		txtTypeExchange.setNullRepresentation("");
		txtTypeExchange.setConverter(new StringToDoubleConverter());
		mainLayout.addComponent(txtTypeExchange,
				"top:249.0px;right:265.0px;left:160px;");
		
		// lblInfo
		lblInfo = new Label();
		lblInfo.setStyleName("update-bill-lblInformation");
		lblInfo.setContentMode(ContentMode.HTML);
		lblInfo.setImmediate(false);
		lblInfo.setWidth("-1px");
		lblInfo.setHeight("-1px");
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<b>Importante:</b> La moneda de la factura depende de la moneda en la que este el proyecto.</br>");
		lblInfo.setValue(strBuilder.toString());
		mainLayout.addComponent(lblInfo, "top:55.0px;left:270.0px;");
		
		return mainLayout;
	}
}
