package views.project;

import java.util.Collection;

import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.TableDragMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import controllers.BillController;
import controllers.CategoryController;
import controllers.ProjectController;
import entities.Bill;
import entities.Category;
import entities.Project;
import entities.RequestContext;

public class CatalogProjectView extends BaseView {

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Label lblTitle;
	private TabSheet tabProject;
	private TextField txtDescription;
	private TextField txtSeller;
	private TextField txtManager;
	private Label lblCategoriesEmpty;
	private Label lblBillsEmpty;
	private Label lblEmployeesEmpty;
	private ComboBox comboProject;
	private Table tblCategories;
	private Table tblBills;
	private VerticalLayout vlCategories;
	private VerticalLayout vlbBills;
	private VerticalLayout vlbEmployees;

	public CatalogProjectView() {

		buildMainLayout();
		setCompositionRoot(mainLayout);
		buildTabSeet();

		comboProject.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (comboProject.getValue() != null) {
					tabProject.setVisible(true);
					loadProject(Integer.parseInt(comboProject.getValue()
							.toString()));
				} else {
					tabProject.setVisible(false);
				}
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if (RequestContext.getRequestContext() != null) {
			// Compruebo si el usuario es de tipo socio
			if (RequestContext.getRequestContext().getUserType() != 2) {
				getUI().getNavigator().navigateTo(WebvaadinUI.MAINMENU);
			}

			Collection<Project> projects = ProjectController
					.getProjectsByStatus(true);
			for (Project project : projects) {
				comboProject.addItem(project.getId());
				comboProject.setItemCaption(project.getId(), project.getName());
			}
			comboProject.setValue(projects.iterator().next().getId());
			setReadOnlyTxt(true);
		}

	}

	private void buildTabSeet() {

		tabProject.setVisible(true);

		// Descripci�n
		txtDescription = new TextField();
		txtDescription.setCaption("Descripci�n");
		txtDescription.setImmediate(false);
		txtDescription.setWidth("390px");
		txtDescription.setHeight("60px");

		// Vendedor
		txtSeller = new TextField();
		txtSeller.setCaption("Vendedor");
		txtSeller.setImmediate(false);
		txtSeller.setWidth("390px");
		txtSeller.setHeight("-1px");

		// Gerente
		txtManager = new TextField();
		txtManager.setCaption("Gerente");
		txtManager.setImmediate(false);
		txtManager.setWidth("-1px");
		txtManager.setHeight("-1px");

		lblCategoriesEmpty = new Label();
		lblCategoriesEmpty.setCaption("No tiene rubros asociados");
		lblCategoriesEmpty.setImmediate(false);
		lblCategoriesEmpty.setWidth("-1px");
		lblCategoriesEmpty.setHeight("-1px");

		lblBillsEmpty = new Label();
		lblBillsEmpty.setCaption("No tiene facturas asociadas");
		lblBillsEmpty.setImmediate(false);
		lblBillsEmpty.setWidth("-1px");
		lblBillsEmpty.setHeight("-1px");

		lblEmployeesEmpty = new Label();
		lblEmployeesEmpty.setCaption("No tiene facturas asociadas");
		lblEmployeesEmpty.setImmediate(false);
		lblEmployeesEmpty.setWidth("-1px");
		lblEmployeesEmpty.setHeight("-1px");

		// Tab datos
		GridLayout tab1 = new GridLayout(1, 3);
		tab1.setSpacing(true);
		tab1.addComponent(txtDescription, 0, 0);
		tab1.addComponent(txtSeller, 0, 1);
		tab1.addComponent(txtManager, 0, 2);
		tabProject.addTab(tab1, "Datos b�sicos");

		// Rubros
		vlCategories = new VerticalLayout();
		vlCategories.setSpacing(true);
		vlCategories.addComponent(lblCategoriesEmpty);
		tabProject.addTab(vlCategories, "Rubros");

		// Rubros
		vlbBills = new VerticalLayout();
		vlbBills.setSpacing(true);
		vlbBills.addComponent(lblBillsEmpty);
		tabProject.addTab(vlbBills, "Facturas");

		// Empleados
		vlbEmployees = new VerticalLayout();
		vlbEmployees.setSpacing(true);
		tabProject.addTab(vlbEmployees, "Empleados");

		// tabProject.setVisible(false);
	}

