package servicelayer.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import servicelayer.core.CoreBill;
import servicelayer.core.CoreCategory;
import servicelayer.core.CoreEmployed;
import servicelayer.core.CoreProject;
import servicelayer.core.CoreUser;
import servicelayer.entity.valueObject.VOBill;
import servicelayer.entity.valueObject.VOCategory;
import servicelayer.entity.valueObject.VOEmployed;
import servicelayer.entity.valueObject.VOProject;
import servicelayer.entity.valueObject.VOSalarySummary;
import servicelayer.entity.valueObject.VOSalarySummaryVersion;
import servicelayer.entity.valueObject.VOUser;
import servicelayer.utilities.Constants;
import shared.LoggerMSMP;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreBill;
import shared.interfaces.core.ICoreCategory;
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

	public ServiceWeb() {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			iCoreUser = CoreUser.GetInstance();
			iCoreEmployed = CoreEmployed.GetInstance();
			iCoreCategory = CoreCategory.GetInstance();
			iCoreProject = CoreProject.GetInstance();
			iCoreBill = CoreBill.GetInstance();
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

			iCoreUser.insertUser(voUser);

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

			return iCoreUser.getUser(id);
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

			return iCoreUser.update(id, voUser);
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

			return iCoreUser.login(userName, password);
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
		ArrayList<VOUser> voUsers;
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			voUsers = iCoreUser.getUsers();
			VOUser[] arrayVoUser = new VOUser[voUsers.size()];
			voUsers.toArray(arrayVoUser);
			return arrayVoUser;

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
		ArrayList<VOUser> voUsers;
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			voUsers = iCoreUser.getUsersByStatus(userStatusId);
			VOUser[] arrayVoUser = new VOUser[voUsers.size()];
			voUsers.toArray(arrayVoUser);
			return arrayVoUser;

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

			iCoreEmployed.insertEmployed(voEmployed);

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
		ArrayList<VOEmployed> voEmployees;
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			voEmployees = iCoreEmployed.getEmployess();
			VOEmployed[] arrayVOEmployed = new VOEmployed[voEmployees.size()];
			voEmployees.toArray(arrayVOEmployed);
			return arrayVOEmployed;

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

			return iCoreEmployed.getEmployed(id);
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

			return iCoreEmployed.estimateSalarySummary(voSalarySummary);
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

			iCoreEmployed.updateEmployed(id, voEmployed);
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

	public VOSalarySummaryVersion[] getAllSalarySummaryVersion(int employedId) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			ArrayList<VOSalarySummaryVersion> listAllVersions = iCoreEmployed
					.getAllSalarySummaryVersion(employedId);
			VOSalarySummaryVersion[] arrayListVersion = new VOSalarySummaryVersion[listAllVersions
					.size()];
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

	public VOSalarySummary getSalarySummaryByVersion(int employedId, int version) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return iCoreEmployed.getSalarySummaryByVersion(employedId, version);

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

			iCoreCategory.insertCategory(voCategory);

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

			return iCoreCategory.getCategory(id);
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
		ArrayList<VOCategory> voCategories;
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			voCategories = iCoreCategory.getCategories();
			VOCategory[] arrayVOCategories = new VOCategory[voCategories.size()];
			voCategories.toArray(arrayVOCategories);
			return arrayVOCategories;
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

	public VOCategory updateCategory(int id, VOCategory category) {
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			return iCoreCategory.updateCategory(id, category);
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
		ArrayList<VOCategory> voCategories;
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			voCategories = iCoreCategory.getCategoriesByProject(id);
			VOCategory[] arrayVOCategories = new VOCategory[voCategories.size()];
			voCategories.toArray(arrayVOCategories);
			return arrayVOCategories;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener los rubros del proyecto");
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

			iCoreBill.insertBill(voBill);

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

			return iCoreBill.updateBill(id, voBill);
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

	public VOBill[] getBills() {
		ArrayList<VOBill> voBills;
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			voBills = iCoreBill.getBills();
			VOBill[] arrayVOBills = new VOBill[voBills.size()];
			voBills.toArray(arrayVOBills);
			return arrayVOBills;
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

	public VOBill[] getBillsWithFilters(Date from, Date to, int projectId,
			String code, boolean isLiquidated) {
		ArrayList<VOBill> voBills;
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			voBills = iCoreBill.getBills(from, to, projectId, code,
					isLiquidated);
			VOBill[] arrayVOBills = new VOBill[voBills.size()];
			voBills.toArray(arrayVOBills);
			return arrayVOBills;
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
	
	public boolean deleteBills(int [] ids)
	{
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

			iCoreProject.insertProject(voProject);

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

	public VOProject[] getProjects() {
		ArrayList<VOProject> voProjects;
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			voProjects = iCoreProject.getProjects();
			VOProject[] arrayVOProjects = new VOProject[voProjects.size()];
			voProjects.toArray(arrayVOProjects);
			return arrayVOProjects;
		} catch (ServerException e) {
			ThrowServerExceptionAndLogError(e, "obtener todos los proyectos");
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
		ArrayList<VOProject> voProjects;
		try {
			transactionLock.tryLock(Constants.DEFAULT_TRANSACTION_TIME,
					TimeUnit.SECONDS);

			voProjects = iCoreProject.getProjectByStatus(projectStatus);
			VOProject[] arrayVoProjects = new VOProject[voProjects.size()];
			voProjects.toArray(arrayVoProjects);
			return arrayVoProjects;

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
}
