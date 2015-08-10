package views.category;

import java.util.Collection;

import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;

import controllers.CategoryController;
import entities.Category;
import entities.RequestContext;

public class CatalogCategoriesView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Label lblTitle;
	private Grid categoriesGrid;
	private BeanItemContainer<Category> beanContainer;
	private Label lblMessage;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public CatalogCategoriesView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);	

		lblMessage = new Label("");
		mainLayout.addComponent(lblMessage, "top:80.0px;left:0.0px;");


	}

	public void buildGrid(){
		Collection<Category> categories = CategoryController.getCategories();
		
		if (categories != null && categories.size() > 0) {
			
			lblMessage.setValue("");
			
			beanContainer = new BeanItemContainer<Category>(Category.class,categories);
					
			categoriesGrid = new Grid(beanContainer);
			categoriesGrid.removeColumn("categoryTypeId");
			categoriesGrid.removeColumn("categoryTypeToShow");
			categoriesGrid.removeColumn("projectId");
			categoriesGrid.removeColumn("createdDateTimeUTC");
			categoriesGrid.removeColumn("isRRHH");
			categoriesGrid.removeColumn("id");
			categoriesGrid.setColumnOrder("description", "amount", "categoryType", "projectName");
	
			categoriesGrid.getColumn("description").setHeaderCaption("Descripci�n");
			categoriesGrid.getColumn("amount").setHeaderCaption("Monto");
			categoriesGrid.getColumn("categoryType").setHeaderCaption("Asociado a");
			categoriesGrid.getColumn("createDateTimeUTCToShow").setHeaderCaption("Fecha de creacion");
			categoriesGrid.getColumn("isRRHHToShow").setHeaderCaption("Tipo de recurso");
			categoriesGrid.getColumn("projectName").setHeaderCaption("Nombre de proyecto");
			categoriesGrid.setWidth(100, Unit.PERCENTAGE);
			categoriesGrid.setHeight(100, Unit.PERCENTAGE);
			categoriesGrid.setSelectionMode(SelectionMode.SINGLE);
			categoriesGrid.getSelectedRows().clear();
			mainLayout.addComponent(categoriesGrid, "top:20%;left:0px;");
		}
		else
		{
			if(categoriesGrid != null)
			{
				categoriesGrid.setVisible(false);
			}
			lblMessage.setValue("No hay rubros para mostrar");
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
			if (categoriesGrid != null) {
				mainLayout.removeComponent(categoriesGrid);
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
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Cat�logo de rubros");
		lblTitle.setStyleName("titleLabel");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");
		
		return mainLayout;
	}
}
