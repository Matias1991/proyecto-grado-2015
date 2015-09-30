package views.category;

import java.util.Collection;
import java.util.Date;

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
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;

import controllers.CategoryController;
import controllers.ProjectController;
import entities.Category;
import entities.Constant;
import entities.Constant.UserType;
import entities.Project;
import entities.RequestContext;

public class UpdateCategoryView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private TextField txtAmount;
	@AutoGenerated
	private OptionGroup categoryType;
	@AutoGenerated
	private TextArea txtDescription;
	@AutoGenerated
	private Button btnUpdate;
	@AutoGenerated
	private Label lblTitle;
	private Grid grid;
	private BeanItemContainer<Category> container;
	private int idSelected;
	private Label lblMessage;
	private PopupDateField creationDate;
	private OptionGroup isRRHH;
	private OptionGroup optCurrency;
	private TextField txtTypeExchange;
	private ComboBox cboProject;
	private Label lblInfo;
	private ComboBox cboxIvaTypes;
	private TextField txtTotalAmount;
	private Collection<Project> projects;

	public UpdateCategoryView() {

		super("Rubros", "Modificar rubros");
		buildMainLayout();
		setCompositionRoot(mainLayout);

		lblMessage = new Label("");
		mainLayout.addComponent(lblMessage, "top:10%;left:0.0px;");

		categoryType.addItems("Empresa", "Proyecto");
		categoryType.select("Empresa");

		optCurrency.addItems("Pesos", "Dolares");
		optCurrency.select("Pesos");

		isRRHH.addItems("Material", "Humano");
		isRRHH.select("Material");

		categoryType.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				cboProject.setValidationVisible(false);
				if (categoryType.getValue() == "Empresa") {
					isRRHH.setValue("Material");
					enablePanelProject(false);
					optCurrency.setEnabled(true);
				} else {
					buildProjectCombo(0);
					enablePanelProject(true);
					optCurrency.setEnabled(false);
				}
			}
		});

		optCurrency.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (optCurrency.getValue() == "Dolares") {
					txtTypeExchange.setVisible(true);
					txtAmount.setCaption("Importe sin IVA (U$S)");
					txtTotalAmount.setCaption("Importe IVA incl. (U$S)");
				} else {
					txtTypeExchange.setVisible(false);
					txtTypeExchange.setValidationVisible(false);
					txtAmount.setCaption("Importe sin IVA ($)");
					txtTotalAmount.setCaption("Importe IVA incl. ($)");
				}
			}
		});

		cboProject.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (cboProject.getValue() != null) {
					int id = (int) cboProject.getValue();
					Project project = getProjectById(id);
					buildCategoryCurrency(project.getIsCurrencyDollar());
				}
			}
		});

		txtAmount.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				buildTotalAmount();
			}
		});

		cboxIvaTypes.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				buildTotalAmount();
			}
		});

		// boton de confirmacion
		btnUpdate.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				btnUpdate.setEnabled(false);
				cboProject.setValidationVisible(true);
				cboxIvaTypes.setValidationVisible(true);
				txtTypeExchange.setValidationVisible(true);

				boolean valid = true;

				if( !txtDescription.isValid() || !txtAmount.isValid() || !cboxIvaTypes.isValid() || !creationDate.isValid()){
					txtDescription.setRequiredError("Es requerido");
					cboxIvaTypes.setRequiredError("Es requerido");
					txtAmount.setRequiredError("Es requerido");
					txtAmount.setConversionError("Debe ser num�rico");
					creationDate.setRequiredError("Es requerido");
					valid = false;
				}
				
				if ((categoryType.getValue().equals("Proyecto") && !cboProject.isValid()) //|| (categoryType.getValue() == "Proyecto" && cboProject.getValue() == null)
						|| (optCurrency.getValue() == "Dolares" && !txtTypeExchange.isValid())) {
					cboProject.setRequiredError("Es requerido");
					txtTypeExchange.setRequiredError("Es requerido");
					txtTypeExchange.setConversionError("Debe ser num�rico");
					
					valid = false;
				}
				

				if (categoryType.getValue() == "Proyecto" && cboProject.isValid()  && cboProject.getValue() != null) {
					int id = (int) cboProject.getValue();
					Project project = getProjectById(id);
					if (project.getIsCurrencyDollar() && !txtTypeExchange.isValid()){
						txtTypeExchange.setRequiredError("Es requerido");
						txtTypeExchange.setConversionError("Debe ser num�rico");
						valid = false;
					}
				}


				if (valid) {
					Category category = new Category();

					if (categoryType.getValue().equals("Empresa")) {
						category.setCategoryTypeId(1);
						category.setProjectId(0);
					} else if (categoryType.getValue().equals("Proyecto")) {
						category.setCategoryTypeId(2);
					}

					if (categoryType.getValue() == "Proyecto") {
						category.setProjectId((Integer) cboProject.getValue());
					} else {
						category.setProjectId(0);
					}

					if (isRRHH.getValue() == "Material") {
						category.setIsRRHH(false);
					} else {
						category.setIsRRHH(true);
					}
					category.setAppliedDateTimeUTC(creationDate.getValue());

					if (optCurrency.getValue() == "Pesos") {
						category.setAmountPeso((Double) (txtAmount
								.getConvertedValue()));
						category.setIsCurrencyDollar(false);
					} else {
						category.setAmountDollar((Double) (txtAmount
								.getConvertedValue()));
						category.setTypeExchange((Double) (txtTypeExchange
								.getConvertedValue()));
						category.setIsCurrencyDollar(true);
					}

					category.setIvaTypeId((int) cboxIvaTypes.getValue());

					CategoryController.modifyCategory(category, idSelected);

					txtAmount.setValidationVisible(false);
					if (grid != null) {
						mainLayout.removeComponent(grid);
					}
					buildGrid();

					cboProject.setReadOnly(true);
					setComponentsReadOnly(true);
					cleanInputs();

				} else {
					btnUpdate.setEnabled(true);
				}

			}
		});

		// boton cancelar
		btnCancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(Constant.View.CATEGORIES);
			}
		});
	}

	public void buildGrid() {
		Collection<Category> categories = CategoryController
				.getCategoriesByActiveProjects();

		if (categories != null && categories.size() > 0) {
			lblMessage.setValue("");

			setComponentsVisible(true);
			
			container = new BeanItemContainer<Category>(Category.class,
					categories);

			grid = new Grid(container);
			// oculto columnas que no me interesa mostrar
			grid.removeColumn("id");
			grid.removeColumn("projectId");
			grid.removeColumn("categoryTypeId");
			grid.removeColumn("categoryType");
			grid.removeColumn("createDateTimeUTCToShow");
			grid.removeColumn("createdDateTimeUTC");
			grid.removeColumn("isRRHH");
			grid.removeColumn("isRRHHToShow");
			grid.removeColumn("amountPeso");
			grid.removeColumn("amountDollar");
			grid.removeColumn("categoryTypeToShow");
			grid.removeColumn("amountToShow");
			grid.removeColumn("typeExchange");
			grid.removeColumn("typeExchangeToShow");
			grid.removeColumn("isDollarToShow");
			grid.removeColumn("currencyDollar");
			grid.removeColumn("ivaTypeToShow");
			grid.removeColumn("ivaTypeId");
			grid.removeColumn("totalAmountToShow");

			
			grid.getColumn("description").setHeaderCaption("Descripci�n");
			grid.getColumn("description").setWidth(200);
			grid.getColumn("projectName").setHeaderCaption("Proyecto");
			grid.setColumnOrder("description", "projectName");
			grid.setWidth("330px");
			grid.setHeight("456px");
			grid.setSelectionMode(SelectionMode.SINGLE);
			grid.getSelectedRows().clear();			

			HeaderRow filterRow = grid.appendHeaderRow();
			// Set up a filter for all columns
			for (final Object pid : grid.getContainerDataSource()
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

							BeanItemContainer<Category> container = ((BeanItemContainer<Category>) grid
									.getContainerDataSource());

							container.removeContainerFilters(pid);
							if (null != newValue && !newValue.isEmpty()) {
								container
										.addContainerFilter(new SimpleStringFilter(
												pid, newValue, true, false));
							}
							// grid.recalculateColumnWidths();
						}
					});
					cell.setComponent(txtFilter);
				}
			}

			mainLayout.addComponent(grid, "top:136px;left:0px;");

			grid.addSelectionListener(new SelectionListener() {

				@Override
				public void select(SelectionEvent event) {
					// precargo los campos
					BeanItem<Category> item = container.getItem(grid
							.getSelectedRow());
					if (item != null) {
						btnUpdate.setEnabled(true);
						setComponentsReadOnly(false);
						Category catToModify = item.getBean();
						if (catToModify.isCurrencyDollar()) {
							optCurrency.select("Dolares");
							txtAmount.setConvertedValue(catToModify
									.getAmountDollar());
							txtTypeExchange.setConvertedValue(catToModify
									.getTypeExchange());
							txtTypeExchange.setVisible(true);
						} else {
							optCurrency.select("Pesos");
							txtAmount.setConvertedValue(catToModify
									.getAmountPeso());
							txtTypeExchange.setVisible(false);
							txtTypeExchange.setValue("");
						}
						txtDescription.setEnabled(true);
						txtDescription.setValue(catToModify.getDescription());
						txtDescription.setEnabled(false);
						categoryType.setReadOnly(false);
						categoryType.select(catToModify.getCategoryTypeToShow());
						if(RequestContext.getRequestContext().getUserType() == UserType.USER_TYPE_MANAGER){
							categoryType.setReadOnly(true);
						}
						if (catToModify.getCategoryTypeToShow().equals(
								"Proyecto")) {
							enablePanelProject(true);
							buildProjectCombo(catToModify.getProjectId());
						}
						idSelected = catToModify.getId();
						if (catToModify.getIsRRHH()) {
							isRRHH.select("Humano");
						} else {
							isRRHH.select("Material");
						}
						cboxIvaTypes.setValue(catToModify.getIvaTypeId());
						creationDate.setValue(catToModify
								.getAppliedDateTimeUTC());
					} else {
						setComponentsReadOnly(true);
						enablePanelProject(false);
						cleanInputs();
						btnUpdate.setEnabled(false);
					}
				}
			});
		} else {
			lblMessage.setValue("No hay rubros para mostrar");
			if (grid != null) {
				grid.setVisible(false);
			}
			setComponentsVisible(false);
		}
	}

	public void buildProjectCombo(int projectToSelect) {
		projects = ProjectController.getProjects();
		cboProject.setReadOnly(false);
		cboProject.removeAllItems();
		if (projects != null && projects.size() > 0) {
			for (Project project : projects) {
				cboProject.addItem(project.getId());
				cboProject.setItemCaption(project.getId(), project.getName());
			}
		}
		if (projectToSelect != 0) {
			cboProject.select(projectToSelect);
		}
	}

	private void setComponentsReadOnly(boolean readOnly) {
		txtAmount.setReadOnly(readOnly);
		txtDescription.setReadOnly(readOnly);
		if(RequestContext.getRequestContext().getUserType() == UserType.USER_TYPE_MANAGER){
			categoryType.setReadOnly(true);
		}else{
			categoryType.setReadOnly(readOnly);
		}		
		creationDate.setReadOnly(readOnly);
		isRRHH.setReadOnly(readOnly);
		optCurrency.setReadOnly(readOnly);
		txtTypeExchange.setReadOnly(readOnly);
		cboxIvaTypes.setReadOnly(readOnly);
	}
	

	private void setComponentsVisible(boolean visible) {
		txtAmount.setVisible(visible);
		btnCancel.setVisible(visible);
		btnUpdate.setVisible(visible);
		txtDescription.setVisible(visible);
		categoryType.setVisible(visible);
		creationDate.setVisible(visible);
		isRRHH.setVisible(visible);
		optCurrency.setVisible(visible);
		txtTypeExchange.setVisible(visible);
		cboxIvaTypes.setVisible(visible);
		lblInfo.setVisible(visible);
		txtTotalAmount.setVisible(visible);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if (RequestContext.getRequestContext() != null) {
			if (grid != null) {
				mainLayout.removeComponent(grid);
			}
			buildGrid();
			setComponentsReadOnly(false);
			txtDescription.clear();
			txtAmount.clear();
			setComponentsReadOnly(true);
			enablePanelProject(false);
			cboxIvaTypes.setReadOnly(false);
			cboxIvaTypes.removeAllItems();

			cboxIvaTypes.addItem(1);
			cboxIvaTypes.setItemCaption(1, "0%");
			cboxIvaTypes.addItem(2);
			cboxIvaTypes.setItemCaption(2, "10%");
			cboxIvaTypes.addItem(3);
			cboxIvaTypes.setItemCaption(3, "22%");
			
			cboxIvaTypes.setValue(3);
			cboxIvaTypes.setReadOnly(true);

			cleanInputs();
			btnUpdate.setEnabled(false);
			
			if(RequestContext.getRequestContext().getUserType() == UserType.USER_TYPE_MANAGER){
				categoryType.setEnabled(false);
			}
		}
	}

	void cleanInputs() {
		setComponentsReadOnly(false);
		txtDescription.setValidationVisible(false);
		txtDescription.setValue("");
		cboProject.setValidationVisible(false);
		txtAmount.setValidationVisible(false);
		txtAmount.setValue("");
		txtTypeExchange.setValidationVisible(false);
		txtTypeExchange.setValue("");
		enablePanelProject(false);
		categoryType.setReadOnly(false);
		creationDate.setValue(new Date());
		categoryType.select("Empresa");
		if(RequestContext.getRequestContext().getUserType() == UserType.USER_TYPE_MANAGER){
			categoryType.setReadOnly(true);
		}
		optCurrency.select("Pesos");
		cboxIvaTypes.setValidationVisible(false);
		cboxIvaTypes.setValue(3);
		txtTotalAmount.setValue("");
		setComponentsReadOnly(true);
	}
	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("1000px");
		mainLayout.setHeight("1000px");

		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue(getBreadCrumbToShow());
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");

		// txtDescripcion
		txtDescription = new TextArea();
		txtDescription.setCaption("Descripci�n");
		txtDescription.setImmediate(true);
		txtDescription.setWidth("240px");
		txtDescription.setHeight("58px");
		txtDescription.setTabIndex(1);
		txtDescription.setMaxLength(240);
		txtDescription.setRows(2);
		txtDescription.setNullRepresentation("");
		mainLayout.addComponent(txtDescription, "top:152.0px;left:345.0px;");

		// creationDate
		creationDate = new PopupDateField();
		creationDate.setCaption("Correspondiente al mes");
		creationDate.setImmediate(true);
		creationDate.setWidth("165px");
		creationDate.setHeight("-1px");
		creationDate.setRequired(true);
		creationDate.setDateFormat("MM-yyyy");
		creationDate.setValue(new Date());
		creationDate.setResolution(Resolution.MONTH);
		mainLayout.addComponent(creationDate,
				"top:239.0px;right:490.0px;left:345.0px;");

		// categoryType
		categoryType = new OptionGroup();
		categoryType.setCaption("Asociado a");
		categoryType.setImmediate(true);
		categoryType.setWidth("-1px");
		categoryType.setHeight("-1px");
		mainLayout.addComponent(categoryType, "top:309.0px;left:345.0px;");

		// isRRHH
		isRRHH = new OptionGroup();
		isRRHH.setCaption("Tipo de recurso");
		isRRHH.setImmediate(false);
		isRRHH.setWidth("-1px");
		isRRHH.setHeight("-1px");
		mainLayout.addComponent(isRRHH, "top:304.0px;left:480.0px;");

		// optCurrency
		optCurrency = new OptionGroup();
		optCurrency.setCaption("Moneda");
		optCurrency.setImmediate(true);
		optCurrency.setWidth("-1px");
		optCurrency.setHeight("-1px");
		mainLayout.addComponent(optCurrency, "top:399.0px;left:345.0px;");

		// txtTypeExchange
		txtTypeExchange = new TextField();
		txtTypeExchange.setCaption("Tipo de cambio");
		txtTypeExchange.setImmediate(true);
		txtTypeExchange.setWidth("110px");
		txtTypeExchange.setHeight("-1px");
		txtTypeExchange.setRequired(true);
		txtTypeExchange.setVisible(false);
		txtTypeExchange.setNullRepresentation("");
		txtTypeExchange.setConverter(new StringToDoubleConverter());
		mainLayout.addComponent(txtTypeExchange, "top:489.0px;left:520.0px;");

		// txtAmount
		txtAmount = new TextField();
		txtAmount.setCaption("Importe sin IVA ($)");
		txtAmount.setImmediate(true);
		txtAmount.setWidth("165px");
		txtAmount.setHeight("-1px");
		txtAmount.setConverter(new StringToDoubleConverter());
		txtAmount.setRequired(true);
		txtAmount.setTabIndex(2);
		txtAmount.setNullRepresentation("");
		mainLayout.addComponent(txtAmount, "top:489.0px;left:345.0px;");

		// cboxIvaTypes
		cboxIvaTypes = new ComboBox();
		cboxIvaTypes.setCaption("IVA");
		cboxIvaTypes.setImmediate(true);
		cboxIvaTypes.setWidth("120px");
		cboxIvaTypes.setHeight("-1px");
		cboxIvaTypes.setRequired(true);
		cboxIvaTypes.setTabIndex(5);
		cboxIvaTypes.setNullSelectionAllowed(false);
		mainLayout.addComponent(cboxIvaTypes, "top:557.0px;left:345.0px;");

		// txtTotalAmount
		txtTotalAmount = new TextField();
		txtTotalAmount.setCaption("Importe IVA incl. ($)");
		txtTotalAmount.setImmediate(true);
		txtTotalAmount.setWidth("165px");
		txtTotalAmount.setHeight("-1px");
		txtTotalAmount.setEnabled(false);
		txtTotalAmount.setNullRepresentation("");
		txtTotalAmount.setConverter(new StringToDoubleConverter());
		txtTotalAmount.setTabIndex(6);
		mainLayout.addComponent(txtTotalAmount, "top:557.0px;left:520.0px;");

		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("-1px");
		btnCancel.setHeight("-1px");
		btnCancel.setTabIndex(4);
		btnCancel.setTabIndex(4);
		mainLayout.addComponent(btnCancel, "top:598.0px;left:120.0px;");

		// btnUpdate
		btnUpdate = new Button();
		btnUpdate.setCaption("Modificar");
		btnUpdate.setImmediate(true);
		btnUpdate.setWidth("-1px");
		btnUpdate.setHeight("-1px");
		btnUpdate.setTabIndex(3);
		mainLayout.addComponent(btnUpdate, "top:598.0px;left:0.0px;");

		// lblInfo
		lblInfo = new Label();
		lblInfo.setStyleName("update-bill-lblInformation");
		lblInfo.setContentMode(ContentMode.HTML);
		lblInfo.setImmediate(false);
		lblInfo.setWidth("-1px");
		lblInfo.setHeight("-1px");
		StringBuilder strBuilder = new StringBuilder();
		strBuilder
				.append("<b>Importante:</b> Los rubros que se muestran cumplen con lo siguiente:</br>");
		strBuilder.append("- Pertenecen a proyectos activos");
		lblInfo.setValue(strBuilder.toString());
		mainLayout.addComponent(lblInfo, "top:90.0px;left:0.0px;");

		// cboProject
		cboProject = new ComboBox();
		cboProject.setImmediate(true);
		cboProject.setWidth("250px");
		cboProject.setHeight("-1px");
		cboProject.setCaption("Proyecto");
		cboProject.setInputPrompt("Seleccione el proyecto");
		cboProject.setNullSelectionAllowed(false);
		cboProject.setRequired(true);
		mainLayout.addComponent(cboProject, "top:145.0px;left:610.0px;");

		return mainLayout;
	}

	void enablePanelProject(boolean value) {
		cboProject.setVisible(value);
		if (!value) {
			cboProject.setReadOnly(false);
			cboProject.removeAllItems();
			cboProject.setReadOnly(true);
		}
	}

	void buildCategoryCurrency(boolean isCurrencyDollar) {
		if (isCurrencyDollar) {
			optCurrency.setValue("Dolares");
			txtTypeExchange.setVisible(true);
			txtAmount.setCaption("Importe sin IVA (U$S)");
			txtTotalAmount.setCaption("Importe IVA incl. (U$S)");
		} else {
			optCurrency.setValue("Pesos");
			txtTypeExchange.setVisible(false);
			txtAmount.setCaption("Importe sin IVA ($)");
			txtTotalAmount.setCaption("Importe IVA incl. ($)");
		}
	}

	Project getProjectById(int id) {
		for (Project p : projects) {
			if (p.getId() == id) {
				return p;
			}
		}
		return null;
	}

	void buildTotalAmount() {
		if (cboxIvaTypes.getValue() != null && txtAmount.getValue() != null) {

			if ((int) cboxIvaTypes.getValue() == 1)// - 0%
			{
				txtTotalAmount.setConvertedValue((Double) txtAmount
						.getConvertedValue());
			} else if ((int) cboxIvaTypes.getValue() == 2)// - 10%
			{
				txtTotalAmount.setConvertedValue((Double) txtAmount
						.getConvertedValue() * 1.10);
			} else if ((int) cboxIvaTypes.getValue() == 3)// - 22%
			{
				txtTotalAmount.setConvertedValue((Double) txtAmount
						.getConvertedValue() * 1.22);
			}
		} else
			txtTotalAmount.clear();
	}
}
