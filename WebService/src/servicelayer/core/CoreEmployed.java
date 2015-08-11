package servicelayer.core;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import datalayer.daos.DAOEmployees;
import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.SalarySummary;
import servicelayer.entity.businessEntity.SalarySummaryVersion;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.valueObject.VOEmployed;
import servicelayer.entity.valueObject.VOSalarySummary;
import servicelayer.entity.valueObject.VOSalarySummaryVersion;
import servicelayer.entity.valueObject.VOUser;
import shared.ConfigurationProperties;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreEmployed;
import shared.interfaces.dataLayer.IDAOEmployees;

public class CoreEmployed implements ICoreEmployed{

	private static CoreEmployed instance = null;
    private static IDAOEmployees iDAOEmployees;
    
	private CoreEmployed() throws ServerException
	{
		iDAOEmployees = new DAOEmployees();
	}
	
	public static CoreEmployed GetInstance() throws ServerException
	{
		if(instance == null)
		{
			instance = new CoreEmployed();
		}
		return instance;
	}
	
	@Override
	public void insertEmployed(VOEmployed voEmployed) throws ServerException, ClientException{

		//utlizar el dao manager para realizar el insertar el empleado y su respectivo resumen de salario
		//se utliza la misma conexion para realizar ambas transacciones
		//si falla un de ellas se revierten los cambios
		DAOManager daoManager = new DAOManager();
		try {
			int countPartners = iDAOEmployees.getCountPartners();
			if(countPartners >= 2 && voEmployed.getEmployedType() == 2 ){
				throw new ClientException("No se pueden crear más de dos empleados de tipo socio");
			} else{
				Employed emp = new Employed(voEmployed, daoManager.getDAOSalarySummaries());
				emp.setCreatedDateTimeUTC(new Date());
				emp.setUpdatedDateTimeUTC(new Date());
				//add new employed
				int newEmployedId = daoManager.getDAOEmployees().insert(emp);
				
				emp.setId(newEmployedId);
				
				SalarySummary salarySummary = calculateSalarySummary(voEmployed.getvOSalarySummary());
				salarySummary.setCreatedDateTimeUTC(new Date());
				//add new salary summary for employed
				emp.addNewSalarySummary(salarySummary);
				
				daoManager.commit();	
			}
			

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		}
		finally
		{
			daoManager.close();
		}
	}
	
	@Override
	public ArrayList<VOEmployed> getEmployess() throws ServerException {
		
		ArrayList<Employed> employees;
		ArrayList<VOEmployed> voEmployess = null;

		employees = iDAOEmployees.getObjects();
		voEmployess = new ArrayList<VOEmployed>(); 
		
		for(Employed employed: employees)
		{
			SalarySummary salarySummary = employed.getLatestVersionSalarySummary();
			VOEmployed voEmployed = BuildVOEmployed(employed, salarySummary);
			
			
			voEmployess.add(voEmployed);
		}
		
		return voEmployess;
	}
	
	@Override
	public VOEmployed getEmployed(int id) throws ServerException, ClientException{
		
		Employed employed;
		VOEmployed voEmployed = null;

		employed = iDAOEmployees.getObject(id);
		if(employed != null)
		{
			SalarySummary salarySummary = employed.getLatestVersionSalarySummary();
			voEmployed = BuildVOEmployed(employed, salarySummary);
		}
		else
			throw new ClientException("No existe un empleado con ese id");

		return voEmployed;
	}
	
	@Override
	public VOSalarySummary estimateSalarySummary(VOSalarySummary voSummarySalary) throws ServerException
	{
		SalarySummary salarySummary = calculateSalarySummary(voSummarySalary);
		
		return BuildVOSalarySummary(salarySummary);
	}
	
