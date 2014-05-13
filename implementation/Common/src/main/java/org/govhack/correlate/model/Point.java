package org.govhack.correlate.model;

/**
 * @author Aidan Morgan
 */
public class Point {
    private double value;

    public Point(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
