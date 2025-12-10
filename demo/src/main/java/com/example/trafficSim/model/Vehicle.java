package com.example.trafficSim.model;

public class Vehicle {
    private double x, y;
    private final double speed;

    public Vehicle(double x, double y, double speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void move() {
        x += speed;
        if (x > 390) x = 0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getSpeed() {
        return speed;
    }
}