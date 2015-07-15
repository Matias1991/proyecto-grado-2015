package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import datalayer.utilities.ManageConnection;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.businessEntity.UserStatus;
import servicelayer.entity.businessEntity.UserType;
import servicelayer.entity.valueObject.VOUser;
import shared.LoggerMSMP;
import shared.exceptions.DataLayerException;
import shared.interfaces.dataLayer.IDAOUsers;

public class DAOUsers implements IDAOUsers {

	private Connection connection;

	public DAOUsers() throws DataLayerException
	{
		BasicConfigurator.configure();
		
		try {
			this.connection = new ManageConnection().GetConnection();
		}catch (Exception e) {
			ThrowDataLayerExceptionAndLogError(e, "conectarse con la base de datos");
		}
	}
	
	public DAOUsers(Connection connection)
	{
		this.connection = connection;
	}

	@Override
	public void insert(User obj) throws DataLayerException{   
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO USER (USERNAME, PASSWORD, NAME, LASTNAME, EMAIL, USERTYPEID, USERSTATUSID) VALUES"
				+ "(?,?,?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, obj.getUserName());
			preparedStatement.setString(2, obj.getPassword());
			preparedStatement.setString(3, obj.getName());
			preparedStatement.setString(4, obj.getLastName());
			preparedStatement.setString(5, obj.getEmail());
			preparedStatement.setInt(6, obj.getUserType().getValue());
			preparedStatement.setInt(7, obj.getUserStatus().getValue());
			
			preparedStatement.executeUpdate();
			 
		} catch (SQLException e) {
			ThrowDataLayerExceptionAndLogError(e, "ingresar el usuario");
		}finally {

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
	public void delete(int id) throws DataLayerException {
		PreparedStatement preparedStatement = null;
		
		try {
		
			String deleteSQL = "DELETE FROM USER WHERE ID = ?";
			preparedStatement = this.connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, id);
			preparedStatement.execute();

		} catch (SQLException e) {
			ThrowDataLayerExceptionAndLogError(e, "eliminar el usuario");
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
	public boolean exist(int id) throws DataLayerException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM USER WHERE id = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			ThrowDataLayerExceptionAndLogError(e, "validar si existe el usuario");
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

		return false;
	}

	@Override
	public User getObject(int id) throws DataLayerException {
		User user = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM USER WHERE id = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				user = BuildUser(rs);
			}
		} catch (SQLException e) {
			ThrowDataLayerExceptionAndLogError(e, "obtener los datos del usuario");
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

		return user;
	}

	@Override
	public ArrayList<User> getObjects() throws DataLayerException {
		ArrayList<User> users = new ArrayList<User>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String sql;
			sql = "SELECT * FROM user";
			preparedStatement = this.connection.prepareStatement(sql);
			rs = preparedStatement.executeQuery(sql);

			while (rs.next()) {
				users.add(BuildUser(rs));
			}

		} catch (SQLException e) {
			ThrowDataLayerExceptionAndLogError(e, "obtener los datos del los usuarios");
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
	public ArrayList<User> getUsersByTypeAndStatus(UserType userType, UserStatus userStatus) throws DataLayerException
	{
		ArrayList<User> users = new ArrayList<User>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM USER WHERE userTypeId = ? and userStatusId = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setInt(1, userType.getValue());
			preparedStatement.setInt(2, userStatus.getValue());
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				users.add(BuildUser(rs));
			}

		} catch (SQLException e) {
			ThrowDataLayerExceptionAndLogError(e, "obtener los datos del los usuarios");
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
	public User getUserByUserName(String userName) throws DataLayerException
	{
		User user = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try
		{
			String getSQL = "SELECT * FROM USER WHERE userName = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setString(1, userName);
			
			rs = preparedStatement.executeQuery();
		
			while (rs.next()) {
				user = BuildUser(rs);
			}
			
		} catch (SQLException e) {
			ThrowDataLayerExceptionAndLogError(e, "obtener los datos del usuario");
		}finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				LoggerMSMP.setLog(e.getMessage());
			}
		}
		
