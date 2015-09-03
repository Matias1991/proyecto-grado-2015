package views.project;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import utils.PopupWindow;
import views.BaseView;

import com.example.webvaadin.WebvaadinUI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToDoubleConverter;
import com.vaadin.event.DataBoundTransferable;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.AbstractSelect.AcceptItem;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.TableFieldFactory;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Table.TableDragMode;

import controllers.EmployeeController;
import controllers.ProjectController;
import controllers.UserController;
import entities.DistributionType;
import entities.ProjectEmployed;
import entities.Employee;
import entities.ProjectPartner;
import entities.Project;
import entities.RequestContext;
import entities.User;

public class CreateProjectView extends BaseView {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	private static final long serialVersionUID = 1L;
	private AbsoluteLayout mainLayout;
	private TextArea txtDescription;
	private TextField txtName;
	private Label lblTitle;
	private ComboBox cboSeller;
	private Button btnCancel;
	private Button btnCreate;
	private ComboBox cboManager;
	private BeanItemContainer<Employee> employedContainer;
	private BeanItemContainer<Employee> employedHoursContainer;
	private Table tblEmployed;
	private Table tblEmployedHours;
	private ComboBox cboPartner1;
	private ComboBox cboDistribution1;
	private ComboBox cboPartner2;
	private ComboBox cboDistribution2;
	private OptionGroup optionGroupCurrency;
	private TextField txtAmount;
	
	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public CreateProjectView() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		optionGroupCurrency.addItems("Pesos", "Dolares");
		optionGroupCurrency.select("Pesos");
		
