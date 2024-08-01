package com.firestore.dashboard.AdminDashboard;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.firestore.firebaseConfig.Dataservice;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AddMemberForm {

    public static void display(ObservableList<ManageMembers.Member> data) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Add New Member");

        // Create the VBox layout
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefWidth(550); // Set the preferred width of the VBox
        vbox.setMinWidth(550); // Set the minimum width of the VBox
        vbox.setMaxWidth(550); // Set the maximum width of the VBox
        vbox.setPrefHeight(450);

        // Apply background color to the VBox, excluding the fields
        vbox.setStyle("-fx-background-color: #99ccff;");

        // Create title
        Label title = new Label("Add Member");
        title.setStyle(
                "-fx-font-size: 25px; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-family: times roman;");

        // Create form fields with placeholders
        TextField nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.setMaxWidth(400);
        nameInput.setPrefHeight(20);
        nameInput.setMaxHeight(20);
        nameInput.setMinHeight(40);

        TextField emailInput = new TextField();
        emailInput.setPromptText("Email");
        emailInput.setMaxWidth(400);
        emailInput.setMinHeight(40);

        TextField flatNoInput = new TextField();
        flatNoInput.setPromptText("Flat No.");
        flatNoInput.setMaxWidth(400);
        flatNoInput.setMinHeight(40);

        TextField mobileNoInput = new TextField();
        mobileNoInput.setPromptText("Mobile No.");
        mobileNoInput.setMaxWidth(400);
        mobileNoInput.setMinHeight(40);

        TextField familyMembersInput = new TextField();
        familyMembersInput.setPromptText("Family Members");
        familyMembersInput.setMaxWidth(400);
        familyMembersInput.setMinHeight(40);

        Button addButton = new Button("Save Data");
        addButton.setStyle("-fx-background-color: #2eb8b8;-fx-font-size: 16px;-fx-text-fill: white;");
        addButton.setPrefWidth(200);
        addButton.setPrefHeight(40);

        addButton.setOnAction(e -> {
            ManageMembers.Member newMember = new ManageMembers.Member(
                    data.size() + 1, // Generate a new ID
                    nameInput.getText(),
                    emailInput.getText(),
                    flatNoInput.getText(),
                    mobileNoInput.getText(),
                    Integer.parseInt(familyMembersInput.getText()));
            data.add(newMember);

             // Save data to Firestore
            Map<String, Object> memberData = new HashMap<>();
            memberData.put("name", nameInput.getText());
            memberData.put("email", emailInput.getText());
            memberData.put("flatNo", flatNoInput.getText());
            memberData.put("mobileNo", mobileNoInput.getText());
            memberData.put("familyMembers", Integer.parseInt(familyMembersInput.getText()));

            try {
                Dataservice.addMember(nameInput.getText(), memberData);
            } catch (ExecutionException | InterruptedException ex) {
                ex.printStackTrace();
            }

            window.close();
            //window.close();


        });

        // Add title and form fields to the VBox
        vbox.getChildren().addAll(title, nameInput, emailInput, flatNoInput, mobileNoInput, familyMembersInput,
                addButton);

        // Create a root VBox to center the green VBox
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: transparent;"); // Set transparent background for root
        root.getChildren().add(vbox);

        // Create the scene and set it on the stage
        Scene scene = new Scene(root, 300, 400);
        window.setScene(scene);
        window.showAndWait();
    }
}
