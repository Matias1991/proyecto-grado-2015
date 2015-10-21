package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.ProjectLiquidation;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOProjectsLiquidations;

import com.mysql.jdbc.Statement;

public class DAOProjectsLiquidations implements IDAOProjectsLiquidations {

	private Connection connection;

	public DAOProjectsLiquidations(Connection connection) {
		this.connection = connection;
	}

	@Override
	public void delete(int id) throws ServerException {

	}

	@Override
	public void update(int id, ProjectLiquidation obj) throws ServerException {

	}

	@Override
	public boolean exist(int id) throws ServerException {

		return false;
	}

	@Override
	public ProjectLiquidation getObject(int id) throws ServerException {

		return null;
	}

	@Override
	public ArrayList<ProjectLiquidation> getObjects() throws ServerException {

		return null;
	}

	@Override
	public int insert(ProjectLiquidation obj) throws ServerException {
		int newLiquidationId = -1;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO PROJECTLIQUIDATION (PROJECTID, TOTALBILLS, "
				+ "OUTSOURCEDCOST , CATEGORIESCOST, EMPLOYEESCOST, EARNING, RESERVE, SELLINGCOST, "
				+ "PARTNER1ID, PARTNER1EARNING, PARTNER2ID, PARTNER2EARNING, ISCURRENCYDOLLAR, "
				+ "APPLIEDDATETIMEUTC, CREATEDDATETIMEUTC) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, obj.getProject().getId());
			preparedStatement.setDouble(2, obj.getTotalBills());
			preparedStatement.setDouble(3, obj.getTotalCostCategoriesHuman());
			preparedStatement
					.setDouble(4, obj.getTotalCostCategoriesMaterial());
			preparedStatement.setDouble(5, obj.getTotalCostEmployees());
			preparedStatement.setDouble(6, obj.getEarnings());
			preparedStatement.setDouble(7, obj.getReserve());
			preparedStatement.setDouble(8, obj.getSale());
			preparedStatement.setDouble(9, obj.getPartner1().getEmployed()
					.getId());
			preparedStatement.setDouble(10, obj.getPartner1Earning());
			preparedStatement.setDouble(11, obj.getPartner2().getEmployed()
					.getId());
			preparedStatement.setDouble(12, obj.getPartner2Earning());
			preparedStatement.setBoolean(13, obj.isCurrencyDollar());
			preparedStatement.setTimestamp(14, new Timestamp(obj
					.getAppliedDateTimeUTC().getTime()));
			preparedStatement.setTimestamp(15, new Timestamp(obj
					.getCreatedDateTimeUTC().getTime()));

			preparedStatement.executeUpdate();

			try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					newLiquidationId = generatedKeys.getInt(1);
				}
			}

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

