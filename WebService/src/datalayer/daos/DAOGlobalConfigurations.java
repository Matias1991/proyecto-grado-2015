package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import servicelayer.entity.businessEntity.GlobalConfiguration;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOGlobalConfigurations;

public class DAOGlobalConfigurations implements IDAOGlobalConfigurations{

	private Connection connection;

	public DAOGlobalConfigurations() {
	}

	public DAOGlobalConfigurations(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public int insert(GlobalConfiguration obj) throws ServerException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void delete(int id) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int id, GlobalConfiguration obj) throws ServerException {
		PreparedStatement preparedStatement = null;

		try {
			
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("UPDATE GlobalConfiguration ");
			strBuilder.append("SET VALUE = ?, DESCRIPTION = ?");
			strBuilder.append("WHERE ID = ?");
			
			preparedStatement = this.connection.prepareStatement(strBuilder.toString());

			preparedStatement.setString(1, obj.getValue());
			preparedStatement.setString(2, obj.getDescription());
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
	public GlobalConfiguration getObject(int id) throws ServerException {
		GlobalConfiguration globalConfiguration = new GlobalConfiguration();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String sql;
			sql = "SELECT * FROM GlobalConfiguration WHERE ID = ?";
			preparedStatement = this.connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery(sql);

			if (rs.next()) {
				globalConfiguration = BuildGlobalConfiguration(rs);
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

		return globalConfiguration;
	}

	@Override
	public ArrayList<GlobalConfiguration> getObjects() throws ServerException {
		ArrayList<GlobalConfiguration> users = new ArrayList<GlobalConfiguration>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String sql;
			sql = "SELECT * FROM GlobalConfiguration";
			preparedStatement = this.connection.prepareStatement(sql);
			rs = preparedStatement.executeQuery(sql);

			while (rs.next()) {
				users.add(BuildGlobalConfiguration(rs));
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

		return users;
	}
	
	@Override
	public String getConfigurationValueByCode(String code) throws ServerException {
		String value = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String sqlGet = "SELECT * FROM GlobalConfiguration WHERE CODE = ?";
			preparedStatement = this.connection.prepareStatement(sqlGet);
			preparedStatement.setString(1, code);
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				value = rs.getString("value");
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

		return value;
	}
	
	GlobalConfiguration BuildGlobalConfiguration(ResultSet rs) throws SQLException {
		int _id = rs.getInt("id");
		String code = rs.getString("code");
		String value = rs.getString("value");
		String description = rs.getString("description");

		GlobalConfiguration globalConfiguration = new GlobalConfiguration();
		globalConfiguration.setId(_id);
		globalConfiguration.setCode(code);
		globalConfiguration.setValue(value);
		globalConfiguration.setDescription(description);

		return globalConfiguration;
	}
}
