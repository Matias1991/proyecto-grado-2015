package servicelayer.service.builder;

import java.util.ArrayList;

import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.EmployedProject;
import servicelayer.entity.businessEntity.PartnerProject;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.valueObject.VOEmployedProject;
import servicelayer.entity.valueObject.VOPartnerProject;
import servicelayer.entity.valueObject.VOProject;

public class ProjectBuilder extends BaseBuilder<VOProject, Project> {

	@Override
	public VOProject BuildVOObject(Project businessObject) {
		
		VOProject voProject = new VOProject();
		voProject.setId(businessObject.getId());
		voProject.setName(businessObject.getName());
		voProject.setDescription(businessObject.getDescription());
		voProject.setManagerId(businessObject.getManager().getId());
		voProject.setManagerName(businessObject.getManager().getName());
		voProject.setManagerLastName(businessObject.getManager().getLastName());
		voProject.setSellerId(businessObject.getSeller().getId());
		voProject.setSellerName(businessObject.getSeller().getName());
		voProject.setSellerLastname(businessObject.getSeller().getLastName());		
		voProject.setEnabled(businessObject.getEnabled());		
		voProject.setCreatedDateTimeUTC(businessObject.getCreatedDateTimeUTC());		
		voProject.setUpdatedDateTimeUTC(businessObject.getUpdatedDateTimeUTC());
		//voProject.setVoEmployedProjects(BuildVOEmployedProjects(businessObject.getiDAOEmployedProject().getEmployeesProject(businessObject.getId())));		
		//voProject.setVoPartnerProjects(BuildVOPartnerProjects(businessObject.getiDAOPartnerProject().getPartnersProject(businessObject.getId())));
		
		return voProject;		
	}

	@Override
	public Project BuildBusinessObject(VOProject voObject) {
		// TODO Auto-generated method stub
		Project project = new Project();
		project.setId(voObject.getId());
		project.setName(voObject.getName());		
		project.setDescription(voObject.getDescription());		
		//project.setiDAOEmployedProject(voObject.get);
		//project.setiDAOPartnerProject(iDAOPartnerProject);
		User manager = new User();
		manager.setId(voObject.getManagerId());
		manager.setName(voObject.getManagerName());
		manager.setLastName(voObject.getManagerLastName());
		project.setManager(manager);
		Employed seller = new Employed();
		seller.setId(voObject.getSellerId());
		seller.setName(voObject.getSellerName());
		seller.setLastName(voObject.getSellerLastname());
		project.setSeller(seller);	
		project.setEnabled(voObject.isEnabled());		
		project.setCreatedDateTimeUTC(voObject.getCreatedDateTimeUTC());
		project.setUpdatedDateTimeUTC(voObject.getUpdatedDateTimeUTC());
		
		return project;
	}
	
	public VOPartnerProject BuildVOPartnerProject(PartnerProject partnerProject){
		
		VOPartnerProject voPartnerProject = new VOPartnerProject();		
		voPartnerProject.setEmployedId(partnerProject.getEmployed().getId());
		voPartnerProject.setEmployedName(partnerProject.getEmployed().getName());
		voPartnerProject.setEmployedLastName(partnerProject.getEmployed().getLastName());
		voPartnerProject.setDistributionType(partnerProject.getDistributionType());		
		voPartnerProject.setVersion(partnerProject.getVersion());
		voPartnerProject.setEnabled(partnerProject.isEnabled());
		voPartnerProject.setCreatedDateTimeUTC(partnerProject.getCreatedDateTimeUTC());
		voPartnerProject.setUpdatedDateTimeUTC(partnerProject.getUpdatedDateTimeUTC());
		
		return voPartnerProject;
	}
	
