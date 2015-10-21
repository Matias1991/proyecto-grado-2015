package servicelayer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import servicelayer.core.CoreBill;
import servicelayer.core.CoreCategory;
import servicelayer.core.CoreCharge;
import servicelayer.core.CoreCompanyLiquidation;
import servicelayer.core.CoreEmployed;
import servicelayer.core.CoreGlobalConfiguration;
import servicelayer.core.CoreProject;
import servicelayer.core.CoreProjectLiquidation;
import servicelayer.core.CoreUser;
import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.ChanelType;
import servicelayer.entity.businessEntity.CompanyLiquidation;
import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.ProjectEmployed;
import servicelayer.entity.businessEntity.ProjectLiquidation;
import servicelayer.entity.businessEntity.ProjectPartner;
import servicelayer.entity.businessEntity.SalarySummary;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.valueObject.VOBill;
import servicelayer.entity.valueObject.VOCategory;
import servicelayer.entity.valueObject.VOCharge;
import servicelayer.entity.valueObject.VOCompanyLiquidation;
import servicelayer.entity.valueObject.VODistributionType;
import servicelayer.entity.valueObject.VOEmployed;
import servicelayer.entity.valueObject.VOGlobalConfiguration;
import servicelayer.entity.valueObject.VOProject;
import servicelayer.entity.valueObject.VOProjectLiquidation;
import servicelayer.entity.valueObject.VOSalarySummary;
import servicelayer.entity.valueObject.VOUser;
import servicelayer.service.builder.BillBuilder;
import servicelayer.service.builder.CategoryBuilder;
import servicelayer.service.builder.ChargeBuilder;
import servicelayer.service.builder.CompanyLiquidationBuilder;
import servicelayer.service.builder.EmployedBuilder;
import servicelayer.service.builder.GlobalConfigurationBuilder;
import servicelayer.service.builder.ProjectBuilder;
import servicelayer.service.builder.ProjectLiquidationBuilder;
import servicelayer.service.builder.SalarySummaryBuilder;
import servicelayer.service.builder.UserBuilder;
import servicelayer.utilities.Constants;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreBill;
import shared.interfaces.core.ICoreCategory;
import shared.interfaces.core.ICoreCharge;
import shared.interfaces.core.ICoreCompanyLiquidation;
import shared.interfaces.core.ICoreEmployed;
import shared.interfaces.core.ICoreGlobalConfiguration;
import shared.interfaces.core.ICoreProject;
import shared.interfaces.core.ICoreProjectLiquidation;
import shared.interfaces.core.ICoreUser;

////IMPORTANTE:
////transactionLock.tryLock -> utilizar esto para brindar un buen manejo sincronizado, con esta sentencia se abre el bloque de exlusicion con un limite de 10 segundos de espera
////definir para cada operation del servico try{}catch{}finally{}
////finally{} siempre liberar el transactionLock
public class ServiceWeb extends ServiceBase {

	private ICoreUser iCoreUser = null;
	private ICoreEmployed iCoreEmployed = null;
	private ICoreCategory iCoreCategory = null;
	private ICoreProject iCoreProject = null;
	private ICoreBill iCoreBill = null;
	private ICoreCharge iCoreCharge = null;
	private ICoreProjectLiquidation iCoreProjectLiquidation = null;
	private ICoreCompanyLiquidation iCoreCompanyLiquidation = null;
	private ICoreGlobalConfiguration iCoreGlobalConfiguration = null;
	
