package com.example.androidproject;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.androidproject.alerts.AlertDialogManager;
import com.example.androidproject.controllers.ReportsController;

import entities.VOProjectLiquidation;

public class ReportCompanyEarnings extends Activity {

	 private Button btnProjectsWithMoreEarnings = (Button)
	 findViewById(R.id.btnProjectsWithMoreEarnings);
	UserSession session;

	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reports);
		ArrayList<VOProjectLiquidation> projects = null;

		session = new UserSession(getApplicationContext());

		ReportsController reportController = new ReportsController(
				getResources().getString(R.string.ip_server));
		try {
			projects = reportController
					.getProjectsLiquidationsWithMoreEarnings(new Date(),
							new Date());
			projects.size();
		} catch (Exception e) {
			e.printStackTrace();
		}

		 
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

}
