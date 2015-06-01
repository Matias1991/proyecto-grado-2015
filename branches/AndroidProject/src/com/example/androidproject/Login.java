package com.example.androidproject;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
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
        			SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
        			
        			if(Boolean.parseBoolean(response.toString())){
        				//Starting a new Intent
                        Intent searchUser = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(searchUser);
        			}else{
        				txtUsername.setText("");
        				txtPassword.setText("");
        				txtUsername.requestFocus();
        			}

        		} catch (Exception e) {
        			e.printStackTrace();
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
