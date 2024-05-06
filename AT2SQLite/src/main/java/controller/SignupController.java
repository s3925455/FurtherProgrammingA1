package main.java.controller;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Model;
import model.User;

public class SignupController {
    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button createUser;
    @FXML
    private Button close;
    @FXML
    private Label status;

    private Stage stage;
    private Stage parentStage;
    private Model model;

    public SignupController(Stage parentStage, Model model) {
        this.stage = new Stage();
        this.parentStage = parentStage;
        this.model = model;
    }

    @FXML
    public void initialize() {
        createUser.setOnAction(event -> {
            if (!name.getText().isEmpty() && !surname.getText().isEmpty() && !username.getText().isEmpty() && !password.getText().isEmpty()) {
                User user;
                try {
                    user = model.getUserDao().createUser(name.getText(), surname.getText(), username.getText(), password.getText()); // Pass name, surname, username, and password
                    if (user != null) {
                        status.setText("Created " + user.getUsername());
                        status.setTextFill(Color.GREEN);
                        clearFields(); // Clear the text fields after successful signup
                    } else {
                        status.setText("Cannot create user");
                        status.setTextFill(Color.RED);
                    }
                } catch (SQLException e) {
                    status.setText("Cannot create user - issue with username or password");
                    status.setTextFill(Color.RED);
                    clearFields(); // Clear the text fields after unsuccessful signup
                }

            } else {
                status.setText("Empty fields");
                status.setTextFill(Color.RED);
            }
        });

        close.setOnAction(event -> {
            stage.close();
            parentStage.show();
        });
    }


    public void showStage(Pane root) {
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sign up");
        stage.show();
    }

    private void clearFields() {
        name.clear();
        surname.clear();
        username.clear();
        password.clear();
    }
}