	@Override
	public void deleteEmployed(int id) throws ServerException, ClientException
	{
		DAOManager daoManager = new DAOManager();
		
		try {
			
	    Employed employed = daoManager.getDAOEmployees().getObject(id);
	    if(employed != null)
	    {
	    	//DELETE ALL SALARY SUMMARIES
	    	employed.setIDAOSalarySummaries(daoManager.getDAOSalarySummaries());
	    	employed.deleteSalarySummaries();
			
	    	//DELETE EMPLOYED
			daoManager.getDAOEmployees().delete(id);
			daoManager.commit();
	    }
	    else
	    	throw new ClientException("No existe un empleado con ese id");
	    
		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		}
		finally
		{
			daoManager.close();
		}
	}
	
	@Override
	public void updateEmployed(int id, VOEmployed voEmployed) throws ServerException, ClientException
	{
		DAOManager daoManager = new DAOManager();
		
		try {
			
	    Employed currentEmployed = daoManager.getDAOEmployees().getObject(id);
	    if(currentEmployed != null)
	    {

			int countPartners = iDAOEmployees.getCountPartners();
			if(countPartners >= 2 && voEmployed.getEmployedType() == 2 ){
				throw new ClientException("No pueden haber más de dos empleados de tipo socio");
			} else{
				Employed updatedEmployed = new Employed(voEmployed);
		    	//UPDATE EMPLOYED	    	
		    	updatedEmployed.setUpdatedDateTimeUTC(new Date());
		    	daoManager.getDAOEmployees().update(id, updatedEmployed);
		    	
		    	//CREATE NEW VERSION OF SALARY SUMMARY	    	
		    	SalarySummary currentSalarySummary = currentEmployed.getLatestVersionSalarySummary();	    	
		    	currentEmployed.setIDAOSalarySummaries(daoManager.getDAOSalarySummaries());
		    	SalarySummary salarySummary = calculateSalarySummary(voEmployed.getvOSalarySummary());
		    	if(updatedSalarySummaries(currentSalarySummary, salarySummary)){
		    		if(!DateFormat.getDateInstance().format(currentSalarySummary.getCreatedDateTimeUTC()).equals(DateFormat.getDateInstance().format(new Date()))){	    			
		    			salarySummary.setCreatedDateTimeUTC(new Date());
		    			currentEmployed.addNewSalarySummary(salarySummary);
		    		}else{
		    			//Actualizar el mismo registro que esta
		    			//salarySummary.setCreatedDateTimeUTC(currentSalarySummary.getCreatedDateTimeUTC());
		    			salarySummary.setCreatedDateTimeUTC(new Date());
		    			salarySummary.setId(currentSalarySummary.getId());
		    			currentEmployed.updateSalarySummary(salarySummary);	    			
		    		}			    		    		
		    	}	    	
				
				daoManager.commit();			
			}
				
	    }
	    else
	    	throw new ClientException("No existe un empleado con ese id");
	    
		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		}
		finally
		{
			daoManager.close();
		}
	}
	
	@Override
	public ArrayList<Integer> getAllVersionSalarySummary(int employedId) throws ServerException, ClientException
	{
		ArrayList<Integer> list;
		
		Employed employed = iDAOEmployees.getObject(employedId);
		if(employed != null)
		{
			list = employed.getAllVersionsSalarySummary();
		}
		else
			throw new ClientException("No existe un empleado con ese id");
		
		return list;
		
	}

	@Override
	public VOSalarySummary getSalarySummaryByVersion(int employedId, int version) throws ServerException, ClientException
	{
		VOSalarySummary voSalarySummary = null;
		
		Employed employed = iDAOEmployees.getObject(employedId);
		if(employed != null)
		{
			SalarySummary salarySummary = employed.getSalarySummaryByVersion(version);
			if(salarySummary != null)
			{
				voSalarySummary = BuildVOSalarySummary(employed.getSalarySummaryByVersion(version));
			}
		}
		else
			throw new ClientException("No existe un empleado con ese id");
		
		return voSalarySummary;
		
	}
	
