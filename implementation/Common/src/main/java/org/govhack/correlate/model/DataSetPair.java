package org.govhack.correlate.model;

import org.govhack.correlate.persistence.Repository;

import java.util.UUID;

/**
 * @author Aidan Morgan
 */
public class DataSetPair {
    private String name;

    private UUID id;

    private UUID domainId;
    private UUID rangeId;

    public DataSetPair() {
        this.id = UUID.randomUUID();
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

    public UUID getDomainId() {
        return domainId;
    }

    public Domain getDomain(Repository<Domain> repo) {
        if (domainId == null) {
            throw new IllegalArgumentException("Cannot get Domain instance for a null UUID.");
        }

        return repo.get(domainId);
    }

    public void setDomainId(UUID domainId) {
        this.domainId = domainId;
    }

    public UUID getRangeId() {
        return rangeId;
    }

    public Range getRange(Repository<Range> repo) {
        if (rangeId == null) {
            throw new IllegalArgumentException("Cannot get Range instance for a null UUID.");
        }

        return repo.get(rangeId);
    }

    public void setRangeId(UUID rangeId) {
        this.rangeId = rangeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataSetPair that = (DataSetPair) o;

        if (!domainId.equals(that.domainId)) return false;
        if (!rangeId.equals(that.rangeId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = domainId.hashCode();
        result = 31 * result + rangeId.hashCode();
        return result;
    }
}
