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
			preparedStatement.setInt(4,partnerProject.getVersion() + 1);
			preparedStatement.setBoolean(5,partnerProject.isEnabled());
			preparedStatement.setTimestamp(6, new Timestamp(new Date().getTime()));
			preparedStatement.setTimestamp(7, new Timestamp(new Date().getTime()));
			
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

			String getSQL = "SELECT pp1.*,e.*,d.* " + 
					"FROM partner_project pp1  " +
					"INNER JOIN employed e on e.Id = pp1.EmployedId " +
					"INNER JOIN distributionType d on d.id = pp1.distributionTypeId "+
					"WHERE (pp1.projectId, pp1.employedId, pp1.version) in " +
					"		(select pp2.projectId, pp2.employedId, MAX(pp2.version) from partner_project pp2 " +
					"			where pp1.projectId = pp2.projectId and " +
					"			pp1.employedId = pp2.employedId) " +
					"and pp1.projectId = ?;";
			
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

	
	@Override
	public void update(ProjectPartner obj) throws ServerException {
		PreparedStatement preparedStatement = null;

		String updateSQL = "UPDATE PARTNER_PROJECT SET DISTRIBUTIONTYPEID = ?, UPDATEDDATETIMEUTC = ? " +
				"WHERE VERSION = ? AND PROJECTID = ? AND EMPLOYEDID = ?;";
		
		try {
			preparedStatement = this.connection.prepareStatement(updateSQL);

			preparedStatement.setInt(1, obj.getDistributionType().getId());
			preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime()));
			preparedStatement.setInt(3, obj.getVersion());
			preparedStatement.setInt(4, obj.getProject().getId());
			preparedStatement.setInt(5, obj.getEmployed().getId());

			preparedStatement.executeUpdate();

		}  catch (SQLException e) {
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

	
	private ProjectPartner BuildProjectPartner (ResultSet rs) throws SQLException {
		int projectId = rs.getInt("projectId");
		int employedId = rs.getInt("employedId");
		String employedName = rs.getString("Name");
		String employedLastname = rs.getString("LastName");
		int distributionId = rs.getInt("distributionTypeId");
		String distributionValue = rs.getString("value");
		int version = rs.getInt("version");
		boolean enabled = rs.getBoolean("enabled");
		Date createdDateTimeUTC = rs.getTimestamp("createdDateTimeUTC");
		Date updatedDateTimeUTC = rs.getTimestamp("updatedDateTimeUTC");
		
		ProjectPartner projectPartner = new ProjectPartner();
		projectPartner.setProject(new Project(projectId));
		Employed employed = new Employed();
		employed.setId(employedId);
		employed.setName(employedName);
		employed.setLastName(employedLastname);
		projectPartner.setEmployed(employed);
		projectPartner.setDistributionType(new DistributionType(distributionId,distributionValue,null));
		projectPartner.setVersion(version);
		projectPartner.setEnabled(enabled);
		projectPartner.setCreatedDateTimeUTC(createdDateTimeUTC);
		projectPartner.setUpdatedDateTimeUTC(updatedDateTimeUTC);

		return projectPartner;
	}
}