	SalarySummary calculateSalarySummary(VOSalarySummary voSalarySummary) throws ServerException
	{
		SalarySummary salarySummary = new SalarySummary();

		double percentage_persoanl_retirement_contribution = Double.parseDouble(ConfigurationProperties.GetConfigValue("PERCENTAGE_PERSONAL_RETIREMENT_CONTRIBUTION"));
		double percentage_employers_contributions_retirement = Double.parseDouble(ConfigurationProperties.GetConfigValue("PERCENTAGE_EMPLOYERS_CONTRIBUTIONS_RETIREMENT"));
		double percentage_employers_FONASA_contribution = Double.parseDouble(ConfigurationProperties.GetConfigValue("PERCENTAGE_EMPLOYERS_FONASA_CONTRIBUTION"));
		double percentage_FRL_contribution = Double.parseDouble(ConfigurationProperties.GetConfigValue("PERCENTAGE_FRL_CONTRIBUTION"));
		double percentage_tickets_employers = Double.parseDouble(ConfigurationProperties.GetConfigValue("PERCENTAGE_TICKETS_EMPLOYERS"));
		double var1_prev = Double.parseDouble(ConfigurationProperties.GetConfigValue("VAR_1_PREV"));
		double var2_prev = Double.parseDouble(ConfigurationProperties.GetConfigValue("VAR_2_PREV"));
		double var3_prev = Double.parseDouble(ConfigurationProperties.GetConfigValue("VAR_3_PREV"));
		double percentage_incidence_salary = Double.parseDouble(ConfigurationProperties.GetConfigValue("PERCENTAGE_INCIDENCE_SALARY"));
		
		salarySummary.setNominalSalary(voSalarySummary.getNominalSalary());
		salarySummary.setTickets(voSalarySummary.getTickets());
		
		salarySummary.setiRPF(voSalarySummary.getiRPF());
		salarySummary.setbSE(voSalarySummary.getbSE());
		salarySummary.setrET(voSalarySummary.getrET());
		salarySummary.setHours(voSalarySummary.getHours());
		salarySummary.setCostSaleHour(voSalarySummary.getCostSaleHour());
	
		//aporte jubilatorio personal
		double personalRetirementContribution = (voSalarySummary.getNominalSalary() * percentage_persoanl_retirement_contribution);
		salarySummary.setPersonalRetirementContribution(personalRetirementContribution);
		
		//aporte jubilatorio patronal
		double employersContributionsRetirement = (voSalarySummary.getNominalSalary() * percentage_employers_contributions_retirement);
		salarySummary.setEmployersContributionsRetirement(employersContributionsRetirement);
		
		//aporte FONASA personal
		double personalFONASAContribution = voSalarySummary.getNominalSalary() * voSalarySummary.getPercentageTypeFONASA();
		salarySummary.setPersonalFONASAContribution(personalFONASAContribution);
		
		//aporte FONASA patronal
		double employersFONASAContribution = voSalarySummary.getNominalSalary() * percentage_employers_FONASA_contribution;
		salarySummary.setEmployersFONASAContribution(employersFONASAContribution);
		
		//aporte FRL personal
		double personalFRLContribution = voSalarySummary.getNominalSalary() * percentage_FRL_contribution;
		salarySummary.setPersonalFRLContribution(personalFRLContribution);
		
		double employersFRLContribution = voSalarySummary.getNominalSalary() * percentage_FRL_contribution;
		salarySummary.setEmployersFRLContribution(employersFRLContribution);
		
		//tickets patronal
		double ticketsEmployers = voSalarySummary.getTickets() * percentage_tickets_employers;
		salarySummary.setTicketsEmployers(ticketsEmployers);
		
		//total de descuentos
		//aporte jubilatorio personal + FONASA personal + FRL personal + IRPF
		double totalDiscounts = salarySummary.getPersonalRetirementContribution() + salarySummary.getPersonalFONASAContribution() + salarySummary.getPersonalFRLContribution() + salarySummary.getiRPF();
		salarySummary.setTotalDiscounts(totalDiscounts);
		
		//total aportes patronales
		//aporte jubilatorio patronal + FONASA patronal + FRL patronal + total tickets patronal + BSE
		double totalEmployerContributions = salarySummary.getEmployersContributionsRetirement() + salarySummary.getEmployersFONASAContribution() + salarySummary.getEmployersFRLContribution() + salarySummary.getTicketsEmployers() + salarySummary.getbSE();
		salarySummary.setTotalEmployerContributions(totalEmployerContributions);
		
		//nominal sin aportes
		double nominalWithoutContributions = salarySummary.getNominalSalary() - salarySummary.getTotalDiscounts();
		salarySummary.setNominalWithoutContributions(nominalWithoutContributions);
		
		//prevencion de despido
		//=(nominal/12)+((nominal/12)*var3)+(nominal/30*var1*var2)
		double nominal = salarySummary.getNominalSalary();
		double dismissalPrevention = (nominal/12)+((nominal/12)*var3_prev)+(nominal/30*var1_prev*var2_prev);
		salarySummary.setDismissalPrevention(dismissalPrevention);
		
		//incidencia sueldo
		double incidenceSalary = salarySummary.getNominalSalary() * percentage_incidence_salary;
		salarySummary.setIncidenceSalary(incidenceSalary);
		
		//incidencia tickets
		//=(nominal/12)+((nominal/12)*var3)
		double tickets = salarySummary.getTickets();
		double incidenceTickets = (tickets/12)+((tickets/12)*var3_prev);
		salarySummary.setIncidenceTickets(incidenceTickets);
		
		//salario a pagar(sueldo liquido del empleado)
		//nominal + tickets - total descuentos - ret
		double salaryToPay = salarySummary.getNominalSalary() + salarySummary.getTickets() - salarySummary.getTotalDiscounts() - salarySummary.getrET();
		salarySummary.setSalaryToPay(salaryToPay);
		
		//costo mensual
		//nominal + tickets + total aportes patronales + incidencia de sueldo + incidencia de tickes + prevencion de despido
		double _nominal = salarySummary.getNominalSalary();
		double _tickets = salarySummary.getTickets();
		double _totalEmployersContributions = salarySummary.getTotalEmployerContributions();
		double _incidenceSalary = salarySummary.getIncidenceSalary();
		double _incidenceTickets = salarySummary.getIncidenceTickets();
		double _dismissalPrevention = salarySummary.getDismissalPrevention();;
		double costMonth = _nominal + _tickets + _totalEmployersContributions + _incidenceSalary + _incidenceTickets + _dismissalPrevention;
		salarySummary.setCostMonth(costMonth);
		
		//costo hora
		//costo mensual / cantidad de horas
		double costRealHour = salarySummary.getCostMonth() / salarySummary.getHours();
		salarySummary.setCostRealHour(costRealHour);
		
		return salarySummary;
	}
	
