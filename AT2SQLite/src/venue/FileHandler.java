package venue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

// FileHandler class to read data from SQLite files
public class FileHandler {

	private static final String DB_URL = "jdbc:sqlite:/Users/amitmunjal/eclipse-workspace/FutureProgramWk9/application.db";

	public List<Venue> readVenuesFromDatabase() throws CustomException {
		List<Venue> venues = new ArrayList<>();

		try (Connection connection = DriverManager.getConnection(DB_URL)) {
			String query = "SELECT venueId, name, capacity, suitableFor, category, rate FROM venues";
			try (PreparedStatement statement = connection.prepareStatement(query);
					ResultSet resultSet = statement.executeQuery()) {
				while (resultSet.next()) {
					int venueId = resultSet.getInt("venueId");
					String name = resultSet.getString("name");
					int capacity = resultSet.getInt("capacity");
					String suitableFor = resultSet.getString("suitableFor");
					String category = resultSet.getString("category");
					int rate = resultSet.getInt("rate");

					venues.add(new Venue(venueId, name, capacity, category, suitableFor, rate));

					// Debug: Print each venue's details as it is added to the list
					System.out.println("Venue:  " + venueId + ", " + name + ", Capacity: " + capacity
							+ ", Suitable for: " + suitableFor + ", Category: " + category + ", Rate: " + rate);
				}

				// Debug: Print the number of venues retrieved from the database
				System.out.println("Number of venues retrieved from database: " + venues.size());
			}
		} catch (SQLException e) {
			throw new CustomException("Error reading venues from database: " + e.getMessage());
		}

		return venues;
	}

	// ----- Method to SAVE job requests to the DATABASE---------------------------------------

	public void saveToDatabase(List<String> finalSummaries) throws CustomException {
	    String url = "jdbc:sqlite:/Users/amitmunjal/eclipse-workspace/FutureProgramWk9/application.db";

	    try (Connection connection = DriverManager.getConnection(url)) {
	        System.out.println("FileHandler: saveToDatabase method called."); // Debug: Print message when method is called

	        if (finalSummaries == null || finalSummaries.isEmpty()) {
	            throw new CustomException("No job requests to save.");
	        }

	        System.out.println("FileHandler: Writing job requests to the database..."); // Debug: Print message before writing to database

	        String insertQuery = "INSERT INTO requests (client, title, artist, date, time, target, duration, type, category, total, comission, venue) " +
	                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
	            // Write job requests to the database
	            for (String request : finalSummaries) {
	                String[] requestData = request.split(","); // Assuming each piece of data is separated by a comma
	                if (requestData.length == 12) { // Check if the data has 12 elements
	                    for (int i = 0; i < requestData.length; i++) {
	                        preparedStatement.setString(i + 1, requestData[i].trim());
	                    }
	                    preparedStatement.executeUpdate();
	                } else {
	                    System.out.println("Invalid data format: " + request);
	                }
	            }
	        }

	        System.out.println("FileHandler: Job requests saved to the database successfully."); // Debug: Print message after writing to database
	    } catch (SQLException e) {
	        throw new CustomException("Error saving data to database: " + e.getMessage());
	    }
	}


	
	
	// ----------Method to write job requests to CSV file-------
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
	}
}
