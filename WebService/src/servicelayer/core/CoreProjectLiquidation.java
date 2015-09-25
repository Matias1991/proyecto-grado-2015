package servicelayer.core;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import datalayer.daos.DAOManager;
import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.Category;
import servicelayer.entity.businessEntity.CompanyLiquidation;
import servicelayer.entity.businessEntity.Employed;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.businessEntity.ProjectEmployed;
import servicelayer.entity.businessEntity.ProjectLiquidation;
import shared.exceptions.ClientException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreProjectLiquidation;

public class CoreProjectLiquidation implements ICoreProjectLiquidation {

	private static CoreProjectLiquidation instance = null;

	private CoreProjectLiquidation() {
	}

	public static CoreProjectLiquidation GetInstance() throws ServerException {
		if (instance == null) {
			instance = new CoreProjectLiquidation();
		}
		return instance;
	}	

	@Override
	public ProjectLiquidation getProjectLiquidationPreview(Date month,
			int projectId) throws ServerException, ClientException {
		DAOManager daoManager = new DAOManager();
		Project project = null;
		ArrayList<Category> categories = null;
		ProjectLiquidation projectLiquidation = null;

		try {
			// Defino el periodo
			Calendar cal = Calendar.getInstance();
			cal.setTime(month);
			cal.set(Calendar.DAY_OF_MONTH, 01);
			Date from = cal.getTime();

			cal.setTime(month);
			cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
			Date to = cal.getTime();

			// Obtengo los proyectos a liquidar
			project = CoreProject.GetInstance()
					.getProjectsReadyToLiquidate(from, to, projectId).get(0);
			
			project.setiDAOBills(daoManager.getDAOBills());
			project.setiDAOCategories(daoManager.getDAOCategories());
			project.setiDAOPartnerProject(daoManager.getDAOPartnerProjects());
			project.setiDAOProjectEmployees(daoManager.getDAOEmployedProjects());
			
			projectLiquidation = new ProjectLiquidation(projectId);
			
			// Cargo facturas asociadas al proyecto
			ArrayList<Bill> associatedBills = project.getBills(from, to);
			if(associatedBills != null){
				for(Bill bill : associatedBills){
					if(bill.getIsCurrencyDollar()){
						bill.setTotalAmountDollar(CoreBill.GetInstance().getTotalAmount(bill.getAmountDollar(), bill.getIvaType()));
					}else{
						bill.setTotalAmountPeso(CoreBill.GetInstance().getTotalAmount(bill.getAmountDollar(), bill.getIvaType()));
					}
				}
			}
			projectLiquidation.setBills(associatedBills);

			// Cargo rubros humanos y rubros materiales asociados al proyecto
			ArrayList<Category> categoriesHumanToShow = new ArrayList<Category>();
			ArrayList<Category> categoriesMaterialToShow = new ArrayList<Category>();
			categories = project.getCategories(from, to);
			if (categories != null && categories.size() > 0) {
				for (Category category : categories) {
					if (category.getIsRRHH()) {
						categoriesHumanToShow.add(category);
					} else {
						categoriesMaterialToShow.add(category);
					}
					if(category.getIsCurrencyDollar()){
						category.setTotalAmountDollar(CoreCategory.GetInstance().getTotalAmount(category.getAmountDollar(), category.getIvaType()));
					}else{
						category.setTotalAmountPeso(CoreCategory.GetInstance().getTotalAmount(category.getAmountPeso(), category.getIvaType()));
					}
				}
			}
			projectLiquidation.setCategoriesHuman(categoriesHumanToShow);
			projectLiquidation.setCategoriesMaterial(categoriesMaterialToShow);

			// Cargo si es dolares
			projectLiquidation.setCurrencyDollar(project.getIsCurrencyDollar());

			// Cargo los empleados asociados al proyecto
			ArrayList<ProjectEmployed> employees = project.getProjectEmployees();
			if(employees != null && employees.size() > 0){
				for(ProjectEmployed projectEmployed : employees){
					Employed employed = CoreEmployed.GetInstance().getEmployed(projectEmployed.getEmployed().getId());
					projectEmployed.setEmployed(employed);
				}
			}
			projectLiquidation.setEmployees(employees);

			// Cargo los socios y distribuciones
			projectLiquidation.setPartner1(project.getProjectPartner().get(0));
			projectLiquidation.setPartner2(project.getProjectPartner().get(1));			
			
		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
		return projectLiquidation;
	}	
	
	@Override
	public ArrayList<ProjectLiquidation> getProjectsLiquidationsByDate(Date month) throws ServerException, ClientException{
		DAOManager daoManager = new DAOManager();
		ArrayList<ProjectLiquidation> projectsLiquidations = new ArrayList<ProjectLiquidation>();
		
		try{	
			Calendar cal = Calendar.getInstance();
			cal.setTime(month);
			cal.set(Calendar.DAY_OF_MONTH, 01);
			Date appliedDate = cal.getTime();
			projectsLiquidations = daoManager.getDAOProjectLiquidation().getProjectsLiquidationsByDate(appliedDate);
			
			for(ProjectLiquidation projectLiquidation : projectsLiquidations){
				projectLiquidation.setEmployedPartner1(CoreEmployed.GetInstance().getEmployed(projectLiquidation.getEmployedPartner1().getId()));
				projectLiquidation.setEmployedPartner2(CoreEmployed.GetInstance().getEmployed(projectLiquidation.getEmployedPartner2().getId()));
				projectLiquidation.setProject(CoreProject.GetInstance().getProject(projectLiquidation.getProject().getId()));
			}
			
		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} catch (ClientException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}		
		return projectsLiquidations;
	}
}
