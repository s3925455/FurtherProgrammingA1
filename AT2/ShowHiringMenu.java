/**
 * 
 */
package AT2;

import java.util.Scanner;

import AT2.DateValidator;
import AT2.TimeValidator;
import AT2.Venue;

/**
 * @author amitmunjal
 *
 */
public class ShowHiringMenu {
	
		
		private static void showHiringMenu(Venue venue) {
			Scanner scanner = new Scanner(System.in);

			System.out.println("\n-----------------------");
			System.out.println("Hiring Details");
			System.out.println("-----------------------");
			
			
			System.out.print("Please enter a request number for this order: ");
			String requestNumber = scanner.nextLine();

			System.out.print("Please enter number of hours: ");
			int numberOfHours = extracted(scanner);

			String date;
			do {
				System.out.print("Please enter date dd/mm/yyyy: ");
				date = scanner.nextLine();
				if (!DateValidator.isValidDate(date)) {
					System.out.println("Invalid date format. Please enter the date in dd/mm/yyyy format.");
				}
			} while (!DateValidator.isValidDate(date));

			while (true) {
				System.out.print("Please enter timet (hh:mmam/pm): ");
				String time = scanner.nextLine();

				if (TimeValidator.isValidTime(time)) {
					System.out.println("Time entered: " + time);
					break; // Exit the loop if the time format is valid
				} else {
					System.out.println("Invalid time format. Please enter time in hh:mmam/pm format.");
				}
			}

			System.out.print("Event name: ");
			String eventName = scanner.nextLine();

			System.out.print("Artist name: ");
			String artistName = scanner.nextLine();

			System.out.print("Requester name: ");
			String requesterName = scanner.nextLine();
			
			System.out.print("Event Type: ");
			String eventType = scanner.nextLine();

			System.out.print("Confirm order (y/n): \n");
			String confirmOrder = scanner.nextLine();

			if (confirmOrder.equalsIgnoreCase("y")) {
				String time = null;
				// Save the hiring details to memory or file
				saveHiringDetails(requestNumber,venue, numberOfHours, date, time, eventName, artistName, requesterName, eventType);
				System.out.println("Order confirmed.\n");
			} else if (confirmOrder.equalsIgnoreCase("n")) {
				System.out.println("Order canceled.\n");
			} else {
				System.out.println("\nInvalid input. Please enter 'y' for yes or 'n' for no.\n");
			}
			Object orderSummaries;
			orderSummaries.add(orderSummary);
		}

		private static int extracted(Scanner scanner) {
			int numberOfHours = getUserInput(scanner);
			return numberOfHours;
		}

		private static void saveHiringDetails(String requestNumber, Venue venue, int numberOfHours, String date,
				String time, String eventName, String artistName, String requesterName, String eventType) {
			// TODO Auto-generated method stub
			
		}
}