	// Builder's para mapear
	// value objects a entidades de negocio
	// value objects a entidades de negocio
	private static BillBuilder billBuilder = new BillBuilder();
	private static CategoryBuilder categoryBuilder = new CategoryBuilder();
	private static ChargeBuilder chargeBuilder = new ChargeBuilder();
	private static EmployedBuilder employedBuilder = new EmployedBuilder();
	private static UserBuilder userBuilser = new UserBuilder();
	private static ProjectBuilder projectBuilder = new ProjectBuilder();
	private static SalarySummaryBuilder salarySummaryBuilder = new SalarySummaryBuilder();
	private static ProjectLiquidationBuilder projectLiquidationBuilder = new ProjectLiquidationBuilder();
	private static CompanyLiquidationBuilder companyLiquidationBuilder = new CompanyLiquidationBuilder();
	private static GlobalConfigurationBuilder globalConfigurationBuilder = new GlobalConfigurationBuilder();
	
	public ServiceWeb() {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			iCoreUser = CoreUser.GetInstance();
			iCoreEmployed = CoreEmployed.GetInstance();
			iCoreCategory = CoreCategory.GetInstance();
			iCoreProject = CoreProject.GetInstance();
			iCoreBill = CoreBill.GetInstance();
			iCoreCharge = CoreCharge.GetInstance();
			iCoreProjectLiquidation = CoreProjectLiquidation.GetInstance();
			iCoreCompanyLiquidation = CoreCompanyLiquidation.GetInstance();
			iCoreGlobalConfiguration = CoreGlobalConfiguration.GetInstance();

		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
	}

