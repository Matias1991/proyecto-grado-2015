package com.example.androidproject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.androidproject.alerts.AlertDialogManager;
import com.example.androidproject.controllers.BillController;
import com.example.androidproject.controllers.ProjectController;

import entities.VOBill;
import entities.VOProject;

public class CreateBill extends Activity {

	private Spinner ivaTypes, projects;
	private TextView txtCode, txtDescription, txtDateApplied,
			txtAmountWithoutIVA, txtAmountWithIVA, txtTypeExchange;
	private Button btnCreate, btnCancel;
	Calendar myCalendar = Calendar.getInstance();
	UserSession session;

	ArrayList<VOProject> listProjects;

	AlertDialogManager alert = new AlertDialogManager();

	void buildFields() {
		txtCode = (TextView) findViewById(R.id.txtCode);
		txtDescription = (TextView) findViewById(R.id.txtDescription);
		txtDateApplied = (TextView) findViewById(R.id.txtDateApplied);
		txtAmountWithoutIVA = (TextView) findViewById(R.id.txtAmountWithoutIVA);
		txtTypeExchange = (TextView) findViewById(R.id.txtTypeExchange);
		txtAmountWithIVA = (TextView) findViewById(R.id.txtAmountWithIVA);
		btnCreate = (Button) findViewById(R.id.btnCreate);
		btnCancel = (Button) findViewById(R.id.btnCancel);
	}

	void cleanInputs() {
		txtCode.setText("");
		txtDescription.setText("");
		projects.setSelection(0);
		txtAmountWithoutIVA.setText("");
		txtTypeExchange.setText("");
		ivaTypes.setSelection(0);
		txtAmountWithIVA.setText("");
		updateLabel();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_bill);

		session = new UserSession(getApplicationContext());

		buildFields();
		buildIVATypes();
		buildProjects();

		txtTypeExchange.setEnabled(false);

