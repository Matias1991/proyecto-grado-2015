package com.example.androidproject;

import android.app.Activity;
import android.os.Bundle;
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
	private final String NAMESPACE = "http://localhost:8080/WebService/services/";
	private final String URL = "http://localhost:8080/WebService/services/ServiceMobile?wsdl";
	private final String SOAP_ACTION = "http://localhost:8080/WebService/services/getUser";
	private final String METHOD_NAME = "getUser";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

        final Button button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
            	EditText EditText = (EditText) findViewById(R.id.editText2);
                TextView text = (TextView) findViewById(R.id.TextView02);
        		//text.setText("Hello " + EditText.getText());
        		
        		
        		//Create request
        		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        		//Property which holds input parameters
        		PropertyInfo getUserPI = new PropertyInfo();
        		//Set Name
        		getUserPI.setName("Id");
        		//Set Value
        		getUserPI.setValue(text);
        		//Set dataType
        		getUserPI.setType(String.class);
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
        			//Invole web service
        			androidHttpTransport.call(SOAP_ACTION, envelope);
        			//Get the response
        			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
        			//Assign it to fahren static variable        		
        			text.setText("Id: " + EditText.getText() + "==> " + response.toString());

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
