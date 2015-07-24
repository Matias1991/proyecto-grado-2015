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
import shared.exceptions.ClientException;
import shared.exceptions.EmailException;
import shared.exceptions.ServerException;
import shared.interfaces.core.ICoreUser;
import shared.interfaces.dataLayer.IDAOUsers;


public class CoreUser implements ICoreUser {
	
	private static CoreUser instance = null;
	private IDAOUsers iDAOUsers;

	private static final Random RANDOM = new SecureRandom();
	public static final int PASSWORD_LENGTH = 8;
	
	private CoreUser() throws ServerException
	{
		iDAOUsers = new DAOUsers();
	}
	
	public static CoreUser GetInstance() throws ServerException
	{
		if(instance == null)
		{
			instance = new CoreUser();
		}
		return instance;
	}

	@Override
	public void insertUser(VOUser voUser) throws ServerException, ClientException {
	
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

			try {
				
				Email.newUser(user, newPassword);
				
			} catch (EmailException e) {
				throw new ClientException("El usuario fue creado pero ocurrio un error al enviar el correo electrónico");
			}
		}
		else
			throw new ClientException("Ya existe un usuario con este nombre de usuario");
	}

	@Override
	public void deleteUser(int id) throws ServerException, ClientException {
		
		User user = iDAOUsers.getObject(id);
		if(user == null)
			throw new ClientException("No existe un usuario con ese id");
		
		if(user.getUserType() == UserType.ADMINISTRATOR & user.getUserStatus() == UserStatus.ACTIVE)
		{
			if(IsLastUserAdmin(user))
				throw new ClientException("No se puede eliminar este usuario, el sistema debe tener al menos un usuario Administrador");
		}
		
		iDAOUsers.delete(id);
	}

	@Override
	public VOUser getUser(int id) throws ServerException, ClientException {
		
		User user;
		VOUser voUser = null;

		user = iDAOUsers.getObject(id);
		if(user != null)
			voUser = BuildVOUser(user);
		else
			throw new ClientException("No existe un usuario con ese id");

		return voUser;
	}

	@Override
	public boolean existUser(int id)  throws ServerException{

		return iDAOUsers.exist(id);
	}

	@Override
	public ArrayList<VOUser> getUsers() throws ServerException{
		
		ArrayList<User> users;
		ArrayList<VOUser> voUsers = null;

		users = iDAOUsers.getObjects();
		voUsers = new ArrayList<VOUser>(); 
		
		for(User user: users)
		{
			voUsers.add(BuildVOUser(user));
		}
		
		return voUsers;
	}
	
	@Override
	public VOUser login(String userName, String password) throws ServerException, ClientException
	{
		VOUser voUser = null;

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
					voUser = BuildVOUser(user);
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
					throw new ClientException("Usuario y/o constraseña incorrecta");
				}
			}
			else
				throw new ClientException("El usuario esta bloquedo");
		}

		return voUser;
	}
	
	@Override
	public VOUser update(int id, VOUser voUser) throws ServerException, ClientException {
		
		User user = new User(voUser);
		User currentUser = iDAOUsers.getObject(id);
		
		if(currentUser.getUserType() == UserType.ADMINISTRATOR & currentUser.getUserStatus() == UserStatus.ACTIVE
				& currentUser.getUserType() != user.getUserType())
		{
			if(IsLastUserAdmin(currentUser))
				throw new ClientException("No se puede modificar el tipo de usuario, el sistema debe tener al menos un usuario Administrador");
		}
		
		iDAOUsers.update(id, user);
	
		return getUser(id);
	}
	
	@Override
	public void forgotPassord(String userEmail) throws ServerException, ClientException {
		
		 User user = iDAOUsers.getUserByUserEmail(userEmail);
	     if(user != null)
	     {
	    	 String password = HashMD5.Decrypt(user.getPassword());
	    	 try {
				Email.forgotPassword(user.getEmail(), password);
			} catch (EmailException e) {
				throw new ClientException("Ocurrio un error al enviar el correo electrónico");
			}
	     }
	     else
	    	 throw new ClientException("No existe un usuario con ese correo electronico");
	}
	
	@Override
	public void resetPassword(int id) throws ServerException, ClientException
	{
		User user = iDAOUsers.getObject(id);
		if(user != null)
		{
			String newPassword = generateRandomPassword();
			String hashPassword = HashMD5.Encrypt(newPassword);
			
			iDAOUsers.updatePassword(id, hashPassword);
			
			try {
				Email.resetPassword(user.getEmail(), newPassword);
			} catch (EmailException e) {
				throw new ClientException("La contraseña fue reseteada correctamente pero ocurrio un error al enviar el correo electrónico");
			}
		}
		else
			throw new ClientException("No existe un usuario con ese id");
	}
	
	@Override
	public void changePassword(int id, String oldPassword, String newPassword) throws ServerException, ClientException
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
					
					try {
						Email.changePassword(user.getEmail(), newPassword);
					} catch (EmailException e) {
						throw new ClientException("La contraseña fue cambiada correctamente pero ocurrio un error al enviar el correo electrónico");
					}
				}
				else
					throw new ClientException("La contraseña nueva no puede ser igual a la anterior");
			}
			else
				throw new ClientException("La contraseña antigua no se corresponde con la ingresada");
		}
		else
			throw new ClientException("No existe un usuario con ese id");
	}
	
	@Override
	public void unlockUser(int id) throws ServerException, ClientException
	{
		User user = iDAOUsers.getObject(id);
		if(user != null)
		{
			if(user.getUserStatus() == UserStatus.BLOCKED)
				iDAOUsers.update(user.getId(), UserStatus.ACTIVE, 0, null);
		}
		else
			throw new ClientException("No existe un usuario con ese id");
	}
	
	@Override
	public ArrayList<VOUser> getUsersByStatus(int userStatusId) throws ServerException
	{
		ArrayList<User> users;
		ArrayList<VOUser> voUsers = null;

		UserStatus userStatus = UserStatus.getEnum(userStatusId);
		users = iDAOUsers.getUsersByStatus(userStatus);
		voUsers = new ArrayList<VOUser>(); 
		
		for(User user: users)
		{
			voUsers.add(BuildVOUser(user));
		}
		
		return voUsers;
	}
	
	VOUser BuildVOUser(User user)
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
	
	boolean IsLastUserAdmin(User user) throws ServerException
	{
		ArrayList<User> adminUsers = iDAOUsers.getUsersByTypeAndStatus(UserType.ADMINISTRATOR, UserStatus.ACTIVE);
		boolean found = false;
		int index = 0;
		
		for(User adminUser : adminUsers)
		{
			if(adminUser.getId() == user.getId()){
				found = true;
				break;
			}
			
			index = index + 1;
		}
		
		if(found){
			adminUsers.remove(index);
		}		
		
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
