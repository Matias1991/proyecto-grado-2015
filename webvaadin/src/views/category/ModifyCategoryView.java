package views.category;

import java.util.Collection;

import org.vaadin.dialogs.ConfirmDialog;

import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;

import controllers.CategoryController;
import entities.Category;
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

	public ModifyCategoryView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		lblMessage = new Label("");
		mainLayout.addComponent(lblMessage, "top:80.0px;left:0.0px;");	
		
		categoryType.addItems("Empresa", "Proyecto");
		categoryType.select("Empresa");

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
								if (confirm.isConfirmed() && txtAmount.isValid() && txtDescription.isValid()) {

									Category category = new Category();
									category.setDescription(txtDescription
											.getValue());
									category.setAmount((Double)(txtAmount.getConvertedValue()));
									
									if (categoryType.getValue().equals("Empresa")) {
										category.setCategoryTypeId(1);
									} else if (categoryType.getValue().equals("Proyecto")) {
										category.setCategoryTypeId(2);
									} 

									CategoryController.modifyCategory(category, idSelected);

									txtDescription.setValidationVisible(false);
									txtAmount.setValidationVisible(false);
									if(grid != null){
										mainLayout.removeComponent(grid);
									}
									buildGrid();
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
			
			container = new BeanItemContainer<Category>(Category.class, categories);
	
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
			grid.removeColumn("projectName");
			
			grid.setColumnOrder("description", "amount", "categoryTypeToShow");
			grid.getColumn("description").setHeaderCaption("Descripci�n");
			grid.getColumn("amount").setHeaderCaption("Monto");
			grid.getColumn("categoryTypeToShow").setHeaderCaption("Asociado a");
			grid.setWidth(373, Unit.PIXELS);
			grid.setHeight(310, Unit.PIXELS);			
			grid.setSelectionMode(SelectionMode.SINGLE);
			grid.getSelectedRows().clear();
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
						txtAmount.setConvertedValue(catToModify.getAmount());
						txtDescription.setValue(catToModify.getDescription());
						categoryType.select(catToModify.getCategoryTypeToShow());
						idSelected = catToModify.getId();
	
					} else {
						setComponentsVisible(false);
					}
				}
			});
		} else {
			lblMessage.setValue("No hay rubros para mostrar");
			if(grid != null){
				grid.setVisible(false);
			}
			btnCancel.setVisible(false);
			btnUpdate.setVisible(false);
		}
	}
	
	private void setComponentsVisible(boolean visible){
		txtAmount.setVisible(visible);
		btnCancel.setVisible(visible);
		btnUpdate.setVisible(visible);
		txtDescription.setVisible(visible);
		categoryType.setVisible(visible);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if (RequestContext.getRequestContext() != null) {
			// Compruebo si el usuario es de tipo socio
			if(RequestContext.getRequestContext().getUserType() != 2){
				getUI().getNavigator().navigateTo(WebvaadinUI.MAINMENU);
			}
			if (grid != null) {
				mainLayout.removeComponent(grid);
			}
			buildGrid();
			txtDescription.clear();
			txtAmount.clear();
			
			setComponentsVisible(false);
		}
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("802px");
		mainLayout.setHeight("501px");

		// top-level component properties
		setWidth("802px");
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
		mainLayout.addComponent(btnUpdate,
				"top:350.0px;right:171.0px;left:555.0px;");

		// txtDescripcion
		txtDescription = new TextField();
		txtDescription.setCaption("Descripci�n");
		txtDescription.setImmediate(true);
		txtDescription.setWidth("220px");
		txtDescription.setHeight("-1px");
		txtDescription.setVisible(false);
		txtDescription.setTabIndex(1);
		txtDescription.setRequired(true);
		mainLayout.addComponent(txtDescription, "top:110.0px;left:555.0px;");

		// categoryType
		categoryType = new OptionGroup();
		categoryType.setCaption("Asociado a");
		categoryType.setImmediate(true);
		categoryType.setWidth("-1px");
		categoryType.setHeight("-1px");
		categoryType.setVisible(false);
		mainLayout.addComponent(categoryType, "top:270.0px;left:555.0px;");

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
		mainLayout.addComponent(txtAmount, "top:190.0px;left:555.0px;");

		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("-1px");
		btnCancel.setHeight("-1px");
		btnCancel.setTabIndex(4);
		btnCancel.setTabIndex(4);
		btnCancel.setVisible(false);
		mainLayout.addComponent(btnCancel,
				"top:350.0px;right:71.0px;left:680.0px;");

		return mainLayout;
	}
}
