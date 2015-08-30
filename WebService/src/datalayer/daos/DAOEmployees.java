package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import com.mysql.jdbc.Statement;
import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.ProjectEmployed;
import servicelayer.entity.businessEntity.EmployedType;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOEmployees;

public class DAOEmployees implements IDAOEmployees {

	private Connection connection;

	public DAOEmployees() {
	}

	public DAOEmployees(Connection connection) {
		this.connection = connection;
	}

	@Override
	public int insert(Employed obj) throws ServerException {
		int newEmployedId = -1;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO EMPLOYED (NAME, LASTNAME, EMAIL, ADDRESS, CELLPHONE, CREATEDDATETIMEUTC, UPDATEDDATETIMEUTC, EMPLOYEDTYPEID, USERID) VALUES"
				+ "(?,?,?,?,?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setString(1, obj.getName());
			preparedStatement.setString(2, obj.getLastName());
			preparedStatement.setString(3, obj.getEmail());
			preparedStatement.setString(4, obj.getAddress());
			preparedStatement.setString(5, obj.getCellPhone());
			preparedStatement.setTimestamp(6, new Timestamp(obj
					.getCreatedDateTimeUTC().getTime()));
			preparedStatement.setTimestamp(7, new Timestamp(obj
					.getUpdatedDateTimeUTC().getTime()));
			preparedStatement.setInt(8, obj.getEmployedType().getValue());
			preparedStatement.setNull(9, java.sql.Types.INTEGER);

			preparedStatement.executeUpdate();

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					newEmployedId = generatedKeys.getInt(1);
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

		return newEmployedId;
	}

	public int getCountPartners() throws ServerException {

		// devuelve cuantos empleados de tipo Socio hay insertados en base
		String controlSQL = "SELECT COUNT(*) FROM EMPLOYED WHERE EmployedTypeId = 2;";
		ResultSet rs = null;
		PreparedStatement statementToControl = null;
		int count = 0;

		try {
			statementToControl = this.connection.prepareStatement(controlSQL);

			rs = statementToControl.executeQuery();
			rs.next();
			count = rs.getInt(1);
		} catch (SQLException e) {
			throw new ServerException(e);
		}
		return count;
	}

	@Override
	public void delete(int id) throws ServerException {
		PreparedStatement preparedStatement = null;

		try {

			String deleteSQL = "DELETE FROM EMPLOYED WHERE ID = ?";
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
	public boolean exist(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Employed getObject(int id) throws ServerException {
		Employed employed = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM EMPLOYED WHERE id = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				employed = BuildEmployed(rs);
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

		return employed;
	}

	@Override
	public ArrayList<Employed> getObjects() throws ServerException {
		ArrayList<Employed> employees = new ArrayList<Employed>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String sql;
			sql = "SELECT * FROM employed";
			preparedStatement = this.connection.prepareStatement(sql);
			rs = preparedStatement.executeQuery(sql);

			while (rs.next()) {
				employees.add(BuildEmployed(rs));
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

		return employees;
	}

	@Override
	public void update(int id, Employed obj) throws ServerException {
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE EMPLOYED " + "SET name = ?, "
				+ "lastName = ?, " + "email = ?, " + "address = ?, "
				+ "cellphone = ?, " + "updatedDateTimeUTC = ?, "
				+ "employedTypeId = ?, " + "userId = ? " + "WHERE id = ?";

		try {
			preparedStatement = this.connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, obj.getName());
			preparedStatement.setString(2, obj.getLastName());
			preparedStatement.setString(3, obj.getEmail());
			preparedStatement.setString(4, obj.getAddress());
			preparedStatement.setString(5, obj.getCellPhone());
			preparedStatement.setTimestamp(6, new Timestamp(obj
					.getUpdatedDateTimeUTC().getTime()));
			preparedStatement.setInt(7, obj.getEmployedType().getValue());
			preparedStatement.setNull(8, java.sql.Types.INTEGER);

			preparedStatement.setInt(9, id);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new ServerException(e);
		}
	}

	Employed BuildEmployed(ResultSet rs) throws SQLException, ServerException {
		int _id = rs.getInt("id");
		String name = rs.getString("name");
		String lastName = rs.getString("lastName");
		String email = rs.getString("email");
		String address = rs.getString("address");
		String cellphone = rs.getString("cellphone");
		Date createdDateTimeUTC = rs.getTimestamp("createdDateTimeUTC");
		Date updatedDateTimeUTC = rs.getTimestamp("updatedDateTimeUTC");
		int employedTypeId = rs.getInt("employedTypeId");

		Employed employed = new Employed();
		employed.setId(_id);
		employed.setName(name);
		employed.setLastName(lastName);
		employed.setEmail(email);
		employed.setAddress(address);
		employed.setCellPhone(cellphone);
		employed.setCreatedDateTimeUTC(createdDateTimeUTC);
		employed.setUpdatedDateTimeUTC(updatedDateTimeUTC);
		employed.setEmployedType(EmployedType.getEnum(employedTypeId));

		return employed;
	}

	public ArrayList<ProjectEmployed> getEmployedHours() throws ServerException {
		ArrayList<ProjectEmployed> result = new ArrayList<ProjectEmployed>();

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		String getSQL = "SELECT employed.Id, employed.Name, employed.LastName, salarysummary.Hours "
				+ "FROM employed "
				+ "INNER JOIN salarysummary "
				+ "ON employed.id=salarysummary.EmployedId "
				+ "WHERE (salarysummary.EmployedId, salarysummary.Version) in (select salarysummary.EmployedId, Max(version) from salarysummary s2 where salarysummary.EmployedId = s2.EmployedId)";
		try {
			preparedStatement = this.connection.prepareStatement(getSQL);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ProjectEmployed aux = new ProjectEmployed();
				aux.setEmployed(this.getObject(rs.getInt("id")));
				aux.setHours(rs.getInt("hours"));
				result.add(aux);
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

		return result;
	}
}
