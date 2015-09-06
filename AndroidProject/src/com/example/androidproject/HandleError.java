package com.example.androidproject;

import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapSerializationEnvelope;

public class HandleError {
	
	public static String getMessageError(SoapSerializationEnvelope envelope){
		String result = "No se pudo capturar el error";
		 if (envelope.bodyIn instanceof SoapFault) {	            
	       result =((SoapFault) envelope.bodyIn).faultstring;
	     }
		 return result;
	}

}
