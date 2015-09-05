package views.charge;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.vaadin.shared.ui.label.ContentMode;
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
import controllers.ChargeController;
import entities.Bill;
import entities.Charge;
import entities.RequestContext;

public class CatalogChargesView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Label lblInfo;
	@AutoGenerated
	private Label lblTitle;
	private Grid billsGrid;
	private BeanItemContainer<Charge> beanContainer;
	private Label lblMessage;
	private static final long serialVersionUID = -6425680180232429909L;

	public CatalogChargesView() {

		super("Cobros", "Cat�logo cobros");

		buildMainLayout();
		setCompositionRoot(mainLayout);

		lblMessage = new Label("");
		mainLayout.addComponent(lblMessage, "top:80.0px;left:0.0px;");
	}

	public void buildGrid() {

		Collection<Charge> charges = ChargeController.getCharges();

		if (charges != null && charges.size() > 0) {

			if (billsGrid != null) {
				mainLayout.removeComponent(billsGrid);
			}

			lblMessage.setValue("");

			beanContainer = new BeanItemContainer<Charge>(Charge.class, charges);

			billsGrid = new Grid(beanContainer);
			billsGrid.removeColumn("id");
			billsGrid.removeColumn("billId");
			billsGrid.removeColumn("createdDateTimeUTC");
			billsGrid.removeColumn("amountPeso");
			billsGrid.removeColumn("amountDollar");
			billsGrid.removeColumn("isCurrencyDollar");
			billsGrid.removeColumn("typeExchange");
			billsGrid.setColumnOrder("number", "description", "amountToShow",
					"typeExchangeToShow",
					"createdDateTimeUTCToShow", "billCode", "billDescription");

			billsGrid.getColumn("number").setHeaderCaption("N� de recibo");
			billsGrid.getColumn("description").setHeaderCaption("Descripci�n cobro");
			billsGrid.getColumn("amountToShow").setHeaderCaption("Importe");
			billsGrid.getColumn("typeExchangeToShow").setHeaderCaption(
					"Tipo de cambio");
			billsGrid.getColumn("createdDateTimeUTCToShow").setHeaderCaption(
					"Fecha de creaci�n");
			billsGrid.getColumn("billCode").setHeaderCaption("Codigo de factura");
			billsGrid.getColumn("billDescription").setHeaderCaption("Descripci�n factura");
			billsGrid.setWidth(100, Unit.PERCENTAGE);
			billsGrid.setHeight(100, Unit.PERCENTAGE);
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
			
			lblMessage
					.setValue("No hay cobros para mostrar");
		}
	}

	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if (RequestContext.getRequestContext() != null) {
			// Compruebo si el usuario es de tipo socio
			if (RequestContext.getRequestContext().getUserType() != 2) {
				getUI().getNavigator().navigateTo(WebvaadinUI.MAINMENU);
			}

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

		return mainLayout;
	}
}