		return user;
	}
	
	@Override
	public User getUserByUserEmail(String userEmail) throws DataLayerException
	{
		User user = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try
		{
			String getSQL = "SELECT * FROM USER WHERE email = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setString(1, userEmail);
			
			rs = preparedStatement.executeQuery();
		
			while (rs.next()) {
				user = BuildUser(rs);
			}
			
		} catch (SQLException e) {
			ThrowDataLayerExceptionAndLogError(e, "obtener los datos del usuario");
		}finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				LoggerMSMP.setLog(e.getMessage());
			}
		}
		
		return user;
	}

	@Override
	public void update(int id, User obj) throws DataLayerException {
		
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE USER "
				+ "SET name = ?, "
				+ "lastName = ?, "
				+ "email = ?, "
				+ "userTypeId = ? "
				+ "WHERE id = ?";

		try {
			preparedStatement = this.connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, obj.getName());
			preparedStatement.setString(2, obj.getLastName());
			preparedStatement.setString(3, obj.getEmail());
			preparedStatement.setInt(4, obj.getUserType().getValue());
			preparedStatement.setInt(5, id);
			
			preparedStatement.executeUpdate();
			 
		} catch (SQLException e) {
			ThrowDataLayerExceptionAndLogError(e, "modificar los datos del usuario");
		}
	}
	
	@Override
	public void update(int id, UserStatus userStatus, int attempts, Date lastAttemptDateTimeUTC) throws DataLayerException {
		
		PreparedStatement preparedStatement = null;
		
		String updateSQL = "UPDATE USER "
				+ "SET attempts = ?, "
				+ "lastAttemptDateTimeUTC = ?, "
				+ "userStatusId = ? "
				+ "WHERE id = ?";

		try {
			preparedStatement = this.connection.prepareStatement(updateSQL);

			Timestamp _lastAttemptTimestamp = null;
			if(lastAttemptDateTimeUTC != null)
				_lastAttemptTimestamp = new Timestamp (lastAttemptDateTimeUTC.getTime());
			
			preparedStatement.setInt(1, attempts);
			preparedStatement.setTimestamp(2, _lastAttemptTimestamp);
			preparedStatement.setInt(3, userStatus.getValue());
			preparedStatement.setInt(4, id);
			
			preparedStatement.executeUpdate();
			 
		} catch (SQLException e) {
			ThrowDataLayerExceptionAndLogError(e, "modificar los datos del usuario");
		}
	}
	
	@Override
	public void updatePassword(int id, String newPassword) throws DataLayerException {
		
		PreparedStatement preparedStatement = null;
		
		String updatePasswordSQL = "UPDATE USER "
				+ "SET password = ?"
				+ "WHERE id = ?";

		try {
			preparedStatement = this.connection.prepareStatement(updatePasswordSQL);

			preparedStatement.setString(1, newPassword);
			preparedStatement.setInt(2, id);
			
			preparedStatement.executeUpdate();
			 
		} catch (SQLException e) {
			ThrowDataLayerExceptionAndLogError(e, "modificar la contraseña del usuario");
		}
	}
	
	User BuildUser(ResultSet rs) throws SQLException
	{
		int _id = rs.getInt("id");
		String userName = rs.getString("userName");
		String name = rs.getString("name");
		String lastName = rs.getString("lastName");
		String email = rs.getString("email");
		String password = rs.getString("password");
		int attempts = rs.getInt("attempts");
		Date lastAttemptDateTimeUTC = rs.getTimestamp("lastAttemptDateTimeUTC");
		int userTypeId = rs.getInt("userTypeId");
		int userStatusId = rs.getInt("userStatusId");
		
		User user = new User();
		user.setId(_id);
		user.setName(name);
		user.setLastName(lastName);
		user.setUserName(userName);
		user.setEmail(email);
		user.setPassword(password);
		user.setAttempts(attempts);
		user.setLastAttemptDateTimeUTC(lastAttemptDateTimeUTC);
		user.setUserType(UserType.getEnum(userTypeId));
		user.setUserStatus(UserStatus.getEnum(userStatusId));
		
		return user;
	}
	
	void ThrowDataLayerExceptionAndLogError(Exception ex, String message) throws DataLayerException
	{
		String errorNumber = LoggerMSMP.setLog(ex.getMessage());
		throw new DataLayerException(String.format("Ocurrio un error al %s, consulte con el Administrador con el codigo de error: %S", message, errorNumber));
	}
}
