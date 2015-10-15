package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import datalayer.utilities.ManageConnection;
import servicelayer.entity.businessEntity.Category;
import servicelayer.entity.businessEntity.CategoryType;
import servicelayer.entity.businessEntity.IVA_Type;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.businessEntity.UserType;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOCategroy;

public class DAOCategories implements IDAOCategroy {

	private Connection connection;

	public DAOCategories(Connection connection) {
		this.connection = connection;
	}

	@Override
	public int insert(Category obj) throws ServerException {
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO CATEGORY (VERSION, NAME, DESCRIPTION, AMOUNTPESO, AMOUNTDOLLAR, ISCURRENCYDOLLAR, "
				+ "TYPEEXCHANGE, IVA_TYPEID, APPLIEDDATETIMEUTC, PROJECTID, CATEGORYTYPE, ISRRHH, UPDATEDDATETIMEUTC) VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL);

			preparedStatement.setInt(1, 1);
			preparedStatement.setString(2,obj.getName());
			preparedStatement.setString(3, obj.getDescription());
			preparedStatement.setDouble(4, obj.getAmountPeso());
			preparedStatement.setDouble(5, obj.getAmountDollar());
			preparedStatement.setBoolean(6, obj.getIsCurrencyDollar());
			preparedStatement.setDouble(7, obj.getTypeExchange());
			preparedStatement.setInt(8, obj.getIvaTypeId());
			preparedStatement.setTimestamp(
					9,
					new Timestamp(setFirstDayOfMonth(
							obj.getAppliedDateTimeUTC()).getTime()));
			if (obj.getProject() != null)
				preparedStatement.setInt(10, obj.getProject().getId());
			else
				preparedStatement.setNull(10, java.sql.Types.INTEGER);
			preparedStatement.setInt(11, obj.getCategoryType().getValue());
			preparedStatement.setBoolean(12, obj.getIsRRHH());
			preparedStatement.setTimestamp(13,
					new Timestamp(new Date().getTime()));

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

		String updateSQL = "";
		try {

			// Si cambi� el rubro el mismo d�a modifico el registro anterior
			if (change == 0) {
				updateSQL = "UPDATE CATEGORY SET DESCRIPTION = ?, AMOUNTPESO = ?, APPLIEDDATETIMEUTC = ?, "
						+ "PROJECTID = ?, CATEGORYTYPE = ?, ISRRHH = ?, UPDATEDDATETIMEUTC = ?,"
						+ "AMOUNTDOLLAR = ?, ISCURRENCYDOLLAR = ?, TYPEEXCHANGE = ?, IVA_TYPEID = ?"
						+ "  WHERE ID = ? AND VERSION = ?";
				preparedStatement = this.connection.prepareStatement(updateSQL);
				
				preparedStatement.setString(1, obj.getDescription());
				preparedStatement.setDouble(2, obj.getAmountPeso());
				preparedStatement.setTimestamp(3, new Timestamp(obj
						.getAppliedDateTimeUTC().getTime()));
				if (obj.getProject() != null)
					preparedStatement.setInt(4, obj.getProject().getId());
				else
					preparedStatement.setNull(4, java.sql.Types.INTEGER);
				preparedStatement.setInt(5, obj.getCategoryType().getValue());
				preparedStatement.setBoolean(6, obj.getIsRRHH());
				preparedStatement.setTimestamp(7,
						new Timestamp(new Date().getTime()));
				preparedStatement.setDouble(8, obj.getAmountDollar());
				preparedStatement.setBoolean(9, obj.getIsCurrencyDollar());
				preparedStatement.setDouble(10, obj.getTypeExchange());
				preparedStatement.setInt(11, obj.getIvaTypeId());
				preparedStatement.setInt(12, obj.getId());
				preparedStatement.setInt(13, obj.getVersion());

			} else {
				updateSQL = "INSERT INTO CATEGORY (VERSION, NAME, DESCRIPTION, AMOUNTPESO, AMOUNTDOLLAR, ISCURRENCYDOLLAR, TYPEEXCHANGE,"
						+ " IVA_TYPEID, APPLIEDDATETIMEUTC, PROJECTID, CATEGORYTYPE, ISRRHH, ID, UPDATEDDATETIMEUTC) VALUES"
						+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

				preparedStatement = this.connection.prepareStatement(updateSQL);

				preparedStatement.setInt(1, obj.getVersion() + 1);
				preparedStatement.setString(2,obj.getName());
				preparedStatement.setString(3, obj.getDescription());
				preparedStatement.setDouble(4, obj.getAmountPeso());
				preparedStatement.setDouble(5, obj.getAmountDollar());
				preparedStatement.setBoolean(6, obj.getIsCurrencyDollar());
				preparedStatement.setDouble(7, obj.getTypeExchange());
				preparedStatement.setInt(8, obj.getIvaTypeId());
				preparedStatement.setTimestamp(9, new Timestamp(
						setFirstDayOfMonth(obj.getAppliedDateTimeUTC())
								.getTime()));
				if (obj.getProject() != null)
					preparedStatement.setInt(10, obj.getProject().getId());
				else
					preparedStatement.setNull(10, java.sql.Types.INTEGER);
				preparedStatement.setInt(11, obj.getCategoryType().getValue());
				preparedStatement.setBoolean(12, obj.getIsRRHH());
				preparedStatement.setInt(13, obj.getId());
				preparedStatement.setTimestamp(14,
						new Timestamp(new Date().getTime()));
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
			strBuilder
					.append("SELECT C_1.*, P.NAME as ProjectName, P.CLOSED as ProjectClosed ");
			strBuilder.append("FROM CATEGORY C_1 ");
			strBuilder
					.append("LEFT OUTER JOIN PROJECT P ON P.Id = C_1.ProjectId ");
			strBuilder
					.append("WHERE (C_1.Id, C_1.Version) in (SELECT C_2.Id, MAX(VERSION) ");
			strBuilder.append("FROM CATEGORY C_2 ");
			strBuilder
					.append("LEFT OUTER JOIN PROJECT P ON P.Id = C_2.ProjectId ");
			strBuilder.append("GROUP BY C_2.Id )");
			preparedStatement = this.connection.prepareStatement(strBuilder
					.toString());
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Category category = BuildCategory(rs);
				if (category.getProject() != null) {
					category.getProject().setName(rs.getString("projectName"));
					category.getProject().setClosed(
							rs.getBoolean("projectClosed"));
				}
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

	// Devuelve todos los rubros cuya fecha de aplicacion sea en el periodo
	// ingresado (ultima versi�n) dado un gerente
	@Override
	public ArrayList<Category> getCategories(User userContext)
			throws ServerException {
		ArrayList<Category> categories = new ArrayList<Category>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT C_1.*, P.NAME as ProjectName, P.CLOSED as ProjectClosed ");
			sql.append("FROM meerkatsys_msmp.category C_1 ");
			sql.append("LEFT OUTER JOIN meerkatsys_msmp.project P ON P.Id = C_1.ProjectId ");
			sql.append("WHERE (C_1.Id, C_1.Version) in (SELECT C_2.Id, MAX(VERSION)");
			sql.append("FROM meerkatsys_msmp.category C_2 LEFT OUTER JOIN meerkatsys_msmp.PROJECT P ON P.Id = C_2.ProjectId ");
			sql.append("GROUP BY C_2.Id ) ");
			if (userContext.getUserType() == UserType.MANAGER) {
				sql.append("AND P.ManagerId = ? ");
			}
			sql.append("ORDER BY APPLIEDDATETIMEUTC DESC ");

			preparedStatement = this.connection
					.prepareStatement(sql.toString());
			if (userContext.getUserType() == UserType.MANAGER) {
				preparedStatement.setInt(1, userContext.getId());
			}
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Category category = BuildCategory(rs);
				if (category.getProject() != null) {
					category.getProject().setName(rs.getString("projectName"));
					category.getProject().setClosed(
							rs.getBoolean("projectClosed"));
				}
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
	public ArrayList<Category> getCategories(String name,
			CategoryType categoryType) throws ServerException {
		ArrayList<Category> categories = new ArrayList<Category>();
		;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM CATEGORY WHERE name = ? and categoryType = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, categoryType.getValue());
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
	public ArrayList<Category> getCategories(String name, boolean isCurrencyDollar, Date date) throws ServerException {
		ArrayList<Category> categories = new ArrayList<Category>();
		;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT C_1.*, P.NAME as ProjectName ");
			sql.append("FROM CATEGORY C_1 ");
			sql.append("LEFT OUTER JOIN PROJECT P ON P.Id = C_1.ProjectId ");
			sql.append("WHERE C_1.AppliedDateTimeUTC = ? AND C_1.name = ? AND C_1.isCurrencyDollar = ? AND (C_1.Id, C_1.Version) in (SELECT C_2.Id, MAX(VERSION)");
			sql.append("FROM CATEGORY C_2 LEFT OUTER JOIN PROJECT P ON P.Id = C_2.ProjectId ");
			sql.append("GROUP BY C_2.Id ) ");
			
			preparedStatement = this.connection.prepareStatement(sql.toString());
			preparedStatement.setTimestamp(1, new Timestamp(date.getTime()));
			preparedStatement.setString(2, name);
			preparedStatement.setBoolean(3, isCurrencyDollar);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Category category = BuildCategory(rs);
				if (category.getProject() != null) {
					category.getProject().setName(rs.getString("projectName"));
				}
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
	public ArrayList<Category> getCategories(String name, int projectId)
			throws ServerException {
		ArrayList<Category> categories = new ArrayList<Category>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM CATEGORY WHERE name = ? and projectId = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setString(1, name);
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

	// Devuelve todos los rubros cuya fecha de aplicacion sea en el periodo
	// ingresado (ultima versi�n)
	@Override
	public ArrayList<Category> getCategories(Date from, Date to,
			User userContext) throws ServerException {
		ArrayList<Category> categories = new ArrayList<Category>();
		;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT C_1.*, P.NAME as ProjectName, P.CLOSED as ProjectClosed ");
			sql.append("FROM CATEGORY C_1 ");
			sql.append("LEFT OUTER JOIN PROJECT P ON P.Id = C_1.ProjectId ");
			sql.append("WHERE (C_1.Id, C_1.Version) in (SELECT C_2.Id, MAX(VERSION) ");
			sql.append("FROM CATEGORY C_2 LEFT OUTER JOIN PROJECT P ON P.Id = C_2.ProjectId ");
			sql.append("GROUP BY C_2.Id ) ");
			if (userContext.getUserType() == UserType.MANAGER) {
				sql.append("AND P.ManagerId = ? ");
			}
			sql.append("AND APPLIEDDATETIMEUTC between ? AND ? ");
			sql.append("ORDER BY APPLIEDDATETIMEUTC DESC");

			preparedStatement = this.connection
					.prepareStatement(sql.toString());
			if (userContext.getUserType() == UserType.MANAGER) {
				preparedStatement.setInt(1, userContext.getId());
				preparedStatement.setTimestamp(2, new Timestamp(
						setFirstDayOfMonth(from).getTime()));
				preparedStatement.setTimestamp(3, new Timestamp(
						setFirstDayOfMonth(to).getTime()));
			} else {
				preparedStatement.setTimestamp(1, new Timestamp(
						setFirstDayOfMonth(from).getTime()));
				preparedStatement.setTimestamp(2, new Timestamp(
						setFirstDayOfMonth(to).getTime()));
			}
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Category category = BuildCategory(rs);
				if (category.getProject() != null) {
					category.getProject().setName(rs.getString("projectName"));
					category.getProject().setClosed(
							rs.getBoolean("projectClosed"));
				}
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

	// Devuelve todos los rubros cuya fecha de aplicacion sea en el periodo
	// ingresado (ultima versi�n) asociados a un proyecto
	@Override
	public ArrayList<Category> getCategories(int projectId, Date from, Date to)
			throws ServerException {
		ArrayList<Category> categories = new ArrayList<Category>();
		;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT C_1.* ");
			sql.append("FROM CATEGORY C_1 ");
			sql.append("WHERE C_1.projectId = ? AND (C_1.Id, C_1.Version) in (SELECT C_2.Id, MAX(VERSION) ");
			sql.append("FROM CATEGORY C_2 ");
			sql.append("GROUP BY C_2.Id ) ");
			sql.append("AND APPLIEDDATETIMEUTC between ? AND ? ");
			sql.append("ORDER BY APPLIEDDATETIMEUTC DESC");

			preparedStatement = this.connection
					.prepareStatement(sql.toString());
			preparedStatement.setInt(1, projectId);
			preparedStatement.setTimestamp(2,
					new Timestamp(setFirstDayOfMonth(from).getTime()));
			preparedStatement.setTimestamp(3,
					new Timestamp(setFirstDayOfMonth(to).getTime()));

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Category category = BuildCategory(rs);
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
	public ArrayList<Category> getCategoriesLastVersion(String name,
			CategoryType categoryType) throws ServerException {
		ArrayList<Category> categories = new ArrayList<Category>();
		;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM CATEGORY WHERE name = ? and categoryType = ?"
					+ " and version = (SELECT MAX(version) from  CATEGORY WHERE name = ? and categoryType = ? )";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, categoryType.getValue());
			preparedStatement.setString(3, name);
			preparedStatement.setInt(4, categoryType.getValue());
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
	public ArrayList<Category> getCategoriesLastVersion(String name,
			int projectId) throws ServerException {
		ArrayList<Category> categories = new ArrayList<Category>();
		;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM CATEGORY WHERE name = ? and projectId = ?"
					+ " and version = (SELECT MAX(version) from  CATEGORY WHERE name = ? and projectId = ? )";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setString(1, name);
			preparedStatement.setInt(2, projectId);
			preparedStatement.setString(3, name);
			preparedStatement.setInt(4, projectId);
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
	public ArrayList<Category> getCategoriesByProject(int idProject)
			throws ServerException {
		ArrayList<Category> categories = new ArrayList<Category>();
		;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM CATEGORY C WHERE PROJECTID = ?  "
					+ "AND C.VERSION IN (SELECT MAX(VERSION) FROM CATEGORY C2 WHERE C2.PROJECTID = ? AND C.ID = C2.ID)";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setInt(1, idProject);
			preparedStatement.setInt(2, idProject);
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
	public ArrayList<Category> getCategoriesAllVersions(int id, Date date)
			throws ServerException {
		ArrayList<Category> categories = new ArrayList<Category>();
		;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM CATEGORY C WHERE ID = ?  AND year(AppliedDateTimeUTC) = year(?)";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setInt(1, id);
			preparedStatement.setTimestamp(2, new Timestamp(date.getTime()));
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
		String name = rs.getString("name");
		String description = rs.getString("description");
		double amountPeso = rs.getDouble("amountPeso");
		double amountDollar = rs.getDouble("amountDollar");
		boolean isCurrencyDollar = rs.getBoolean("isCurrencyDollar");
		double typeExchange = rs.getDouble("typeExchange");
		int ivaTypeID = rs.getInt("iva_TypeId");
		Date appliedDateTimeUTC = rs.getTimestamp("appliedDateTimeUTC");
		int projectId = rs.getInt("projectid");
		int categoryType = rs.getInt("categoryType");
		boolean isRRhh = rs.getBoolean("isRRHH");
		int version = rs.getInt("version");
		Date updatedDateTimeUTC = rs.getTimestamp("updatedDateTimeUTC");

		Category category = new Category();
		category.setId(_id);
		category.setName(name);
		category.setDescription(description);
		category.setAmountPeso(amountPeso);
		category.setAmountDollar(amountDollar);
		category.setIsCurrencyDollar(isCurrencyDollar);
		category.setTypeExchange(typeExchange);
		category.setIvaTypeId(ivaTypeID);
		category.setIvaType(IVA_Type.getEnum(ivaTypeID));
		category.setAppliedDateTimeUTC(appliedDateTimeUTC);
		if (projectId != 0)
			category.setProject(new Project(projectId));
		category.setCategoryType(CategoryType.getEnum(categoryType));
		category.setIsRRHH(isRRhh);
		category.setVersion(version);
		category.setUpdatedDateTimeUTC(updatedDateTimeUTC);

		return category;
	}

	// Setea el dia en 1
	Date setFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 01);

		return cal.getTime();
	}
}
