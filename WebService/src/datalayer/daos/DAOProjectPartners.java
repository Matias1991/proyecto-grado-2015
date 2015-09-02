package datalayer.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import servicelayer.entity.businessEntity.DistributionType;
import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.ProjectPartner;
import shared.LoggerMSMP;
import shared.exceptions.ServerException;
import shared.interfaces.dataLayer.IDAOProjectPartners;

public class DAOProjectPartners implements IDAOProjectPartners {
	
	private Connection connection;
	
	public DAOProjectPartners(){		
	}
	
	public DAOProjectPartners(Connection connection){
		this.connection = connection;
	}

	@Override
	public int insertPartnerProject(int projectId, ProjectPartner partnerProject)
			throws ServerException {
		int newPartnerProjectId = -1;
		PreparedStatement preparedStatement = null;
		String insertSQL = "INSERT INTO PARTNER_PROJECT (PROJECTID, EMPLOYEDID, DISTRIBUTIONTYPEID, VERSION, ENABLED, CREATEDDATETIMEUTC, UPDATEDDATETIMEUTC) VALUES"
				+ "(?,?,?,?,?,?,?)";
		
		try{
			preparedStatement = this.connection.prepareStatement(insertSQL);
			
			preparedStatement.setInt(1,projectId);
			preparedStatement.setInt(2, partnerProject.getEmployed().getId());
			preparedStatement.setInt(3,partnerProject.getDistributionType().getId());
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
	public ArrayList<ProjectPartner> getPartnersProject(int id) throws ServerException {
		ArrayList<ProjectPartner> projectPartners = new ArrayList<ProjectPartner>();
		
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		try {

			String getSQL = "SELECT * FROM PARTNER_PROJECT WHERE id = ?";
			preparedStatement = this.connection.prepareStatement(getSQL);
			preparedStatement.setInt(1, id);
			rs = preparedStatement.executeQuery();

			while (rs.next()) {
				ProjectPartner projectPartner = BuildProjectPartner(rs);
				projectPartners.add(projectPartner);
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

		return projectPartners;
	}

	private ProjectPartner BuildProjectPartner (ResultSet rs) throws SQLException {
		int projectId = rs.getInt("projectId");
		int employedId = rs.getInt("employedId");
		int distribution = rs.getInt("ditributionTypeId");
		int version = rs.getInt("version");
		boolean enabled = rs.getBoolean("enabled");
		Date createdDateTimeUTC = rs.getTimestamp("createdDateTimeUTC");
		Date updatedDateTimeUTC = rs.getTimestamp("updatedDateTimeUTC");
		
		ProjectPartner projectPartner = new ProjectPartner();
		projectPartner.setProject(new Project(projectId));
		projectPartner.setEmployed(new Employed(employedId));
		projectPartner.setDistributionType(new DistributionType(distribution));
		projectPartner.setVersion(version);
		projectPartner.setEnabled(enabled);
		projectPartner.setCreatedDateTimeUTC(createdDateTimeUTC);
		projectPartner.setUpdatedDateTimeUTC(updatedDateTimeUTC);

		return projectPartner;
	}
}
