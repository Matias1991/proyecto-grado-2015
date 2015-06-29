package servicelayer.interfaces.core;

import java.sql.SQLException;

import servicelayer.interfaces.dataLayer.IDAOUsers;

public interface IDAOManager {

	public void Commit() throws SQLException;
	public void Rollback() throws SQLException;
	public void Close() throws SQLException;
	
	public IDAOUsers GetDAOUsers();
}
