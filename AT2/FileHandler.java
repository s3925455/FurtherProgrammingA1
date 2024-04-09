/**
 * @author amitmunjal
 *
 */

package AT2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


//FileHandler class to read data from CSV files
class FileHandler {
	// Method to read data from CSV file and return list of venues
	public List<Venue> readVenuesFromFile(String filename) throws CustomException {
		List<Venue> venues = new ArrayList<>();

		try {
			File file = new File(filename);
			Scanner scanner = new Scanner(file);

			// Skip header line
			scanner.nextLine();

			// Read venue data from file
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

			// Read job requests from file
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
//	public void writeJobRequestsToFile(List<String> jobRequests, String filename) throws CustomException {
	public void writeJobRequestsToFile(List<String> finalSummaries, String filename) throws CustomException {
        if (finalSummaries == null) {
        	System.out.println("Null Jobs to write to " + filename + " Exiting...");
            return; // Exit the method if there are no job requests to write
        }

        if (finalSummaries.isEmpty()) {
            System.out.println("No job requests to write. Exiting...");
            return; // Exit the method if there are no job requests to write
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            // Write job requests to file
            for (String request : finalSummaries) {
                writer.println(request);
            }
        } catch (IOException e) {
            throw new CustomException("Error writing job requests file: " + e.getMessage());
        }
	}}
