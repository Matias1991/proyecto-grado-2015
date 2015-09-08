package servicelayer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import servicelayer.core.CoreBill;
import servicelayer.core.CoreCategory;
import servicelayer.core.CoreCharge;
import servicelayer.core.CoreEmployed;
import servicelayer.core.CoreProject;
import servicelayer.core.CoreUser;
import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.ChanelType;
import servicelayer.entity.businessEntity.DistributionType;
import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.ProjectEmployed;
import servicelayer.entity.businessEntity.ProjectPartner;
import servicelayer.entity.businessEntity.SalarySummary;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.businessEntity.UserType;
import servicelayer.entity.valueObject.VOBill;
import servicelayer.entity.valueObject.VOCategory;
import servicelayer.entity.valueObject.VOCharge;
import servicelayer.entity.valueObject.VODistributionType;
import servicelayer.entity.valueObject.VOEmployed;
import servicelayer.entity.valueObject.VOProject;
import servicelayer.entity.valueObject.VOSalarySummary;
import servicelayer.entity.valueObject.VOUser;
import servicelayer.service.builder.BillBuilder;
import servicelayer.service.builder.CategoryBuilder;
import servicelayer.service.builder.ChargeBuilder;
import servicelayer.service.builder.EmployedBuilder;
import servicelayer.service.builder.ProjectBuilder;
import servicelayer.service.builder.SalarySummaryBuilder;
import servicelayer.service.builder.UserBuilder;
import servicelayer.utilities.Constants;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreBill;
import shared.interfaces.core.ICoreCategory;
import shared.interfaces.core.ICoreCharge;
import shared.interfaces.core.ICoreEmployed;
import shared.interfaces.core.ICoreProject;
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

	public VOSalarySummary estimateSalarySummary(VOSalarySummary voSalarySummary) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			SalarySummary salarySummary = iCoreEmployed
					.estimateSalarySummary(employedBuilder
							.BuildBusinessSalarySummary(voSalarySummary));
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

	public VOCategory[] getCategories() {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return categoryBuilder.BuildArrayVOObject(VOCategory.class,
					iCoreCategory.getCategories());

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

	public VOCategory[] getCategoriesByDate(Date from, Date to) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return categoryBuilder.BuildArrayVOObject(VOCategory.class,
					iCoreCategory.getCategories(from, to));

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

	public VOBill[] getBills(Date from, Date to) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return billBuilder.BuildArrayVOObject(VOBill.class,
					iCoreBill.getBills(from, to));

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
			boolean isLiquidated) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return billBuilder.BuildArrayVOObject(VOBill.class,
					iCoreBill.getBills(from, to, isLiquidated));

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

	public VOBill[] getBillsByFilters(Date from, Date to, boolean isLiquidated,
			boolean withCharges) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return billBuilder.BuildArrayVOObject(VOBill.class,
					iCoreBill.getBills(from, to, isLiquidated, withCharges));

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

	public VOBill[] getBillsByFiltersWithCharges(Date from, Date to) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return billBuilder.BuildArrayVOObject(VOBill.class,
					iCoreBill.getBillsWithCharges(from, to));

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

			User user = iCoreUser.getUser(userContextId);

			if (user.getUserType() == UserType.PARTNER) {
				return projectBuilder.BuildArrayVOObject(VOProject.class,
						iCoreProject.getProjects());
			} else {
				return projectBuilder.BuildArrayVOObject(VOProject.class,
						iCoreProject.getProjectsByManager(user.getId()));
			}

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

	public VOProject[] getProjectsByStatus(boolean projectStatus) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return projectBuilder.BuildArrayVOObject(VOProject.class,
					iCoreProject.getProjectByStatus(projectStatus));

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

	// `

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

	public VOCharge[] getCharges() {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return chargeBuilder.BuildArrayVOObject(VOCharge.class,
					iCoreCharge.getCharges());

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
			boolean isProjectClosed) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return chargeBuilder.BuildArrayVOObject(VOCharge.class,
					iCoreCharge.getCharges(isBillLiquidated, isProjectClosed));

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
	/* COMIENZO COBROS */
}
