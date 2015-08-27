package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import datalayer.utilities.ManageConnection;
import servicelayer.entity.businessEntity.Charge;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOCharges;

public class DAOCharges implements IDAOCharges {

	private Connection connection;

	public DAOCharges() throws ServerException {
		try {
			this.connection = new ManageConnection().GetConnection();
		} catch (Exception e) {
			throw new ServerException(e);
		}
	}

	public DAOCharges(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public int insert(Charge obj) throws ServerException {
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO CHARGE (DESCRIPTION, AMOUNTPESO, AMOUNTDOLLAR, ISCURRENCYDOLLAR, TYPEEXCHANGE, APPLIEDDATETIMEUTC, BILLID) VALUES"
				+ "(?,?,?,?,?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, obj.getDescription());
			preparedStatement.setDouble(2, obj.getAmountPeso());
			preparedStatement.setDouble(3, obj.getAmountDollar());
			preparedStatement.setBoolean(4, obj.getIsCurrencyDollar());
			preparedStatement.setDouble(5, obj.getTypeExchange());
			preparedStatement.setTimestamp(6, new Timestamp(obj.getCreatedDateTimeUTC().getTime()));
			if(obj.getBill() != null)
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int id, Charge obj) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exist(int id) throws ServerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Charge getObject(int id) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Charge> getObjects() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

}
