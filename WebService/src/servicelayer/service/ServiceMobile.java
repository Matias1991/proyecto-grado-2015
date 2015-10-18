package servicelayer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import servicelayer.core.CoreBill;
import servicelayer.core.CoreCharge;
import servicelayer.core.CoreCompanyLiquidation;
import servicelayer.core.CoreProject;
import servicelayer.core.CoreProjectLiquidation;
import servicelayer.core.CoreUser;
import servicelayer.entity.businessEntity.ChanelType;
import servicelayer.entity.businessEntity.Charge;
import servicelayer.entity.businessEntity.CompanyLiquidation;
import servicelayer.entity.businessEntity.ProjectLiquidation;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.valueObject.VOBill;
import servicelayer.entity.valueObject.VOCategory;
import servicelayer.entity.valueObject.VOCharge;
import servicelayer.entity.valueObject.VOCompanyLiquidation;
import servicelayer.entity.valueObject.VOProject;
import servicelayer.entity.valueObject.VOProjectLiquidation;
import servicelayer.entity.valueObject.VOUser;
import servicelayer.service.builder.BillBuilder;
import servicelayer.service.builder.CategoryBuilder;
import servicelayer.service.builder.ChargeBuilder;
import servicelayer.service.builder.CompanyLiquidationBuilder;
import servicelayer.service.builder.EmployedBuilder;
import servicelayer.service.builder.ProjectBuilder;
import servicelayer.service.builder.ProjectLiquidationBuilder;
import servicelayer.service.builder.UserBuilder;
import servicelayer.utilities.Constants;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreBill;
import shared.interfaces.core.ICoreCharge;
import shared.interfaces.core.ICoreCompanyLiquidation;
import shared.interfaces.core.ICoreProject;
import shared.interfaces.core.ICoreProjectLiquidation;
import shared.interfaces.core.ICoreUser;

public class ServiceMobile extends ServiceBase{

	private ICoreUser iCoreUser = null;
	private ICoreProject iCoreProject = null;
	private ICoreBill iCoreBill = null;
	private ICoreCharge iCoreCharge = null;
	private ICoreProjectLiquidation iCoreProjectLiquidation = null;
	private ICoreCompanyLiquidation iCoreCompanyLiquidation = null;
	
	private static UserBuilder userBuilser = new UserBuilder();
	private static ProjectBuilder projectBuilder = new ProjectBuilder();
	private static BillBuilder billBuilder = new BillBuilder();
	private static ChargeBuilder chargeBuilder = new ChargeBuilder();
	private static ProjectLiquidationBuilder projectLiquidationBuilder = new ProjectLiquidationBuilder();
	private static CompanyLiquidationBuilder companyLiquidationBuilder = new CompanyLiquidationBuilder();
	private static EmployedBuilder employedBuilder = new EmployedBuilder();
	private static CategoryBuilder categoryBuilder = new CategoryBuilder();
	
	public ServiceMobile()
	{
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);
			
			iCoreUser = CoreUser.GetInstance();
			iCoreProject = CoreProject.GetInstance();
			iCoreBill = CoreBill.GetInstance();
			iCoreCharge = CoreCharge.GetInstance();
			iCoreProjectLiquidation = CoreProjectLiquidation.GetInstance();
			iCoreCompanyLiquidation = CoreCompanyLiquidation.GetInstance();
			
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
	
	public VOBill[] getBillsByProject(int projectId) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return billBuilder.BuildArrayVOObject(VOBill.class,
					iCoreBill.getBills(projectId));

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener todos las facturas");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}

	public boolean insertCharge(VOCharge voCharge) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);
			Charge charge = chargeBuilder.BuildBusinessObject(voCharge);
			iCoreCharge.insertCharge(charge);

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
	
	public VOProjectLiquidation[] getProjectsLiquidationsWithMoreEarnings(Date dateFrom, Date dateTo, boolean isCurrencyDollar, int count){

		VOProjectLiquidation[] voProjectsLiquidations= null;
		try{
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);			
			
			ArrayList<ProjectLiquidation> projectsLiquidations = iCoreProjectLiquidation.getProjectsWithMoreEarnings(dateFrom, dateTo, isCurrencyDollar, count);
			voProjectsLiquidations = new VOProjectLiquidation[projectsLiquidations.size()];
			
			int i = 0;
			for(ProjectLiquidation projectLiquidation : projectsLiquidations){
				VOProjectLiquidation voProjectLiquidation = projectLiquidationBuilder.BuildVOObject(projectLiquidation);
				voProjectLiquidation.setProject(projectBuilder.BuildVOObject(projectLiquidation.getProject()));
				voProjectsLiquidations[i] = voProjectLiquidation;
				i++;
			}
			return voProjectsLiquidations;
			
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener liquidaciones de proyectos para mostrar");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}
	
	public VOProjectLiquidation getProjectLiquidation(Date month, int projectId, int userContextId){
		try{
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);	
			
			User userContext = iCoreUser.getUser(userContextId);			
			ProjectLiquidation liquidation =  iCoreProjectLiquidation.getProjectLiquidationByDate(month, projectId, userContext);
			VOProjectLiquidation voLiquidation = null;
			if(liquidation != null){
				voLiquidation = projectLiquidationBuilder.BuildVOObject(liquidation);
				voLiquidation.setEmployees(projectBuilder.BuildVOEmployedProjects(liquidation.getEmployees()));
				voLiquidation.setProject(projectBuilder.BuildVOObject(liquidation.getProject()));
			}
						
			return voLiquidation;
			
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener liquidaciones de proyectos para mostrar");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}
	

	public VOCompanyLiquidation getCompanyLiquidation(Date month, int userContextId){
		try{
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);			
			
			User userContext = iCoreUser.getUser(userContextId);
			CompanyLiquidation companyLiquidation = iCoreCompanyLiquidation.getCompanyLiqudationsByDate(month, userContext);
			
			VOCompanyLiquidation voCompanyLiquidation = companyLiquidationBuilder.BuildVOObject(companyLiquidation);
			
			voCompanyLiquidation.setPartner1(employedBuilder.BuildVOObject(companyLiquidation.getPartner1()));
			voCompanyLiquidation.setPartner2(employedBuilder.BuildVOObject(companyLiquidation.getPartner2()));
			voCompanyLiquidation.setEmployees(projectBuilder.BuildVOEmployedProjects(companyLiquidation.getEmployees()));
			voCompanyLiquidation.setCategoriesHuman(categoryBuilder.BuildArrayVOObject(VOCategory.class, companyLiquidation.getCategoriesHuman()));
			voCompanyLiquidation.setCategoriesMaterial(categoryBuilder.BuildArrayVOObject(VOCategory.class, companyLiquidation.getCategoriesMaterial()));
			
			return voCompanyLiquidation;
			
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener liquidaciones de proyectos para mostrar");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}
	
	public String example(VOBill voBill)
	{
		return voBill.getId() + " - " + voBill.getCode() + " - " + voBill.getDescription();
	}
}
