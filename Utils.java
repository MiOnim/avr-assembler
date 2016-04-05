package assembler;

/**
 * 
 * @author MI ONIM
 *
 */
public class Utils
{
	public static final char COLON = ':';
	public static final String LINE_SEPARATOR = "line.separator";
	public static final String WHITESPACE = "\\s";
	
	public static boolean isAlpha(char x)
	{
		return ((x >= 'a' && x <= 'z') || (x >= 'A' && x <= 'Z'));
	}
	
	public static boolean isAlpha(String word)
	{
		char[] chars = word.toCharArray();

	    for (char c : chars) {
	        if(!isAlpha(c)) {
	            return false;
	        } //if
	    } //for

	    return true;
	}
	
	public static boolean isInteger(String number)
	{
		try {
			Integer.parseInt(number);
		} //try
		catch (Exception e) {
			return false;
		} //catch
		
		return true;
	}

	public static String removeTrailingComma(String part)
	{
		if (part.charAt(part.length()-1) == ',') {
			part = part.substring(0, part.length()-1);
		} //if
		
		return part;
	}
}
