package com.example.androidproject.controllers;

import java.util.ArrayList;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.androidproject.HandleError;

import entities.VOProject;

public class ProjectController {

	private final String NAMESPACE = "http://service.servicelayer";
	private final String URL;
	private final String SOAP_ACTION = "urn:getProjectsByUserContext";
	private final String METHOD_NAME = "getProjectsByUserContext";
	
	public ProjectController(String server)
	{
		this.URL = String.format("http://%s:8080/WebService/services/ServiceMobile?wsdl", server);
	}
	
	public ArrayList<VOProject> getActiveProjects(int userContextId) throws Exception
	{
		ArrayList<VOProject> result = new ArrayList<VOProject>();
		
		//Create request
		SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
		//Property which holds input parameters
		PropertyInfo propUserContextId = new PropertyInfo();
		//Set Name
		propUserContextId.setName("userContextId");
		//Set Value
		propUserContextId.setValue(userContextId);
		//Set dataType
		propUserContextId.setType(Integer.class);
		//Add the property to request object
		request.addProperty(propUserContextId);
		
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

			 SoapObject response = (SoapObject) envelope.bodyIn;
			 for (int i = 0; i< response.getPropertyCount(); i++) 
			 {
				 SoapObject object = (SoapObject) response.getProperty(i);
				 result.add(new VOProject(object));
			 }

		} catch (Exception e) {
			throw new Exception(HandleError.getMessageError(envelope));
		}        
		return result;
	}
}
