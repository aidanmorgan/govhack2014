package org.govhack.correlate.model;

import javax.persistence.Embedded;
import java.util.UUID;

/**
 * @author Aidan Morgan
 */
@javax.persistence.Entity
public class Range extends AbstractTableEntity {
    private String name;

    private UUID dataId;

    private RangeType type;

    @Embedded
    private InterpolationMode interpolationMode;

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
}
