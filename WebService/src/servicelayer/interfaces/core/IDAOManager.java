package servicelayer.interfaces.core;

import java.sql.SQLException;

import servicelayer.interfaces.dataLayer.IDAOUsers;

public interface IDAOManager {

	public void commit() throws SQLException;
	public void rollback() throws SQLException;
	public void close() throws SQLException;
	
	public IDAOUsers getDAOUsers();
}
