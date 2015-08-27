package datalayer.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOEmployedProject;
import shared.interfaces.dataLayer.IDAOEmployees;
import shared.interfaces.dataLayer.IDAOManager;
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
	private IDAOEmployedProject iDAOEmployedProjects;
	
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
	
	public IDAOEmployedProject getDAOEmployedProjects()
	{
		if(iDAOEmployedProjects == null)
		{
			return new DAOEmployedProject(connection);
		}
		
		return iDAOEmployedProjects;
	}
	
}
