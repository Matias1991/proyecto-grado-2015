package servicelayer.service.builder;

import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.Charge;
import servicelayer.entity.valueObject.VOCharge;

public class ChargeBuilder extends BaseBuilder<VOCharge, Charge>{

	@Override
	public VOCharge BuildVOObject(Charge businessObject) {
		
		VOCharge voCharge = new VOCharge();
		voCharge.setId(businessObject.getId());
		voCharge.setNumber(businessObject.getNumber());
		voCharge.setDescription(businessObject.getDescription());
		voCharge.setAmountPeso(businessObject.getAmountPeso());
		voCharge.setAmountDollar(businessObject.getAmountDollar());
		voCharge.setIsCurrencyDollar(businessObject.getIsCurrencyDollar());
		voCharge.setTypeExchange(businessObject.getTypeExchange());
		voCharge.setCreatedDateTimeUTC(businessObject.getCreatedDateTimeUTC());
		if(businessObject.getBill() != null)
		{
			voCharge.setBillCode(businessObject.getBill().getCode());
			voCharge.setBillDescription(businessObject.getBill().getDescription());
		}
		return voCharge;
	}

	@Override
	public Charge BuildBusinessObject(VOCharge voObject) {
		
		Charge charge = new Charge();
		charge.setId(voObject.getId());
		charge.setNumber(voObject.getNumber());
		charge.setDescription(voObject.getDescription());
		charge.setAmountPeso(voObject.getAmountPeso());
		charge.setAmountDollar(voObject.getAmountDollar());
		charge.setIsCurrencyDollar(voObject.getIsCurrencyDollar());
		charge.setTypeExchange(voObject.getTypeExchange());
		if(voObject.getBillId() != 0)
			charge.setBill(new Bill(voObject.getBillId()));
		
		return charge;
	}

}
