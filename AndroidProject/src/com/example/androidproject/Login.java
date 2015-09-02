package com.example.androidproject;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity {
	private final String NAMESPACE = "http://service.servicelayer";
	private final String URL = "http://10.0.2.2:8080/WebService/services/ServiceMobile?wsdl";
	private final String SOAP_ACTION = "urn:login";
	private final String METHOD_NAME = "login";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
	     
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		final Button button = (Button) findViewById(R.id.btnLoginApp);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	EditText txtUsername = (EditText) findViewById(R.id.txtUsernameApp);
                EditText txtPassword = (EditText) findViewById(R.id.txtPasswordApp);
            	String username = txtUsername.getText().toString();
            	String password = txtPassword.getText().toString();
            			
            	System.out.println(username +" "+password);
            	
            	//Create request
        		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        		//Property which holds input parameters
        		PropertyInfo loginUsernamePI = new PropertyInfo();
        		PropertyInfo loginPasswordPI = new PropertyInfo();
        		//Set Name
        		loginUsernamePI.setName("userName");
        		loginPasswordPI.setName("password");
        		//Set Value
        		loginUsernamePI.setValue(username);
        		loginPasswordPI.setValue(password);
        		//Set dataType
        		loginUsernamePI.setType(String.class);
        		loginPasswordPI.setType(String.class);
        		//Add the property to request object
        		request.addProperty(loginUsernamePI);
        		request.addProperty(loginPasswordPI);
        		
        		//Create envelope
        		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
        				SoapEnvelope.VER11);
        		envelope.dotNet = true;
        		//Set output SOAP object
        		envelope.setOutputSoapObject(request);
        		//Create HTTP call object
        		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        		
        		try {
        			//Invole web service
        			androidHttpTransport.call(SOAP_ACTION, envelope);
        			//Get the response
        			//SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
        			//VOUser response = (VOUser)envelope.getResponse();
        			SoapObject response = (SoapObject)envelope.getResponse();
        			        			
        			if(response != null && (Integer.parseInt(response.getProperty("userType").toString()) == 2 || Integer.parseInt(response.getProperty("userType").toString()) == 3)){
        				//if(Boolean.parseBoolean(response.toString()))
        				//Starting a new Intent
        				Intent partnerMenu = new Intent(getApplicationContext(), MainMenu.class);
                        //Intent searchUser = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(partnerMenu);
        			}else{
        				txtUsername.setText("");
        				txtPassword.setText("");
        				txtUsername.requestFocus();        				
        			}

        		} catch (Exception e) {
        			e.printStackTrace();
        			new AlertDialog.Builder(Login.this)
        				.setTitle("Error")
        				.setMessage("¡Usuario y/o contraseña incorrecta!")        				
        			   .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
        			        public void onClick(DialogInterface dialog, int which) { 
        			            // continue with delete
        			        }
        			     })        			    
        			    .setIcon(android.R.drawable.ic_dialog_alert)
        			     .show();
        		}
                
            }
        });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_user, menu);
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
