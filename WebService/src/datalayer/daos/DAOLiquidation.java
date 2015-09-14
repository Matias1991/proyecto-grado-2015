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
import servicelayer.entity.businessEntity.Liquidation;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOLiquidation;
import datalayer.utilities.ManageConnection;

public class DAOLiquidation implements IDAOLiquidation {
	
	private Connection connection;
	
	public DAOLiquidation(Connection connection){
		this.connection = connection;
	}
	
	public DAOLiquidation() throws ServerException{
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
	public void update(int id, Liquidation obj) throws ServerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean exist(int id) throws ServerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Liquidation getObject(int id) throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Liquidation> getObjects() throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(Liquidation obj) throws ServerException {
		int newLiquidationId = -1;
		PreparedStatement preparedStatement = null;

		String insertSQL = "INSERT INTO LIQUIDATION (PROJECTID, TOTALBILLS, "
				+ "HOURCOSTEMPLOYEES, HOURCOSTOUTSOURCED , CATEGORIESCOST, COMPANYCOST, PROFIT, RESERVE, SELLINGCOST, "
				+ "EMPLOYED1ID, EMPLOYED1EARNING, EMPLOYED2ID, EMPLOYED2EARNING, APPLIEDDATETIMEUTC, CREATEDDATETIMEUTC) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			preparedStatement = this.connection.prepareStatement(insertSQL,
					Statement.RETURN_GENERATED_KEYS);

			preparedStatement.setInt(1, obj.getProject().getId());
			preparedStatement.setDouble(2, obj.getTotalBills());
			preparedStatement.setDouble(3, obj.getTotalCostHoursEmployees());
			preparedStatement.setDouble(4, obj.getTotalCostCategoriesHuman());			
			preparedStatement.setDouble(5, obj.getTotalCostCategoriesMaterial());
			preparedStatement.setDouble(6, obj.getTotalCostCategoriesCompany());
			preparedStatement.setDouble(7, obj.getEarnings());
			preparedStatement.setDouble(8, obj.getReserve());
			preparedStatement.setDouble(9, obj.getSale());
			preparedStatement.setDouble(10, obj.getPartner1().getId());
			preparedStatement.setDouble(11, obj.getPartner1Earning());
			preparedStatement.setDouble(12, obj.getPartner2().getId());
			preparedStatement.setDouble(13, obj.getPartner2Earning());
			preparedStatement.setTimestamp(14, new Timestamp(obj.getAppliedDateTimeUTC().getTime()));
			preparedStatement.setTimestamp(15, new Timestamp(obj.getCreatedDateTimeUTC().getTime()));
			
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
			String getSQL = "SELECT id FROM LIQUIDATION WHERE AppliedDateTimeUTC = ?";
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
