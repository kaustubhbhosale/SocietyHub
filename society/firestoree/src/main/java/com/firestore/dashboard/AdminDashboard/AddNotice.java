package com.firestore.dashboard.AdminDashboard;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.firestore.firebaseConfig.Dataservice;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;

public class AddNotice {

         private static Dataservice dataservice = new Dataservice();

    public static Pane getPane() {
        // Create the VBox layout
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.TOP_CENTER); // Align to top center
        vbox.setPrefWidth(550); // Set the preferred width of the VBox
        vbox.setMinWidth(550); // Set the minimum width of the VBox
        vbox.setMaxWidth(550); // Set the maximum width of the VBox
        vbox.setPrefHeight(350);
        vbox.setLayoutX(220);
        vbox.setLayoutY(50);

        // Apply background color to the VBox, excluding the fields
        // vbox.setStyle("-fx-background-color: #99ccff;");

        // Create title
        Label title = new Label("Create Notice");
        title.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: black; -fx-font-family: Arial;");

        // Create form fields with placeholders
        TextField noticeNameInput = new TextField();
        noticeNameInput.setPromptText("Notice Name");
        noticeNameInput.setMaxWidth(700);
        noticeNameInput.setMinHeight(38);

        TextField noticeTypeInput = new TextField();
        noticeTypeInput.setPromptText("Notice Type");
        noticeTypeInput.setMaxWidth(700);
        noticeTypeInput.setMinHeight(38);

        DatePicker noticeDateInput = new DatePicker();
        noticeDateInput.setPromptText("MM/DD/YYYY");
        noticeDateInput.setMaxWidth(700);
        noticeDateInput.setMinHeight(38);

        TextArea noticeMessageInput = new TextArea();
        noticeMessageInput.setPromptText("Message");
        noticeMessageInput.setMaxWidth(700);
        noticeMessageInput.setPrefRowCount(6);
        noticeMessageInput.setMinHeight(150);

        Button sendButton = new Button("Send");
        sendButton.setStyle("-fx-background-color: #2eb8b8; -fx-font-size: 15px;");
        sendButton.setMaxWidth(150);
        sendButton.setMinHeight(38);

        sendButton.setOnAction(e -> {
            // Handle sending the notice
            String noticeName = noticeNameInput.getText();
            String noticeType = noticeTypeInput.getText();
            String noticeDate = noticeDateInput.getValue() != null
                    ? noticeDateInput.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))
                    : "";
            String noticeMessage = noticeMessageInput.getText();

             // Create a map with notice details
            Map<String, Object> noticeData = new HashMap<>();
            noticeData.put("name", noticeName);
            noticeData.put("type", noticeType);
            noticeData.put("date", noticeDate);
            noticeData.put("message", noticeMessage);

             try {
                // Add notice data to Firestore using the data service
                dataservice.addNotice(noticeName, noticeData);


                System.out.println("Notice added successfully.");
                noticeNameInput.clear();
                noticeTypeInput.clear();
                noticeDateInput.setValue(null); 
                noticeMessageInput.clear();

                 // Show success alert
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Notice Submitted");
                alert.setHeaderText(null);
                alert.setContentText("Notice submitted successfully.");
                alert.showAndWait();
                
               // snackbar.show("Notice submitted successfully", 3000);
            } catch (ExecutionException | InterruptedException ex) {
                ex.printStackTrace();
                // Handle exceptions appropriately
            }

             // Display success message using Snackbar
            
            // Add logic to handle the notice submission
            System.out.println("Notice Name: " + noticeName);
            System.out.println("Notice Type: " + noticeType);
            System.out.println("Notice Date: " + noticeDate);
            System.out.println("Notice Message: " + noticeMessage);
        });


        // Add title and form fields to the VBox
        vbox.getChildren().addAll(title, noticeNameInput, noticeTypeInput, noticeDateInput, noticeMessageInput,
                sendButton);

        return vbox;
    }
}
