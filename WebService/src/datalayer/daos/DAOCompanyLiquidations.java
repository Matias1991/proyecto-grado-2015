package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.CompanyLiquidation;
import servicelayer.entity.businessEntity.Employed;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOCompanyLiquidations;

public class DAOCompanyLiquidations implements IDAOCompanyLiquidations {
	
	private Connection connection;
	
	public DAOCompanyLiquidations(Connection connection){
		this.connection = connection;
	}

	@Override
	public int insert(CompanyLiquidation obj) throws ServerException {
		int newCompanyLiquidationId = -1;
		PreparedStatement preparedStatement = null;
		
		String insertSQL = "INSERT INTO COMPANYLIQUIDATION (COMPANYCATEGORY, CONTRIBUTION, "
				+ "SALARYNOTPARTNERS, IRAE, IVASALE, IVAPURCHASE, PARTNER1ID, PARTNER1EARNINGSDOLLAR, "
				+ "PARTNER1EARNINGSPESO, PARTNER2ID, PARTNER2EARNINGSDOLLAR, PARTNER2EARNINGSPESO, "
				+ "SALARYPARTNERS, DISMISSALPREVENTION, INCIDENCESALARY, INCIDENCETICKETS, EMPLOYEESCOST, "
				+ "TOTALEARNINGSPESO, TOTALEARNINGSDOLLAR, TYPEEXCHANGE, APPLIEDDATETIMEUTC, CREATEDDATETIMEUTC) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		try{
			preparedStatement = this.connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
			
			preparedStatement.setDouble(1,obj.getCompanyCategory());
			preparedStatement.setDouble(2,obj.getContribution());
			preparedStatement.setDouble(3, obj.getSalaryNotPartners());
			preparedStatement.setDouble(4, obj.getIrae());
			preparedStatement.setDouble(5, obj.getIVASale());
			preparedStatement.setDouble(6, obj.getIVAPurchase());
			preparedStatement.setInt(7, obj.getPartner1().getId());
			preparedStatement.setDouble(8, obj.getPartner1EarningsDollar());
			preparedStatement.setDouble(9, obj.getPartner1EarningsPeso());
			preparedStatement.setInt(10, obj.getPartner2().getId());
			preparedStatement.setDouble(11, obj.getPartner2EarningsDollar());
			preparedStatement.setDouble(12, obj.getPartner2EarningsPeso());
			preparedStatement.setDouble(13, obj.getSalaryPartners());
			preparedStatement.setDouble(14, obj.getDismissalPrevention());
			preparedStatement.setDouble(15, obj.getIncidenceSalary());
			preparedStatement.setDouble(16, obj.getIncidenceTickets());
			preparedStatement.setDouble(17, obj.getEmployeesCost());
			preparedStatement.setDouble(18, obj.getTotalEarningsPeso());
			preparedStatement.setDouble(19, obj.getTotalEarningsDollar());			
			preparedStatement.setDouble(20, obj.getTypeExchange());
			preparedStatement.setTimestamp(21, new Timestamp(obj.getAppliedDateTimeUTC().getTime()));
			preparedStatement.setTimestamp(22, new Timestamp(obj.getCreatedDateTimeUTC().getTime()));
			
			preparedStatement.executeUpdate();
			
			try(ResultSet generatedKeys = preparedStatement.getGeneratedKeys()){
				if(generatedKeys.next()){
					newCompanyLiquidationId = generatedKeys.getInt(1);
				}
			}
		}catch (SQLException e){
			throw new ServerException(e);
		}finally{
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					LoggerMSMP.setLog(e.getMessage());
				}
			}
		}
		
		return newCompanyLiquidationId;			
	}
	

	@Override
	public void delete(int id) throws ServerException {
		
	}

	@Override
	public void update(int id, CompanyLiquidation obj) throws ServerException {
		
		
	}

	@Override
	public boolean exist(int id) throws ServerException {
		
		return false;
	}

	@Override
	public CompanyLiquidation getObject(int id) throws ServerException {
		
		return null;
	}

	@Override
	public ArrayList<CompanyLiquidation> getObjects() throws ServerException {
		
		return null;
	}

	@Override
	public boolean existLiquidation(Date appliedDate) throws ServerException {
		boolean exist = false;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String getSQL = "SELECT id FROM COMPANYLIQUIDATION WHERE AppliedDateTimeUTC = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setTimestamp(1, new Timestamp(appliedDate.getTime()));
			rs = preparedStatement.executeQuery();
			
			if(rs.next()){
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
	public CompanyLiquidation getCompanyLiquidationByDate(Date appliedDate) throws ServerException{
		CompanyLiquidation companyLiquidation = null;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String getSQL = "SELECT * FROM COMPANYLIQUIDATION WHERE AppliedDateTimeUTC = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setTimestamp(1, new Timestamp(appliedDate.getTime()));
			rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				companyLiquidation = buildCompanyLiquidation(rs);
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

		return companyLiquidation;	
	}
	
	@Override
	public ArrayList<CompanyLiquidation> getCompanyLiquidations(Date date) throws ServerException{
		ArrayList<CompanyLiquidation> companyLiquidations = new ArrayList<CompanyLiquidation>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			
			StringBuilder strBuilder = new StringBuilder();
			strBuilder.append("Select * from CompanyLiquidation ");
			strBuilder.append("WHERE year(AppliedDateTimeUTC) = year(?) ");

			preparedStatement = this.connection.prepareStatement(strBuilder.toString());
			
			preparedStatement.setTimestamp(1, new Timestamp(date.getTime()));
			rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				companyLiquidations.add(buildCompanyLiquidation(rs));
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

		return companyLiquidations;	
	}
	
	@Override
	public double getTypeExchange(Date appliedDate) throws ServerException {
		double typeExchange = 0.0;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String getSQL = "SELECT typeExchange FROM COMPANYLIQUIDATION WHERE AppliedDateTimeUTC = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setTimestamp(1, new Timestamp(appliedDate.getTime()));
			rs = preparedStatement.executeQuery();
			
			if(rs.next()){
				typeExchange = rs.getDouble("typeExchange");
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

		return typeExchange;
	}

	private CompanyLiquidation buildCompanyLiquidation(ResultSet rs) throws SQLException {
		int _id = rs.getInt("id");
		double companyCategory = rs.getDouble("companyCategory");
		double contribution = rs.getDouble("contribution");
		double salaryNotPartners = rs.getDouble("salaryNotPartners");
		double irae = rs.getDouble("irae");
		double ivaSale = rs.getDouble("ivaSale");
		double ivaPurchase = rs.getDouble("ivaPurchase");
		Employed partner1 = new Employed(rs.getInt("partner1Id"));
		double partner1EarningsDollar = rs.getDouble("partner1EarningsDollar");
		double partner1EarningsPeso = rs.getDouble("partner1EarningsPeso");
		Employed partner2 = new Employed(rs.getInt("partner2Id"));
		double partner2EarningsDollar = rs.getDouble("partner2EarningsDollar");
		double partner2EarningsPeso = rs.getDouble("partner2EarningsPeso");
		double typeExchange = rs.getDouble("typeExchange");
		Date appliedDateTimeUTC = rs.getTimestamp("appliedDateTimeUTC");
		Date createdDateTimeUTC = rs.getTimestamp("createdDateTimeUTC");
		double dismissalPrevention = rs.getDouble("dismissalPrevention");
		double employeesCost = rs.getDouble("employeesCost");
		double incidenceSalary = rs.getDouble("incidenceSalary");
		double incidenceTickets = rs.getDouble("incidenceTickets");		
		double salaryPartners = rs.getDouble("salaryPartners");
		double totalEarningsDollar = rs.getDouble("totalEarningsDollar");
		double totalEarningsPeso = rs.getDouble("totalEarningsPeso");
		
		CompanyLiquidation companyLiquidation = new CompanyLiquidation();
		companyLiquidation.setId(_id);
		companyLiquidation.setCompanyCategory(companyCategory);
		companyLiquidation.setContribution(contribution);
		companyLiquidation.setSalaryNotPartners(salaryNotPartners);
		companyLiquidation.setIrae(irae);
		companyLiquidation.setIVASale(ivaSale);
		companyLiquidation.setIVAPurchase(ivaPurchase);
		companyLiquidation.setPartner1(partner1);
		companyLiquidation.setPartner1EarningsDollar(partner1EarningsDollar);
		companyLiquidation.setPartner1EarningsPeso(partner1EarningsPeso);
		companyLiquidation.setPartner2(partner2);
		companyLiquidation.setPartner2EarningsDollar(partner2EarningsDollar);
		companyLiquidation.setPartner2EarningsPeso(partner2EarningsPeso);
		companyLiquidation.setTypeExchange(typeExchange);
		companyLiquidation.setAppliedDateTimeUTC(appliedDateTimeUTC);
		companyLiquidation.setCreatedDateTimeUTC(createdDateTimeUTC);
		companyLiquidation.setEmployeesCost(employeesCost);
		companyLiquidation.setDismissalPrevention(dismissalPrevention);
		companyLiquidation.setIncidenceSalary(incidenceSalary);
		companyLiquidation.setIncidenceTickets(incidenceTickets);
		companyLiquidation.setSalaryPartners(salaryPartners);
		companyLiquidation.setTotalEarningsDollar(totalEarningsDollar);
		companyLiquidation.setTotalEarningsPeso(totalEarningsPeso);
		
		return companyLiquidation;
		}

}
