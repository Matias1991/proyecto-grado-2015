package views.reports;

import java.lang.reflect.Array;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import org.dussan.vaadin.dcharts.DCharts;
import org.dussan.vaadin.dcharts.base.elements.XYaxis;
import org.dussan.vaadin.dcharts.base.elements.XYseries;
import org.dussan.vaadin.dcharts.data.DataSeries;
import org.dussan.vaadin.dcharts.data.Ticks;
import org.dussan.vaadin.dcharts.metadata.LegendPlacements;
import org.dussan.vaadin.dcharts.metadata.SeriesToggles;
import org.dussan.vaadin.dcharts.metadata.TooltipAxes;
import org.dussan.vaadin.dcharts.metadata.XYaxes;
import org.dussan.vaadin.dcharts.metadata.locations.TooltipLocations;
import org.dussan.vaadin.dcharts.metadata.renderers.AxisRenderers;
import org.dussan.vaadin.dcharts.metadata.renderers.SeriesRenderers;
import org.dussan.vaadin.dcharts.options.Axes;
import org.dussan.vaadin.dcharts.options.Highlighter;
import org.dussan.vaadin.dcharts.options.Legend;
import org.dussan.vaadin.dcharts.options.Options;
import org.dussan.vaadin.dcharts.options.Series;
import org.dussan.vaadin.dcharts.options.SeriesDefaults;
import org.dussan.vaadin.dcharts.renderers.legend.EnhancedLegendRenderer;
import org.dussan.vaadin.dcharts.renderers.tick.AxisTickRenderer;

import views.BaseView;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;