	public PartnerProject BuildBusinessPartnerProject(VOPartnerProject voPartnerProject){
		
		PartnerProject partnerProject = new PartnerProject();
		Employed employed = new Employed();
		employed.setId(voPartnerProject.getEmployedId());
		employed.setName(voPartnerProject.getEmployedName());
		employed.setLastName(voPartnerProject.getEmployedLastName());
		partnerProject.setEmployed(employed);
		partnerProject.setDistributionType(voPartnerProject.getDistributionType());		
		partnerProject.setVersion(voPartnerProject.getVersion());
		partnerProject.setEnabled(voPartnerProject.isEnabled());
		partnerProject.setCreatedDateTimeUTC(voPartnerProject.getCreatedDateTimeUTC());
		partnerProject.setUpdatedDateTimeUTC(voPartnerProject.getUpdatedDateTimeUTC());
		
		return partnerProject;
	}
	
	public VOPartnerProject[] BuildVOPartnerProjects(ArrayList<PartnerProject> partnerProjects){
		int i = 0;
		VOPartnerProject[] voPartProjects = new VOPartnerProject[partnerProjects.size()];
		for (PartnerProject partnerProject : partnerProjects) {
			voPartProjects[i] = BuildVOPartnerProject(partnerProject);
			i++;	
		}
		
		return voPartProjects;
	}
	
	public ArrayList<PartnerProject> BuildPartnerProjects(VOPartnerProject[] voPartnerProjects){
		ArrayList<PartnerProject> partnerProjects = new ArrayList<PartnerProject>();
		
		for (VOPartnerProject voPartnerProject : voPartnerProjects) {
			partnerProjects.add(BuildBusinessPartnerProject(voPartnerProject));			
		}
		
		return partnerProjects;		
	}
	
	public VOEmployedProject BuildVOEmployedProject(EmployedProject employedProject){
		VOEmployedProject voEmployedProject = new VOEmployedProject();		
		voEmployedProject.setEmployedId(employedProject.getEmployed().getId());
		voEmployedProject.setEmployedName(employedProject.getEmployed().getName());
		voEmployedProject.setEmployedLastname(employedProject.getEmployed().getLastName());
		voEmployedProject.setHours(employedProject.getHours());
		voEmployedProject.setVersion(employedProject.getVersion());	
		voEmployedProject.setCreatedDateTimeUTC(employedProject.getCreatedDateTimeUTC());
		voEmployedProject.setUpdatedDateTimeUTC(employedProject.getUpdatedDateTimeUTC());
		voEmployedProject.setEnabled(employedProject.isEnabled());	
		
		return voEmployedProject;		
	}
	
	public EmployedProject BuildBusinessEmployedProject(VOEmployedProject voEmployedProject){
		EmployedProject employedProject = new EmployedProject();	
		Employed employed = new Employed();
		employed.setId(voEmployedProject.getEmployedId());
		employed.setName(voEmployedProject.getEmployedName());
		employed.setLastName(voEmployedProject.getEmployedLastname());		
		employedProject.setEmployed(employed);
		employedProject.setHours(voEmployedProject.getHours());
		employedProject.setVersion(voEmployedProject.getVersion());	
		employedProject.setCreatedDateTimeUTC(voEmployedProject.getCreatedDateTimeUTC());
		employedProject.setUpdatedDateTimeUTC(voEmployedProject.getUpdatedDateTimeUTC());
		employedProject.setEnabled(voEmployedProject.isEnabled());	
		
		return employedProject;	
	}
	
	public VOEmployedProject[] BuildVOEmployedProjects(ArrayList<EmployedProject> employedProjects){
		int i = 0;
		VOEmployedProject[] voEmpProjects = new VOEmployedProject[employedProjects.size()];
		for (EmployedProject employedProject : employedProjects) {
			voEmpProjects[i] = BuildVOEmployedProject(employedProject);
			i++;			
		}
		
		return voEmpProjects;
	}
	
	public ArrayList<EmployedProject> BuildEmployedProjects(VOEmployedProject[] voEmployedProjects){
		ArrayList<EmployedProject> employedProjects = new ArrayList<EmployedProject>();
		
		for (VOEmployedProject voEmployedProject : voEmployedProjects) {
			employedProjects.add(BuildBusinessEmployedProject(voEmployedProject));			
		}
		
		return employedProjects;		
	}

}
