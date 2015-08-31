package servicelayer.service.builder;

import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.Project;
import servicelayer.entity.valueObject.VOBill;

public class BillBuilder extends BaseBuilder<VOBill, Bill>{

	@Override
	public VOBill BuildVOObject(Bill businessObject) {
		
		VOBill voBill = new VOBill();
		voBill.setId(businessObject.getId());
		voBill.setCode(businessObject.getCode());
		voBill.setDescription(businessObject.getDescription());
		voBill.setAmountDollar(businessObject.getAmountDollar());
		voBill.setAmountPeso(businessObject.getAmountPeso());
		voBill.setIsCurrencyDollar(businessObject.getIsCurrencyDollar());
		voBill.setTypeExchange(businessObject.getTypeExchange());
		voBill.setAppliedDateTimeUTC(businessObject.getAppliedDateTimeUTC());
		voBill.setLiquidated(businessObject.getIsLiquidated());
		if (businessObject.getProject() != null) {
			voBill.setProjectId(businessObject.getProject().getId());
			voBill.setProjectName(businessObject.getProject().getName());
		}

		voBill.setAmountChargedDollar(businessObject.getAmountChargedDollar());
		voBill.setAmountChargedPeso(businessObject.getAmountChargedPeso());
		
		return voBill;
	}

	@Override
	public Bill BuildBusinessObject(VOBill voObject) {
		
		Bill bill = new Bill();
		bill.setId(voObject.getId());
		bill.setCode(voObject.getCode());
		bill.setDescription(voObject.getDescription());
		bill.setAmountPeso(voObject.getAmountPeso());
		bill.setAmountDollar(voObject.getAmountDollar());
		bill.setIsCurrencyDollar(voObject.getIsCurrencyDollar());
		bill.setTypeExchange(voObject.getTypeExchange());
		bill.setAppliedDateTimeUTC(voObject.getAppliedDateTimeUTC());
		if (voObject.getProjectId() != 0)
			bill.setProject(new Project(voObject.getProjectId()));
		
		return bill;
	}
}