		txtDateApplied.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				createDialogWithoutDateField().show();
			}
		});

		txtAmountWithoutIVA.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

				double totalAmount = 0;
				if (!s.equals("")) { 
					if (!txtAmountWithoutIVA.getText().toString().matches("")) {
						double amount = Double.parseDouble(txtAmountWithoutIVA
								.getText().toString());
						if (ivaTypes.getSelectedItem().toString() == "IVA 22%") {
							totalAmount = amount * 1.22;
						} else if (ivaTypes.getSelectedItem().toString() == "IVA 10%") {

							totalAmount = amount * 1.10;
						} else
							totalAmount = amount;
					}
				}
				DecimalFormat df = new DecimalFormat("#.00");
				String strTotalAmount = df.format(totalAmount);
				if (totalAmount == 0)
					txtAmountWithIVA.setText("");
				else
					txtAmountWithIVA.setText(strTotalAmount);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});

		ivaTypes.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				String selCat = ivaTypes.getItemAtPosition(arg2).toString();

				double totalAmount = 0;
				if (!txtAmountWithoutIVA.getText().toString().matches("")) {
					double amount = Double.parseDouble(txtAmountWithoutIVA
							.getText().toString());
					if (selCat == "IVA 22%") {
						totalAmount = amount * 1.22;
					} else if (selCat == "IVA 10%") {
						totalAmount = amount * 1.10;
					} else
						totalAmount = amount;
				}
				DecimalFormat df = new DecimalFormat("#.00");
				String strTotalAmount = df.format(totalAmount);
				if (totalAmount == 0)
					txtAmountWithIVA.setText("");
				else
					txtAmountWithIVA.setText(strTotalAmount);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			
			}
		});

		projects.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				String selCat = projects.getItemAtPosition(arg2).toString();

				if (selCat != "Seleccione el proyecto") {
					VOProject voProject = getProject(selCat);
					if (voProject.getIsCurrencyDollar()) {
						txtAmountWithoutIVA.setHint("Importe sin IVA (U$S)");
						txtAmountWithIVA.setHint("Importe IVA incl. (U$S)");
						txtTypeExchange.setEnabled(true);
					} else {
						txtAmountWithoutIVA.setHint("Importe sin IVA ($)");
						txtAmountWithIVA.setHint("Importe IVA incl. ($)");
						txtTypeExchange.setEnabled(false);
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		btnCreate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (validateFields()) {
					
					
					BillController billController = new BillController(
							getResources().getString(R.string.ip_server));
					try {
						VOBill voBill = new VOBill();
						voBill.setCode(txtCode.getText().toString());
						voBill.setDescription(txtDescription.getText()
								.toString());
						voBill.setProjectId(getProject(
								projects.getSelectedItem().toString()).getId());
						SimpleDateFormat format = new SimpleDateFormat(
								"MM-yyyy");
						voBill.setAppliedDateTimeUTC(format
								.parse(txtDateApplied.getText().toString()));

						String ivaType = ivaTypes.getSelectedItem().toString();

						if (ivaType == "IVA 22%")
							voBill.setIvaType(3);
						else if (ivaType == "IVA 10%")
							voBill.setIvaType(2);
						else
							voBill.setIvaType(1);

						if (txtTypeExchange.isEnabled()) {
							voBill.setAmountDollar(Double
									.parseDouble(txtAmountWithoutIVA.getText()
											.toString()));
							voBill.setTypeExchange(Double
									.parseDouble(txtTypeExchange.getText()
											.toString()));
							voBill.setIsCurrencyDollar(true);
						} else {
							voBill.setAmountPeso(Double
									.parseDouble(txtAmountWithoutIVA.getText()
											.toString()));
							voBill.setIsCurrencyDollar(false);
						}

						boolean result = billController.createBill(voBill);
						if (result) {
							cleanInputs();
							alert.showAlertDialog(CreateBill.this,
									"Creacion de factura",
									"La factura fue creada correctamente");
						} else
							alert.showAlertDialog(CreateBill.this, "Error",
									"Ocurrio un error al crear la factura.");

					} catch (Exception e) {
						alert.showAlertDialog(CreateBill.this, "Error",
								e.getMessage());
					}
				}
			}
		});

		btnCancel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				Intent i = new Intent(getApplicationContext(), MainMenu.class);
				startActivity(i);
				finish();
			}
		});
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

	public void buildIVATypes() {

		ivaTypes = (Spinner) findViewById(R.id.spinnerIVATypes);
		List<String> list = new ArrayList<String>();
		list.add("IVA 22%");
		list.add("IVA 10%");
		list.add("IVA 0%");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ivaTypes.setAdapter(dataAdapter);
	}

	public void buildProjects() {

		projects = (Spinner) findViewById(R.id.spinnerProjects);
		List<String> list = new ArrayList<String>();

		ProjectController proj = new ProjectController(getResources()
				.getString(R.string.ip_server));
		int userContextId = Integer.parseInt(session.getUserDetails().get(
				"UserId"));
		try {

			listProjects = proj.getActiveProjects(userContextId);

			list.add("Seleccione el proyecto");
			for (VOProject voProject : listProjects) {
				list.add(voProject.getName());
			}

		} catch (Exception e) {
			alert.showAlertDialog(CreateBill.this, "Error", e.getMessage());
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		projects.setAdapter(dataAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_bill, menu);
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

	private void updateLabel() {

		String myFormat = "MM-yyyy"; // In which you need put here
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

		txtDateApplied.setText(sdf.format(myCalendar.getTime()));
	}

	boolean validateFields() {
		
		
		
		StringBuilder strBuilder = new StringBuilder();
		if (txtCode.getText().toString().trim().length() == 0)
			strBuilder.append("Código \n");

		if (txtDescription.getText().toString().trim().length() == 0)
			strBuilder.append("Descripción \n");

		if (projects.getSelectedItem().toString() == "Seleccione el proyecto")
			strBuilder.append("Proyecto \n");

		if (txtAmountWithoutIVA.getText().toString().trim().length() == 0)
			strBuilder.append("Importe sin IVA \n");

		if (txtTypeExchange.isEnabled()
				&& txtTypeExchange.getText().toString().trim().length() == 0)
			strBuilder.append("Cotización \n");

		if (txtAmountWithIVA.getText().toString().trim().length() == 0)
			strBuilder.append("Importe IVA incl. \n");

		if (txtDateApplied.getText().toString().trim().length() == 0)
			strBuilder.append("Correspondiente al mes");

		if (strBuilder.length() > 0) {
			alert.showAlertDialog(CreateBill.this,
					"Debe ingresar los siguientes campos..",
					strBuilder.toString());
			return false;
		} else
			return true;
	}	
}
