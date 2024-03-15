package A1;
/*
 * 
 * Amit Munjal
 * s3925455
 * 
 * Further Programming
 * A1- Wk5
 * 
 * */


import java.util.*;



//Main class to run the program-----
public class VenueMatcher {
 private static Scanner scanner = new Scanner(System.in);
 private static List<String> newJobRequests = new ArrayList<>();
 private static List<Venue> venues = new ArrayList<>(); // Declared venues here
 private static List<String> orderSummaries = new ArrayList<>(); // Maintain order summaries

 	// Method to run the program
 	public static void run() {
     FileHandler fileHandler = new FileHandler();
     List<String> existingJobRequests = null;

     // Read existing data from files
     try {
         venues = fileHandler.readVenuesFromFile("venues.csv");
         existingJobRequests = fileHandler.readJobRequestsFromFile("requests.csv");
     } catch (CustomException e) {
         System.out.println("Error: " + e.getMessage());
         return;
     }

     newJobRequests.addAll(existingJobRequests);

     // Main menu loop
     while (true) {
         displayMainMenu();

         int choice = getUserInput(1, 6);

         switch (choice) {
             case 1:
                 displayNewJobRequests();
                 break;
             case 2:
                 selectByCategory();
                 break;
             case 3:
                 selectFromVenueList();
                 break;
             case 4:
                 selectFromMatchingList();
                 break;
             case 5:
                 displayOrderSummary(); // Display order summary
                 break;
             case 6:
                 // Exit the program and write new job requests to file
                 try {
                     fileHandler.writeJobRequestsToFile(newJobRequests, "requests.csv");
                 } catch (CustomException e) {
                     System.out.println("Error: " + e.getMessage());
                 }
                 System.out.println("Exiting...");
                 return;
             default:
                 System.out.println("Please select a valid menu option.\n");
         }
     }
 }


    // Method to display new job requests
    private static void displayNewJobRequests() {
        if (newJobRequests.isEmpty()) {
            System.out.println("\nNo New Jobs Entered\n");
        } else {
            for (String jobRequest : newJobRequests) {
                System.out.println(jobRequest);
            }
        }
    }

    // Method to select venues by category
    private static void selectByCategory() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("2. Select by category");
        System.out.println("--------------------------------------------------");
        System.out.println("1) Outdoor");
        System.out.println("2) Indoor");
        System.out.println("3) Convertible");
        System.out.println("4) Go to main menu\n");

        int categoryChoice = getUserInput(1, 4);

        // Get the selected category
        String selectedCategory = getCategoryChoice(categoryChoice);

