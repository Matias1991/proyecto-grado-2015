package servicelayer.service;

import java.util.concurrent.TimeUnit;

import servicelayer.core.CoreUser;
import servicelayer.entity.businessEntity.ChanelType;
import servicelayer.entity.valueObject.VOUser;
import servicelayer.service.builder.UserBuilder;
import servicelayer.utilities.Constants;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreUser;

public class ServiceMobile extends ServiceBase{

	private ICoreUser iCoreUser = null;
	private static UserBuilder userBuilser = new UserBuilder();
	
	public ServiceMobile()
	{
		try {
			iCoreUser = CoreUser.GetInstance();
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		}
	}
	
	public VOUser login(String userName,String password)
	{
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);
			
			return userBuilser.BuildVOObject(iCoreUser.login(userName, password, ChanelType.MOBILE));
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "realizar el ingreso del usuario en el sistema");
		} catch (ClientException e) {
			throw new RuntimeException(e.getMessage());
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);	
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		}
		finally
		{
			transactionLock.unlock();
		}
		return null;
	}	
}
