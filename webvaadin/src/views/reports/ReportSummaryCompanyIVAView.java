package views.reports;

import java.util.Calendar;
import java.util.Collection;

import org.dussan.vaadin.dcharts.DCharts;

import views.BaseView;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;

import controllers.LiquidationController;
import entities.ProjectLiquidation;

public class ReportSummaryCompanyIVAView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@AutoGenerated
	private AbsoluteLayout mainLayout;
	private PopupDateField popupDateFieldYear;
	private Label lblTitle;
	private DCharts chartPeso;
	private Label lblMessage;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public ReportSummaryCompanyIVAView() {
		
		super("Reportes / Empresa", "Resumen IVA Compra y Venta");
		
		buildMainLayout();
		setCompositionRoot(mainLayout);

		builInputs();
		
		lblMessage = new Label("");
		mainLayout.addComponent(lblMessage, "top:155.0px;left:0.0px;");
		
		popupDateFieldYear.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (popupDateFieldYear.getValue() != null) {
					buildChartPeso();
				}
			}
		});
		
		// TODO add user code here
	}

	void builInputs()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, 1);
		popupDateFieldYear.setValue(cal.getTime());
		popupDateFieldYear.setDateFormat("yyyy");
		popupDateFieldYear.setResolution(Resolution.YEAR);
	}
	
	public void buildChartPeso(){

		if (chartPeso != null) {
			mainLayout.removeComponent(chartPeso);
		}
		
		//Chart pesos
		Collection<ProjectLiquidation> projectLiquidations = LiquidationController.getProjectLiquidations(0, popupDateFieldYear.getValue(), false);
		
		if(projectLiquidations.size() > 0)
		{	
			lblMessage.setValue("");
			
			chartPeso = new DCharts();
			
			chartPeso.setWidth(100, Unit.PERCENTAGE);
			chartPeso.setHeight(350, Unit.PIXELS);
			
			mainLayout.addComponent(chartPeso, "top:19%;left:0px;");
		}
		else
		{
			lblMessage.setValue("No hay datos en el a�o seleccionado");
		}
	}
	
	@AutoGenerated
	private void buildMainLayout() {
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
				
		// popupDateFieldFrom
		popupDateFieldYear = new PopupDateField();
		popupDateFieldYear.setCaption("A�o");
		popupDateFieldYear.setImmediate(true);
		popupDateFieldYear.setWidth("105px");
		popupDateFieldYear.setHeight("-1px");
		popupDateFieldYear.setTabIndex(1);
		popupDateFieldYear.setRequired(true);
		mainLayout.addComponent(popupDateFieldYear, "top:105.0px;left:0.0px;");
	}

}
