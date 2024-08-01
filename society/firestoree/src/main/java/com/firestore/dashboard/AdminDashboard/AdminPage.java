package com.firestore.dashboard.AdminDashboard;

import com.firestore.controller.Logincontroller;
import com.firestore.firebaseConfig.Dataservice;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminPage {

    private Pane contentPane;
    private Dataservice dataService;
    private Logincontroller loginController;
    private Stage primaryStage;
    private Scene adminScene;

    public AdminPage() {
        this.loginController = loginController;
        this.dataService = dataService;
    }

    public Scene createAdminDashboard(Runnable onLogout) {
        VBox vbox = new VBox(20);
        Label adminLabel = new Label("Admin Dashboard");

        // Add other UI components and functionality here

        vbox.getChildren().addAll(adminLabel);
        return new Scene(vbox, 700, 600);
    }

    public VBox initilizeAdminUI(Runnable logouthandler) {

        // Create HBox for the top bar with Society Hub text
        VBox topBar = new VBox();
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color: #0C1844;");
        topBar.setMinHeight(40);

        // Create label for Society Hub
        Label societyHubLabel = new Label("SOCIETYHUB");
        societyHubLabel
                .setStyle("-fx-font-family: Arial; -fx-text-fill: white; -fx-font-size: 24px; -fx-font-weight: bold;");

        // Add label to topBar
        topBar.getChildren().add(societyHubLabel);

        // Create profile icon
        Image profileImage = new Image(getClass().getResourceAsStream("/images/member.png")); // Ensure path is correct

        ImageView profileImageView = new ImageView(profileImage);
        profileImageView.setFitWidth(100); // Set the desired width
        profileImageView.setFitHeight(100); // Set the desired height

        // Create label for Admin
        Label adminLabel = new Label("Admin");
        adminLabel.setStyle("-fx-font-family: Arial; -fx-text-fill: white; -fx-font-size: 20px;"); // Apply Arial font

        // Create buttons for navigation
        Button manageMembersButton = createNavButton("Manage Members");
        Button addNoticeButton = createNavButton("Add Notice");
        Button viewComplaintsButton = createNavButton("View Complaints");
        Button aboutUsButton = createNavButton("About Us");

        Button logoutButton = createNavButton("Logout"); // New Logout button

        // Set button actions
        manageMembersButton.setOnAction(event -> {
            setContent(ManageMembers.getPane());
            System.out.println("Manage Members");
        });

        addNoticeButton.setOnAction(event -> {
            setContent(AddNotice.getPane());
            System.out.println("Add Notice");
        });

        viewComplaintsButton.setOnAction(event -> {
            setContent(ViewComplaints.getPane());
            System.out.println("View Complaints");
        });

        aboutUsButton.setOnAction(event -> {
            // Set the content to the AboutUs pane
            // setContent(AboutUs.getPane());
            System.out.println("About Us");
        });

        logoutButton.setOnAction(event -> {
            logouthandler.run();
        });

        // Create a VBox for the navigation panel
        VBox navigationPanel = new VBox(35); // Increase spacing between elements to 15px
        navigationPanel.setPadding(new Insets(10));
        navigationPanel.setAlignment(Pos.TOP_CENTER); // Center all children in the VBox

        // Shift the profile image a little bit down
        VBox.setMargin(profileImageView, new Insets(20, 0, 0, 0)); // Add top margin

        navigationPanel.getChildren().addAll(profileImageView, adminLabel, manageMembersButton, addNoticeButton,
                viewComplaintsButton, aboutUsButton, logoutButton); // Add Logout button to VBox
        navigationPanel.setMinWidth(200); // Set a wider width for the navigation panel
        navigationPanel.setMinHeight(950);
        navigationPanel.setStyle("-fx-background-color:#333333;"); // Apply NavyBlue color to VBox

        // Create a pane for the content
        contentPane = new Pane();
        setContent(ManageMembers.getPane()); // Set initial content

        // Create an HBox for the main layout
        HBox mainLayout = new HBox();
        mainLayout.getChildren().addAll(navigationPanel, contentPane);
        mainLayout.setStyle("-fx-background-color:  #e6f9ff");

        // Create a VBox to hold the top bar and main layout
        VBox root = new VBox();
        root.getChildren().addAll(topBar, mainLayout);

        // Create a Scene and set it on the Stage
        Scene scene = new Scene(root, 2000, 1000);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm()); // Apply the CSS file
        primaryStage.setResizable(true);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin Dashboard");
        primaryStage.show();

        return root;
    }

    private void setContent(Pane pane) {
        contentPane.getChildren().clear();
        contentPane.getChildren().add(pane);
    }

    private Button createNavButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("button-nav"); // Apply the CSS class to the button
        button.setPrefWidth(180); // Adjust width as needed
        return button;
    }

    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void setScene(Scene scene) {
        this.adminScene = scene;
    }

}
