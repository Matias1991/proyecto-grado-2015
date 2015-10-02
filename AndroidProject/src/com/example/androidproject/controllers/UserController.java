package com.example.androidproject.controllers;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.androidproject.HandleError;
import com.example.androidproject.VOUser;

public class UserController {

	private final String NAMESPACE = "http://service.servicelayer";
	private final String URL;
	private final String SOAP_ACTION = "urn:login";
	private final String METHOD_NAME = "login";

	public UserController(String server) {
		this.URL = String
				.format("http://%s:8080/WebService/services/ServiceMobile?wsdl",
						server);
	}

	public VOUser login(String username, String password) throws Exception {
		VOUser voUser = null;

		// Create request
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		// Property which holds input parameters
		PropertyInfo loginUsernamePI = new PropertyInfo();
		PropertyInfo loginPasswordPI = new PropertyInfo();
		// Set Name
		loginUsernamePI.setName("userName");
		loginPasswordPI.setName("password");
		// Set Value
		loginUsernamePI.setValue(username);
		loginPasswordPI.setValue(password);
		// Set dataType
		loginUsernamePI.setType(String.class);
		loginPasswordPI.setType(String.class);
		// Add the property to request object
		request.addProperty(loginUsernamePI);
		request.addProperty(loginPasswordPI);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);
		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapObject response = (SoapObject) envelope.getResponse();

			if (response != null)
				voUser = new VOUser(response);

		} catch (Exception e) {
			throw new Exception(HandleError.getMessageError(envelope));
		}
		return voUser;
	}
}
