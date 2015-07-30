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

	public ModifyCategoryView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		categoryType.addItems("Empresa", "Proyecto");
		categoryType.select("Empresa");

		// boton de confirmacion
		btnUpdate.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {

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
										category.setDistributionType(1);
									} else if (categoryType.getValue().equals("Proyecto")) {
										category.setDistributionType(2);
									} 

									CategoryController.modifyCategory(category, idSelected);

									txtDescription.setValidationVisible(false);
									txtAmount.setValidationVisible(false);
									mainLayout.removeComponent(grid);
									buildGrid();

								} else {
									txtAmount.setRequiredError("Es requerido");
									txtAmount.setConversionError("Debe ser num�rico");
								}

							}
						});

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

		container = new BeanItemContainer<Category>(Category.class, categories);

		grid = new Grid(container);
		// oculto columnas que no me interesa mostrar
		grid.removeColumn("id");
		grid.removeColumn("projectId");
		grid.removeColumn("distributionType");
		grid.setColumnOrder("description", "amount", "distributionTypeToShow");
		grid.getColumn("description").setHeaderCaption("Nombre");
		grid.getColumn("amount").setHeaderCaption("Importe");
		grid.getColumn("distributionTypeToShow").setHeaderCaption("Distribuci�n");
		grid.setWidth(65, Unit.PERCENTAGE);
		grid.setHeight(65, Unit.PERCENTAGE);
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
					categoryType.select(catToModify.getDistributionTypeToShow());
					idSelected = catToModify.getId();

				} else {
					setComponentsVisible(false);
				}
			}
		});
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
		lblTitle.setValue("Modificar rubro");
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
		txtDescription.setImmediate(false);
		txtDescription.setWidth("220px");
		txtDescription.setHeight("-1px");
		txtDescription.setVisible(false);
		txtDescription.setTabIndex(1);
		txtDescription.setRequired(true);
		mainLayout.addComponent(txtDescription, "top:110.0px;left:555.0px;");

		// categoryType
		categoryType = new OptionGroup();
		categoryType.setCaption("Asociado a");
		categoryType.setImmediate(false);
		categoryType.setWidth("-1px");
		categoryType.setHeight("-1px");
		categoryType.setVisible(false);
		mainLayout.addComponent(categoryType, "top:270.0px;left:555.0px;");

		// txtAmount
		txtAmount = new TextField();
		txtAmount.setCaption("Monto");
		txtAmount.setImmediate(false);
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
