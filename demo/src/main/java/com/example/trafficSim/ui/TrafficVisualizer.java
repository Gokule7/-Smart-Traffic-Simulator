package com.example.trafficSim.ui;

import com.example.trafficSim.model.RoadNetwork;
import com.example.trafficSim.model.TrafficLight;
import com.example.trafficSim.model.Vehicle;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TrafficVisualizer {
    private final Canvas canvas;
    private final RoadNetwork roadNetwork;

    public TrafficVisualizer(Canvas canvas, RoadNetwork roadNetwork) {
        this.canvas = canvas;
        this.roadNetwork = roadNetwork;
    }

    public void draw(double congestionLevel) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.DARKGRAY);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(1);
        for (int x = 0; x <= canvas.getWidth(); x += 50) {
            gc.strokeLine(x, 0, x, canvas.getHeight());
        }
        for (int y = 50; y <= canvas.getHeight(); y += 50) {
            gc.strokeLine(0, y, canvas.getWidth(), y);
        }

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(3);
        for (int y = 50; y <= 350; y += 50) {
            gc.strokeLine(0, y, canvas.getWidth(), y);
        }

        for (TrafficLight light : roadNetwork.getTrafficLights()) {
            gc.setFill(light.isGreen() ? Color.GREEN : Color.RED);
            gc.fillRect(light.getX() - 5, light.getY() - 5, 15, 15);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(light.getX() - 5, light.getY() - 5, 15, 15);
        }

        gc.setFont(new Font("Arial", 10));
        int vehicleId = 1;
        for (Vehicle v : roadNetwork.getVehicles()) {
            gc.setFill(congestionLevel > 0.7 ? Color.DARKRED : Color.LIMEGREEN);
            gc.fillOval(v.getX(), v.getY(), 12, 12);
            gc.setFill(Color.WHITE);
            gc.fillText("V" + vehicleId++, v.getX(), v.getY() - 5);
        }

        gc.setFill(Color.BLACK);
        gc.fillRect(canvas.getWidth() - 60, 10, 50, 20);
        gc.setFill(congestionLevel > 0.7 ? Color.RED : Color.GREEN);
        gc.fillRect(canvas.getWidth() - 60, 10, 50 * congestionLevel, 20);
        gc.setStroke(Color.WHITE);
        gc.strokeRect(canvas.getWidth() - 60, 10, 50, 20);
    }
}