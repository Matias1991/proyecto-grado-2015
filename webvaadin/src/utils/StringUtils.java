package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {

	public static boolean validateMail(String email) {
		Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$;");
		Matcher emailFormat = p.matcher(email);
		return emailFormat.find();
	}

}
