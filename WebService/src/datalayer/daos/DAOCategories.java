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
import servicelayer.entity.businessEntity.CategoryType;
import servicelayer.entity.businessEntity.Project;
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

		String insertSQL = "INSERT INTO CATEGORY (VERSION, DESCRIPTION, AMOUNT, CREATEDDATETIMEUTC, PROJECTID, CATEGORYTYPE, ISRRHH, MODIFYDATETIMEUTC) VALUES"
				+ "(?,?,?,?,?,?,?, ?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL);

			preparedStatement.setInt(1, 1);
			preparedStatement.setString(2, obj.getDescription());
			preparedStatement.setDouble(3, obj.getAmount());
			preparedStatement.setTimestamp(4, new Timestamp(obj
					.getCreateDateTimeUTC().getTime()));
			if(obj.getProject() != null)
				preparedStatement.setInt(5, obj.getProject().getId());
			else
				preparedStatement.setNull(5, java.sql.Types.INTEGER);
			preparedStatement.setInt(6, obj.getCategoryType().getValue());
			preparedStatement.setBoolean(7, obj.getIsRRHH());
			preparedStatement.setTimestamp(8, new Timestamp(new Date().getTime()));
			
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
	public void update(int change, Category obj) throws ServerException {
		PreparedStatement preparedStatement = null;

		String updateSQL =  "";
		try {
			
			// Si cambi� el rubro el mismo d�a modifico el registro anterior
			if(change == 0){
				updateSQL =  "UPDATE CATEGORY SET AMOUNT = ?, CREATEDDATETIMEUTC = ?, "
						+ "PROJECTID = ?, CATEGORYTYPE = ?, ISRRHH = ?, MODIFYDATETIMEUTC = ? WHERE ID = ? AND VERSION = ?";
				preparedStatement = this.connection.prepareStatement(updateSQL);

				preparedStatement.setDouble(1, obj.getAmount());
				preparedStatement.setTimestamp(2, new Timestamp(obj
						.getCreateDateTimeUTC().getTime()));
				if(obj.getProject() != null)
					preparedStatement.setInt(3, obj.getProject().getId());
				else
					preparedStatement.setNull(3, java.sql.Types.INTEGER);
				preparedStatement.setInt(4, obj.getCategoryType().getValue());
				preparedStatement.setBoolean(5, obj.getIsRRHH());
				preparedStatement.setTimestamp(6, new Timestamp(new Date().getTime()));
				preparedStatement.setInt(7, obj.getId());
				preparedStatement.setInt(8, obj.getVersion());

			} else {
				updateSQL =  "INSERT INTO CATEGORY (VERSION, DESCRIPTION, AMOUNT, CREATEDDATETIMEUTC, "
						+ "PROJECTID, CATEGORYTYPE, ISRRHH, ID, MODIFYDATETIMEUTC) VALUES"
						+ "(?,?,?,?,?,?,?,?, ?)";
				
				preparedStatement = this.connection.prepareStatement(updateSQL);

				preparedStatement.setInt(1, obj.getVersion() + 1);
				preparedStatement.setString(2, obj.getDescription());
				preparedStatement.setDouble(3, obj.getAmount());
				preparedStatement.setTimestamp(4, new Timestamp(obj
						.getCreateDateTimeUTC().getTime()));
				if(obj.getProject() != null)
					preparedStatement.setInt(5, obj.getProject().getId());
				else
					preparedStatement.setNull(5, java.sql.Types.INTEGER);
				preparedStatement.setInt(6, obj.getCategoryType().getValue());
				preparedStatement.setBoolean(7, obj.getIsRRHH());
				preparedStatement.setInt(8, obj.getId());
				preparedStatement.setTimestamp(9, new Timestamp(new Date().getTime()));
			}
						
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

			String getSQL = "SELECT C.*, P.NAME FROM CATEGORY C"
					+ " LEFT OUTER JOIN PROJECT P ON P.Id = C.ProjectId"
					+ " WHERE C.ID = ? AND VERSION = (SELECT MAX(VERSION) FROM CATEGORY WHERE ID = ?)";
			
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setInt(1, id);
			preparedStatement.setInt(2, id);
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
			
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("SELECT C_1.*, P.NAME as ProjectName ");
			strBuilder.append("FROM CATEGORY C_1 ");
			strBuilder.append("LEFT OUTER JOIN PROJECT P ON P.Id = C_1.ProjectId ");
			strBuilder.append("WHERE (C_1.Description, C_1.Version) in (SELECT C_2.Description, MAX(VERSION) ");
		    strBuilder.append("FROM CATEGORY C_2 ");
			strBuilder.append("LEFT OUTER JOIN PROJECT P ON P.Id = C_2.ProjectId ");
			strBuilder.append("GROUP BY C_2.Description )");
			
			preparedStatement = this.connection.prepareStatement(strBuilder.toString());
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Category category = BuildCategory(rs);
				if(category.getProject() != null)
					category.getProject().setName(rs.getString("projectName"));
				categories.add(category);
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
	
	@Override
	public ArrayList<Category> getCategories(String description) throws ServerException {
		ArrayList<Category> categories = new ArrayList<Category>();;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM CATEGORY WHERE description = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setString(1, description);
			rs = preparedStatement.executeQuery();

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
	
	@Override
	public ArrayList<Category> getCategories(String description, int projectId) throws ServerException {
		ArrayList<Category> categories = new ArrayList<Category>();;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM CATEGORY WHERE description = ? and projectId = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setString(1, description);
			preparedStatement.setInt(2, projectId);
			rs = preparedStatement.executeQuery();

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
		int categoryType = rs.getInt("categoryType");
		boolean isRRhh = rs.getBoolean("isRRHH");
		int version = rs.getInt("version");
		Date modifyDateTimeUTC = rs.getTimestamp("modifyDateTimeUTC");

		Category category = new Category();
		category.setId(_id);
		category.setDescription(description);
		category.setAmount(amount);
		category.setCreateDateTimeUTC(createDateTimeUTC);
		if(projectId != 0)
			category.setProject(new Project(projectId));
		category.setCategoryType(CategoryType.getEnum(categoryType));
		category.setIsRRHH(isRRhh);
		category.setVersion(version);
		category.setModifyDateTimeUTC(modifyDateTimeUTC);
		
		return category;
	}
}
