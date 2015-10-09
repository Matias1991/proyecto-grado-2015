package views.category;

import java.util.Collection;
import java.util.Date;
import java.util.Locale;

import utils.PopupWindow;
import views.BaseView;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
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

import controllers.CategoryController;
import controllers.ProjectController;
import entities.Category;
import entities.Constant;
import entities.Constant.UserType;
import entities.Project;
import entities.RequestContext;

public class CreateCategoryView extends BaseView {

	private static final long serialVersionUID = 1L;
	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private OptionGroup isRRHH;
	@AutoGenerated
	private PopupDateField createdDateTimeField;
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private Button btnCreate;
	@AutoGenerated
	private OptionGroup categoryType;
	@AutoGenerated
	private TextField txtAmount;
	@AutoGenerated
	private TextArea txtDescription;
	@AutoGenerated
	private Label lblTitle;
	private OptionGroup optCurrency;
	private TextField txtTypeExchange;
	private ComboBox cboProject;
	private ComboBox cboxIvaTypes;
	private TextField txtTotalAmount;
	private Collection<Project> projects;

	public CreateCategoryView() {

		super("Rubros", "Crear rubro");

		buildMainLayout();
		setCompositionRoot(mainLayout);

		categoryType.addItems("Empresa", "Proyecto");
		categoryType.select("Empresa");

		optCurrency.addItems("Pesos", "Dolares");
		optCurrency.select("Pesos");

		isRRHH.addItems("Material", "Humano");
		isRRHH.select("Material");

		categoryType.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				if (categoryType.getValue() == "Empresa") {
					isRRHH.setValue("Material");
					enablePanelProject(false);
					optCurrency.setEnabled(true);
				} else {
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

		btnCreate.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				btnCreate.setEnabled(false);
				txtDescription.setValidationVisible(true);
				txtAmount.setValidationVisible(true);
				txtTypeExchange.setValidationVisible(true);
				cboProject.setValidationVisible(true);
				txtTotalAmount.setValidationVisible(true);
				cboxIvaTypes.setValidationVisible(true);

				boolean valid = true;

				if (!txtDescription.isValid() || !txtAmount.isValid()
						|| !cboxIvaTypes.isValid()
						|| !createdDateTimeField.isValid()) {
					txtDescription.setRequiredError("Es requerido");
					cboxIvaTypes.setRequiredError("Es requerido");
					txtAmount.setRequiredError("Es requerido");
					txtAmount.setConversionError("Debe ser num�rico");
					createdDateTimeField.setRequiredError("Es requerido");
					valid = false;
				}

				if ((categoryType.getValue() == "Proyecto" && !cboProject
						.isValid())
						|| (optCurrency.getValue() == "Dolares" && !txtTypeExchange
								.isValid())) {
					cboProject.setRequiredError("Es requerido");
					txtTypeExchange.setRequiredError("Es requerido");
					txtTypeExchange.setConversionError("Debe ser num�rico");

					valid = false;
				}

				if (categoryType.getValue() == "Proyecto"
						&& cboProject.isValid()
						&& cboProject.getValue() != null) {
					int id = (int) cboProject.getValue();
					Project project = getProjectById(id);
					if (project.getIsCurrencyDollar()
							&& !txtTypeExchange.isValid()) {
						txtTypeExchange.setRequiredError("Es requerido");
						txtTypeExchange.setConversionError("Debe ser num�rico");
						valid = false;
					}
				}

				if (valid) {
					Category category = new Category();
					category.setDescription(txtDescription.getValue());

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

					if (categoryType.getValue().equals("Empresa")) {
						category.setCategoryTypeId(1);
					} else if (categoryType.getValue().equals("Proyecto")) {
						category.setCategoryTypeId(2);
						category.setProjectId((Integer) cboProject.getValue());
						Project project = getProjectById((Integer) cboProject
								.getValue());
						if (project.getIsCurrencyDollar()) {
							category.setAmountDollar((Double) (txtAmount
									.getConvertedValue()));
							category.setTypeExchange((Double) (txtTypeExchange
									.getConvertedValue()));
							category.setIsCurrencyDollar(true);
						}
					}

					if (isRRHH.getValue() == "Material")
						category.setIsRRHH(false);
					else
						category.setIsRRHH(true);

					category.setAppliedDateTimeUTC(createdDateTimeField
							.getValue());

					category.setIvaTypeId((int) cboxIvaTypes.getValue());

					boolean result = CategoryController
							.createCategory(category);

					if (result) {
						new PopupWindow("AVISO", "Rubro creado correctamente");

						cleanInputs();
					}
				}

				btnCreate.setEnabled(true);
			}
		});

		btnCancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator()
						.navigateTo(Constant.View.CATEGORIES);
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
	}

	public void buildProjectCombo() {
		projects = ProjectController.getActiveProjects();

		cboProject.removeAllItems();
		if (projects != null && projects.size() > 0) {
			for (Project project : projects) {
				cboProject.addItem(project.getId());
				cboProject.setItemCaption(project.getId(), project.getName());
			}
		}
	}

	void buildTotalAmount() {
		if(!txtAmount.isValid())
		{
			txtAmount.setValidationVisible(true);
			txtAmount.setRequiredError("Es requerido");
			txtAmount.setConversionError("Debe ser num�rico");
		}
		else
		{
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

	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if (RequestContext.getRequestContext() != null) {
			
			cboxIvaTypes.removeAllItems();
			cboxIvaTypes.addItem(1);
			cboxIvaTypes.setItemCaption(1, "0%");
			cboxIvaTypes.addItem(2);
			cboxIvaTypes.setItemCaption(2, "10%");
			cboxIvaTypes.addItem(3);
			cboxIvaTypes.setItemCaption(3, "22%");
			cboxIvaTypes.setValue(3);
			cleanInputs();
			if (RequestContext.getRequestContext().getUserType() == UserType.USER_TYPE_MANAGER) {
				categoryType.select("Proyecto");
				categoryType.setEnabled(false);
			}
		}
	}

	void cleanInputs() {
		
		enablePanelProject(false);
		
		txtDescription.clear();
		txtAmount.clear();
		txtTypeExchange.clear();
		txtTotalAmount.clear();
		
		txtTotalAmount.setValidationVisible(false);
		txtDescription.setValidationVisible(false);
		cboProject.setValidationVisible(false);
		txtAmount.setValidationVisible(false);
		txtTypeExchange.setValidationVisible(false);
		
		cboProject.setVisible(false);
		txtTypeExchange.setVisible(false);
		
		categoryType.select("Empresa");
		optCurrency.select("Pesos");
		
		createdDateTimeField.setValue(new Date());
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("1000px");
		mainLayout.setHeight("1000px");

		// top-level component properties
		setWidth("1000px");
		setHeight("1000px");

		// label_1
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue(getBreadCrumbToShow());
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");

		// txtDescription
		txtDescription = new TextArea();
		txtDescription.setCaption("Descripci�n");
		txtDescription.setImmediate(true);
		txtDescription.setWidth("240px");
		txtDescription.setHeight("58px");
		txtDescription.setTabIndex(1);
		txtDescription.setRequired(true);
		txtDescription.setMaxLength(120);
		txtDescription.setRows(2);
		txtDescription.setNullRepresentation("");
		mainLayout.addComponent(txtDescription, "top:116.0px;left:0.0px;");

		// createdDateTimeField
		createdDateTimeField = new PopupDateField();
		createdDateTimeField.setCaption("Correspondiente al mes");
		createdDateTimeField.setImmediate(true);
		createdDateTimeField.setWidth("165px");
		createdDateTimeField.setHeight("-1px");
		createdDateTimeField.setRequired(true);
		createdDateTimeField.setDateFormat("MM-yyyy");
		createdDateTimeField.setValue(new Date());
		createdDateTimeField.setResolution(Resolution.MONTH);
		mainLayout.addComponent(createdDateTimeField,
				"top:210.0px;right:513.0px;left:0.0px;");

		// categoryType
		categoryType = new OptionGroup();
		categoryType.setCaption("Asociado a");
		categoryType.setImmediate(true);
		categoryType.setWidth("-1px");
		categoryType.setHeight("-1px");
		mainLayout.addComponent(categoryType, "top:280.0px;left:3.0px;");

		// isRRHH
		isRRHH = new OptionGroup();
		isRRHH.setCaption("Tipo de recurso");
		isRRHH.setImmediate(false);
		isRRHH.setVisible(true);
		isRRHH.setWidth("-1px");
		isRRHH.setHeight("-1px");
		mainLayout.addComponent(isRRHH, "top:280.0px;left:130.0px;");

		// optCurrency
		optCurrency = new OptionGroup();
		optCurrency.setCaption("Moneda");
		optCurrency.setImmediate(true);
		optCurrency.setWidth("-1px");
		optCurrency.setHeight("-1px");
		mainLayout.addComponent(optCurrency,
				"top:370.0px;right:372.0px;left:3.0px;");

		// txtTypeExchange
		txtTypeExchange = new TextField();
		txtTypeExchange.setCaption("Tipo de cambio");
		txtTypeExchange.setImmediate(true);
		txtTypeExchange.setWidth("100px");
		txtTypeExchange.setHeight("-1px");
		txtTypeExchange.setRequired(true);
		txtTypeExchange.setNullRepresentation("");
		txtTypeExchange.setConverter(new StringToDoubleConverter());
		txtTypeExchange.setLocale(Locale.US);
		mainLayout.addComponent(txtTypeExchange,
				"top:460.0px;right:372.0px;left:175.0px;");

		// txtAmount
		txtAmount = new TextField();
		txtAmount.setCaption("Importe sin IVA ($)");
		txtAmount.setImmediate(true);
		txtAmount.setWidth("165px");
		txtAmount.setHeight("-1px");
		txtAmount.setTabIndex(2);
		txtAmount.setRequired(true);
		txtAmount.setNullRepresentation("");
		txtAmount.setConverter(new StringToDoubleConverter());
		txtAmount.setLocale(Locale.US);
		mainLayout.addComponent(txtAmount,
				"top:460.0px;right:372.0px;left:0.0px;");

		// cboxIvaTypes
		cboxIvaTypes = new ComboBox();
		cboxIvaTypes.setCaption("IVA");
		cboxIvaTypes.setImmediate(true);
		cboxIvaTypes.setWidth("120px");
		cboxIvaTypes.setHeight("-1px");
		cboxIvaTypes.setTabIndex(5);
		cboxIvaTypes.setNullSelectionAllowed(false);
		mainLayout.addComponent(cboxIvaTypes, "top:528.0px;left:0.0px;");

		// txtTotalAmount
		txtTotalAmount = new TextField();
		txtTotalAmount.setCaption("Importe IVA incl. ($)");
		txtTotalAmount.setImmediate(true);
		txtTotalAmount.setWidth("165px");
		txtTotalAmount.setHeight("-1px");
		txtTotalAmount.setEnabled(false);
		txtTotalAmount.setNullRepresentation("");
		txtTotalAmount.setConverter(new StringToDoubleConverter());
		txtTotalAmount.setLocale(Locale.US);
		txtTotalAmount.setTabIndex(6);
		mainLayout.addComponent(txtTotalAmount, "top:598.0px;left:0.0px;");

		// btnCreate
		btnCreate = new Button();
		btnCreate.setCaption("Crear");
		btnCreate.setImmediate(true);
		btnCreate.setWidth("120px");
		btnCreate.setHeight("-1px");
		btnCreate.setTabIndex(3);
		mainLayout.addComponent(btnCreate,
				"top:650.0px;right:500.0px;left:0.0px;");

		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("120px");
		btnCancel.setHeight("-1px");
		btnCancel.setTabIndex(4);
		mainLayout.addComponent(btnCancel,
				"top:650.0px;right:340.0px;left:140.0px;");

		// cboProject
		cboProject = new ComboBox();
		cboProject.setImmediate(true);
		cboProject.setWidth("250px");
		cboProject.setHeight("-1px");
		cboProject.setCaption("Proyecto");
		cboProject.setInputPrompt("Seleccione el proyecto");
		cboProject.setNullSelectionAllowed(false);
		cboProject.setRequired(true);
		mainLayout.addComponent(cboProject, "top:116.0px;left:270.0px;");

		return mainLayout;
	}

	void enablePanelProject(boolean value) {
		cboProject.setVisible(value);
		if (!value) {
			cboProject.removeAllItems();
		} else {
			buildProjectCombo();
		}
	}

	void buildCategoryCurrency(boolean isCurrencyDollar) {
		if (isCurrencyDollar) {
			txtTypeExchange.setVisible(true);
			optCurrency.setValue("Dolares");
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
}
