package com.example.androidproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.androidproject.reports.Reports;

public class MainMenu extends Activity {

	UserSession session;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_menu);

		session = new UserSession(getApplicationContext());

		final Button btnCreateBill = (Button) findViewById(R.id.btnCreateBill);
		final Button btnCreateCharge = (Button) findViewById(R.id.btnCreateCharge);
		final Button btnReports = (Button) findViewById(R.id.btnReports);
		final Button btnLogout = (Button) findViewById(R.id.btnLogout);

		session.checkLogin();

		if(session.getUserDetails().get("UserType") == "MANAGER")
		{
			btnReports.setBackgroundDrawable(null);
			btnReports.setVisibility(View.GONE);
		}
	
		btnCreateBill.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(), CreateBill.class);
				startActivity(i);
			}
		});

		btnCreateCharge.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						CreateCharge.class);
				startActivity(i);
			}
		});

		btnReports.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),
						Reports.class);
				startActivity(i);
			}
		});
		
		btnLogout.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				session.logoutUser();
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
	
	@Override
	public void onBackPressed(){
		finish();
	}
}
