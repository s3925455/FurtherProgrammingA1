/**
 * 
 */
package AT2;

import java.util.ArrayList;
import java.util.List;

import AT2.Venue;

/**
 * @author amitmunjal
 *
 */


public class SaveHiringDetails {
	
	private static List<String> finalSummaries = new ArrayList<>();
	private static List<String> orderSummaries = new ArrayList<>(); // Maintain order summaries
	private Object categoryChoice;
	// Get the selected category
	String selectedCategory = getCategoryChoice(categoryChoice);

	/**
	 * @param args
	 */
	// Method to save hiring details
		private static void saveHiringDetails(String requestNumber, Venue venue, int numberOfHours, String date, String time, String eventName,
				String artistName, String requesterName, String eventType) {
			// Save hiring details to memory or file
			String orderSummary = String.format(
					"\n Order Summary\n" + "--------------------------------------------------\n" + "Request No: %s\n" +"Client: %s\n"
							+ "Venue: %s\n" + "Event name: %s\n" + "Artist: %s\n" + "Event date (dd/mm/yyyy): %s\n"
							+ "Event time: %s\n" + "%d hours venue hire @ $550 -is: $%d\n"
							+ "Brokering commission 10%%: $%.2f\n",
							requestNumber,requesterName, venue.getName(), eventName, artistName, date, time, numberOfHours, numberOfHours * 550,
					numberOfHours * 550 * 0.1);

			orderSummaries.add(orderSummary);

			//*** String for saving to file ***
			String finalSummary = null;
			try {
				finalSummary = String.format("%s,%s,%s,%s,%s,%s,%d,%s,%s",
				        requesterName, venue.getName(), eventName, artistName, date, time, numberOfHours, eventType, getCategoryChoice(0));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			finalSummaries.add(finalSummary);

		}

	private static String getCategoryChoice(Object categoryChoice2) {
		// TODO Auto-generated method stub
		return null;
	}

}
