package org.govhack.correlate.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Aidan Morgan
 */
public class Data implements Entity {
    private String id;

    private List<Double> values;

    public Data() {
        this.id = UUID.randomUUID().toString();
        this.values = new ArrayList<Double>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (!id.equals(data.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
