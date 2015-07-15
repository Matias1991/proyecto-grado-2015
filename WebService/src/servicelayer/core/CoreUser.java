package servicelayer.core;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import datalayer.daos.DAOUsers;
import servicelayer.entity.businessEntity.User;
import servicelayer.entity.businessEntity.UserStatus;
import servicelayer.entity.businessEntity.UserType;
import servicelayer.entity.valueObject.VOUser;
import servicelayer.utilities.Email;
import servicelayer.utilities.HashMD5;
import shared.ConfigurationProperties;
import shared.exceptions.ConfigPropertiesException;
import shared.exceptions.DataLayerException;
import shared.exceptions.EmailException;
import shared.exceptions.MD5Exception;
import shared.exceptions.ServiceLayerException;
import shared.interfaces.core.ICoreUser;
import shared.interfaces.dataLayer.IDAOUsers;


public class CoreUser implements ICoreUser {
	
	private static CoreUser instance = null;
	private IDAOUsers iDAOUsers;

	private static final Random RANDOM = new SecureRandom();
	public static final int PASSWORD_LENGTH = 8;
	
	private CoreUser() throws ServiceLayerException
	{
		try {
			iDAOUsers = new DAOUsers();
		} catch (DataLayerException e) {
			throw new ServiceLayerException(e);
		}
	}
	
	public static CoreUser GetInstance() throws ServiceLayerException
	{
		if(instance == null)
		{
			instance = new CoreUser();
		}
		return instance;
	}

	@Override
	public void insertUser(VOUser voUser) throws ServiceLayerException {
		
		try {
			
			if(iDAOUsers.getUserByUserName(voUser.getUserName()) == null)
			{
				//setear el estado del usuario en activo
				voUser.setUserStatus(UserStatus.ACTIVE.getValue());
				User user = new User(voUser);
				
				String newPassword = generateRandomPassword();
				
				//encriptar el password del usuario
				String hashPassword = HashMD5.Encrypt(newPassword);
				user.setPassword(hashPassword);
				
				iDAOUsers.insert(user);

				Email.newUser(user, newPassword);
			}
			else
				throw new ServiceLayerException("Ya existe un usuario con este nombre de usuario");

		} catch (DataLayerException | EmailException | MD5Exception e) {
			throw new ServiceLayerException(e.getMessage());
		}
	}

	@Override
	public void deleteUser(int id) throws ServiceLayerException {
		
		try
		{
			User user = iDAOUsers.getObject(id);
			if(user == null)
				throw new ServiceLayerException("No existe un usuario con ese id");
			
			if(user.getUserType() == UserType.ADMINISTRATOR & user.getUserStatus() == UserStatus.ACTIVE)
			{
				if(IsLastUserAdmin(user))
					throw new ServiceLayerException("No se puede eliminar este usuario, el sistema debe tener al menos un usuario Administrador");
			}
			
			iDAOUsers.delete(id);
		}
		catch(DataLayerException e)
		{
			throw new ServiceLayerException(e.getMessage());
		}
	}

	@Override
	public VOUser getUser(int id) throws ServiceLayerException {
		
		User user;
		VOUser voUser = null;
		
		try {
			user = iDAOUsers.getObject(id);
			if(user != null)
				voUser = BuildVoUser(user);
			else
				throw new ServiceLayerException("No existe un usuario con ese id");
			
		} catch (DataLayerException e) {
			throw new ServiceLayerException(e.getMessage());
		}

		return voUser;
	}

	@Override
	public boolean existUser(int id)  throws ServiceLayerException{

		try {
			return iDAOUsers.exist(id);
		} catch (DataLayerException e) {
			throw new ServiceLayerException(e.getMessage());
		}
	}

	@Override
	public ArrayList<VOUser> getUsers() throws ServiceLayerException{
		
		ArrayList<User> users;
		ArrayList<VOUser> voUsers = null;
		
		try {
			users = iDAOUsers.getObjects();
			voUsers = new ArrayList<VOUser>(); 
			
			for(User user: users)
			{
				voUsers.add(BuildVoUser(user));
			}
		} catch (DataLayerException e) {
			throw new ServiceLayerException(e.getMessage());
		}
		
		return voUsers;
	}
	
	@Override
	public VOUser login(String userName, String password) throws ServiceLayerException
	{
		VOUser voUser = null;
		try {
			
			//tOdo: move this to UI
			String hashPassword = HashMD5.Encrypt(password);
			//
			User user = iDAOUsers.getUserByUserName(userName);
			if(user != null)
			{
				if(user.getUserStatus() == UserStatus.ACTIVE)
				{
					if(user.getPassword().equals(hashPassword))
					{
						voUser = BuildVoUser(user);
					}
					else
					{
						if(!IsLastUserAdmin(user))
						{
							long diffInMinutes = 0;
							Date now  = new Date();
							if(user.getLastAttemptDateTimeUTC() != null)
							{
								Date lastAttemptDate = user.getLastAttemptDateTimeUTC();
								long duration  = now.getTime() - lastAttemptDate.getTime();
								diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(duration);
							}
							
							if(diffInMinutes > Integer.parseInt(ConfigurationProperties.GetConfigValue("EXPIRATION_TIME_ATTEMPTS_IN_MINUTES")))
							{
								iDAOUsers.update(user.getId(), UserStatus.ACTIVE, 1, now);
							}
							else
							{
								int attempts = user.getAttempts() + 1;
								
								int maxAttemptsLogin = Integer.parseInt(ConfigurationProperties.GetConfigValue("MAX_ATTEMPTS_LOGIN"));
								if(attempts >= maxAttemptsLogin)
								{
									iDAOUsers.update(user.getId(), UserStatus.BLOCKED , attempts, now);
								}
								else
								{
									iDAOUsers.update(user.getId(), UserStatus.ACTIVE, attempts, now);
								}
							}
						}
					}
				}
				else
					throw new ServiceLayerException("El usuario esta bloquedo");
			}
			
		}catch (DataLayerException | MD5Exception | ConfigPropertiesException e) {
			throw new ServiceLayerException(e.getMessage());
		}
		
		return voUser;
	}
	
