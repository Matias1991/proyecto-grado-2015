package com.example.androidproject;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.androidproject.alerts.AlertDialogManager;
import com.example.androidproject.controllers.BillController;
import com.example.androidproject.controllers.ChargeController;
import com.example.androidproject.controllers.ProjectController;

import entities.VOBill;
import entities.VOCharge;
import entities.VOProject;

public class CreateCharge extends Activity {

	private TextView txtChargeNumber, txtDescription, txtAmount;
	private Button btnCreate, btnCancel;
	private Spinner bills, projects;
	UserSession session;
	ArrayList<VOBill> listBills;
	ArrayList<VOProject> listProjects;

	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_charge);

		session = new UserSession(getApplicationContext());

		buildFields();
		buildProjects();
		buildBills(0);

		projects.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				String selCat = projects.getItemAtPosition(arg2).toString();

				if (selCat != "Seleccione el proyecto") {
					VOProject voProject = getProject(selCat);

					buildBills(voProject.getId());
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

		btnCreate.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (validateFields()) {
					ChargeController chargeController = new ChargeController(
							getResources().getString(R.string.ip_server));
					try {
						VOCharge voCharge = new VOCharge();
						voCharge.setChargeCode(txtChargeNumber.getText()
								.toString());
						voCharge.setDescription(txtDescription.getText()
								.toString());

						voCharge.setBillId(getBill(
								bills.getSelectedItem().toString()).getId());

						if (getBill(bills.getSelectedItem().toString())
								.getIsCurrencyDollar()) {
							voCharge.setAmountDollar(Double
									.parseDouble(txtAmount.getText().toString()));
							voCharge.setIsCurrencyDollar(true);
						} else {
							voCharge.setAmountPeso(Double.parseDouble(txtAmount
									.getText().toString()));
							voCharge.setIsCurrencyDollar(false);
						}

						boolean result = chargeController
								.createCharge(voCharge);
						if (result) {
							cleanInputs();
							alert.showAlertDialog(CreateCharge.this,
									"Creación de cobro",
									"El cobro fue cargado correctamente");
						} else {
							alert.showAlertDialog(CreateCharge.this, "Error",
									"Ocurrió un error al crear el cobro.");
						}

					} catch (Exception e) {
						alert.showAlertDialog(CreateCharge.this, "Error",
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_charge, menu);
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

	void cleanInputs() {
		txtChargeNumber.setText("");
		txtDescription.setText("");
		txtAmount.setText("");
		projects.setSelection(0);
		bills.setSelection(0);
	}

	void buildFields() {
		txtChargeNumber = (TextView) findViewById(R.id.txtChargeNumber);
		txtDescription = (TextView) findViewById(R.id.txtDescription);
		txtAmount = (TextView) findViewById(R.id.txtChargeAmount);
		btnCreate = (Button) findViewById(R.id.btnCreate);
		btnCancel = (Button) findViewById(R.id.btnCancel);
	}

	boolean validateFields() {
		StringBuilder strBuilder = new StringBuilder();

		if (bills.getSelectedItem().toString() == "Seleccione el proyecto")
			strBuilder.append("Proyecto \n");

		if (bills.getSelectedItem().toString() == "Seleccione la factura")
			strBuilder.append("Factura \n");

		if (txtChargeNumber == null
				|| (txtChargeNumber != null && txtChargeNumber.getText()
						.toString().trim().length() == 0))
			strBuilder.append("Nº de recibo \n");

		if (txtDescription == null
				|| (txtDescription != null && txtDescription.getText()
						.toString().trim().length() == 0))
			strBuilder.append("Descripción \n");

		if (txtAmount == null
				|| (txtAmount != null && txtAmount.getText().toString().trim()
						.length() == 0))
			strBuilder.append("Importe \n");

		if (strBuilder.length() > 0) {
			alert.showAlertDialog(CreateCharge.this,
					"Debe ingresar los siguientes campos..",
					strBuilder.toString());
			return false;
		} else {
			return true;
		}
	}

	public void buildBills(int projectId) {

		bills = (Spinner) findViewById(R.id.spinnerBills);
		List<String> list = new ArrayList<String>();

		BillController billController = new BillController(getResources()
				.getString(R.string.ip_server));
		try {

			listBills = billController.getBills(projectId);

			if (listBills.size() == 0) {
				bills.setEnabled(false);
			} else {
				bills.setEnabled(true);
			}

			list.add("Seleccione la factura");
			for (VOBill voBill : listBills) {
				list.add(voBill.getCode());
			}

		} catch (Exception e) {
			alert.showAlertDialog(CreateCharge.this, "Error", e.getMessage());
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		bills.setAdapter(dataAdapter);

		bills.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {

				String selectedBill = bills.getItemAtPosition(arg2).toString();

				if (selectedBill != "Seleccione la factura") {
					VOBill voBill = getBill(selectedBill);

					if (voBill.getIsCurrencyDollar()) {
						txtAmount.setHint("Importe (U$S)");
					} else {
						txtAmount.setHint("Importe ($)");
					}
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	VOBill getBill(String name) {
		for (VOBill voBill : listBills) {
			if (voBill.getCode() == name) {
				return voBill;
			}
		}
		return null;
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
			alert.showAlertDialog(CreateCharge.this, "Error", e.getMessage());
		}

		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		projects.setAdapter(dataAdapter);
	}

	VOProject getProject(String name) {
		for (VOProject voProject : listProjects) {
			if (voProject.getName() == name) {
				return voProject;
			}
		}
		return null;
	}

}
