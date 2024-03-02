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
                    // Implement showing order summary
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
                    System.out.println("Please select a valid menu option.");
            }
        }
    }

    private static void displayNewJobRequests() {
        if (newJobRequests.isEmpty()) {
            System.out.println("No New Jobs Entered");
        } else {
            for (String jobRequest : newJobRequests) {
                System.out.println(jobRequest);
            }
        }
    }

    private static void selectByCategory() {
        System.out.println("--------------------------------------------------");
        System.out.println("2. Select by category");
        System.out.println("--------------------------------------------------");
        System.out.println("1) Outdoor");
        System.out.println("2) Indoor");
        System.out.println("3) Convertible");
        System.out.println("4) Go to main menu");

        int categoryChoice = getUserInput(1, 4);

        // Display venues based on the selected category
        String selectedCategory = null;
        switch (categoryChoice) {
            case 1:
                selectedCategory = "Outdoor";
                break;
            case 2:
                selectedCategory = "Indoor";
                break;
            case 3:
                selectedCategory = "Convertible";
                break;
            case 4:
                return; // Go back to the main menu
            default:
                System.out.println("Invalid category choice.");
                return; // Go back to the main menu
        }

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

        // Prompt the user to make a selection from the displayed venues
        System.out.print("Please select a venue: ");
        int venueChoice = getUserInput(1, selectedVenues.size()) - 1;
        Venue selectedVenue = selectedVenues.get(venueChoice);

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
                System.out.println("Invalid action choice.");
        }
    }

    private static void showHiringMenu(Venue venue) {
        // Implement hiring menu logic here
        Scanner scanner = new Scanner(System.in);

        System.out.println("-----------------------");
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
            System.out.println("Invalid input. Please enter 'y' for yes or 'n' for no.");
        }
    }

    private static void saveHiringDetails(Venue venue, int numberOfHours, String date, String time,
		     String eventName, String artistName, String requesterName) {
			// Save hiring details to memory or file
			newJobRequests.add("Hiring Details: " + venue.getName() + ", Date: " + date + ", Time: " + time + ", Hours: " + numberOfHours);
			
			// You can also save to a file using FileHandler class
			FileHandler fileHandler = new FileHandler();
			try {
			List<String> jobRequest = new ArrayList<>();
			jobRequest.add("Hiring Details: " + venue.getName() + ", Date: " + date + ", Time: " + time + ", Hours: " + numberOfHours);
			fileHandler.writeJobRequestsToFile(jobRequest, "requests.csv");
			} catch (CustomException e) {
			System.out.println("Error writing job requests file: " + e.getMessage());
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
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }

        return userInput;
    }

    private static void selectFromVenueList() {
        System.out.println("--------------------------------------------------");
        System.out.println("3. Select from venue list");
        System.out.println("--------------------------------------------------");

        int venueIndex = 1;
        for (Venue venue : venues) {
            System.out.println(venueIndex + ") " + venue.getName());
            venueIndex++;
        }

        System.out.print("Please select: ");
        int venueChoice = getUserInput(1, venues.size());
        Venue selectedVenue = venues.get(venueChoice - 1);
        // Implement the logic for venue selection here
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
        System.out.print("Please select: ");
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
                    System.out.print("Invalid input. Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }

        return choice;
    }
}
