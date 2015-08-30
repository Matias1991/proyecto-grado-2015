package views.charge;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import utils.PopupWindow;
import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;

import controllers.BillController;
import controllers.ChargeController;
import controllers.ProjectController;
import entities.Bill;
import entities.Charge;
import entities.Project;
import entities.RequestContext;

public class CreateChargeView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private static final long serialVersionUID = 1L;
	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private Button btnCreate;
	@AutoGenerated
	private TextField txtTypeExchange;
	@AutoGenerated
	private TextField txtAmount;
	@AutoGenerated
	private TextField txtDescription;
	@AutoGenerated
	private TextField txtNumber;
	@AutoGenerated
	private PopupDateField popupDateFieldTo;
	@AutoGenerated
	private PopupDateField popupDateFieldFrom;
	@AutoGenerated
	private Label lblInfo;
	@AutoGenerated
	private Label lblTitle;
	private Grid billsGrid;
	private BeanItemContainer<Bill> beanContainer;
	private Label lblMessage;
	
	@SuppressWarnings("deprecation")
	public CreateChargeView() {
		
		super("Cobros", "Crear cobro");
		
		buildMainLayout();
		setCompositionRoot(mainLayout);

		builInputs();
		
		lblMessage = new Label("");
		mainLayout.addComponent(lblMessage, "top:183.0px;left:0.0px;");

		popupDateFieldFrom.addListener(new ValueChangeListener() {
		    private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				buildGrid();
			}
		});
		
		popupDateFieldTo.addListener(new ValueChangeListener() {
		    private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				buildGrid(); 
			}
		});
		
		btnCreate.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				btnCreate.setEnabled(false);
				btnCancel.setEnabled(false);
				txtNumber.setValidationVisible(true);
				txtDescription.setValidationVisible(true);
				txtAmount.setValidationVisible(true);
				txtTypeExchange.setValidationVisible(true);
				
				boolean valid = true;
				
				if(txtTypeExchange.isVisible() && !txtTypeExchange.isValid())
				{
					txtTypeExchange.setRequiredError("Es requerido");
					valid = false;
				}
				
				if(!txtAmount.isValid() || !txtDescription.isValid() || !txtNumber.isValid()){
					txtNumber.setRequiredError("Es requerido");
					txtAmount.setRequiredError("Es requerido");
					txtDescription.setRequiredError("Es requerido");
					txtAmount.setConversionError("Debe ser num�rico");
					valid = false;
				}
				
				if(valid)
				{
					Bill item = (Bill) billsGrid.getSelectedRow();
					Charge charge = new Charge();
					charge.setNumber(txtNumber.getValue());
					charge.setDescription(txtDescription.getValue());
					charge.setBillId(item.getId());
					
					if(txtTypeExchange.isVisible() && txtTypeExchange.getValue() != null)
					{
						charge.setAmountDollar((Double)(txtAmount.getConvertedValue()));
						charge.setTypeExchange((Double)(txtTypeExchange.getConvertedValue()));
						charge.setIsCurrencyDollar(true);
					}
					else
					{
						charge.setAmountPeso((Double)(txtAmount.getConvertedValue()));
						charge.setIsCurrencyDollar(false);
					}
					
					ChargeController.createCharge(charge);

					new PopupWindow("AVISO", "Cobro creado correctamente");
					
					ClearInputs();
					buildGrid();
				}
				
				btnCreate.setEnabled(true);
				btnCancel.setEnabled(true);
			}
		
		});
		
		btnCancel.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(WebvaadinUI.MAINMENU);
			}
		});

		
		// TODO add user code here
	}
	
	void builInputs()
	{
		popupDateFieldFrom.setValue(new Date());
		popupDateFieldFrom.setDateFormat("MM-yyyy");
		popupDateFieldFrom.setResolution(Resolution.MONTH);
		
		popupDateFieldTo.setValue(new Date());
		popupDateFieldTo.setDateFormat("MM-yyyy");
		popupDateFieldTo.setResolution(Resolution.MONTH);
		
		ClearInputs();
		
	}
	
	void ClearInputs()
	{
		txtNumber.clear();
		txtDescription.clear();
		txtAmount.clear();
		txtTypeExchange.clear();
		
		txtNumber.setValidationVisible(false);
		txtDescription.setValidationVisible(false);
		txtAmount.setValidationVisible(false);
		txtTypeExchange.setValidationVisible(false);
		
		txtTypeExchange.setVisible(false);
		
		txtAmount.setCaption("Importe");
	}
	
	void buildChargeCurrency(boolean isCurrencyDollar)
	{	
		if(isCurrencyDollar)
		{
			txtTypeExchange.setVisible(true);
			txtAmount.setCaption("Importe (U$S)");
		}
		else
		{
			txtTypeExchange.setVisible(false);
			txtAmount.setCaption("Importe ($)");
		}
	}
	
	public void buildGrid(){
		
		int projectId = 0;
		String code = null;
		
		Collection<Bill> bills = BillController.getBills(popupDateFieldFrom.getValue(), popupDateFieldTo.getValue(), projectId, code, false);
		
		if (bills != null && bills.size() > 0) {
			
			if (billsGrid != null) {
				mainLayout.removeComponent(billsGrid);
			}
			
			lblMessage.setValue("");
			
			beanContainer = new BeanItemContainer<Bill>(Bill.class,bills);
					
			billsGrid = new Grid(beanContainer);
			billsGrid.setCaption("Facturas");
			billsGrid.removeColumn("id");
			billsGrid.removeColumn("projectId");
			billsGrid.removeColumn("appliedDateTimeUTC");
			billsGrid.removeColumn("amountPeso");
			billsGrid.removeColumn("amountDollar");
			billsGrid.removeColumn("isCurrencyDollar");
			billsGrid.removeColumn("typeExchange");
			billsGrid.removeColumn("typeExchangeToShow");
			billsGrid.removeColumn("appliedDateTimeUTCToShow");
			billsGrid.removeColumn("projectName");
			billsGrid.setColumnOrder("code", "description", "amountToShow");
	
			billsGrid.getColumn("code").setHeaderCaption("C�digo");
			billsGrid.getColumn("description").setHeaderCaption("Descripci�n");
			billsGrid.getColumn("amountToShow").setHeaderCaption("Monto");
			billsGrid.setWidth(480, Unit.PIXELS);
			billsGrid.setHeight(285, Unit.PIXELS);
			billsGrid.setSelectionMode(SelectionMode.SINGLE);
			billsGrid.getSelectedRows().clear();
			
			HeaderRow filterRow = billsGrid.appendHeaderRow();
			for ( final Object pid: billsGrid.getContainerDataSource().getContainerPropertyIds()){
				HeaderCell cell = filterRow.getCell(pid);
				if(cell != null){
					TextField txtFilter = new TextField();
					txtFilter.setImmediate(true);
					txtFilter.setWidth("125px");
					txtFilter.setHeight("30px");
					txtFilter.setInputPrompt("Filtro");
					
					txtFilter.addTextChangeListener(new TextChangeListener() {	 
						private static final long serialVersionUID = 1L;

					@Override
					  public void textChange(TextChangeEvent event) {
					   String newValue = (String) event.getText();
					  
					   @SuppressWarnings("unchecked")
					   BeanItemContainer<Bill> container = ((BeanItemContainer<Bill>) billsGrid.getContainerDataSource());
					  
					   container.removeContainerFilters(pid);
					   if (null != newValue && !newValue.isEmpty()) {
						   container.addContainerFilter(new SimpleStringFilter(pid, newValue, true, false));
					   }					   
					  }	
					 });
					cell.setComponent(txtFilter);	
				}
			}
			
			mainLayout.addComponent(billsGrid, "top:38%;left:0px;");
			
			billsGrid.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void select(SelectionEvent event) {
					
					BeanItem<Bill> item = beanContainer.getItem(billsGrid.getSelectedRow());
					if(item != null)
					{
						setEnabledEditionInputs(true);
						setRequiredEditionInputs(true);
						buildChargeCurrency(item.getBean().getIsCurrencyDollar());
					}
					else
						setEnabledEditionInputs(false);
				}
				
			});
			
			setVisibleEditionInputs(true);
			setEnabledEditionInputs(false);
			setRequiredEditionInputs(false);
		}
		else
		{
			if(billsGrid != null)
			{
				billsGrid.setVisible(false);
			}
			
			String strDateFrom = new SimpleDateFormat("MM-yyyy").format(popupDateFieldFrom.getValue());
			String strDateTo = new SimpleDateFormat("MM-yyyy").format(popupDateFieldTo.getValue());

			lblMessage.setValue("No hay facturas para mostrar entre el rango fechas " + strDateFrom  + " - " + strDateTo);
			
			setVisibleEditionInputs(false);
		}
	}
	
	void setVisibleEditionInputs(boolean visible)
	{
		btnCreate.setVisible(visible);
		btnCancel.setVisible(visible);
		txtNumber.setVisible(visible);
		txtDescription.setVisible(visible);
		txtAmount.setVisible(visible);
	}
	
	void setEnabledEditionInputs(boolean enabled)
	{
		btnCreate.setEnabled(enabled);
		btnCancel.setEnabled(enabled);
		txtNumber.setEnabled(enabled);
		txtDescription.setEnabled(enabled);
		txtAmount.setEnabled(enabled);
	}
	
	void setRequiredEditionInputs(boolean required)
	{
		txtNumber.setRequired(required);
		txtDescription.setRequired(required);
		txtAmount.setRequired(required);
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if(RequestContext.getRequestContext() != null){
			// Compruebo si el usuario es de tipo socio
			if(RequestContext.getRequestContext().getUserType() != 2){
				getUI().getNavigator().navigateTo(WebvaadinUI.MAINMENU);
			}
			
			builInputs();
			buildGrid();
		}
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("880px");
		mainLayout.setHeight("501px");
		
		// top-level component properties
		setWidth("880px");
		setHeight("800px");
		
		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue(getBreadCrumbToShow());
		mainLayout.addComponent(lblTitle, "top:40.0px;left:0.0px;");
		
		// lblInfo
		lblInfo = new Label();
		lblInfo.setStyleName("update-bill-lblInformation");
		lblInfo.setImmediate(false);
		lblInfo.setWidth("-1px");
		lblInfo.setHeight("-1px");
		lblInfo.setValue("Importante: Las facturas que se muestran no estan liquidadas aun y no tienen cobros asociados.");
		mainLayout.addComponent(lblInfo, "top:130.0px;left:270.0px;");
		
		// popupDateFieldFrom
		popupDateFieldFrom = new PopupDateField();
		popupDateFieldFrom.setCaption("Desde");
		popupDateFieldFrom.setImmediate(true);
		popupDateFieldFrom.setWidth("120px");
		popupDateFieldFrom.setHeight("-1px");
		popupDateFieldFrom.setTabIndex(1);
		popupDateFieldFrom.setRequired(true);
		mainLayout.addComponent(popupDateFieldFrom, "top:120.0px;left:0.0px;");
		
		// popupDateFieldTo
		popupDateFieldTo = new PopupDateField();
		popupDateFieldTo.setCaption("Hasta");
		popupDateFieldTo.setImmediate(true);
		popupDateFieldTo.setWidth("120px");
		popupDateFieldTo.setHeight("-1px");
		popupDateFieldTo.setTabIndex(2);
		popupDateFieldTo.setRequired(true);
		mainLayout.addComponent(popupDateFieldTo, "top:120.0px;left:140.0px;");
		
		// txtDescription
		txtNumber = new TextField();
		txtNumber.setCaption("N�mero de recibo");
		txtNumber.setImmediate(false);
		txtNumber.setWidth("300px");
		txtNumber.setHeight("-1px");
		txtNumber.setTabIndex(3);
		txtNumber.setRequired(true);
		mainLayout.addComponent(txtNumber, "top:210.0px;left:500.0px;");
				
		// txtDescription
		txtDescription = new TextField();
		txtDescription.setCaption("Descripci�n");
		txtDescription.setImmediate(false);
		txtDescription.setWidth("300px");
		txtDescription.setHeight("-1px");
		txtDescription.setTabIndex(3);
		txtDescription.setRequired(true);
		mainLayout.addComponent(txtDescription, "top:275.0px;left:500.0px;");
		
		// txtAmount
		txtAmount = new TextField();
		txtAmount.setCaption("Importe");
		txtAmount.setImmediate(true);
		txtAmount.setWidth("120px");
		txtAmount.setHeight("-1px");
		txtAmount.setTabIndex(5);
		txtAmount.setRequired(true);
		txtAmount.setNullRepresentation("");
		txtAmount.setConverter(new StringToDoubleConverter());
		mainLayout.addComponent(txtAmount, "top:340.0px;left:500.0px;");
		
		// txtTypeExchange
		txtTypeExchange = new TextField();
		txtTypeExchange.setCaption("Tipo de cambio");
		txtTypeExchange.setImmediate(true);
		txtTypeExchange.setVisible(false);
		txtTypeExchange.setWidth("120px");
		txtTypeExchange.setHeight("-1px");
		txtTypeExchange.setTabIndex(6);
		txtTypeExchange.setRequired(true);
		txtTypeExchange.setNullRepresentation("");
		txtTypeExchange.setConverter(new StringToDoubleConverter());
		mainLayout.addComponent(txtTypeExchange, "top:340.0px;left:630.0px;");
		
		// btnUpdate
		btnCreate = new Button();
		btnCreate.setCaption("Crear");
		btnCreate.setImmediate(false);
		btnCreate.setWidth("120px");
		btnCreate.setHeight("-1px");
		btnCreate.setTabIndex(9);
		mainLayout.addComponent(btnCreate, "top:400.0px;left:500px;");
		
		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(false);
		btnCancel.setWidth("120px");
		btnCancel.setHeight("-1px");
		btnCancel.setTabIndex(10);
		mainLayout.addComponent(btnCancel, "top:400.0px;left:630.0px;");
		
		return mainLayout;
	}

}