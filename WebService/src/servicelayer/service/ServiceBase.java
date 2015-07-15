package servicelayer.service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import servicelayer.utilities.Constants;
import shared.LoggerMSMP;

public class ServiceBase {

	public final Lock transactionLock = new ReentrantLock();
	
	void ThrowGenericExceptionAndLogError(Exception ex) throws RuntimeException
	{
		String errorNumber = LoggerMSMP.setLog(ex.getMessage());
		throw new RuntimeException(String.format(Constants.GENERIC_ERROR, errorNumber));
	}
}
