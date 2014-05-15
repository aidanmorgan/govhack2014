package org.govhack.correlate.persistence;

import org.govhack.correlate.model.Entity;

import java.util.List;
import java.util.UUID;

/**
 * @author Aidan Morgan
 */
public interface Repository<T extends Entity> {
    public T get(UUID id);

    public void add(T val);

    public void update(T val);

    public void delete(T val);

    public List<T> all();
}