	private void loadProject(int selectedProjectId) {

		// traigo los datos del proyecto
		Project projectToShow = ProjectController.getProject(selectedProjectId);
		// seteo en readonly false, para poder cargar los datos
		setReadOnlyTxt(false);

		txtDescription.setValue(projectToShow.getDescription());
		if (projectToShow.getSeller() != null) {
			txtSeller.setValue(projectToShow.getSeller().getName() + " "
					+ projectToShow.getSeller().getLastName());
		}
		if (projectToShow.getManager() != null) {
			txtManager.setValue(projectToShow.getManager().getName() + " "
					+ projectToShow.getManager().getLastName());
		}

		/* RUBROS */

		// Obtengo los rubros asociados al proyecto seleccionado y creo el grid
		BeanItemContainer<Category> projectCategories = new BeanItemContainer<Category>(
				Category.class, CategoryController.getCategoriesByProject(selectedProjectId));
		if (projectCategories.size() > 0) {
			createCategoryTable(projectCategories);
		} else {
			if(tblCategories != null)
				vlCategories.removeComponent(tblCategories);
			vlCategories.addComponent(lblCategoriesEmpty);
		}

		/* FACTURAS */

		// Obtengo las facturas asociadas al proyecto
		BeanItemContainer<Bill> projectBills = new BeanItemContainer<Bill>(
				Bill.class, BillController.getBills(selectedProjectId));
		if (projectBills.size() > 0) {
			createBillTable(projectBills);
		} else {
			if(tblBills != null)
				vlbBills.removeComponent(tblBills);
			vlbBills.addComponent(lblBillsEmpty);
		}

		setReadOnlyTxt(true);

	}

	private void createCategoryTable(
			BeanItemContainer<Category> projectCategories) {
		// Creo la tabla de rubros asociados al proyecto
		tblCategories = new Table("", projectCategories);
		tblCategories.setWidth("100%");
		tblCategories.setHeight("300px");
		tblCategories.setVisibleColumns("description", "amountToShow",
				"typeExchangeToShow", "isRRHHToShow");
		tblCategories.setColumnHeaders("Descripcion", "Importe",
				"Tipo de cambio", "Tipo");
		tblCategories.setDragMode(TableDragMode.ROW);
		vlCategories.removeComponent(lblCategoriesEmpty);
		vlCategories.addComponent(tblCategories);
	}

	private void createBillTable(BeanItemContainer<Bill> projectBills) {
		// Creo la tabla de facturas asociadas al proyecto

		tblBills = new Table("", projectBills);
		tblBills.setWidth("100%");
		tblBills.setHeight("300px");
		tblBills.setVisibleColumns("code", "description", "amountToShow",
				"typeExchangeToShow");
		tblBills.setColumnHeaders("C�digo", "Descripci�n", "Importe",
				"Tipo de cambio");
		tblBills.setDragMode(TableDragMode.ROW);
		vlbBills.removeComponent(lblBillsEmpty);
		vlbBills.addComponent(tblBills);
	}

	private void setReadOnlyTxt(boolean readOnly) {
		txtSeller.setReadOnly(readOnly);
		txtDescription.setReadOnly(readOnly);
		txtManager.setReadOnly(readOnly);
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
		lblTitle.setValue("Cat�logo de proyectos");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");

		comboProject = new ComboBox();
		comboProject.setCaption("Proyecto");
		comboProject.setImmediate(true);
		comboProject.setWidth("240px");
		comboProject.setHeight("-1px");
		mainLayout.addComponent(comboProject, "top:120.0px;left:0.0px;");

		tabProject = new TabSheet();
		tabProject.setImmediate(false);
		tabProject.setWidth("-1px");
		tabProject.setHeight("-1px");
		mainLayout.addComponent(tabProject, "top:90.0px;left:270.0px;");

		return mainLayout;
	}

}
