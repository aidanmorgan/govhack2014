package org.govhack.correlate.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;

/**
 * A {@see Range} corresoponds to the y-axis in a cartesian graph.
 *
 * Allows an {@see InterpolationMode} to be specified that is used to "predict" values that are between the defined
 * values in the {@see Range} when dealing with {@see Domain}s of different granularity (months vs. weeks).
 *
 * @author Aidan Morgan
 */
@javax.persistence.Entity
public class Range extends AbstractTableEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private UUID dataId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
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