		return newLiquidationId;
	}

	@Override
	public boolean existLiquidation(Date appliedDate) throws ServerException {
		boolean exist = false;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String getSQL = "SELECT id FROM PROJECTLIQUIDATION WHERE AppliedDateTimeUTC = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setTimestamp(1,
					new Timestamp(appliedDate.getTime()));
			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				exist = true;
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

		return exist;
	}

	@Override
	public ArrayList<ProjectLiquidation> getProjectsLiquidationsByDate(
			Date appliedDate) throws ServerException {
		ArrayList<ProjectLiquidation> projectsLiquidations = new ArrayList<ProjectLiquidation>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String getSQL = "SELECT * FROM PROJECTLIQUIDATION WHERE AppliedDateTimeUTC = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setTimestamp(1,
					new Timestamp(appliedDate.getTime()));

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ProjectLiquidation projectLiquidation = buildProjectLiquidation(rs);
				projectsLiquidations.add(projectLiquidation);
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

		return projectsLiquidations;

	}

	@Override
	public ProjectLiquidation getProjectLiquidationByDate(Date appliedDate,
			int projectId) throws ServerException {
		ProjectLiquidation projectLiquidation = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String getSQL = "SELECT * FROM PROJECTLIQUIDATION WHERE APPLIEDDATETIMEUTC = ? AND PROJECTID = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setTimestamp(1,
					new Timestamp(appliedDate.getTime()));
			preparedStatement.setInt(2, projectId);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				projectLiquidation = buildProjectLiquidation(rs);
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

		return projectLiquidation;

	}

	@Override
	public ArrayList<ProjectLiquidation> getProjectsWithMoreEarnings(Date from,
			Date to, boolean isCurrencyDollar, int count)
			throws ServerException {
		ArrayList<ProjectLiquidation> projectsLiquidations = new ArrayList<ProjectLiquidation>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder strBuilder = new StringBuilder();
			strBuilder
					.append("Select PL.*, P.Name from ProjectLiquidation PL ");
			strBuilder.append("INNER JOIN PROJECT P ON P.ID = PL.ProjectId ");
			strBuilder.append("WHERE appliedDateTimeUTC between ? and ? ");
			strBuilder.append("AND PL.isCurrencyDollar = ? ");
			strBuilder.append("GROUP BY PL.projectId ");
			strBuilder.append("ORDER BY Earning DESC ");
			strBuilder.append("LIMIT ?");

			preparedStatement = this.connection.prepareStatement(strBuilder
					.toString());

			preparedStatement.setTimestamp(1, new Timestamp(from.getTime()));
			preparedStatement.setTimestamp(2, new Timestamp(to.getTime()));
			preparedStatement.setBoolean(3, isCurrencyDollar);
			preparedStatement.setInt(4, count);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ProjectLiquidation projectLiquidation = new ProjectLiquidation();
				Project project = new Project(rs.getInt("projectId"));
				project.setName(rs.getString("name"));
				projectLiquidation.setProject(project);
				double earning = rs.getDouble("earning");
				projectLiquidation.setEarnings(earning);
				double reserve = rs.getDouble("reserve");
				projectLiquidation.setReserve(reserve);

				projectsLiquidations.add(projectLiquidation);
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

		return projectsLiquidations;

	}

	@Override
	public ArrayList<ProjectLiquidation> getProjectLiquidations(int projectId,
			Date date, boolean isCurrencyDollar) throws ServerException {
		ArrayList<ProjectLiquidation> projectsLiquidations = new ArrayList<ProjectLiquidation>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("Select PL.* from ProjectLiquidation PL ");
			strBuilder.append("WHERE PL.projectId = ? ");
			strBuilder.append("AND PL.isCurrencyDollar = ? ");
			strBuilder.append("AND year(pl.AppliedDateTimeUTC) = year(?) ");

			preparedStatement = this.connection.prepareStatement(strBuilder
					.toString());

			preparedStatement.setInt(1, projectId);
			preparedStatement.setBoolean(2, isCurrencyDollar);
			preparedStatement.setTimestamp(3, new Timestamp(date.getTime()));
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ProjectLiquidation projectLiquidation = buildProjectLiquidation(rs);
				projectsLiquidations.add(projectLiquidation);
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

		return projectsLiquidations;

	}

	private ProjectLiquidation buildProjectLiquidation(ResultSet rs)
			throws SQLException {
		int _id = rs.getInt("id");
		Project project = new Project(rs.getInt("projectId"));
		double totalBills = rs.getDouble("totalBills");
		double outsourcedCost = rs.getDouble("outsourcedCost");
		double categoriesCost = rs.getDouble("categoriesCost");
		double employeesCost = rs.getDouble("employeesCost");
		double earnings = rs.getDouble("earning");
		double reserve = rs.getDouble("reserve");
		double sellingCost = rs.getDouble("sellingCost");
		Employed partner1 = new Employed(rs.getInt("partner1Id"));
		double partner1Earnings = rs.getDouble("partner1Earning");
		Employed partner2 = new Employed(rs.getInt("partner2Id"));
		double partner2Earnings = rs.getDouble("partner2Earning");
		boolean isCurrencyDollar = rs.getBoolean("isCurrencyDollar");
		Date appliedDateTimeUTC = rs.getTimestamp("appliedDateTimeUTC");
		Date createdDateTimeUTC = rs.getTimestamp("createdDateTimeUTC");

		ProjectLiquidation projectLiquidation = new ProjectLiquidation();

		projectLiquidation.setAppliedDateTimeUTC(appliedDateTimeUTC);
		projectLiquidation.setTotalBills(totalBills);
		projectLiquidation.setCreatedDateTimeUTC(createdDateTimeUTC);
		projectLiquidation.setCurrencyDollar(isCurrencyDollar);
		projectLiquidation.setId(_id);
		projectLiquidation.setEmployedPartner1(partner1);
		projectLiquidation.setPartner1Earning(partner1Earnings);
		projectLiquidation.setEmployedPartner2(partner2);
		projectLiquidation.setPartner2Earning(partner2Earnings);
		projectLiquidation.setProject(project);
		projectLiquidation.setReserve(reserve);
		projectLiquidation.setSale(sellingCost);
		projectLiquidation.setTotalCostCategoriesHuman(outsourcedCost);
		projectLiquidation.setTotalCostCategoriesMaterial(categoriesCost);
		projectLiquidation.setTotalCostEmployees(employeesCost);
		projectLiquidation.setEarnings(earnings);

		return projectLiquidation;
	}

}
