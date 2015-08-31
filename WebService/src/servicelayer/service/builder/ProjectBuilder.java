package servicelayer.service.builder;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.DistributionType;
import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.ProjectEmployed;
import servicelayer.entity.businessEntity.ProjectPartner;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.valueObject.VODistributionType;
import servicelayer.entity.valueObject.VOProjectEmployed;
import servicelayer.entity.valueObject.VOProjectPartner;
import servicelayer.entity.valueObject.VOProject;

public class ProjectBuilder extends BaseBuilder<VOProject, Project> {

	@Override
	public VOProject BuildVOObject(Project businessObject) {

		VOProject voProject = new VOProject();
		voProject.setId(businessObject.getId());
		voProject.setName(businessObject.getName());
		voProject.setDescription(businessObject.getDescription());
		if (businessObject.getManager() != null) {
			voProject.setManagerId(businessObject.getManager().getId());
			voProject.setManagerName(businessObject.getManager().getName());
			voProject.setManagerLastName(businessObject.getManager()
					.getLastName());
		}
		voProject.setSellerId(businessObject.getSeller().getId());
		voProject.setSellerName(businessObject.getSeller().getName());
		voProject.setSellerLastname(businessObject.getSeller().getLastName());
		voProject.setClosed(businessObject.getClosed());
		voProject.setCreatedDateTimeUTC(businessObject.getCreatedDateTimeUTC());
		voProject.setUpdatedDateTimeUTC(businessObject.getUpdatedDateTimeUTC());
		// voProject.setVoEmployedProjects(BuildVOEmployedProjects(businessObject.getiDAOEmployedProject().getEmployeesProject(businessObject.getId())));
		// voProject.setVoPartnerProjects(BuildVOPartnerProjects(businessObject.getiDAOPartnerProject().getPartnersProject(businessObject.getId())));

		return voProject;
	}

	@Override
	public Project BuildBusinessObject(VOProject voObject) {
		// TODO Auto-generated method stub
		Project project = new Project();
		project.setId(voObject.getId());
		project.setName(voObject.getName());
		project.setDescription(voObject.getDescription());
		// project.setiDAOEmployedProject(voObject.get);
		// project.setiDAOPartnerProject(iDAOPartnerProject);
		User manager = new User();
		if (voObject.getManagerId() != 0) {
			manager.setId(voObject.getManagerId());
			manager.setName(voObject.getManagerName());
			manager.setLastName(voObject.getManagerLastName());
		}
		project.setManager(manager);
		Employed seller = new Employed();
		seller.setId(voObject.getSellerId());
		seller.setName(voObject.getSellerName());
		seller.setLastName(voObject.getSellerLastname());
		project.setSeller(seller);
		project.setClosed(voObject.isClosed());
		project.setCreatedDateTimeUTC(voObject.getCreatedDateTimeUTC());
		project.setUpdatedDateTimeUTC(voObject.getUpdatedDateTimeUTC());

		return project;
	}

	public VOProjectPartner BuildVOPartnerProject(ProjectPartner partnerProject) {

		VOProjectPartner voPartnerProject = new VOProjectPartner();
		voPartnerProject.setEmployedId(partnerProject.getEmployed().getId());
		voPartnerProject
				.setEmployedName(partnerProject.getEmployed().getName());
		voPartnerProject.setEmployedLastName(partnerProject.getEmployed()
				.getLastName());
		voPartnerProject.setDistributionType(this
				.BuildVODistributionType(partnerProject.getDistributionType()));
		voPartnerProject.setVersion(partnerProject.getVersion());
		voPartnerProject.setEnabled(partnerProject.isEnabled());
		voPartnerProject.setCreatedDateTimeUTC(partnerProject
				.getCreatedDateTimeUTC());
		voPartnerProject.setUpdatedDateTimeUTC(partnerProject
				.getUpdatedDateTimeUTC());

		return voPartnerProject;
	}

	public ProjectPartner BuildBusinessPartnerProject(
			VOProjectPartner voPartnerProject) {

		ProjectPartner partnerProject = new ProjectPartner();
		Employed employed = new Employed();
		employed.setId(voPartnerProject.getEmployedId());
		employed.setName(voPartnerProject.getEmployedName());
		employed.setLastName(voPartnerProject.getEmployedLastName());
		partnerProject.setEmployed(employed);
		partnerProject
				.setDistributionType(this
						.BuildDistritbutionType(voPartnerProject
								.getDistributionType()));
		partnerProject.setVersion(voPartnerProject.getVersion());
		partnerProject.setEnabled(voPartnerProject.isEnabled());
		partnerProject.setCreatedDateTimeUTC(voPartnerProject
				.getCreatedDateTimeUTC());
		partnerProject.setUpdatedDateTimeUTC(voPartnerProject
				.getUpdatedDateTimeUTC());

		return partnerProject;
	}

