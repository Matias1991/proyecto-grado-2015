package servicelayer.service.builder;

import servicelayer.entity.businessEntity.User;
import servicelayer.entity.businessEntity.UserStatus;
import servicelayer.entity.businessEntity.UserType;
import servicelayer.entity.valueObject.VOUser;

public class UserBuilder extends BaseBuilder<VOUser, User>{

	@Override
	public VOUser BuildVOObject(User businessObject) {
		
		VOUser voUser = new VOUser();
		voUser.setId(businessObject.getId());
		voUser.setName(businessObject.getName());
		voUser.setUserName(businessObject.getUserName());
		voUser.setLastName(businessObject.getLastName());
		voUser.setEmail(businessObject.getEmail());
		voUser.setUserType(businessObject.getUserType().getValue());
		voUser.setUserStatus(businessObject.getUserStatus().getValue());

		return voUser;
	}

	@Override
	public User BuildBusinessObject(VOUser voObject) {
		
		User user = new User();
		user.setId(voObject.getId());
		user.setName(voObject.getName());
		user.setLastName(voObject.getLastName());
		user.setEmail(voObject.getEmail());
		user.setUserName(voObject.getUserName());
		user.setPassword(voObject.getPassword());
		if (voObject.getUserType() != 0)
			user.setUserType(UserType.getEnum(voObject.getUserType()));
		if (voObject.getUserStatus() != 0)
			user.setUserStatus(UserStatus.getEnum(voObject.getUserStatus()));
		
		return user;
	}

}
