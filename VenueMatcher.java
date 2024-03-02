import java.util.*;
import java.io.*;

// Custom exception class
class CustomException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

                // Parse venue information
                String name = parts[0];
                int capacity = Integer.parseInt(parts[1]);
                String category = parts[2];
                String suitableFor = parts[3];

                venues.add(new Venue(name, capacity, category, suitableFor));
            }
            scanner.close();
        } catch (FileNotFoundException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
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
    private static List<String> newJobRequests = new ArrayList<>(); // Added here
    
    public static void main(String[] args) {
        FileHandler fileHandler = new FileHandler();
        List<Venue> venues = null;
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
                    // Implement browsing venues by category
                    break;
                case 3:
                    // Implement searching venues by name
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
                    System.out.print("Invalid input. Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }

        return choice;
    }
}
