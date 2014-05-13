package org.govhack.correlate.model;

import java.util.UUID;

/**
 * @author Aidan Morgan
 */
public class Range {
    private UUID id;
    private String name;

    private UUID dataId;

    private RangeType type;
    private InterpolationMode interpolationMode;

    public Range() {
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RangeType getType() {
        return type;
    }

    public UUID getDataId() {
        return dataId;
    }

    public void setDataId(UUID dataId) {
        this.dataId = dataId;
    }

    public void setType(RangeType type) {
        this.type = type;
    }

    public InterpolationMode getInterpolationMode() {
        return interpolationMode;
    }

    public void setInterpolationMode(InterpolationMode interpolationMode) {
        this.interpolationMode = interpolationMode;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Range range = (Range) o;

        if (!id.equals(range.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
