package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.Statement;

import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.ProjectLiquidation;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOProjectsLiquidations;
import datalayer.utilities.ManageConnection;

public class DAOProjectsLiquidations implements IDAOProjectsLiquidations {
	
	private Connection connection;
	
	public DAOProjectsLiquidations(Connection connection){
		this.connection = connection;
	}
	
	public DAOProjectsLiquidations() throws ServerException{
		try{
			this.connection = new ManageConnection().GetConnection();
		}catch (Exception e){
			throw new ServerException(e);
		}
	}

	@Override
	public void delete(int id) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int id, ProjectLiquidation obj) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exist(int id) throws ServerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ProjectLiquidation getObject(int id) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ProjectLiquidation> getObjects() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(ProjectLiquidation obj) throws ServerException {
		int newLiquidationId = -1;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO PROJECTLIQUIDATION (PROJECTID, TOTALBILLS, "
				+ "OUTSOURCEDCOST , CATEGORIESCOST, PROFIT, RESERVE, SELLINGCOST, "
				+ "PARTNER1ID, PARTNER1EARNING, PARTNER2ID, PARTNER2EARNING, ISCURRENCYDOLLAR, "
				+ "APPLIEDDATETIMEUTC, CREATEDDATETIMEUTC) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, obj.getProject().getId());
			preparedStatement.setDouble(2, obj.getTotalBills());
			preparedStatement.setDouble(3, obj.getTotalCostCategoriesHuman());			
			preparedStatement.setDouble(4, obj.getTotalCostCategoriesMaterial());
			preparedStatement.setDouble(5, obj.getEarnings());
			preparedStatement.setDouble(6, obj.getReserve());
			preparedStatement.setDouble(7, obj.getSale());
			preparedStatement.setDouble(8, obj.getPartner1().getId());
			preparedStatement.setDouble(9, obj.getPartner1Earning());
			preparedStatement.setDouble(10, obj.getPartner2().getId());
			preparedStatement.setDouble(11, obj.getPartner2Earning());
			preparedStatement.setBoolean(12, obj.isCurrencyDollar());			
			preparedStatement.setTimestamp(13, new Timestamp(obj.getAppliedDateTimeUTC().getTime()));
			preparedStatement.setTimestamp(14, new Timestamp(obj.getCreatedDateTimeUTC().getTime()));
			
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
	public boolean existLiquidation(Date appliedDate)throws ServerException {	
		boolean exist = false;
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {
			String getSQL = "SELECT id FROM PROJECTLIQUIDATION WHERE AppliedDateTimeUTC = ?";
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

}
