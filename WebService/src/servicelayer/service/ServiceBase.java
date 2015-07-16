package servicelayer.service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import servicelayer.utilities.Constants;
import shared.LoggerMSMP;

public class ServiceBase {

	public final Lock transactionLock = new ReentrantLock();
	
	void ThrowServerExceptionAndLogError(Exception ex, String operation) throws RuntimeException
	{
		String errorNumber = LoggerMSMP.setLog(ex.getMessage());
		throw new RuntimeException(String.format("Ocurrio un error al %s, consulte con el Administrador con el codigo de error: %s", operation, errorNumber));
	}
	
	void ThrowGenericExceptionAndLogError(Exception ex) throws RuntimeException
	{
		String errorNumber = LoggerMSMP.setLog(ex.getMessage());
		throw new RuntimeException(String.format(Constants.GENERIC_ERROR, errorNumber));
	}
}
