package servicelayer.service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import servicelayer.core.CoreUser;
import servicelayer.entity.valueObject.VOUser;
import servicelayer.utilities.Constants;
import shared.exceptions.ServiceLayerException;
import shared.interfaces.core.ICoreUser;

////IMPORTANTE:
////transactionLock.tryLock -> utilizar esto para brindar un buen manejo sincronizado, con esta sentencia se abre el bloque de exlusicion con un limite de 10 segundos de espera
////definir para cada operation del servico try{}catch{}finally{}
////finally{} siempre liberar el transactionLock
public class ServiceWeb extends ServiceBase{

	private ICoreUser iCoreUser = null;
	
	public ServiceWeb()
	{
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME , TimeUnit.SECONDS);
			
			iCoreUser = CoreUser.GetInstance();
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (Exception e) {
			throw new RuntimeException(Constants.GENERIC_ERROR);
		}
		finally
		{
			transactionLock.unlock();
		}
	}
	
	public boolean insertUser(VOUser voUser)
	{
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);
			
			iCoreUser.insertUser(voUser);
			
			return true;
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			throw new RuntimeException(Constants.GENERIC_ERROR);
		}
		finally
		{
			transactionLock.unlock();
		}
	}
	
	public VOUser getUser(int id)
	{
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);
			
			return iCoreUser.getUser(id);
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		}catch (InterruptedException e) {
				throw new RuntimeException(Constants.TRANSACTION_ERROR);	
		} catch (Exception e) {
			throw new RuntimeException(Constants.GENERIC_ERROR);
		}
		finally
		{
			transactionLock.unlock();
		}
	}
	
	public boolean deleteUser(int id)
	{
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);
			
			iCoreUser.deleteUser(id);
			return true;
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (InterruptedException e) {
				throw new RuntimeException(Constants.TRANSACTION_ERROR);	
		} catch (Exception e) {
			throw new RuntimeException(Constants.GENERIC_ERROR);
		}
		finally
		{
			transactionLock.unlock();
		}
	}
	
	public VOUser updateUser(int id, VOUser voUser)
	{
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);
			
			return iCoreUser.update(id, voUser);
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (InterruptedException e) {
				throw new RuntimeException(Constants.TRANSACTION_ERROR);	
		} catch (Exception e) {
			throw new RuntimeException(Constants.GENERIC_ERROR);
		}
		finally
		{
			transactionLock.unlock();
		}
	}
	
	public boolean existUser(int id)
	{
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);
			
			return iCoreUser.existUser(id);
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);	
		} catch (Exception e) {
			throw new RuntimeException(Constants.GENERIC_ERROR);
		}
		finally
		{
			transactionLock.unlock();
		}
	}
	
	public VOUser login(String userName,String password)
	{
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);
			
			return iCoreUser.login(userName, password);
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			throw new RuntimeException(Constants.GENERIC_ERROR);
		}
		finally
		{
			transactionLock.unlock();
		}
	}
	
	public VOUser[] getUsers()
	{
		ArrayList<VOUser> voUsers;
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);
			
			voUsers = iCoreUser.getUsers();
			VOUser [] arrayVoUser = new VOUser[voUsers.size()];
		    voUsers.toArray(arrayVoUser);
		    return arrayVoUser;
		    
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			throw new RuntimeException(Constants.GENERIC_ERROR);
		}
		finally
		{
			transactionLock.unlock();
		}
	}
	
	public boolean forgotPassword(String userEmail)
	{
		
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);
			
			iCoreUser.forgotPassord(userEmail);
			
			return true;
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			throw new RuntimeException(Constants.GENERIC_ERROR);
		}
		finally
		{
			transactionLock.unlock();
		}
	}
	
	public boolean resetPassword(int id)
	{
		
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);
			
			iCoreUser.resetPassword(id);
		    
			return true;
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			throw new RuntimeException(Constants.GENERIC_ERROR);
		}
		finally
		{
			transactionLock.unlock();
		}
	}
	
	public boolean changePassword(int id, String oldPassword, String newPassword)
	{
		
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);
			
			iCoreUser.changePassword(id, oldPassword, newPassword);
		    
			return true;
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			throw new RuntimeException(Constants.GENERIC_ERROR);
		}
		finally
		{
			transactionLock.unlock();
		}
	}
	
	public boolean unlockUser(int id)
	{
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);
			
			iCoreUser.unlockUser(id);
			
			return true;
		} catch (ServiceLayerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			throw new RuntimeException(Constants.GENERIC_ERROR);
		}
		finally
		{
			transactionLock.unlock();
		}
	}
}
