package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import datalayer.utils.ManageConnection;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.valueObject.VOUser;
import servicelayer.exceptions.DataLayerException;
import servicelayer.interfaces.dataLayer.IDAOUsers;

public class DAOUsers implements IDAOUsers {

	private Connection connection;
	private static Logger log = Logger.getLogger("************ Data access error ********");
	
	public DAOUsers() throws DataLayerException
	{
		BasicConfigurator.configure();
		
		try {
			this.connection = new ManageConnection().GetConnection();
		}catch (Exception e) {
			log.error(e.getMessage());
			throw new DataLayerException("Ocurrio una problema al conectarse con la base de datos");
		}
	}
	
	public DAOUsers(Connection connection)
	{
		this.connection = connection;
	}

	@Override
	public void insert(User obj) throws DataLayerException{   
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO USER (USERNAME, PASSWORD, NAME, LASTNAME, EMAIL) VALUES"
				+ "(?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertTableSQL);

			preparedStatement.setString(1, obj.getUserName());
			preparedStatement.setString(2, obj.getPassword());
			preparedStatement.setString(3, obj.getName());
			preparedStatement.setString(4, obj.getLastName());
			preparedStatement.setString(5, obj.getEmail());

			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DataLayerException("Ocurrio un problema al intentar ingresar un usuario a la base de datos, consulte con el administrador");
		}
	}

	@Override
	public void delete(int id) throws DataLayerException {
		PreparedStatement preparedStatement = null;
		
		try {
		
			String insertTableSQL = "DELETE FROM USER WHERE ID = ?";
			preparedStatement = this.connection.prepareStatement(insertTableSQL);
			preparedStatement.setInt(1, id);
			preparedStatement.execute();

		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DataLayerException("Ocurrio un error al intentar eliminar el usuario, consulte con el administrador");
		} finally {

			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					log.error(e.getMessage());
				}
			}
		}
	}

	@Override
	public boolean exist(int id) throws DataLayerException {
		Statement stmt = null;
		ResultSet rs = null;
		try {

			stmt = this.connection.createStatement();
			String sql;
			sql = "SELECT * FROM user where id = " + id;
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				return true;
			}

		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DataLayerException("Ocurrio un error al intentar validar si existe el usuario, consulte con el administrador");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}

		return false;
	}

	@Override
	public User getObject(int id) throws DataLayerException {
		User user = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = this.connection.createStatement();
			String sql;
			sql = "SELECT * FROM user where id = " + id;
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				user = new User();
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
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DataLayerException("Ocurrio un error al intentar obtener el usuario, consulte con el administrador");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}

		return user;
	}

	@Override
	public ArrayList<User> getObjects() throws DataLayerException {
		ArrayList<User> users = new ArrayList<User>();
		Statement stmt = null;
		ResultSet rs = null;
		try {

			stmt = this.connection.createStatement();
			String sql;
			sql = "SELECT * FROM user";
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
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

		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DataLayerException("Ocurrio un error al intentar obtener los usuarios, consulte con el administrador");
	
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}

		return users;
	}
	
	@Override
	public boolean login(String userName,String password) throws DataLayerException
	{
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		
		try
		{
			String insertTableSQL = "SELECT id FROM USER WHERE userName = ? and password = ?";
			preparedStatement = this.connection.prepareStatement(insertTableSQL);
			preparedStatement.setString(1, userName);
			preparedStatement.setString(2, password);
		
			rs = preparedStatement.executeQuery();
			if(rs.first())
				return true;
			else
				return false;
			
		} catch (SQLException e) {
			log.error(e.getMessage());
			throw new DataLayerException("Ocurrio un error al intentar obtener los usuarios, consulte con el administrador");
		}finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
				if (rs != null)
					rs.close();
			} catch (SQLException e) {
				log.error(e.getMessage());
			}
		}
	}
}
