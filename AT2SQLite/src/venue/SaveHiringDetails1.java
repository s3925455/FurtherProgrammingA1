package venue;

import java.util.ArrayList;
import java.util.List;

public class SaveHiringDetails1 {
    private static List<String> orderSummaries = new ArrayList<>(); // Maintain order summaries

    // Method to save hiring details
    public static void saveHiringDetails(Venue venue, int numberOfHours, String date, String time, String eventName,
                                         String artistName, String requesterName, String eventType) {

        // Save hiring details to memory or file
        String orderSummary = String.format("\n Order Summary\n"
                        + "--------------------------------------------------\n" + "Request No: %s\n" + "Client: %s\n"
                        + "Venue: %s\n" + "Event name: %s\n" + "Artist: %s\n" + "Event date (dd/mm/yyyy): %s\n"
                        + "Event time: %s\n" + "%d hours venue hire @ $%d -is: $%d\n" + "Brokering commission 10%%: $%.2f\n",
                requesterName, venue.getName(), eventName, artistName, date, time, numberOfHours,
                numberOfHours * HireDetails.getRate(), numberOfHours * HireDetails.getRate() * 0.1);

        orderSummaries.add(orderSummary);

        // No need to save to session here, as it's done in DisplayOrderSummary

        System.out.println("Order Summaries added: " + orderSummaries); //  line for debugging
    }

    public static List<String> getOrderSummaries() {
        return orderSummaries;
    }

    public static List<String> getFinalSummaries() {
        return new ArrayList<>(); 
    }

    public static void setVenueDetails(String selectedVenueName, int selectedVenueRate) {
        HireDetails.setVenueName(selectedVenueName);
        HireDetails.setSelectedVenueRate(selectedVenueRate);
    }
}
