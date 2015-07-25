package views.employees;

import java.util.Collection;

import org.vaadin.dialogs.ConfirmDialog;

import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

import controllers.EmployeeController;
import entities.Employee;
import entities.RequestContext;
import entities.User;

public class DeleteEmployeeView extends BaseView {

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	private Button btnDelete;
	private Label lblTitle;
	private Grid grdGrid;
	private BeanItemContainer<Employee> beanContainer;

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public DeleteEmployeeView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
	}
	
	public void buildGrid() {
		Collection<Employee> employees = EmployeeController.GetEmployees();

		if (employees != null && employees.size() > 0) {
			beanContainer = new BeanItemContainer<Employee>(employees);

			grdGrid = new Grid(beanContainer);
			grdGrid.removeColumn("id");
			grdGrid.removeColumn("updatedDateTimeUTC");
			grdGrid.removeColumn("createdDateTimeUTC");
			//grdGrid.removeColumn("employedType");
			grdGrid.removeColumn("cellPhone");
			grdGrid.removeColumn("user");
			grdGrid.removeColumn("voSalarySummary");
			grdGrid.removeColumn("voSalarySummaries");
			grdGrid.removeColumn("email");
			grdGrid.removeColumn("address");

			grdGrid.setColumnOrder("name", "lastName", "employedType");

			grdGrid.getColumn("name").setHeaderCaption("Nombre");
			grdGrid.getColumn("lastName").setHeaderCaption("Apellido");
			grdGrid.getColumn("employedType").setHeaderCaption("Tipo de empleado");
			grdGrid.setWidth(100, Unit.PERCENTAGE);
			grdGrid.setHeight(100, Unit.PERCENTAGE);
			grdGrid.setSelectionMode(SelectionMode.SINGLE);
			grdGrid.getSelectedRows().clear();
			mainLayout.addComponent(grdGrid, "top:20%;left:0px;");
			
			grdGrid.addSelectionListener(new SelectionListener() {

				@Override
				public void select(SelectionEvent event) {
					btnDelete.setEnabled(grdGrid.getSelectedRows().size() >0);
//					BeanItem<Employee> item = beanContainer.getItem(grdGrid.getSelectedRow());
//					Employee selectedEmployee = item.getBean();										
				}				
			});
			btnDelete.addClickListener(new Button.ClickListener() {

				@Override
				public void buttonClick(ClickEvent event) {
					
					ConfirmDialog.show(WebvaadinUI.getCurrent(), "Confirmación", "Desea eliminar al empleado?", "Si", "No", new ConfirmDialog.Listener() {
						
						@Override
						public void onClose(ConfirmDialog confirm) {
							if(confirm.isConfirmed()){
								BeanItem<Employee> item = beanContainer.getItem(grdGrid.getSelectedRow());
								
								if(EmployeeController.DeleteEmployee(item.getBean().getId())){
									grdGrid.getContainerDataSource().removeItem(grdGrid.getSelectedRow());	
								}
								
								btnDelete.setEnabled(false);
							}
							
						}
					});
					
					
				}
			});
			
		} else {
			// PopupWindow popup = new PopupWindow("Aviso",
			// "No hay empleados para mostrar");
			Notification.show("No hay empleados para mostrar",
					Notification.TYPE_WARNING_MESSAGE);
		}

	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if (RequestContext.getRequestContext() != null) {
			buildGrid();
		}

	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// the main layout and components will be created here
		mainLayout = new AbsoluteLayout();
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("682px");
		mainLayout.setHeight("501px");

		// top-level component properties
		setWidth("682px");
		setHeight("501px");

		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Eliminar empleado");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");

		// btnDelete
		btnDelete = new Button();
		btnDelete.setCaption("Eiminar empleado");
		btnDelete.setEnabled(false);
		btnDelete.setImmediate(true);
		btnDelete.setWidth("-1px");
		btnDelete.setHeight("-1px");
		mainLayout.addComponent(btnDelete, "top:50.0px;left:510.0px;");
				
		return mainLayout;
	}

}
