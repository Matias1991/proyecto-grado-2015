package servicelayer.core;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import datalayer.daos.DAOManager;
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

	private static final Random RANDOM = new SecureRandom();
	public static final int PASSWORD_LENGTH = 8;

	private CoreUser() {
	}

	public static CoreUser GetInstance() throws ServerException {
		if (instance == null) {
			instance = new CoreUser();
		}
		return instance;
	}

	@Override
	public void insertUser(User user) throws ServerException,
			ClientException {

		DAOManager daoManager = new DAOManager();
		try {
			if (daoManager.getDAOUsers()
					.getUserByUserName(user.getUserName()) == null) {
				if (!daoManager.getDAOUsers().existsEmail(user.getEmail())) {
					// setear el estado del usuario en activo
					user.setUserStatus(UserStatus.ACTIVE);

					String newPassword = generateRandomPassword();

					// encriptar el password del usuario
					String hashPassword = HashMD5.Encrypt(newPassword);
					user.setPassword(hashPassword);

					daoManager.getDAOUsers().insert(user);

					try {

						Email.newUser(user, newPassword);

					} catch (EmailException e) {
						throw new ClientException(
								"El usuario fue creado pero ocurrio un error al enviar el correo electrónico");
					}
				} else {
					throw new ClientException(
							"Ya existe un usuario con este correo electrónico");
				}
			} else
				throw new ClientException(
						"Ya existe un usuario con este nombre de usuario");

			daoManager.commit();

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public void deleteUser(int id) throws ServerException, ClientException {

		DAOManager daoManager = new DAOManager();
		try {
			User user = daoManager.getDAOUsers().getObject(id);
			if (user == null)
				throw new ClientException("No existe un usuario con ese id");

			if (user.getUserType() == UserType.ADMINISTRATOR
					& user.getUserStatus() == UserStatus.ACTIVE) {
				if (IsLastUserAdmin(user, daoManager.getDAOUsers()))
					throw new ClientException(
							"No se puede eliminar este usuario, el sistema debe tener al menos un usuario Administrador");
			}

			daoManager.getDAOUsers().delete(id);
			daoManager.commit();

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public User getUser(int id) throws ServerException, ClientException {

		User user;
		DAOManager daoManager = new DAOManager();
		try {

			user = daoManager.getDAOUsers().getObject(id);
			if (user == null)
				throw new ClientException("No existe un usuario con ese id");

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
		return user;
	}

	@Override
	public boolean existUser(int id) throws ServerException {
		DAOManager daoManager = new DAOManager();
		try {
			return daoManager.getDAOUsers().exist(id);

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public ArrayList<User> getUsers() throws ServerException {

		DAOManager daoManager = new DAOManager();
		try {

			return daoManager.getDAOUsers().getObjects();

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public User login(String userName, String password)
			throws ServerException, ClientException {

		User user = null;
		DAOManager daoManager = new DAOManager();
		try {
			// tOdo: move this to UI
			String hashPassword = HashMD5.Encrypt(password);
			//
			user = daoManager.getDAOUsers().getUserByUserName(userName);
			if (user != null) {
				if (user.getUserStatus() == UserStatus.ACTIVE) {

					Date now = new Date();
					if (user.getPassword().equals(hashPassword)) {
						daoManager.getDAOUsers().update(user.getId(),
								UserStatus.ACTIVE, 0, now);
					} else {

						if (!IsLastUserAdmin(user, daoManager.getDAOUsers())) {
							long diffInMinutes = 0;
							if (user.getLastAttemptDateTimeUTC() != null) {
								Date lastAttemptDate = user
										.getLastAttemptDateTimeUTC();
								long duration = now.getTime()
										- lastAttemptDate.getTime();
								diffInMinutes = TimeUnit.MILLISECONDS
										.toMinutes(duration);
							}

							if (diffInMinutes > Integer
									.parseInt(ConfigurationProperties
											.GetConfigValue("EXPIRATION_TIME_ATTEMPTS_IN_MINUTES"))) {
								daoManager.getDAOUsers().update(user.getId(),
										UserStatus.ACTIVE, 1, now);
							} else {
								int attempts = user.getAttempts() + 1;

								int maxAttemptsLogin = Integer
										.parseInt(ConfigurationProperties
												.GetConfigValue("MAX_ATTEMPTS_LOGIN"));
								if (attempts >= maxAttemptsLogin) {
									daoManager.getDAOUsers().update(
											user.getId(), UserStatus.BLOCKED,
											attempts, now);
								} else {
									daoManager.getDAOUsers().update(
											user.getId(), UserStatus.ACTIVE,
											attempts, now);
								}
							}
						}
						throw new ClientException(
								"Usuario y/o constraseña incorrecta");
					}
				} else
					throw new ClientException("El usuario esta bloquedo");
			} else
				throw new ClientException("Usuario y/o constraseña incorrecta");

			daoManager.commit();

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
		return user;
	}

	@Override
	public User update(int id, User user) throws ServerException,
			ClientException {

		DAOManager daoManager = new DAOManager();
		try {
			User currentUser = daoManager.getDAOUsers().getObject(id);

			if (currentUser.getUserType() == UserType.ADMINISTRATOR
					& currentUser.getUserStatus() == UserStatus.ACTIVE
					& currentUser.getUserType() != user.getUserType()) {
				if (IsLastUserAdmin(currentUser, daoManager.getDAOUsers()))
					throw new ClientException(
							"No se puede modificar el tipo de usuario, el sistema debe tener al menos un usuario Administrador");
			}
			if (!user.getEmail().equals(currentUser.getEmail())
					&& daoManager.getDAOUsers().existsEmail(user.getEmail())) {
				throw new ClientException(
						"Ya existe un usuario con este correo electrónico");
			}

			daoManager.getDAOUsers().update(id, user);
			daoManager.commit();

			return getUser(id);

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public void forgotPassord(String userEmail) throws ServerException,
			ClientException {

		DAOManager daoManager = new DAOManager();
		try {
			User user = daoManager.getDAOUsers().getUserByUserEmail(userEmail);
			if (user != null) {
				String password = HashMD5.Decrypt(user.getPassword());
				try {
					Email.forgotPassword(user.getEmail(), user.getUserName(),
							password);
				} catch (EmailException e) {
					throw new ClientException(
							"Ocurrio un error al enviar el correo electrónico");
				}
			} else
				throw new ClientException(
						"No existe un usuario con ese correo electronico");

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public void resetPassword(int id) throws ServerException, ClientException {
		DAOManager daoManager = new DAOManager();
		try {
			User user = daoManager.getDAOUsers().getObject(id);
			if (user != null) {
				String newPassword = generateRandomPassword();
				String hashPassword = HashMD5.Encrypt(newPassword);

				daoManager.getDAOUsers().updatePassword(id, hashPassword);

				try {
					Email.resetPassword(user.getEmail(), user.getUserName(),
							newPassword);
				} catch (EmailException e) {
					throw new ClientException(
							"La contraseña fue reseteada correctamente pero ocurrio un error al enviar el correo electrónico");
				}
			} else
				throw new ClientException("No existe un usuario con ese id");

			daoManager.commit();

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public void changePassword(int id, String oldPassword, String newPassword)
			throws ServerException, ClientException {
		DAOManager daoManager = new DAOManager();
		try {
			User user = daoManager.getDAOUsers().getObject(id);
			if (user != null) {
				String hashOldPassword = HashMD5.Encrypt(oldPassword);
				if (user.getPassword().equals(hashOldPassword)) {
					if (!oldPassword.equals(newPassword)) {
						String hashNewPassword = HashMD5.Encrypt(newPassword);
						daoManager.getDAOUsers().updatePassword(id,
								hashNewPassword);

						try {
							Email.changePassword(user.getEmail(), newPassword);
						} catch (EmailException e) {
							throw new ClientException(
									"La contraseña fue cambiada correctamente pero ocurrio un error al enviar el correo electrónico");
						}
					} else
						throw new ClientException(
								"La contraseña nueva no puede ser igual a la anterior");
				} else
					throw new ClientException(
							"La contraseña antigua no se corresponde con la ingresada");
			} else
				throw new ClientException("No existe un usuario con ese id");

			daoManager.commit();

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public void unlockUser(int id) throws ServerException, ClientException {
		DAOManager daoManager = new DAOManager();
		try {
			User user = daoManager.getDAOUsers().getObject(id);
			if (user != null) {
				if (user.getUserStatus() == UserStatus.BLOCKED)
					daoManager.getDAOUsers().update(user.getId(),
							UserStatus.ACTIVE, 0, null);
			} else
				throw new ClientException("No existe un usuario con ese id");

			daoManager.commit();

		} catch (ServerException e) {
			daoManager.rollback();
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public ArrayList<User> getUsersByStatus(int userStatusId)
			throws ServerException {

		DAOManager daoManager = new DAOManager();
		try {
			UserStatus userStatus = UserStatus.getEnum(userStatusId);
			return daoManager.getDAOUsers().getUsersByStatus(userStatus);

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public ArrayList<User> getUsersByType(int userTypeId)
			throws ServerException {

		DAOManager daoManager = new DAOManager();
		try {
			UserType userType = UserType.getEnum(userTypeId);
			return daoManager.getDAOUsers().getUsersByType(userType);

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}

	@Override
	public ArrayList<User> getUsersByTypeIdAndStatus(int userStatusId,
			int usersTypeId) throws ServerException {

		DAOManager daoManager = new DAOManager();
		try {
			UserStatus userStatus = UserStatus.getEnum(userStatusId);
			UserType userType = UserType.getEnum(usersTypeId);
			return daoManager.getDAOUsers().getUsersByTypeAndStatus(userType,
					userStatus);

		} catch (ServerException e) {
			throw e;
		} finally {
			daoManager.close();
		}
	}

	boolean IsLastUserAdmin(User user, IDAOUsers iDAOUsers)
			throws ServerException {
		ArrayList<User> adminUsers = iDAOUsers.getUsersByTypeAndStatus(
				UserType.ADMINISTRATOR, UserStatus.ACTIVE);
		boolean found = false;
		int index = 0;

		for (User adminUser : adminUsers) {
			if (adminUser.getId() == user.getId()) {
				found = true;
				break;
			}

			index = index + 1;
		}

		if (found) {
			adminUsers.remove(index);
		}

		if (adminUsers.size() == 0)
			return true;
		else
			return false;

	}

	String generateRandomPassword() {
		String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789+@";

		String pw = "";
		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			int index = (int) (RANDOM.nextDouble() * letters.length());
			pw += letters.substring(index, index + 1);
		}
		return pw;
	}

}
