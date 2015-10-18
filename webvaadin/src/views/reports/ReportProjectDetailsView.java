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
import org.openqa.selenium.internal.BuildInfo;

import views.BaseView;

import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;

import controllers.LiquidationController;
import controllers.ProjectController;
import entities.Project;
import entities.ProjectLiquidation;
import entities.RequestContext;

public class ReportProjectDetailsView extends BaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@AutoGenerated
	private AbsoluteLayout mainLayout;
	private PopupDateField popupDateFieldYear;
	private Label lblTitle;
	private ComboBox cboxProject;
	private DCharts chart;
	private DCharts chart2;
	private DCharts chart3;
	private DCharts chart4;
	private Collection<Project> projects;
	private Label lblMessage;
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public ReportProjectDetailsView() {
		
		super("Reportes", "Detalles por proyecto");
		
		buildMainLayout();
		setCompositionRoot(mainLayout);
		
		lblMessage = new Label("");
		mainLayout.addComponent(lblMessage, "top:155.0px;left:0.0px;");
		
		cboxProject.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (cboxProject.getValue() != null && popupDateFieldYear.getValue() != null) {
					int id = (int) cboxProject.getValue();
					
					Project project = getProjectById(id);
					buildChart(project);
				}
			}
		});
		
		popupDateFieldYear.addValueChangeListener(new ValueChangeListener() {

			@Override
			public void valueChange(ValueChangeEvent event) {
				if (cboxProject.getValue() != null && popupDateFieldYear.getValue() != null) {
					int id = (int) cboxProject.getValue();
					
					Project project = getProjectById(id);
					buildChart(project);
				}
			}
		});
		
		// TODO add user code here
	}
	
	void buildInputs()
	{
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_YEAR, 1);
		popupDateFieldYear.setValue(cal.getTime());
		popupDateFieldYear.setDateFormat("yyyy");
		popupDateFieldYear.setResolution(Resolution.YEAR);
		
		cleanLayoutCharts();
	}
	
	void cleanLayoutCharts()
	{
		if (chart != null)
			mainLayout.removeComponent(chart);
		
		if (chart2 != null)
			mainLayout.removeComponent(chart2);
		
		if (chart3 != null)
			mainLayout.removeComponent(chart3);
		
		if (chart4 != null)
			mainLayout.removeComponent(chart4);
		
		lblMessage.setValue("");
	}
	
	//Chart semetre 1(ENERO, FEBRERO, MARZO)
	DCharts buildChart_1(HashMap<Integer, ProjectLiquidation> projectLiquidationsByMonth, String currency)
	{
		DataSeries dataSeries = new DataSeries();

		dataSeries.add(projectLiquidationsByMonth.get(0).getEarnings(), 
					   projectLiquidationsByMonth.get(1).getEarnings(), 
					   projectLiquidationsByMonth.get(2).getEarnings());
		
		dataSeries.add(projectLiquidationsByMonth.get(0).getReserve(), 
					   projectLiquidationsByMonth.get(1).getReserve(), 
					   projectLiquidationsByMonth.get(2).getReserve());
		
		dataSeries.add(projectLiquidationsByMonth.get(0).getTotalCostEmployees(), 
					   projectLiquidationsByMonth.get(1).getTotalCostEmployees(), 
					   projectLiquidationsByMonth.get(2).getTotalCostEmployees());
		
		dataSeries.add(projectLiquidationsByMonth.get(0).getTotalCostCategoriesHuman(), 
					   projectLiquidationsByMonth.get(1).getTotalCostCategoriesHuman(), 
					   projectLiquidationsByMonth.get(2).getTotalCostCategoriesHuman());
		
		dataSeries.add(projectLiquidationsByMonth.get(0).getTotalCostCategoriesMaterial(), 
					   projectLiquidationsByMonth.get(1).getTotalCostCategoriesMaterial(), 
					   projectLiquidationsByMonth.get(2).getTotalCostCategoriesMaterial());
		
		Ticks ticks = new Ticks();
		ticks.add("Enero");
		ticks.add("Febrero");
		ticks.add("Marzo");
		
		return buildChart(currency, dataSeries, ticks);
	}
	
	//Chart semetre 2(ABRIL, MAYO, JUNIO)
	DCharts buildChart_2(HashMap<Integer, ProjectLiquidation> projectLiquidationsByMonth, String currency)
	{
		DataSeries dataSeries = new DataSeries();
	
		dataSeries.add(projectLiquidationsByMonth.get(3).getEarnings(), 
					   projectLiquidationsByMonth.get(4).getEarnings(), 
					   projectLiquidationsByMonth.get(5).getEarnings());
		
		dataSeries.add(projectLiquidationsByMonth.get(3).getReserve(), 
					   projectLiquidationsByMonth.get(4).getReserve(), 
					   projectLiquidationsByMonth.get(5).getReserve());
		
		dataSeries.add(projectLiquidationsByMonth.get(3).getTotalCostEmployees(), 
					   projectLiquidationsByMonth.get(4).getTotalCostEmployees(), 
					   projectLiquidationsByMonth.get(5).getTotalCostEmployees());
		
		dataSeries.add(projectLiquidationsByMonth.get(3).getTotalCostCategoriesHuman(), 
					   projectLiquidationsByMonth.get(4).getTotalCostCategoriesHuman(), 
					   projectLiquidationsByMonth.get(5).getTotalCostCategoriesHuman());
		
		dataSeries.add(projectLiquidationsByMonth.get(3).getTotalCostCategoriesMaterial(), 
					   projectLiquidationsByMonth.get(4).getTotalCostCategoriesMaterial(), 
					   projectLiquidationsByMonth.get(5).getTotalCostCategoriesMaterial());
		
		
		Ticks ticks = new Ticks();
		ticks.add("Abril");
		ticks.add("Mayo");
		ticks.add("Junio");
		
		return buildChart(currency, dataSeries, ticks);
	}
	
	//Chart semetre 3(JULIO, AGOSTO, SETIEMBRE)
	DCharts buildChart_3(HashMap<Integer, ProjectLiquidation> projectLiquidationsByMonth, String currency)
	{
		DataSeries dataSeries = new DataSeries();
	
		dataSeries.add(projectLiquidationsByMonth.get(6).getEarnings(), 
					   projectLiquidationsByMonth.get(7).getEarnings(), 
					   projectLiquidationsByMonth.get(8).getEarnings());
		
		dataSeries.add(projectLiquidationsByMonth.get(6).getReserve(), 
					   projectLiquidationsByMonth.get(7).getReserve(), 
					   projectLiquidationsByMonth.get(8).getReserve());
		
		dataSeries.add(projectLiquidationsByMonth.get(6).getTotalCostEmployees(), 
					   projectLiquidationsByMonth.get(7).getTotalCostEmployees(), 
					   projectLiquidationsByMonth.get(8).getTotalCostEmployees());
		
		dataSeries.add(projectLiquidationsByMonth.get(6).getTotalCostCategoriesHuman(), 
					   projectLiquidationsByMonth.get(7).getTotalCostCategoriesHuman(), 
					   projectLiquidationsByMonth.get(8).getTotalCostCategoriesHuman());
		
		dataSeries.add(projectLiquidationsByMonth.get(6).getTotalCostCategoriesMaterial(), 
					   projectLiquidationsByMonth.get(7).getTotalCostCategoriesMaterial(), 
					   projectLiquidationsByMonth.get(8).getTotalCostCategoriesMaterial());
		
		
		Ticks ticks = new Ticks();
		ticks.add("Julio");
		ticks.add("Agosto");
		ticks.add("Setiembre");
		
		return buildChart(currency, dataSeries, ticks);
	}
	
	//Chart semetre 4(OCTUBRE, NOVIEMBRE, DICIEMBRE)
	DCharts buildChart_4(HashMap<Integer, ProjectLiquidation> projectLiquidationsByMonth, String currency)
	{
		DataSeries dataSeries = new DataSeries();
	
		dataSeries.add(projectLiquidationsByMonth.get(9).getEarnings(), 
					   projectLiquidationsByMonth.get(10).getEarnings(), 
					   projectLiquidationsByMonth.get(11).getEarnings());
		
		dataSeries.add(projectLiquidationsByMonth.get(9).getReserve(), 
					   projectLiquidationsByMonth.get(10).getReserve(), 
					   projectLiquidationsByMonth.get(11).getReserve());
		
		dataSeries.add(projectLiquidationsByMonth.get(9).getTotalCostEmployees(), 
					   projectLiquidationsByMonth.get(10).getTotalCostEmployees(), 
					   projectLiquidationsByMonth.get(11).getTotalCostEmployees());
		
		dataSeries.add(projectLiquidationsByMonth.get(9).getTotalCostCategoriesHuman(), 
					   projectLiquidationsByMonth.get(10).getTotalCostCategoriesHuman(), 
					   projectLiquidationsByMonth.get(11).getTotalCostCategoriesHuman());
		
		dataSeries.add(projectLiquidationsByMonth.get(9).getTotalCostCategoriesMaterial(), 
					   projectLiquidationsByMonth.get(10).getTotalCostCategoriesMaterial(), 
					   projectLiquidationsByMonth.get(11).getTotalCostCategoriesMaterial());
		
		
		Ticks ticks = new Ticks();
		ticks.add("Octubre");
		ticks.add("Noviembre");
		ticks.add("Diciembre");
		
		return buildChart(currency, dataSeries, ticks);
	}
	
	DCharts buildChart(String currency, DataSeries dataSeries, Ticks ticks)
	{
		SeriesDefaults seriesDefaults = new SeriesDefaults()
			.setFillToZero(true)
			.setRenderer(SeriesRenderers.BAR);

		Series series = new Series()
			.addSeries(
				new XYseries()
					.setLabel("Ganacia en " + currency))
		.addSeries(
				new XYseries()
					.setLabel("Reserva en " + currency))
		.addSeries(
				new XYseries()
					.setLabel("Costo empleados en " + currency))
		.addSeries(
				new XYseries()
					.setLabel("Costo rubros RRHH en " + currency))
		.addSeries(
				new XYseries()
					.setLabel("Costo rubros materiales en " + currency));

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
					.setTicks(ticks))
			.addAxis(
				new XYaxis(XYaxes.Y)
					.setPad(1.05f)
					.setTickOptions(
						new AxisTickRenderer()
							.setFormatString(currency + " %d")));

		Options options = new Options()
			.setSeriesDefaults(seriesDefaults)
			.setSeries(series)
			.setHighlighter(highlighter)
			.setAxes(axes);

		return new DCharts()
			.setDataSeries(dataSeries)
			.setOptions(options)
			.show();
	}
	
	public void buildChart(Project project){
		
		cleanLayoutCharts();
		
		Collection<ProjectLiquidation> projectLiquidations = LiquidationController.getProjectLiquidations(project.getId(), popupDateFieldYear.getValue(), project.getIsCurrencyDollar());
		
		String currency = project.getIsCurrencyDollar() ? "U$S" : "$";
		
		if(projectLiquidations.size() > 0)
		{	
			ProjectLiquidation[] array = (ProjectLiquidation[]) Array.newInstance(ProjectLiquidation.class, projectLiquidations.size());
			projectLiquidations.toArray(array);
			
			HashMap<Integer, ProjectLiquidation> projectLiquidationsByMonth = buildProjectLiquidacions(array);
			
			chart = buildChart_1(projectLiquidationsByMonth, currency);
			chart.setWidth(430, Unit.PIXELS);
			chart.setHeight(350, Unit.PIXELS);
			mainLayout.addComponent(chart, "top:19%;left:0px;");
			
			chart2 = buildChart_2(projectLiquidationsByMonth, currency);
			chart2.setWidth(430, Unit.PIXELS);
			chart2.setHeight(350, Unit.PIXELS);
			mainLayout.addComponent(chart2, "top:19%;left:450px;");
			
			chart3 = buildChart_3(projectLiquidationsByMonth, currency);
			chart3.setWidth(430, Unit.PIXELS);
			chart3.setHeight(350, Unit.PIXELS);
			mainLayout.addComponent(chart3, "top:60%;left:0px;");
			
			chart4 = buildChart_4(projectLiquidationsByMonth, currency);
			chart4.setWidth(430, Unit.PIXELS);
			chart4.setHeight(350, Unit.PIXELS);
			mainLayout.addComponent(chart4, "top:60%;left:450px;");
		}
		else
		{
			lblMessage.setValue("No hay datos en el a�o seleccionado para ese proyecto");
		}
	}

	HashMap<Integer, ProjectLiquidation> buildProjectLiquidacions(ProjectLiquidation[] projectLiquidations)
	{
		HashMap<Integer, ProjectLiquidation> projectLiquidacionByMonth = new HashMap<Integer, ProjectLiquidation>();
		projectLiquidacionByMonth.put(0, new ProjectLiquidation());
		projectLiquidacionByMonth.put(1, new ProjectLiquidation());
		projectLiquidacionByMonth.put(2, new ProjectLiquidation());
		projectLiquidacionByMonth.put(3, new ProjectLiquidation());
		projectLiquidacionByMonth.put(4, new ProjectLiquidation());
		projectLiquidacionByMonth.put(5, new ProjectLiquidation());
		projectLiquidacionByMonth.put(6, new ProjectLiquidation());
		projectLiquidacionByMonth.put(7, new ProjectLiquidation());
		projectLiquidacionByMonth.put(8, new ProjectLiquidation());
		projectLiquidacionByMonth.put(9, new ProjectLiquidation());
		projectLiquidacionByMonth.put(10, new ProjectLiquidation());
		projectLiquidacionByMonth.put(11, new ProjectLiquidation());
		
		for(int i = 0; i< projectLiquidations.length; i++)
		{
			int month = projectLiquidations[i].getAppliedDateTimeUTC().getMonth();
			projectLiquidacionByMonth.put(month, projectLiquidations[i]);
		}

		return projectLiquidacionByMonth;
	}

	Project getProjectById(int id)
	{
		for(Project p : projects)
		{
			if(p.getId() == id)
			{
				return p;
			}
		}
		return null;
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		super.enter(event);
		if(RequestContext.getRequestContext() != null){
			projects = ProjectController.getActiveProjects();
			cboxProject.removeAllItems();
			for(Project project : projects)
			{
				cboxProject.addItem(project.getId());
				cboxProject.setItemCaption(project.getId(), project.getName());
			}
			
			buildInputs();
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
				
		// popupDateFieldFrom
		popupDateFieldYear = new PopupDateField();
		popupDateFieldYear.setCaption("A�o");
		popupDateFieldYear.setImmediate(true);
		popupDateFieldYear.setWidth("105px");
		popupDateFieldYear.setHeight("-1px");
		popupDateFieldYear.setTabIndex(1);
		popupDateFieldYear.setRequired(true);
		mainLayout.addComponent(popupDateFieldYear, "top:105.0px;left:0.0px;");
		
		// cboxProject
		cboxProject = new ComboBox();
		cboxProject.setCaption("Proyecto");
		cboxProject.setImmediate(true);
		cboxProject.setWidth("245px");
		cboxProject.setHeight("-1px");
		cboxProject.setRequired(true);
		cboxProject.setInputPrompt("Seleccione el proyecto");
		cboxProject.setTabIndex(3);
		mainLayout.addComponent(cboxProject, "top:105.0px;left:120.0px;");

		return mainLayout;
	}

}