	public VOProjectPartner[] BuildVOPartnerProjects(
			ArrayList<ProjectPartner> partnerProjects) {
		int i = 0;
		VOProjectPartner[] voPartProjects = new VOProjectPartner[partnerProjects
				.size()];
		for (ProjectPartner partnerProject : partnerProjects) {
			voPartProjects[i] = BuildVOPartnerProject(partnerProject);
			i++;
		}

		return voPartProjects;
	}

	public ArrayList<ProjectPartner> BuildPartnerProjects(
			VOProjectPartner[] voPartnerProjects) {
		ArrayList<ProjectPartner> partnerProjects = new ArrayList<ProjectPartner>();

		if (voPartnerProjects != null) {
			for (VOProjectPartner voPartnerProject : voPartnerProjects) {
				partnerProjects
						.add(BuildBusinessPartnerProject(voPartnerProject));
			}
		}

		return partnerProjects;
	}

	public VOProjectEmployed BuildVOEmployedProject(
			ProjectEmployed employedProject) {
		VOProjectEmployed voEmployedProject = new VOProjectEmployed();
		voEmployedProject.setEmployedId(employedProject.getEmployed().getId());
		voEmployedProject.setEmployedName(employedProject.getEmployed()
				.getName());
		voEmployedProject.setEmployedLastname(employedProject.getEmployed()
				.getLastName());
		voEmployedProject.setHours(employedProject.getHours());
		voEmployedProject.setVersion(employedProject.getVersion());
		voEmployedProject.setCreatedDateTimeUTC(employedProject
				.getCreatedDateTimeUTC());
		voEmployedProject.setUpdatedDateTimeUTC(employedProject
				.getUpdatedDateTimeUTC());
		voEmployedProject.setEnabled(employedProject.isEnabled());

		return voEmployedProject;
	}

	public ProjectEmployed BuildBusinessEmployedProject(
			VOProjectEmployed voEmployedProject) {
		ProjectEmployed employedProject = new ProjectEmployed();
		Employed employed = new Employed();
		employed.setId(voEmployedProject.getEmployedId());
		employed.setName(voEmployedProject.getEmployedName());
		employed.setLastName(voEmployedProject.getEmployedLastname());
		employedProject.setEmployed(employed);
		employedProject.setHours(voEmployedProject.getHours());
		employedProject.setVersion(voEmployedProject.getVersion());
		employedProject.setCreatedDateTimeUTC(voEmployedProject
				.getCreatedDateTimeUTC());
		employedProject.setUpdatedDateTimeUTC(voEmployedProject
				.getUpdatedDateTimeUTC());
		employedProject.setEnabled(voEmployedProject.isEnabled());

		return employedProject;
	}

	public VOProjectEmployed[] BuildVOEmployedProjects(
			ArrayList<ProjectEmployed> employedProjects) {
		int i = 0;
		VOProjectEmployed[] voEmpProjects = new VOProjectEmployed[employedProjects
				.size()];
		for (ProjectEmployed employedProject : employedProjects) {
			voEmpProjects[i] = BuildVOEmployedProject(employedProject);
			i++;
		}

		return voEmpProjects;
	}

	public ArrayList<ProjectEmployed> BuildEmployedProjects(
			VOProjectEmployed[] voEmployedProjects) {
		ArrayList<ProjectEmployed> employedProjects = new ArrayList<ProjectEmployed>();

		if (voEmployedProjects != null) {
			for (VOProjectEmployed voEmployedProject : voEmployedProjects) {
				employedProjects
						.add(BuildBusinessEmployedProject(voEmployedProject));
			}
		}

		return employedProjects;
	}

	public DistributionType BuildDistritbutionType(
			VODistributionType voDistributionType) {
		DistributionType distributionType = new DistributionType();
		distributionType.setId(voDistributionType.getId());
		distributionType.setValue(voDistributionType.getValue());
		distributionType.setDescription(voDistributionType.getDescription());

		return distributionType;
	}

	public VODistributionType BuildVODistributionType(
			DistributionType distributionType) {
		VODistributionType voDistributionType = new VODistributionType();
		voDistributionType.setId(distributionType.getId());
		voDistributionType.setValue(distributionType.getValue());
		voDistributionType.setDescription(distributionType.getDescription());

		return voDistributionType;
	}

	public VODistributionType[] BuildVOArrayDistributionType(
			ArrayList<DistributionType> distributionTypes) {
		int i = 0;
		VODistributionType[] voDistTypes = new VODistributionType[distributionTypes
				.size()];
		for (DistributionType distributions : distributionTypes) {
			voDistTypes[i] = BuildVODistributionType(distributions);
			i++;
		}

		return voDistTypes;
	}

	public ArrayList<DistributionType> BuildDistributionTypes(
			VODistributionType[] voDistributionTypes) {
		ArrayList<DistributionType> distributions = new ArrayList<DistributionType>();

		for (VODistributionType voDistribution : voDistributionTypes) {
			distributions.add(BuildDistritbutionType(voDistribution));
		}

		return distributions;
	}

}
