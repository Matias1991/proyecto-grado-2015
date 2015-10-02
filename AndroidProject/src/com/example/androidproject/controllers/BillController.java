package com.example.androidproject.controllers;

import java.util.ArrayList;
import java.util.Date;

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
import entities.VOProject;

public class BillController {

	private final String NAMESPACE = "http://service.servicelayer";
	private  String URL;
	private final String SOAP_ACTION = "urn:insertBill";
	private final String METHOD_NAME = "insertBill";
	private final String GET_SOAP_ACTION = "urn:getNotLiquidatedBills";
	private final String GET_METHOD_NAME = "getNotLiquidatedBills";
	
	public BillController(String server)
	{
		this.URL = String.format("http://%s:8080/WebService/services/ServiceMobile?wsdl", server);
	}
	
	public boolean createBill(VOBill voBill) throws Exception {
		//Create request
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		
        PropertyInfo object = new PropertyInfo();
        object.setName("voBill");
        object.setValue(voBill);
        object.setType(voBill.getClass());

        request.addProperty(object);
    	
		//Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.implicitTypes = true;
		//Set output SOAP object
		envelope.setOutputSoapObject(request);
		
		envelope.addMapping(NAMESPACE, "VOBill", new VOBill().getClass());
		MarshalDouble mDouble = new MarshalDouble();
		MarshalDate mDate = new MarshalDate();
		mDouble.register(envelope);
		mDate.register(envelope);

		//Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		
		try {
			androidHttpTransport.call(SOAP_ACTION, envelope);
			SoapPrimitive response = (SoapPrimitive)envelope.getResponse();
			
			if(Boolean.parseBoolean(response.toString()))
				return true;
			else
				return false;
			
		} catch (Exception e) {
			throw new Exception(HandleError.getMessageError(envelope));
		}        
	}
	
	public ArrayList<VOBill> getBills(int userContextId) throws Exception
	{
		ArrayList<VOBill> result = new ArrayList<VOBill>();
		
		//Create request
		SoapObject request = new SoapObject(NAMESPACE, GET_METHOD_NAME);
		
        PropertyInfo userContext = new PropertyInfo();
        userContext.setName("userContextId");
        userContext.setValue(userContextId);
        userContext.setType(Integer.class);
        
        request.addProperty(userContext);
		
		//Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.dotNet = true;
		//Set output SOAP object
		envelope.setOutputSoapObject(request);
		//Create HTTP call object
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
		
		try {
			androidHttpTransport.call(GET_SOAP_ACTION, envelope);

			 SoapObject response = (SoapObject) envelope.bodyIn;
			 for (int i = 0; i< response.getPropertyCount(); i++) 
			 {
				 SoapObject object = (SoapObject) response.getProperty(i);
				 result.add(new VOBill(object));
			 }

		} catch (Exception e) {
			throw new Exception(HandleError.getMessageError(envelope));
		}        
		return result;
	}
}
