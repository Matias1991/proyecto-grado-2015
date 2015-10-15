package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.Charge;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.businessEntity.UserType;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOCharges;

public class DAOCharges implements IDAOCharges {

	private Connection connection;

	public DAOCharges() {
	}

	public DAOCharges(Connection connection) {
		this.connection = connection;
	}

	@Override
	public int insert(Charge obj) throws ServerException {
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO CHARGE (NUMBER, DESCRIPTION, AMOUNT, CREATEDDATETIMEUTC, BILLID) VALUES"
				+ "(?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, obj.getNumber());
			preparedStatement.setString(2, obj.getDescription());
			preparedStatement.setDouble(3, obj.getAmount());
			preparedStatement.setTimestamp(4, new Timestamp(obj
					.getCreatedDateTimeUTC().getTime()));
			if (obj.getBill() != null)
				preparedStatement.setInt(5, obj.getBill().getId());
			else
				preparedStatement.setNull(5, java.sql.Types.INTEGER);

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
			String deleteSQL = "DELETE FROM CHARGE WHERE ID = ?";
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
	public void deleteCharges(int[] ids) throws ServerException {
		PreparedStatement preparedStatement = null;

		try {
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("(");

			int indexParameter = 0;
			while (indexParameter < ids.length) {
				strBuilder.append("?");
				strBuilder.append(",");
				indexParameter++;
			}
			strBuilder.append(")");
			strBuilder.replace(strBuilder.length() - 2,
					strBuilder.length() - 1, "");

			String deleteSQL = "DELETE FROM CHARGE WHERE ID IN "
					+ strBuilder.toString();
			preparedStatement = this.connection.prepareStatement(deleteSQL);

			int index = 1;
			for (int id : ids) {
				preparedStatement.setInt(index, id);
				index++;
			}

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
	public void update(int id, Charge obj) throws ServerException {
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE CHARGE " + "SET DESCRIPTION = ?, "
				+ "AMOUNT = ? "
				+ "WHERE id = ?";

		try {
			preparedStatement = this.connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, obj.getDescription());
			preparedStatement.setDouble(2, obj.getAmount());
			preparedStatement.setInt(3, id);

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
	public Charge getObject(int id) throws ServerException {
		Charge charge = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM CHARGE WHERE id = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				charge = BuildCharge(rs);
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

		return charge;
	}

	@Override
	public ArrayList<Charge> getObjects() throws ServerException {
		ArrayList<Charge> charges = new ArrayList<Charge>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT C.*, B.Code as BillCode, B.Description as BillDescription FROM CHARGE C ");
			sql.append("INNER JOIN BILL B ON B.Id = C.BillId ");
			preparedStatement = this.connection
					.prepareStatement(sql.toString());
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Charge charge = BuildCharge(rs);
				if (charge.getBill() != null) {
					charge.getBill().setCode(rs.getString("billCode"));
					charge.getBill().setDescription(
							rs.getString("billDescription"));
				}
				charges.add(charge);
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

		return charges;
	}

	@Override
	public ArrayList<Charge> getCharges(boolean isBillLiquidated,
			boolean isProjectClosed, User userContext) throws ServerException {
		ArrayList<Charge> charges = new ArrayList<Charge>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT C.*, B.Code as BillCode, B.Description as BillDescription, B.IsCurrencyDollar as BillCurrencyDollar FROM CHARGE C ");
			sql.append("INNER JOIN BILL B ON B.Id = C.BillId ");
			sql.append("INNER JOIN Project P ON P.Id = B.ProjectId ");
			sql.append("WHERE B.IsLiquidated = ? AND P.Closed = ? ");
			if(userContext.getUserType() == UserType.MANAGER)
				sql.append("AND P.ManagerId = ?");
			
			preparedStatement = this.connection
					.prepareStatement(sql.toString());
			preparedStatement.setBoolean(1, isBillLiquidated);
			preparedStatement.setBoolean(2, isProjectClosed);
			
			if(userContext.getUserType() == UserType.MANAGER)
				preparedStatement.setInt(3, userContext.getId());
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Charge charge = BuildCharge(rs);
				if (charge.getBill() != null) {
					charge.getBill().setCode(rs.getString("billCode"));
					charge.getBill().setDescription(
							rs.getString("billDescription"));
					charge.getBill().setIsCurrencyDollar(rs.getBoolean("billCurrencyDollar"));
				}
				charges.add(charge);
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

		return charges;
	}

	@Override
	public Charge getCharge(String number) throws ServerException {
		Charge charge = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM CHARGE WHERE number = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setString(1, number);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				charge = BuildCharge(rs);
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

		return charge;
	}

	@Override
	public ArrayList<Charge> getChargesByBill(int billId)
			throws ServerException {
		ArrayList<Charge> charges = new ArrayList<Charge>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT C.*, B.Description as BillDescription FROM CHARGE C ");
			sql.append("INNER JOIN BILL B ON B.Id = C.BillId ");
			sql.append("WHERE B.Id = ?");
			preparedStatement = this.connection
					.prepareStatement(sql.toString());
			preparedStatement.setInt(1, billId);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Charge charge = BuildCharge(rs);
				if (charge.getBill() != null)
					charge.getBill().setDescription(
							rs.getString("billDescription"));
				charges.add(charge);
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

		return charges;
	}
	
	@Override
	public ArrayList<Charge> getCharges(User userContext)
			throws ServerException {
		ArrayList<Charge> charges = new ArrayList<Charge>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT C.*, B.Code as BillCode, B.Description as BillDescription, B.IsCurrencyDollar as BillCurrencyDollar FROM CHARGE C ");
			sql.append("INNER JOIN BILL B ON B.Id = C.BillId ");
			if(userContext.getUserType() == UserType.MANAGER)
			{
				sql.append("INNER JOIN PROJECT P ON P.Id = B.ProjectId ");
				sql.append("WHERE P.ManagerId = ? ");
			}
		
			preparedStatement = this.connection
					.prepareStatement(sql.toString());

			if(userContext.getUserType() == UserType.MANAGER)
				preparedStatement.setInt(1, userContext.getId());
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Charge charge = BuildCharge(rs);
				if (charge.getBill() != null) {
					charge.getBill().setCode(rs.getString("billCode"));
					charge.getBill().setDescription(
							rs.getString("billDescription"));
					charge.getBill().setIsCurrencyDollar(rs.getBoolean("billCurrencyDollar"));
				}
				charges.add(charge);
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

		return charges;
	}

	private Charge BuildCharge(ResultSet rs) throws SQLException {
		int _id = rs.getInt("id");
		String number = rs.getString("number");
		String description = rs.getString("description");
		double amount = rs.getDouble("amount");
		Date createdDateTimeUTC = rs.getTimestamp("createdDateTimeUTC");
		int billId = rs.getInt("billId");

		Charge charge = new Charge();
		charge.setId(_id);
		charge.setNumber(number);
		charge.setDescription(description);
		charge.setAmount(amount);
		charge.setCreatedDateTimeUTC(createdDateTimeUTC);
		if (billId != 0)
			charge.setBill(new Bill(billId));

		return charge;
	}
}
