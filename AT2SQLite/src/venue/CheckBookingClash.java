package venue;


import java.util.List;

public class CheckBookingClash {

	public boolean checkBookingClash(String date, String time, int numberOfHours, List<VenueMatcher> oldJobRequests) {
        // Calculate end time based on the start time and number of hours
        String endTime = TimeValidator.calculateEndTime(time, numberOfHours);

        // Iterate through existing bookings to check for clashes
        for (VenueMatcher booking : oldJobRequests) {
            if (booking.getDate().equals(date)) {
                // Check if the time range of the new booking overlaps with any existing booking
                if (TimeValidator.timeRangeOverlaps(time, endTime, booking.getStartTime(), booking.getEndTime())) {
                    return true; // Clash detected
                }
            }
        }
        return false; // No clash found
    }

}
