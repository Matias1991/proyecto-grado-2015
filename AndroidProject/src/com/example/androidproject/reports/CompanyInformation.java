package com.example.androidproject.reports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.androidproject.R;
import com.example.androidproject.UserSession;
import com.example.androidproject.R.id;
import com.example.androidproject.R.layout;
import com.example.androidproject.R.menu;
import com.example.androidproject.R.string;
import com.example.androidproject.alerts.AlertDialogManager;
import com.example.androidproject.controllers.ReportsController;

import entities.VOCompanyLiquidation;
import entities.VOProject;

public class CompanyInformation extends Activity {

	UserSession session;

	private TextView txtPartner1Name, txtPartner2Name, txtCompanyEarningsPartner1,txtCompanyEarningsPartner1Dollar,
	txtCompanyEarningsPartner2, txtDate, txtIVAPurchase, txtIVASale,
	txtCompanyEarningsPartner2Dollar;
	Calendar myCalendar = Calendar.getInstance();

	ArrayList<VOProject> listProjects;

	SimpleDateFormat format = new SimpleDateFormat("MM-yyyy");
	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_company_information);
		session = new UserSession(getApplicationContext());

		txtPartner1Name = (TextView) findViewById(R.id.txtPartner1Name);
		txtPartner2Name = (TextView) findViewById(R.id.txtPartner2Name);
		txtDate = (TextView) findViewById(R.id.txtDateInfoCompany);
		txtCompanyEarningsPartner1Dollar = (TextView) findViewById(R.id.txtCompanyEarningsPartner1Dollar);
		txtCompanyEarningsPartner2Dollar = (TextView) findViewById(R.id.txtCompanyEarningsPartner2Dollar);
		txtCompanyEarningsPartner1 = (TextView) findViewById(R.id.txtCompanyEarningsPartner1);
		txtCompanyEarningsPartner2 = (TextView) findViewById(R.id.txtCompanyEarningsPartner2);
		txtIVAPurchase = (TextView) findViewById(R.id.txtIVAPurchase);
		txtIVASale = (TextView) findViewById(R.id.txtIVASale);
		
		final ReportsController reportController = new ReportsController(
				getResources().getString(R.string.ip_server));
		
		try {
			VOCompanyLiquidation voCompanyInformation =  reportController
					.getCompanyInfo(//format.parse(txtDate.getText().toString()),
							new Date(),
							2);
			if(voCompanyInformation != null){
				txtPartner1Name.setText(voCompanyInformation.getPartner1FullName());
				txtPartner2Name.setText(voCompanyInformation.getPartner2FullName());
				txtIVAPurchase.setText("IVA compra $ " + String.valueOf(voCompanyInformation.getIVAPurchase()));
				txtIVASale.setText("IVA venta $ " + String.valueOf(voCompanyInformation.getIVASale()));
				txtCompanyEarningsPartner1Dollar.setText("$ " + voCompanyInformation.getPartner1EarningsDollar());
				txtCompanyEarningsPartner2Dollar.setText("U$S " + voCompanyInformation.getPartner2EarningsDollar());
				txtCompanyEarningsPartner2.setText("$ " + String.valueOf(voCompanyInformation.getPartner2EarningsPeso()));
				txtCompanyEarningsPartner1.setText("$ " + String.valueOf(voCompanyInformation.getPartner1EarningsPeso()));
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		txtDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				createDialogWithoutDateField().show();

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
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			myCalendar.set(Calendar.YEAR, year);
			myCalendar.set(Calendar.MONTH, monthOfYear);
		}

	};

	private DatePickerDialog createDialogWithoutDateField() {
		DatePickerDialog dpd = new DatePickerDialog(this, date,
				myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
				1);
		try {
			java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass()
					.getDeclaredFields();
			for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
				if (datePickerDialogField.getName().equals("mDatePicker")) {
					datePickerDialogField.setAccessible(true);
					DatePicker datePicker = (DatePicker) datePickerDialogField
							.get(dpd);
					java.lang.reflect.Field[] datePickerFields = datePickerDialogField
							.getType().getDeclaredFields();
					for (java.lang.reflect.Field datePickerField : datePickerFields) {
						Log.i("test", datePickerField.getName());
						if ("mDaySpinner".equals(datePickerField.getName())) {
							datePickerField.setAccessible(true);
							Object dayPicker = datePickerField.get(datePicker);
							((View) dayPicker).setVisibility(View.GONE);
						}
					}
				}
			}
		} catch (Exception ex) {
		}
		return dpd;
	}

}
