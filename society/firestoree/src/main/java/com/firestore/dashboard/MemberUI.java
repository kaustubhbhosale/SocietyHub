
package com.firestore.dashboard;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.firestore.controller.Logincontroller;
import com.firestore.dashboard.MemberUI.Notice;
import com.firestore.dashboard.AdminDashboard.ViewComplaints.Complaint;
import com.firestore.firebaseConfig.Dataservice;
import com.google.cloud.firestore.DocumentSnapshot;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MemberUI {

    private Stage primaryStage;
    private Scene scene;
    public static String name;
    private Label usernameInfoLabel = new Label("Loading...\nYour Username");
    private Label flatNumberLabel = new Label("Loading...\nYour Flat No.");
    private Label noOfFamliyLabel = new Label("Loading...\nYour Number of Family Members");

    private Dataservice dataservice = new Dataservice();

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    public void setScene(Scene scene) {

        this.scene = scene;
    }

    public BorderPane initilizeMemberUI(Runnable logoutHandler) {
        // primaryStage.setTitle("Society Management System");

        // Sidebar
        VBox sidebar = new VBox();
        sidebar.setStyle("-fx-padding: 10; -fx-spacing: 50; -fx-background-color: #2A2A2A; -fx-pref-width: 300px;");

        // Profile Image and Username
        Image profileImage = new Image("images/member.png"); // Replace with the path to your profile image
        ImageView profileImageView = new ImageView(profileImage);
        profileImageView.setFitWidth(100); // Set appropriate width for the profile image
        profileImageView.setFitHeight(100); // Set appropriate height for the profile image
        profileImageView.setPreserveRatio(true);

        Label usernameLabel = new Label("Member");
        usernameLabel.setStyle("-fx-text-fill: white; -fx-font-size: 22px;-fx-font-family: Arial;");

        VBox profileBox = new VBox(10, profileImageView, usernameLabel);
        profileBox.setStyle("-fx-alignment: center;");

        Button dashboardButton = createSidebarButton("Dashboard");
        Button noticeBoardButton = createSidebarButton("Notice Board");
        Button registerComplaintButton = createSidebarButton("Register Complaint");
        Button logoutButton = createSidebarButton("Logout");

        sidebar.getChildren().addAll(profileBox, dashboardButton, noticeBoardButton, registerComplaintButton,
                logoutButton);

        // Top bar with Society Hub label
        VBox topBar = new VBox();
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color: #0C1844;");
        topBar.setMinHeight(40);

        Label societyHubLabel = new Label("SOCIETYHUB");
        societyHubLabel
                .setStyle("-fx-font-family: Arial; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        topBar.getChildren().add(societyHubLabel);

        // Main content
        VBox mainContent = new VBox();
        mainContent.setStyle("-fx-padding: 20; -fx-spacing: 30;-fx-font-family: Arial;");

        // Layout
        BorderPane layout = new BorderPane();
        layout.setLeft(sidebar);
        layout.setTop(topBar);
        layout.setCenter(mainContent);
        layout.setStyle("-fx-background-color: #e6f9ff;");
        // layout.setStyle("-fx-background-color: #ADD8E6;");

        // Scene scene = new Scene(layout, 800, 600);
        // primaryStage.setScene(scene);
        // primaryStage.show();

        // Set initial view to dashboard

        showDashboard(mainContent);

        // Button actions
        dashboardButton.setOnAction(e -> showDashboard(mainContent));
        noticeBoardButton.setOnAction(e -> showNoticeBoard(mainContent));
        registerComplaintButton.setOnAction(e -> showRegisterComplaint(mainContent));
        logoutButton.setOnAction(e -> {
            // System.out.println("User logged out");
            // Scene loginScene = Logincontroller.getLoginScene();
            // primaryStage.setScene(loginScene);
            logoutHandler.run();
        }); // Close the application on logout
        return layout;
    }

    private Button createSidebarButton(String text) {
        Button button = new Button(text);
        button.setStyle(
                "-fx-background-color: #2A2A2A; -fx-text-fill: white; -fx-alignment: center-left; -fx-pref-width: 280px; -fx-font-family: Arial; -fx-font-size: 20px; -fx-padding: 10 20 10 20;"); // Increase
                                                                                                                                                                                                   // width,
                                                                                                                                                                                                   // font
                                                                                                                                                                                                   // size,
                                                                                                                                                                                                   // and
                                                                                                                                                                                                   // padding
        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #4682B4; -fx-text-fill: white; -fx-alignment: center-left; -fx-pref-width: 280px; -fx-font-family: Arial; -fx-font-size: 20px; -fx-padding: 10 20 10 20;"));
        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: #2A2A2A; -fx-text-fill: white; -fx-alignment: center-left; -fx-pref-width: 280px; -fx-font-family: Arial; -fx-font-size: 20px; -fx-padding: 10 20 10 20;"));
        return button;
    }

    private void showDashboard(VBox mainContent) {
        mainContent.getChildren().clear();

        Label welcomeLabel = new Label("Welcome to Dashboard");
        welcomeLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold;-fx-font-family: Arial;");
        welcomeLabel.setAlignment(Pos.CENTER); // Center the label
        welcomeLabel.setPadding(new Insets(30, 0, 30, 480)); // Add padding around the label

        // Create HBox for user info and align it to the left with reduced padding
        HBox userInfo = new HBox(40); // Increased spacing between VBox containers for better alignment
        userInfo.setAlignment(Pos.CENTER_LEFT); // Align HBox contents to the center left
        userInfo.setPadding(new Insets(40, 0, 40, 250)); // Add padding to the left side to move HBox to the left

        // Create VBox for each label with background color and icons
        VBox usernameBox = new VBox(15);
        usernameBox.setStyle(
                "-fx-background-color: #003366; -fx-padding: 15; -fx-border-radius: 10; -fx-background-radius: 10;"); // Dark
                                                                                                                      // blue
                                                                                                                      // background
        usernameBox.setPrefSize(300, 150); // Increased width and height
        usernameBox.setAlignment(Pos.CENTER);
        ImageView userIcon = new ImageView(new Image("images/malee.png")); // Replace with actual icon path
        userIcon.setFitWidth(30);
        userIcon.setFitHeight(30);
        // Label usernameInfoLabel = new Label("Loading...\nYour Username");
        // Label usernameInfoLabel = new Label(name.getText());

        usernameInfoLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: white;-fx-font-family: Arial;");
        HBox usernameHBox = new HBox(15, userIcon, usernameInfoLabel);
        usernameHBox.setAlignment(Pos.CENTER);
        usernameBox.getChildren().add(usernameHBox);

        VBox flatNumberBox = new VBox(15);
        flatNumberBox.setStyle(
                "-fx-background-color:#003366; -fx-padding: 15; -fx-border-radius: 10; -fx-background-radius: 10;-fx-font-family: Arial;"); // Slightly
                                                                                                                                            // lighter
                                                                                                                                            // blue
                                                                                                                                            // background
        flatNumberBox.setPrefSize(300, 150); // Increased width and height
        flatNumberBox.setAlignment(Pos.CENTER);
        ImageView flatIcon = new ImageView(new Image("images/home.png")); // Replace with actual icon path
        flatIcon.setFitWidth(30);
        flatIcon.setFitHeight(30);
        // Label flatNumberLabel = new Label("Loading...\nYour Flat No.");
        flatNumberLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: white;-fx-font-family: Arial;");
        HBox flatNumberHBox = new HBox(15, flatIcon, flatNumberLabel);
        flatNumberHBox.setAlignment(Pos.CENTER);
        flatNumberBox.getChildren().add(flatNumberHBox);

        VBox secretaryBox = new VBox(15);
        secretaryBox.setStyle(
                "-fx-background-color: #003366; -fx-padding: 15; -fx-border-radius: 10; -fx-background-radius: 10;"); // Darker
                                                                                                                      // blue
                                                                                                                      // background
        secretaryBox.setPrefSize(300, 150); // Increased width and height
        secretaryBox.setAlignment(Pos.CENTER);

        ImageView secretaryIcon = new ImageView(new Image("images/secretary_icon.png")); // Replace with actual icon
                                                                                         // path
        secretaryIcon.setFitWidth(30);
        secretaryIcon.setFitHeight(30);
        noOfFamliyLabel.setStyle("-fx-font-size: 22px; -fx-text-fill: white;-fx-font-family: Arial;");
        HBox secretaryHBox = new HBox(15, secretaryIcon, noOfFamliyLabel);
        secretaryHBox.setAlignment(Pos.CENTER);
        secretaryBox.getChildren().add(secretaryHBox);

        // Add VBox containers to HBox
        userInfo.getChildren().addAll(usernameBox, flatNumberBox, secretaryBox);

        // Fetch data from Firestore and update the labels
        fetchMemberDataAndUpdateUI();
        // System.out.println(vb.getChildren().size());

        // Add elements to main content
        mainContent.getChildren().addAll(welcomeLabel, userInfo);
    }

    private VBox fetchMemberDataAndUpdateUI() {
        try {
            // System.out.println("456789"+name);
            DocumentSnapshot doc = dataservice.getData("members", name); // Fetch data from Firestore
            if (doc.exists()) {
                String username = doc.getString("name");

                String flatNumber = doc.getString("flatNo");
                Long noOfFamilyLong = doc.getLong("familyMembers"); // Fetching as Long
                String noOfFamily = noOfFamilyLong != null ? noOfFamilyLong.toString() : ""; // Convert Long to String
                // String noOfFamily = doc.getString("familyMembers");

                System.out.println(username);
                System.out.println(flatNumber);
                System.out.println(noOfFamily);

                if (name.equals(username)) {
                    usernameInfoLabel.setText(username + "\nYour Username");
                    flatNumberLabel.setText(flatNumber + "\nYour Flat Number");
                    noOfFamliyLabel.setText(noOfFamily + "\nYour Number Of Family Members");
                    // return new VBox(usernameInfoLabel, flatNumberInfoLabel);
                } else {
                    // showAlert(AlertType.ERROR, "Access Denied", "You do not have permission to
                    // view this data.");
                    System.out.println("Member is not Registered");
                }

            }

        } catch (ExecutionException | InterruptedException e) {
            // showError("Error fetching data from Firestore.");
            e.printStackTrace();
        }
        return new VBox();
    }

    private void showNoticeBoard(VBox mainContent) {
        mainContent.getChildren().clear();

        // Create TableView for notices
        TableView<Notice> table = new TableView<>();
        table.setPrefHeight(600); // Increased height for table
        table.setPrefWidth(900); // Increased width for table
        table.setMaxWidth(900); // Ensure table doesn't expand beyond this width
        table.setMinWidth(900); // Ensure table doesn't shrink below this width

        // Create columns
        // TableColumn<Notice, Integer> idColumn = new TableColumn<>("ID");
        // idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        // idColumn.setPrefWidth(50);

        TableColumn<Notice, String> nameColumn = new TableColumn<>("Notice Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("noticeName"));
        nameColumn.setPrefWidth(150);

        TableColumn<Notice, String> typeColumn = new TableColumn<>("Notice Type");
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("noticeType"));
        typeColumn.setPrefWidth(200);

        TableColumn<Notice, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setPrefWidth(80);

        TableColumn<Notice, String> messageColumn = new TableColumn<>("Message");
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        messageColumn.setPrefWidth(400);

        // Add columns to table
        table.getColumns().addAll(nameColumn, typeColumn, dateColumn, messageColumn);

        // Sample data for the TableView
        // ObservableList<Notice> noticeList = FXCollections.observableArrayList(
        // new Notice(1, "Water Supply Notice", "Maintenance", "2024-07-01",
        // "Water supply will be off from 10 AM to 5 PM."),
        // new Notice(2, "Fire Drill Notice", "Safety", "2024-07-05", "Fire drill will
        // be conducted at 3 PM."),
        // new Notice(3, "Society Meeting", "Meeting", "2024-07-10",
        // "Society meeting scheduled for 6 PM at the clubhouse."),
        // new Notice(4, "Gardening Day", "Event", "2024-07-12", "Join us for a
        // gardening event this Sunday."),
        // new Notice(5, "Electricity Maintenance", "Maintenance", "2024-07-15",
        // "Electricity maintenance will be performed from 10 AM to 5 PM."),
        // new Notice(6, "Security Alert", "Security", "2024-07-18",
        // "Be cautious of suspicious activity in the area."),
        // new Notice(7, "Parking Restrictions", "Parking", "2024-07-20",
        // "Parking restrictions will be in place from 8 AM to 6 PM."),
        // new Notice(8, "Water Conservation", "Environment", "2024-07-22", "Conserve
        // water to avoid shortages."),
        // new Notice(9, "Noise Complaint", "Noise", "2024-07-25",
        // "Report any noise complaints to the society office."),
        // new Notice(10, "Society Event", "Event", "2024-07-28", "Join us for a society
        // event this Saturday.")
        // );

        // table.setItems(noticeList);

        // Apply styles to table
        table.setStyle("-fx-font-size: 16px; -fx-font-family: Arial;");

        // Apply styles to table headers
        // idColumn.setStyle(
        // "-fx-font-size: 18px;-fx-font-family: Arial; -fx-background-color: #ADD8E6;
        // -fx-text-fill: black;");
        nameColumn.setStyle(
                "-fx-font-size: 18px;-fx-font-family: Arial; -fx-background-color: #ADD8E6; -fx-text-fill: black;");
        typeColumn.setStyle(
                "-fx-font-size: 18px;-fx-font-family: Arial; -fx-background-color: #ADD8E6; -fx-text-fill: black;");
        dateColumn.setStyle(
                "-fx-font-size: 18px;-fx-font-family: Arial; -fx-background-color: #ADD8E6; -fx-text-fill: black");
        messageColumn.setStyle(
                "-fx-font-size: 18px;-fx-font-family: Arial; -fx-background-color: #ADD8E6; -fx-text-fill: black;");

        // Set the placeholder for empty table
        table.setPlaceholder(new Label("No notices found") {
            {
                setStyle("-fx-font-family: Arial; -fx-font-size: 18px; -fx-text-fill: #888888;");
            }
        });

        // Fetch data from Firestore and populate the TableView
        try {
            Dataservice dataservice = new Dataservice();
            List<Map<String, Object>> noticesData = dataservice.getAllNotices();

            // Convert data into Notice objects
            ObservableList<Notice> noticeList = FXCollections.observableArrayList();
            for (Map<String, Object> noticeData : noticesData) {
                Notice notice = new Notice(
                        null, (String) noticeData.get("name"),
                        (String) noticeData.get("type"),
                        (String) noticeData.get("date"),
                        (String) noticeData.get("message"));
                noticeList.add(notice);
            }

            // Set the items in the table
            table.setItems(noticeList);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        // Add the table to the main content
        mainContent.getChildren().add(table);
    }

    // private ObservableList<Notice> fetchNoticeDataFromFirestore() throws
    // ExecutionException, InterruptedException {
    // ObservableList<Notice> notices = FXCollections.observableArrayList();
    // // Assuming dataservice is properly configured to interact with Firestore
    // dataservice.getAllNotices().forEach(doc -> {
    // Notice notice = new Notice(null, null, null, null, null);
    // notice.setNoticeName(doc.getString("noticeName"));
    // notice.setNoticeType(doc.getString("noticeType"));
    // notice.setDate(doc.getString("date"));
    // notice.setMessage(doc.getString("message"));
    // notices.add(notice);
    // });
    // return notices;
    // }

    private void showRegisterComplaint(VBox mainContent) {
        mainContent.getChildren().clear();

        Label registerComplaintLabel = new Label("Register Your Anonymous Complaints");
        registerComplaintLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;-fx-font-family: Arial;");
        registerComplaintLabel.setAlignment(Pos.CENTER);

        // Create a ComboBox for complaint titles with suggestions
        ObservableList<String> complaintSuggestions = FXCollections.observableArrayList(
                "Water leakage", "Electricity issue", "Noise complaint", "Security concern", "Maintenance issue",
                "Parking problem");

        ComboBox<String> complaintTitleComboBox = new ComboBox<>(complaintSuggestions);
        complaintTitleComboBox.setEditable(true); // Allow users to type their own complaint
        complaintTitleComboBox.setPromptText("Select or type your complaint title");
        complaintTitleComboBox.setStyle("-fx-prompt-text-fill: grey;-fx-font-family: Arial;");
        complaintTitleComboBox.setPrefWidth(500); // Fixed width for the combo box
        complaintTitleComboBox.setPrefHeight(30); // Fixed height for the combo box

        // Label complaintLabel = new Label("Complaint:");
        TextArea complaintTextArea = new TextArea();
        complaintTextArea.setPromptText("Enter your complaint here...");
        complaintTextArea.setPrefWidth(500); // Fixed width for the text area
        complaintTextArea.setMaxWidth(500); // Maximum width for the text area
        complaintTextArea.setMinWidth(400); // Minimum width for the text area
        complaintTextArea.setPrefHeight(300); // Fixed height for the text area

        Button lodgeComplaintButton = new Button("Lodge complaint");
        lodgeComplaintButton.setStyle(
                "-fx-background-color: #4682B4; -fx-text-fill: white;-fx-font-family: Arial;-fx-font-size: 16px;");
        lodgeComplaintButton.setPrefWidth(180); // Increased width for the button
        lodgeComplaintButton.setPrefHeight(50); // Increased height for the button

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle(
                "-fx-background-color: #FF6347; -fx-text-fill: white;-fx-font-family: Arial;-fx-font-size: 16px;");
        cancelButton.setPrefWidth(180); // Increased width for the button
        cancelButton.setPrefHeight(50); // Increased height for the button

        HBox buttonBox = new HBox(10, lodgeComplaintButton, cancelButton);

        mainContent.getChildren().addAll(registerComplaintLabel, complaintTitleComboBox, complaintTextArea, buttonBox);

        // Snackbar setup
        StackPane rootPane = new StackPane(); // Create a root pane to show the Snackbar
        rootPane.setAlignment(Pos.TOP_CENTER);
        Snackbar snackbar = new Snackbar(rootPane);

        lodgeComplaintButton.setOnAction(e -> {
            String title = complaintTitleComboBox.getValue();
            String complaint = complaintTextArea.getText();

            if (title.isEmpty() || complaint.isEmpty()) {
                Alert alert = new Alert(AlertType.WARNING, "Please fill out both fields.", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            // Create a Map with complaint details
            Map<String, Object> complaintData = new HashMap<>();
            complaintData.put("title", title);
            complaintData.put("complaint", complaint);

            try {
                // Add the complaint data to Firestore using Dataservice
                dataservice.addComplaint(title, complaintData);
                // ("complaints", title, "dummy@example.com", "101", "1234567890", "4",
                // complaintData);

                // Clear the fields
                complaintTitleComboBox.setValue(null);
                complaintTextArea.clear();

                // Display success message using Snackbar
                snackbar.show("Complaint submitted!");

                // Clear the fields
                complaintTitleComboBox.setValue(null);
                complaintTextArea.clear();
            } catch (ExecutionException | InterruptedException ex) {
                // showError("Failed to submit complaint.");
                ex.printStackTrace();
            }
        });

        // Code to submit the complaint goes here
        // For example, you might save the complaint to a database

        // Display success message using Snackbar
        // snackbar.show("Complaint submitted!");

        // Clear the fields
        complaintTitleComboBox.setValue(null);
        complaintTextArea.clear();

        cancelButton.setOnAction(e -> {
            complaintTitleComboBox.setValue(null);
            complaintTextArea.clear();
        });

        // Add the rootPane to the main content
        mainContent.getChildren().add(rootPane);

    }

    public class Snackbar {
        private final StackPane rootPane;
        private final Label messageLabel;

        public Snackbar(StackPane rootPane) {
            this.rootPane = rootPane;
            this.messageLabel = new Label();
            this.messageLabel.setStyle(
                    "-fx-background-color:#555555; -fx-text-fill: white; -fx-padding: 10px; -fx-background-radius: 5px;-fx-font-family: Arial;-fx-font-size: 18px;");
            this.messageLabel.setPrefWidth(200);
            this.messageLabel.setPrefHeight(45);

            this.messageLabel.setAlignment(Pos.TOP_CENTER);

            this.messageLabel.setVisible(false);
        }

        public void show(String message) {
            messageLabel.setText(message);
            messageLabel.setVisible(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(e -> messageLabel.setVisible(false));
            pause.play();

            rootPane.getChildren().add(messageLabel);

        }

    }

    // public static void main(String[] args) {
    // launch(args);
    // }
    //
    public static class Notice {
        private Integer id;
        private String noticeName;
        private String noticeType;
        private String date;
        private String message;

        public Notice(Integer id, String noticeName, String noticeType, String date, String message) {
            this.id = id;
            this.noticeName = noticeName;
            this.noticeType = noticeType;
            this.date = date;
            this.message = message;
        }

        public Integer getId() {
            return id;
        }
        // public void setId(Integer id) {
        // this.id = id;
        // }

        public String getNoticeName() {
            return noticeName;
        }

        // public void setname(String name){
        // this.noticeName=name;
        // }

        public String getNoticeType() {
            return noticeType;
        }

        public String getDate() {
            return date;
        }

        // public void setDate(String date) {
        // this.date = date;
        // }

        // public void setNoticeType(String noticeType) {
        // this.noticeType = noticeType;
        // }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

}
