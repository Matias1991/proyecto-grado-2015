package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import com.mysql.jdbc.Statement;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.Employed;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOProjects;

public class DAOProjects implements IDAOProjects {

	private Connection connection;

	public DAOProjects(Connection connection) {
		this.connection = connection;
	}

	@Override
	public int insert(Project obj) throws ServerException {
		int newProjectId = -1;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO PROJECT (NAME, DESCRIPTION, MANAGERID, SELLERID, CREATEDDATETIMEUTC, UPDATEDDATETIMEUTC, ENABLED) VALUES"
				+ "(?,?,?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, obj.getName());
			preparedStatement.setString(2, obj.getDescription());
			preparedStatement.setInt(3, obj.getManager().getId());
			preparedStatement.setInt(4, obj.getSeller().getId());
			preparedStatement.setTimestamp(5, new Timestamp(obj
					.getCreatedDateTimeUTC().getTime()));
			preparedStatement.setTimestamp(6, new Timestamp(obj
					.getUpdatedDateTimeUTC().getTime()));
			preparedStatement.setBoolean(7, obj.getEnabled());

			preparedStatement.executeUpdate();

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					newProjectId = generatedKeys.getInt(1);
				}
			}

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

		return newProjectId;
	}

	@Override
	public void delete(int id) throws ServerException {
		PreparedStatement preparedStatement = null;

		try {
			String deleteSQL = "UPDATE PROJECT SET ENABLED = 0 WHERE ID = ?";
			preparedStatement = this.connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, id);
			preparedStatement.execute();

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
	}

	@Override
	public void update(int id, Project obj) throws ServerException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean exist(int id) throws ServerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Project getObject(int id) throws ServerException {
		Project project = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM PROJECT WHERE ID = ?";

			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				project = BuildProject(rs);
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

		return project;
	}

	@Override
	public ArrayList<Project> getObjects() throws ServerException {
		ArrayList<Project> projects = new ArrayList<Project>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String sql;
			sql = "SELECT * FROM project";
			preparedStatement = this.connection.prepareStatement(sql);
			rs = preparedStatement.executeQuery(sql);

			while (rs.next()) {
				projects.add(BuildProject(rs));
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

		return projects;
	}

	@Override
	public Project getProjectUByUserName(String name) throws ServerException {
		Project project = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			String getSQL = "SELECT * FROM Project WHERE name = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setString(1, name);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				project = BuildProject(rs);
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

		return project;
	}

	@Override
	public ArrayList<Project> getProjectsByStatus(boolean projectStatus)
			throws ServerException {
		ArrayList<Project> project = new ArrayList<Project>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM PROJECT WHERE ENABLED = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setBoolean(1, projectStatus);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				project.add(BuildProject(rs));
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

		return project;
	}

	Project BuildProject(ResultSet rs) throws SQLException, ServerException {
		int _id = rs.getInt("id");
		String name = rs.getString("name");
		String description = rs.getString("description");
		int managerId = rs.getInt("managerId");
		int sellerId = rs.getInt("sellerId");
		Date createdDateTimeUTC = rs.getTimestamp("createdDateTimeUTC");
		Date updatedDateTimeUTC = rs.getTimestamp("updatedDateTimeUTC");
		boolean enabled = rs.getBoolean("enabled");

		Project project = new Project();
		project.setId(_id);
		project.setName(name);
		project.setCreatedDateTimeUTC(createdDateTimeUTC);
		project.setUpdatedDateTimeUTC(updatedDateTimeUTC);
		project.setEnabled(enabled);
		project.setDescription(description);

		if (sellerId != 0) {
			Employed manager = new Employed();
			manager.setId(sellerId);
			project.setSeller(manager);
		}
		if (managerId != 0) {
			Employed seller = new Employed();
			seller.setId(managerId);
			project.setSeller(seller);
		}

		return project;
	}

}
