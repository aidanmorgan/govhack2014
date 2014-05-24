package org.govhack.correlate.model;

import javax.persistence.Embeddable;

/**
 * @author Aidan Morgan
 */
@Embeddable
public class InterpolationMode {
    private boolean isDefault;
    private double defaultValue;
    private boolean isSpline;
    private boolean isRepeatPrevious;

    public static InterpolationMode createDefaultValue(double defaultValue) {
        InterpolationMode mode = new InterpolationMode();
        mode.setDefault(true);
        mode.setDefaultValue(defaultValue);

        return mode;
    }

    public static InterpolationMode createSpline() {
        InterpolationMode mode = new InterpolationMode();
        mode.setSpline(true);

        return mode;
    }

    public static InterpolationMode createRepeatLast() {
        InterpolationMode mode = new InterpolationMode();
        mode.setRepeatPrevious(true);

        return mode;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public double getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(double defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isSpline() {
        return isSpline;
    }

    public void setSpline(boolean isSpline) {
        this.isSpline = isSpline;
    }

    public boolean isRepeatPrevious() {
        return isRepeatPrevious;
    }

    public void setRepeatPrevious(boolean isRepeatPrevious) {
        this.isRepeatPrevious = isRepeatPrevious;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        InterpolationMode that = (InterpolationMode) o;

        if (Double.compare(that.defaultValue, defaultValue) != 0) return false;
        if (isDefault != that.isDefault) return false;
        if (isSpline != that.isSpline) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (isDefault ? 1 : 0);
        temp = Double.doubleToLongBits(defaultValue);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (isSpline ? 1 : 0);
        return result;
    }
}
