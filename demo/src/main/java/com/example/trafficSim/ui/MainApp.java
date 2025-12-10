package com.example.trafficSim.ui;

import java.util.ArrayList;
import java.util.List;

import com.example.trafficSim.ai.VehicleAgent;
import com.example.trafficSim.ml.TrafficPredictor;
import com.example.trafficSim.model.RoadNetwork;
import com.example.trafficSim.model.TrafficLight;
import com.example.trafficSim.model.Vehicle;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {
    private RoadNetwork roadNetwork;
    private TrafficPredictor predictor;
    private TrafficVisualizer visualizer;
    private List<VehicleAgent> agents;
    private TrafficLight trafficLight;
    private long lastToggleTime = 0;

    @Override
    public void start(Stage primaryStage) {
        // Initialize simulation components
        roadNetwork = new RoadNetwork(10);
        roadNetwork.addVehicle(new Vehicle(50, 50, 1.0));
        roadNetwork.addVehicle(new Vehicle(50, 100, 0.8));
        roadNetwork.addVehicle(new Vehicle(50, 150, 1.2));
        trafficLight = new TrafficLight(200, 50);
        roadNetwork.addTrafficLight(trafficLight);

        // Initialize machine learning model
        predictor = new TrafficPredictor();
        predictor.generateData();
        predictor.train();

        // Set up visualization
        Canvas canvas = new Canvas(400, 400);
        visualizer = new TrafficVisualizer(canvas, roadNetwork);
        agents = new ArrayList<>();
        roadNetwork.getVehicles().forEach(v -> agents.add(new VehicleAgent(v)));

        // Control pane
        VBox controlPane = new VBox(10);
        controlPane.setPadding(new Insets(10));
        controlPane.setStyle("-fx-background-color: #ffffff; -fx-border-color: #d0d0d0; -fx-border-width: 1;");

        Button addVehicleButton = new Button("Add Vehicle");
        addVehicleButton.setOnAction(e -> {
            Vehicle newVehicle = new Vehicle(50, 50 + agents.size() * 50 % 300, 1.0);
            roadNetwork.addVehicle(newVehicle);
            agents.add(new VehicleAgent(newVehicle));
        });

        ToggleButton lightToggleButton = new ToggleButton("Toggle Light");
        lightToggleButton.setOnAction(e -> trafficLight.toggle());

        Slider spawnRateSlider = new Slider(0, 5, 0);
        spawnRateSlider.setShowTickMarks(true);
        spawnRateSlider.setShowTickLabels(true);
        Label spawnRateLabel = new Label("Spawn Rate (vehicles/sec): 0.0");

        Slider lightTimingSlider = new Slider(2, 10, 5);
        lightTimingSlider.setShowTickMarks(true);
        lightTimingSlider.setShowTickLabels(true);
        Label lightTimingLabel = new Label("Light Toggle (sec): 5.0");

        controlPane.getChildren().addAll(
            new Label("Controls"),
            addVehicleButton,
            lightToggleButton,
            new Label("Spawn Rate"),
            spawnRateSlider,
            spawnRateLabel,
            new Label("Light Timing"),
            lightTimingSlider,
            lightTimingLabel
        );

        // Status bar
        HBox statusBar = new HBox(10);
        statusBar.setPadding(new Insets(5));
        Label congestionLabel = new Label("Congestion: 0.0");
        Label vehicleCountLabel = new Label("Vehicles: 3");
        statusBar.getChildren().addAll(congestionLabel, vehicleCountLabel);

        // Set up layout
        BorderPane root = new BorderPane();
        root.setCenter(canvas);
        root.setRight(controlPane);
        root.setBottom(statusBar);
        Scene scene = new Scene(root, 600, 450);
        scene.getStylesheets().add(getClass().getResource("/com/example/trafficSim/styles.css").toExternalForm());

        primaryStage.setTitle("Smart Traffic Simulator");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Animation loop
        long[] lastSpawnTime = {0};
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Update traffic light timing
                double intervalSec = lightTimingSlider.getValue();
                lightTimingLabel.setText(String.format("Light Toggle (sec): %.1f", intervalSec));
                if (now - lastToggleTime > intervalSec * 1_000_000_000L) {
                    trafficLight.toggle();
                    lastToggleTime = now;
                }

                // Spawn new vehicles based on rate
                double spawnRate = spawnRateSlider.getValue();
                spawnRateLabel.setText(String.format("Spawn Rate (vehicles/sec): %.1f", spawnRate));
                if (spawnRate > 0 && now - lastSpawnTime[0] > 1_000_000_000L / spawnRate) {
                    Vehicle newVehicle = new Vehicle(50, 50 + agents.size() * 50 % 300, 1.0);
                    roadNetwork.addVehicle(newVehicle);
                    agents.add(new VehicleAgent(newVehicle));
                    lastSpawnTime[0] = now;
                }

                // Predict congestion and update agents
                double avgSpeed = roadNetwork.getVehicles().stream()
                    .mapToDouble(Vehicle::getSpeed)
                    .average().orElse(1.0);
                double isGreen = trafficLight.isGreen() ? 1.0 : 0.0;
                double vehicleCount = roadNetwork.getVehicles().size();
                double congestion = predictor.predict(vehicleCount, avgSpeed, isGreen);
                // Debug logging
                System.out.println("VehicleCount: " + vehicleCount + ", AvgSpeed: " + avgSpeed + ", isGreen: " + isGreen + ", Congestion: " + congestion);

                agents.forEach(agent -> agent.update(congestion, trafficLight));

                // Update UI
                visualizer.draw(congestion);
                congestionLabel.setText(String.format("Congestion: %.2f", congestion));
                vehicleCountLabel.setText("Vehicles: " + roadNetwork.getVehicles().size());
            }
        };
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}