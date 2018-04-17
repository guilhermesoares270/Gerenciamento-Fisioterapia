package utility;

import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidate {
	
	Logger logger = Logger.getLogger(InputValidate.class.getName());
	
	public static final Pattern VALID_EMAIL_FORMAT = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	public static final Pattern CPF_FORMAT = Pattern.compile("[0-9].[0-9].[0-9]+-[0-9]");
	public static final Pattern FLOAT_FORMAT = Pattern.compile("\\d{0,7}([\\.]\\d{0,4})?");
	public static final Pattern INTEGER_FORMAT = Pattern.compile("\\d{0,10}");
	
	public boolean validateEmail(String email) {
		Matcher matcher = VALID_EMAIL_FORMAT.matcher(email);
		return matcher.find();
	}
	
	public void replaceInput(String entrada) {
		entrada.replaceAll("()[]-", "");
	}
	
	public void TextInput(String entrada) {
		
	}
	
    public boolean intInput(String entrada) {
            try {
                Integer.parseInt(entrada);
                return true;
            }catch(InputMismatchException | NumberFormatException e) {
                logger.log(Level.WARNING, e.getMessage(), e);
                return false;
            }
	}
    
    public boolean floatInput(String entrada) {
    	try {
    		Float.parseFloat(entrada);
    		return true;
    	}catch(InputMismatchException | NumberFormatException e) {
    		logger.log(Level.WARNING, e.getMessage(), e);
    		return false;
    	}
    }
    
    public boolean doubleInput(String entrada) {
    	try {
    		Double.parseDouble(entrada);
    		return true;
    	}catch(InputMismatchException | NumberFormatException e) {
    		return false;
    	}
    }
    
}