        // Display venues for the selected category and prompt for further action
        if (selectedCategory != null) {
            List<Venue> selectedVenues = displayVenues(selectedCategory);
            if (selectedVenues != null) {
                Venue selectedVenue = selectVenue(selectedVenues);
                if (selectedVenue != null) {
                    promptForAction(selectedVenue);
                }
            }
        }
    }

    // Method to get the selected category
    private static String getCategoryChoice(int categoryChoice) {
        switch (categoryChoice) {
            case 1:
                return "Outdoor";
            case 2:
                return "Indoor";
            case 3:
                return "Convertible";
            case 4:
                return null; // Go back to the main menu
            default:
                System.out.println("Invalid category choice.\n");
                return null; // Go back to the main menu
        }
    }

    // Method to display venues for a selected category
    private static List<Venue> displayVenues(String selectedCategory) {
        // Display venues for the selected category
        System.out.println("\n Venues under category: " + selectedCategory);
        int venueIndex = 1;
        List<Venue> selectedVenues = new ArrayList<>();
        for (Venue venue : venues) {
            if (venue.getCategory().equalsIgnoreCase(selectedCategory)) {
                System.out.println(venueIndex + ") " + venue.getName());
                selectedVenues.add(venue);
                venueIndex++;
            }
        }
        return selectedVenues.isEmpty() ? null : selectedVenues;
    }

    // Method to select a venue from the displayed venues
    private static Venue selectVenue(List<Venue> selectedVenues) {
        // Prompt the user to make a selection from the displayed venues
        System.out.print("\n Please select a venue: ");
        int venueChoice = getUserInput(1, selectedVenues.size()) - 1;
        return selectedVenues.get(venueChoice);
    }

    // Method to prompt the user for further action after selecting a venue
    private static void promptForAction(Venue selectedVenue) {
        // Display the attributes of the selected venue
        System.out.println("\n-----------------------");
        System.out.println(selectedVenue);

        // Prompt the user for further action
        System.out.println("\n --------------------------------------------------");
        System.out.println("1) Hire for $550.00/hour");
        System.out.println("2) Back to venue list\n");
        System.out.print("Please select: \n");
        int actionChoice = getUserInput(1, 2);

        switch (actionChoice) {
            case 1:
                // Show hiring menu
                showHiringMenu(selectedVenue);
                break;
            case 2:
                // Go back to the venue list
                break;
            default:
                System.out.println("\n Invalid action choice.\n");
        }
    }

    private static void showHiringMenu(Venue venue) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n-----------------------");
        System.out.println("Hiring Details");
        System.out.println("-----------------------");

        System.out.print("Please enter number of hours: ");
        int numberOfHours = getUserInput(scanner);

        String date;
        do {
            System.out.print("Please enter date dd/mm/yyyy: ");
            date = scanner.nextLine();
            if (!DateValidator.isValidDate(date)) {
                System.out.println("Invalid date format. Please enter the date in dd/mm/yyyy format.");
            }
        } while (!DateValidator.isValidDate(date));
        
        while (true) {
            System.out.print("Please enter timet (hh:mm am/pm): ");
            String time = scanner.nextLine();

            if (TimeValidator.isValidTime(time)) {
                System.out.println("Time entered: " + time);
                break; // Exit the loop if the time format is valid
            } else {
                System.out.println("Invalid time format. Please enter time in hh:mm am/pm format.");
            }
        }

        System.out.print("Event name: ");
        String eventName = scanner.nextLine();

        System.out.print("Artist name: ");
        String artistName = scanner.nextLine();

        System.out.print("Requester name: ");
        String requesterName = scanner.nextLine();

        System.out.print("Confirm order (y/n): \n");
        String confirmOrder = scanner.nextLine();

        if (confirmOrder.equalsIgnoreCase("y")) {
            String time = null;
			// Save the hiring details to memory or file
            saveHiringDetails(venue, numberOfHours, date, time, eventName, artistName, requesterName);
            System.out.println("Order confirmed.\n");
        } else if (confirmOrder.equalsIgnoreCase("n")) {
            System.out.println("Order canceled.\n");
        } else {
            System.out.println("\nInvalid input. Please enter 'y' for yes or 'n' for no.\n");
        }
    }

    // Method to save hiring details
    private static void saveHiringDetails(Venue venue, int numberOfHours, String date, String time,
                                           String eventName, String artistName, String requesterName) {
        // Save hiring details to memory or file
        String orderSummary = String.format("\n Order Summary\n" +
                "--------------------------------------------------\n" +
                "Client: %s\n" +
                "Venue: %s\n" +
                "Event name: %s\n" +
                "Artist: %s\n" +
                "Event date (dd/mm/yyyy): %s\n" +
                "Event time: %s\n" +
                "%d hours venue hire @ $550 -is: $%d\n" +
                "Brokering commission 10%%: $%.2f\n",
                requesterName, venue.getName(), eventName, artistName, date, time,
                numberOfHours, numberOfHours * 550, numberOfHours * 550 * 0.1);

        orderSummaries.add(orderSummary);
    }

    // Method to display order summary
    private static void displayOrderSummary() {
        for (String summary : orderSummaries) {
            System.out.println(summary);
        }
    }

    // Method to get user input from scanner
    private static int getUserInput(Scanner scanner) {
        int userInput = 0;
        boolean isValid = false;

        while (!isValid) {
            try {
                userInput = Integer.parseInt(scanner.nextLine());
                isValid = true;
            } catch (NumberFormatException e) {
                System.out.print("\nInvalid input. Please enter a valid number: \n");
            }
        }

        return userInput;
    }

    // Method to select a venue from the venue list
    private static Venue selectFromVenueList() {
        System.out.println("\n--------------------------------------------------");
        System.out.println("3. Select from venue list");
        System.out.println("--------------------------------------------------");

        // Display all venues in memory
        int venueIndex = 1;
        for (Venue venue : venues) {
            System.out.println(venueIndex + ") " + venue.getName());
            venueIndex++;
        }

        // Prompt the user to select a venue
        System.out.print("\nPlease select a venue: ");
        int venueChoice = getUserInput(1, venues.size());

        // Retrieve the selected venue from the list
        Venue selectedVenue = venues.get(venueChoice - 1);

        // Continue with the logic in the promptForAction method
        promptForAction(selectedVenue);

        return selectedVenue;
    }

    // Method to select a venue from the matching list
    private static void selectFromMatchingList() {
    	
        System.out.println("\n--------------------------------------------------");
        System.out.println("Enter your preference");
        System.out.println("--------------------------------------------------\n");
        
//        System.out.print("Please enter date of your event:\n ");
//        String date = scanner.nextLine();
        
        String date;
        do {
            System.out.print("Please enter date dd/mm/yyyy: ");
            date = scanner.nextLine();
            if (!DateValidator.isValidDate(date)) {
                System.out.println("Invalid date format. Please enter the date in dd/mm/yyyy format.");
            }
        } while (!DateValidator.isValidDate(date));
        
//        System.out.print("Event time of event: \n");
//        String time = scanner.nextLine();

        while (true) {
            System.out.print("Event time of event (hh:mm am/pm): ");
            String time = scanner.nextLine();

            if (TimeValidator.isValidTime(time)) {
                System.out.println("Time entered: " + time);
                break; // Exit the loop if the time format is valid
            } else {
                System.out.println("Invalid time format. Please enter time in hh:mm am/pm format.");
            }
        }
        
        System.out.println("\n--------------------------------------------------");
        System.out.println("Select from the matching list");
        System.out.println("--------------------------------------------------");

        // Randomly select a venue from memory
        Random random = new Random();
        int randomIndex = random.nextInt(venues.size());
        Venue randomVenue = venues.get(randomIndex);

        System.out.println("1) " + randomVenue.getName());
        System.out.println("2) Go to main menu");

        System.out.print("\nPlease select: ");
        int choice = getUserInput(1, 2);

        switch (choice) {
            case 1:
                // Display attributes of the randomly selected venue
                System.out.println("\n--------------------------------------------------");
                System.out.println(randomVenue);
                // Continue with the logic in the promptForAction method
                promptForAction(randomVenue);
                break;
            case 2:
                // Go back to the main menu
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    // Method to display the main menu
    private static void displayMainMenu() {
//    static void displayMainMenu() {
        System.out.println("\n Welcome to Venue Matcher");
        System.out.println("--------------------------------------------------");
        System.out.println(" Select from main menu");
        System.out.println("--------------------------------------------------");
        System.out.println("1) List current job requests");
        System.out.println("2) Browse venue by category");
        System.out.println("3) Search venue by name");
        System.out.println("4) Auto-match events with suitable venues");
        System.out.println("5) Show order summary");
        System.out.println("6) Exit");
        System.out.print("Please select: \n");
    }

    // Method to get user input with validation
    private static int getUserInput(int min, int max) {
        int choice = 0;
        boolean isValid = false;

        while (!isValid) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    isValid = true;
                } else {
                    System.out.print("\nInvalid input. Please enter a number between " + min + " and " + max + ": \n");
                }
            } catch (NumberFormatException e) {
                System.out.print("\nInvalid input. Please enter a valid number: \n");
            }
        }

        return choice;
    }

		
	}

