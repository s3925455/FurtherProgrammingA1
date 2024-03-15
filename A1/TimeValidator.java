/**
 * 
 */
package A1;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeValidator {

	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm a");

	public static boolean isValidTime(String time) {
		try {
			TIME_FORMATTER.parse(time);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}
}
