package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.ProjectEmployed;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.businessEntity.UserStatus;
import servicelayer.entity.businessEntity.UserType;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOProjectEmployees;

public class DAOProjectEmployees implements IDAOProjectEmployees {

	private Connection connection;

	public DAOProjectEmployees() {
	}

	public DAOProjectEmployees(Connection connection) {
		this.connection = connection;
	}

	@Override
	public int insertEmployedProject(int projectId,
			ProjectEmployed employedProyect) throws ServerException {
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
	public ArrayList<ProjectEmployed> getEmployeesProject(int projectId)
			throws ServerException {
		ArrayList<ProjectEmployed> projectEmployees = new ArrayList<ProjectEmployed>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM EMPLOYED_PROJECT ");
			sql.append("WHERE PROJECTID = ?");

			preparedStatement = this.connection
					.prepareStatement(sql.toString());
			preparedStatement.setInt(1, projectId);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ProjectEmployed projectEmployed = BuildProjectEmployees(rs);
				projectEmployees.add(projectEmployed);
			}

		} catch (SQLException e) {
			throw new ServerException(e);
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				LoggerMSMP.setLog(e.getMessage());
			}
		}

		return projectEmployees;
	}

	ProjectEmployed BuildProjectEmployees(ResultSet rs) throws SQLException {
		int projectId = rs.getInt("projectId");
		int employedId = rs.getInt("employedId");
		int version = rs.getInt("version");
		int hours = rs.getInt("hours");
		boolean enabled = rs.getBoolean("enabled");
		Date createdDateTimeUTC = rs.getTimestamp("createdDateTimeUTC");
		Date updatedDateTimeUTC = rs.getTimestamp("updatedDateTimeUTC");
		
		ProjectEmployed projectEmployed = new ProjectEmployed();
		projectEmployed.setCreatedDateTimeUTC(createdDateTimeUTC);
		projectEmployed.setEmployed(new Employed(employedId));
		
		return projectEmployed;
		
	}
}
