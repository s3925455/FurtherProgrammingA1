package venue;




import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateValidator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static boolean isValidDate(String date) {
        try {
            LocalDate parsedDate = LocalDate.parse(date, DATE_FORMATTER);

            // Validate day, month, and year components
            int day = parsedDate.getDayOfMonth();
            int month = parsedDate.getMonthValue();
            int year = parsedDate.getYear();

            // Check if the year is valid (not negative or too far in the future)
            if (year < 0 || year > 9999) {
                return false;
            }

            // Check if the month is valid (between 1 and 12)
            if (month < 1 || month > 12) {
                return false;
            }

            // Check if the day is valid for the given month and year
            if (day < 1 || day > parsedDate.lengthOfMonth()) {
                return false;
            }

            // If all checks pass, the date is considered valid
            return true;
        } catch (DateTimeParseException e) {
            // If parsing fails, the date is not valid
            return false;
        }
    }
}

