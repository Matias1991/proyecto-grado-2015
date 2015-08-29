package servicelayer.service.builder;

import servicelayer.entity.businessEntity.Bill;
import servicelayer.entity.businessEntity.Charge;
import servicelayer.entity.valueObject.VOCharge;

public class ChargeBuilder extends BaseBuilder<VOCharge, Charge>{

	@Override
	public VOCharge BuildVOObject(Charge businessObject) {
		// TODO Auto-generated method stub
		return null;
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
