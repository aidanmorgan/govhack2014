package org.govhack.correlate.persistence;

import java.util.List;
import java.util.UUID;

/**
 * @author Aidan Morgan
 */
public interface Repository<T> {
    public T get(UUID id);

    public void save(T val);

    public void delete(T val);

    public List<T> all();
}
