package utils;

public class ValidationUtils {

	/**
	 * Validates if the provided string contains only alphabetical characters and spaces.
	 * 
	 * @param str The string to be validated.
	 * @return True if the string contains only alphabetical characters and spaces, false otherwise.
	 */
	public static boolean isAlphabetical(String str) {
		String pattern = "[a-zA-Z ]+";
		return str.toLowerCase().matches(pattern);
	}
	
	/**
     * Validates if the provided text contains only digits (0-9).
     * 
     * @param text The text to be validated.
     * @return True if the text contains only digits, false otherwise.
     */
    public static boolean isNumeric(String text) {
        return text.matches("\\d+"); // Match digits only
    }
    

    /**
     * Validates if the provided string meets the criteria for a valid username.
     * 
     * @param str The username to be validated.
     * @return True if the username meets the criteria, false otherwise.
     */
	public static boolean isUsername(String str) {
		String pattern = "^(?=.*[a-zA-Z])[a-zA-Z0-9_]{6,}$";
		return str.matches(pattern);
	}

	/**
	 * Validates if the provided string is in a valid phone number format.
	 * 
	 * @param str The phone number to be validated.
	 * @return True if the phone number is in a valid format, false otherwise.
	 */
	public static boolean isPhone(String str) {
		String pattern = "^\\+\\d{3}\\-\\d{10}$";
		return str.matches(pattern);
	}

	/**
	 * Validates if the provided string meets the criteria for a valid password.
	 * 
	 * @param str The password to be validated.
	 * @return True if the password meets the criteria, false otherwise.
	 */
	public static boolean isPassword(String str) {
		String pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{7,}$";
		return str.matches(pattern);
	}
	
	/**
	 * Checks if the provided string represents a valid image file based on its extension.
	 * 
	 * @param str The string representing the image file name.
	 * @return True if the string represents a valid image file, false otherwise.
	 */
	public static boolean isImage(String str) { 
	    return str.endsWith(".jpg") || str.endsWith(".jpeg") || str.endsWith(".png") ||
	           str.endsWith(".gif") || str.endsWith(".bmp") || str.endsWith(".tif") ||
	           str.endsWith(".tiff") || str.endsWith(".svg");	
	}
	
	/**
     * Validates if the provided text is a valid email address format.
     * 
     * @param email The email address to be validated.
     * @return True if the email address has a valid format, false otherwise.
     */
    public static boolean isEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,}$"); // Match standard email pattern
    }
}
