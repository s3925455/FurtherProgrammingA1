package AT2;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField name;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        login.setOnAction(event -> {
            String username = name.getText();
            String pass = password.getText();

            if (!username.isEmpty() && !pass.isEmpty()) {
                if (isValidUser(username, pass)) {
                    showAlert("Login Success", "Welcome, " + username + "!");
                    clearFields();
                    openVenueMatcher(stage); // Open VenueMatcher when login is successful
                } else {
                    showAlert("Login Failed", "Invalid username or password.");
                }
            } else {
                showAlert("Error", "Empty username or password.");
            }
        });
    }

    private boolean isValidUser(String username, String password) {
        // Check username and password validity (e.g., against a database)
        return username.equals("admin") && password.equals("admin123");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        name.clear();
        password.clear();
        name.requestFocus(); // Focus on username field after clearing
    }

    private void openVenueMatcher(Stage stage) {
        try {
            VenueMatcher venueMatcher = new VenueMatcher();
            venueMatcher.start(stage); // Call the start method of VenueMatcher
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
