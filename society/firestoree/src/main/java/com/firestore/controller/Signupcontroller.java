package com.firestore.controller;

import com.firestore.firebaseConfig.Dataservice;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Signupcontroller {

        private Logincontroller loginController;

        public Signupcontroller(Logincontroller loginController) {
                this.loginController = loginController;
        }

        public Scene createSignupScene(Stage primaryStage) {
                Label titleLabel = new Label("Registration");
                titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
                titleLabel.setStyle("-fx-text-fill: white;");

                Label userLabel = new Label("Username:");
                userLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: white;");
                TextField userTextField = new TextField();

                Label emailLabel = new Label("Email:");
                emailLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: white;");
                TextField emailField = new TextField();

                Label flatNoLabel = new Label("FlatNo:");
                flatNoLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: white;");
                TextField flatNOField = new TextField();

                Label mobileNoLabel = new Label("Mobile Number:");
                mobileNoLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: white;");
                TextField mobileNoField = new TextField();

                Label noOfFamilyLabel = new Label("Number of Family Members:");
                noOfFamilyLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: white;");
                TextField noOfFamilyField = new TextField();

                Label passLabel = new Label("Password:");
                passLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: white;");
                PasswordField passField = new PasswordField();

                Button signupButton = new Button("Register");
                signupButton.setPrefWidth(110);
                signupButton.setStyle(
                                "-fx-background-color: #00e600; -fx-text-fill: white; -fx-background-radius: 12; -fx-font-size: 17px; -fx-padding: 5 10;-fx-font-weight: bold;");

                Button backButton = new Button("Back");
                backButton.setOnAction(event -> loginController.showLoginScene());
                backButton.setPrefWidth(110);
                backButton.setStyle(
                                "-fx-background-color:#00e600; -fx-text-fill: white; -fx-background-radius: 12; -fx-font-size: 17px; -fx-padding: 5 10;-fx-font-weight: bold;");

                VBox fieldBox1 = new VBox(10, userLabel, userTextField);
                fieldBox1.setMaxSize(300, 30);

                VBox fieldBox2 = new VBox(10, emailLabel, emailField);
                fieldBox2.setMaxSize(300, 30);

                VBox fieldBox3 = new VBox(10, flatNoLabel, flatNOField);
                fieldBox3.setMaxSize(300, 30);

                VBox fieldBox4 = new VBox(10, mobileNoLabel, mobileNoField);
                fieldBox4.setMaxSize(300, 30);

                VBox fieldBox5 = new VBox(10, noOfFamilyLabel, noOfFamilyField);
                fieldBox5.setMaxSize(300, 30);

                VBox fieldBox6 = new VBox(10, passLabel, passField);
                fieldBox6.setMaxSize(300, 30);

                HBox buttonBox = new HBox(50, signupButton, backButton);
                buttonBox.setMaxSize(350, 30);
                buttonBox.setAlignment(Pos.CENTER);

                VBox vbox = new VBox(20, fieldBox1, fieldBox2, fieldBox3, fieldBox4, fieldBox5, fieldBox6,
                                buttonBox);
                vbox.setAlignment(Pos.CENTER);
                vbox.setMaxWidth(400);
                vbox.setMaxHeight(600);
                vbox.setMinHeight(600);

                signupButton.setOnAction(
                                event -> handleSignup(primaryStage, userTextField.getText(), emailField.getText(),
                                                flatNOField.getText(), mobileNoField.getText(),
                                                noOfFamilyField.getText(), passField.getText()));

                // Make the VBox background transparent black
                BackgroundFill backgroundFill = new BackgroundFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0.5),
                                CornerRadii.EMPTY, null);
                vbox.setBackground(new Background(backgroundFill));

                // Load the background image
                Image backgroundImage = new Image("images/imm.jpg"); // Update path if necessary
                BackgroundSize backgroundSize = new BackgroundSize(1, 1, true, true, false, false);
                BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
                backgroundImage.isSmooth();

                VBox rootVBox = new VBox(vbox);
                rootVBox.setBackground(new Background(background));
                rootVBox.setAlignment(Pos.CENTER);

                Scene scene = new Scene(rootVBox, 1400, 650);

                // Set the scene to be resizable
                primaryStage.setScene(scene);
                primaryStage.setMaximized(true);
                primaryStage.setResizable(true);

                return scene;
        }

        private void handleSignup(Stage primaryStage, String username, String email, String flatNo, String mobileNo,
                        String noOfFamilynumber, String password) {
                Dataservice dataService;
                try {
                        dataService = new Dataservice();
                        Map<String, Object> data = new HashMap<>();
                        data.put("password", password);
                        data.put("username", username);
                        data.put("email", email);
                        data.put("flatNo", flatNo);
                        data.put("mobileNumber", mobileNo);
                        data.put("noOfFamilyNumbers", noOfFamilynumber);

                        dataService.addData("users", username, email, flatNo, mobileNo, noOfFamilynumber, data);
                        System.out.println("User registered successfully");

                        loginController.showLoginScene();
                } catch (Exception ex) {
                        ex.printStackTrace();
                }
        }
}
