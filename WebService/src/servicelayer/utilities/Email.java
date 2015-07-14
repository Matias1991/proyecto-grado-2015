package servicelayer.utilities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.httpclient.UsernamePasswordCredentials;

import servicelayer.entity.businessEntity.User;
import shared.ConfigurationProperties;
import shared.LoggerMSMP;
import shared.exceptions.ConfigPropertiesException;
import shared.exceptions.EmailException;

public class Email {

	public static void changePassword(String userEmail, String password) throws EmailException{
		
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<b>Contraseña: </b>" + password);

		sendEmail(userEmail, "[MSMP] - Cambio de Contraseña", strBuilder.toString());
	}

	public static void resetPassword(String userEmail, String password) throws EmailException{
		
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<b>Nueva contraseña: </b>" + password);

		sendEmail(userEmail, "[MSMP] - Reseteo de Contraseña", strBuilder.toString());
	}
	
	public static void forgotPassword(String userEmail, String password) throws EmailException {
		
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<b>Contraseña: </b>" + password);

		sendEmail(userEmail, "[MSMP] - Olvido de Contraseña", strBuilder.toString());
	}

	public static void newUser(User user, String password) throws EmailException {
		
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append("<p><b>Datos del nuevo usuario</b><p>");
		strBuilder.append("<b>Nombre:</b> " + user.getName());
		strBuilder.append("<br>");
		strBuilder.append("<b>Nombre de usuario:</b> " + user.getUserName());
		strBuilder.append("<br>");
		strBuilder.append("<b>Contraseña: </b>" + password);
		strBuilder.append("<p>Haga click <a href='http://localhost:8080/webvaadin/'>aqui</a> para ingresar al sistema por primera vez</p>");

		sendEmail(user.getEmail(), "[MSMP] - Nuevo usuario", strBuilder.toString());
	}

	static void sendEmail(String toEmail, String subject, String text)throws EmailException {

		String fromEmail = null;
		try {

			final String username = ConfigurationProperties.GetConfigValue("SMTP_GMAIL_USER");
			final String password = ConfigurationProperties.GetConfigValue("SMTP_GMAIL_PASSWORD");

			fromEmail = username;
			
			String host = "smtp.gmail.com";
			Multipart multiPart = new MimeMultipart("alternative");
			MimeBodyPart htmlPart = new MimeBodyPart();

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}
					});

			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(username, "Notificacion MSMP"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

			message.setSubject(subject);

			htmlPart.setContent(text, "text/html; charset=utf-8");
			multiPart.addBodyPart(htmlPart);
			message.setContent(multiPart);

			Transport.send(message);

		} catch (MessagingException e) {
			String numberError = LoggerMSMP.setLog(buildSendEmailException(e, fromEmail, toEmail));
			throw new EmailException("Problemas al enviar el correo con el nuevo usuario, consulte con el Administrador con el codigo de error: " + numberError);
		} catch (ConfigPropertiesException e) {
			throw new EmailException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new EmailException("Problemas al enviar el correo con el nuevo usuario, consulte con el Administrador");
		}
	}

	static String buildSendEmailException(Exception ex, String fromEmail, String toEmail) {
		
		StringBuilder strBuilder = new StringBuilder();
		strBuilder.append(System.getProperty("line.separator"));
		strBuilder.append("From email: " + fromEmail);
		strBuilder.append(System.getProperty("line.separator"));
		strBuilder.append("To Email:" + toEmail);
		strBuilder.append(System.getProperty("line.separator"));
		strBuilder.append("Exeception: " + ex.getMessage());

		return strBuilder.toString();
	}
}
