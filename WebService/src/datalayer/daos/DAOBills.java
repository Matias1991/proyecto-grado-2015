package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import datalayer.utilities.ManageConnection;
import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.Project;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOBills;

public class DAOBills implements IDAOBills{

	private Connection connection;

	public DAOBills() throws ServerException {
		try {
			this.connection = new ManageConnection().GetConnection();
		} catch (Exception e) {
			throw new ServerException(e);
		}
	}

	public DAOBills(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public int insert(Bill obj) throws ServerException {
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO BILL (DESCRIPTION, AMOUNT, CREATEDDATETIMEUTC, PROJECTID) VALUES"
				+ "(?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, obj.getDescription());
			preparedStatement.setDouble(2, obj.getAmount());
			preparedStatement.setTimestamp(3, new Timestamp(obj
					.getCreatedDateTimeUTC().getTime()));
			if(obj.getProject() != null)
				preparedStatement.setInt(4, obj.getProject().getId());
			else
				preparedStatement.setNull(4, java.sql.Types.INTEGER);
			
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

		return 0;
	}

	@Override
	public void delete(int id) throws ServerException {
		PreparedStatement preparedStatement = null;

		try {
			String deleteSQL = "DELETE FROM BILL WHERE ID = ?";
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
	public void update(int id, Bill obj) throws ServerException {
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE CATEGORY "
				+ "SET DESCRIPTION = ?, "
				+ "AMOUNT = ?, "
				+ "CREATEDDATETIMEUTC = ?, "
				+ "PROJECTID = ? "
				+ "WHERE id = ?";

		try {
			preparedStatement = this.connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, obj.getDescription());
			preparedStatement.setDouble(2, obj.getAmount());
			preparedStatement.setTimestamp(3, new Timestamp(obj.getCreatedDateTimeUTC().getTime()));
			if(obj.getProject() != null)
				preparedStatement.setInt(4, obj.getProject().getId());
			else
				preparedStatement.setNull(4, java.sql.Types.INTEGER);
			preparedStatement.setInt(5 , id);
			
			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new ServerException(e);
		}
	}

	@Override
	public boolean exist(int id) throws ServerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Bill getObject(int id) throws ServerException {
		Bill bill = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM BILL WHERE id = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bill = BuildBill(rs);
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

		return bill;
	}

	@Override
	public ArrayList<Bill> getObjects() throws ServerException {
		ArrayList<Bill> bills = new ArrayList<Bill>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT B.*, P.Name as ProjectName ");
			sql.append("FROM BILL B ");
			sql.append("LEFT OUTER JOIN PROJECT P ON P.Id = B.ProjectId");
			preparedStatement = this.connection.prepareStatement(sql.toString());
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bills.add(BuildBill(rs));
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

		return bills;
	}

	@Override
	public Bill getBill(String description, int projectId) throws ServerException {
		Bill bill = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM BILL WHERE description = ? and projectId = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setString(1, description);
			preparedStatement.setInt(2, projectId);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bill = BuildBill(rs);
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

		return bill;
	}
	
	private Bill BuildBill(ResultSet rs) throws SQLException {
		int _id = rs.getInt("id");
		String description = rs.getString("description");
		double amount = rs.getDouble("amount");
		Date createDateTimeUTC = rs.getTimestamp("createdDateTimeUTC");
		int projectId = rs.getInt("projectid");

		Bill bill = new Bill();
		bill.setId(_id);
		bill.setDescription(description);
		bill.setAmount(amount);
		bill.setCreatedDateTimeUTC(createDateTimeUTC);
		if(projectId != 0)
			bill.setProject(new Project(projectId));
		
		return bill;
	}
}
