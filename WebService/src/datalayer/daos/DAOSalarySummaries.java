package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import servicelayer.entity.businessEntity.SalarySummary;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOSalarySummaries;

public class DAOSalarySummaries implements IDAOSalarySummaries {

	private Connection connection;

	public DAOSalarySummaries() {
	}

	public DAOSalarySummaries(Connection connection) {
		this.connection = connection;
	}

	@Override
	public int insert(int employedId, SalarySummary salarySummary)
			throws ServerException {
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO SALARYSUMMARY (version, employedId, nominalSalary, tickets,"
				+ " personalRetirementContribution, employersContributionsRetirement, personalFONASAContribution, employersFONASAContribution, personalFRLContribution,"
				+ " employersFRLContribution, IRPF, ticketsEmployers, BSE, totalDiscounts, totalEmployerContributions, nominalWithoutContributions,"
				+ " dismissalPrevention, incidenceSalary, incidenceTickets, RET, salaryToPay, costMonth, costRealHour, costSaleHour, hours, createdDateTimeUTC) VALUES"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL);

			int newVersion = getLatestVersion(employedId) + 1;

			preparedStatement.setInt(1, newVersion);
			preparedStatement.setInt(2, employedId);
			preparedStatement.setDouble(3, salarySummary.getNominalSalary());
			preparedStatement.setDouble(4, salarySummary.getTickets());
			preparedStatement.setDouble(5,
					salarySummary.getPersonalRetirementContribution());
			preparedStatement.setDouble(6,
					salarySummary.getEmployersContributionsRetirement());
			preparedStatement.setDouble(7,
					salarySummary.getPersonalFONASAContribution());
			preparedStatement.setDouble(8,
					salarySummary.getEmployersFONASAContribution());
			preparedStatement.setDouble(9,
					salarySummary.getPersonalFRLContribution());
			preparedStatement.setDouble(10,
					salarySummary.getEmployersFRLContribution());
			preparedStatement.setDouble(11, salarySummary.getiRPF());
			preparedStatement
					.setDouble(12, salarySummary.getTicketsEmployers());
			preparedStatement.setDouble(13, salarySummary.getbSE());
			preparedStatement.setDouble(14, salarySummary.getTotalDiscounts());
			preparedStatement.setDouble(15,
					salarySummary.getTotalEmployerContributions());
			preparedStatement.setDouble(16,
					salarySummary.getNominalWithoutContributions());
			preparedStatement.setDouble(17,
					salarySummary.getDismissalPrevention());
			preparedStatement.setDouble(18, salarySummary.getIncidenceSalary());
			preparedStatement
					.setDouble(19, salarySummary.getIncidenceTickets());
			preparedStatement.setDouble(20, salarySummary.getrET());
			preparedStatement.setDouble(21, salarySummary.getSalaryToPay());
			preparedStatement.setDouble(22, salarySummary.getCostMonth());
			preparedStatement.setDouble(23, salarySummary.getCostRealHour());
			preparedStatement.setDouble(24, salarySummary.getCostSaleHour());
			preparedStatement.setDouble(25, salarySummary.getHours());
			preparedStatement.setTimestamp(26, new Timestamp(salarySummary
					.getCreatedDateTimeUTC().getTime()));

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
	public int update(int employedId, SalarySummary salarySummary)
			throws ServerException {
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE SALARYSUMMARY SET"
				// + " employedId = ?,"
				+ " nominalSalary =?," + " tickets = ?,"
				+ " personalRetirementContribution =?,"
				+ " employersContributionsRetirement =?,"
				+ " personalFONASAContribution =?,"
				+ " employersFONASAContribution =?,"
				+ " personalFRLContribution =?,"
				+ " employersFRLContribution =?," + " IRPF =?,"
				+ " ticketsEmployers =?," + " BSE =?," + " totalDiscounts =?,"
				+ " totalEmployerContributions =?,"
				+ " nominalWithoutContributions =?,"
				+ " dismissalPrevention =?," + " incidenceSalary =?,"
				+ " incidenceTickets =?," + " RET =?," + " salaryToPay =?,"
				+ " costMonth =?," + " costRealHour =?," + " costSaleHour =?,"
				+ " hours =?," + " createdDateTimeUTC =?"
				+ " WHERE id = ? AND version = ?";

		try {
			preparedStatement = this.connection.prepareStatement(updateSQL);

			// preparedStatement.setInt(1, employedId);
			preparedStatement.setDouble(1, salarySummary.getNominalSalary());
			preparedStatement.setDouble(2, salarySummary.getTickets());
			preparedStatement.setDouble(3,
					salarySummary.getPersonalRetirementContribution());
			preparedStatement.setDouble(4,
					salarySummary.getEmployersContributionsRetirement());
			preparedStatement.setDouble(5,
					salarySummary.getPersonalFONASAContribution());
			preparedStatement.setDouble(6,
					salarySummary.getEmployersFONASAContribution());
			preparedStatement.setDouble(7,
					salarySummary.getPersonalFRLContribution());
			preparedStatement.setDouble(8,
					salarySummary.getEmployersFRLContribution());
			preparedStatement.setDouble(9, salarySummary.getiRPF());
			preparedStatement
					.setDouble(10, salarySummary.getTicketsEmployers());
			preparedStatement.setDouble(11, salarySummary.getbSE());
			preparedStatement.setDouble(12, salarySummary.getTotalDiscounts());
			preparedStatement.setDouble(13,
					salarySummary.getTotalEmployerContributions());
			preparedStatement.setDouble(14,
					salarySummary.getNominalWithoutContributions());
			preparedStatement.setDouble(15,
					salarySummary.getDismissalPrevention());
			preparedStatement.setDouble(16, salarySummary.getIncidenceSalary());
			preparedStatement
					.setDouble(17, salarySummary.getIncidenceTickets());
			preparedStatement.setDouble(18, salarySummary.getrET());
			preparedStatement.setDouble(19, salarySummary.getSalaryToPay());
			preparedStatement.setDouble(20, salarySummary.getCostMonth());
			preparedStatement.setDouble(21, salarySummary.getCostRealHour());
			preparedStatement.setDouble(22, salarySummary.getCostSaleHour());
			preparedStatement.setDouble(23, salarySummary.getHours());
			preparedStatement.setTimestamp(24, new Timestamp(salarySummary
					.getCreatedDateTimeUTC().getTime()));

			preparedStatement.setInt(25, salarySummary.getId());
			preparedStatement.setInt(26, getLatestVersion(employedId));

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
	public void delete(int employedId, int salarySummaryId)
			throws ServerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteSalarySummaries(int employedId) throws ServerException {
		PreparedStatement preparedStatement = null;

		try {

			String deleteSQL = "DELETE FROM SALARYSUMMARY WHERE EMPLOYEDID = ?";
			preparedStatement = this.connection.prepareStatement(deleteSQL);
			preparedStatement.setInt(1, employedId);
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
	public SalarySummary getLatestVersionSalarySummary(int employedId)
			throws ServerException {

		SalarySummary salarySummary = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String getSQL = "SELECT * FROM SALARYSUMMARY WHERE EMPLOYEDID = ? and VERSION = ?";

		try {
			preparedStatement = this.connection.prepareStatement(getSQL);

			int latestVersion = getLatestVersion(employedId);
			preparedStatement.setInt(1, employedId);
			preparedStatement.setInt(2, latestVersion);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				salarySummary = BuildSalarySummary(rs);
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

		return salarySummary;
	}

	@Override
	public ArrayList<SalarySummary> getSalarySummaries(int employedId)
			throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Integer> getALLVersionsSalarySummary(int employedId)
			throws ServerException {

		ArrayList<Integer> list = new ArrayList<Integer>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String getSQL = "SELECT VERSION FROM SALARYSUMMARY WHERE EMPLOYEDID = ?  ORDER BY VERSION DESC";

		try {
			preparedStatement = this.connection.prepareStatement(getSQL);

			preparedStatement.setInt(1, employedId);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				list.add(rs.getInt("version"));
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

		return list;
	}

	@Override
	public SalarySummary getSalarySummaryByVersion(int employedId, int version)
			throws ServerException {

		SalarySummary salarySummary = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String getSQL = "SELECT * FROM SALARYSUMMARY WHERE EMPLOYEDID = ? and VERSION = ?";

		try {
			preparedStatement = this.connection.prepareStatement(getSQL);

			preparedStatement.setInt(1, employedId);
			preparedStatement.setInt(2, version);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				salarySummary = BuildSalarySummary(rs);
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

		return salarySummary;
	}

	int getLatestVersion(int employedId) throws ServerException {
		int latestVersion = -1;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String insertSQL = "SELECT MAX(VERSION) AS MAX_VERSION FROM SALARYSUMMARY WHERE EMPLOYEDID = ?";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL);

			preparedStatement.setInt(1, employedId);

			rs = preparedStatement.executeQuery();

			if (rs.next()) {
				latestVersion = rs.getInt("MAX_VERSION");
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

		return latestVersion;
	}

	SalarySummary BuildSalarySummary(ResultSet rs) throws SQLException {
		int _id = rs.getInt("id");
		int version = rs.getInt("version");
		double nominalSalary = rs.getDouble("nominalSalary");
		double tickets = rs.getDouble("tickets");
		double personalRetirementContribution = rs
				.getDouble("personalRetirementContribution");
		double employersContributionsRetirement = rs
				.getDouble("employersContributionsRetirement");
		double personalFONASAContribution = rs
				.getDouble("personalFONASAContribution");
		double employersFONASAContribution = rs
				.getDouble("employersFONASAContribution");
		double personalFRLContribution = rs
				.getDouble("personalFRLContribution");
		double employersFRLContribution = rs
				.getDouble("employersFRLContribution");
		double iRPF = rs.getDouble("iRPF");
		double ticketsEmployers = rs.getDouble("ticketsEmployers");
		double bSE = rs.getDouble("bSE");
		double totalDiscounts = rs.getDouble("totalDiscounts");
		double totalEmployerContributions = rs
				.getDouble("totalEmployerContributions");
		double nominalWithoutContributions = rs
				.getDouble("nominalWithoutContributions");
		double dismissalPrevention = rs.getDouble("dismissalPrevention");
		double incidenceSalary = rs.getDouble("incidenceSalary");
		double incidenceTickets = rs.getDouble("incidenceTickets");
		double rET = rs.getDouble("rET");
		double salaryToPay = rs.getDouble("salaryToPay");
		double costMonth = rs.getDouble("costMonth");
		double costRealHour = rs.getDouble("costRealHour");
		double costSaleHour = rs.getDouble("costSaleHour");
		int hours = rs.getInt("hours");
		Date createdDateTimeUTC = rs.getDate("createdDateTimeUTC");

		SalarySummary salarySummary = new SalarySummary();
		salarySummary.setId(_id);
		salarySummary.setVersion(version);
		salarySummary.setNominalSalary(nominalSalary);
		salarySummary.setTickets(tickets);
		salarySummary
				.setPersonalRetirementContribution(personalRetirementContribution);
		salarySummary
				.setEmployersContributionsRetirement(employersContributionsRetirement);
		salarySummary.setPersonalFONASAContribution(personalFONASAContribution);
		salarySummary
				.setEmployersFONASAContribution(employersFONASAContribution);
		salarySummary.setPersonalFRLContribution(personalFRLContribution);
		salarySummary.setEmployersFRLContribution(employersFRLContribution);
		salarySummary.setiRPF(iRPF);
		salarySummary.setTicketsEmployers(ticketsEmployers);
		salarySummary.setbSE(bSE);
		salarySummary.setTotalDiscounts(totalDiscounts);
		salarySummary.setTotalEmployerContributions(totalEmployerContributions);
		salarySummary
				.setNominalWithoutContributions(nominalWithoutContributions);
		salarySummary.setDismissalPrevention(dismissalPrevention);
		salarySummary.setIncidenceSalary(incidenceSalary);
		salarySummary.setIncidenceTickets(incidenceTickets);
		salarySummary.setrET(rET);
		salarySummary.setSalaryToPay(salaryToPay);
		salarySummary.setCostMonth(costMonth);
		salarySummary.setCostRealHour(costRealHour);
		salarySummary.setCostSaleHour(costSaleHour);
		salarySummary.setHours(hours);
		salarySummary.setCreatedDateTimeUTC(createdDateTimeUTC);

		return salarySummary;
	}

	@Override
	public ArrayList<SalarySummary> getAllVersionsDateSalarySummary(
			int employedId) throws ServerException {
		ArrayList<SalarySummary> list = new ArrayList<SalarySummary>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		String getSQL = "SELECT VERSION, CREATEDDATETIMEUTC FROM SALARYSUMMARY WHERE EMPLOYEDID = ?  ORDER BY CREATEDDATETIMEUTC DESC";

		try {
			preparedStatement = this.connection.prepareStatement(getSQL);

			preparedStatement.setInt(1, employedId);

			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				SalarySummary aux = new SalarySummary();
				aux.setCreatedDateTimeUTC(rs.getDate("createdDateTimeUTC"));
				aux.setVersion(rs.getInt("version"));
				list.add(aux);
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

		return list;

	}

}
