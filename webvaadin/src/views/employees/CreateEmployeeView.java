package views.employees;

import java.util.Locale;

import utils.PopupWindow;
import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import controllers.EmployeeController;
import entities.Constant;
import entities.Employee;
import entities.RequestContext;
import entities.SalarySummary;

public class CreateEmployeeView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;	
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private Button btnCreate;
	@AutoGenerated
	private TabSheet tabTaxes;
	@AutoGenerated
	private OptionGroup optEmployeeType;
	@AutoGenerated
	private TextField txtCellPhone;
	@AutoGenerated
	private TextField txtEmail;
	@AutoGenerated
	private TextField txtAddress;
	@AutoGenerated
	private TextField txtSurname;
	@AutoGenerated
	private Label lblTitle;
	@AutoGenerated
	private TextField txtName;
	private TextField txtNominalSalary;
	private TextField txtTickets;
	private ComboBox cboPercentagePersonalFonasaContribution;
	private TextField txtRet;
	private TextField txtIrpf;
	private TextField txtBse;
	private TextField txtHours;
	private TextField txtCostSaleHour;
	private TextField txtPersonalRetirementContribution;
	private TextField txtEmployerRetirementContribution;
	private TextField txtPersonalFrlContribution;
	private TextField txtEmployerFrlContribution;
	private TextField txtPersonalFonasaContribution;
	private TextField txtEmployerFonasaContribution;
	private TextField txtTicketsEmployer;
	private TextField txtTotalDiscounts;
	private TextField txtTotalEmployersContribution;
	private TextField txtNominalWithoutContribution;
	private TextField txtDismisalPrevension;
	private TextField txtIncidenceSalary;
	private TextField txtSalaryToPay;
	private TextField txtCostMonth;
	private TextField txtCostRealHour;
	private TextField txtIncidenceTickets;
	private Button btnEstimate;	
	
	

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public CreateEmployeeView() {
		super("Empleados","Crear empleado");
		buildMainLayout();		
		setCompositionRoot(mainLayout);
					
		optEmployeeType.addItems("Empleado", "Socio");
		optEmployeeType.select("Empleado");
		
		// Sueldo Nominal
		txtNominalSalary = new TextField();
		txtNominalSalary.setCaption("Sueldo Nominal");
		txtNominalSalary.setImmediate(true);
		txtNominalSalary.setWidth("-1px");
		txtNominalSalary.setHeight("-1px");
		txtNominalSalary.setRequired(true);	
		txtNominalSalary.setNullRepresentation("");	
		txtNominalSalary.setConverter(new StringToDoubleConverter());
				
		// Tickets
		txtTickets = new TextField();
		txtTickets.setCaption("Tickets");
		txtTickets.setImmediate(true);
		txtTickets.setWidth("-1px");
		txtTickets.setHeight("-1px");
		txtTickets.setRequired(true);			
		txtTickets.setNullRepresentation("");
		txtTickets.setConverter(new StringToDoubleConverter());
		
		// RET
		txtRet = new TextField();
		txtRet.setCaption("RET");
		txtRet.setImmediate(true);
		txtRet.setWidth("-1px");
		txtRet.setHeight("-1px");			
		txtRet.setNullRepresentation("");
		txtRet.setConverter(new StringToDoubleConverter());
		
		// IRPF
		txtIrpf = new TextField();
		txtIrpf.setCaption("IRPF");
		txtIrpf.setImmediate(true);
		txtIrpf.setWidth("-1px");
		txtIrpf.setHeight("-1px");
		txtIrpf.setNullRepresentation("");
		txtIrpf.setConverter(new StringToDoubleConverter());
			
		// BSE
		txtBse = new TextField();
		txtBse.setCaption("BSE");
		txtBse.setImmediate(true);
		txtBse.setWidth("-1px");
		txtBse.setHeight("-1px");
		txtBse.setNullRepresentation("");
		txtBse.setConverter(new StringToDoubleConverter());
		
		// Horas mensuales
		txtHours = new TextField();
		txtHours.setCaption("Cantidad Horas Mensual");
		txtHours.setImmediate(true);
		txtHours.setWidth("-1px");
		txtHours.setHeight("-1px");
		txtHours.setRequired(true);
		txtHours.setNullRepresentation("");
		txtHours.setConverter(new StringToIntegerConverter());
		
		// Precio de venta por hora
		txtCostSaleHour = new TextField();
		txtCostSaleHour.setCaption("Costo hora Venta");
		txtCostSaleHour.setImmediate(true);
		txtCostSaleHour.setWidth("-1px");
		txtCostSaleHour.setHeight("-1px");		
		txtCostSaleHour.setNullRepresentation("");
		txtCostSaleHour.setConverter(new StringToDoubleConverter());
		
		// Porcentaje aporte FONASA Personal
		cboPercentagePersonalFonasaContribution = new ComboBox();
		cboPercentagePersonalFonasaContribution.setCaption("% Aporte FONASA Personal");
		cboPercentagePersonalFonasaContribution.setImmediate(true);
		cboPercentagePersonalFonasaContribution.setWidth("-1px");
		cboPercentagePersonalFonasaContribution.setHeight("-1px");
		cboPercentagePersonalFonasaContribution.setRequired(true);
		cboPercentagePersonalFonasaContribution.setLocale(Locale.ENGLISH);
		cboPercentagePersonalFonasaContribution.addItems(Double.valueOf("2"));
		cboPercentagePersonalFonasaContribution.addItems(Double.valueOf("3"));
		cboPercentagePersonalFonasaContribution.addItems(Double.valueOf("4.5"));
		cboPercentagePersonalFonasaContribution.addItems(Double.valueOf("5"));
		cboPercentagePersonalFonasaContribution.addItems(Double.valueOf("6"));
		cboPercentagePersonalFonasaContribution.addItems(Double.valueOf("8"));
		

		// Aporte Jubilatorio Personal
		txtPersonalRetirementContribution = new TextField();
		txtPersonalRetirementContribution.setCaption("Aporte Jubilatorio Personal");
		txtPersonalRetirementContribution.setImmediate(true);
		txtPersonalRetirementContribution.setWidth("-1px");
		txtPersonalRetirementContribution.setHeight("-1px");
		txtPersonalRetirementContribution.setNullRepresentation("");
		txtPersonalRetirementContribution.setConverter(new StringToDoubleConverter());
		
		// Aporte Jubilatorio Patronal
		txtEmployerRetirementContribution = new TextField();
		txtEmployerRetirementContribution.setCaption("Aporte Jubilatorio Patronal");
		txtEmployerRetirementContribution.setImmediate(true);
		txtEmployerRetirementContribution.setWidth("-1px");
		txtEmployerRetirementContribution.setHeight("-1px");
		txtEmployerRetirementContribution.setNullRepresentation("");
		txtEmployerRetirementContribution.setConverter(new StringToDoubleConverter());
		
		// Aporte FRL Personal
		txtPersonalFrlContribution = new TextField();
		txtPersonalFrlContribution.setCaption("Aporte FRL Personal");
		txtPersonalFrlContribution.setImmediate(true);
		txtPersonalFrlContribution.setWidth("-1px");
		txtPersonalFrlContribution.setHeight("-1px");
		txtPersonalFrlContribution.setNullRepresentation("");
		txtPersonalFrlContribution.setConverter(new StringToDoubleConverter());
		
		// Aporte FRL Patronal
		txtEmployerFrlContribution = new TextField();
		txtEmployerFrlContribution.setCaption("Aporte FRL Patronal");
		txtEmployerFrlContribution.setImmediate(true);
		txtEmployerFrlContribution.setWidth("-1px");
		txtEmployerFrlContribution.setHeight("-1px");
		txtEmployerFrlContribution.setNullRepresentation("");
		txtEmployerFrlContribution.setConverter(new StringToDoubleConverter());
		
		// Aporte FONASA Personal
		txtPersonalFonasaContribution = new TextField();
		txtPersonalFonasaContribution.setCaption("Aporte FONASA Personal");
		txtPersonalFonasaContribution.setImmediate(true);
		txtPersonalFonasaContribution.setWidth("-1px");
		txtPersonalFonasaContribution.setHeight("-1px");
		txtPersonalFonasaContribution.setNullRepresentation("");
		txtPersonalFonasaContribution.setConverter(new StringToDoubleConverter());
		
		// Aporte FONASA Patronal
		txtEmployerFonasaContribution = new TextField();
		txtEmployerFonasaContribution.setCaption("Aporte FONASA Patronal");
		txtEmployerFonasaContribution.setImmediate(true);
		txtEmployerFonasaContribution.setWidth("-1px");
		txtEmployerFonasaContribution.setHeight("-1px");
		txtEmployerFonasaContribution.setNullRepresentation("");
		txtEmployerFonasaContribution.setConverter(new StringToDoubleConverter());
		
		// Tickets Patronal
		txtTicketsEmployer = new TextField();
		txtTicketsEmployer.setCaption("Tickets Patronal");
		txtTicketsEmployer.setImmediate(true);
		txtTicketsEmployer.setWidth("-1px");
		txtTicketsEmployer.setHeight("-1px");
		txtTicketsEmployer.setNullRepresentation("");
		txtTicketsEmployer.setConverter(new StringToDoubleConverter());
		
		// Total Descuentos
		txtTotalDiscounts = new TextField();
		txtTotalDiscounts.setCaption("Total Descuentos");
		txtTotalDiscounts.setImmediate(true);
		txtTotalDiscounts.setWidth("-1px");
		txtTotalDiscounts.setHeight("-1px");
		txtTotalDiscounts.setNullRepresentation("");
		txtTotalDiscounts.setConverter(new StringToDoubleConverter());
		
		// Total Aportes Patronales
		txtTotalEmployersContribution = new TextField();
		txtTotalEmployersContribution.setCaption("Total Aportes Patronales");
		txtTotalEmployersContribution.setImmediate(true);
		txtTotalEmployersContribution.setWidth("-1px");
		txtTotalEmployersContribution.setHeight("-1px");
		txtTotalEmployersContribution.setNullRepresentation("");
		txtTotalEmployersContribution.setConverter(new StringToDoubleConverter());
		
		// Nominal Sin Aportes
		txtNominalWithoutContribution = new TextField();
		txtNominalWithoutContribution.setCaption("Nominal Sin Aportes");
		txtNominalWithoutContribution.setImmediate(true);
		txtNominalWithoutContribution.setWidth("-1px");
		txtNominalWithoutContribution.setHeight("-1px");
		txtNominalWithoutContribution.setNullRepresentation("");
		txtNominalWithoutContribution.setConverter(new StringToDoubleConverter());
		
		// Prevision Despido
		txtDismisalPrevension = new TextField();
		txtDismisalPrevension.setCaption("Prevision Despido");
		txtDismisalPrevension.setImmediate(true);
		txtDismisalPrevension.setWidth("-1px");
		txtDismisalPrevension.setHeight("-1px");
		txtDismisalPrevension.setNullRepresentation("");
		txtDismisalPrevension.setConverter(new StringToDoubleConverter());
		
		// Incidencia Sueldo
		txtIncidenceSalary = new TextField();
		txtIncidenceSalary.setCaption("Incidencia Sueldo");
		txtIncidenceSalary.setImmediate(true);
		txtIncidenceSalary.setWidth("-1px");
		txtIncidenceSalary.setHeight("-1px");
		txtIncidenceSalary.setNullRepresentation("");
		txtIncidenceSalary.setConverter(new StringToDoubleConverter());
		
		//Incidencia Tickets
		txtIncidenceTickets = new TextField();
		txtIncidenceTickets.setCaption("Incidencia Tickets");
		txtIncidenceTickets.setImmediate(true);
		txtIncidenceTickets.setWidth("-1px");
		txtIncidenceTickets.setHeight("-1px");
		txtIncidenceTickets.setNullRepresentation("");
		txtIncidenceTickets.setConverter(new StringToDoubleConverter());
		
		// Sueldos a Pagar
		txtSalaryToPay = new TextField();
		txtSalaryToPay.setCaption("Sueldos A Pagar");
		txtSalaryToPay.setImmediate(true);
		txtSalaryToPay.setWidth("-1px");
		txtSalaryToPay.setHeight("-1px");
		txtSalaryToPay.setNullRepresentation("");
		txtSalaryToPay.setConverter(new StringToDoubleConverter());
		
		// Costo Mensual
		txtCostMonth = new TextField();
		txtCostMonth.setCaption("Costo Mensual");
		txtCostMonth.setImmediate(true);
		txtCostMonth.setWidth("-1px");
		txtCostMonth.setHeight("-1px");
		txtCostMonth.setNullRepresentation("");
		txtCostMonth.setConverter(new StringToDoubleConverter());
			
		// Costo Hora Meerkat
		txtCostRealHour = new TextField();
		txtCostRealHour.setCaption("Costo Hora Meerkat");
		txtCostRealHour.setImmediate(true);
		txtCostRealHour.setWidth("-1px");
		txtCostRealHour.setHeight("-1px");	
		txtCostRealHour.setNullRepresentation("");
		txtCostRealHour.setConverter(new StringToDoubleConverter());
		
		changeReadOnlyState(true);
		
		// Boton Estimar
		btnEstimate = new Button();
		btnEstimate.setCaption("Estimar");
		btnEstimate.setImmediate(true);
		btnEstimate.setWidth("-1px");
		btnEstimate.setHeight("-1px");
		
		//TAB 1
		GridLayout tab1 = new GridLayout(2, 5);
		tab1.setSpacing(true);		
		tab1.addComponent(txtNominalSalary, 0, 0);
		tab1.addComponent(txtTickets, 1, 0);		
		tab1.addComponent(cboPercentagePersonalFonasaContribution, 0,1);
		tab1.addComponent(txtHours,1,1);
		tab1.addComponent(txtIrpf,0,2);
		tab1.addComponent(txtBse,1,2);
		tab1.addComponent(txtRet,0,3);
		tab1.addComponent(txtCostSaleHour,1,3);
		tab1.addComponent(btnEstimate,0,4);
		tabTaxes.addTab(tab1, "Costos 1");
		
		//TAB 2
		GridLayout tab2 = new GridLayout(2,4);
		tab2.setSpacing(true);
		tab2.addComponent(txtPersonalRetirementContribution,0,0);
		tab2.addComponent(txtEmployerRetirementContribution,1,0);
		tab2.addComponent(txtPersonalFrlContribution,0,1);
		tab2.addComponent(txtEmployerFrlContribution,1,1);
		tab2.addComponent(txtPersonalFonasaContribution,0,2);
		tab2.addComponent(txtEmployerFonasaContribution,1,2);
		tab2.addComponent(txtTotalDiscounts,0,3);
		tab2.addComponent(txtTicketsEmployer,1,3);		
		tabTaxes.addTab(tab2, "Costos 2");
		
		
		//TAB3
		GridLayout tab3 = new GridLayout(2,4);
		tab3.setSpacing(true);
		tab3.addComponent(txtNominalWithoutContribution,0,0);
		tab3.addComponent(txtTotalEmployersContribution,1,0);			
		tab3.addComponent(txtIncidenceSalary,0,1);
		tab3.addComponent(txtIncidenceTickets,1,1);
		tab3.addComponent(txtDismisalPrevension,0,2);		
		tab3.addComponent(txtCostMonth,1,2);
		tab3.addComponent(txtCostRealHour,0,3);
		tab3.addComponent(txtSalaryToPay,1,3);		
		tabTaxes.addTab(tab3, "Costos 3");
		
		
		btnEstimate.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (validateEstimateEmployee()){										
					SalarySummary estimateSalarySummary = new SalarySummary();					
					estimateSalarySummary.setNominalSalary((Double)txtNominalSalary.getConvertedValue());
					estimateSalarySummary.setTickets((Double)txtTickets.getConvertedValue());	
					if(txtRet.getConvertedValue() == null){
						txtRet.setConvertedValue(0.0);
					}					
					estimateSalarySummary.setRET((Double)txtRet.getConvertedValue());
					if(txtIrpf.getConvertedValue()== null){
						txtIrpf.setConvertedValue(0.0);
					}
					estimateSalarySummary.setIRPF((Double)txtIrpf.getConvertedValue());		
					if(txtBse.getConvertedValue() == null){
						txtBse.setConvertedValue(0.0);
					}
					estimateSalarySummary.setBSE((Double)txtBse.getConvertedValue());
					estimateSalarySummary.setHours((Integer)txtHours.getConvertedValue());
					if(txtCostSaleHour.getConvertedValue() == null){
						txtCostSaleHour.setConvertedValue(0.0);
					}
					estimateSalarySummary.setCostSaleHour((Double)txtCostSaleHour.getConvertedValue());
					//estimateSalarySummary.setPercentageTypeFONASA((Double.parseDouble(cboPercentagePersonalFonasaContribution.getValue().toString()))/100);
					estimateSalarySummary.setPercentageTypeFONASA((Double)cboPercentagePersonalFonasaContribution.getValue()/100);
					
									
					SalarySummary aux = EmployeeController.estimateEmployee(estimateSalarySummary);
					if(aux != null){						
						changeReadOnlyState(false);
						
						txtNominalSalary.setConvertedValue(aux.getNominalSalary());
						txtTickets.setConvertedValue(aux.getTickets());
						txtRet.setConvertedValue(aux.getRET());
						txtIrpf.setConvertedValue(aux.getIRPF());						
						txtBse.setConvertedValue(aux.getBSE());						
						txtHours.setConvertedValue(aux.getHours());
						txtCostSaleHour.setConvertedValue(aux.getCostSaleHour());							
						txtPersonalRetirementContribution.setConvertedValue(aux.getPersonalRetirementContribution());
						txtEmployerRetirementContribution.setConvertedValue(aux.getEmployersContributionsRetirement());
						txtPersonalFrlContribution.setConvertedValue(aux.getPersonalFRLContribution());
						txtEmployerFrlContribution.setConvertedValue(aux.getEmployersFRLContribution());
						txtPersonalFonasaContribution.setConvertedValue(aux.getPersonalFONASAContribution());
						txtEmployerFonasaContribution.setConvertedValue(aux.getEmployersFONASAContribution());
						txtTicketsEmployer.setConvertedValue(aux.getTicketsEmployers());
						txtTotalDiscounts.setConvertedValue(aux.getTotalDiscounts());
						txtTotalEmployersContribution.setConvertedValue(aux.getTotalEmployerContributions());
						txtNominalWithoutContribution.setConvertedValue(aux.getNominalWithoutContributions());
						txtDismisalPrevension.setConvertedValue(aux.getDismissalPrevention());
						txtIncidenceSalary.setConvertedValue(aux.getIncidenceSalary());
						txtSalaryToPay.setConvertedValue(aux.getSalaryToPay());
						txtCostMonth.setConvertedValue(aux.getCostMonth());
						txtCostRealHour.setConvertedValue(aux.getCostRealHour());	
						cboPercentagePersonalFonasaContribution.setValue(aux.getPercentageTypeFONASA()*100);
						txtIncidenceTickets.setConvertedValue(aux.getIncidenceTickets());
						
						changeReadOnlyState(true);
						tabTaxes.setSelectedTab(1);
					}else{					
						PopupWindow notification = new PopupWindow("ERROR", "No se pudo estimar");			
					}
					
				}
				
			}
		});
		
		btnCreate.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
			btnCreate.setEnabled(false);
				
				if(validateCreateEmployee()){
					Employee newEmployee = new Employee();
					newEmployee.setName(txtName.getValue());
					newEmployee.setLastName(txtSurname.getValue());
					newEmployee.setAddress(txtAddress.getValue());
					newEmployee.setEmail(txtEmail.getValue());
					newEmployee.setCellPhone(txtCellPhone.getValue());
					newEmployee.setEmployedType(optEmployeeType.getValue().toString());

					SalarySummary newSalarySummary = new SalarySummary();
					newSalarySummary.setNominalSalary((Double)txtNominalSalary.getConvertedValue());
					newSalarySummary.setTickets((Double)txtTickets.getConvertedValue());					
					if(txtRet.getConvertedValue() == null){
						txtRet.setConvertedValue(0.0);
					}					
					newSalarySummary.setRET((Double)txtRet.getConvertedValue());
					if(txtIrpf.getConvertedValue()== null){
						txtIrpf.setConvertedValue(0.0);
					}
					newSalarySummary.setIRPF((Double)txtIrpf.getConvertedValue());		
					if(txtBse.getConvertedValue() == null){
						txtBse.setConvertedValue(0.0);
					}
					newSalarySummary.setBSE((Double)txtBse.getConvertedValue());
					newSalarySummary.setHours((Integer)txtHours.getConvertedValue());
					if(txtCostSaleHour.getConvertedValue() == null){
						txtCostSaleHour.setConvertedValue(0.0);
					}					
					newSalarySummary.setCostSaleHour((Double)txtCostSaleHour.getConvertedValue());
					//newSalarySummary.setPercentageTypeFONASA(((Double.parseDouble(cboPercentagePersonalFonasaContribution.getValue().toString())))/100);
					newSalarySummary.setPercentageTypeFONASA((Double)cboPercentagePersonalFonasaContribution.getValue()/100);
					
					newEmployee.setSalarySummary(newSalarySummary);
				
				
					if(EmployeeController.createEmployee(newEmployee)){
						PopupWindow popup = new PopupWindow("AVISO", "Empleado creado correctamente");
						cleanInputs();								
					}
				}				
				btnCreate.setEnabled(true);
			}
		});
		
		btnCancel.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				UI.getCurrent().getNavigator().navigateTo(Constant.View.CATALOGEMPLOYEES);				
			}
		});
		
		
		
	}		
	
	public boolean validateEstimateEmployee(){		
		boolean result = true;
		
		if(!txtNominalSalary.isValid()){
			result = false;
			txtNominalSalary.setRequiredError("Es requerido");
			txtNominalSalary.setConversionError("Debe ser num�rico");
		}
		if(!txtTickets.isValid()){
			result = false;
			txtTickets.setRequiredError("Es requerido");
			txtTickets.setConversionError("Debe ser num�rico");
		}
		if(!cboPercentagePersonalFonasaContribution.isValid()){
			result = false;
			cboPercentagePersonalFonasaContribution.setRequiredError("Es requerido");
			cboPercentagePersonalFonasaContribution.setConversionError("Debe ser num�rico entre 0 y 100");			
		}
		if(!txtHours.isValid()){
			result = false;
			txtHours.setRequiredError("Es requerido");
			txtHours.setConversionError("Debe ser num�rico");
		}
		if(!txtIrpf.isValid()){
			result = false;
			txtIrpf.setConversionError("Debe ser num�rico");
		}
		if(!txtBse.isValid()){
			result = false;
			txtBse.setConversionError("Debe ser num�rico");
		}
		if(!txtRet.isValid()){
			result = false;
			txtRet.setConversionError("Debe ser num�rico");
		}
		if(!txtCostSaleHour.isValid()){
			result = false;
			txtCostSaleHour.setConversionError("Debe ser num�rico");
		}
		
		return result;	
	}
	
	public boolean validateCreateEmployee(){
		boolean validate = validateEstimateEmployee();
		
		if(!txtName.isValid() || !txtSurname.isValid() || !txtEmail.isValid()){			
			txtName.setRequiredError("Es requerido");
			txtSurname.setRequiredError("Es requerido");
			validate = false;
		}		
		
		return validate;		
	}
	
	public void changeReadOnlyState(boolean state){
		txtPersonalRetirementContribution.setReadOnly(state);
		txtEmployerRetirementContribution.setReadOnly(state);
		txtPersonalFrlContribution.setReadOnly(state);
		txtEmployerFrlContribution.setReadOnly(state);
		txtPersonalFonasaContribution.setReadOnly(state);
		txtEmployerFonasaContribution.setReadOnly(state);
		txtTicketsEmployer.setReadOnly(state);
		txtTotalDiscounts.setReadOnly(state);
		txtTotalEmployersContribution.setReadOnly(state);
		txtNominalWithoutContribution.setReadOnly(state);
		txtDismisalPrevension.setReadOnly(state);
		txtIncidenceSalary.setReadOnly(state);
		txtSalaryToPay.setReadOnly(state);
		txtCostMonth.setReadOnly(state);
		txtCostRealHour.setReadOnly(state);
		txtIncidenceTickets.setReadOnly(state);
		
	}
	
	public void cleanInputs(){
		changeReadOnlyState(false);
		optEmployeeType.select("Empleado");
		txtCellPhone.clear();
		txtEmail.clear();
		txtAddress.clear();
		txtSurname.clear();
		txtName.clear();
		txtNominalSalary.clear();
		txtTickets.clear();
		cboPercentagePersonalFonasaContribution.clear();
		txtRet.clear();
		txtIrpf.clear();
		txtBse.clear();
		txtHours.clear();
		txtCostSaleHour.clear();
		txtPersonalRetirementContribution.clear();
		txtEmployerRetirementContribution.clear();
		txtPersonalFrlContribution.clear();
		txtEmployerFrlContribution.clear();
		txtPersonalFonasaContribution.clear();
		txtEmployerFonasaContribution.clear();
		txtTicketsEmployer.clear();
		txtTotalDiscounts.clear();
		txtTotalEmployersContribution.clear();
		txtNominalWithoutContribution.clear();
		txtDismisalPrevension.clear();
		txtIncidenceSalary.clear();
		txtSalaryToPay.clear();
		txtCostMonth.clear();
		txtCostRealHour.clear();
		txtIncidenceTickets.clear();
		changeReadOnlyState(true);	
		txtNominalSalary.setRequiredError(null);
		txtTickets.setRequiredError(null);
		cboPercentagePersonalFonasaContribution.setRequiredError(null);
		txtHours.setRequiredError(null);
		txtName.setRequiredError(null);
		txtSurname.setRequiredError(null);
		tabTaxes.setSelectedTab(0);		
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();		
		mainLayout.setImmediate(true);
		mainLayout.setWidth("1083px");
		mainLayout.setHeight("500px");
		
		// top-level component properties
		setWidth("1083px");
		setHeight("500px");
		
		// txtName
		txtName = new TextField();
		txtName.setCaption("Nombre");
		txtName.setImmediate(true);
		txtName.setWidth("390px");
		txtName.setHeight("-1px");
		txtName.setRequired(true);
		mainLayout.addComponent(txtName, "top:116.0px;left:0.0px;");
		
		// lblTitle
		lblTitle = new Label();
		lblTitle.setImmediate(true);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue(getBreadCrumbToShow());
		lblTitle.setStyleName("titleLabel");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");
		
		// txtSurname
		txtSurname = new TextField();
		txtSurname.setCaption("Apellido");
		txtSurname.setImmediate(true);
		txtSurname.setWidth("390px");
		txtSurname.setHeight("-1px");
		txtSurname.setRequired(true);
		mainLayout.addComponent(txtSurname, "top:176.0px;left:0.0px;");
		
		// txtAddress
		txtAddress = new TextField();
		txtAddress.setCaption("Domicilio");
		txtAddress.setImmediate(true);
		txtAddress.setWidth("390px");
		txtAddress.setHeight("-1px");
		mainLayout.addComponent(txtAddress, "top:236.0px;left:0.0px;");
		
		// txtEmail
		txtEmail = new TextField();
		txtEmail.setCaption("Correo");
		txtEmail.setImmediate(true);
		txtEmail.setWidth("390px");
		txtEmail.setHeight("-1px");
		txtEmail.addValidator(new EmailValidator("Formato inv�lido"));
		mainLayout.addComponent(txtEmail, "top:296.0px;left:0.0px;");
		
		// txtCellPhone
		txtCellPhone = new TextField();
		txtCellPhone.setCaption("Celular");
		txtCellPhone.setImmediate(true);
		txtCellPhone.setWidth("-1px");
		txtCellPhone.setHeight("-1px");
		mainLayout.addComponent(txtCellPhone, "top:357.0px;left:0.0px;");
		
		// optEmployeeType
		optEmployeeType = new OptionGroup();
		optEmployeeType.setCaption("Tipo");
		optEmployeeType.setImmediate(true);
		optEmployeeType.setWidth("-1px");
		optEmployeeType.setHeight("-1px");
		optEmployeeType.setRequired(true);
		mainLayout.addComponent(optEmployeeType, "top:357.0px;left:204.0px;");
		
		// tabTaxes
		tabTaxes = new TabSheet();
		tabTaxes.setCaption("Costos Empleado");
		tabTaxes.setImmediate(true);
		tabTaxes.setWidth("-1px");
		tabTaxes.setHeight("-1px");
		mainLayout.addComponent(tabTaxes, "top:116.0px;left:400.0px;");
		
		// btnCreate
		btnCreate = new Button();
		btnCreate.setCaption("Agregar");
		btnCreate.setImmediate(true);
		btnCreate.setWidth("-1px");
		btnCreate.setHeight("-1px");
		mainLayout.addComponent(btnCreate, "top:454.0px;left:0.0px;");
		
		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("-1px");
		btnCancel.setHeight("-1px");
		mainLayout.addComponent(btnCancel, "top:454.0px;left:120.0px;");
		
		return mainLayout;
	}
	
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if (RequestContext.getRequestContext() != null) {
			btnCreate.setEnabled(true);
			cleanInputs();	
		}
		
	}
	
}
