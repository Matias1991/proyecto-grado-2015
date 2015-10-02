package views.bill;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import views.BaseView;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;

import controllers.BillController;
import entities.Bill;
import entities.RequestContext;

public class CatalogBillsView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private CheckBox checkBoxIsLiquidated;
	@AutoGenerated
	private PopupDateField popupDateFieldTo;
	@AutoGenerated
	private PopupDateField popupDateFieldFrom;
	@AutoGenerated
	private Label lblTitle;
	private Grid billsGrid;
	private BeanItemContainer<Bill> beanContainer;
	private Label lblMessage;

	public CatalogBillsView() {

		super("Facturas", "Cat�logo de facturas");

		buildMainLayout();
		setCompositionRoot(mainLayout);

		builInputs();

		lblMessage = new Label("");
		mainLayout.addComponent(lblMessage, "top:183.0px;left:0.0px;");

		popupDateFieldFrom.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				buildGrid();
			}
		});

		popupDateFieldTo.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				buildGrid();
			}
		});

		checkBoxIsLiquidated.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = -6214146119924863403L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				buildGrid();
			}
		});
	}

	void builInputs() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		popupDateFieldFrom.setValue(cal.getTime());
		popupDateFieldFrom.setDateFormat("MM-yyyy");
		popupDateFieldFrom.setResolution(Resolution.MONTH);

		popupDateFieldTo.setValue(new Date());
		popupDateFieldTo.setDateFormat("MM-yyyy");
		popupDateFieldTo.setResolution(Resolution.MONTH);

		checkBoxIsLiquidated.setValue(true);
	}

	public void buildGrid() {

		Collection<Bill> bills = BillController.getAllBillsByFilters(
				popupDateFieldFrom.getValue(), popupDateFieldTo.getValue(),
				checkBoxIsLiquidated.getValue());

		if (bills != null && bills.size() > 0) {

			if (billsGrid != null) {
				mainLayout.removeComponent(billsGrid);
			}

			lblMessage.setValue("");

			beanContainer = new BeanItemContainer<Bill>(Bill.class, bills);

			billsGrid = new Grid(beanContainer);
			billsGrid.removeColumn("id");
			billsGrid.removeColumn("projectId");
			billsGrid.removeColumn("appliedDateTimeUTC");
			billsGrid.removeColumn("amountPeso");
			billsGrid.removeColumn("amountDollar");
			billsGrid.removeColumn("isCurrencyDollar");
			billsGrid.removeColumn("typeExchange");
			billsGrid.removeColumn("ivaType");
			billsGrid.setColumnOrder("code", "description", "amountToShow",
					"ivaTypeToShow", "totalAmountToShow", "typeExchangeToShow",
					"amountChargedToShow", "amountReceivableToShow",
					"appliedDateTimeUTCToShow", "projectName");

			billsGrid.getColumn("code").setHeaderCaption("C�digo");
			billsGrid.getColumn("description").setHeaderCaption("Descripci�n");
			billsGrid.getColumn("amountToShow").setHeaderCaption(
					"Importe sin IVA incl.");
			billsGrid.getColumn("ivaTypeToShow").setHeaderCaption("IVA");
			billsGrid.getColumn("totalAmountToShow").setHeaderCaption(
					"Importe IVA incl.");
			billsGrid.getColumn("typeExchangeToShow").setHeaderCaption(
					"Tipo de cambio");
			billsGrid.getColumn("amountChargedToShow").setHeaderCaption(
					"Importe cobrado");
			billsGrid.getColumn("amountReceivableToShow").setHeaderCaption(
					"Importe a cobrar");
			billsGrid.getColumn("appliedDateTimeUTCToShow").setHeaderCaption(
					"Correspondiente al mes");
			billsGrid.getColumn("projectName").setHeaderCaption("Proyecto");

			billsGrid.getColumn("description").setWidth(200);

			billsGrid.setWidth(100, Unit.PERCENTAGE);
			billsGrid.setHeight(500, Unit.PIXELS);
			billsGrid.setSelectionMode(SelectionMode.NONE);
			billsGrid.getSelectedRows().clear();

			HeaderRow filterRow = billsGrid.appendHeaderRow();
			for (final Object pid : billsGrid.getContainerDataSource()
					.getContainerPropertyIds()) {
				HeaderCell cell = filterRow.getCell(pid);
				if (cell != null) {
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
							BeanItemContainer<Bill> container = ((BeanItemContainer<Bill>) billsGrid
									.getContainerDataSource());

							container.removeContainerFilters(pid);
							if (null != newValue && !newValue.isEmpty()) {
								container
										.addContainerFilter(new SimpleStringFilter(
												pid, newValue, true, false));
							}
						}
					});
					cell.setComponent(txtFilter);
				}
			}

			mainLayout.addComponent(billsGrid, "top:20%;left:0px;");
		} else {
			if (billsGrid != null) {
				billsGrid.setVisible(false);
			}

			String strDateFrom = new SimpleDateFormat("MM-yyyy")
					.format(popupDateFieldFrom.getValue());
			String strDateTo = new SimpleDateFormat("MM-yyyy")
					.format(popupDateFieldTo.getValue());

			if (checkBoxIsLiquidated.getValue())
				lblMessage
						.setValue("No hay facturas liquidadas para mostrar entre el rango fechas "
								+ strDateFrom + " - " + strDateTo);
			else
				lblMessage
						.setValue("No hay facturas para mostrar entre el rango fechas "
								+ strDateFrom + " - " + strDateTo);
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if (RequestContext.getRequestContext() != null) {
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
		mainLayout.setHeight("880px");

		// top-level component properties
		setWidth("880px");
		setHeight("880px");

		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue(getBreadCrumbToShow());
		mainLayout.addComponent(lblTitle, "top:40.0px;left:0.0px;");

		// popupDateFieldFrom
		popupDateFieldFrom = new PopupDateField();
		popupDateFieldFrom.setCaption("Desde");
		popupDateFieldFrom.setImmediate(true);
		popupDateFieldFrom.setWidth("120px");
		popupDateFieldFrom.setHeight("-1px");
		popupDateFieldFrom.setRequired(true);
		mainLayout.addComponent(popupDateFieldFrom, "top:120.0px;left:0.0px;");

		// popupDateFieldTo
		popupDateFieldTo = new PopupDateField();
		popupDateFieldTo.setCaption("Hasta");
		popupDateFieldTo.setImmediate(true);
		popupDateFieldTo.setWidth("120px");
		popupDateFieldTo.setHeight("-1px");
		popupDateFieldTo.setRequired(true);
		mainLayout.addComponent(popupDateFieldTo, "top:120.0px;left:140.0px;");

		// checkBoxIsLiquidated
		checkBoxIsLiquidated = new CheckBox();
		checkBoxIsLiquidated.setCaption("Liquidado");
		checkBoxIsLiquidated.setImmediate(true);
		checkBoxIsLiquidated.setWidth("-1px");
		checkBoxIsLiquidated.setHeight("-1px");
		mainLayout.addComponent(checkBoxIsLiquidated,
				"top:128.0px;left:280.0px;");

		return mainLayout;
	}
}
