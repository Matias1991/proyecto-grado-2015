package com.example.androidproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.androidproject.alerts.AlertDialogManager;

public class Reports extends Activity {

	UserSession session;

	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reports);

		session = new UserSession(getApplicationContext());
		
		final Button btnCompanyEarnings = (Button) findViewById(R.id.btnProjectsWithMoreEarnings);
		final Button btnInfoProjects = (Button) findViewById(R.id.btnProjectInfo);
		final Button btnLiquidations = (Button) findViewById(R.id.btnLiquidations);
		
		
		btnCompanyEarnings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Reports.this)
						.setTitle("Atento")
						.setMessage("Aun no va a ningun lado")
						.setPositiveButton(android.R.string.yes,
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).setIcon(android.R.drawable.ic_dialog_alert)
						.show();
			}
		});


		btnInfoProjects.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(), ProjectInformation.class);
				startActivity(i);
				finish();
			}
		});
		

		btnLiquidations.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(Reports.this)
				.setTitle("Atento")
				.setMessage("Aun no va a ningun lado")
				.setPositiveButton(android.R.string.yes,
								new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
							}
						}).setIcon(android.R.drawable.ic_dialog_alert)
				.show();
			}
		});

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
