package com.indicium.ui;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EvidenceDashBoard extends Application {

    @Override
    public void start(Stage stage) {
        // 1. Create UI Components
        Label statusLabel = new Label("System Status: Ready");
        statusLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #2a2a2a;");

        Button scanBtn = new Button("Initialize Evidence Scan");

        // 2. Add Logic (Testing the Event Loop)
        scanBtn.setOnAction(e -> {
            System.out.println("[DEBUG] Scan requested at: " + java.time.LocalTime.now());
            statusLabel.setText("Status: Scanning bitstream...");
            scanBtn.setDisable(true);
        });

        // 3. Setup Layout
        VBox root = new VBox(15); // 15px spacing
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(statusLabel, scanBtn);

        // 4. Create Scene and Stage
        Scene scene = new Scene(root, 400, 300);

        stage.setTitle("Indicium Alpha - System Test");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}