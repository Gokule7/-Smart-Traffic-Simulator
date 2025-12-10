package com.example.trafficSim.model;

public class TrafficLight {
    private boolean isGreen;
    private final double x, y;

    public TrafficLight(double x, double y) {
        this.x = x;
        this.y = y;
        this.isGreen = true;
    }

    public void toggle() {
        isGreen = !isGreen;
    }

    public boolean isGreen() {
        return isGreen;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}