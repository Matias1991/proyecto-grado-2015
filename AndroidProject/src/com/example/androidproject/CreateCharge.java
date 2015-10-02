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

import entities.VOBill;
import entities.VOCharge;

public class CreateCharge extends Activity {

	private TextView txtChargeNumber, txtDescription, txtAmount,
			txtTypeExchange;
	private Button btnCreate, btnCancel;
	private Spinner bills;
	UserSession session;
	ArrayList<VOBill> listBills;

	AlertDialogManager alert = new AlertDialogManager();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_charge);

		session = new UserSession(getApplicationContext());

		buildFields();
		buildBills();

		txtTypeExchange.setEnabled(false);

		// txtAmount.addTextChangedListener(new TextWatcher() {
		//
		// @Override
		// public void onTextChanged(CharSequence s, int start, int before,
		// int count) {
		//
		// double amount = 0;
		// if(!s.equals("") ) {
		// if (!txtAmount.getText().toString().matches("")) {
		// amount = Double.parseDouble(txtAmount.getText().toString());
		// }
		// }
		// DecimalFormat df = new DecimalFormat("#.00");
		// String strTotalAmount = df.format(amount);
		// if(amount == 0)
		// txtAmount.setText("");
		// else
		// txtAmount.setText(strTotalAmount);
		// }
		//
		// @Override
		// public void beforeTextChanged(CharSequence s, int start, int count,
		// int after) {
		// // TODO Auto-generated method stub
		//
		// }
		//
		// @Override
		// public void afterTextChanged(Editable s) {
		// // TODO Auto-generated method stub
		// }
		// });

		bills.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub

				String selectedBill = bills.getItemAtPosition(arg2).toString();

				if (selectedBill != "Seleccione la factura") {
					VOBill voBill = getBill(selectedBill);

					if (voBill.getIsCurrencyDollar()) {
						txtAmount.setHint("Importe (U$S)");
						txtTypeExchange.setEnabled(true);
					} else {
						txtAmount.setHint("Importe ($)");
						txtTypeExchange.setEnabled(false);
					}
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

						if (txtTypeExchange.isEnabled()) {
							voCharge.setAmountDollar(Double
									.parseDouble(txtAmount.getText().toString()));
							voCharge.setTypeExchange(Double
									.parseDouble(txtTypeExchange.getText()
											.toString()));
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
		txtTypeExchange.setText("");
	}

	void buildFields() {
		txtChargeNumber = (TextView) findViewById(R.id.txtChargeNumber);
		txtDescription = (TextView) findViewById(R.id.txtDescription);
		txtAmount = (TextView) findViewById(R.id.txtAmount);
		txtTypeExchange = (TextView) findViewById(R.id.txtTypeExchange);
		btnCreate = (Button) findViewById(R.id.btnCreate);
		btnCancel = (Button) findViewById(R.id.btnCancel);
	}

	boolean validateFields() {
		StringBuilder strBuilder = new StringBuilder();

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

		if (txtTypeExchange.isEnabled()
				&& (txtTypeExchange == null || txtTypeExchange != null
						&& txtTypeExchange.getText().toString().trim().length() == 0))
			strBuilder.append("Cotización \n");

		if (strBuilder.length() > 0) {
			alert.showAlertDialog(CreateCharge.this,
					"Debe ingresar los siguientes campos..",
					strBuilder.toString());
			return false;
		} else {
			return true;
		}
	}

	public void buildBills() {
		bills = (Spinner) findViewById(R.id.spinnerBills);
		List<String> list = new ArrayList<String>();

		BillController billController = new BillController(getResources()
				.getString(R.string.ip_server));
		int userContextId = Integer.parseInt(session.getUserDetails().get(
				"UserId"));
		try {

			listBills = billController.getBills(userContextId);

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
	}

	VOBill getBill(String name) {
		for (VOBill voBill : listBills) {
			if (voBill.getCode() == name) {
				return voBill;
			}
		}
		return null;
	}

}
