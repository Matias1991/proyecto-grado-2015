package com.example.androidproject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import com.example.androidproject.controllers.UserController;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class CreateBill extends Activity {

	private Spinner ivaTypes, projects;
	private TextView txtDateApplied;
	Calendar myCalendar = Calendar.getInstance();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_bill);
		
		addItemsOnSpinner1();
		addItemsOnSpinner2();
        
        txtDateApplied = (TextView) findViewById(R.id.txtAppliedDate);
     
        txtDateApplied.setOnClickListener(new OnClickListener() {

        	@Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
        		createDialogWithoutDateField().show();
            }
        });
	}
	
	DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

	    @Override
	    public void onDateSet(DatePicker view, int year, int monthOfYear,
	            int dayOfMonth) {
	        // TODO Auto-generated method stub
	        myCalendar.set(Calendar.YEAR, year);
	        myCalendar.set(Calendar.MONTH, monthOfYear);
	        updateLabel();
	    }

	};

	
	private DatePickerDialog createDialogWithoutDateField() {
        DatePickerDialog dpd = new DatePickerDialog(this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), 1);
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
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
        } 
        catch (Exception ex) {
        }
        return dpd;
    }
	// add items into spinner dynamically
	  public void addItemsOnSpinner1() {

		ivaTypes = (Spinner) findViewById(R.id.spinnerIVATypes);
		List<String> list = new ArrayList<String>();
		list.add("IVA 22%");
		list.add("IVA 10%");
		list.add("IVA 0%");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		ivaTypes.setAdapter(dataAdapter);
	  }
	  
	
	// add items into spinner dynamically
		  public void addItemsOnSpinner2() {

			  projects = (Spinner) findViewById(R.id.spinnerProjects);
			List<String> list = new ArrayList<String>();
			list.add("proyecto 1");
			list.add("proyecto 2");
			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
			dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
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

	    String myFormat = "MM-yyyy"; //In which you need put here
	    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

	    txtDateApplied.setText(sdf.format(myCalendar.getTime()));
	    }
}
