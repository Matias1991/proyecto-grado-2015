package views.category;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.vaadin.dialogs.ConfirmDialog;

import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Container.Indexed;
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
import com.vaadin.server.UserError;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextField;

import controllers.CategoryController;
import controllers.ProjectController;
import entities.Category;
import entities.Project;
import entities.RequestContext;

public class ModifyCategoryView extends BaseView {

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
	private TextField txtDescription;
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

	public ModifyCategoryView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		lblMessage = new Label("");
		mainLayout.addComponent(lblMessage, "top:80.0px;left:0.0px;");

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
					isRRHH.setValue("Recurso material");
					enablePanelProject(false);
				} else {
					enablePanelProject(true);
					buildProjectCombo(container.getItem(grid.getSelectedRow()).getBean().getProjectId());					
				}
			}
		});
		
		 optCurrency.addValueChangeListener(new ValueChangeListener() {
				
				@Override
				public void valueChange(ValueChangeEvent event) {
					// TODO Auto-generated method stub
					if(optCurrency.getValue() == "Dolares"){
						txtTypeExchange.setVisible(true);
					}else{
						txtTypeExchange.setVisible(false);
					}
				}
			});

		// boton de confirmacion
		btnUpdate.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				btnUpdate.setEnabled(false);

				ConfirmDialog.show(WebvaadinUI.getCurrent(), "Confirmaci�n",
						"�Desea modificar este rubro?", "Si", "No",
						new ConfirmDialog.Listener() {

							@Override
							public void onClose(ConfirmDialog confirm) {
								boolean valid = true;

								if (categoryType.getValue() == "Proyecto" && cboProject.getValue() == null) {
									cboProject.setRequiredError("Es requerido");
									valid = false;
								}
								
								if(optCurrency.getValue() == "Dolares" && !txtTypeExchange.isValid()){
									txtTypeExchange.setRequiredError("Es requerido");
									txtTypeExchange.setConversionError("Debe ser num�rico");
									valid = false;
								}

								if (confirm.isConfirmed() && txtAmount.isValid()  && valid) {
									Category category = new Category();									
									//category.setAmountPeso((Double) (txtAmount.getConvertedValue()));
									//lblProjectTitle.setComponentError(null);

									if (categoryType.getValue().equals("Empresa")) {
										category.setCategoryTypeId(1);
										category.setProjectId(0);
									} else if (categoryType.getValue().equals("Proyecto")) {
										category.setCategoryTypeId(2);
									}
									
									if(categoryType.getValue() == "Proyecto"){
										//category.setProjectName(cboProject.getValue().toString());
										category.setProjectId((Integer)cboProject.getValue());
									}else{
										category.setProjectId(0);
									}									

									if (isRRHH.getValue() == "Material") {
										category.setIsRRHH(false);
									} else {
										category.setIsRRHH(true);
									}
									category.setCreatedDateTimeUTC(creationDate
											.getValue());
									
									if(optCurrency.getValue() == "Pesos")
									{
										category.setAmountPeso((Double)(txtAmount.getConvertedValue()));
										category.setIsCurrencyDollar(false);
									}
									else
									{
										category.setAmountDollar((Double)(txtAmount.getConvertedValue()));
										category.setTypeExchange((Double)(txtTypeExchange.getConvertedValue()));
										category.setIsCurrencyDollar(true);
									}

									CategoryController.modifyCategory(category,
											idSelected);

									txtAmount.setValidationVisible(false);
									if (grid != null) {
										mainLayout.removeComponent(grid);
									}
									buildGrid();

									//lblProjectTitle.setVisible(false);
									enablePanelProject(false);
									setComponentsVisible(false);

								} else {
									txtAmount.setRequiredError("Es requerido");
									txtAmount.setConversionError("Debe ser num�rico");
								}

							}
						});
				btnUpdate.setEnabled(true);
			}
		});

		// boton cancelar
		btnCancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(WebvaadinUI.MAINMENU);
			}
		});
	}

	public void buildGrid() {
		Collection<Category> categories = CategoryController.getCategories();

		if (categories != null && categories.size() > 0) {
			lblMessage.setValue("");
			btnCancel.setVisible(true);
			btnUpdate.setVisible(true);

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
			

			grid.getColumn("description").setHeaderCaption("Descripci�n");
			grid.getColumn("projectName").setHeaderCaption("Proyecto");
			grid.setColumnOrder("description", "projectName");
			grid.setWidth(320, Unit.PIXELS);
			grid.setHeight(310, Unit.PIXELS);
			grid.setSelectionMode(SelectionMode.SINGLE);
			grid.getSelectedRows().clear();
			
			HeaderRow filterRow = grid.appendHeaderRow();
			// Set up a filter for all columns
			for ( final Object pid: grid.getContainerDataSource().getContainerPropertyIds()){
				HeaderCell cell = filterRow.getCell(pid);
				if(cell != null){
					TextField textField = new TextField();
					textField.setImmediate(true);
					textField.setWidth("125px");
					
					textField.addTextChangeListener(new TextChangeListener() {	 
					  @Override
					  public void textChange(TextChangeEvent event) {
					   String newValue = (String) event.getText();
					  
					   BeanItemContainer<Category> container = ((BeanItemContainer<Category>) grid.getContainerDataSource());
					  
					   container.removeContainerFilters(pid);
					   if (null != newValue && !newValue.isEmpty()) {
						   container.addContainerFilter(new SimpleStringFilter(pid, newValue, true, false));
					   }					   
					  // grid.recalculateColumnWidths();
					  }	
					 });
					cell.setComponent(textField);	
				}
			}
			
			mainLayout.addComponent(grid, "top:20%;left:0px;");

			grid.addSelectionListener(new SelectionListener() {

				@Override
				public void select(SelectionEvent event) {
					// precargo los campos
					BeanItem<Category> item = container.getItem(grid
							.getSelectedRow());
					if (item != null) {
						setComponentsVisible(true);
						Category catToModify = item.getBean();
						if(catToModify.isCurrencyDollar()){
							optCurrency.setValue("Dolares");
							txtAmount.setConvertedValue(catToModify.getAmountDollar());
							txtTypeExchange.setConvertedValue(catToModify.getTypeExchange());
							txtTypeExchange.setVisible(true);
						}else{
							optCurrency.setValue("Pesos");
							txtAmount.setConvertedValue(catToModify.getAmountPeso());
							txtTypeExchange.setVisible(false);
						}
						txtDescription.setEnabled(true);
						txtDescription.setValue(catToModify.getDescription());
						txtDescription.setEnabled(false);
						categoryType.select(catToModify.getCategoryTypeToShow());
						if(catToModify.getCategoryTypeToShow().equals("Proyecto")){
							enablePanelProject(true);
							buildProjectCombo(catToModify.getProjectId());							
						}
						idSelected = catToModify.getId();
						if(catToModify.getIsRRHH()){
							isRRHH.select("Humano");
						} else {
							isRRHH.select("Material");
						}
						creationDate.setValue(catToModify.getCreatedDateTimeUTC());
					} else {
						setComponentsVisible(false);						
						enablePanelProject(false);
					}
				}
			});
		} else {
			lblMessage.setValue("No hay rubros para mostrar");
			if (grid != null) {
				grid.setVisible(false);
			}
			btnCancel.setVisible(false);
			btnUpdate.setVisible(false);
		}
	}
	
	public void buildProjectCombo(int projectToSelect){
		Collection<Project> projects = ProjectController.getProjects();
		
		cboProject.removeAllItems();
		if(projects != null && projects.size() > 0){					
			for (Project project : projects) {
				cboProject.addItem(project.getId());
				cboProject.setItemCaption(project.getId(), project.getName());
			}			
		}		
		if(projectToSelect != 0){
			cboProject.select(projectToSelect);
		}		
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
		if(!visible){
			txtTypeExchange.setVisible(visible);
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
			if (grid != null) {
				mainLayout.removeComponent(grid);
			}
			buildGrid();
			txtDescription.clear();
			txtAmount.clear();			
			setComponentsVisible(false);
			enablePanelProject(false);
		}
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("501px");

		// top-level component properties
		setWidth("100%");
		setHeight("501px");

		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Modificar rubros");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");

		// btnUpdate
		btnUpdate = new Button();
		btnUpdate.setCaption("Modificar");
		btnUpdate.setImmediate(true);
		btnUpdate.setWidth("-1px");
		btnUpdate.setVisible(false);
		btnUpdate.setHeight("-1px");
		btnUpdate.setTabIndex(3);
		mainLayout.addComponent(btnUpdate,"top:414.0px;left:0.0px;");
		

		// txtDescripcion
		txtDescription = new TextField();
		txtDescription.setCaption("Descripci�n");
		txtDescription.setImmediate(true);
		txtDescription.setWidth("220px");
		txtDescription.setHeight("-1px");
		txtDescription.setVisible(false);
		txtDescription.setTabIndex(1);
		txtDescription.setRequired(true);
		mainLayout.addComponent(txtDescription, "top:110.0px;left:345.0px;");

		// categoryType
		categoryType = new OptionGroup();
		categoryType.setCaption("Asociado a");
		categoryType.setImmediate(true);
		categoryType.setWidth("-1px");
		categoryType.setHeight("-1px");
		categoryType.setVisible(false);
		mainLayout.addComponent(categoryType, "top:390.0px;left:345.0px;");

		// isRRHH
		isRRHH = new OptionGroup();
		isRRHH.setCaption("Tipo de recurso");
		isRRHH.setImmediate(false);
		isRRHH.setVisible(true);
		isRRHH.setWidth("-1px");
		isRRHH.setHeight("-1px");
		mainLayout.addComponent(isRRHH, "top:390.0px;left:480.0px;");

		// creationDate
		creationDate = new PopupDateField();
		creationDate.setCaption("Fecha");
		creationDate.setImmediate(true);
		creationDate.setWidth("140px");
		creationDate.setHeight("-1px");
		creationDate.setRequired(true);
		creationDate.setDateFormat("MM-yyyy");
		creationDate.setValue(new Date());
		creationDate.setResolution(Resolution.MONTH);
		mainLayout.addComponent(creationDate,"top:318.0px;right:490.0px;left:345.0px;");

		// txtAmount
		txtAmount = new TextField();
		txtAmount.setCaption("Monto");
		txtAmount.setImmediate(true);
		txtAmount.setWidth("220px");
		txtAmount.setHeight("-1px");
		txtAmount.setConverter(new StringToDoubleConverter());
		txtAmount.setRequired(true);
		txtAmount.setTabIndex(2);
		txtAmount.setVisible(false);
		txtAmount.setNullRepresentation("");
		mainLayout.addComponent(txtAmount, "top:252.0px;left:345.0px;");
		
		//optCurrency
		optCurrency = new OptionGroup();
		optCurrency.setCaption("Moneda");
		optCurrency.setImmediate(true);
		optCurrency.setWidth("-1px");
		optCurrency.setHeight("-1px");
		mainLayout.addComponent(optCurrency,"top:175.0px;left:345.0px;");
	
		//txtTypeExchange
		txtTypeExchange = new TextField();
		txtTypeExchange.setCaption("Tipo de cambio");
		txtTypeExchange.setImmediate(true);
		txtTypeExchange.setWidth("110px");
		txtTypeExchange.setHeight("-1px");
		txtTypeExchange.setRequired(true);
		txtTypeExchange.setNullRepresentation("");
		txtTypeExchange.setConverter(new StringToDoubleConverter());
		mainLayout.addComponent(txtTypeExchange,"top:252.0px;left:600.0px;");

		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("-1px");
		btnCancel.setHeight("-1px");
		btnCancel.setTabIndex(4);
		btnCancel.setTabIndex(4);
		btnCancel.setVisible(false);
		mainLayout.addComponent(btnCancel,"top:414.0px;left:120.0px;");		
		
		//cboProject
		cboProject = new ComboBox();
		cboProject.setImmediate(true);
		cboProject.setWidth("250px");
		cboProject.setHeight("-1px");
		cboProject.setCaption("Proyecto");
		cboProject.setInputPrompt("Seleccione el proyecto");
		cboProject.setNullSelectionAllowed(false);
		cboProject.setRequired(true);
		mainLayout.addComponent(cboProject, "top:110.0px;left:600.0px;");
		

		return mainLayout;
	}

	void enablePanelProject(boolean value) {
		cboProject.setVisible(value);
		if(!value){
			cboProject.removeAllItems();
		}
	}

	
}
