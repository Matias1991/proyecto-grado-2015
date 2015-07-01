package datalayer.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import servicelayer.interfaces.core.IDAOManager;
import servicelayer.interfaces.dataLayer.IDAOEmployees;
import servicelayer.interfaces.dataLayer.IDAOUsers;
import datalayer.utils.ManageConnection;

public class DAOManager implements IDAOManager{

	private Connection connection;
	private IDAOUsers iDAOUsers;
	private IDAOEmployees iDAOEmployees;
	
	public DAOManager()
	{
		try {
			connection = new ManageConnection().GetConnection();
			connection.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void commit() throws SQLException {
		// TODO Auto-generated method stub
		this.connection.commit();
	}

	@Override
	public void rollback() throws SQLException {
		// TODO Auto-generated method stub
		this.connection.rollback();
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

	@Override
	public void close() throws SQLException {
		// TODO Auto-generated method stub
		this.connection.close();
	}
}