		btnCancel.addClickListener(new Button.ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				getUI().getNavigator().navigateTo(WebvaadinUI.MAINMENU);
			}
		});

		btnCreate.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				btnCreate.setEnabled(false);
				cboSeller.setValidationVisible(true);
				cboManager.setValidationVisible(true);
				txtName.setValidationVisible(true);

				if (validate()) {
					Project newProject = new Project();
					newProject.setName(txtName.getValue());
					newProject.setDescription(txtDescription.getValue());
					newProject.setAmount((Double)(txtAmount.getConvertedValue()));
					
					if(optionGroupCurrency.getValue() == "Pesos")
						newProject.setIsCurrencyDollar(false);
					else
						newProject.setIsCurrencyDollar(true);
					
					User manager = new User();
					if (cboManager.getValue() != null) {
						manager.setId((Integer) cboManager.getValue());
						newProject.setManager(manager);
					}

					Employee seller = new Employee();
					seller.setId((Integer) cboSeller.getValue());
					newProject.setSeller(seller);

					List<ProjectEmployed> listEmployedHours = new ArrayList<ProjectEmployed>();

					for (Iterator i = tblEmployedHours.getItemIds().iterator(); i
							.hasNext();) {
						Employee employee = (Employee) i.next();
						ProjectEmployed emp = new ProjectEmployed();

						emp.setEmployedId(employee.getId());
						emp.setEmployedHours(employee.getSalarySummary()
								.getHours());
						listEmployedHours.add(emp);
					}

					newProject.setEmployedHours(listEmployedHours);

					List<ProjectPartner> listPartnerProject = new ArrayList<ProjectPartner>();

					ProjectPartner partner1 = new ProjectPartner();
					partner1.setEmployedId((Integer) cboPartner1.getValue());
					DistributionType distribution1 = new DistributionType();
					distribution1.setId((Integer) cboDistribution1.getValue());
					partner1.setDistributionType(distribution1);
					listPartnerProject.add(partner1);

					ProjectPartner partner2 = new ProjectPartner();
					partner2.setEmployedId((Integer) cboPartner2.getValue());
					DistributionType distribution2 = new DistributionType();
					distribution2.setId((Integer) cboDistribution2.getValue());
					partner2.setDistributionType(distribution2);
					listPartnerProject.add(partner2);

					newProject.setProjectPartners(listPartnerProject);

					boolean result = ProjectController
							.createProject(newProject);

					if (result) {
						new PopupWindow("AVISO",
								"Proyecto creado correctamente");
						cboSeller.setValidationVisible(false);
						cboManager.setValidationVisible(false);
						txtName.setValidationVisible(false);
						cleanInputs();
						
					}
				}
				btnCreate.setEnabled(true);
			}
		});

		cboPartner1.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				// TODO Auto-generated method stub
				for (Object aux : cboPartner1.getItemIds()) {
					if (aux != cboPartner1.getValue()) {
						cboPartner2.select(aux);
					}
				}
			}
		});

		cboDistribution1.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				// Se setea el inverso de la distribucion
				if (!cboDistribution1.isEmpty()) {
					// Distribucion 50/50
					if ((Integer) cboDistribution1.getValue() == 1) {
						cboDistribution2.select(1);
					}
					// Distribucion 2/3-1/3
					if ((Integer) cboDistribution1.getValue() == 2) {
						cboDistribution2.select(3);
					}
					// Distribucion 1/3-2/3
					if ((Integer) cboDistribution1.getValue() == 3) {
						cboDistribution2.select(2);
					}
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
		}
		if (tblEmployed != null) {
			mainLayout.removeComponent(tblEmployed);
		}
		if (tblEmployedHours != null) {
			mainLayout.removeComponent(tblEmployedHours);
		}

		buildTables();
		loadComboBoxes();
		txtName.clear();
		txtDescription.clear();		
		cboSeller.setValidationVisible(false);
		cboManager.setValidationVisible(false);
		txtName.setValidationVisible(false);

	}

	private void cleanInputs() {
		if (tblEmployed != null) {
			mainLayout.removeComponent(tblEmployed);
		}
		if (tblEmployedHours != null) {
			mainLayout.removeComponent(tblEmployedHours);
		}
		buildTables();
		loadComboBoxes();
		txtName.clear();
		txtDescription.clear();
		txtAmount.clear();
		optionGroupCurrency.select("Pesos");
	}

	private void buildTables() {
		// tblEmployed
		employedContainer = new BeanItemContainer<Employee>(Employee.class,
				EmployeeController.GetEmployees());
		employedContainer.addNestedContainerProperty("salarySummary.hours");
		tblEmployed = new Table("Empleados disponibles", employedContainer);
		tblEmployed.setWidth("435px");
		tblEmployed.setHeight("180px");
		tblEmployed
				.setVisibleColumns("name", "lastName", "salarySummary.hours");
		tblEmployed.setColumnHeaders("Nombre", "Apellido", "Horas");
		tblEmployed.setDragMode(TableDragMode.ROW);

		mainLayout.addComponent(tblEmployed, "top:315.0px;left:445.0px;");

		// tblEmployedHours
		Collection<Employee> employedHoursCollection = null;
		employedHoursContainer = new BeanItemContainer<Employee>(
				Employee.class, employedHoursCollection);
		employedHoursContainer
				.addNestedContainerProperty("salarySummary.hours");
		tblEmployedHours = new Table("Empleados asociados",
				employedHoursContainer);
		tblEmployedHours.setWidth("430px");
		tblEmployedHours.setHeight("180px");
		tblEmployedHours.setVisibleColumns("name", "lastName",
				"salarySummary.hours");
		tblEmployedHours.setColumnHeaders("Nombre", "Apellido", "Horas");
		tblEmployedHours.setEditable(true);
		tblEmployedHours.setDragMode(TableDragMode.ROW);

		mainLayout.addComponent(tblEmployedHours, "top:315.0px;left:0px;");

		tblEmployedHours.setTableFieldFactory(new TableFieldFactory() {
			@Override
			public Field createField(Container container, Object itemId,
					Object propertyId, Component uiContext) {
				if (propertyId.toString().equals("salarySummary.hours")) {
					TextField aux = new TextField();
					aux.setWidth("60px");
					aux.setConverter(new StringToDoubleConverter());
					aux.setNullRepresentation("");
					aux.setImmediate(true);
					return aux;
				}
				return null;
			}
		});

		tblEmployedHours.setDropHandler(new DropHandler() {

			@Override
			public AcceptCriterion getAcceptCriterion() {
				// TODO Auto-generated method stub
				return AcceptItem.ALL;
			}

			@Override
			public void drop(DragAndDropEvent event) {
				// TODO Auto-generated method stub
				final DataBoundTransferable t = (DataBoundTransferable) event
						.getTransferable();
				Object ItemId = (Object) t.getItemId();
				employedContainer.removeItem(ItemId);
				employedHoursContainer.addItem(ItemId);
			}
		});

		tblEmployed.setDropHandler(new DropHandler() {

			@Override
			public AcceptCriterion getAcceptCriterion() {
				// TODO Auto-generated method stub
				return AcceptItem.ALL;
			}

			@Override
			public void drop(DragAndDropEvent event) {
				// TODO Auto-generated method stub
				final DataBoundTransferable t = (DataBoundTransferable) event
						.getTransferable();
				Object ItemId = (Object) t.getItemId();
				employedContainer.addItem(ItemId);
				employedHoursContainer.removeItem(ItemId);
			}
		});

	}

	private void loadComboBoxes() {
		Collection<Employee> sellers = EmployeeController.GetEmployees();

		cboSeller.removeAllItems();
		if (sellers != null && sellers.size() > 0) {
			for (Employee employee : sellers) {
				cboSeller.addItem(employee.getId());
				cboSeller.setItemCaption(employee.getId(), employee.getName()
						+ " " + employee.getLastName());
			}
		}

		Collection<User> managers = UserController.getUsersByType(3);

		cboManager.removeAllItems();
		if (managers != null && managers.size() > 0) {
			for (User user : managers) {
				cboManager.addItem(user.getId());
				cboManager.setItemCaption(user.getId(), user.getName() + " "
						+ user.getLastName());
			}
		}

		Collection<Employee> partners = EmployeeController
				.GetEmployeesByType(2);

		cboPartner1.removeAllItems();
		cboPartner2.removeAllItems();
		if (partners != null && partners.size() > 0) {
			for (Employee user : partners) {
				cboPartner1.addItem(user.getId());
				cboPartner1.setItemCaption(user.getId(), user.getName() + " "
						+ user.getLastName());
				cboPartner2.addItem(user.getId());
				cboPartner2.setItemCaption(user.getId(), user.getName() + " "
						+ user.getLastName());

			}
			cboPartner1.setValue(partners.iterator().next().getId());
		}

		Collection<DistributionType> distributions = ProjectController
				.getDistributionTypes();

		cboDistribution1.removeAllItems();
		cboDistribution2.removeAllItems();
		if (distributions != null && distributions.size() > 0) {
			for (DistributionType distribution : distributions) {
				cboDistribution1.addItem(distribution.getId());
				cboDistribution1.setItemCaption(distribution.getId(),
						distribution.getValue());
				cboDistribution2.addItem(distribution.getId());
				cboDistribution2.setItemCaption(distribution.getId(),
						distribution.getValue());
			}
			cboDistribution1.setValue(distributions.iterator().next().getId());
		}

	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("880px");
		mainLayout.setHeight("540px");

		// top-level component properties
		setWidth("880px");
		setHeight("540px");

		// lblTitle
		lblTitle = new Label();
		lblTitle.setStyleName("titleLabel");
		lblTitle.setImmediate(false);
		lblTitle.setWidth("-1px");
		lblTitle.setHeight("-1px");
		lblTitle.setValue("Crear proyecto");
		mainLayout.addComponent(lblTitle, "top:42.0px;left:0.0px;");

		// btnCreate
		btnCreate = new Button();
		btnCreate.setCaption("Crear");
		btnCreate.setImmediate(true);
		btnCreate.setWidth("120px");
		btnCreate.setHeight("-1px");
		btnCreate.setTabIndex(7);
		mainLayout.addComponent(btnCreate,
				"top:503.0px;right:500.0px;left:0.0px;");

		// btnCancel
		btnCancel = new Button();
		btnCancel.setCaption("Cancelar");
		btnCancel.setImmediate(true);
		btnCancel.setWidth("120px");
		btnCancel.setHeight("-1px");
		btnCancel.setTabIndex(8);
		mainLayout.addComponent(btnCancel,
				"top:503.0px;right:340.0px;left:140.0px;");

		// txtName
		txtName = new TextField();
		txtName.setCaption("Nombre");
		txtName.setImmediate(true);
		txtName.setWidth("240px");
		txtName.setHeight("-1px");
		txtName.setTabIndex(1);
		txtName.setRequired(true);
		txtName.setNullRepresentation("");
		mainLayout.addComponent(txtName, "top:116.0px;left:0.0px;");

		// txtDescription
		txtDescription = new TextArea();
		txtDescription.setCaption("Descripción");
		txtDescription.setImmediate(true);
		txtDescription.setWidth("175px");
		txtDescription.setHeight("-1px");
		txtDescription.setMaxLength(240);
		txtDescription.setRows(7);
		txtDescription.setTabIndex(4);
		txtDescription.setNullRepresentation("");
		mainLayout.addComponent(txtDescription, "top:116.0px;left:260.0px;");

		// cboSeller
		cboSeller = new ComboBox();
		cboSeller.setImmediate(true);
		cboSeller.setWidth("215px");
		cboSeller.setHeight("-1px");
		cboSeller.setCaption("Vendedor");
		cboSeller.setInputPrompt("Seleccione el vendedor");
		cboSeller.setTabIndex(2);
		cboSeller.setNullSelectionAllowed(false);
		cboSeller.setRequired(true);
		mainLayout.addComponent(cboSeller,
				"top:116px;right:0px;");

		// cboManager
		cboManager = new ComboBox();
		cboManager.setImmediate(true);
		cboManager.setWidth("215px");
		cboManager.setHeight("-1px");
		cboManager.setCaption("Gerente");
		cboManager.setInputPrompt("Seleccione el gerente");
		cboManager.setTabIndex(3);
		cboManager.setNullSelectionAllowed(true);
		mainLayout.addComponent(cboManager,
				"top:116px;right:222px;");

		// cboPartner
		cboPartner1 = new ComboBox();
		cboPartner1.setImmediate(true);
		cboPartner1.setWidth("215px");
		cboPartner1.setHeight("-1px");
		cboPartner1.setCaption("Socio");
		cboPartner1.setInputPrompt("Seleccione el socio");
		cboPartner1.setTabIndex(5);
		cboPartner1.setNullSelectionAllowed(false);
		cboPartner1.setRequired(true);
		mainLayout.addComponent(cboPartner1,
				"top:180.0px;right:222px;");

		// cboDistribution
		cboDistribution1 = new ComboBox();
		cboDistribution1.setImmediate(true);
		cboDistribution1.setWidth("215px");
		cboDistribution1.setHeight("-1px");
		cboDistribution1.setCaption("Distribución ganancias");
		cboDistribution1.setInputPrompt("Seleccione la distribución");
		cboDistribution1.setTabIndex(6);
		cboDistribution1.setNullSelectionAllowed(false);
		cboDistribution1.setRequired(true);
		mainLayout.addComponent(cboDistribution1,
				"top:245.0px;right:222px;");

		// txtPartner
		cboPartner2 = new ComboBox();
		cboPartner2.setCaption("Socio");
		cboPartner2.setImmediate(true);
		cboPartner2.setWidth("215px");
		cboPartner2.setHeight("-1px");
		cboPartner2.setEnabled(false);
		cboPartner2.setRequired(true);
		mainLayout.addComponent(cboPartner2,
				"top:180.0px;right:0px;");

		// txtDistribution
		cboDistribution2 = new ComboBox();
		cboDistribution2.setCaption("Distribución ganancias");
		cboDistribution2.setImmediate(true);
		cboDistribution2.setWidth("215px");
		cboDistribution2.setHeight("-1px");
		cboDistribution2.setEnabled(false);
		cboDistribution2.setRequired(true);
		mainLayout.addComponent(cboDistribution2,
				"top:245.0px;right:0px;");
		
		// optionGroupCurrency
		optionGroupCurrency = new OptionGroup();
		optionGroupCurrency.setCaption("Moneda");
		optionGroupCurrency.setImmediate(true);
		optionGroupCurrency.setTabIndex(3);
		mainLayout.addComponent(optionGroupCurrency,
				"top:200.0px;left:0.0px;");

		// txtAmount
		txtAmount = new TextField();
		txtAmount.setCaption("Importe estimado");
		txtAmount.setImmediate(true);
		txtAmount.setWidth("140px");
		txtAmount.setHeight("-1px");
		txtAmount.setNullRepresentation("");
		txtAmount.setConverter(new StringToDoubleConverter());
		txtAmount.setTabIndex(4);
		mainLayout.addComponent(txtAmount, "top:200.0px;left:100.0px;");
				
		return mainLayout;
	}

	private boolean validate() {
		boolean valid = true;
		String requiredMessage = "Es requerido";

		if (!txtName.isValid() || !cboSeller.isValid()
				|| !cboPartner1.isValid() || !cboDistribution2.isValid()
				|| !cboPartner2.isValid() || !cboDistribution2.isValid()) {
			txtName.setRequiredError(requiredMessage);
			cboSeller.setRequiredError(requiredMessage);
			cboPartner1.setRequiredError(requiredMessage);
			cboDistribution1.setRequiredError(requiredMessage);
			cboPartner2.setRequiredError(requiredMessage);
			cboDistribution2.setRequiredError(requiredMessage);

			valid = false;
		}

		return valid;
	}

}
