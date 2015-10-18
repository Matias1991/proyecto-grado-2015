package com.example.androidproject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.textservice.TextInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.example.androidproject.alerts.AlertDialogManager;
import com.example.androidproject.controllers.ProjectController;
import com.example.androidproject.controllers.ReportsController;

import entities.VOProject;
import entities.VOProjectLiquidation;

public class ProjectInformation extends Activity {

	UserSession session;

	private Spinner projects;
	private TextView txtPartner1Name, txtReserve, txtTotalBills,
			txtPartner2Name, txtEarningsPartner1, txtEarningsPartner2, txtDate;
	ArrayList<VOProject> listProjects;
	Calendar myCalendar = Calendar.getInstance();

	SimpleDateFormat format = new SimpleDateFormat("MM-yyyy");
	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_project_information);
		session = new UserSession(getApplicationContext());

		txtPartner1Name = (TextView) findViewById(R.id.txtPartner1Name);
		txtPartner2Name = (TextView) findViewById(R.id.txtPartner2Name);
		txtEarningsPartner1 = (TextView) findViewById(R.id.txtEarningsPartner1);
		txtEarningsPartner2 = (TextView) findViewById(R.id.txtEarningsPartner2);
		txtReserve = (TextView) findViewById(R.id.txtReserve);
		txtTotalBills = (TextView) findViewById(R.id.txtTotalBills);
		txtDate = (TextView) findViewById(R.id.txtDateInfoProject);
		projects = (Spinner) findViewById(R.id.spinnerProjectInfo);
		
		loadProjects();

		final ReportsController reportController = new ReportsController(
				getResources().getString(R.string.ip_server));

		if(projects != null){
			projects.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {

					String selProj = projects.getItemAtPosition(arg2).toString();

					try {
						if (selProj != "Seleccione el proyecto"
								&& format.parse(txtDate.getText().toString()) != null) {

							try {
								VOProject voProject = getProject(selProj);
								ArrayList<VOProjectLiquidation> projectList = reportController
										.getProjectsInfo(format.parse(txtDate
												.getText().toString()), voProject
												.getId());

								if (projectList.size() > 0) {
									txtPartner1Name.setVisibility(View.VISIBLE);
									txtPartner2Name.setVisibility(View.VISIBLE);
									for (VOProjectLiquidation voProjectLiquidation : projectList) {
										txtPartner1Name.setText("Socio: "
												+ voProjectLiquidation
														.getPartner1Name()
												+ " "
												+ voProjectLiquidation
														.getPartner1Lastname());
										txtPartner2Name.setText("Socio: "
												+ voProjectLiquidation
														.getPartner2Name()
												+ " "
												+ voProjectLiquidation
														.getPartner2Lastname());
										if (voProjectLiquidation.isCurrencyDollar()) {
											txtEarningsPartner1.setText("Ganancia: U$S "
													+ String.valueOf(voProjectLiquidation
															.getPartner1Earning()));
											txtEarningsPartner2.setText("Ganancia: U$S "
													+ String.valueOf(voProjectLiquidation
															.getPartner2Earning()));
											txtReserve.setText("Reserva: U$S "
													+ String.valueOf(voProjectLiquidation
															.getReserve()));
											txtTotalBills.setText("Facturación total: U$S "
													+ String.valueOf(voProjectLiquidation
															.getReserve()));
										} else {
											txtEarningsPartner1.setText("Ganancia: $ "
													+ String.valueOf(voProjectLiquidation
															.getPartner1Earning()));
											txtEarningsPartner2.setText("Ganancia: $ "
													+ String.valueOf(voProjectLiquidation
															.getPartner2Earning()));
											txtReserve.setText("Reserva: $ "
													+ String.valueOf(voProjectLiquidation
															.getReserve()));
											txtTotalBills.setText("Facturación total: $ "
													+ String.valueOf(voProjectLiquidation
															.getTotalBills()));
										}

									}

								} else {
									alert.showAlertDialog(ProjectInformation.this,
											"Atención",
											"No hay información para el proyecto seleccionado");
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						} else {
							txtPartner1Name.setText("");
							txtPartner2Name.setText("");
						}
					} catch (ParseException e) {
						alert.showAlertDialog(ProjectInformation.this, "Error",
								e.getMessage());
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {

				}
			});
	
		} else {
			new AlertDialog.Builder(ProjectInformation.this)
			.setTitle("Atención")
			.setMessage("No hay proyectos cargados en el sistema")
			.setPositiveButton(android.R.string.yes,
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						}
					}).setIcon(android.R.drawable.ic_dialog_alert)
			.show();
		}
		
		txtDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				createDialogWithoutDateField().show();
			}
		});

	}
	
	private void updateLabel() {

		String myFormat = "MM-yyyy"; // In which you need put here
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

		txtDate.setText(sdf.format(myCalendar.getTime()));
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

	public void loadProjects() {
		try {
			List<String> list = new ArrayList<String>();

			ProjectController proj = new ProjectController(getResources()
					.getString(R.string.ip_server));
			int userContextId = Integer.parseInt(session.getUserDetails().get(
					"UserId"));

			listProjects = proj.getActiveProjects(userContextId);

			list.add("Seleccione el proyecto");
			for (VOProject voProject : listProjects) {
				list.add(voProject.getName());
			}
			
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, list);
			dataAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			projects.setAdapter(dataAdapter);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	VOProject getProject(String name) {
		for (VOProject voProject : listProjects) {
			if (voProject.getName() == name) {
				return voProject;
			}
		}
		return null;
	}

	DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			myCalendar.set(Calendar.YEAR, year);
			myCalendar.set(Calendar.MONTH, monthOfYear);
			updateLabel();
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
