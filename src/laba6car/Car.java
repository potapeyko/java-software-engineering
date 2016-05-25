/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package laba6car;

/**
 *
 * @author Дмитрий
 */
public class Car {

    public void setMaxspeed(double maxspeed) {
        this.maxspeed = maxspeed;
    }

    
    private double x = 0;//м
    private double speed = 0;//м/с
    private double acceleration;//м/с2
    private double maxacceleration;
    private double maxdeceleration;
    private final double comfortdeceleration = 4;
    private double maxspeed;//м/с 
    public Car() {
        this(150.0*1000/3600);
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public double getComfortdeceleration() {
        return comfortdeceleration;
    }
    public Car(double maxspeed) {
        acceleration = 0;
        maxacceleration = 3;//среднее значение обычных авто
        maxdeceleration = 7;//среднее значение
        this.maxspeed = maxspeed;
    }
    
    public double getMaxacceleration() {
        return maxacceleration;
    }

    public double getMaxdeceleration() {
        return maxdeceleration;
    }

    public double getMaxspeed() {
        return maxspeed;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAccel() {
        return acceleration;
    }
}

