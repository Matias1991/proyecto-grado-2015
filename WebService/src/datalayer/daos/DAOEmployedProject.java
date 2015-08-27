package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import servicelayer.entity.businessEntity.EmployedProject;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOEmployedProject;

public class DAOEmployedProject implements IDAOEmployedProject {

	private Connection connection;

	public DAOEmployedProject() {
	}

	public DAOEmployedProject(Connection connection) {
		this.connection = connection;
	}

	@Override
	public int insertEmployedProject(int projectId,
			EmployedProject employedProyect) throws ServerException {
		// TODO Auto-generated method stub
		int newEmployedProjectId = -1;
		PreparedStatement preparedStatement = null;
		String insertSQL = "INSERT INTO EMPLOYED_PROJECT (PROJECTID, EMPLOYEDID, VERSION, HOURS, ENABLED, CREATEDDATETIMEUTC, UPDATEDDATETIMEUTC) VALUES"
				+ "(?,?,?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL);

			preparedStatement.setInt(1, projectId);
			preparedStatement.setInt(2, employedProyect.getEmployed().getId());
			preparedStatement.setInt(3, 1);
			preparedStatement.setInt(4, employedProyect.getHours());
			preparedStatement.setBoolean(5, employedProyect.isEnabled());
			preparedStatement.setTimestamp(6, new Timestamp(employedProyect
					.getCreatedDateTimeUTC().getTime()));
			preparedStatement.setTimestamp(7, new Timestamp(employedProyect
					.getUpdatedDateTimeUTC().getTime()));

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new ServerException(e);
		} finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					LoggerMSMP.setLog(e.getMessage());
				}
			}
		}

		return newEmployedProjectId;

	}

	@Override
	public ArrayList<EmployedProject> getEmployeesProject(int projectId)
			throws ServerException {
		// TODO Auto-generated method stub

		// select weher el projectID
		// en el core llamo al otro core y obtengo el obj el id
		return null;
	}

}
