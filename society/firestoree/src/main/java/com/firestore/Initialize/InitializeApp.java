package com.firestore.Initialize;

import com.firestore.controller.Logincontroller;
import com.firestore.dashboard.AdminDashboard.AdminPage;
import com.firestore.firebaseConfig.Dataservice;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class InitializeApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Logincontroller loginController = new Logincontroller(primaryStage);
        primaryStage.setScene(loginController.getLoginScene());
        primaryStage.setTitle("Login");
        primaryStage.show();

    }
}
