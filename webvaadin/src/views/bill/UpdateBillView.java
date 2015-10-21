package views.bill;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import utils.PopupWindow;
import views.BaseView;

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
import com.vaadin.shared.ui.label.ContentMode;
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
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import controllers.BillController;
import controllers.ProjectController;
import entities.Bill;
import entities.Constant;
import entities.Project;
import entities.RequestContext;

public class UpdateBillView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private static final long serialVersionUID = 1L;
	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private Button btnUpdate;
	@AutoGenerated
	private PopupDateField popupDateFieldAppliedDate;
	@AutoGenerated
	private ComboBox comboBoxProjects;
	@AutoGenerated
	private TextField txtTypeExchange;
	@AutoGenerated
	private TextField txtAmount;
	@AutoGenerated
	private TextArea txtDescription;
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
	private Collection<Project> projects;
	private ComboBox cboxIVA_Types;
	private TextField txtTotalAmount;

	public UpdateBillView() {

		super("Facturas", "Modificar facturas");

		buildMainLayout();
		setCompositionRoot(mainLayout);

		builInputs();

		lblMessage = new Label("");
		mainLayout.addComponent(lblMessage, "top:183.0px;left:0.0px;");

		comboBoxProjects.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (comboBoxProjects.getValue() != null) {
					int id = (int) comboBoxProjects.getValue();
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

		popupDateFieldFrom.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (popupDateFieldFrom.getValue() != null) {
					buildGrid();
				} else {
					popupDateFieldFrom.setRequiredError("Es requerido");
				}

			}
		});

		popupDateFieldTo.addValueChangeListener(new ValueChangeListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (popupDateFieldTo.getValue() != null) {
					buildGrid();
				} else {
					popupDateFieldTo.setRequiredError("Es requerido");
				}
			}
		});

		btnUpdate.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				btnUpdate.setEnabled(false);
				txtDescription.setValidationVisible(true);
				txtAmount.setValidationVisible(true);
				txtTypeExchange.setValidationVisible(true);

				boolean valid = true;

				if (txtTypeExchange.isVisible() && !txtTypeExchange.isValid()) {
					txtTypeExchange.setRequiredError("Es requerido");
					valid = false;
				}

				if (!txtAmount.isValid() || !txtDescription.isValid()
						|| !popupDateFieldAppliedDate.isValid()) {
					txtAmount.setRequiredError("Es requerido");
					txtDescription.setRequiredError("Es requerido");
					txtAmount.setConversionError("Debe ser num�rico");
					popupDateFieldAppliedDate.setRequiredError("Es requerido");
					valid = false;
				}

				if (valid) {
					Bill item = (Bill) billsGrid.getSelectedRow();
					Bill bill = new Bill();
					bill.setCode(item.getCode());
					bill.setDescription(txtDescription.getValue());
					bill.setProjectId(Integer.parseInt(comboBoxProjects
							.getValue().toString()));
					bill.setAppliedDateTimeUTC(popupDateFieldAppliedDate
							.getValue());
					bill.setId(item.getId());
					bill.setIvaType((int) cboxIVA_Types.getValue());

					if (txtTypeExchange.isVisible()
							&& txtTypeExchange.getValue() != null) {
						bill.setAmountDollar((Double) (txtAmount
								.getConvertedValue()));
						bill.setTypeExchange((Double) (txtTypeExchange
								.getConvertedValue()));
						bill.setIsCurrencyDollar(true);
					} else {
						bill.setAmountPeso((Double) (txtAmount
								.getConvertedValue()));
						bill.setIsCurrencyDollar(false);
					}

					Bill result = BillController.updateBill(item.getId(), bill);

					if (result != null) {
						new PopupWindow("AVISO",
								"Factura modificada correctamente");
						clearInputs();
						buildGrid();
					} else {
						btnUpdate.setEnabled(true);
					}
				} else {
					btnCancel.setEnabled(true);
				}
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

	Project getProjectById(int id) {
		for (Project p : projects) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}

	void buildBillCurrency(boolean isCurrencyDollar) {
		if (isCurrencyDollar) {
			txtTypeExchange.setVisible(true);
			txtAmount.setCaption("Importe sin IVA (U$S)");
			txtTotalAmount.setCaption("Importe IVA incl. (U$S)");
		} else {
			txtTypeExchange.setVisible(false);
			txtAmount.setCaption("Importe sin IVA ($)");
			txtTotalAmount.setCaption("Importe IVA incl. ($)");
		}
	}

	void clearInputs() {
		txtDescription.clear();
		txtAmount.clear();
		txtTypeExchange.clear();
		cboxIVA_Types.clear();
		txtTotalAmount.clear();

		txtDescription.setValidationVisible(false);
		txtAmount.setValidationVisible(false);
		txtTypeExchange.setValidationVisible(false);

		popupDateFieldAppliedDate.setValue(new Date());

		comboBoxProjects.removeAllItems();
		comboBoxProjects.setValue(0);
		comboBoxProjects.setNullSelectionItemId(null);

		cboxIVA_Types.removeAllItems();
		cboxIVA_Types.setValue(0);
		cboxIVA_Types.setNullSelectionItemId(null);

		txtTypeExchange.setVisible(false);

		txtAmount.setCaption("Importe sin IVA");
		txtTotalAmount.setCaption("Importe IVA incl.");
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

		clearInputs();
	}

	void buildEntityData(Bill bill) {
		txtDescription.setValue(bill.getDescription());
		if (bill.getIsCurrencyDollar()) {
			txtTypeExchange.setConvertedValue(bill.getTypeExchange());
			txtAmount.setConvertedValue(bill.getAmountDollar());
		} else {
			txtAmount.setConvertedValue(bill.getAmountPeso());
		}

		cboxIVA_Types.select((int) bill.getIvaType());

		buildTotalAmount();

		comboBoxProjects.setValue(bill.getProjectId());
		comboBoxProjects.setNullSelectionItemId(new Project());

		popupDateFieldAppliedDate.setValue(bill.getAppliedDateTimeUTC());
	}

	void buildProjects() {
		comboBoxProjects.clear();
		comboBoxProjects.removeAllItems();

		projects = ProjectController.getActiveProjects();
		for (Project project : projects) {
			comboBoxProjects.addItem(project.getId());
			comboBoxProjects.setItemCaption(project.getId(), project.getName());
		}
	}

	void buildIVA_Types() {
		cboxIVA_Types.removeAllItems();

		cboxIVA_Types.addItem(1);
		cboxIVA_Types.setItemCaption(1, "0%");
		cboxIVA_Types.addItem(2);
		cboxIVA_Types.setItemCaption(2, "10%");
		cboxIVA_Types.addItem(3);
		cboxIVA_Types.setItemCaption(3, "22%");

		cboxIVA_Types.setValue(3);
	}

	public void buildGrid() {

		Collection<Bill> bills = BillController
				.getBillsByFiltersAndActiveProjects(
						popupDateFieldFrom.getValue(),
						popupDateFieldTo.getValue(), false, false);

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
			billsGrid.removeColumn("typeExchangeToShow");
			billsGrid.removeColumn("appliedDateTimeUTCToShow");
			billsGrid.removeColumn("projectName");
			billsGrid.removeColumn("ivaType");
			billsGrid.removeColumn("amountToShow");
			billsGrid.removeColumn("amountChargedToShow");
			billsGrid.removeColumn("amountReceivableToShow");
			billsGrid.removeColumn("totalAmount");
			billsGrid.removeColumn("amountCharged");
			billsGrid.removeColumn("amountReceivable");
			billsGrid.setColumnOrder("code", "description",
					"totalAmountToShow", "ivaTypeToShow");

			billsGrid.getColumn("code").setHeaderCaption("C�digo");
			billsGrid.getColumn("description").setHeaderCaption("Descripci�n");
			billsGrid.getColumn("totalAmountToShow").setHeaderCaption(
					"Importe IVA incl.");
			billsGrid.getColumn("ivaTypeToShow").setHeaderCaption("IVA");
			billsGrid.getColumn("description").setWidth(200);
			billsGrid.setWidth(600, Unit.PIXELS);
			billsGrid.setHeight(430, Unit.PIXELS);
			billsGrid.setSelectionMode(SelectionMode.SINGLE);
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

			billsGrid.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void select(SelectionEvent event) {

					BeanItem<Bill> item = beanContainer.getItem(billsGrid
							.getSelectedRow());
					if (item != null) {
						Bill selectedBill = item.getBean();

						setEnabledEditionInputs(true);
						setRequiredEditionInputs(true);
						buildProjects();
						buildIVA_Types();
						buildEntityData(selectedBill);
					} else {
						setEnabledEditionInputs(false);
						setRequiredEditionInputs(false);
						clearInputs();
					}
				}

			});

			setVisibleEditionInputs(true);
			setEnabledEditionInputs(false);
			setRequiredEditionInputs(false);
			clearInputs();
		} else {
			if (billsGrid != null) {
				billsGrid.setVisible(false);
			}

			String strDateFrom = new SimpleDateFormat("MM-yyyy")
					.format(popupDateFieldFrom.getValue());
			String strDateTo = new SimpleDateFormat("MM-yyyy")
					.format(popupDateFieldTo.getValue());

			lblMessage
					.setValue("No hay facturas para mostrar entre el rango fechas "
							+ strDateFrom + " - " + strDateTo);

			txtTypeExchange.setVisible(false);

			setVisibleEditionInputs(false);
		}
	}

	void setEnabledEditionInputs(boolean enabled) {
		btnUpdate.setEnabled(enabled);
		txtDescription.setEnabled(enabled);
		txtAmount.setEnabled(enabled);
		comboBoxProjects.setEnabled(enabled);
		popupDateFieldAppliedDate.setEnabled(enabled);
		cboxIVA_Types.setEnabled(enabled);
	}

	void setRequiredEditionInputs(boolean required) {
		txtDescription.setRequired(required);
		txtAmount.setRequired(required);
		txtAmount.setRequired(required);
		popupDateFieldAppliedDate.setRequired(required);
	}

	void setVisibleEditionInputs(boolean visible) {
		btnUpdate.setVisible(visible);
		btnCancel.setVisible(visible);
		txtDescription.setVisible(visible);
		txtAmount.setVisible(visible);
		comboBoxProjects.setVisible(visible);
		popupDateFieldAppliedDate.setVisible(visible);
		cboxIVA_Types.setVisible(visible);
		txtTotalAmount.setVisible(visible);
	}

	void buildTotalAmount() {
		if (cboxIVA_Types.getValue() != null && txtAmount.getValue() != null) {

			if ((int) cboxIVA_Types.getValue() == 1)// - 0%
			{
				txtTotalAmount.setConvertedValue((Double) txtAmount
						.getConvertedValue());
			} else if ((int) cboxIVA_Types.getValue() == 2)// - 10%
			{
				txtTotalAmount.setConvertedValue((Double) txtAmount
						.getConvertedValue() * 1.10);
			} else if ((int) cboxIVA_Types.getValue() == 3)// - 22%
			{
				txtTotalAmount.setConvertedValue((Double) txtAmount
						.getConvertedValue() * 1.22);
			}
		} else
			txtTotalAmount.clear();
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

		// lblInfo
		lblInfo = new Label();
		lblInfo.setStyleName("update-bill-lblInformation");
		lblInfo.setContentMode(ContentMode.HTML);
		lblInfo.setImmediate(false);
		lblInfo.setWidth("-1px");
		lblInfo.setHeight("-1px");
		StringBuilder strBuilder = new StringBuilder();
		strBuilder
				.append("<b>Importante:</b> Las facturas que se muestran cumplen con lo siguiente</br>");
		strBuilder.append("- No estan liquidadas aun</br>");
		strBuilder.append("- No tienen cobros asociados</br>");
		strBuilder.append("- Pertenecen a proyectos activos");
		lblInfo.setValue(strBuilder.toString());
		mainLayout.addComponent(lblInfo, "top:100.0px;left:270.0px;");

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
		txtDescription = new TextArea();
		txtDescription.setCaption("Descripci�n");
		txtDescription.setImmediate(false);
		txtDescription.setWidth("265px");
		txtDescription.setHeight("100px");
		txtDescription.setRequired(true);
		txtDescription.setMaxLength(240);
		txtDescription.setRows(4);
		txtDescription.setTabIndex(2);
		txtDescription.setNullRepresentation("");
		mainLayout.addComponent(txtDescription, "top:190.0px;right:0px;");

		// txtAmount
		txtAmount = new TextField();
		txtAmount.setCaption("Importe");
		txtAmount.setImmediate(true);
		txtAmount.setWidth("155px");
		txtAmount.setHeight("-1px");
		txtAmount.setTabIndex(5);
		txtAmount.setRequired(true);
		txtAmount.setNullRepresentation("");
		txtAmount.setConverter(new StringToDoubleConverter());
		txtAmount.setLocale(Locale.US);
		mainLayout.addComponent(txtAmount, "top:385.0px;left:615.0px;");

		// txtTypeExchange
		txtTypeExchange = new TextField();
		txtTypeExchange.setCaption("Tipo de cambio");
		txtTypeExchange.setImmediate(true);
		txtTypeExchange.setVisible(false);
		txtTypeExchange.setWidth("100px");
		txtTypeExchange.setHeight("-1px");
		txtTypeExchange.setTabIndex(6);
		txtTypeExchange.setRequired(true);
		txtTypeExchange.setNullRepresentation("");
		txtTypeExchange.setConverter(new StringToDoubleConverter());
		txtTypeExchange.setLocale(Locale.US);
		mainLayout.addComponent(txtTypeExchange, "top:385.0px;right:0px;");

		// cboxIVA_Types
		cboxIVA_Types = new ComboBox();
		cboxIVA_Types.setCaption("IVA");
		cboxIVA_Types.setImmediate(true);
		cboxIVA_Types.setWidth("120px");
		cboxIVA_Types.setHeight("-1px");
		cboxIVA_Types.setTabIndex(5);
		cboxIVA_Types.setNullSelectionAllowed(false);
		mainLayout.addComponent(cboxIVA_Types, "top:450.0px;left:615.0px;");

		// txtTotalAmount
		txtTotalAmount = new TextField();
		txtTotalAmount.setCaption("Importe");
		txtTotalAmount.setImmediate(true);
		txtTotalAmount.setWidth("155px");
		txtTotalAmount.setHeight("-1px");
		txtTotalAmount.setEnabled(false);
		txtTotalAmount.setNullRepresentation("");
		txtTotalAmount.setConverter(new StringToDoubleConverter());
		txtTotalAmount.setTabIndex(6);
		txtTotalAmount.setLocale(Locale.US);
		mainLayout.addComponent(txtTotalAmount, "top:510.0px;left:615.0px;");

		// comboBoxProjects
		comboBoxProjects = new ComboBox();
		comboBoxProjects.setCaption("Proyecto");
		comboBoxProjects.setImmediate(true);
		comboBoxProjects.setWidth("245px");
		comboBoxProjects.setHeight("-1px");
		comboBoxProjects.setTabIndex(7);
		mainLayout.addComponent(comboBoxProjects, "top:320.0px;left:615.0px;");

		// popupDateFieldAppliedDate
		popupDateFieldAppliedDate = new PopupDateField();
		popupDateFieldAppliedDate.setCaption("Correspondiente al mes");
		popupDateFieldAppliedDate.setImmediate(false);
		popupDateFieldAppliedDate.setWidth("165px");
		popupDateFieldAppliedDate.setHeight("-1px");
		popupDateFieldAppliedDate.setTabIndex(8);
		popupDateFieldAppliedDate.setRequired(true);
		popupDateFieldAppliedDate.setDateFormat("MM-yyyy");
		popupDateFieldAppliedDate.setValue(new Date());
		popupDateFieldAppliedDate.setResolution(Resolution.MONTH);
		mainLayout.addComponent(popupDateFieldAppliedDate,
				"top:570.0px;left:615.0px;");

		// btnUpdate
		btnUpdate = new Button();
		btnUpdate.setCaption("Modificar");
		btnUpdate.setImmediate(false);
		btnUpdate.setWidth("120px");
		btnUpdate.setHeight("-1px");
		btnUpdate.setTabIndex(9);
		mainLayout.addComponent(btnUpdate, "top:610.0px;left:0px;");

		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(false);
		btnCancel.setWidth("120px");
		btnCancel.setHeight("-1px");
		btnCancel.setTabIndex(10);
		mainLayout.addComponent(btnCancel, "top:610.0px;left:130.0px;");

		return mainLayout;
	}

}
