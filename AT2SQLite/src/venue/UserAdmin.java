package venue;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import dao.UserDaoImpl;
import model.User;
import java.sql.SQLException;

public class UserAdmin extends Application {

    private dao.UserDaoImpl userDao; // Updated import
    private TextField usernameField;
    private TextField passwordField;
    private Button updateButton;
    private Button deleteButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        userDao = new dao.UserDaoImpl(); // Updated package reference

        // Create UI components
        Label searchLabel = new Label("Search User:");
        TextField searchField = new TextField();
        Button searchButton = new Button("Search");

        Label usernameLable = new Label("User Name:");
        usernameField = new TextField();
        Label userpasswordLable = new Label("User Password:");
        passwordField = new TextField();
        Button addButton = new Button("Add User");
        updateButton = new Button("Update User");
        deleteButton = new Button("Delete User");
        
        Button exitButton = new Button("Exit"); // Exit button

        // Hide update and delete buttons initially
        updateButton.setVisible(false);
        deleteButton.setVisible(false);

        // Add margins to UI components
        VBox.setMargin(searchLabel, new Insets(10, 0, 0, 0));
        VBox.setMargin(searchField, new Insets(0, 0, 10, 0));
        VBox.setMargin(addButton, new Insets(10, 0, 0, 0));
        VBox.setMargin(updateButton, new Insets(10, 0, 0, 0));
        VBox.setMargin(deleteButton, new Insets(10, 0, 0, 0));
        VBox.setMargin(exitButton, new Insets(10, 0, 0, 0)); // Margins for Exit button

        // Add event handlers
        addButton.setOnAction(event -> addUser(usernameField.getText(), passwordField.getText()));
        
        updateButton.setOnAction(event -> {
            try {
                updateUser(usernameField.getText(), passwordField.getText());
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update user: " + e.getMessage());
            }
        });
        deleteButton.setOnAction(event -> {
            try {
                deleteUser(usernameField.getText());
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete user: " + e.getMessage());
            }
        });
        
        searchButton.setOnAction(event -> {
            try {
                searchUser(searchField.getText());
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to search user: " + e.getMessage());
            }
        });

        exitButton.setOnAction(event -> stage.close()); // Event handler for Exit button

        // Create layout
        VBox root = new VBox(10);
        root.setPadding(new Insets(10)); // Add padding to the VBox
        root.getChildren().addAll(searchLabel, searchField, searchButton, new Separator(), usernameLable,usernameField, userpasswordLable, passwordField, addButton, updateButton, deleteButton, exitButton); // Added exitButton
        Scene scene = new Scene(root, 600, 500);

        // Set the scene and show the stage
        stage.setTitle("User Admin");
        stage.setScene(scene);
        stage.show();
    }

    
    
    private void addUser(String username, String password) {
        try {
            if (userDao.userExists(username)) {
                showAlert(Alert.AlertType.ERROR, "Error", "User already exists! Please choose a different username.");
            } else {
                userDao.createUser(username, password);
                showAlert(Alert.AlertType.INFORMATION, "User Added", "User successfully added!");
                usernameField.clear(); // Clear usernameField after adding user
                passwordField.clear(); // Clear passwordField after adding user
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add user: " + e.getMessage());
        }
    }

    private void updateUser(String username, String password) throws SQLException {
        userDao.updateUser(username, password);
        showAlert(Alert.AlertType.INFORMATION, "User Updated", "User successfully updated!");
    }

    private void deleteUser(String username) throws SQLException {
        userDao.deleteUser(username);
        showAlert(Alert.AlertType.INFORMATION, "User Deleted", "User successfully deleted!");
    }

    private void searchUser(String username) throws SQLException {
        String password = null;
		User user = userDao.getUserExist(username, password);
        if (user != null) {
            usernameField.setText(user.getUsername());
            passwordField.setText(user.getPassword());
            updateButton.setVisible(true);
            deleteButton.setVisible(true);
            showAlert(Alert.AlertType.INFORMATION, "User Found", "User found in the database.");
        } else {
            usernameField.clear();
            passwordField.clear();
            updateButton.setVisible(false);
            deleteButton.setVisible(false);
            showAlert(Alert.AlertType.INFORMATION, "User Not Found", "User not found in the database.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
