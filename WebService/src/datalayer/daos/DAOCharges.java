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

		String insertSQL = "INSERT INTO CHARGE (NUMBER, DESCRIPTION, AMOUNTPESO, AMOUNTDOLLAR, ISCURRENCYDOLLAR, TYPEEXCHANGE, CREATEDDATETIMEUTC, BILLID) VALUES"
				+ "(?,?,?,?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, obj.getNumber());
			preparedStatement.setString(2, obj.getDescription());
			preparedStatement.setDouble(3, obj.getAmountPeso());
			preparedStatement.setDouble(4, obj.getAmountDollar());
			preparedStatement.setBoolean(5, obj.getIsCurrencyDollar());
			preparedStatement.setDouble(6, obj.getTypeExchange());
			preparedStatement.setTimestamp(7, new Timestamp(obj
					.getCreatedDateTimeUTC().getTime()));
			if (obj.getBill() != null)
				preparedStatement.setInt(8, obj.getBill().getId());
			else
				preparedStatement.setNull(8, java.sql.Types.INTEGER);

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

		String updateSQL = "UPDATE CHARGE " 
				+ "SET DESCRIPTION = ?, "
				+ "AMOUNTPESO = ?, " 
				+ "AMOUNTDOLLAR = ?, "
				+ "TYPEEXCHANGE = ? "
				+ "WHERE id = ?";

		try {
			preparedStatement = this.connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, obj.getDescription());
			preparedStatement.setDouble(2, obj.getAmountPeso());
			preparedStatement.setDouble(3, obj.getAmountDollar());
			preparedStatement.setDouble(4, obj.getTypeExchange());
			preparedStatement.setInt(5, id);

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
			sql.append("SELECT C.*, B.Description as BillDescription FROM CHARGE C ");
			sql.append("INNER JOIN BILL B ON B.Id = C.BillId ");
			preparedStatement = this.connection
					.prepareStatement(sql.toString());
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
	public ArrayList<Charge> getCharges(boolean isBillLiquidated, boolean isProjectClosed) throws ServerException {
		ArrayList<Charge> charges = new ArrayList<Charge>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT C.*, B.Code as BillCode, B.Description as BillDescription FROM CHARGE C ");
			sql.append("INNER JOIN BILL B ON B.Id = C.BillId ");
			sql.append("INNER JOIN Project P ON P.Id = B.ProjectId ");
			sql.append("WHERE B.IsLiquidated = ? AND P.Closed = ?");
			preparedStatement = this.connection
					.prepareStatement(sql.toString());
			preparedStatement.setBoolean(1, isBillLiquidated);
			preparedStatement.setBoolean(2, isProjectClosed);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Charge charge = BuildCharge(rs);
				if (charge.getBill() != null)
				{
					charge.getBill().setCode(rs.getString("billCode"));
					charge.getBill().setDescription(rs.getString("billDescription"));
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
	public ArrayList<Charge> getChargesByBill(int billId) throws ServerException {
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

	private Charge BuildCharge(ResultSet rs) throws SQLException {
		int _id = rs.getInt("id");
		String number = rs.getString("number");
		String description = rs.getString("description");
		double amountPeso = rs.getDouble("amountPeso");
		double amountDollar = rs.getDouble("amountDollar");
		boolean isCurrencyDollar = rs.getBoolean("isCurrencyDollar");
		double typeExchange = rs.getDouble("typeExchange");
		Date createdDateTimeUTC = rs.getTimestamp("createdDateTimeUTC");
		int billId = rs.getInt("billId");

		Charge charge = new Charge();
		charge.setId(_id);
		charge.setNumber(number);
		charge.setDescription(description);
		charge.setIsCurrencyDollar(isCurrencyDollar);
		charge.setTypeExchange(typeExchange);
		charge.setCreatedDateTimeUTC(createdDateTimeUTC);
		if (billId != 0)
			charge.setBill(new Bill(billId));

		if (charge.getIsCurrencyDollar()) {
			charge.setAmountDollar(amountDollar);
			charge.setTypeExchange(typeExchange);
		} else
			charge.setAmountPeso(amountPeso);

		return charge;
	}
}
