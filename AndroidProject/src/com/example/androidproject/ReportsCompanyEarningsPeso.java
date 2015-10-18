package com.example.androidproject;

import java.util.ArrayList;
import java.util.Date;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
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

		XYMultipleSeriesRenderer renderer = getBarRenderer();
		chartSettings(renderer, projects);
//		Intent intent = ChartFactory.getBarChartIntent(this,
//				getBarDataset(projects), renderer, Type.DEFAULT);
//		startActivity(intent);
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

	// To display Android AChartEngine bar chart we need to create a bars
	// renderer
	public XYMultipleSeriesRenderer getBarRenderer() {
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		renderer.setAxisTitleTextSize(16);
		renderer.setChartTitleTextSize(25);
		renderer.setLabelsTextSize(20);
		renderer.setLegendTextSize(20);
		renderer.setMargins(new int[] { 40, 40, 15, 0 });
		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		r.setColor(Color.BLUE);
		renderer.addSeriesRenderer(r);
		r = new SimpleSeriesRenderer();
		r.setColor(Color.RED);
		renderer.addSeriesRenderer(r);
		return renderer;
	}

	private void chartSettings(XYMultipleSeriesRenderer renderer, ArrayList<VOProjectLiquidation> projects) {
		renderer.setChartTitle("Proyectos en $ con mayor ganancia en los últimos 6 meses");
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

		renderer.setXAxisMin(2);
		renderer.setXAxisMax(10);
		renderer.setYLabelsAlign(Align.RIGHT);
		renderer.setBarSpacing(0.5);
		renderer.setShowGrid(true);
		renderer.setGridColor(Color.GRAY);
		renderer.setXLabels(0); // sets the number of integer labels to appear
	}
}
