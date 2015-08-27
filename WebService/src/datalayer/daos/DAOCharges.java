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

		String insertSQL = "INSERT INTO CHARGE (DESCRIPTION, AMOUNTPESO, AMOUNTDOLLAR, ISCURRENCYDOLLAR, TYPEEXCHANGE, APPLIEDDATETIMEUTC, BILLID) VALUES"
				+ "(?,?,?,?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, obj.getDescription());
			preparedStatement.setDouble(2, obj.getAmountPeso());
			preparedStatement.setDouble(3, obj.getAmountDollar());
			preparedStatement.setBoolean(4, obj.getIsCurrencyDollar());
			preparedStatement.setDouble(5, obj.getTypeExchange());
			preparedStatement.setTimestamp(6, new Timestamp(obj
					.getCreatedDateTimeUTC().getTime()));
			if (obj.getBill() != null)
				preparedStatement.setInt(7, obj.getBill().getId());
			else
				preparedStatement.setNull(7, java.sql.Types.INTEGER);

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
	public void update(int id, Charge obj) throws ServerException {
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE CHARGE " + "SET DESCRIPTION = ?, "
				+ "AMOUNTPESO = ?, " + "AMOUNTDOLLAR = ?, "
				+ "ISCURRENCYDOLLAR = ?, " + "TYPEEXCHANGE = ?, "
				+ "BILLID = ? " + "WHERE id = ?";

		try {
			preparedStatement = this.connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, obj.getDescription());
			preparedStatement.setDouble(2, obj.getAmountPeso());
			preparedStatement.setDouble(3, obj.getAmountDollar());
			preparedStatement.setBoolean(4, obj.getIsCurrencyDollar());
			preparedStatement.setDouble(5, obj.getTypeExchange());
			preparedStatement.setTimestamp(7, new Timestamp(obj
					.getCreatedDateTimeUTC().getTime()));
			if (obj.getBill() != null)
				preparedStatement.setInt(8, obj.getBill().getId());
			else
				preparedStatement.setNull(8, java.sql.Types.INTEGER);
			preparedStatement.setInt(9, id);

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

	private Charge BuildCharge(ResultSet rs) throws SQLException {
		int _id = rs.getInt("id");
		String description = rs.getString("description");
		double amountPeso = rs.getDouble("amountPeso");
		double amountDollar = rs.getDouble("amountDollar");
		boolean isCurrencyDollar = rs.getBoolean("isCurrencyDollar");
		double typeExchange = rs.getDouble("typeExchange");
		Date appliedDateTimeUTC = rs.getTimestamp("appliedDateTimeUTC");
		int billId = rs.getInt("billId");

		Charge charge = new Charge();
		charge.setId(_id);
		charge.setDescription(description);
		charge.setIsCurrencyDollar(isCurrencyDollar);
		charge.setTypeExchange(typeExchange);
		charge.setCreatedDateTimeUTC(appliedDateTimeUTC);
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
