
package com.firestore.controller;

import com.firestore.dashboard.MemberUI;
import com.firestore.dashboard.AdminDashboard.AdminPage;
import com.firestore.firebaseConfig.Dataservice;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
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
import javafx.util.Duration;

public class Logincontroller {

    private Stage primaryStage;
    private static Scene loginScene;
    private Scene memberScene, adminScene;
    private Dataservice dataService;
    public static String key;

    public Logincontroller(Stage primaryStage) {
        this.primaryStage = primaryStage;
        dataService = new Dataservice();
        initScenes();
    }

    private void initScenes() {
        initLoginScene();
    }

    private void initLoginScene() {

        // Add the title label with typewriter effect
        Label titleLabel = new Label();
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 30px;");

        Timeline timeline = createTypewriterEffect(titleLabel, "Welcome to SocietyHub", Duration.millis(200));
        timeline.play();
        

        Label userLabel = new Label("Username");
        TextField userTextField = new TextField();
        userTextField.setPromptText("Name");

        Label passLabel = new Label("Password");
        PasswordField passField = new PasswordField();
        passField.setPromptText("********");

        Button adminLoginButton = new Button("Admin Login");
        Button userLoginButton = new Button("Member Login");
        Button signupButton = new Button("Register");

        adminLoginButton.setOnAction(event -> handleAdminLogin(userTextField.getText(), passField.getText()));
        userLoginButton.setOnAction(event -> handleUserLogin(userTextField.getText(), passField.getText()));
        signupButton.setOnAction(event -> showSignupScene());

        // Styling the labels and buttons to match the example
        userLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        passLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14px;");
        userTextField.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 10; -fx-font-size: 14px;");
        passField.setStyle(
                "-fx-background-color: white; -fx-background-radius: 10; -fx-padding: 10; -fx-font-size: 14px;");
        adminLoginButton.setStyle(
                "-fx-background-color:  #ff5500; -fx-text-fill: white; -fx-background-radius: 12; -fx-font-size: 17px; -fx-padding: 5 10;-fx-font-weight: bold;");
        userLoginButton.setStyle(
                "-fx-background-color: #ff5500; -fx-text-fill: white; -fx-background-radius: 12; -fx-font-size: 17px; -fx-padding: 5 10;-fx-font-weight: bold;");
        signupButton.setStyle(
                "-fx-background-color: #ff5500; -fx-text-fill: white; -fx-background-radius: 12; -fx-font-size: 17px; -fx-padding: 5 10;-fx-font-weight: bold;");

        VBox fieldBox1 = new VBox(10, userLabel, userTextField);
        fieldBox1.setMaxSize(300, 30);

        VBox fieldBox4 = new VBox(10, passLabel, passField);
        fieldBox4.setMaxSize(300, 30);

        HBox buttonBox = new HBox(10, adminLoginButton, userLoginButton, signupButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(35, titleLabel, fieldBox1, fieldBox4, buttonBox);
        vbox.setMaxWidth(500);
        vbox.setMinHeight(500);

        BackgroundFill backgroundFill = new BackgroundFill(javafx.scene.paint.Color.rgb(0, 0, 0, 0.5),
                CornerRadii.EMPTY, null);
        vbox.setBackground(new Background(backgroundFill));
        vbox.setAlignment(Pos.CENTER);

        Image backgroundImage = new Image("images/pig.jpeg"); // Update path if necessary
        BackgroundSize backgroundSize = new BackgroundSize(1, 1, true, true, false, false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);

        VBox rootVBox = new VBox(vbox);
        rootVBox.setBackground(new Background(background));
        rootVBox.setAlignment(Pos.CENTER);

        loginScene = new Scene(rootVBox, 700, 700);

        // Make the stage resizable and set to full screen
        primaryStage.setResizable(true);
        primaryStage.setMaximized(true);
    }

    private Timeline createTypewriterEffect(Label label, String text, Duration duration) {
        final StringBuilder displayedText = new StringBuilder();
        Timeline timeline = new Timeline();
        for (int i = 0; i < text.length(); i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(duration.multiply(i), event -> {
                displayedText.append(text.charAt(index));
                label.setText(displayedText.toString());
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        return timeline;
    }

    public static Scene getLoginScene() {
        return loginScene;
    }

    public void showLoginScene() {
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    private void handleUserLogin(String username, String password) {
        try {
            if (dataService.authenticateUser(username, password) && !dataService.isAdmin(username)) {
                MemberUI.name = username;
                key = username;
                launchMemberUI();
            } else {
                System.out.println("Invalid user credentials");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void handleAdminLogin(String username, String password) {
        try {
            if (dataService.authenticateUser(username, password) && dataService.isAdmin(username)) {
                launchAdminUI();
            } else {
                System.out.println("Invalid admin credentials");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void launchMemberUI() {
        MemberUI memberUI = new MemberUI();
        memberUI.setStage(primaryStage);
        memberScene = new Scene(memberUI.initilizeMemberUI(this::handleLogout), 1290, 700);
        memberUI.setScene(memberScene);
        primaryStage.setScene(memberScene);
        primaryStage.setTitle("Member Dashboard");
        // primaryStage.setResizable(true);
        primaryStage.show();
    }

    private void launchAdminUI() {
        AdminPage adminPage = new AdminPage();
        adminPage.setStage(primaryStage);
        adminScene = new Scene(adminPage.initilizeAdminUI(this::handleLogout), 1200, 600);
        adminPage.setScene(adminScene);
        primaryStage.setScene(adminScene);
        primaryStage.setTitle("Admin Dashboard");
        // primaryStage.setResizable(true);
        primaryStage.show();
    }

    private void showSignupScene() {
        Signupcontroller signupController = new Signupcontroller(this);
        Scene signupScene = signupController.createSignupScene(primaryStage);
        primaryStage.setScene(signupScene);
        primaryStage.setTitle("Signup");
        primaryStage.show();
    }

    private void handleLogout() {
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Login");
    }
}
