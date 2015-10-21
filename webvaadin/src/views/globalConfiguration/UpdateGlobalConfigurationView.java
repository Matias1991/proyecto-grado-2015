package views.globalConfiguration;

import java.util.Collection;
import utils.PopupWindow;
import views.BaseView;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.HeaderCell;
import com.vaadin.ui.Grid.HeaderRow;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import controllers.GlobalConfigurationController;
import entities.Constant;
import entities.GlobalConfiguration;
import entities.RequestContext;

public class UpdateGlobalConfigurationView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private static final long serialVersionUID = 1L;
	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private Button btnCancel;
	@AutoGenerated
	private Button btnUpdate;
	@AutoGenerated
	private TextArea txtDescription;
	private TextField txtValue;
	@AutoGenerated
	private Label lblTitle;
	private Grid billsGrid;
	private BeanItemContainer<GlobalConfiguration> beanContainer;

	public UpdateGlobalConfigurationView() {

		super("Configuraciones globales", "Modificar configuración");

		buildMainLayout();
		setCompositionRoot(mainLayout);

		btnUpdate.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				txtDescription.setValidationVisible(true);
				txtValue.setValidationVisible(true);
				
				if (!txtDescription.isValid() || !txtValue.isValid()) {
					txtDescription.setRequiredError("Es requerido");
				}
				else{
					GlobalConfiguration item = (GlobalConfiguration) billsGrid.getSelectedRow();
					GlobalConfiguration globalConfiguration = new GlobalConfiguration();
					globalConfiguration.setValue(txtValue.getValue());
					globalConfiguration.setDescription(txtDescription.getValue());
					
					GlobalConfiguration result = GlobalConfigurationController.updateConfiguration(item.getId(), globalConfiguration);

					if (result != null) {
						new PopupWindow("AVISO",
								"Configuración modificada correctamente");
						clearInputs();
						buildGrid();
					}
				}
			}

		});

		btnCancel.addClickListener(new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event) {
				UI.getCurrent().getNavigator().navigateTo(Constant.View.GLOBAL_CONFIGURATIONS_CATALOG_VIEW);
			}
		});

	}

	void clearInputs() {
		txtDescription.clear();
		txtValue.clear();
		
		txtDescription.setValidationVisible(false);
		txtValue.setValidationVisible(false);
		
		btnUpdate.setEnabled(false);
	}

	void buildEntityData(GlobalConfiguration globalConfiguration) {
		txtDescription.setValue(globalConfiguration.getDescription());
		txtValue.setValue(globalConfiguration.getValue());
	}

	public void buildGrid() {

		Collection<GlobalConfiguration> globalConfiguration = GlobalConfigurationController.getGlobalConfigurations();

		if (globalConfiguration != null && globalConfiguration.size() > 0) {

			if (billsGrid != null) {
				mainLayout.removeComponent(billsGrid);
			}

			beanContainer = new BeanItemContainer<GlobalConfiguration>(GlobalConfiguration.class, globalConfiguration);

			billsGrid = new Grid(beanContainer);
			billsGrid.removeColumn("id");
			billsGrid.removeColumn("code");
			billsGrid.setColumnOrder("description", "value");

			billsGrid.getColumn("description").setHeaderCaption("Descripción");
			billsGrid.getColumn("value").setHeaderCaption(
					"Valor");
			billsGrid.getColumn("description").setWidth(500);
			billsGrid.getColumn("description").setMaximumWidth(100);
			billsGrid.getColumn("description").setMaximumWidth(500);
			billsGrid.setWidth(600, Unit.PIXELS);
			billsGrid.setHeight(515, Unit.PIXELS);
			billsGrid.setSelectionMode(SelectionMode.SINGLE);
			billsGrid.getSelectedRows().clear();

			HeaderRow filterRow = billsGrid.appendHeaderRow();
			for (final Object pid : billsGrid.getContainerDataSource()
					.getContainerPropertyIds()) {
				HeaderCell cell = filterRow.getCell(pid);
				if (cell != null) {
					TextField txtFilter = new TextField();
					txtFilter.setImmediate(true);
					txtFilter.setWidth("125px");
					txtFilter.setHeight("30px");
					txtFilter.setInputPrompt("Filtro");

					txtFilter.addTextChangeListener(new TextChangeListener() {
						private static final long serialVersionUID = 1L;

						@Override
						public void textChange(TextChangeEvent event) {
							String newValue = (String) event.getText();

							@SuppressWarnings("unchecked")
							BeanItemContainer<GlobalConfiguration> container = ((BeanItemContainer<GlobalConfiguration>) billsGrid
									.getContainerDataSource());

							container.removeContainerFilters(pid);
							if (null != newValue && !newValue.isEmpty()) {
								container
										.addContainerFilter(new SimpleStringFilter(
												pid, newValue, true, false));
							}
						}
					});
					cell.setComponent(txtFilter);
				}
			}

			mainLayout.addComponent(billsGrid, "top:10%;left:0px;");

			billsGrid.addSelectionListener(new SelectionListener() {
				private static final long serialVersionUID = 1L;

				@Override
				public void select(SelectionEvent event) {

					BeanItem<GlobalConfiguration> item = beanContainer.getItem(billsGrid
							.getSelectedRow());
					if (item != null) {
						GlobalConfiguration selectedGlobalConfiguration = item.getBean();

						setEnabledEditionInputs(true);
						setRequiredEditionInputs(true);
						buildEntityData(selectedGlobalConfiguration);
					} else {
						setEnabledEditionInputs(false);
						setRequiredEditionInputs(false);
						clearInputs();
					}
				}

			});

			setEnabledEditionInputs(false);
			setRequiredEditionInputs(false);
			clearInputs();
		}
	}

	void setEnabledEditionInputs(boolean enabled) {
		btnUpdate.setEnabled(enabled);
		txtDescription.setEnabled(enabled);
		txtValue.setEnabled(enabled);
	}

	void setRequiredEditionInputs(boolean required) {
		txtDescription.setRequired(required);
		txtValue.setRequired(required);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if (RequestContext.getRequestContext() != null) {
			clearInputs();
			buildGrid();
		}
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("880px");
		mainLayout.setHeight("880px");

		// top-level component properties
		setWidth("880px");
		setHeight("880px");

		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue(getBreadCrumbToShow());
		mainLayout.addComponent(lblTitle, "top:40.0px;left:0.0px;");

		// txtDescription
		txtDescription = new TextArea();
		txtDescription.setCaption("Descripción");
		txtDescription.setImmediate(false);
		txtDescription.setWidth("265px");
		txtDescription.setHeight("100px");
		txtDescription.setRequired(true);
		txtDescription.setMaxLength(240);
		txtDescription.setRows(4);
		txtDescription.setTabIndex(2);
		txtDescription.setNullRepresentation("");
		mainLayout.addComponent(txtDescription, "top:105.0px;right:0px;");
		
		// txtValue
		txtValue = new TextField();
		txtValue.setCaption("Valor");
		txtValue.setImmediate(false);
		txtValue.setWidth("265px");
		txtValue.setRequired(true);
		txtValue.setTabIndex(2);
		txtValue.setNullRepresentation("");
		mainLayout.addComponent(txtValue, "top:230.0px;right:0px;");

		// btnUpdate
		btnUpdate = new Button();
		btnUpdate.setCaption("Modificar");
		btnUpdate.setImmediate(false);
		btnUpdate.setWidth("120px");
		btnUpdate.setHeight("-1px");
		btnUpdate.setTabIndex(9);
		mainLayout.addComponent(btnUpdate, "top:610.0px;left:0px;");

		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(false);
		btnCancel.setWidth("120px");
		btnCancel.setHeight("-1px");
		btnCancel.setTabIndex(10);
		mainLayout.addComponent(btnCancel, "top:610.0px;left:130.0px;");

		return mainLayout;
	}

}
