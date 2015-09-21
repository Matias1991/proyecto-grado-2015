package datalayer.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOBills;
import shared.interfaces.dataLayer.IDAOCategroy;
import shared.interfaces.dataLayer.IDAOCharges;
import shared.interfaces.dataLayer.IDAOCompanyLiquidations;
import shared.interfaces.dataLayer.IDAOProjectsLiquidations;
import shared.interfaces.dataLayer.IDAOProjectEmployees;
import shared.interfaces.dataLayer.IDAOEmployees;
import shared.interfaces.dataLayer.IDAOManager;
import shared.interfaces.dataLayer.IDAOProjectPartners;
import shared.interfaces.dataLayer.IDAOProjects;
import shared.interfaces.dataLayer.IDAOSalarySummaries;
import shared.interfaces.dataLayer.IDAOUsers;
import datalayer.utilities.ManageConnection;

public class DAOManager implements IDAOManager{

	private Connection connection;
	private IDAOUsers iDAOUsers;
	private IDAOEmployees iDAOEmployees;
	private IDAOSalarySummaries iDAOSalarySummaries;
	private IDAOProjects iDAOProjects;
	private IDAOProjectEmployees iDAOEmployedProjects;
	private IDAOBills iDAOBills;
	private IDAOCategroy iDAOCategories;
	private IDAOCharges iDAOCharges;
	private IDAOProjectPartners iDAOPartnerProjects;
	private IDAOProjectsLiquidations iDAOProjectLiquidation;
	private IDAOCompanyLiquidations iDAOCompanyLiquidation;
		
	public DAOManager() throws ServerException
	{
		try {
			connection = new ManageConnection().GetConnection();
			connection.setAutoCommit(false);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			throw new ServerException(e);
		}
	}
	
	@Override
	public void commit() throws ServerException {
		try {
			this.connection.commit();
		} catch (SQLException e) {
			throw new ServerException(e);
		}
	}

	@Override
	public void rollback() throws  ServerException{
		
		try {
			this.connection.rollback();
		} catch (SQLException e) {
			throw new ServerException(e);
		}
	}
	
	@Override
	public void close() throws ServerException {
		try {
			this.connection.close();
		} catch (SQLException e) {
			throw new ServerException(e);
		}
	}
	
	public IDAOUsers getDAOUsers()
	{
		if(iDAOUsers == null)
		{
			return new DAOUsers(connection);
		}
		
		return iDAOUsers;
	}
	
	public IDAOEmployees getDAOEmployees()
	{
		if(iDAOUsers == null)
		{
			return new DAOEmployees(connection);
		}
		
		return iDAOEmployees;
	}
	
	public IDAOSalarySummaries getDAOSalarySummaries()
	{
		if(iDAOSalarySummaries == null)
		{
			return new DAOSalarySummaries(connection);
		}
		
		return iDAOSalarySummaries;
	}
	
	public IDAOProjects getDAOProjects()
	{
		if(iDAOProjects == null)
		{
			return new DAOProjects(connection);
		}
		
		return iDAOProjects;
	}
	
	public IDAOProjectEmployees getDAOEmployedProjects()
	{
		if(iDAOEmployedProjects == null)
		{
			return new DAOProjectEmployees(connection);
		}
		
		return iDAOEmployedProjects;
	}
	
	public IDAOBills getDAOBills()
	{
		if(iDAOBills == null)
		{
			return new DAOBills(connection);
		}
		
		return iDAOBills;
	}
	
	public IDAOCategroy getDAOCategories()
	{
		if(iDAOCategories == null)
		{
			return new DAOCategories(connection);
		}
		
		return iDAOCategories;
	}
	
	public IDAOCharges getDAOCharges()
	{
		if(iDAOCharges == null)
		{
			return new DAOCharges(connection);
		}
		
		return iDAOCharges;
	}
	
	public IDAOProjectPartners getDAOPartnerProjects()
	{
		if(iDAOPartnerProjects == null)
		{
			return new DAOProjectPartners(connection);
		}
		
		return iDAOPartnerProjects;
	}	
	
	public IDAOProjectsLiquidations getDAOProjectLiquidation(){
		if(iDAOProjectLiquidation == null){
			return new DAOProjectsLiquidations(connection);
		}
		
		return iDAOProjectLiquidation;
	}
	
	public IDAOCompanyLiquidations getDAOCompanyLiquidation(){
		if(iDAOCompanyLiquidation == null){
			return new DAOCompanyLiquidations(connection);
		}
		return iDAOCompanyLiquidation;		
	}
}
