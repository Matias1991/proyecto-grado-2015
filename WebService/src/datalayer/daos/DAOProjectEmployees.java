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
		int newEmployedProjectId = -1;
		PreparedStatement preparedStatement = null;
		String insertSQL = "INSERT INTO EMPLOYED_PROJECT (PROJECTID, EMPLOYEDID, VERSION, HOURS, ENABLED, CREATEDDATETIMEUTC, UPDATEDDATETIMEUTC) VALUES"
				+ "(?,?,?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL);

			preparedStatement.setInt(1, projectId);
			preparedStatement.setInt(2, employedProyect.getEmployed().getId());
			preparedStatement.setInt(3, employedProyect.getVersion() + 1);
			preparedStatement.setInt(4, employedProyect.getHours());
			preparedStatement.setBoolean(5, employedProyect.isEnabled());
			preparedStatement.setTimestamp(6, new Timestamp(new Date().getTime()));
			preparedStatement.setTimestamp(7, new Timestamp(new Date().getTime()));

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
			sql.append("SELECT * FROM EMPLOYED_PROJECT E1 ");
			sql.append("WHERE E1.PROJECTID = ? AND E1.ENABLED = 1 ");
			sql.append("AND E1.VERSION = (SELECT MAX(E2.VERSION) FROM EMPLOYED_PROJECT E2 WHERE E2.PROJECTID = E1.PROJECTID AND E2.EMPLOYEDID = E1.EMPLOYEDID)");
			preparedStatement = this.connection
					.prepareStatement(sql.toString());
			preparedStatement.setInt(1, projectId);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ProjectEmployed projectEmployed = buildProjectEmployees(rs);
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
	
	@Override
	public ProjectEmployed getEmployeed (int projectId, int employeedId)
			throws ServerException {
		ProjectEmployed projectEmployed = new ProjectEmployed();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM EMPLOYED_PROJECT ");
			sql.append("WHERE PROJECTID = ? AND EMPLOYEDID = ? ");
			sql.append("AND VERSION = (SELECT MAX(VERSION) FROM EMPLOYED_PROJECT ");
			sql.append("WHERE PROJECTID = ? AND EMPLOYEDID = ?)");

			preparedStatement = this.connection.prepareStatement(sql.toString());
			preparedStatement.setInt(1, projectId);
			preparedStatement.setInt(2, employeedId);
			preparedStatement.setInt(3, projectId);
			preparedStatement.setInt(4, employeedId);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				projectEmployed = buildProjectEmployees(rs);
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

		return projectEmployed;
	}
	
	@Override
	public void update(int id, ProjectEmployed obj) throws ServerException {
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE EMPLOYED_PROJECT SET HOURS = ?, "+
						"ENABLED = ?, UPDATEDDATETIMEUTC = ? " +
						"WHERE PROJECTID = ? AND EMPLOYEDID = ? " +
						"AND VERSION = ?;";

		try {
			preparedStatement = this.connection.prepareStatement(updateSQL);


			preparedStatement.setInt(1, obj.getHours());
			preparedStatement.setBoolean(2, obj.isEnabled());
			preparedStatement.setTimestamp(3, new Timestamp(new Date().getTime()));
			preparedStatement.setInt(4, id);
			preparedStatement.setInt(5, obj.getEmployed().getId());
			preparedStatement.setInt(6, obj.getVersion());
			
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new ServerException(e);
		}
	}
	
	ProjectEmployed buildProjectEmployees(ResultSet rs) throws SQLException {
		int employedId = rs.getInt("employedId");
		int version = rs.getInt("version");
		int hours = rs.getInt("hours");
		boolean enabled = rs.getBoolean("enabled");
		Date createdDateTimeUTC = rs.getTimestamp("createdDateTimeUTC");
		Date updatedDateTimeUTC = rs.getTimestamp("updatedDateTimeUTC");
		
		ProjectEmployed projectEmployed = new ProjectEmployed();
		projectEmployed.setCreatedDateTimeUTC(createdDateTimeUTC);
		projectEmployed.setEmployed(new Employed(employedId));
		projectEmployed.setEnabled(enabled);
		projectEmployed.setHours(hours);
		projectEmployed.setUpdatedDateTimeUTC(updatedDateTimeUTC);
		projectEmployed.setVersion(version);
		
		return projectEmployed;
		
	}
}
