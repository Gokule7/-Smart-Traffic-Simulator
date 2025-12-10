package com.example.trafficSim.ai;

import com.example.trafficSim.model.TrafficLight;
import com.example.trafficSim.model.Vehicle;

public class VehicleAgent {
    private final Vehicle vehicle;

    public VehicleAgent(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void update(double congestionLevel, TrafficLight light) {
        boolean canMove = true;
        if (light != null && Math.abs(vehicle.getX() - light.getX()) < 20 && !light.isGreen()) {
            canMove = false;
        }
        double adjustedSpeed = congestionLevel > 0.7 ? vehicle.getSpeed() * 0.5 : vehicle.getSpeed();
        if (canMove) {
            vehicle.move();
        }
    }
}