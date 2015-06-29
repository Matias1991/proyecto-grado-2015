package datalayer.daos;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import datalayer.utils.ManageConnection;
import servicelayer.entity.businessEntity.User;
import servicelayer.exceptions.DataLayerException;
import servicelayer.interfaces.dataLayer.IDAOUsers;

public class DAOUsers implements IDAOUsers {

	private Connection connection;
	
	public DAOUsers() throws DataLayerException
	{
		try {
			this.connection = new ManageConnection().GetConnection();
			
		} catch (ClassNotFoundException e) {
			throw new DataLayerException(e);
		} catch (SQLException e) {
			throw new DataLayerException(e);
		}catch (IOException e) {
			throw new DataLayerException(e);
		}
	}
	
	public DAOUsers(Connection connection)
	{
		this.connection = connection;
	}

	@Override
	public void Insert(User obj) throws DataLayerException{
		// TODO Auto-generated method stub     
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO USER (USERNAME, PASSWORD, NAME, LASTNAME, EMAIL) VALUES"
				+ "(?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(
					insertTableSQL);

			preparedStatement.setString(1, obj.getUserName());
			preparedStatement.setString(2, obj.getPassword());
			preparedStatement.setString(3, obj.getName());
			preparedStatement.setString(4, obj.getLastName());
			preparedStatement.setString(5, obj.getEmail());
			
			// execute insert SQL stetement
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			throw new DataLayerException(e);
		}
	}

	@Override
	public void Delete(int id) throws DataLayerException {
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "DELETE FROM USER WHERE ID = ?";

		try {
			preparedStatement = this.connection.prepareStatement(
					insertTableSQL);

			preparedStatement.setInt(1, id);

			// execute insert SQL stetement
			preparedStatement.execute();

		} catch (SQLException e) {
			throw new DataLayerException(e);
		} finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean Exist(int id) throws DataLayerException {
		// TODO Auto-generated method stub
		Statement stmt = null;
		try {

			stmt = this.connection.createStatement();
			String sql;
			sql = "SELECT * FROM user where id = " + id;
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				return true;
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
			
		} catch (SQLException se) {
			throw new DataLayerException(se);
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
		}// end tryÃ�Ã�

		return false;
	}

	@Override
	public User GetObject(int id) throws DataLayerException {
		User user = null;
		Statement stmt = null;
		try {

			stmt = this.connection.createStatement();
			String sql;
			sql = "SELECT * FROM user where id = " + id;
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				user = new User();
				// Retrieve by column name
				int _id = rs.getInt("id");
				String userName = rs.getString("userName");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String lastName = rs.getString("lastName");
				String email = rs.getString("email");

				user.setId(_id);
				user.setName(name);
				user.setLastName(lastName);
				user.setPassword(password);
				user.setUserName(userName);
				user.setEmail(email);
			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
		} catch (SQLException se) {
			throw new DataLayerException(se);
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
		}// end tryÃ�Ã�

		return user;
	}

	@Override
	public ArrayList<User> GetAll() throws DataLayerException {
		// TODO Auto-generated method stub
		ArrayList<User> users = new ArrayList<User>();
		Statement stmt = null;
		try {

			stmt = this.connection.createStatement();
			String sql;
			sql = "SELECT * FROM user";
			ResultSet rs = stmt.executeQuery(sql);

			// STEP 5: Extract data from result set
			while (rs.next()) {
				// Retrieve by column name
				int _id = rs.getInt("id");
				String userName = rs.getString("userName");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String lastName = rs.getString("lastName");
				String email = rs.getString("email");

				User user = new User();
				user.setId(_id);
				user.setName(name);
				user.setLastName(lastName);
				user.setPassword(password);
				user.setUserName(userName);
				user.setEmail(email);

				users.add(user);

			}
			// STEP 6: Clean-up environment
			rs.close();
			stmt.close();
		} catch (SQLException se) {
			throw new DataLayerException(se);
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					stmt.close();
			} catch (SQLException se2) {
			}// nothing we can do
		}// end tryÃ�Ã�

		return users;
	}
}
