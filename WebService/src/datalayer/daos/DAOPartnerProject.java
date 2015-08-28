package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import servicelayer.entity.businessEntity.PartnerProject;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOPartnerProject;

public class DAOPartnerProject implements IDAOPartnerProject {
	
	private Connection connection;
	
	public DAOPartnerProject(){		
	}
	
	public DAOPartnerProject(Connection connection){
		this.connection = connection;
	}

	@Override
	public int insertPartnerProject(int projectId, PartnerProject partnerProject)
			throws ServerException {
		int newPartnerProjectId = -1;
		PreparedStatement preparedStatement = null;
		String insertSQL = "INSERT INTO PARTNER_PROJECT (PROJECTID, EMPLOYEDID, DISTRIBUTIONTYPEID, VERSION, ENABLED, CREATEDDATETIMEUTC, UPDATEDDATETIMEUTC) VALUES"
				+ "(?,?,?,?,?,?,?)";
		
		try{
			preparedStatement = this.connection.prepareStatement(insertSQL);
			
			preparedStatement.setInt(1,projectId);
			preparedStatement.setInt(2, partnerProject.getEmployed().getId());
			preparedStatement.setInt(3,partnerProject.getDistributionType());
			preparedStatement.setInt(4,1);
			preparedStatement.setBoolean(5,partnerProject.isEnabled());
			preparedStatement.setTimestamp(6, new Timestamp(partnerProject.getCreatedDateTimeUTC().getTime()));
			preparedStatement.setTimestamp(7, new Timestamp(partnerProject.getUpdatedDateTimeUTC().getTime()));
			
			preparedStatement.executeUpdate();
		}catch (SQLException e) {
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
		
		return newPartnerProjectId;
	}

	@Override
	public ArrayList<PartnerProject> getPartnersProject(int projectId)
			throws ServerException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
