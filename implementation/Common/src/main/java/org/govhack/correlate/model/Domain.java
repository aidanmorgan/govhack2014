package org.govhack.correlate.model;

import java.util.UUID;

/**
 * @author Aidan Morgan
 */
@javax.persistence.Entity
public class Domain extends AbstractTableEntity {
    private String name;
    private DomainType type;
    private UUID dataId;

    public Domain() {
    }

    public Domain(String name, DomainType type) {
        this.name = name;
        this.type = type;
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

    public UUID getDataId() {
        return dataId;
    }

    public void setDataId(UUID dataId) {
        this.dataId = dataId;
    }
}
