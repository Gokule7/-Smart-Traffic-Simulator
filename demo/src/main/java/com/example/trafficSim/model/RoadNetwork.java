package com.example.trafficSim.model;

import java.util.ArrayList;
import java.util.List;

public class RoadNetwork {
    private final int gridSize;
    private final List<Vehicle> vehicles;
    private final List<TrafficLight> trafficLights;

    public RoadNetwork(int gridSize) {
        this.gridSize = gridSize;
        this.vehicles = new ArrayList<>();
        this.trafficLights = new ArrayList<>();
    }

    public void addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
    }

    public void addTrafficLight(TrafficLight light) {
        trafficLights.add(light);
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<TrafficLight> getTrafficLights() {
        return trafficLights;
    }

    public int getGridSize() {
        return gridSize;
    }
}