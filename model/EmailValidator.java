package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class provides methods that can be useful to test
 * if an email address is valid or not.
 * @author Alessandro Giovannacci
 * @version 1.0
 */
public final class EmailValidator {
	
	final Pattern VALID_EMAIL_ADDRESS_REGEX =  Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	/**
	 * It checks if the string given by parameter is a valid email string or not
	 * @param emailStr the email string that has to be checked
	 * @return <code>true</code> if the email string is valid, <code>false</code> otherwise
	 */
	public boolean isEmailValid(String emailStr){
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
		return matcher.find();
	}
	
}