	VOEmployed BuildVOEmployed(Employed employed, SalarySummary salarySummary)
	{
		VOEmployed voEmployed = new VOEmployed();
		voEmployed.setId(employed.getId());
		voEmployed.setName(employed.getName());
		voEmployed.setLastName(employed.getLastName());
		voEmployed.setEmail(employed.getEmail());
		voEmployed.setAddress(employed.getAddress());
		voEmployed.setCellPhone(employed.getCellPhone());
		voEmployed.setCreatedDateTimeUTC(employed.getCreatedDateTimeUTC());
		voEmployed.setUpdatedDateTimeUTC(employed.getUpdatedDateTimeUTC());
		voEmployed.setEmployedType(employed.getEmployedType().getValue());
		if(employed.getUser() != null)
		{
			VOUser voUser = new VOUser();
			voUser.setId(employed.getUser().getId());
			voEmployed.setUser(voUser);
		}
		
		if(salarySummary != null)
			voEmployed.setvOSalarySummary(BuildVOSalarySummary(salarySummary));
		
		return voEmployed;
	}
	
	VOSalarySummary BuildVOSalarySummary(SalarySummary salarySummary)
	{
		VOSalarySummary voSalarySummary = new VOSalarySummary();
		voSalarySummary.setId(salarySummary.getId());
		voSalarySummary.setVersion(salarySummary.getVersion());
		voSalarySummary.setNominalSalary(salarySummary.getNominalSalary());
		voSalarySummary.setTickets(salarySummary.getTickets());
		voSalarySummary.setPersonalRetirementContribution(salarySummary.getPersonalRetirementContribution());
		voSalarySummary.setEmployersContributionsRetirement(salarySummary.getEmployersContributionsRetirement());
		voSalarySummary.setPersonalFONASAContribution(salarySummary.getPersonalFONASAContribution());
		voSalarySummary.setEmployersFONASAContribution(salarySummary.getEmployersFONASAContribution());
		voSalarySummary.setPersonalFRLContribution(salarySummary.getPersonalFRLContribution());
		voSalarySummary.setEmployersFRLContribution(salarySummary.getEmployersFRLContribution());
		voSalarySummary.setiRPF(salarySummary.getiRPF());
		voSalarySummary.setTicketsEmployers(salarySummary.getTicketsEmployers());
		voSalarySummary.setbSE(salarySummary.getbSE());
		voSalarySummary.setTotalDiscounts(salarySummary.getTotalDiscounts());
		voSalarySummary.setTotalEmployerContributions(salarySummary.getTotalEmployerContributions());
		voSalarySummary.setNominalWithoutContributions(salarySummary.getNominalWithoutContributions());
		voSalarySummary.setDismissalPrevention(salarySummary.getDismissalPrevention());
		voSalarySummary.setIncidenceSalary(salarySummary.getIncidenceSalary());
		voSalarySummary.setIncidenceTickets(salarySummary.getIncidenceTickets());
		voSalarySummary.setrET(salarySummary.getrET());
		voSalarySummary.setSalaryToPay(salarySummary.getSalaryToPay());
		voSalarySummary.setCostMonth(salarySummary.getCostMonth());
		voSalarySummary.setCostRealHour(salarySummary.getCostRealHour());
		voSalarySummary.setCostSaleHour(salarySummary.getCostSaleHour());
		voSalarySummary.setHours(salarySummary.getHours());
		voSalarySummary.setPercentageTypeFONASA(voSalarySummary.getPersonalFONASAContribution()/voSalarySummary.getNominalSalary());
		
		return voSalarySummary;
	}
	
