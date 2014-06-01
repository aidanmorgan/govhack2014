package org.govhack.correlate.model;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * A {@see Domain} corresponds traditionally to the x-axis of a cartesian graph.
 * <p/>
 * Allows a specific {@see DomainType} to be specified that allows different correlation methods to be used
 * depending on what the domain represents (e.g. dates, geospatial coordinates).
 *
 * @author Aidan Morgan
 */
@javax.persistence.Entity
public class Domain extends AbstractTableEntity {
    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DomainType type;

    @Column(nullable = false)
    private String dataId;

    public Domain() {
    }

    public Domain(String name, DomainType type) {
        setName(name);
        setType(type);
    }


    public DomainType getType() {
        return type;
    }

    public void setType(DomainType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }
}
