/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package com.driver.models;

/**
 *
 * @author Aubain
 */
public class GenCoordinateModel {
    private Coordinates centerCoordinate;
    private int radius;
    
    public GenCoordinateModel() {
    }

    public Coordinates getCenterCoordinate() {
        return centerCoordinate;
    }

    public void setCenterCoordinate(Coordinates centerCoordinate) {
        this.centerCoordinate = centerCoordinate;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
    
}
