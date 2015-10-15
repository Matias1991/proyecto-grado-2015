package com.example.androidproject.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import com.example.androidproject.HandleError;

import entities.MarshalDate;
import entities.VOProject;
import entities.VOProjectLiquidation;

public class ReportsController {

	private final String NAMESPACE = "http://service.servicelayer";
	private final String URL;
	private final String MORE_EARNINGS_SOAP_ACTION = "urn:getProjectsLiquidationsWithMoreEarnings";
	private final String MORE_EARNINGS_METHOD_NAME = "getProjectsLiquidationsWithMoreEarnings";
	private final String INFO_PROJECT_SOAP_ACTION = "urn:getProjectLiquidation";
	private final String INFO_PROJECT__METHOD_NAME = "getProjectLiquidation";
	

	public ReportsController(String server) {
		this.URL = String
				.format("http://%s:8080/WebService/services/ServiceMobile?wsdl",
						server);
	}

	public ArrayList<VOProjectLiquidation> getProjectsLiquidationsWithMoreEarnings(Date from, Date to)
			throws Exception {
		ArrayList<VOProjectLiquidation> result = new ArrayList<VOProjectLiquidation>();

		// Create request
		SoapObject request = new SoapObject(NAMESPACE, MORE_EARNINGS_METHOD_NAME);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(2015, 05, 01, 0, 0, 0);
		
		PropertyInfo dateFrom = new PropertyInfo();
		dateFrom.setName("dateFrom");
		dateFrom.setValue(cal.getTime());
		dateFrom.setType(Date.class);
		request.addProperty(dateFrom);
		
		cal.set(2015, 10, 9, 0, 0, 0);
		
		PropertyInfo dateTo = new PropertyInfo();
		dateTo.setName("dateTo");
		dateTo.setValue(cal.getTime());
		dateTo.setType(Date.class);
		request.addProperty(dateTo);

		PropertyInfo isCurrencyDollar = new PropertyInfo();
		isCurrencyDollar.setName("isCurrencyDollar");
		isCurrencyDollar.setValue(true);
		isCurrencyDollar.setType(Boolean.class);
		request.addProperty(isCurrencyDollar);		

		PropertyInfo count = new PropertyInfo();
		count.setName("count");
		count.setValue(5);
		count.setType(Integer.class);
		request.addProperty(count);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		MarshalDate mDate = new MarshalDate();
		mDate.register(envelope);
		
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(MORE_EARNINGS_SOAP_ACTION, envelope);

			SoapObject response = (SoapObject) envelope.bodyIn;
			if(response != null){
				for (int i = 0; i < response.getPropertyCount(); i++) {
					SoapObject object = (SoapObject) response.getProperty(i);
					if(object != null){
						result.add(new VOProjectLiquidation(object));						
					}
				}				
			}

		} catch (Exception e) {
			throw new Exception(HandleError.getMessageError(envelope));
		}
		return result;
	}

	
	public ArrayList<VOProjectLiquidation> getProjectsInfo(Date month, int id)
			throws Exception {
		ArrayList<VOProjectLiquidation> result = new ArrayList<VOProjectLiquidation>();

		// Create request
		SoapObject request = new SoapObject(NAMESPACE, INFO_PROJECT__METHOD_NAME);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(2015, 10, 01, 0, 0, 0);
		
		PropertyInfo liquidationDate = new PropertyInfo();
		liquidationDate.setName("month");
		liquidationDate.setValue(cal.getTime());
		liquidationDate.setType(Date.class);
		request.addProperty(liquidationDate);

		PropertyInfo projectId = new PropertyInfo();
		projectId.setName("projectId");
		projectId.setValue(id);
		projectId.setType(Integer.class);
		request.addProperty(projectId);		

		PropertyInfo userContextId = new PropertyInfo();
		userContextId.setName("userContextId");
		/* Harcode tipo de usuario en 2 = SOCIO */
		userContextId.setValue(2);
		userContextId.setType(Integer.class);
		request.addProperty(userContextId);

		// Create envelope
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		MarshalDate mDate = new MarshalDate();
		mDate.register(envelope);
		
		HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

		try {
			androidHttpTransport.call(INFO_PROJECT_SOAP_ACTION, envelope);

			SoapObject response = (SoapObject) envelope.bodyIn;
			if(response != null){
				for (int i = 0; i < response.getPropertyCount(); i++) {
					SoapObject object = (SoapObject) response.getProperty(i);
					if(object != null){
						result.add(new VOProjectLiquidation(object));						
					}
				}				
			}

		} catch (Exception e) {
			throw new Exception(HandleError.getMessageError(envelope));
		}
		return result;
	}
}
