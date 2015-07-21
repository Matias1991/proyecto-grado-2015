package utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static boolean validateMail(String email) {
//		Pattern p = Pattern
//				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$;");
//		Matcher emailFormat = p.matcher(email);
//		return emailFormat.find();
		return true;
	}

	public static double parseDecimal(String number) throws ParseException  {
		double result = 0;
		if (number != null && !"".equals(number)) {
			String mask = "#,##0.00";
			DecimalFormat formatter = new DecimalFormat(mask);
			Number resultAux = formatter.parse(number);
			if (resultAux != null) {
				result = resultAux.doubleValue();
			}
		}
		return result;
	}
	
	
}
