package servicelayer.service;

import java.util.concurrent.TimeUnit;

import servicelayer.core.CoreBill;
import servicelayer.core.CoreProject;
import servicelayer.core.CoreUser;
import servicelayer.entity.businessEntity.ChanelType;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.valueObject.VOBill;
import servicelayer.entity.valueObject.VOProject;
import servicelayer.entity.valueObject.VOUser;
import servicelayer.service.builder.BillBuilder;
import servicelayer.service.builder.ProjectBuilder;
import servicelayer.service.builder.UserBuilder;
import servicelayer.utilities.Constants;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreBill;
import shared.interfaces.core.ICoreProject;
import shared.interfaces.core.ICoreUser;

public class ServiceMobile extends ServiceBase{

	private ICoreUser iCoreUser = null;
	private ICoreProject iCoreProject = null;
	private ICoreBill iCoreBill = null;
	
	private static UserBuilder userBuilser = new UserBuilder();
	private static ProjectBuilder projectBuilder = new ProjectBuilder();
	private static BillBuilder billBuilder = new BillBuilder();
	
	public ServiceMobile()
	{
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);
			
			iCoreUser = CoreUser.GetInstance();
			iCoreProject = CoreProject.GetInstance();
			iCoreBill = CoreBill.GetInstance();
			
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
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
	
	public VOProject[] getProjectsByUserContext(int userContextId) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			User userContext = iCoreUser.getUser(userContextId);
			
			return projectBuilder.BuildArrayVOObject(VOProject.class,
					iCoreProject.getProjects(userContext));

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener todos los proyectos");
		} catch (ClientException e) {
			throw new RuntimeException(e.getMessage());
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}
	
	public boolean insertBill(VOBill voBill) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			iCoreBill.insertBill(billBuilder.BuildBusinessObject(voBill));

			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "insertar factura");
		} catch (ClientException e) {
			throw new RuntimeException(e.getMessage());
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return false;
	}
	
	public String example(VOBill voBill)
	{
		return voBill.getId() + " - " + voBill.getCode() + " - " + voBill.getDescription();
	}
}
