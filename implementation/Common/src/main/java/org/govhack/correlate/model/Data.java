package org.govhack.correlate.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Aidan Morgan
 */
public class Data {
    private UUID id;

    private List<Double> values;

    public Data() {
        this.id = UUID.randomUUID();
        this.values = new ArrayList<Double>();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
