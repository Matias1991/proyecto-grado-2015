package views.employees;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jdt.core.compiler.BuildContext;

import servicelayer.service.ServiceWebStub.VOSalarySummary;
import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;

import controllers.EmployeeController;
import entities.Category;
import entities.Employee;
import entities.RequestContext;
import entities.SalarySummary;
import entities.SalarySummaryVersion;

public class CatalogEmployeesView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private TabSheet tabEmployee;
	@AutoGenerated
	private Label lblTitle;
	private Grid catalogEmployeesGrid;
	private BeanItemContainer<Employee> beanContainer;
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
	private TextField txtIncidenceTickets;
	private Label lblMessage;
	private OptionGroup optEmployeeType;
	private ComboBox cboVersion;

	public CatalogEmployeesView() {
		buildMainLayout();
		buildTabSeet();
		setCompositionRoot(mainLayout);
		
		lblMessage = new Label("");
		mainLayout.addComponent(lblMessage, "top:80.0px;left:0.0px;");
		
		
		
		cboVersion.addValueChangeListener(new  ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				if(cboVersion.getValue() != null){
					BeanItem<Employee> item = beanContainer.getItem(catalogEmployeesGrid.getSelectedRow());
					if(item != null){						
						SalarySummary versionEmployees = EmployeeController.GetVersionEmployee(item.getBean().getId(), (Integer)cboVersion.getValue());
																
						loadEmployee(item.getBean(),versionEmployees.toVOSalarySummary());
												
						tabEmployee.setVisible(false);
					}else{
						tabEmployee.setVisible(false);
						cboVersion.removeAllItems();
					}
					tabEmployee.setVisible(true);
				}else{
					tabEmployee.setVisible(false);
				}
			}
		});
		
		

	}
		
	public void buildTabSeet(){
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
		txtCostSaleHour.setCaption("Costo hora Venta");
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

		// TAB 1
		GridLayout tab1 = new GridLayout(2, 5);
		tab1.setSpacing(true);
		tab1.addComponent(txtAddress, 0, 0,1,0);
		tab1.addComponent(txtEmail, 0, 1,1,1);
		tab1.addComponent(txtCellphone,0,2);
		tab1.addComponent(optEmployeeType,1,2);
		tabEmployee.addTab(tab1, "Datos personales");
		
		// TAB 2
		GridLayout tab2 = new GridLayout(2, 5);
		tab2.setSpacing(true);
		tab2.addComponent(txtNominalSalary, 0, 0);
		tab2.addComponent(txtTickets, 1, 0);
		tab2.addComponent(txtPercentagePersonalFonasaContribution, 0, 1);
		tab2.addComponent(txtHours, 1, 1);
		tab2.addComponent(txtIrpf, 0, 2);
		tab2.addComponent(txtBse, 1, 2);
		tab2.addComponent(txtRet, 0, 3);
		tab2.addComponent(txtCostSaleHour, 1, 3);
		tabEmployee.addTab(tab2, "Costos 1");
		
		//TAB 2
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
		
		
		//tab4
		GridLayout tab4 = new GridLayout(2,4);
		tab4.setSpacing(true);
		tab4.addComponent(txtTotalEmployersContribution,0,0);
		tab4.addComponent(txtNominalWithoutContribution,1,0);		
		tab4.addComponent(txtIncidenceSalary,0,1);
		tab4.addComponent(txtIncidenceTickets,1,1);
		tab4.addComponent(txtDismisalPrevension,0,2);		
		tab4.addComponent(txtCostMonth,1,2);
		tab4.addComponent(txtCostRealHour,0,3);
		tab4.addComponent(txtSalaryToPay,1,3);	
		tabEmployee.addTab(tab4, "Costos 3");
		
		tabEmployee.setVisible(false);
	}
	
	@SuppressWarnings("serial")
	public void buildGrid() {
		Collection<Employee> employees = EmployeeController.GetEmployees();

		if (employees != null && employees.size() > 0) {
			lblMessage.setValue("");			
			beanContainer = new BeanItemContainer<Employee>(Employee.class,employees);

			catalogEmployeesGrid = new Grid(beanContainer);
			catalogEmployeesGrid.removeColumn("id");
			catalogEmployeesGrid.removeColumn("updatedDateTimeUTC");
			catalogEmployeesGrid.removeColumn("createdDateTimeUTC");
			catalogEmployeesGrid.removeColumn("employedType");
			catalogEmployeesGrid.removeColumn("user");
			catalogEmployeesGrid.removeColumn("salarySummary");
			catalogEmployeesGrid.removeColumn("salarySummaries");
			catalogEmployeesGrid.removeColumn("email");
			catalogEmployeesGrid.removeColumn("address");
			catalogEmployeesGrid.removeColumn("cellPhone");

			catalogEmployeesGrid.setColumnOrder("name", "lastName");

			catalogEmployeesGrid.getColumn("name").setWidth(192.05);
			catalogEmployeesGrid.getColumn("name").setHeaderCaption("Nombre");
			catalogEmployeesGrid.getColumn("lastName").setHeaderCaption("Apellido");			
			catalogEmployeesGrid.setWidth(373, Unit.PIXELS);
			catalogEmployeesGrid.setHeight(310, Unit.PIXELS);
			catalogEmployeesGrid.setSelectionMode(SelectionMode.SINGLE);
			catalogEmployeesGrid.getSelectedRows().clear();
			catalogEmployeesGrid.select(employees.iterator().next());
			buildVersion(employees.iterator().next().getId());
			
			// Filtros
			HeaderRow filterRow = catalogEmployeesGrid.appendHeaderRow();
			
			for (final Object pid : catalogEmployeesGrid.getContainerDataSource()
					.getContainerPropertyIds()) {
				HeaderCell cell = filterRow.getCell(pid);
				if (cell != null) {
					TextField txtFilter = new TextField();
					txtFilter.setImmediate(true);
					txtFilter.setWidth("125px");
					txtFilter.setHeight("30px");
					txtFilter.setInputPrompt("Filtro");

					txtFilter.addTextChangeListener(new TextChangeListener() {
						@Override
						public void textChange(TextChangeEvent event) {
							String newValue = (String) event.getText();

							@SuppressWarnings("unchecked")
							BeanItemContainer<Category> container = ((BeanItemContainer<Category>) catalogEmployeesGrid
									.getContainerDataSource());

							container.removeContainerFilters(pid);
							if (null != newValue && !newValue.isEmpty()) {
								container.addContainerFilter(new SimpleStringFilter(
												pid, newValue, true, false));
							}
						}
					});
					cell.setComponent(txtFilter);
				}
			}
			
			mainLayout.addComponent(catalogEmployeesGrid, "top:34%;left:0.0px;");
			
			catalogEmployeesGrid.addSelectionListener(new SelectionListener() {

				@Override
				public void select(SelectionEvent event) {
					
					BeanItem<Employee> item = beanContainer.getItem(catalogEmployeesGrid.getSelectedRow());
					if(item != null){
						Employee selectedEmployee = item.getBean();						
						//loadEmployee(selectedEmployee);
						buildVersion(selectedEmployee.getId());						
					}else{
						tabEmployee.setVisible(false);
						cboVersion.removeAllItems();
					}
					
				}
				
			});
			
			
		} else {
			lblMessage.setValue("No hay empleados para mostrar");
			if(catalogEmployeesGrid != null){
				catalogEmployeesGrid.setVisible(false);
			}
		}

	}
	
	
	
	
	private void setReadOnlyTxt (boolean readOnly){
		txtEmail.setReadOnly(readOnly);
		txtAddress.setReadOnly(readOnly);
		txtNominalSalary.setReadOnly(readOnly);
		txtTickets.setReadOnly(readOnly);
		txtRet.setReadOnly(readOnly);
		txtIrpf.setReadOnly(readOnly);
		txtBse.setReadOnly(readOnly);
		txtHours.setReadOnly(readOnly);
		txtCostSaleHour.setReadOnly(readOnly);
		txtPercentagePersonalFonasaContribution.setReadOnly(readOnly);
		txtPersonalRetirementContribution.setReadOnly(readOnly);
		txtEmployerRetirementContribution.setReadOnly(readOnly);
		txtPersonalFrlContribution.setReadOnly(readOnly);
		txtEmployerFrlContribution.setReadOnly(readOnly);
		txtPersonalFonasaContribution.setReadOnly(readOnly);
		txtEmployerFonasaContribution.setReadOnly(readOnly);
		txtTicketsEmployer.setReadOnly(readOnly);
		txtTotalDiscounts.setReadOnly(readOnly);
		txtTotalEmployersContribution.setReadOnly(readOnly);
		txtNominalWithoutContribution.setReadOnly(readOnly);
		txtDismisalPrevension.setReadOnly(readOnly);
		txtIncidenceSalary.setReadOnly(readOnly);
		txtSalaryToPay.setReadOnly(readOnly);
		txtCostMonth.setReadOnly(readOnly);
		txtCostRealHour.setReadOnly(readOnly);	
		txtCellphone.setReadOnly(readOnly);
		txtIncidenceTickets.setReadOnly(readOnly);
		optEmployeeType.setReadOnly(readOnly);
	}
	
	public void loadEmployee (Employee selectedEmployee, VOSalarySummary voSalarySummary){

		// seteo en readonly false, para poder cargar los datos
		setReadOnlyTxt(false);
		
		//VOSalarySummary voSalarySummary = selectedEmployee.getVoSalarySummary();		
		txtAddress.setValue(selectedEmployee.getAddress());
		txtEmail.setValue(selectedEmployee.getEmail());
		txtCellphone.setValue(selectedEmployee.getCellPhone());
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
		txtIncidenceTickets.setConvertedValue(voSalarySummary.getIncidenceTickets());
		optEmployeeType.select(selectedEmployee.getEmployedType());
		//seteo en readonly false los campos del empleado a mostrar
		setReadOnlyTxt(true);
		
	}
	
	private void buildVersion(int employeeId){		
		Collection<SalarySummaryVersion> aux = EmployeeController.GetVersions(employeeId);
		
		cboVersion.removeAllItems();	
		for (SalarySummaryVersion salarySummaryVersion : aux) {
			cboVersion.addItem(salarySummaryVersion.getVersion());			
			cboVersion.setItemCaption(salarySummaryVersion.getVersion(), new SimpleDateFormat("dd-MM-yyyy").format(salarySummaryVersion.getCreatedDateTimeUTC()));
		}
		cboVersion.setValue(aux.iterator().next().getVersion());
		cboVersion.setNullSelectionItemId(new SalarySummaryVersion());
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if (RequestContext.getRequestContext() != null) {
			// Compruebo si el usuario es de tipo socio
			if(RequestContext.getRequestContext().getUserType() != 2){
				getUI().getNavigator().navigateTo(WebvaadinUI.MAINMENU);
			}
			if(catalogEmployeesGrid != null){
				mainLayout.removeComponent(catalogEmployeesGrid);
			}
			buildGrid();
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
		lblTitle.setValue("Cat�logo de empleados");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");

		// tabEmployee
		tabEmployee = new TabSheet();
		//tabEmployee.setCaption("Datos del empleado");
		tabEmployee.setImmediate(false);
		tabEmployee.setWidth("-1px");
		tabEmployee.setHeight("-1px");
		mainLayout.addComponent(tabEmployee, "top:160.0px;left:400.0px;");
		
		//cboVersion
		cboVersion = new ComboBox();		
		cboVersion.setImmediate(true);
		cboVersion.setWidth("194px");
		cboVersion.setHeight("30px");
		cboVersion.setCaption("Versi�n");
		cboVersion.setInputPrompt("Seleccione la fecha");
		mainLayout.addComponent(cboVersion, "top:110.0;left:0px");

		return mainLayout;
	}

}
