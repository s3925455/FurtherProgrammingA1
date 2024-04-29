package venue;
/**
 * 
 */


import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class TimeValidator {

	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mma");

	public static boolean isValidTime(String time) {
		try {
			TIME_FORMATTER.parse(time);
			return true;
		} catch (DateTimeParseException e) {
			return false;
		}
	}

	public static String calculateEndTime(String time, int numberOfHours) {
		// TODO Auto-generated method stub
		return null;
	}

	public static boolean timeRangeOverlaps(String time, String endTime, Object startTime, Object endTime2) {
		// TODO Auto-generated method stub
		return false;
	}
}
