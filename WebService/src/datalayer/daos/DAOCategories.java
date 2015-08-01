package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import datalayer.utilities.ManageConnection;
import servicelayer.entity.businessEntity.Category;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOCategroy;

public class DAOCategories implements IDAOCategroy {

	private Connection connection;

	public DAOCategories() throws ServerException {
		try {
			this.connection = new ManageConnection().GetConnection();
		} catch (Exception e) {
			throw new ServerException(e);
		}
	}

	public DAOCategories(Connection connection) {
		this.connection = connection;
	}

	@Override
	public int insert(Category obj) throws ServerException {
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO CATEGORY (DESCRIPTION, AMOUNT, CREATEDDATETIMEUTC, PROJECTID, DISTRIBUTIONTYPE) VALUES"
				+ "(?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, obj.getDescription());
			preparedStatement.setDouble(2, obj.getAmount());
			preparedStatement.setTimestamp(3, new Timestamp(obj
					.getCreateDateTimeUTC().getTime()));
			preparedStatement.setInt(4, obj.getProjectId());
			preparedStatement.setInt(5, obj.getDistributionType());

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
			String deleteSQL = "DELETE FROM CATEGORY WHERE ID = ?";
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
	public void update(int id, Category obj) throws ServerException {
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE CATEGORY "
				+ "SET DESCRIPTION = ?, AMOUNT = ?, PROJECTID = ?, DISTRIBUTIONTYPE = ? WHERE id = ?";

		try {
			preparedStatement = this.connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, obj.getDescription());
			preparedStatement.setDouble(2, obj.getAmount());
			preparedStatement.setInt(3, obj.getProjectId());
			preparedStatement.setInt(4, obj.getDistributionType());
			preparedStatement.setInt(5, id);

			preparedStatement.executeUpdate();

		} catch (SQLException e) {
			throw new ServerException(e);
		}

	}

	@Override
	public boolean exist(int id) throws ServerException {
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			String getSQL = "SELECT * FROM CATEGORY WHERE id = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				return true;
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

		return false;
	}

	@Override
	public Category getObject(int id) throws ServerException {
		Category category = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM CATEGORY WHERE id = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				category = BuildCategory(rs);
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

		return category;
	}

	@Override
	public ArrayList<Category> getObjects() throws ServerException {
		ArrayList<Category> categories = new ArrayList<Category>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String sql;
			sql = "SELECT * FROM CATEGORY";
			preparedStatement = this.connection.prepareStatement(sql);
			rs = preparedStatement.executeQuery(sql);

			while (rs.next()) {
				categories.add(BuildCategory(rs));
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

		return categories;
	}

	private Category BuildCategory(ResultSet rs) throws SQLException {
		int _id = rs.getInt("id");
		String description = rs.getString("description");
		double amount = rs.getDouble("amount");
		Date createDateTimeUTC = rs.getTimestamp("createdDateTimeUTC");
		int projectId = rs.getInt("projectid");
		int distributionType = rs.getInt("distributionType");

		Category category = new Category();
		category.setId(_id);
		category.setDescription(description);
		category.setAmount(amount);
		category.setCreateDateTimeUTC(createDateTimeUTC);
		category.setProjectId(projectId);
		category.setDistributionType(distributionType);

		return category;
	}

}
