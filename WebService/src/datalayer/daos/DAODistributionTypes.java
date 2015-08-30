package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import servicelayer.entity.businessEntity.DistributionType;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAODistributionType;

public class DAODistributionTypes implements IDAODistributionType{
	
	private Connection connection;
	
	public DAODistributionTypes(){
	}
	
	public DAODistributionTypes (Connection connection){
		this.connection = connection;
	}	

	@Override
	public ArrayList<DistributionType> getOjects() throws ServerException {
		ArrayList<DistributionType> distributions = new ArrayList<DistributionType>();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try{
			
			String sql;
			sql = "SELECT * FROM distributionType";
			preparedStatement = this.connection.prepareStatement(sql);
			rs = preparedStatement.executeQuery(sql);
			
			while(rs.next()){
				distributions.add(BuildDistributions(rs));
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

		return distributions;		
	}
	
	DistributionType BuildDistributions(ResultSet rs) throws SQLException, ServerException{
		int _id = rs.getInt("id");
		String value = rs.getString("value");
		String description = rs.getString("description");
		
		DistributionType distribution = new DistributionType();
		distribution.setId(_id);
		distribution.setValue(value);
		distribution.setDescription(description);
		
		return distribution;		
	}

}
