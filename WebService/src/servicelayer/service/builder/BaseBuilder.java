package servicelayer.service.builder;

import java.lang.reflect.Array;
import java.util.ArrayList;

public abstract class BaseBuilder<VO, BusinessObject>{

	public VO[] BuildArrayVOObject(Class<VO> c, ArrayList<BusinessObject> businessObjects){
		
		ArrayList<VO> voObjects = new ArrayList<VO>();

		for (BusinessObject businessObject : businessObjects) {
			voObjects.add(BuildVOObject(businessObject));
		}
		
		 @SuppressWarnings("unchecked")
		 VO[] array = (VO[]) Array.newInstance(c, businessObjects.size());
		 voObjects.toArray(array);
		return array;
	}
	
	public abstract VO BuildVOObject(BusinessObject businessObject);
	
	public abstract BusinessObject BuildBusinessObject(VO voObject);
}
