package venue;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import dao.Database;

public class ShowBackup extends Application {

    private final FileChooser fileChooser = new FileChooser();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Backup: Export/Import File");

        Button importButton = new Button("Import From File");
        Button exportButton = new Button("Export to File");
        Button exitButton = new Button("Exit");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.getChildren().addAll(importButton, exportButton, exitButton);

        importButton.setOnAction(event -> importFromFile(primaryStage));
        exportButton.setOnAction(event -> {
            try {
                exportToFile(primaryStage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        exitButton.setOnAction(event -> primaryStage.close());

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void importFromFile(Stage primaryStage) {
        File selectedFile = fileChooser.showOpenDialog(primaryStage);
        if (selectedFile != null) {
            try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(selectedFile))) {
                // Read the serialized data from the file
                Object userData = inputStream.readObject();

                // Perform the necessary operations to import the data into the database
                // For example, assuming userData is a List<Object[]> containing the data rows
                if (userData instanceof List) {
                    List<Object[]> dataRows = (List<Object[]>) userData;
                    for (Object[] dataRow : dataRows) {
                        insertDataRow(dataRow);
                    }
                }

                showAlert("Import Successful", "Data imported from file.");
            } catch (IOException | ClassNotFoundException e) {
                showAlert("Error", "Failed to import data: " + e.getMessage());
            }
        }
    }

    private void insertDataRow(Object[] dataRow) {
        String tableName = "your_table_name"; // Set the table name where data will be inserted
        StringBuilder insertSql = new StringBuilder("INSERT INTO ")
                .append(tableName)
                .append(" (");

        int numColumns = dataRow.length; // Total number of columns in the data row

        for (int i = 0; i < numColumns; i++) {
            insertSql.append("column").append(i + 1); // Assuming column names like column1, column2, etc.
            if (i < numColumns - 1) {
                insertSql.append(",");
            }
        }

        insertSql.append(") VALUES (");

        for (int i = 0; i < numColumns; i++) {
            insertSql.append("?");
            if (i < numColumns - 1) {
                insertSql.append(",");
            }
        }

        insertSql.append(")");

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertSql.toString())) {

            // Bind values to parameters
            for (int i = 0; i < numColumns; i++) {
                statement.setObject(i + 1, dataRow[i]); // Assuming dataRow is an Object[] with values
            }

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully.");
            } else {
                showAlert("Database Error", "No data inserted into table.");
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Failed to insert data into table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void exportToFile(Stage primaryStage) throws IOException {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(primaryStage);

        if (selectedDirectory != null) {
            File selectedFile = new File(selectedDirectory.getAbsolutePath(), "transactiondata.lmvm");

            // Perform database retrieval and get user data as an Object
            dao.UserDaoImpl userDao = new dao.UserDaoImpl();
            Object userData = userDao.exportUserData(); // Change the return type of exportUserData as needed

            // Serialize data and write to file
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(selectedFile))) {
                outputStream.writeObject(userData);
                showAlert("Export Successful", "Data exported to file: " + selectedFile.getAbsolutePath());
            } catch (IOException e) {
                showAlert("Error", "Failed to export data: " + e.getMessage());
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
