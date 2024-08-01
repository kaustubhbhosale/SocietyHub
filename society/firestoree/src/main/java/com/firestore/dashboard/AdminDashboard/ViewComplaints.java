package com.firestore.dashboard.AdminDashboard;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.firestore.firebaseConfig.Dataservice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;

public class ViewComplaints {

    public static Pane getPane() {
        // Create the VBox layout
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(20));
        vbox.setAlignment(Pos.TOP_CENTER); // Align to top center
        vbox.setPrefWidth(800); // Set the preferred width of the VBox
        vbox.setMinWidth(800); // Set the minimum width of the VBox
        vbox.setMaxWidth(800); // Set the maximum width of the VBox
        vbox.setPrefHeight(400);

        // Apply background color to the VBox
        vbox.setStyle("-fx-background-color: #e6f9ff;");

        // Create title
        Label title = new Label("Complaints");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333333; -fx-font-family: Arial;");

        // Create the TableView
        TableView<Complaint> tableView = new TableView<>();
        tableView.setPrefWidth(900);
        // tableView.setMaxWidth(900);
        // tableView.setMinWidth(850);

        // Create columns
        // TableColumn<Complaint, Integer> idColumn = new TableColumn<>("ID");
        // idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        // idColumn.setPrefWidth(50);

        TableColumn<Complaint, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setPrefWidth(200);

        TableColumn<Complaint, String> complaintColumn = new TableColumn<>("Complaint");
        complaintColumn.setCellValueFactory(new PropertyValueFactory<>("complaint"));
        complaintColumn.setPrefWidth(1650);

        // Add columns to TableView
        //tableView.getColumns().add(idColumn);
        tableView.getColumns().add(titleColumn);
        tableView.getColumns().add(complaintColumn);


        // // Sample data
        // ObservableList<Complaint> complaints = FXCollections.observableArrayList(
        //         new Complaint(1, "Leakage problem",
        //                 "This is to inform that there is leakage in my flat due to the society water tank leaking. The tank is placed right above our flat and I request the society to change the tank and take necessary actions on that. C-005, Shubham"),
        //         new Complaint(2, "Clubhouse complaint",
        //                 "This is to inform that the electricity switch board near the door of the clubhouse is not working. I request to fix it as soon as possible."),
        //         new Complaint(3, "Garden problem", "There are repairs in the garden area."));

         // Fetch data from Firestore and populate the TableView
        ObservableList<Complaint> complaints = FXCollections.observableArrayList();
        try {
            // Replace "dataservice" with your actual Firestore data service class
            // Fetch complaints data from Firestore here
            complaints = fetchDataFromFirestore();
        } catch (ExecutionException | InterruptedException e) {
            // Handle exceptions appropriately
            e.printStackTrace();
        }

        // Set data to TableView
        tableView.setItems(complaints);

        // Add title and TableView to the VBox
        vbox.getChildren().addAll(title, tableView);

        // Create a StackPane to center the VBox
        StackPane stackPane = new StackPane();
        stackPane.setAlignment(Pos.CENTER); // Center the VBox within the StackPane
        stackPane.getChildren().add(vbox);
        stackPane.setStyle("-fx-background-color: #f0f8ff;"); // Apply background color to the StackPane

        return stackPane;
    }

     private static ObservableList<Complaint> fetchDataFromFirestore() throws ExecutionException, InterruptedException {
        ObservableList<Complaint> complaints = FXCollections.observableArrayList();
        Dataservice dataservice = new Dataservice(); // Instantiate your Firestore data service

        try {
            List<Map<String, Object>> complaintsData = dataservice.getAllComplaints();

            for (Map<String, Object> complaintData : complaintsData) {
                //int id = (int) (long) complaintData.get("id"); // Assuming "id" is stored as a number in Firestore
                String title = (String) complaintData.get("title");
                String complaint = (String) complaintData.get("complaint");

                complaints.add(new Complaint(title, complaint));
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }

        return complaints;
    }

    public static class Complaint {
       // private final int id;
        private final String title;
        private final String complaint;

        public Complaint( String title, String complaint) {
            //this.id = id;
            this.title = title;
            this.complaint = complaint;
        }

        // public int getId() {
        //     return id;
        // }

        public String getTitle() {
            return title;
        }

        public String getComplaint() {
            return complaint;
        }
    }
}
