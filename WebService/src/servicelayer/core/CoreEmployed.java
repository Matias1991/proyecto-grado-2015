package servicelayer.core;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.EmployedType;
import servicelayer.entity.businessEntity.SalarySummary;
import shared.ConfigurationProperties;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreEmployed;

public class CoreEmployed implements ICoreEmployed {

	private static CoreEmployed instance = null;

	private CoreEmployed() {
	}

	public static CoreEmployed GetInstance() throws ServerException {
		if (instance == null) {
			instance = new CoreEmployed();
		}
		return instance;
	}

	@Override
	public void insertEmployed(Employed employed, SalarySummary salarySummary)
			throws ServerException, ClientException {

		// utlizar el dao manager para realizar el insertar el empleado y su
		// respectivo resumen de salario
		// se utliza la misma conexion para realizar ambas transacciones
		// si falla un de ellas se revierten los cambios
		DAOManager daoManager = new DAOManager();
		try {
			int countPartners = daoManager.getDAOEmployees().getEmployedByType(2).size();
			if (countPartners >= 2
					&& employed.getEmployedType() == EmployedType.PARTNER) {
				throw new ClientException(
						"No se pueden crear más de dos empleados de tipo socio");
			} else {

				employed.setIDAOSalarySummaries(daoManager
						.getDAOSalarySummaries());
				employed.setCreatedDateTimeUTC(new Date());
				employed.setUpdatedDateTimeUTC(new Date());
				// add new employed
				int newEmployedId = daoManager.getDAOEmployees().insert(
						employed);

				employed.setId(newEmployedId);
				employed.setDeleted(false);
				SalarySummary calculateSalarySummary = calculateSalarySummary(salarySummary);
				calculateSalarySummary.setCreatedDateTimeUTC(new Date());
				// add new salary summary for employed
				employed.addNewSalarySummary(calculateSalarySummary);

				daoManager.commit();
			}

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public ArrayList<Employed> getEmployess() throws ServerException {

		DAOManager daoManager = new DAOManager();
		try {
			return daoManager.getDAOEmployees().getObjects();

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public Employed getEmployed(int id) throws ServerException, ClientException {

		Employed employed;

		DAOManager daoManager = new DAOManager();
		try {
			employed = daoManager.getDAOEmployees().getObject(id);
			if (employed == null)
				throw new ClientException("No existe un empleado con ese id");

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return employed;
	}

	@Override
	public SalarySummary estimateSalarySummary(SalarySummary voSummarySalary)
			throws ServerException {
		SalarySummary salarySummary = calculateSalarySummary(voSummarySalary);

		return salarySummary;
	}

	@Override
	public void deleteEmployed(int id) throws ServerException, ClientException {
		DAOManager daoManager = new DAOManager();
		try {

			Employed employed = daoManager.getDAOEmployees().getObject(id);
			if (employed != null) {
				// DELETE ALL SALARY SUMMARIES
//				employed.setIDAOSalarySummaries(daoManager
//						.getDAOSalarySummaries());
//				employed.deleteSalarySummaries();

				// DELETE EMPLOYED
				daoManager.getDAOEmployees().delete(id);
				daoManager.commit();
			} else
				throw new ClientException("No existe un empleado con ese id");

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public void updateEmployed(int id, Employed employed, SalarySummary salarySummary)
			throws ServerException, ClientException {
		DAOManager daoManager = new DAOManager();

		try {

			Employed currentEmployed = daoManager.getDAOEmployees().getObject(
					id);
			if (currentEmployed != null) {

				currentEmployed.setIDAOSalarySummaries(daoManager
						.getDAOSalarySummaries());

				int countPartners = daoManager.getDAOEmployees()
						.getEmployedByType(2).size();

				if (countPartners >= 2
						&& employed.getEmployedType() == EmployedType.PARTNER
						&& currentEmployed.getEmployedType() != employed
								.getEmployedType()) {
					throw new ClientException(
							"No pueden haber más de dos empleados de tipo socio");

				} else {

					// UPDATE EMPLOYED
					employed.setUpdatedDateTimeUTC(new Date());
					daoManager.getDAOEmployees().update(id, employed);

					// CREATE NEW VERSION OF SALARY SUMMARY
					SalarySummary currentSalarySummary = currentEmployed.getLatestVersionSalarySummary();
					SalarySummary calculateSalarySummary = calculateSalarySummary(salarySummary);
					if (updatedSalarySummaries(currentSalarySummary,
							calculateSalarySummary)) {
						if (!DateFormat
								.getDateInstance()
								.format(currentSalarySummary
										.getCreatedDateTimeUTC())
								.equals(DateFormat.getDateInstance().format(
										new Date()))) {
							calculateSalarySummary.setCreatedDateTimeUTC(new Date());
							currentEmployed.addNewSalarySummary(calculateSalarySummary);
						} else {
							// Actualizar el mismo registro que esta
							// salarySummary.setCreatedDateTimeUTC(currentSalarySummary.getCreatedDateTimeUTC());
							calculateSalarySummary.setCreatedDateTimeUTC(new Date());
							calculateSalarySummary.setId(currentSalarySummary.getId());
							currentEmployed.updateSalarySummary(calculateSalarySummary);
						}
					}

					daoManager.commit();
				}

			} else
				throw new ClientException("No existe un empleado con ese id");

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public ArrayList<Integer> getAllVersionSalarySummary(int employedId)
			throws ServerException, ClientException {
		ArrayList<Integer> list;
		DAOManager daoManager = new DAOManager();
		try {
			Employed employed = daoManager.getDAOEmployees().getObject(
					employedId);
			if (employed != null) {
				employed.setIDAOSalarySummaries(daoManager
						.getDAOSalarySummaries());
				list = employed.getAllVersionsSalarySummary();
			} else
				throw new ClientException("No existe un empleado con ese id");

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return list;
	}

	@Override
	public SalarySummary getSalarySummaryByVersion(int employedId, int version)
			throws ServerException, ClientException {
		SalarySummary salarySummary = null;
		DAOManager daoManager = new DAOManager();
		try {
			Employed employed = daoManager.getDAOEmployees().getObject(
					employedId);
			if (employed != null) {
				employed.setIDAOSalarySummaries(daoManager.getDAOSalarySummaries());
				
				//version -1, indica la ultima version
				if(version == -1)
					salarySummary = employed.getLatestVersionSalarySummary();
				else
					salarySummary = employed.getSalarySummaryByVersion(version);
			} else
				throw new ClientException("No existe un empleado con ese id");

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return salarySummary;
	}

	
	@Override
	public ArrayList<SalarySummary> getAllSalarySummaryVersion(
			int employedId) throws ServerException, ClientException {
		ArrayList<SalarySummary> list = new ArrayList<SalarySummary>();

		DAOManager daoManager = new DAOManager();
		try {
			Employed employed = daoManager.getDAOEmployees().getObject(
					employedId);
			if (employed != null) {
				employed.setIDAOSalarySummaries(daoManager
						.getDAOSalarySummaries());
				ArrayList<SalarySummary> aux = employed.getAllSalarySummaryVersion(employedId);

				for (SalarySummary salarySummary : aux) {
					SalarySummary voSSV = new SalarySummary();
					voSSV.setCreatedDateTimeUTC(salarySummary
							.getCreatedDateTimeUTC());
					voSSV.setVersion(salarySummary.getVersion());
					list.add(voSSV);
				}

			} else
				throw new ClientException("No existe un empleado con ese id");

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
		return list;
	}

	SalarySummary calculateSalarySummary(SalarySummary salarySummary)
			throws ServerException {
		SalarySummary newSalarySummary = new SalarySummary();

		double percentage_persoanl_retirement_contribution = Double
				.parseDouble(ConfigurationProperties
						.GetConfigValue("PERCENTAGE_PERSONAL_RETIREMENT_CONTRIBUTION"));
		double percentage_employers_contributions_retirement = Double
				.parseDouble(ConfigurationProperties
						.GetConfigValue("PERCENTAGE_EMPLOYERS_CONTRIBUTIONS_RETIREMENT"));
		double percentage_employers_FONASA_contribution = Double
				.parseDouble(ConfigurationProperties
						.GetConfigValue("PERCENTAGE_EMPLOYERS_FONASA_CONTRIBUTION"));
		double percentage_FRL_contribution = Double
				.parseDouble(ConfigurationProperties
						.GetConfigValue("PERCENTAGE_FRL_CONTRIBUTION"));
		double percentage_tickets_employers = Double
				.parseDouble(ConfigurationProperties
						.GetConfigValue("PERCENTAGE_TICKETS_EMPLOYERS"));
		double var1_prev = Double.parseDouble(ConfigurationProperties
				.GetConfigValue("VAR_1_PREV"));
		double var2_prev = Double.parseDouble(ConfigurationProperties
				.GetConfigValue("VAR_2_PREV"));
		double var3_prev = Double.parseDouble(ConfigurationProperties
				.GetConfigValue("VAR_3_PREV"));
		double percentage_incidence_salary = Double
				.parseDouble(ConfigurationProperties
						.GetConfigValue("PERCENTAGE_INCIDENCE_SALARY"));

		newSalarySummary.setNominalSalary(salarySummary.getNominalSalary());
		newSalarySummary.setTickets(salarySummary.getTickets());

		newSalarySummary.setiRPF(salarySummary.getiRPF());
		newSalarySummary.setbSE(salarySummary.getbSE());
		newSalarySummary.setrET(salarySummary.getrET());
		newSalarySummary.setHours(salarySummary.getHours());
		newSalarySummary.setCostSaleHour(salarySummary.getCostSaleHour());

		// aporte jubilatorio personal
		double personalRetirementContribution = (salarySummary
				.getNominalSalary() * percentage_persoanl_retirement_contribution);
		newSalarySummary
				.setPersonalRetirementContribution(personalRetirementContribution);

		// aporte jubilatorio patronal
		double employersContributionsRetirement = (salarySummary
				.getNominalSalary() * percentage_employers_contributions_retirement);
		newSalarySummary
				.setEmployersContributionsRetirement(employersContributionsRetirement);

		// aporte FONASA personal
		double personalFONASAContribution = salarySummary.getNominalSalary()
				* salarySummary.getPercentageTypeFONASA();
		newSalarySummary
				.setPersonalFONASAContribution(personalFONASAContribution);

		// aporte FONASA patronal
		double employersFONASAContribution = salarySummary.getNominalSalary()
				* percentage_employers_FONASA_contribution;
		newSalarySummary
				.setEmployersFONASAContribution(employersFONASAContribution);

		// aporte FRL personal
		double personalFRLContribution = salarySummary.getNominalSalary()
				* percentage_FRL_contribution;
		newSalarySummary.setPersonalFRLContribution(personalFRLContribution);

		double employersFRLContribution = salarySummary.getNominalSalary()
				* percentage_FRL_contribution;
		newSalarySummary.setEmployersFRLContribution(employersFRLContribution);

		// tickets patronal
		double ticketsEmployers = salarySummary.getTickets()
				* percentage_tickets_employers;
		newSalarySummary.setTicketsEmployers(ticketsEmployers);

		// total de descuentos
		// aporte jubilatorio personal + FONASA personal + FRL personal + IRPF
		double totalDiscounts = newSalarySummary
				.getPersonalRetirementContribution()
				+ newSalarySummary.getPersonalFONASAContribution()
				+ newSalarySummary.getPersonalFRLContribution()
				+ newSalarySummary.getiRPF();
		newSalarySummary.setTotalDiscounts(totalDiscounts);

		// total aportes patronales
		// aporte jubilatorio patronal + FONASA patronal + FRL patronal + total
		// tickets patronal + BSE
		double totalEmployerContributions = newSalarySummary
				.getEmployersContributionsRetirement()
				+ newSalarySummary.getEmployersFONASAContribution()
				+ newSalarySummary.getEmployersFRLContribution()
				+ newSalarySummary.getTicketsEmployers()
				+ newSalarySummary.getbSE();
		newSalarySummary
				.setTotalEmployerContributions(totalEmployerContributions);

		// nominal sin aportes
		double nominalWithoutContributions = newSalarySummary
				.getNominalSalary() - newSalarySummary.getTotalDiscounts();
		newSalarySummary
				.setNominalWithoutContributions(nominalWithoutContributions);

		// prevencion de despido
		// =(nominal/12)+((nominal/12)*var3)+(nominal/30*var1*var2)
		double nominal = newSalarySummary.getNominalSalary();
		double dismissalPrevention = (nominal / 12)
				+ ((nominal / 12) * var3_prev)
				+ (nominal / 30 * var1_prev * var2_prev);
		newSalarySummary.setDismissalPrevention(dismissalPrevention);

		// incidencia sueldo
		double incidenceSalary = newSalarySummary.getNominalSalary()
				* percentage_incidence_salary;
		newSalarySummary.setIncidenceSalary(incidenceSalary);

		// incidencia tickets
		// =(nominal/12)+((nominal/12)*var3)
		double tickets = newSalarySummary.getTickets();
		double incidenceTickets = (tickets / 12) + ((tickets / 12) * var3_prev);
		newSalarySummary.setIncidenceTickets(incidenceTickets);

		// salario a pagar(sueldo liquido del empleado)
		// nominal + tickets - total descuentos - ret
		double salaryToPay = newSalarySummary.getNominalSalary()
				+ newSalarySummary.getTickets()
				- newSalarySummary.getTotalDiscounts()
				- newSalarySummary.getrET();
		newSalarySummary.setSalaryToPay(salaryToPay);

		// costo mensual
		// nominal + tickets + total aportes patronales + incidencia de sueldo +
		// incidencia de tickes + prevencion de despido
		double _nominal = newSalarySummary.getNominalSalary();
		double _tickets = newSalarySummary.getTickets();
		double _totalEmployersContributions = newSalarySummary
				.getTotalEmployerContributions();
		double _incidenceSalary = newSalarySummary.getIncidenceSalary();
		double _incidenceTickets = newSalarySummary.getIncidenceTickets();
		double _dismissalPrevention = newSalarySummary.getDismissalPrevention();
		;
		double costMonth = _nominal + _tickets + _totalEmployersContributions
				+ _incidenceSalary + _incidenceTickets + _dismissalPrevention;
		newSalarySummary.setCostMonth(costMonth);

		// costo hora
		// costo mensual / cantidad de horas
		double costRealHour = newSalarySummary.getCostMonth()
				/ newSalarySummary.getHours();
		newSalarySummary.setCostRealHour(costRealHour);

		return newSalarySummary;
	}

	boolean updatedSalarySummaries(SalarySummary currentSalarySummary,
			SalarySummary newSalarySummary) {
		boolean updated = false;

		if (currentSalarySummary.getNominalSalary() != newSalarySummary
				.getNominalSalary()) {
			updated = true;
		}
		if (currentSalarySummary.getTickets() != newSalarySummary.getTickets()) {
			updated = true;
		}
		if (currentSalarySummary.getPersonalFONASAContribution()
				/ currentSalarySummary.getNominalSalary() != newSalarySummary
				.getPersonalFONASAContribution()
				/ newSalarySummary.getNominalSalary()) {
			updated = true;
		}
		if (currentSalarySummary.getHours() != newSalarySummary.getHours()) {
			updated = true;
		}
		if (currentSalarySummary.getiRPF() != newSalarySummary.getiRPF()) {
			updated = true;
		}
		if (currentSalarySummary.getbSE() != newSalarySummary.getbSE()) {
			updated = true;
		}
		if (currentSalarySummary.getrET() != newSalarySummary.getrET()) {
			updated = true;
		}
		if (currentSalarySummary.getCostSaleHour() != newSalarySummary
				.getCostSaleHour()) {
			updated = true;
		}

		return updated;
	}

	@Override
	public ArrayList<Employed> getEmployedByType(int employedTypeId) throws ServerException,
			ClientException {
		DAOManager daoManager = new DAOManager();
		try {
			return daoManager.getDAOEmployees().getEmployedByType(employedTypeId);

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		
	}
	
	@Override
	public ArrayList<SalarySummary> getSalarySummariesLatestVersionUpToDate(Date to) throws ServerException, ClientException {
		ArrayList<SalarySummary> salaries = new ArrayList<SalarySummary>();
		DAOManager daoManager = new DAOManager();
		
		try{
			ArrayList<Employed> employees = daoManager.getDAOEmployees().getEmployeesToDate(to);
			for (Employed employed : employees) {
				employed.setIDAOSalarySummaries(daoManager.getDAOSalarySummaries());
				salaries.add(employed.getSalarySummaryToDate(to));
			}			
			
			return salaries;
		}catch (ServerException e){
			throw e;
		}finally{
			daoManager.close();
		}		
	}

	@Override
	public ArrayList<Employed> getEmployeesToDate(Date to)
			throws ServerException, ClientException {
		DAOManager daoManager = new DAOManager();
		try {
			return daoManager.getDAOEmployees().getEmployeesToDate(to);

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}


	}

}