	boolean updatedSalarySummaries(SalarySummary currentSalarySummary, SalarySummary newSalarySummary){
		boolean updated = false;
		
		if(currentSalarySummary.getNominalSalary() != newSalarySummary.getNominalSalary()){
			updated = true;
		}
		if(currentSalarySummary.getTickets() != newSalarySummary.getTickets()){
			updated = true;
		}
		if(currentSalarySummary.getPersonalFONASAContribution()/currentSalarySummary.getNominalSalary() != newSalarySummary.getPersonalFONASAContribution()/newSalarySummary.getNominalSalary()){
			updated = true;
		}
		if(currentSalarySummary.getHours() != newSalarySummary.getHours()){
			updated = true;
		}
		if(currentSalarySummary.getiRPF() != newSalarySummary.getiRPF()){
			updated = true;
		}
		if(currentSalarySummary.getbSE() != newSalarySummary.getbSE()){
			updated = true;
		}
		if(currentSalarySummary.getrET() != newSalarySummary.getrET()){
			updated = true;
		}
		if(currentSalarySummary.getCostSaleHour() != newSalarySummary.getCostSaleHour()){
			updated = true;
		}
				
		return updated;
	}

	@Override
	public ArrayList<VOSalarySummaryVersion> getAllSalarySummaryVersion(int employedId) throws ServerException, ClientException {	
		ArrayList<VOSalarySummaryVersion> list = new ArrayList<VOSalarySummaryVersion>();
		
		Employed employed = iDAOEmployees.getObject(employedId);
		if(employed != null){			
			ArrayList<SalarySummaryVersion> aux = employed.getAllSalarySummaryVersion();
			
			for (SalarySummaryVersion salarySummaryVersion : aux) {
				VOSalarySummaryVersion voSSV = new VOSalarySummaryVersion();
				voSSV.setCreatedDateTimeUTC(salarySummaryVersion.getCreatedDateTimeUTC());
				voSSV.setVersion(salarySummaryVersion.getVersion());
				list.add(voSSV);
			}
					
		}
		else
			throw new ClientException("No existe un empleado con ese id");
		
		return list;		
	}
}
