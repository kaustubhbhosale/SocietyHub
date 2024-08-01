package com.firestore.dashboard.AdminDashboard;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.firestore.firebaseConfig.Dataservice;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ManageMembers {

     private static Dataservice dataservice = new Dataservice();
    private static ObservableList<Member> data;

    public static VBox getPane() {
        VBox pane = new VBox(10); // VBox with spacing between elements
        pane.setPadding(new Insets(30)); // Padding for the VBox

        // Create Add Member button
        Button addMemberButton = new Button("Add Member");
        addMemberButton.setStyle(
                "-fx-background-color: #2eb8b8; -fx-text-fill: white; -fx-font-family: Arial;-fx-font-size: 16px;");
        addMemberButton.setOnAction(e -> AddMemberForm.display(data));

        pane.getChildren().add(addMemberButton);

        // Create table for members
        TableView<Member> table = new TableView<>();
        table.setPrefHeight(600); // Increase table height
        table.setPrefWidth(1000); // Increase table width

        // Create columns
        TableColumn<Member, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Member, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Member, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Member, String> flatNoColumn = new TableColumn<>("FlatNo.");
        flatNoColumn.setCellValueFactory(new PropertyValueFactory<>("flatNo"));

        TableColumn<Member, String> mobileNoColumn = new TableColumn<>("Mobile No.");
        mobileNoColumn.setCellValueFactory(new PropertyValueFactory<>("mobileNo"));

        TableColumn<Member, Integer> familyMembersColumn = new TableColumn<>("No. of Family Members");
        familyMembersColumn.setCellValueFactory(new PropertyValueFactory<>("familyMembers"));

        TableColumn<Member, Button> updateColumn = new TableColumn<>("Update");
        updateColumn.setCellValueFactory(new PropertyValueFactory<>("updateButton"));

        TableColumn<Member, Button> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setCellValueFactory(new PropertyValueFactory<>("deleteButton"));

        // Add columns to table
        table.getColumns().addAll(idColumn, nameColumn, emailColumn, flatNoColumn, mobileNoColumn, familyMembersColumn,
                updateColumn, deleteColumn);

        // Set the column widths
        idColumn.setMinWidth(50);
        nameColumn.setMinWidth(100);
        emailColumn.setMinWidth(150);
        flatNoColumn.setMinWidth(80);
        mobileNoColumn.setMinWidth(120);
        familyMembersColumn.setMinWidth(150);
        updateColumn.setMinWidth(100);
        deleteColumn.setMinWidth(120);

        // Initialize an empty ObservableList<Member>
        // Sample data initialization
        // Sample data
        data = FXCollections.observableArrayList(
                // new Member(1, "John Doe", "john@example.com", "101", "1234567890", 4),
                // new Member(2, "Jane Doe", "jane@example.com", "102", "0987654321", 3)
                );


                 // Fetch data from Firestore and populate the table
        try {
            List<Map<String, Object>> members = dataservice.getAllMembers();
            for (Map<String, Object> member : members) {
                data.add(new Member(
                        data.size() + 1,
                        (String) member.get("name"),
                        (String) member.get("email"),
                        (String) member.get("flatNo"),
                        (String) member.get("mobileNo"),
                        ((Long) member.get("familyMembers")).intValue()
                ));
            }
        } catch (ExecutionException | InterruptedException ex) {
            ex.printStackTrace();
        }
        table.setItems(data);

        // Apply styles to table
        table.setStyle("-fx-font-size: 16px; -fx-font-family: Arial;");

        // Apply styles to table headers
        table.getStylesheets().add(AdminPage.class.getResource("/styles.css").toExternalForm());

        // Add table to pane
        pane.getChildren().add(table);

        return pane;
    }

    public static class Member {
        private Integer id;
        private String name;
        private String email;
        private String flatNo;
        private String mobileNo;
        private Integer familyMembers;
        private final Button updateButton;
        private final Button deleteButton;

        public Member(Integer id, String name, String email, String flatNo, String mobileNo, Integer familyMembers) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.flatNo = flatNo;
            this.mobileNo = mobileNo;
            this.familyMembers = familyMembers;
            this.updateButton = new Button("Update");
            this.deleteButton = new Button("Delete");

            // Style the buttons
            updateButton.setStyle(
                    "-fx-background-color: #2eb8b8; -fx-text-fill: white; -fx-font-family: Arial; -fx-font-size: 16px; -fx-padding: 5 15 5 15;-fx-font-weight: bold;");
            deleteButton.setStyle(
                    "-fx-background-color: #2eb8b8; -fx-text-fill: white; -fx-font-family: Arial; -fx-font-size: 16px; -fx-padding: 5 15 5 15;-fx-font-weight: bold;");
            updateButton.setOnAction(e -> UpdateMemberForm.display(this, data,name));
           // deleteButton.setOnAction(e -> data.remove(this));

           deleteButton.setOnAction(e -> {
            try {
                dataservice.deleteMember(name);
                data.remove(this);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFlatNo() {
            return flatNo;
        }

        public void setFlatNo(String flatNo) {
            this.flatNo = flatNo;
        }

        public String getMobileNo() {
            return mobileNo;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public Integer getFamilyMembers() {
            return familyMembers;
        }

        public void setFamilyMembers(Integer familyMembers) {
            this.familyMembers = familyMembers;
        }

        public Button getUpdateButton() {
            return updateButton;
        }

        public Button getDeleteButton() {
            return deleteButton;
        }
    }
}
