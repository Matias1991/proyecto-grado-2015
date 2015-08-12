package views.category;

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
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.UserError;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
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

public class CreateCategoryView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Label label_2;
	@AutoGenerated
	private Label lblTitleProyectos;
	@AutoGenerated
	private OptionGroup isRRHH;
	@AutoGenerated
	private PopupDateField popupDateField_1;
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private Button btnCreate;
	@AutoGenerated
	private OptionGroup categoryType;
	@AutoGenerated
	private TextField txtAmount;
	@AutoGenerated
	private TextField txtDescription;
	@AutoGenerated
	private Label label_1;
	private Grid projectsGrid;
	private BeanItemContainer<Project> beanContainer;
	private boolean existProjects;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public CreateCategoryView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		categoryType.addItems("Empresa", "Proyecto");
		categoryType.select("Empresa");
		
		isRRHH.addItems("Recurso material", "Recurso humano");
		isRRHH.select("Recurso material");
		
		
        categoryType.addListener(new ValueChangeListener() {
        	 @Override
        	 public void valueChange(ValueChangeEvent event) {
        		 if(categoryType.getValue() == "Empresa")
        		 {
        			 isRRHH.setValue("Recurso material");
 					enablePanelProject(false);
        		 }
 				else
 					enablePanelProject(true);
             }
        });
		
		btnCreate.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				btnCreate.setEnabled(false);
				txtDescription.setValidationVisible(true);
				txtAmount.setValidationVisible(true);
				lblTitleProyectos.setComponentError(null);
				boolean valid = true;
				
				if(categoryType.getValue() == "Proyecto" && projectsGrid != null && projectsGrid.getSelectedRow() == null || categoryType.getValue() == "Proyecto" && projectsGrid == null)
				{
					lblTitleProyectos.setComponentError(new UserError("Debe seleccionar un proyecto"));
					valid = false;
				}
				
				if(!txtAmount.isValid() || !txtDescription.isValid() || !popupDateField_1.isValid()){
					txtAmount.setRequiredError("Es requerido");
					txtDescription.setRequiredError("Es requerido");
					txtAmount.setConversionError("Debe ser num�rico");
					popupDateField_1.setRequiredError("Es requerido");
					valid = false;
				}
				
				
				if(valid){
					
					Category category = new Category();
					category.setDescription(txtDescription.getValue());
					category.setAmount((Double)(txtAmount.getConvertedValue()));
					
					if (categoryType.getValue().equals("Empresa")) {
						category.setCategoryTypeId(1);
					} else if (categoryType.getValue().equals("Proyecto")) {
						category.setCategoryTypeId(2);
					}
					
					if(projectsGrid != null && projectsGrid.getSelectedRow() != null)
					{
						BeanItem<Project> item = beanContainer.getItem(projectsGrid.getSelectedRow());
						category.setProjectId(item.getBean().getId());
					}
					
					if(isRRHH.getValue() == "Recurso material")
						category.setIsRRHH(false);
					else
						category.setIsRRHH(true);
					
					category.setCreatedDateTimeUTC(popupDateField_1.getValue());
					
					boolean result = CategoryController.createCategory(category);
					
					if(result)
					{
						new PopupWindow("AVISO", "Rubro creado correctamente");
						
						txtDescription.clear();
						txtAmount.clear();
						
						txtDescription.setValidationVisible(false);
						txtAmount.setValidationVisible(false);
						lblTitleProyectos.setComponentError(null);
					}
				}

				btnCreate.setEnabled(true);
			}
		});
		
		btnCancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(WebvaadinUI.MAINMENU);
			}
		});
		// TODO add user code here
	}

	public void buildGrid(){
		Collection<Project> projects = ProjectController.getProjects();
		
		if (projects != null && projects.size() > 0) {
			existProjects = true;
			beanContainer = new BeanItemContainer<Project>(Project.class,projects);
					
			projectsGrid = new Grid(beanContainer);
			projectsGrid.setVisible(false);
			projectsGrid.setWidth("550px");
			projectsGrid.setHeight("315px");
			projectsGrid.removeColumn("createdDateTimeUTC");
			projectsGrid.removeColumn("updatedDateTimeUTC");
			projectsGrid.setColumnOrder("id", "name", "createDateTimeUTCToShow", "enabledToShow");
	
			projectsGrid.getColumn("id").setHeaderCaption("Identificador");
			projectsGrid.getColumn("name").setHeaderCaption("Nombre");
			projectsGrid.getColumn("createDateTimeUTCToShow").setHeaderCaption("Fecha de creacion");
			projectsGrid.getColumn("enabledToShow").setHeaderCaption("Estado");
			projectsGrid.setSelectionMode(SelectionMode.SINGLE);
			projectsGrid.getSelectedRows().clear();
			
			mainLayout.addComponent(projectsGrid, "top:116.0px;left:330.0px;");
		}
		else
		{
			existProjects = false;
			if(projectsGrid != null)
			{
				projectsGrid.setVisible(false);
				lblTitleProyectos.setVisible(true);
				lblTitleProyectos.setComponentError(null);
				label_2.setVisible(true);
			}
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
			if (projectsGrid != null) {
				mainLayout.removeComponent(projectsGrid);
			}
			btnCreate.setEnabled(true);
			buildGrid();
			
			categoryType.select("Empresa");
		}
	}
	
	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("1000px");
		mainLayout.setHeight("501px");
		
		// top-level component properties
		setWidth("1000px");
		setHeight("501px");
		
		// label_1
		label_1 = new Label();
		label_1.setStyleName("titleLabel");
		label_1.setImmediate(false);
		label_1.setWidth("-1px");
		label_1.setHeight("-1px");
		label_1.setValue("Crear rubro");
		mainLayout.addComponent(label_1, "top:42.0px;left:0.0px;");
		
		// txtDescription
		txtDescription = new TextField();
		txtDescription.setCaption("Descripci�n");
		txtDescription.setImmediate(true);
		txtDescription.setWidth("240px");
		txtDescription.setHeight("-1px");
		txtDescription.setTabIndex(1);
		txtDescription.setRequired(true);
		mainLayout.addComponent(txtDescription, "top:116.0px;left:0.0px;");
		
		// txtAmount
		txtAmount = new TextField();
		txtAmount.setCaption("Monto");
		txtAmount.setImmediate(true);
		txtAmount.setWidth("240px");
		txtAmount.setHeight("-1px");
		txtAmount.setTabIndex(2);
		txtAmount.setRequired(true);
		txtAmount.setNullRepresentation("");
		txtAmount.setConverter(new StringToDoubleConverter());
		mainLayout.addComponent(txtAmount,
				"top:180.0px;right:372.0px;left:3.0px;");
		
		// categoryType
		categoryType = new OptionGroup();
		categoryType.setCaption("Asociado a");
		categoryType.setImmediate(true);
		categoryType.setWidth("-1px");
		categoryType.setHeight("-1px");
		mainLayout.addComponent(categoryType, "top:320.0px;left:0.0px;");
		
		// btnCreate
		btnCreate = new Button();
		btnCreate.setCaption("Crear");
		btnCreate.setImmediate(true);
		btnCreate.setWidth("120px");
		btnCreate.setHeight("-1px");
		btnCreate.setTabIndex(3);
		mainLayout.addComponent(btnCreate,
				"top:394.0px;right:500.0px;left:0.0px;");
		
		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("120px");
		btnCancel.setHeight("-1px");
		btnCancel.setTabIndex(4);
		mainLayout.addComponent(btnCancel,
				"top:394.0px;right:340.0px;left:140.0px;");
		
		// popupDateField_1
		popupDateField_1 = new PopupDateField();
		popupDateField_1.setCaption("Fecha");
		popupDateField_1.setImmediate(true);
		popupDateField_1.setWidth("140px");
		popupDateField_1.setHeight("-1px");
		popupDateField_1.setRequired(true);
		popupDateField_1.setDateFormat("dd-MM-yyyy");
		popupDateField_1.setValue(new Date());
		mainLayout.addComponent(popupDateField_1,
				"top:240.0px;right:490.0px;left:3.0px;");
		
		// isRRHH
		isRRHH = new OptionGroup();
		isRRHH.setCaption("Tipo de recurso");
		isRRHH.setImmediate(false);
		isRRHH.setVisible(true);
		isRRHH.setWidth("-1px");
		isRRHH.setHeight("-1px");
		mainLayout.addComponent(isRRHH, "top:320.0px;left:130.0px;");
		
		// lblTitleProyectos
		lblTitleProyectos = new Label();
		lblTitleProyectos.setImmediate(false);
		lblTitleProyectos.setStyleName("subTitleLabelProjects");
		lblTitleProyectos.setWidth("-1px");
		lblTitleProyectos.setHeight("-1px");
		lblTitleProyectos.setValue("Proyectos");
		lblTitleProyectos.setVisible(false);
		mainLayout.addComponent(lblTitleProyectos, "top:95.0px;left:330.0px;");
		
		// label_2
		label_2 = new Label();
		label_2.setImmediate(false);
		label_2.setWidth("-1px");
		label_2.setHeight("-1px");
		label_2.setValue("No hay proyectos ingresados");
		label_2.setVisible(false);
		mainLayout.addComponent(label_2, "top:116.0px;left:330.0px;");
		
		return mainLayout;
	}
	
	void enablePanelProject(boolean value)
	{
		lblTitleProyectos.setVisible(value);
		if(projectsGrid != null)
			projectsGrid.setVisible(value);
		
		if(existProjects)
			label_2.setVisible(false);
		else
			label_2.setVisible(value);
	}
}
