package views.bill;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.vaadin.dialogs.ConfirmDialog;

import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
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
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.Grid.MultiSelectionModel;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;

import controllers.BillController;
import entities.Bill;
import entities.RequestContext;

public class DeleteBillsView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button btnDelete;
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
	private static final long serialVersionUID = -6425680180232429909L;

	@SuppressWarnings("deprecation")
	public DeleteBillsView() {
		
		super("Facturas", "Eliminar facturas");
		
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
		
		btnDelete.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				btnDelete.setEnabled(false);

				ConfirmDialog.show(WebvaadinUI.getCurrent(), "Confirmaci�n",
						"�Desea eliminar las facturas seleccionadas?", "Si", "No",
						new ConfirmDialog.Listener() {

							private static final long serialVersionUID = 1L;

							@Override
							public void onClose(ConfirmDialog confirm) {
								if (confirm.isConfirmed()) {
									
									int [] ids = new int[billsGrid.getSelectedRows().size()];
									int i=0;
									for (Object itemId: billsGrid.getSelectedRows())
									{
										BeanItem<Bill> item = beanContainer.getItem(itemId);
										ids[i] = item.getBean().getId();
										i++;
									}
									
									BillController.deleteBills(ids);

									btnDelete.setEnabled(false);
									if(billsGrid != null){
										mainLayout.removeComponent(billsGrid);
									}
									buildGrid();
								}

							}
						});
				btnDelete.setEnabled(true);
			}
		});
	}

	void builInputs()
	{
		popupDateFieldFrom.setValue(new Date());
		popupDateFieldFrom.setDateFormat("MM-yyyy");
		popupDateFieldFrom.setResolution(Resolution.MONTH);
		
		popupDateFieldTo.setValue(new Date());
		popupDateFieldTo.setDateFormat("MM-yyyy");
		popupDateFieldTo.setResolution(Resolution.MONTH);
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
			
			btnDelete.setVisible(true);
			
			beanContainer = new BeanItemContainer<Bill>(Bill.class,bills);
					
			billsGrid = new Grid(beanContainer);
			billsGrid.removeColumn("id");
			billsGrid.removeColumn("projectId");
			billsGrid.removeColumn("appliedDateTimeUTC");
			billsGrid.removeColumn("amountPeso");
			billsGrid.removeColumn("amountDollar");
			billsGrid.removeColumn("isCurrencyDollar");
			billsGrid.removeColumn("typeExchange");
			billsGrid.setColumnOrder("code", "description", "amountToShow", "typeExchangeToShow", "appliedDateTimeUTCToShow", "projectName");
	
			billsGrid.getColumn("code").setHeaderCaption("C�digo");
			billsGrid.getColumn("description").setHeaderCaption("Descripci�n");
			billsGrid.getColumn("amountToShow").setHeaderCaption("Monto");
			billsGrid.getColumn("typeExchangeToShow").setHeaderCaption("Tipo de cambio");
			billsGrid.getColumn("appliedDateTimeUTCToShow").setHeaderCaption("Mes");
			billsGrid.getColumn("projectName").setHeaderCaption("Proyecto");
			billsGrid.setWidth(100, Unit.PERCENTAGE);
			billsGrid.setHeight(100, Unit.PERCENTAGE);
			billsGrid.setSelectionMode(SelectionMode.MULTI);
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
			
			billsGrid.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void select(SelectionEvent event) {
					btnDelete.setEnabled(billsGrid.getSelectedRows().size() > 0);
				}
			});
			
			mainLayout.addComponent(billsGrid, "top:35%;left:0px;");
		}
		else
		{
			if(billsGrid != null)
			{
				billsGrid.setVisible(false);
			}
			
			btnDelete.setVisible(false);
			
			String strDateFrom = new SimpleDateFormat("MM-yyyy").format(popupDateFieldFrom.getValue());
			String strDateTo = new SimpleDateFormat("MM-yyyy").format(popupDateFieldTo.getValue());
			
			lblMessage.setValue("No hay facturas para mostrar entre el rango fechas " + strDateFrom  + " - " + strDateTo);
		}
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
		setHeight("501px");
		
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
		
		// btnDelete
		btnDelete = new Button();
		btnDelete.setCaption("Eliminar");
		btnDelete.setEnabled(false);
		btnDelete.setImmediate(true);
		btnDelete.setWidth("-1px");
		btnDelete.setHeight("-1px");
		btnDelete.setTabIndex(3);
		mainLayout.addComponent(btnDelete, "top:120.0px;right:0px;");
		
		return mainLayout;
	}
}