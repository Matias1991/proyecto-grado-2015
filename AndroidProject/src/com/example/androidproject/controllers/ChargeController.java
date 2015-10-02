package com.example.androidproject.controllers;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.androidproject.HandleError;

import entities.MarshalDate;
import entities.MarshalDouble;
import entities.VOBill;
import entities.VOCharge;

public class ChargeController {

	private final String NAMESPACE = "http://service.servicelayer";
	private final String URL;
	private final String SOAP_ACTION = "urn:insertCharge";
	private final String INSERT_METHOD_NAME = "insertCharge";

	public ChargeController(String server) {
		this.URL = String
				.format("http://%s:8080/WebService/services/ServiceMobile?wsdl",
						server);
	}

	public boolean createCharge(VOCharge voCharge) throws Exception {
		// Create request
		SoapObject request = new SoapObject(NAMESPACE, INSERT_METHOD_NAME);

		PropertyInfo object = new PropertyInfo();
		object.setName("voCharge");
		object.setValue(voCharge);
		object.setType(voCharge.getClass());

		request.addProperty(object);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.implicitTypes = true;
		// Set output SOAP object
		envelope.setOutputSoapObject(request);

		envelope.addMapping(NAMESPACE, "voCharge", new VOCharge().getClass());
		MarshalDouble mDouble = new MarshalDouble();
		mDouble.register(envelope);

		// Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

			if (Boolean.parseBoolean(response.toString()))
				return true;
			else
				return false;

		} catch (Exception e) {
			throw new Exception(HandleError.getMessageError(envelope));
		}
	}
}
