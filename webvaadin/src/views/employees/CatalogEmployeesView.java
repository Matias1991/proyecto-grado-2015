package views.employees;

import java.util.Collection;

import views.BaseView;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;

import controllers.EmployeeController;
import entities.Employee;
import entities.RequestContext;

public class CatalogEmployeesView extends BaseView{

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Label lblTitle;
	private Grid catalogEmployeesGrid;
	private BeanItemContainer<Employee> beanContainer;

	public CatalogEmployeesView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
	}

	public void buildGrid(){
		Collection<Employee> employees = EmployeeController.GetEmployees();
		
		beanContainer = new BeanItemContainer<Employee>(employees);
				
		catalogEmployeesGrid = new Grid(beanContainer);
		catalogEmployeesGrid.removeColumn("id");
		catalogEmployeesGrid.removeColumn("updatedDateTimeUTC");
		catalogEmployeesGrid.removeColumn("createdDateTimeUTC");
		catalogEmployeesGrid.removeColumn("employedType");
		catalogEmployeesGrid.removeColumn("user");
		catalogEmployeesGrid.removeColumn("voSalarySummary");
		catalogEmployeesGrid.removeColumn("voSalarySummaries");
		
		catalogEmployeesGrid.setColumnOrder("name", "lastName", "email", "cellPhone", "address");

		catalogEmployeesGrid.getColumn("name").setHeaderCaption("Nombre");
		catalogEmployeesGrid.getColumn("lastName").setHeaderCaption("Apellido");
		catalogEmployeesGrid.getColumn("email").setHeaderCaption("Correo electr�nico");
		catalogEmployeesGrid.getColumn("cellPhone").setHeaderCaption("Celular");
		catalogEmployeesGrid.getColumn("address").setHeaderCaption("Direcci�n");
		catalogEmployeesGrid.setWidth(100, Unit.PERCENTAGE);
		catalogEmployeesGrid.setHeight(100, Unit.PERCENTAGE);
		catalogEmployeesGrid.setSelectionMode(SelectionMode.SINGLE);
		catalogEmployeesGrid.getSelectedRows().clear();
		mainLayout.addComponent(catalogEmployeesGrid, "top:20%;left:0px;");
			
	}

	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);		
		if(RequestContext.getRequestContext() != null){
			buildGrid();
		}
		
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("682px");
		mainLayout.setHeight("501px");
		
		// top-level component properties
		setWidth("682px");
		setHeight("501px");
		
		// labelTitle
		lblTitle = new Label();
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Cat�logo de empleados");
		lblTitle.setStyleName("titleLabel");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");
		
		return mainLayout;
	}

}