import controllers.LiquidationController;
import entities.CompanyLiquidation;
import entities.ProjectLiquidation;
import entities.RequestContext;

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
		
		super("Reportes", "Resumen de IVA");
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		lblMessage = new Label("");
		mainLayout.addComponent(lblMessage, "top:155.0px;left:0.0px;");
		
		popupDateFieldYear.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (popupDateFieldYear.getValue() != null) {
					buildChart();
				}
			}
		});
		
	}

	void buildInputs()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, 1);
		popupDateFieldYear.setValue(cal.getTime());
		popupDateFieldYear.setDateFormat("yyyy");
		popupDateFieldYear.setResolution(Resolution.YEAR);
		
		buildChart();
	}
	
	DCharts buildChart(Collection<CompanyLiquidation> companyLiquidations)
	{
		DataSeries dataSeries = new DataSeries();
		
		CompanyLiquidation[] array = (CompanyLiquidation[]) Array.newInstance(CompanyLiquidation.class, companyLiquidations.size());
		companyLiquidations.toArray(array);
		
		HashMap<Integer, CompanyLiquidation> companyLiquidationsByMonth = buildCompanyLiquidacions(array);
		
		dataSeries.add(companyLiquidationsByMonth.get(0).getIVAPurchase(), 
					   companyLiquidationsByMonth.get(1).getIVAPurchase(), 
					   companyLiquidationsByMonth.get(2).getIVAPurchase(),
					   companyLiquidationsByMonth.get(3).getIVAPurchase(),
					   companyLiquidationsByMonth.get(4).getIVAPurchase(),
					   companyLiquidationsByMonth.get(5).getIVAPurchase(),
					   companyLiquidationsByMonth.get(6).getIVAPurchase(),
					   companyLiquidationsByMonth.get(7).getIVAPurchase(),
					   companyLiquidationsByMonth.get(8).getIVAPurchase(),
					   companyLiquidationsByMonth.get(9).getIVAPurchase(),
					   companyLiquidationsByMonth.get(10).getIVAPurchase(),
					   companyLiquidationsByMonth.get(11).getIVAPurchase());
		
		dataSeries.add(companyLiquidationsByMonth.get(0).getIVASale(), 
					   companyLiquidationsByMonth.get(1).getIVASale(), 
					   companyLiquidationsByMonth.get(2).getIVASale(),
					   companyLiquidationsByMonth.get(3).getIVASale(),
					   companyLiquidationsByMonth.get(4).getIVASale(),
					   companyLiquidationsByMonth.get(5).getIVASale(),
					   companyLiquidationsByMonth.get(6).getIVASale(),
					   companyLiquidationsByMonth.get(7).getIVASale(),
					   companyLiquidationsByMonth.get(8).getIVASale(),
					   companyLiquidationsByMonth.get(9).getIVASale(),
					   companyLiquidationsByMonth.get(10).getIVASale(),
					   companyLiquidationsByMonth.get(11).getIVASale());
		
		dataSeries.add(companyLiquidationsByMonth.get(0).getIVASale() - companyLiquidationsByMonth.get(0).getIVAPurchase(), 
				   companyLiquidationsByMonth.get(1).getIVASale() - companyLiquidationsByMonth.get(1).getIVAPurchase(), 
				   companyLiquidationsByMonth.get(2).getIVASale() - companyLiquidationsByMonth.get(2).getIVAPurchase(),
				   companyLiquidationsByMonth.get(3).getIVASale() - companyLiquidationsByMonth.get(3).getIVAPurchase(),
				   companyLiquidationsByMonth.get(4).getIVASale() - companyLiquidationsByMonth.get(4).getIVAPurchase(),
				   companyLiquidationsByMonth.get(5).getIVASale() - companyLiquidationsByMonth.get(5).getIVAPurchase(),
				   companyLiquidationsByMonth.get(6).getIVASale() - companyLiquidationsByMonth.get(6).getIVAPurchase(),
				   companyLiquidationsByMonth.get(7).getIVASale() - companyLiquidationsByMonth.get(7).getIVAPurchase(),
				   companyLiquidationsByMonth.get(8).getIVASale() - companyLiquidationsByMonth.get(8).getIVAPurchase(),
				   companyLiquidationsByMonth.get(9).getIVASale() - companyLiquidationsByMonth.get(9).getIVAPurchase(),
				   companyLiquidationsByMonth.get(10).getIVASale() - companyLiquidationsByMonth.get(10).getIVAPurchase(),
				   companyLiquidationsByMonth.get(11).getIVASale() - companyLiquidationsByMonth.get(11).getIVAPurchase());
		
		SeriesDefaults seriesDefaults = new SeriesDefaults()
			.setFillToZero(true)
			.setRenderer(SeriesRenderers.BAR);

		Series series = new Series()
			.addSeries(
				new XYseries()
					.setLabel("IVA Compra en $"))
		.addSeries(
				new XYseries()
					.setLabel("IVA Venta en $"))
		.addSeries(
				new XYseries()
					.setLabel("IVA a pagar en $"));
		
		Legend legend = new Legend()
			.setShow(true)
			.setRendererOptions(
				new EnhancedLegendRenderer()
					.setSeriesToggle(SeriesToggles.SLOW)
					.setSeriesToggleReplot(true))
			.setPlacement(LegendPlacements.OUTSIDE_GRID);

		Highlighter highlighter = new Highlighter()
			.setShow(true)
			.setShowTooltip(true)
			.setTooltipAlwaysVisible(true)
			.setKeepTooltipInsideChart(true)
			.setTooltipLocation(TooltipLocations.NORTH)
			.setTooltipAxes(TooltipAxes.XY_BAR);
		
		Axes axes = new Axes()
			.addAxis(
				new XYaxis()
					.setRenderer(AxisRenderers.CATEGORY)
					.setTicks(
		                new Ticks()
		                    .add("Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Set", "Oct", "Nov", "Dic")))
			.addAxis(
				new XYaxis(XYaxes.Y)
					.setPad(1.05f)
					.setTickOptions(
						new AxisTickRenderer()
							.setFormatString("$ %d")));

		Options options = new Options()
			.setSeriesDefaults(seriesDefaults)
			.setSeries(series)
			.setLegend(legend)
			.setHighlighter(highlighter)
			.setAxes(axes);

		return new DCharts()
			.setDataSeries(dataSeries)
			.setOptions(options)
			.show();
	}
	
	public void buildChart(){

		if (chartPeso != null) {
			mainLayout.removeComponent(chartPeso);
		}
		
		Collection<CompanyLiquidation> companyLiquidations = LiquidationController.getCompanyLiquidations(popupDateFieldYear.getValue());
		
		if(companyLiquidations.size() > 0)
		{	
			lblMessage.setValue("");
			
			chartPeso = buildChart(companyLiquidations);
			
			chartPeso.setWidth(100, Unit.PERCENTAGE);
			chartPeso.setHeight(350, Unit.PIXELS);
			
			mainLayout.addComponent(chartPeso, "top:19%;left:0px;");
		}
		else
		{
			lblMessage.setValue("No hay datos en el a�o seleccionado");
		}
	}
	
	HashMap<Integer, CompanyLiquidation> buildCompanyLiquidacions(CompanyLiquidation[] companyLiquidations)
	{
		HashMap<Integer, CompanyLiquidation> companyLiquidacionByMonth = new HashMap<Integer, CompanyLiquidation>();
		companyLiquidacionByMonth.put(0, new CompanyLiquidation());
		companyLiquidacionByMonth.put(1, new CompanyLiquidation());
		companyLiquidacionByMonth.put(2, new CompanyLiquidation());
		companyLiquidacionByMonth.put(3, new CompanyLiquidation());
		companyLiquidacionByMonth.put(4, new CompanyLiquidation());
		companyLiquidacionByMonth.put(5, new CompanyLiquidation());
		companyLiquidacionByMonth.put(6, new CompanyLiquidation());
		companyLiquidacionByMonth.put(7, new CompanyLiquidation());
		companyLiquidacionByMonth.put(8, new CompanyLiquidation());
		companyLiquidacionByMonth.put(9, new CompanyLiquidation());
		companyLiquidacionByMonth.put(10, new CompanyLiquidation());
		companyLiquidacionByMonth.put(11, new CompanyLiquidation());
		
		for(int i = 0; i< companyLiquidations.length; i++)
		{
			int month = companyLiquidations[i].getAppliedDateTimeUTC().getMonth();
			companyLiquidacionByMonth.put(month, companyLiquidations[i]);
		}

		return companyLiquidacionByMonth;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if (RequestContext.getRequestContext() != null) {
			buildInputs();
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
