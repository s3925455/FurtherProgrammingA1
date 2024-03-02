import java.util.*;
import java.io.*;

// Custom exception class
class CustomException extends Exception {
    public CustomException(String message) {
        super(message);
    }
}

// Venue class representing venue information
class Venue {
    private String name;
    private int capacity;
    private String suitableFor;
    private String category;

    public Venue(String name, int capacity, String suitableFor, String category) {
        this.name = name;
        this.capacity = capacity;
        this.suitableFor = suitableFor;
        this.category = category;
    }

    @Override
    public String toString() {
        return String.format("%s\nCapacity: %d\nCategory: %s\nSuitable for: %s\n",
                name, capacity, category, suitableFor);
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getCategory() {
        return category;
    }

    public String getSuitableFor() {
        return suitableFor;
    }
}

// FileHandler class to read data from CSV files
class FileHandler {
    // Method to read data from CSV file and return list of venues
    public List<Venue> readVenuesFromFile(String filename) throws CustomException {
        List<Venue> venues = new ArrayList<>();

        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            // Skip header line
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                // Check if the line has at least the minimum number of parts
                if (parts.length < 4) {
                    System.out.println("Warning: Invalid format in venues file: " + line);
                    continue; // Skip this line and proceed to the next one
                }

                // Parse venue information
                String name = parts[0];
                int capacity = Integer.parseInt(parts[1]);
                String category = parts[2];
                String suitableFor = parts[3];

                venues.add(new Venue(name, capacity, category, suitableFor));
            }
            scanner.close();
        } catch (FileNotFoundException | NumberFormatException e) {
            throw new CustomException("Error reading venues file: " + e.getMessage());
        }

        return venues;
    }

    // Method to read data from CSV file and return list of job requests
    public List<String> readJobRequestsFromFile(String filename) throws CustomException {
        List<String> jobRequests = new ArrayList<>();

        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);

            // Skip header line
            scanner.nextLine();

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                jobRequests.add(line);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new CustomException("Error reading job requests file: " + e.getMessage());
        }

        return jobRequests;
    }

    // Method to write job requests to CSV file
    public void writeJobRequestsToFile(List<String> jobRequests, String filename) throws CustomException {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(filename, true));

            for (String request : jobRequests) {
                writer.println(request);
            }

            writer.close();
        } catch (IOException e) {
            throw new CustomException("Error writing job requests file: " + e.getMessage());
        }
    }
}

// Main class to run the program
public class VenueMatcher {
    private static Scanner scanner = new Scanner(System.in);
    private static List<String> newJobRequests = new ArrayList<>();
    private static List<Venue> venues = new ArrayList<>(); // Declared venues here
    private static List<String> orderSummaries = new ArrayList<>(); // Maintain order summaries

    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler();
        List<String> existingJobRequests = null;

        try {
            venues = fileHandler.readVenuesFromFile("venues.csv");
            existingJobRequests = fileHandler.readJobRequestsFromFile("requests.csv");
        } catch (CustomException e) {
            System.out.println("Error: " + e.getMessage());
            return;
        }

        newJobRequests.addAll(existingJobRequests);

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
                    // Implement auto-matching events with suitable venues
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

    private static void displayNewJobRequests() {
        if (newJobRequests.isEmpty()) {
            System.out.println("No New Jobs Entered\n");
        } else {
            for (String jobRequest : newJobRequests) {
                System.out.println(jobRequest);
            }
        }
    }
//----
    private static void selectByCategory() {
        System.out.println("--------------------------------------------------");
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

    private static List<Venue> displayVenues(String selectedCategory) {
        // Display venues for the selected category
        System.out.println("Venues under category: " + selectedCategory);
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

    private static Venue selectVenue(List<Venue> selectedVenues) {
        // Prompt the user to make a selection from the displayed venues
        System.out.print("Please select a venue: ");
        int venueChoice = getUserInput(1, selectedVenues.size()) - 1;
        return selectedVenues.get(venueChoice);
    }

    private static void promptForAction(Venue selectedVenue) {
        // Display the attributes of the selected venue
        System.out.println("\n-----------------------");
        System.out.println(selectedVenue);

        // Prompt the user for further action
        System.out.println("--------------------------------------------------");
        System.out.println("1) Hire for $550.00/hour");
        System.out.println("2) Back to venue list");
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
                System.out.println("Invalid action choice.\n");
        }
    }

    
    
//    -------

    private static void showHiringMenu(Venue venue) {
        // Implement hiring menu logic here
        Scanner scanner = new Scanner(System.in);

        System.out.println("\n-----------------------");
        System.out.println("Hiring Details");
        System.out.println("-----------------------");

        System.out.print("Please enter number of hours: ");
        int numberOfHours = getUserInput(scanner);

        System.out.print("Please enter date: ");
        String date = scanner.nextLine();

        System.out.print("Please enter time: ");
        String time = scanner.nextLine();

        System.out.print("Event name: ");
        String eventName = scanner.nextLine();

        System.out.print("Artist name: ");
        String artistName = scanner.nextLine();

        System.out.print("Requester name: ");
        String requesterName = scanner.nextLine();

        System.out.print("Confirm order (y/n): ");
        String confirmOrder = scanner.nextLine();

        if (confirmOrder.equalsIgnoreCase("y")) {
            // Save the hiring details to memory or file
            saveHiringDetails(venue, numberOfHours, date, time, eventName, artistName, requesterName);
            System.out.println("Order confirmed.");
        } else if (confirmOrder.equalsIgnoreCase("n")) {
            System.out.println("Order canceled.");
        } else {
            System.out.println("\nInvalid input. Please enter 'y' for yes or 'n' for no.\n");
        }
    }

    private static void saveHiringDetails(Venue venue, int numberOfHours, String date, String time,
                                           String eventName, String artistName, String requesterName) {
        // Save hiring details to memory or file
        String orderSummary = String.format("Order Summary\n" +
                "--------------------------------------------------\n" +
                "Client: %s\n" +
                "Venue: %s\n" +
                "Event name: %s\n" +
                "Artist: %s\n" +
                "Event date: %s\n" +
                "Event time: %s\n" +
                "%d hours venue hire @ $550 -is: $%d\n" +
                "Brokering commission 10%%: $%.2f\n",
                requesterName, venue.getName(), eventName, artistName, date, time,
                numberOfHours, numberOfHours * 550, numberOfHours * 550 * 0.1);

        orderSummaries.add(orderSummary);
    }

    private static void displayOrderSummary() {
        for (String summary : orderSummaries) {
            System.out.println(summary);
        }
    }

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


    private static void displayMainMenu() {
        System.out.println("\nWelcome to Venue Matcher");
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
