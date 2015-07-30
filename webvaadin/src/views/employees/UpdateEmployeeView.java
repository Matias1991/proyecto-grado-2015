package views.employees;

import java.util.Collection;
import java.util.Locale;

import servicelayer.service.ServiceWebStub.VOEmployed;
import servicelayer.service.ServiceWebStub.VOSalarySummary;
import utils.PopupWindow;
import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.DoubleRangeValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;

import controllers.EmployeeController;
import entities.Employee;
import entities.RequestContext;

public class UpdateEmployeeView extends BaseView {

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private TabSheet tabEmployee;
	@AutoGenerated
	private Label lblTitle;
	private Grid updateEmployeesGrid;
	private BeanItemContainer<Employee> beanContainer;
	private TextField txtName;
	private TextField txtSurname;
	private TextField txtEmail;
	private TextField txtAddress;
	private TextField txtCellphone;
	private TextField txtNominalSalary;
	private TextField txtTickets;
	private TextField txtPercentagePersonalFonasaContribution;
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
	private Label lblMessage;
	private OptionGroup optEmployeeType;
	private Button btnModiffy;
	private Button btnEstimate;
	private Button btnCancel;

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public UpdateEmployeeView() {
		buildMainLayout();
		buildTabSeet();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		lblMessage = new Label("");
		mainLayout.addComponent(lblMessage, "top:80.0px;left:0.0px;");		
		
		btnEstimate.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if (validateEstimateEmployee()){										
					VOSalarySummary estimateSalarySummary = new VOSalarySummary();					
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
					estimateSalarySummary.setPercentageTypeFONASA(((Double)txtPercentagePersonalFonasaContribution.getConvertedValue())/100);
					
					
									
					VOSalarySummary aux = EmployeeController.estimateEmployee(estimateSalarySummary);
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
						txtPercentagePersonalFonasaContribution.setConvertedValue(aux.getPercentageTypeFONASA()*100);
						
						changeReadOnlyState(true);
						tabEmployee.setSelectedTab(1);
					}else{					
						PopupWindow notification = new PopupWindow("ERROR", "No se pudo estimar");			
					}
					
				}
				
			}
		});
		
		btnModiffy.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				
				if(validateUpdateEmployee()){
					VOEmployed updateEmployee = new VOEmployed();
					updateEmployee.setName(txtName.getValue());
					updateEmployee.setLastName(txtSurname.getValue());
					updateEmployee.setAddress(txtAddress.getValue());
					updateEmployee.setEmail(txtEmail.getValue());
					updateEmployee.setCellPhone(txtCellphone.getValue());
					if(optEmployeeType.getValue().equals("Empleado")){
						updateEmployee.setEmployedType(1);
					}else{
						updateEmployee.setEmployedType(2);
					}
					
					VOSalarySummary updateSalarySummary = new VOSalarySummary();
					updateSalarySummary.setNominalSalary((Double)txtNominalSalary.getConvertedValue());
					updateSalarySummary.setTickets((Double)txtTickets.getConvertedValue());					
					if(txtRet.getConvertedValue() == null){
						txtRet.setConvertedValue(0.0);
					}					
					updateSalarySummary.setRET((Double)txtRet.getConvertedValue());
					if(txtIrpf.getConvertedValue()== null){
						txtIrpf.setConvertedValue(0.0);
					}
					updateSalarySummary.setIRPF((Double)txtIrpf.getConvertedValue());		
					if(txtBse.getConvertedValue() == null){
						txtBse.setConvertedValue(0.0);
					}
					updateSalarySummary.setBSE((Double)txtBse.getConvertedValue());
					updateSalarySummary.setHours((Integer)txtHours.getConvertedValue());
					if(txtCostSaleHour.getConvertedValue() == null){
						txtCostSaleHour.setConvertedValue(0.0);
					}					
					updateSalarySummary.setCostSaleHour((Double)txtCostSaleHour.getConvertedValue());
					updateSalarySummary.setPercentageTypeFONASA(((Double)txtPercentagePersonalFonasaContribution.getConvertedValue())/100);
					
					updateEmployee.setVOSalarySummary(updateSalarySummary);
				
					;
					if(EmployeeController.UpdateEmployee(beanContainer.getItem(updateEmployeesGrid.getSelectedRow()).getBean().getId(), updateEmployee)){
						PopupWindow popup = new PopupWindow("AVISO", "Empleado actualizado correctamente");
						updateEmployeesGrid.select(null);						
						mainLayout.removeComponent(updateEmployeesGrid);					
						buildGrid();
						tabEmployee.setSelectedTab(0);
						tabEmployee.setVisible(false);
						btnModiffy.setEnabled(false);								
					}
				}				
			}
		});
		
		btnCancel.addClickListener(new Button.ClickListener() {
			
			@Override
			public void buttonClick(ClickEvent event) {
				// TODO Auto-generated method stub
				getUI().getNavigator().navigateTo(WebvaadinUI.MAINMENU);				
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
		if(!txtPercentagePersonalFonasaContribution.isValid()){
			result = false;
			txtPercentagePersonalFonasaContribution.setRequiredError("Es requerido");
			txtPercentagePersonalFonasaContribution.setConversionError("Debe ser num�rico entre 0 y 100");			
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
	
	public boolean validateUpdateEmployee(){
		boolean validate = validateEstimateEmployee();
		
		if(!txtName.isValid()){
			validate = false;
			txtName.setRequiredError("Es requerido");
		}
		if(!txtSurname.isValid()){
			validate = false;
			txtSurname.setRequiredError("Es requerido");
		}		
		
		return validate;		
	}
	
	public void buildTabSeet(){
		// txtName
		txtName = new TextField();
		txtName.setCaption("Nombre");
		txtName.setImmediate(true);
		txtName.setWidth("390px");
		txtName.setHeight("-1px");
		txtName.setRequired(true);
		
		// txtSurname
		txtSurname = new TextField();
		txtSurname.setCaption("Apellido");
		txtSurname.setImmediate(true);
		txtSurname.setWidth("390px");
		txtSurname.setHeight("-1px");
		txtSurname.setRequired(true);
		
		// Email
		txtEmail = new TextField();
		txtEmail.setCaption("Correo electr�nico");
		txtEmail.setImmediate(false);
		txtEmail.setWidth("390px");
		txtEmail.setHeight("-1px");
		
		// Direccion
		txtAddress = new TextField();
		txtAddress.setCaption("Direcci�n");
		txtAddress.setImmediate(false);
		txtAddress.setWidth("390px");
		txtAddress.setHeight("-1px");
		
		//Celular
		txtCellphone = new TextField();
		txtCellphone.setCaption("Celular");
		txtCellphone.setImmediate(false);
		txtCellphone.setWidth("-1px");
		txtCellphone.setHeight("-1px");

		// Sueldo Nominal
		txtNominalSalary = new TextField();
		txtNominalSalary.setCaption("Sueldo Nominal");
		txtNominalSalary.setImmediate(false);
		txtNominalSalary.setWidth("-1px");
		txtNominalSalary.setHeight("-1px");
		txtNominalSalary.setNullRepresentation("");	
		txtNominalSalary.setConverter(new StringToDoubleConverter());

		// Tickets
		txtTickets = new TextField();
		txtTickets.setCaption("Tickets");
		txtTickets.setImmediate(false);
		txtTickets.setWidth("-1px");
		txtTickets.setHeight("-1px");
		txtTickets.setNullRepresentation("");
		txtTickets.setConverter(new StringToDoubleConverter());

		// RET
		txtRet = new TextField();
		txtRet.setCaption("RET");
		txtRet.setImmediate(false);
		txtRet.setWidth("-1px");
		txtRet.setHeight("-1px");
		txtRet.setNullRepresentation("");
		txtRet.setConverter(new StringToDoubleConverter());

		// IRPF
		txtIrpf = new TextField();
		txtIrpf.setCaption("IRPF");
		txtIrpf.setImmediate(false);
		txtIrpf.setWidth("-1px");
		txtIrpf.setHeight("-1px");
		txtIrpf.setNullRepresentation("");
		txtIrpf.setConverter(new StringToDoubleConverter());

		// BSE
		txtBse = new TextField();
		txtBse.setCaption("BSE");
		txtBse.setImmediate(false);
		txtBse.setWidth("-1px");
		txtBse.setHeight("-1px");
		txtBse.setNullRepresentation("");
		txtBse.setConverter(new StringToDoubleConverter());

		// Horas mensuales
		txtHours = new TextField();
		txtHours.setCaption("Cantidad Horas Mensual");
		txtHours.setImmediate(false);
		txtHours.setWidth("-1px");
		txtHours.setHeight("-1px");
		txtHours.setNullRepresentation("");
		txtHours.setConverter(new StringToIntegerConverter());

		// Precio de venta por hora
		txtCostSaleHour = new TextField();
		txtCostSaleHour.setCaption("Hora Venta");
		txtCostSaleHour.setImmediate(false);
		txtCostSaleHour.setWidth("-1px");
		txtCostSaleHour.setHeight("-1px");
		txtCostSaleHour.setNullRepresentation("");
		txtCostSaleHour.setConverter(new StringToDoubleConverter());

		// Porcentaje aporte FONASA Personal
		txtPercentagePersonalFonasaContribution = new TextField();
		txtPercentagePersonalFonasaContribution
				.setCaption("% Aporte FONASA Personal");
		txtPercentagePersonalFonasaContribution.setImmediate(false);
		txtPercentagePersonalFonasaContribution.setWidth("-1px");
		txtPercentagePersonalFonasaContribution.setHeight("-1px");
		txtPercentagePersonalFonasaContribution.setNullRepresentation("");
		txtPercentagePersonalFonasaContribution.setConverter(new StringToDoubleConverter());

		// Aporte Jubilatorio Personal
		txtPersonalRetirementContribution = new TextField();
		txtPersonalRetirementContribution
				.setCaption("Aporte Jubilatorio Personal");
		txtPersonalRetirementContribution.setImmediate(false);
		txtPersonalRetirementContribution.setWidth("-1px");
		txtPersonalRetirementContribution.setHeight("-1px");
		txtPersonalRetirementContribution.setNullRepresentation("");
		txtPersonalRetirementContribution.setConverter(new StringToDoubleConverter());

		// Aporte Jubilatorio Patronal
		txtEmployerRetirementContribution = new TextField();
		txtEmployerRetirementContribution
				.setCaption("Aporte Jubilatorio Patronal");
		txtEmployerRetirementContribution.setImmediate(false);
		txtEmployerRetirementContribution.setWidth("-1px");
		txtEmployerRetirementContribution.setHeight("-1px");
		txtEmployerRetirementContribution.setNullRepresentation("");
		txtEmployerRetirementContribution.setConverter(new StringToDoubleConverter());

		// Aporte FRL Personal
		txtPersonalFrlContribution = new TextField();
		txtPersonalFrlContribution.setCaption("Aporte FRL Personal");
		txtPersonalFrlContribution.setImmediate(false);
		txtPersonalFrlContribution.setWidth("-1px");
		txtPersonalFrlContribution.setHeight("-1px");
		txtPersonalFrlContribution.setNullRepresentation("");
		txtPersonalFrlContribution.setConverter(new StringToDoubleConverter());

		// Aporte FRL Patronal
		txtEmployerFrlContribution = new TextField();
		txtEmployerFrlContribution.setCaption("Aporte FRL Patronal");
		txtEmployerFrlContribution.setImmediate(false);
		txtEmployerFrlContribution.setWidth("-1px");
		txtEmployerFrlContribution.setHeight("-1px");
		txtEmployerFrlContribution.setNullRepresentation("");
		txtEmployerFrlContribution.setConverter(new StringToDoubleConverter());

		// Aporte FONASA Personal
		txtPersonalFonasaContribution = new TextField();
		txtPersonalFonasaContribution.setCaption("Aporte FONASA Personal");
		txtPersonalFonasaContribution.setImmediate(false);
		txtPersonalFonasaContribution.setWidth("-1px");
		txtPersonalFonasaContribution.setHeight("-1px");
		txtPersonalFonasaContribution.setNullRepresentation("");
		txtPersonalFonasaContribution.setConverter(new StringToDoubleConverter());

		// Aporte FONASA Patronal
		txtEmployerFonasaContribution = new TextField();
		txtEmployerFonasaContribution.setCaption("Aporte FONASA Patronal");
		txtEmployerFonasaContribution.setImmediate(false);
		txtEmployerFonasaContribution.setWidth("-1px");
		txtEmployerFonasaContribution.setHeight("-1px");
		txtEmployerFonasaContribution.setNullRepresentation("");
		txtEmployerFonasaContribution.setConverter(new StringToDoubleConverter());

		// Tickets Patronal
		txtTicketsEmployer = new TextField();
		txtTicketsEmployer.setCaption("Tickets Patronal");
		txtTicketsEmployer.setImmediate(false);
		txtTicketsEmployer.setWidth("-1px");
		txtTicketsEmployer.setHeight("-1px");
		txtTicketsEmployer.setNullRepresentation("");
		txtTicketsEmployer.setConverter(new StringToDoubleConverter());

		// Total Descuentos
		txtTotalDiscounts = new TextField();
		txtTotalDiscounts.setCaption("Total Descuentos");
		txtTotalDiscounts.setImmediate(false);
		txtTotalDiscounts.setWidth("-1px");
		txtTotalDiscounts.setHeight("-1px");
		txtTotalDiscounts.setNullRepresentation("");
		txtTotalDiscounts.setConverter(new StringToDoubleConverter());

		// Total Aportes Patronales
		txtTotalEmployersContribution = new TextField();
		txtTotalEmployersContribution.setCaption("Total Aportes Patronales");
		txtTotalEmployersContribution.setImmediate(false);
		txtTotalEmployersContribution.setWidth("-1px");
		txtTotalEmployersContribution.setHeight("-1px");
		txtTotalEmployersContribution.setNullRepresentation("");
		txtTotalEmployersContribution.setConverter(new StringToDoubleConverter());

		// Nominal Sin Aportes
		txtNominalWithoutContribution = new TextField();
		txtNominalWithoutContribution.setCaption("Nominal Sin Aportes");
		txtNominalWithoutContribution.setImmediate(false);
		txtNominalWithoutContribution.setWidth("-1px");
		txtNominalWithoutContribution.setHeight("-1px");
		txtNominalWithoutContribution.setNullRepresentation("");
		txtNominalWithoutContribution.setConverter(new StringToDoubleConverter());

		// Prevision Despido
		txtDismisalPrevension = new TextField();
		txtDismisalPrevension.setCaption("Prevision Despido");
		txtDismisalPrevension.setImmediate(false);
		txtDismisalPrevension.setWidth("-1px");
		txtDismisalPrevension.setHeight("-1px");
		txtDismisalPrevension.setNullRepresentation("");
		txtDismisalPrevension.setConverter(new StringToDoubleConverter());

		// Incidencia Sueldo
		txtIncidenceSalary = new TextField();
		txtIncidenceSalary.setCaption("Incidencia Sueldo");
		txtIncidenceSalary.setImmediate(false);
		txtIncidenceSalary.setWidth("-1px");
		txtIncidenceSalary.setHeight("-1px");
		txtIncidenceSalary.setNullRepresentation("");
		txtIncidenceSalary.setConverter(new StringToDoubleConverter());

		// Sueldos a Pagar
		txtSalaryToPay = new TextField();
		txtSalaryToPay.setCaption("Sueldos A Pagar");
		txtSalaryToPay.setImmediate(false);
		txtSalaryToPay.setWidth("-1px");
		txtSalaryToPay.setHeight("-1px");
		txtSalaryToPay.setNullRepresentation("");
		txtSalaryToPay.setConverter(new StringToDoubleConverter());

		// Costo Mensual
		txtCostMonth = new TextField();
		txtCostMonth.setCaption("Costo Mensual");
		txtCostMonth.setImmediate(false);
		txtCostMonth.setWidth("-1px");
		txtCostMonth.setHeight("-1px");
		txtCostMonth.setNullRepresentation("");
		txtCostMonth.setConverter(new StringToDoubleConverter());

		// Costo Hora Meerkat
		txtCostRealHour = new TextField();
		txtCostRealHour.setCaption("Costo Hora Meerkat");
		txtCostRealHour.setImmediate(false);
		txtCostRealHour.setWidth("-1px");
		txtCostRealHour.setHeight("-1px");
		txtCostRealHour.setNullRepresentation("");
		txtCostRealHour.setConverter(new StringToDoubleConverter());
		
		//Tipo empleado
		optEmployeeType = new OptionGroup();
		optEmployeeType.addItems("Empleado", "Socio");
		optEmployeeType.setCaption("Tipo");
		optEmployeeType.setImmediate(true);
		optEmployeeType.setWidth("-1px");
		optEmployeeType.setHeight("-1px");
		optEmployeeType.setRequired(true);
		
		// Boton Estimar
		btnEstimate = new Button();
		btnEstimate.setCaption("Estimar");
		btnEstimate.setImmediate(true);
		btnEstimate.setWidth("-1px");
		btnEstimate.setHeight("-1px");		

		// TAB 1
		GridLayout tab1 = new GridLayout(2, 5);
		tab1.setSpacing(true);
		tab1.addComponent(txtName,0,0,1,0);
		tab1.addComponent(txtSurname,0,1,1,1);
		tab1.addComponent(txtAddress, 0, 2,1,2);
		tab1.addComponent(txtEmail, 0, 3,1,3);
		tab1.addComponent(txtCellphone,0,4);
		tab1.addComponent(optEmployeeType,1,4);
		tabEmployee.addTab(tab1, "Datos personales");
		
		// TAB 2
		GridLayout tab2 = new GridLayout(2, 5);
		tab2.setSpacing(true);
		tab2.addComponent(txtNominalSalary, 0, 0);
		tab2.addComponent(txtTickets, 1, 0);
		tab2.addComponent(txtPercentagePersonalFonasaContribution, 0, 1);
		tab2.addComponent(txtRet, 1, 1);
		tab2.addComponent(txtIrpf, 0, 2);
		tab2.addComponent(txtBse, 1, 2);
		tab2.addComponent(txtHours, 0, 3);
		tab2.addComponent(txtCostSaleHour, 1, 3);
		tab2.addComponent(btnEstimate,0,4);
		tabEmployee.addTab(tab2, "Costos 1");
		
		//TAB 3
		GridLayout tab3 = new GridLayout(2,4);
		tab3.setSpacing(true);
		tab3.addComponent(txtPersonalRetirementContribution,0,0);
		tab3.addComponent(txtEmployerRetirementContribution,1,0);
		tab3.addComponent(txtPersonalFrlContribution,0,1);
		tab3.addComponent(txtEmployerFrlContribution,1,1);
		tab3.addComponent(txtPersonalFonasaContribution,0,2);
		tab3.addComponent(txtEmployerFonasaContribution,1,2);
		tab3.addComponent(txtTicketsEmployer,0,3);
		tab3.addComponent(txtTotalDiscounts,1,3);
		tabEmployee.addTab(tab3, "Costos 2");
		
		
		//TAB 4
		GridLayout tab4 = new GridLayout(2,4);
		tab4.setSpacing(true);
		tab4.addComponent(txtTotalEmployersContribution,0,0);
		tab4.addComponent(txtNominalWithoutContribution,1,0);		
		tab4.addComponent(txtIncidenceSalary,0,1);
		tab4.addComponent(txtDismisalPrevension,1,1);		
		tab4.addComponent(txtCostMonth,0,2);
		tab4.addComponent(txtCostRealHour,1,2);
		tab4.addComponent(txtSalaryToPay,0,3);		
		tabEmployee.addTab(tab4, "Costos 3");
		
		tabEmployee.setVisible(false);
	}
	
	public void buildGrid() {
		Collection<Employee> employees = EmployeeController.GetEmployees();

		if (employees != null && employees.size() > 0) {
			lblMessage.setValue("");
			beanContainer = new BeanItemContainer<Employee>(Employee.class,employees);

			updateEmployeesGrid = new Grid(beanContainer);			
			updateEmployeesGrid.removeColumn("id");
			updateEmployeesGrid.removeColumn("updatedDateTimeUTC");
			updateEmployeesGrid.removeColumn("createdDateTimeUTC");
			updateEmployeesGrid.removeColumn("employedType");
			updateEmployeesGrid.removeColumn("user");
			updateEmployeesGrid.removeColumn("voSalarySummary");
			updateEmployeesGrid.removeColumn("voSalarySummaries");
			updateEmployeesGrid.removeColumn("email");
			updateEmployeesGrid.removeColumn("address");
			updateEmployeesGrid.removeColumn("cellPhone");

			updateEmployeesGrid.setColumnOrder("name", "lastName");

			updateEmployeesGrid.getColumn("name").setHeaderCaption("Nombre");
			updateEmployeesGrid.getColumn("lastName").setHeaderCaption("Apellido");			
			updateEmployeesGrid.setWidth(373, Unit.PIXELS);
			updateEmployeesGrid.setHeight(310, Unit.PIXELS);
			updateEmployeesGrid.setSelectionMode(SelectionMode.SINGLE);
			updateEmployeesGrid.getSelectedRows().clear();
			mainLayout.addComponent(updateEmployeesGrid, "top:20%;left:0.0px;");
			
			updateEmployeesGrid.addSelectionListener(new SelectionListener() {

				@Override
				public void select(SelectionEvent event) {
					
					BeanItem<Employee> item = beanContainer.getItem(updateEmployeesGrid.getSelectedRow());
					if(item != null){
						Employee selectedEmployee = item.getBean();
						loadEmployee(selectedEmployee);
						tabEmployee.setVisible(true);
						btnModiffy.setEnabled(true);
					}else{
						tabEmployee.setVisible(false);
						btnModiffy.setEnabled(false);
					}
					
				}
				
			});
		} else {
			lblMessage.setValue("No hay empleados para mostrar");
			if(updateEmployeesGrid != null){
				updateEmployeesGrid.setVisible(false);
			}
		}

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
		
	}
	
	public void loadEmployee (Employee selectedEmployee){

		// seteo en readonly false, para poder cargar los datos
		changeReadOnlyState(false);
		
		VOSalarySummary voSalarySummary = selectedEmployee.getVoSalarySummary();	
		txtName.setValue(selectedEmployee.getName());
		txtSurname.setValue(selectedEmployee.getLastName());
		txtCellphone.setValue(selectedEmployee.getCellPhone());
		txtAddress.setValue(selectedEmployee.getAddress());
		txtEmail.setValue(selectedEmployee.getEmail());
		txtNominalSalary.setConvertedValue(voSalarySummary.getNominalSalary());	
		txtTickets.setConvertedValue(voSalarySummary.getTickets());	
		txtPercentagePersonalFonasaContribution.setConvertedValue(voSalarySummary.getPercentageTypeFONASA()*100);	
		txtRet.setConvertedValue(voSalarySummary.getRET());	
		txtIrpf.setConvertedValue(voSalarySummary.getIRPF());			
		txtBse.setConvertedValue(voSalarySummary.getBSE());
		txtHours.setConvertedValue(voSalarySummary.getHours());
		txtCostSaleHour.setConvertedValue(voSalarySummary.getCostSaleHour());
		txtPersonalRetirementContribution.setConvertedValue(voSalarySummary.getPersonalRetirementContribution());
		txtEmployerRetirementContribution.setConvertedValue(voSalarySummary.getEmployersContributionsRetirement());
		txtPersonalFrlContribution.setConvertedValue(voSalarySummary.getPersonalFRLContribution());
		txtEmployerFrlContribution.setConvertedValue(voSalarySummary.getEmployersFRLContribution());
		txtPersonalFonasaContribution.setConvertedValue(voSalarySummary.getPersonalFONASAContribution());
		txtEmployerFonasaContribution.setConvertedValue(voSalarySummary.getEmployersFONASAContribution());
		txtTicketsEmployer.setConvertedValue(voSalarySummary.getTicketsEmployers());
		txtTotalDiscounts.setConvertedValue(voSalarySummary.getTotalDiscounts());
		txtTotalEmployersContribution.setConvertedValue(voSalarySummary.getTotalEmployerContributions());
		txtNominalWithoutContribution.setConvertedValue(voSalarySummary.getNominalWithoutContributions());
		txtDismisalPrevension.setConvertedValue(voSalarySummary.getDismissalPrevention());
		txtIncidenceSalary.setConvertedValue(voSalarySummary.getIncidenceSalary());
		txtSalaryToPay.setConvertedValue(voSalarySummary.getSalaryToPay());
		txtCostMonth.setConvertedValue(voSalarySummary.getCostMonth());
		txtCostRealHour.setConvertedValue(voSalarySummary.getCostRealHour());				
		optEmployeeType.select(selectedEmployee.getEmployedType());
		//seteo en readonly false los campos del empleado a mostrar
		changeReadOnlyState(true);
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if (RequestContext.getRequestContext() != null) {
			if(updateEmployeesGrid != null){
				mainLayout.removeComponent(updateEmployeesGrid);
			}
			buildGrid();
			tabEmployee.setVisible(false);
			btnModiffy.setEnabled(false);
		}

	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("1083px");
		mainLayout.setHeight("500px");

		// top-level component properties
		setWidth("1083px");
		setHeight("500px");

		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Modificar empleado");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");

		// tabEmployee
		tabEmployee = new TabSheet();
		tabEmployee.setCaption("Datos del empleado");
		tabEmployee.setImmediate(false);
		tabEmployee.setWidth("-1px");
		tabEmployee.setHeight("-1px");
		mainLayout.addComponent(tabEmployee, "top:80.0px;left:400.0px;");
		
		// btnCreate
		btnModiffy = new Button();
		btnModiffy.setCaption("Modificar");
		btnModiffy.setImmediate(true);
		btnModiffy.setWidth("-1px");
		btnModiffy.setHeight("-1px");
		mainLayout.addComponent(btnModiffy, "top:414.0px;left:0.0px;");
		
		//btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("-1px");
		btnCancel.setHeight("-1px");
		mainLayout.addComponent(btnCancel,"top:414.0px;left:120.0px;");

		return mainLayout;
	}

}