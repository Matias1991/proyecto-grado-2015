package com.example.androidproject;

import java.util.ArrayList;
import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.androidproject.alerts.AlertDialogManager;
import com.example.androidproject.controllers.ReportsController;

import entities.VOProjectLiquidation;

public class ReportsCompanyEarningsPeso extends Activity {

	UserSession session;

	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reports_company_earnings_peso);
		ArrayList<VOProjectLiquidation> projects = null;

		session = new UserSession(getApplicationContext());

		ReportsController reportController = new ReportsController(
				getResources().getString(R.string.ip_server));
		try {
			projects = reportController
					.getProjectsLiquidationsWithMoreEarnings(false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		chartSettings(renderer, projects);
		LinearLayout linearLayout = (LinearLayout)findViewById(R.id.reportsCompanyEarningsPeso);
		linearLayout.addView(ChartFactory.getBarChartView(this, getBarDataset(projects), renderer, Type.DEFAULT));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.partner_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private XYMultipleSeriesDataset getBarDataset(ArrayList<VOProjectLiquidation> projects) {
		XYSeries earningsS = new XYSeries("Ganancia ($)");
		XYSeries reserveS = new XYSeries("Reserva ($)");

		int i = 1;
		for (VOProjectLiquidation project : projects) {
			earningsS.add(i, project.getEarnings());
			reserveS.add(i, project.getReserve());
			i++;
		}

		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		dataset.addSeries(earningsS);
		dataset.addSeries(reserveS);

		return dataset;
	}

	private void chartSettings(XYMultipleSeriesRenderer renderer, ArrayList<VOProjectLiquidation> projects) {
		renderer.setChartTitle("Proyectos en pesos con mayor ganancia del año corriente");
		double maxNum = 0.0;
		double minNum = 0.0;
		if(projects != null){
			int i = 1;
			for(VOProjectLiquidation project : projects){
				renderer.addXTextLabel(i, project.getProject().getName());
				// Get Y max
				if(maxNum < project.getEarnings() || maxNum < project.getReserve()){
					if(project.getEarnings() > project.getReserve()){
						maxNum = project.getEarnings();
					} else {
						maxNum = project.getReserve();
					}
				}
				
				// Get Y min
				if(minNum > project.getEarnings() || minNum > project.getReserve()){
					if(project.getEarnings() > project.getReserve()){
						minNum = project.getReserve();
					} else {
						minNum = project.getEarnings();
					}
				}
				
				i++;
			}
		}
		
		renderer.setYAxisMax(maxNum + 1);
		renderer.setYAxisMin(minNum - 1);

		renderer.setXAxisMin(0);
		renderer.setXAxisMax(6);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setBarSpacing(0.5);
		renderer.setShowGrid(true);
		renderer.setGridColor(Color.GRAY);
		renderer.setXLabels(0); // sets the number of integer labels to appear		
		
		renderer.setAxisTitleTextSize(30);
	    renderer.setChartTitleTextSize(30);
	    renderer.setLabelsTextSize(30);
	    renderer.setLegendTextSize(30);
	    renderer.setXLabelsAngle(45);
	    renderer.setYTitle("$");
	    renderer.setMargins(new int[] {70, 60, 200, 0});
	    SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	    r.setColor(Color.BLUE);
	    r.setChartValuesTextSize(30);
	    r.setDisplayChartValues(true);
	    renderer.addSeriesRenderer(r);
	    r = new SimpleSeriesRenderer();
	    r.setColor(Color.CYAN);
	    r.setChartValuesTextSize(30);
	    r.setDisplayChartValues(true);
	    renderer.addSeriesRenderer(r);
	    renderer.setBackgroundColor(Color.BLACK);
	    renderer.setApplyBackgroundColor(true);
	}
}
