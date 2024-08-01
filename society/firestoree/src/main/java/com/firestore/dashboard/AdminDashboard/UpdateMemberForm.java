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

public class UpdateMemberForm {

    private static Dataservice dataservice = new Dataservice();

    public static void display(ManageMembers.Member member, ObservableList<ManageMembers.Member> data,String initialName) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Update Member");

        // Create the VBox layout
        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefWidth(650); // Set the preferred width of the VBox
        vbox.setMinWidth(650); // Set the minimum width of the VBox
        vbox.setMaxWidth(650); // Set the maximum width of the VBox
        vbox.setPrefHeight(550);

        // Apply background color to the VBox, excluding the fields
        vbox.setStyle("-fx-background-color: #99ccff;");

        // Create title
        Label title = new Label("Update Member");
        title.setStyle(
                "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-family: times roman;");

        // Create form fields with placeholders and pre-filled data
        TextField nameInput = new TextField(member.getName());
        nameInput.setPromptText("Name");
        nameInput.setMaxWidth(400);

        TextField emailInput = new TextField(member.getEmail());
        emailInput.setPromptText("Email");
        emailInput.setMaxWidth(400);

        TextField flatNoInput = new TextField(member.getFlatNo());
        flatNoInput.setPromptText("Flat No.");
        flatNoInput.setMaxWidth(400);

        TextField mobileNoInput = new TextField(member.getMobileNo());
        mobileNoInput.setPromptText("Mobile No.");
        mobileNoInput.setMaxWidth(400);

        Button updateButton = new Button("Update Data");
        updateButton.setStyle("-fx-background-color: #2eb8b8;");

        updateButton.setOnAction(e -> {
            // member.setName(nameInput.getText());
            // member.setEmail(emailInput.getText());
            // member.setFlatNo(flatNoInput.getText());
            // member.setMobileNo(mobileNoInput.getText());


             // Update Firestore
            Map<String, Object> updatedData = new HashMap<>();
            updatedData.put("name", nameInput.getText());
            updatedData.put("email", emailInput.getText());
            updatedData.put("flatNo", flatNoInput.getText());
            updatedData.put("mobileNo", mobileNoInput.getText());
            updatedData.put("familyMembers", member.getFamilyMembers()); // Assuming this field is not editable

            try {
                dataservice.updateMember(initialName, updatedData);
                member.setName(nameInput.getText());
            member.setEmail(emailInput.getText());
            member.setFlatNo(flatNoInput.getText());
            member.setMobileNo(mobileNoInput.getText());

            // No need to re-add the member to the ObservableList
            data.set(data.indexOf(member), member);

            } catch (ExecutionException | InterruptedException ex) {
                ex.printStackTrace();
            }

            // Refresh the table view to show updated data
           // data.set(data.indexOf(member), member);
            window.close();
        });

        // Add title and form fields to the VBox
        vbox.getChildren().addAll(title, nameInput, emailInput, flatNoInput, mobileNoInput, updateButton);

        // Create a root VBox to center the green VBox
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: transparent;"); // Set transparent background for root
        root.getChildren().add(vbox);

        // Create the scene and set it on the stage
        Scene scene = new Scene(root, 600, 450); // Adjusted scene size to accommodate VBox width
        window.setScene(scene);
        window.showAndWait();
    }
}
