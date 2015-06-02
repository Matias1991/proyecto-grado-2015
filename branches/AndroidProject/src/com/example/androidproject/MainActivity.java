package com.example.androidproject;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class MainActivity extends Activity {
	private final String NAMESPACE = "http://service.servicelayer";
	private final String URL = "http://192.168.1.98:8080/WebService/services/ServiceMobile?wsdl";
	private final String SOAP_ACTION = "urn:getUser";
	private final String METHOD_NAME = "getUser";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		 StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	     StrictMode.setThreadPolicy(policy);
		
		
		setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	EditText textUserId = (EditText) findViewById(R.id.txtUserId);
                TextView textName = (TextView) findViewById(R.id.txtName);
                TextView txtUserName = (TextView) findViewById(R.id.txtUserName);
                TextView txtLastName = (TextView) findViewById(R.id.txtLastName);
                TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
                
                textName.setText("");
                txtUserName.setText("");
                txtLastName.setText("");
                txtEmail.setText("");
                
                //text.setText("ALOHA:  " + EditText.getText());
        		String valueUserId = textUserId.getText().toString();
                int userId = Integer.parseInt(valueUserId);
        		
        		//Create request
        		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        		//Property which holds input parameters
        		PropertyInfo getUserPI = new PropertyInfo();
        		//Set Name
        		getUserPI.setName("id");
        		//Set Value
        		getUserPI.setValue(userId);
        		//Set dataType
        		getUserPI.setType(Integer.class);
        		//Add the property to request object
        		request.addProperty(getUserPI);
        		//Create envelope
        		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
        				SoapEnvelope.VER11);
        		envelope.dotNet = true;
        		//Set output SOAP object
        		envelope.setOutputSoapObject(request);
        		//Create HTTP call object
        		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

        		try {
        			androidHttpTransport.call(SOAP_ACTION, envelope);
        			VOUser voUser = new VOUser((SoapObject)envelope.getResponse());
        
        			textName.setText(voUser.name);
        			txtUserName.setText(voUser.userName);
        			txtLastName.setText(voUser.lastName);
        			txtEmail.setText(voUser.email);

        		} catch (Exception e) {
        			e.printStackTrace();
        		}
        		
            }
        });
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
