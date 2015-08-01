package views.category;

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
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;

import controllers.CategoryController;
import entities.Category;
import entities.RequestContext;

public class DeleteCategoriesView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button btn_delete;
	@AutoGenerated
	private Label lblTitle;
	private Grid grid;
	private BeanItemContainer<Category> container;

	public DeleteCategoriesView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		btn_delete.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {

				ConfirmDialog.show(WebvaadinUI.getCurrent(), "Confirmación",
						"¿Desea eliminar el rubro?", "Si", "No",
						new ConfirmDialog.Listener() {

							@Override
							public void onClose(ConfirmDialog confirm) {
								if (confirm.isConfirmed()) {
									BeanItem<Category> item = container.getItem(grid.getSelectedRow());

									if (CategoryController.deleteCategory(item.getBean().getId())) {
										grid.getContainerDataSource()
												.removeItem(grid.getSelectedRow());
									}

									btn_delete.setEnabled(false);
								}

							}
						});

			}
		});
	}

	public void buildGrid() {
		Collection<Category> categories = CategoryController.getCategories();

		container = new BeanItemContainer<Category>(Category.class, categories);

		grid = new Grid(container);
		grid.removeColumn("id");
		grid.removeColumn("projectId");
		grid.removeColumn("categoryTypeId");
		grid.removeColumn("categoryType");
		grid.setColumnOrder("description", "amount", "distributionTypeToShow");
		grid.getColumn("description").setHeaderCaption("Nombre");
		grid.getColumn("amount").setHeaderCaption("Importe");
		grid.getColumn("distributionTypeToShow").setHeaderCaption("Distribución");
		grid.setWidth(80, Unit.PERCENTAGE);
		grid.setHeight(80, Unit.PERCENTAGE);
		grid.setSelectionMode(SelectionMode.SINGLE);
		grid.getSelectedRows().clear();
		mainLayout.addComponent(grid, "top:20%;left:0px;");

		grid.addSelectionListener(new SelectionListener() {

			@Override
			public void select(SelectionEvent event) {
				btn_delete.setEnabled(grid.getSelectedRows().size() > 0);
			}
		});
	}

	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if (RequestContext.getRequestContext() != null) {
			if (grid != null) {
				mainLayout.removeComponent(grid);
			}
			buildGrid();
		}
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("700px");
		mainLayout.setHeight("501px");

		// top-level component properties
		setWidth("700px");
		setHeight("501px");

		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Eliminar rubro");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");

		// btn_delete
		btn_delete = new Button();
		btn_delete.setCaption("Eiminar rubro");
		btn_delete.setEnabled(false);
		btn_delete.setImmediate(true);
		btn_delete.setWidth("-1px");
		btn_delete.setHeight("-1px");
		mainLayout.addComponent(btn_delete, "top:50.0px;left:424.0px;");

		return mainLayout;
	}
}