	@Override
	public VOUser update(int id, VOUser voUser) throws ServiceLayerException {
		
		try {
			
			User user = new User(voUser);
			User currentUser = iDAOUsers.getObject(id);
			
			if(currentUser.getUserType() == UserType.ADMINISTRATOR & currentUser.getUserStatus() == UserStatus.ACTIVE
					& currentUser.getUserType() != user.getUserType())
			{
				if(IsLastUserAdmin(currentUser))
					throw new ServiceLayerException("No se puede modificar el tipo de usuario, el sistema debe tener al menos un usuario Administrador");
			}
			
			iDAOUsers.update(id, user);
		
			return getUser(id);
			
		}catch (DataLayerException e) {
			throw new ServiceLayerException(e.getMessage());
		}
	}
	
	@Override
	public void forgotPassord(String userEmail) throws ServiceLayerException {
		
		try {

			 User user = iDAOUsers.getUserByUserEmail(userEmail);
		     if(user != null)
		     {
		    	 String password = HashMD5.Decrypt(user.getPassword());
		    	 Email.forgotPassword(user.getEmail(), password);
		     }
		     else
		    	 throw new ServiceLayerException("No existe un usuario con ese correo electronico");
			
		}catch (DataLayerException | EmailException| MD5Exception e) {
			throw new ServiceLayerException(e.getMessage());
		}
	}
	
	@Override
	public void resetPassword(int id) throws ServiceLayerException
	{
		try
		{
			User user = iDAOUsers.getObject(id);
			if(user != null)
			{
				String newPassword = generateRandomPassword();
				String hashPassword = HashMD5.Encrypt(newPassword);
				
				iDAOUsers.updatePassword(id, hashPassword);
				
				Email.resetPassword(user.getEmail(), newPassword);
			}
			else
				throw new ServiceLayerException("No existe un usuario con ese id");
			
		}catch (DataLayerException | EmailException | MD5Exception e) {
			throw new ServiceLayerException(e.getMessage());
		}
	}
	
	@Override
	public void changePassword(int id, String oldPassword, String newPassword) throws ServiceLayerException
	{
		try
		{
			User user = iDAOUsers.getObject(id);
			if(user != null)
			{
				String hashOldPassword = HashMD5.Encrypt(oldPassword);
				if(user.getPassword().equals(hashOldPassword))
				{
					if(!oldPassword.equals(newPassword))
					{
						String hashNewPassword = HashMD5.Encrypt(newPassword);
						iDAOUsers.updatePassword(id, hashNewPassword);
						
						Email.changePassword(user.getEmail(), newPassword);
					}
					else
						throw new ServiceLayerException("La contraseña nueva no puede ser igual a la anterior");
				}
				else
					throw new ServiceLayerException("La contraseña antigua no se corresponde con la ingresada");
			}
			else
				throw new ServiceLayerException("No existe un usuario con ese id");
				
		}catch (DataLayerException | EmailException | MD5Exception e) {
			throw new ServiceLayerException(e.getMessage());
		}
	}
	
	@Override
	public void unlockUser(int id) throws ServiceLayerException
	{
		try
		{
			User user = iDAOUsers.getObject(id);
			if(user != null)
			{
				if(user.getUserStatus() == UserStatus.BLOCKED)
				{
					iDAOUsers.update(user.getId(), UserStatus.ACTIVE, 0, null);
				}
			}
			else
				throw new ServiceLayerException("No existe un usuario con ese id");
			
		}catch (DataLayerException e) {
			throw new ServiceLayerException(e.getMessage());
		}
	}
	
	VOUser BuildVoUser(User user)
	{
		VOUser voUser = new VOUser();
		voUser.setId(user.getId());
		voUser.setName(user.getName());
		voUser.setUserName(user.getUserName());
		voUser.setLastName(user.getLastName());
		voUser.setEmail(user.getEmail());
		voUser.setUserType(user.getUserType().getValue());
		voUser.setUserStatus(user.getUserStatus().getValue());
		
		return voUser;
	}
	
	boolean IsLastUserAdmin(User user) throws DataLayerException
	{
		ArrayList<User> adminUsers = iDAOUsers.getUsersByTypeAndStatus(UserType.ADMINISTRATOR, UserStatus.ACTIVE);
		
		int index = 0;
		
		for(User adminUser : adminUsers)
		{
			if(adminUser.getId() == user.getId())
				break;
			
			index = index + 1;
		}
		
		adminUsers.remove(index);
		
		if(adminUsers.size() == 0)
			return true;
		else
			return false;
		
	}

	String generateRandomPassword()
    {
      String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

      String pw = "";
      for (int i=0; i<PASSWORD_LENGTH; i++)
      {
          int index = (int)(RANDOM.nextDouble()*letters.length());
          pw += letters.substring(index, index+1);
      }
      return pw;
    }
}
