package org.govhack.correlate.model;


import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

/**
 * @author Aidan Morgan
 */
@MappedSuperclass()
public class AbstractTableEntity implements Entity {
    @Id
    private String id;

    public AbstractTableEntity() {
        this(UUID.randomUUID());
    }

    public AbstractTableEntity(UUID id) {
        setId(id.toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String uuid) {
        this.id = uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractTableEntity that = (AbstractTableEntity) o;

        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
