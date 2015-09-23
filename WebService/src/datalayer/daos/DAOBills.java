package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.IVA_Type;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.businessEntity.UserType;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOBills;

public class DAOBills implements IDAOBills {

	private Connection connection;

	public DAOBills(Connection connection) {
		this.connection = connection;
	}

	@Override
	public int insert(Bill obj) throws ServerException {
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO BILL (CODE, DESCRIPTION, AMOUNTPESO, AMOUNTDOLLAR, ISCURRENCYDOLLAR, TYPEEXCHANGE, IVA_TYPEID, APPLIEDDATETIMEUTC, ISLIQUIDATED, PROJECTID) VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL);

			preparedStatement.setString(1, obj.getCode());
			preparedStatement.setString(2, obj.getDescription());
			preparedStatement.setDouble(3, obj.getAmountPeso());
			preparedStatement.setDouble(4, obj.getAmountDollar());
			preparedStatement.setBoolean(5, obj.getIsCurrencyDollar());
			preparedStatement.setDouble(6, obj.getTypeExchange());
			preparedStatement.setInt(7, obj.getIvaType().getValue());
			preparedStatement.setTimestamp(
					8,
					new Timestamp(setFirstDayOfMonth(
							obj.getAppliedDateTimeUTC()).getTime()));
			preparedStatement.setBoolean(9, false);
			if (obj.getProject() != null)
				preparedStatement.setInt(10, obj.getProject().getId());
			else
				preparedStatement.setNull(10, java.sql.Types.INTEGER);

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
			String deleteSQL = "DELETE FROM BILL WHERE ID = ?";
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
	public void deleteBills(int[] ids) throws ServerException {
		PreparedStatement preparedStatement = null;

		try {
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("(");

			int indexParameter = 0;
			while (indexParameter < ids.length) {
				strBuilder.append("?");
				strBuilder.append(",");
				indexParameter++;
			}
			strBuilder.append(")");
			strBuilder.replace(strBuilder.length() - 2,
					strBuilder.length() - 1, "");

			String deleteSQL = "DELETE FROM BILL WHERE ID IN "
					+ strBuilder.toString();
			preparedStatement = this.connection.prepareStatement(deleteSQL);

			int index = 1;
			for (int id : ids) {
				preparedStatement.setInt(index, id);
				index++;
			}

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
	public void update(int id, Bill obj) throws ServerException {
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE BILL " + "SET DESCRIPTION = ?, "
				+ "CODE = ?, " + "AMOUNTPESO = ?, " + "AMOUNTDOLLAR = ?, "
				+ "ISCURRENCYDOLLAR = ?, " + "TYPEEXCHANGE = ?, "  + "IVA_TYPEID = ?, "
				+ "APPLIEDDATETIMEUTC = ?, " + "PROJECTID = ? "
				+ "WHERE id = ?";

		try {
			preparedStatement = this.connection.prepareStatement(updateSQL);

			preparedStatement.setString(1, obj.getDescription());
			preparedStatement.setString(2, obj.getCode());
			preparedStatement.setDouble(3, obj.getAmountPeso());
			preparedStatement.setDouble(4, obj.getAmountDollar());
			preparedStatement.setBoolean(5, obj.getIsCurrencyDollar());
			preparedStatement.setDouble(6, obj.getTypeExchange());
			preparedStatement.setInt(7, obj.getIvaType().getValue());
			preparedStatement.setTimestamp(
					8,
					new Timestamp(setFirstDayOfMonth(
							obj.getAppliedDateTimeUTC()).getTime()));
			if (obj.getProject() != null)
				preparedStatement.setInt(9, obj.getProject().getId());
			else
				preparedStatement.setNull(9, java.sql.Types.INTEGER);
			preparedStatement.setInt(10, id);

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
	public Bill getObject(int id) throws ServerException {
		Bill bill = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM BILL WHERE id = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bill = BuildBill(rs);
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

		return bill;
	}

	@Override
	public ArrayList<Bill> getObjects() throws ServerException {
		ArrayList<Bill> bills = new ArrayList<Bill>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT B.*, P.Name as ProjectName ");
			sql.append("FROM BILL B ");
			sql.append("LEFT OUTER JOIN PROJECT P ON P.Id = B.ProjectId");
			preparedStatement = this.connection
					.prepareStatement(sql.toString());
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bills.add(BuildBill(rs));
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

		return bills;
	}

	@Override
	public Bill getBill(String code, int projectId) throws ServerException {
		Bill bill = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM BILL WHERE code = ? and projectId = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setString(1, code);
			preparedStatement.setInt(2, projectId);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bill = BuildBill(rs);
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

		return bill;
	}

	@Override
	public Bill getBill(String code) throws ServerException {
		Bill bill = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM BILL WHERE code = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setString(1, code);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				bill = BuildBill(rs);
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

		return bill;
	}

	@Override
	public ArrayList<Bill> getBills(Date from, Date to, boolean isLiquidated, User userContext)
			throws ServerException {
		ArrayList<Bill> bills = new ArrayList<Bill>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT B.*, P.Name as ProjectName, P.Closed as ProjectClosed FROM BILL B ");
			sql.append("INNER JOIN PROJECT P ON P.Id = B.ProjectId ");
			sql.append("WHERE B.AppliedDateTimeUTC between ? AND ? ");
			sql.append("AND B.IsLiquidated = ? ");
			if(userContext.getUserType() == UserType.MANAGER)
				sql.append("AND P.ManagerId = ? ");
			sql.append("ORDER BY B.AppliedDateTimeUTC DESC");

			preparedStatement = this.connection
					.prepareStatement(sql.toString());
			preparedStatement.setTimestamp(1,
					new Timestamp(setFirstDayOfMonth(from).getTime()));
			preparedStatement.setTimestamp(2,
					new Timestamp(setFirstDayOfMonth(to).getTime()));
			preparedStatement.setBoolean(3, isLiquidated);

			if(userContext.getUserType() == UserType.MANAGER)
				preparedStatement.setInt(4, userContext.getId());
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Bill bill = BuildBill(rs);
				if (bill.getProject() != null) {
					bill.getProject().setName(rs.getString("projectName"));
					bill.getProject().setClosed(rs.getBoolean("projectClosed"));
				}
				bills.add(bill);
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

		return bills;
	}

	@Override
	public ArrayList<Bill> getBills(Date from, Date to, boolean isLiquidated,
			boolean withCharges, User userContext) throws ServerException {
		ArrayList<Bill> bills = new ArrayList<Bill>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT B.*, P.Name as ProjectName, P.Closed as ProjectClosed FROM BILL B ");
			sql.append("INNER JOIN PROJECT P ON P.Id = B.ProjectId ");
			sql.append("WHERE B.AppliedDateTimeUTC between ? AND ? ");
			sql.append("AND B.IsLiquidated = ? ");
			if (withCharges)
				sql.append("AND B.Id IN (SELECT BillId FROM CHARGE) ");
			else
				sql.append("AND B.Id NOT IN (SELECT BillId FROM CHARGE) ");
			
			if(userContext.getUserType() == UserType.MANAGER)
				sql.append("AND P.ManagerId = ? ");
			
			sql.append("ORDER BY B.AppliedDateTimeUTC DESC");

			preparedStatement = this.connection
					.prepareStatement(sql.toString());
			preparedStatement.setTimestamp(1,
					new Timestamp(setFirstDayOfMonth(from).getTime()));
			preparedStatement.setTimestamp(2,
					new Timestamp(setFirstDayOfMonth(to).getTime()));
			preparedStatement.setBoolean(3, isLiquidated);

			if(userContext.getUserType() == UserType.MANAGER)
				preparedStatement.setInt(4, userContext.getId());
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Bill bill = BuildBill(rs);
				if (bill.getProject() != null) {
					bill.getProject().setName(rs.getString("projectName"));
					bill.getProject().setClosed(rs.getBoolean("projectClosed"));
				}
				bills.add(bill);
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

		return bills;
	}

	@Override
	public ArrayList<Bill> getBills(Date from, Date to, User userContext) throws ServerException {
		ArrayList<Bill> bills = new ArrayList<Bill>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT B.*, P.Name as ProjectName, P.Closed as ProjectClosed FROM BILL B ");
			sql.append("INNER JOIN PROJECT P ON P.Id = B.ProjectId ");
			sql.append("WHERE B.AppliedDateTimeUTC between ? AND ? ");
			if(userContext.getUserType() == UserType.MANAGER)
				sql.append("AND P.ManagerId = ? ");
			sql.append("ORDER BY B.AppliedDateTimeUTC DESC");

			preparedStatement = this.connection
					.prepareStatement(sql.toString());
			preparedStatement.setTimestamp(1,
					new Timestamp(setFirstDayOfMonth(from).getTime()));
			preparedStatement.setTimestamp(2,
					new Timestamp(setFirstDayOfMonth(to).getTime()));

			if(userContext.getUserType() == UserType.MANAGER)
				preparedStatement.setInt(3, userContext.getId());
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Bill bill = BuildBill(rs);
				if (bill.getProject() != null) {
					bill.getProject().setName(rs.getString("projectName"));
					bill.getProject().setClosed(rs.getBoolean("projectClosed"));
				}
				bills.add(bill);
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

		return bills;
	}
	
	@Override
	public ArrayList<Bill> getBillsWithCharges(Date from, Date to, User userContext) throws ServerException {
		ArrayList<Bill> bills = new ArrayList<Bill>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT B.*, P.Name as ProjectName, P.Closed as ProjectClosed FROM BILL B ");
			sql.append("INNER JOIN PROJECT P ON P.Id = B.ProjectId ");
			sql.append("WHERE B.AppliedDateTimeUTC between ? AND ? ");
			sql.append("AND B.Id IN (SELECT BillId FROM CHARGE) ");

			if(userContext.getUserType() == UserType.MANAGER)
				sql.append("AND P.ManagerId = ? ");

			sql.append("ORDER BY B.AppliedDateTimeUTC DESC");
			
			preparedStatement = this.connection
					.prepareStatement(sql.toString());
			preparedStatement.setTimestamp(1,
					new Timestamp(setFirstDayOfMonth(from).getTime()));
			preparedStatement.setTimestamp(2,
					new Timestamp(setFirstDayOfMonth(to).getTime()));

			if(userContext.getUserType() == UserType.MANAGER)
				preparedStatement.setInt(3, userContext.getId());
			
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Bill bill = BuildBill(rs);
				if (bill.getProject() != null) {
					bill.getProject().setName(rs.getString("projectName"));
					bill.getProject().setClosed(rs.getBoolean("projectClosed"));
				}
				bills.add(bill);
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

		return bills;
	}

	@Override
	public ArrayList<Bill> getBills(int projectId) throws ServerException {
		ArrayList<Bill> bills = new ArrayList<Bill>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT B.*, P.Name as ProjectName FROM BILL B ");
			sql.append("INNER JOIN PROJECT P ON P.Id = B.ProjectId ");
			sql.append("WHERE B.projectId = ? ");
			sql.append("ORDER BY B.AppliedDateTimeUTC DESC");

			int index = 1;
			preparedStatement = this.connection
					.prepareStatement(sql.toString());

			preparedStatement.setInt(index, projectId);
			index++;

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Bill bill = BuildBill(rs);
				if (bill.getProject() != null)
					bill.getProject().setName(rs.getString("projectName"));
				bills.add(bill);
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

		return bills;
	}

	Date setFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 01);

		return cal.getTime();
	}

	private Bill BuildBill(ResultSet rs) throws SQLException {
		int _id = rs.getInt("id");
		String code = rs.getString("code");
		String description = rs.getString("description");
		double amountPeso = rs.getDouble("amountPeso");
		double amountDollar = rs.getDouble("amountDollar");
		boolean isCurrencyDollar = rs.getBoolean("isCurrencyDollar");
		double typeExchange = rs.getDouble("typeExchange");
		int ivaType = rs.getInt("iva_TypeId");
		Date appliedDateTimeUTC = rs.getTimestamp("appliedDateTimeUTC");
		boolean isLiquidated = rs.getBoolean("isLiquidated");
		int projectId = rs.getInt("projectid");

		Bill bill = new Bill();
		bill.setId(_id);
		bill.setCode(code);
		bill.setDescription(description);
		bill.setIsCurrencyDollar(isCurrencyDollar);
		bill.setTypeExchange(typeExchange);
		bill.setIvaType(IVA_Type.getEnum(ivaType));
		bill.setAppliedDateTimeUTC(appliedDateTimeUTC);
		bill.setIsLiquidated(isLiquidated);
		if (projectId != 0)
			bill.setProject(new Project(projectId));

		if (bill.getIsCurrencyDollar()) {
			bill.setAmountDollar(amountDollar);
			bill.setTypeExchange(typeExchange);
		} else
			bill.setAmountPeso(amountPeso);

		return bill;
	}

	@Override
	public boolean liquidateBill(int billId) throws ServerException {
		boolean result = false;
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE BILL SET ISLIQUIDATED = 1 WHERE ID = ?";

		try {
			preparedStatement = this.connection.prepareStatement(updateSQL);

			preparedStatement.setInt(1, billId);			
			preparedStatement.executeUpdate();
			
			result = true;

		} catch (SQLException e) {
			throw new ServerException(e);
		}
		return result;
	}
}
