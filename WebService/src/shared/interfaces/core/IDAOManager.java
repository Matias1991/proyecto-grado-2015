package shared.interfaces.core;

import java.sql.SQLException;

import shared.interfaces.dataLayer.IDAOUsers;

public interface IDAOManager {

	void commit() throws SQLException;
	void rollback() throws SQLException;
	void close() throws SQLException;
	
	IDAOUsers getDAOUsers();
}