	public boolean insertUser(VOUser voUser) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			iCoreUser.insertUser(userBuilser.BuildBusinessObject(voUser));

			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "crear el usuario");
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

	public VOUser getUser(int id) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return userBuilser.BuildVOObject(iCoreUser.getUser(id));
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener los datos del usuario");
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

	public boolean deleteUser(int id) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			iCoreUser.deleteUser(id);
			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "eliminar el usuario");
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

	public VOUser updateUser(int id, VOUser voUser) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return userBuilser.BuildVOObject(iCoreUser.update(id,
					userBuilser.BuildBusinessObject(voUser)));
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e,
					"modificar los datos del usuario");
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

	public boolean existUser(int id) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return iCoreUser.existUser(id);
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "validar si existe el usuario");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return false;
	}

	public VOUser login(String userName, String password) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return userBuilser.BuildVOObject(iCoreUser.login(userName,
					password, ChanelType.WEB));
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e,
					"realizar el ingreso del usuario en el sistema");
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

	public VOUser[] getUsers() {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return userBuilser.BuildArrayVOObject(VOUser.class,
					iCoreUser.getUsers());

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener todos los usuarios");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}

	public boolean forgotPassword(String userEmail) {

		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			iCoreUser.forgotPassord(userEmail);

			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e,
					"realizar la operacion de olvido de contraseña");
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

	public boolean resetPassword(int id) {

		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			iCoreUser.resetPassword(id);

			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e,
					"realizar la operacion de reseteo de contraseña");
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

	public boolean changePassword(int id, String oldPassword, String newPassword) {

		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			iCoreUser.changePassword(id, oldPassword, newPassword);

			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e,
					"realizar la operacion de cambio de contraseña");
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

	public boolean unlockUser(int id) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			iCoreUser.unlockUser(id);

			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "desbloquear el usuario");
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

	public VOUser[] getUsersByStatus(int userStatusId) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return userBuilser.BuildArrayVOObject(VOUser.class,
					iCoreUser.getUsersByStatus(userStatusId));

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener todos los usuarios");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}

	public boolean insertEmployed(VOEmployed voEmployed) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			Employed employed = employedBuilder.BuildBusinessObject(voEmployed);
			SalarySummary salarySummary = employedBuilder
					.BuildBusinessSalarySummary(voEmployed.getvOSalarySummary());
			iCoreEmployed.insertEmployed(employed, salarySummary);

			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "ingresar el empleado");
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

	public VOEmployed[] getEmployees() {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			VOEmployed[] employees = employedBuilder.BuildArrayVOObject(
					VOEmployed.class, iCoreEmployed.getEmployess());

			for (VOEmployed voEmployed : employees) {
				SalarySummary salarySummary = iCoreEmployed
						.getSalarySummaryByVersion(voEmployed.getId(), -1);
				voEmployed.setvOSalarySummary(employedBuilder
						.BuildVOSalarySummary(salarySummary));
			}

			return employees;

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener todos los empleados");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}

	public VOEmployed getEmployed(int id) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			VOEmployed voEmployed = employedBuilder.BuildVOObject(iCoreEmployed
					.getEmployed(id));
			SalarySummary salarySummary = iCoreEmployed
					.getSalarySummaryByVersion(voEmployed.getId(), -1);
			voEmployed.setvOSalarySummary(employedBuilder
					.BuildVOSalarySummary(salarySummary));

			return voEmployed;

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener los datos del empleado");
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

	public VOSalarySummary estimateSalarySummary(VOSalarySummary voSalarySummary, boolean isPartner) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			SalarySummary salarySummary = iCoreEmployed
					.estimateSalarySummary(employedBuilder
							.BuildBusinessSalarySummary(voSalarySummary), isPartner);
			return employedBuilder.BuildVOSalarySummary(salarySummary);

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e,
					"estimar el resumen de salario del empleado");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}

	public boolean deleteEmployed(int id) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			iCoreEmployed.deleteEmployed(id);
			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e,
					"estimar el resumen de salario del empleado");
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

	public boolean updatedEmployed(int id, VOEmployed voEmployed) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			Employed employed = employedBuilder.BuildBusinessObject(voEmployed);
			SalarySummary salarySummary = employedBuilder
					.BuildBusinessSalarySummary(voEmployed.getvOSalarySummary());
			iCoreEmployed.updateEmployed(id, employed, salarySummary);

			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e,
					"modificar los datos del empleado");
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

	public Integer[] getAllVersionsSalarySummary(int employedId) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			ArrayList<Integer> listAllVersions = iCoreEmployed
					.getAllVersionSalarySummary(employedId);
			Integer[] arrayListVersion = new Integer[listAllVersions.size()];
			listAllVersions.toArray(arrayListVersion);
			return arrayListVersion;

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e,
					"obtener todas las versiones del resumen de salario");
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

	public VOSalarySummary[] getAllSalarySummaryVersion(int employedId) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			VOSalarySummary[] arrayListVersion = salarySummaryBuilder
					.BuildArrayVOObject(VOSalarySummary.class, iCoreEmployed
							.getAllSalarySummaryVersion(employedId));

			return arrayListVersion;

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e,
					"obtener todas las versiones del resumen de salario");
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

	public VOSalarySummary getSalarySummaryByVersion(int employedId, int version) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return employedBuilder.BuildVOSalarySummary(iCoreEmployed
					.getSalarySummaryByVersion(employedId, version));

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e,
					"obtener todas las versiones del resumen de salario");
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

	public boolean insertCategory(VOCategory voCategory) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			iCoreCategory.insertCategory(categoryBuilder
					.BuildBusinessObject(voCategory));

			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "Insertar rubro");
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

	public VOCategory getCategory(int id) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return categoryBuilder.BuildVOObject(iCoreCategory.getCategory(id));

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener los datos del rubro");
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

	public boolean deleteCategory(int id) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			iCoreCategory.deleteCateory(id);
			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "eliminar el rubro");
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

	public VOCategory[] getCategories(int userContextId) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);
			User userContext = iCoreUser.getUser(userContextId);
			return categoryBuilder.BuildArrayVOObject(VOCategory.class,
					iCoreCategory.getCategories(userContext));
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener todos los rubros");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}
	
	public VOCategory[] getCategoriesByNameAndCurrency(String name, boolean isCurrencyDollar, Date date) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);
			
			return categoryBuilder.BuildArrayVOObject(VOCategory.class,
					iCoreCategory.getCategories(name, isCurrencyDollar, date));
			
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener todos los rubros");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}

	public VOCategory updateCategory(int id, VOCategory voCategory) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return categoryBuilder.BuildVOObject(iCoreCategory.updateCategory(
					id, categoryBuilder.BuildBusinessObject(voCategory)));

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener todos los rubros");
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

	public VOCategory[] getCategoriesByProject(int id) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return categoryBuilder.BuildArrayVOObject(VOCategory.class,
					iCoreCategory.getCategoriesByProject(id));

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e,
					"obtener los rubros del proyecto");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}

	public VOCategory[] getCategoriesByDate(Date from, Date to,
			int userContextId) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);
			User userContext = iCoreUser.getUser(userContextId);
			return categoryBuilder.BuildArrayVOObject(VOCategory.class,
					iCoreCategory.getCategories(from, to, userContext));
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e,
					"obtener los rubros por intervalo de fecha");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}
	
	public VOCategory[] getCategoriesAllVersions(int id, Date date) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);
			
			return categoryBuilder.BuildArrayVOObject(VOCategory.class,
					iCoreCategory.getCategoriesAllVersions(id, date));
			
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e,
					"obtener los rubros por intervalo de fecha");
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

	public VOBill updateBill(int id, VOBill voBill) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			Bill bill = iCoreBill.updateBill(id,
					billBuilder.BuildBusinessObject(voBill));

			return billBuilder.BuildVOObject(bill);

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "modificar la factura");
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

	public VOBill[] getBills(Date from, Date to, int userContextId) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			User userContext = iCoreUser.getUser(userContextId);

			return billBuilder.BuildArrayVOObject(VOBill.class,
					iCoreBill.getBills(from, to, userContext));

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

	public VOBill[] getAllBillsByFilters(Date from, Date to,
			boolean isLiquidated, int userContextId) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			User userContext = iCoreUser.getUser(userContextId);

			return billBuilder.BuildArrayVOObject(VOBill.class,
					iCoreBill.getBills(from, to, isLiquidated, userContext));

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener todas las facturas");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}
	
	//Utilizado esta operacion para reportes por lo cual no tiene userContextId
	public VOBill[] getAllBillsByYearAndProject(Date date, int projectId) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return billBuilder.BuildArrayVOObject(VOBill.class,
					iCoreBill.getBills(date, projectId));

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener todas las facturas");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}

	public double getTotalAmountBills(int projectId, Date from, Date to) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return iCoreBill.getTotalAmountBills(projectId, from, to);

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener todas las facturas");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return 0;
	}
	
	public VOBill[] getBillsByFilters(Date from, Date to, boolean isLiquidated,
			boolean withCharges, int userContextId) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			User userContext = iCoreUser.getUser(userContextId);

			return billBuilder
					.BuildArrayVOObject(VOBill.class, iCoreBill.getBills(from,
							to, isLiquidated, withCharges, userContext));

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

	public VOBill[] getBillsByFiltersWithCharges(Date from, Date to,
			int userContextId) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			User userContext = iCoreUser.getUser(userContextId);

			return billBuilder.BuildArrayVOObject(VOBill.class,
					iCoreBill.getBillsWithCharges(from, to, userContext));

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

	public boolean deleteBills(int[] ids) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			iCoreBill.deleteBills(ids);
			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "eliminar multiples facturas");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return false;
	}

	/* PROYECTOS */

	public boolean insertProject(VOProject voProject) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			Project project = projectBuilder.BuildBusinessObject(voProject);
			ArrayList<ProjectEmployed> employedProjects = projectBuilder
					.BuildEmployedProjects(voProject.getVoEmployedProjects());
			ArrayList<ProjectPartner> partnerProjects = projectBuilder
					.BuildPartnerProjects(voProject.getVoPartnerProjects());

			iCoreProject.insertProject(project, employedProjects,
					partnerProjects);

			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "Insertar proyecto");
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

	public VOProject getProject(int id) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			VOProject voProject = projectBuilder.BuildVOObject(iCoreProject
					.getProject(id));
			// Empleados
			ArrayList<ProjectEmployed> projEmpl = iCoreProject
					.getProjectEmployees(id);
			for (ProjectEmployed projectEmployed : projEmpl) {
				Employed employed = iCoreEmployed.getEmployed(projectEmployed
						.getEmployed().getId());
				projectEmployed.getEmployed().setName(employed.getName());
				projectEmployed.getEmployed().setLastName(
						employed.getLastName());
			}
			voProject.setVoEmployedProjects(projectBuilder
					.BuildVOEmployedProjects(projEmpl));
			// Distribucion
			ArrayList<ProjectPartner> projectPartners = iCoreProject
					.getProjectPartners(id);

			for (ProjectPartner projectPartner : projectPartners) {
				Employed employed = iCoreEmployed.getEmployed(projectPartner
						.getEmployed().getId());
				projectPartner.getEmployed().setName(employed.getName());
				projectPartner.getEmployed()
						.setLastName(employed.getLastName());
			}

			voProject.setVoPartnerProjects(projectBuilder
					.BuildVOPartnerProjects(projectPartners));
			return voProject;

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener el proyecto.");
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

	public VODistributionType[] getDistributionTypes() {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return projectBuilder.BuildVOArrayDistributionType(iCoreProject
					.getDistributionTypes());

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e,
					"obtener todos las distribuciones de las ganancias");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}

	public boolean deleteProject(int id) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			iCoreProject.deleteProject(id);
			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "eliminar el proyecto");
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

	public VOProject[] getProjectsByStatus(int userContextId, boolean projectStatus) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			User userContext = iCoreUser.getUser(userContextId);
			
			return projectBuilder.BuildArrayVOObject(VOProject.class,
					iCoreProject.getProjectByStatus(userContext, projectStatus));

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener todos los usuarios");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}

	public VOProject updateProject(VOProject voProject) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			Project project = projectBuilder.BuildBusinessObject(voProject);
			ArrayList<ProjectEmployed> employedProjects = projectBuilder
					.BuildEmployedProjects(voProject.getVoEmployedProjects());
			ArrayList<ProjectPartner> partnerProjects = projectBuilder
					.BuildPartnerProjects(voProject.getVoPartnerProjects());

			Project projectUpdated = iCoreProject.updateProject(project, employedProjects, partnerProjects);
			
			VOProject voProjectUpdated = projectBuilder.BuildVOObject(projectUpdated);
			// Empleados
			ArrayList<ProjectEmployed> projEmpl = iCoreProject.getProjectEmployees(voProjectUpdated.getId());
			for (ProjectEmployed projectEmployed : projEmpl) {
				Employed employed = iCoreEmployed.getEmployed(projectEmployed
						.getEmployed().getId());
				projectEmployed.getEmployed().setName(employed.getName());
				projectEmployed.getEmployed().setLastName(
						employed.getLastName());
			}
			voProject.setVoEmployedProjects(projectBuilder
					.BuildVOEmployedProjects(projEmpl));
			
			// Distribucion
			ArrayList<ProjectPartner> projectPartners = iCoreProject.getProjectPartners(voProjectUpdated.getId());

			for (ProjectPartner projectPartner : projectPartners) {
				Employed employed;

				employed = iCoreEmployed.getEmployed(projectPartner
						.getEmployed().getId());

				projectPartner.getEmployed().setName(employed.getName());
				projectPartner.getEmployed()
						.setLastName(employed.getLastName());
			}

			voProject.setVoPartnerProjects(projectBuilder.BuildVOPartnerProjects(projectPartners));
			return voProjectUpdated;
			
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "modificar proyecto");
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

	//

	/* COMIENZO COBROS */
	public boolean insertCharge(VOCharge voCharge) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			iCoreCharge.insertCharge(chargeBuilder
					.BuildBusinessObject(voCharge));

			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "insertar cobro");
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

	public VOCharge[] getChargesByBill(int billId) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return chargeBuilder.BuildArrayVOObject(VOCharge.class,
					iCoreCharge.getChargesByBill(billId));

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener cobros por factura");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}

	public VOCharge updateCharge(int id, VOCharge voCharge) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return chargeBuilder.BuildVOObject(iCoreCharge.updateCharge(id,
					chargeBuilder.BuildBusinessObject(voCharge)));

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "modificar cobro");
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

	public boolean deleteCharges(int[] ids) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			iCoreCharge.deleteCharges(ids);
			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "eliminar multiples cobros");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return false;
	}

	public VOCharge[] getCharges(int userContextId) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			User userContext = iCoreUser.getUser(userContextId);

			return chargeBuilder.BuildArrayVOObject(VOCharge.class,
					iCoreCharge.getCharges(userContext));

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener cobros por factura");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}

	public VOCharge[] getChargesByFilters(boolean isBillLiquidated,
			boolean isProjectClosed, int userContextId) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			User userContext = iCoreUser.getUser(userContextId);

			return chargeBuilder
					.BuildArrayVOObject(VOCharge.class, iCoreCharge.getCharges(
							isBillLiquidated, isProjectClosed, userContext));

		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener cobros por factura");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return null;
	}

	/* COMIENZO LIQUIDACIONES */
	public boolean createLiquidation(Date month, int userContextId,
			double typeExchange) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			User userContext = iCoreUser.getUser(userContextId);
			
			iCoreCompanyLiquidation.liquidationByCompany(month, userContext,
					typeExchange,true);
			
			return true;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "crear liquidacion");
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
	
	public VOCompanyLiquidation getCompanyLiquidationPreview(Date month, int userContextId, double typeExchange){
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			User userContext = iCoreUser.getUser(userContextId);
			CompanyLiquidation companyLiquidation = iCoreCompanyLiquidation.liquidationByCompany(month, userContext,typeExchange,false);
			VOCompanyLiquidation voCompanyLiquidation = companyLiquidationBuilder.BuildVOObject(companyLiquidation);
			voCompanyLiquidation.setPartner1(employedBuilder.BuildVOObject(companyLiquidation.getPartner1()));
			voCompanyLiquidation.setPartner2(employedBuilder.BuildVOObject(companyLiquidation.getPartner2()));
			if(companyLiquidation.getCategoriesHuman() != null)
				voCompanyLiquidation.setCategoriesHuman(categoryBuilder.BuildArrayVOObject(VOCategory.class, companyLiquidation.getCategoriesHuman()));
			if(companyLiquidation.getCategoriesMaterial() != null)
				voCompanyLiquidation.setCategoriesMaterial(categoryBuilder.BuildArrayVOObject(VOCategory.class, companyLiquidation.getCategoriesMaterial()));
			if(companyLiquidation.getEmployees() != null)
				voCompanyLiquidation.setEmployees(projectBuilder.BuildVOEmployedProjects(companyLiquidation.getEmployees()));
			
			return voCompanyLiquidation;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "crear liquidacion");
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
	
	
	public VOProjectLiquidation getProjectLiquidationsPreview(Date month, int projectId, double typeExchange){
		try{
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);			
			
			ProjectLiquidation liquidation =  iCoreProjectLiquidation.getProjectLiquidationPreview(month, projectId, typeExchange);
			
			VOProjectLiquidation voLiquidation = projectLiquidationBuilder.BuildVOObject(liquidation);			
						
			voLiquidation.setBills(billBuilder.BuildArrayVOObject(VOBill.class, liquidation.getBills()));
			voLiquidation.setCategoriesHuman(categoryBuilder.BuildArrayVOObject(VOCategory.class, liquidation.getCategoriesHuman()));
			voLiquidation.setCategoryMaterial(categoryBuilder.BuildArrayVOObject(VOCategory.class, liquidation.getCategoriesMaterial()));
			voLiquidation.setEmployees(projectBuilder.BuildVOEmployedProjects(liquidation.getEmployees()));
			voLiquidation.setProject(projectBuilder.BuildVOObject(liquidation.getProject()));
			
			return voLiquidation;
			
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener liquidaciones de proyectos para mostrar");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		}catch (ClientException e) {
			throw new RuntimeException(e.getMessage());
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
			
			VOProjectLiquidation voLiquidation = projectLiquidationBuilder.BuildVOObject(liquidation);			
						
			voLiquidation.setBills(billBuilder.BuildArrayVOObject(VOBill.class, liquidation.getBills()));
			voLiquidation.setCategoriesHuman(categoryBuilder.BuildArrayVOObject(VOCategory.class, liquidation.getCategoriesHuman()));
			voLiquidation.setCategoryMaterial(categoryBuilder.BuildArrayVOObject(VOCategory.class, liquidation.getCategoriesMaterial()));
			voLiquidation.setEmployees(projectBuilder.BuildVOEmployedProjects(liquidation.getEmployees()));
			voLiquidation.setProject(projectBuilder.BuildVOObject(liquidation.getProject()));
			
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
	
	public VOProjectLiquidation[] getProjectsLiquidations(Date month){
		VOProjectLiquidation[] voProjectsLiquidations= null;
		try{
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);			
			
			ArrayList<ProjectLiquidation> projectsLiquidations = iCoreProjectLiquidation.getProjectsLiquidationsByDate(month);
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
	
	public boolean existLiquidation(Date month) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return iCoreCompanyLiquidation.existLiquidation(month);
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "validar si existe el usuario");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return false;
	}
	
	//Reports
	public VOProjectLiquidation[] getProjectsLiquidationsWithMoreEarnings(Date from, Date to, boolean isCurrencyDollar, int count){
		VOProjectLiquidation[] voProjectsLiquidations= null;
		try{
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);			
			
			ArrayList<ProjectLiquidation> projectsLiquidations = iCoreProjectLiquidation.getProjectsWithMoreEarnings(from, to, isCurrencyDollar, count);
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
	
	public VOProjectLiquidation[] getProjectLiquidations(int projectId, Date date, boolean isCurrencyDollar){
		VOProjectLiquidation[] voProjectsLiquidations= null;
		try{
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);			
			
			ArrayList<ProjectLiquidation> projectsLiquidations = iCoreProjectLiquidation.getProjectLiquidations(projectId, date, isCurrencyDollar);
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
	
	public VOCompanyLiquidation[] getCompanyLiquidations(Date date){
		try{
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);			
		
			return companyLiquidationBuilder.BuildArrayVOObject(VOCompanyLiquidation.class, iCoreCompanyLiquidation.getCompanyLiquidations(date));
			
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
	
	public double getTypeExchange(Date month){
		try{
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);
			
			return iCoreCompanyLiquidation.getTypeExchange(month);
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener liquidaciones de proyectos para mostrar");
		} catch (InterruptedException e) {
			throw new RuntimeException(Constants.TRANSACTION_ERROR);
		} catch (Exception e) {
			ThrowGenericExceptionAndLogError(e);
		} finally {
			transactionLock.unlock();
		}
		return 0;
	}
	
	public VOGlobalConfiguration[] getGlobalConfigurations(){
		try{
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);			
		
			return globalConfigurationBuilder.BuildArrayVOObject(VOGlobalConfiguration.class, iCoreGlobalConfiguration.getGlobalConfigurations());
			
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
	
	public VOGlobalConfiguration updateGlobalConfiguration(int id, VOGlobalConfiguration voGlobalConfiguration){
		try{
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME, TimeUnit.SECONDS);			
		
			return globalConfigurationBuilder.BuildVOObject(iCoreGlobalConfiguration.update(id, globalConfigurationBuilder.BuildBusinessObject(voGlobalConfiguration)));
			
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
}

