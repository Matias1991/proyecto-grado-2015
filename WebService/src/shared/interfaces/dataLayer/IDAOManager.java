package shared.interfaces.dataLayer;

import shared.exceptions.ServerException;

public interface IDAOManager {

	void commit() throws ServerException;

	void rollback() throws ServerException;

	void close() throws ServerException;

	IDAOUsers getDAOUsers();
}
