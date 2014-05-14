package org.govhack.correlate.model;

import com.microsoft.windowsazure.services.table.client.TableServiceEntity;

import java.util.UUID;

/**
 * @author Aidan Morgan
 */
public class Domain extends TableServiceEntity {
    private UUID id;
    private String name;
    private DomainType type;

    private UUID dataId;

    public Domain() {
        setId(UUID.randomUUID());
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
        super.setRowKey(id.toString());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Domain domain = (Domain) o;

        if (!id.equals(domain.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
