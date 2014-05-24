package org.govhack.correlate.persistence;

import org.govhack.correlate.model.Entity;

import java.util.List;
import java.util.UUID;

/**
 * An interface that defines the basic operations a {@see Repository} should provide.
 *
 * @author Aidan Morgan
 */
public interface Repository<T extends Entity> {
    /**
     * Returns the {@see T} instance with the provided id, {@code null} if no entity with the provided key exists.
     *
     * @param id the id to retrieve the entity for.
     * @return the entity with the provided id, {@code null} otherwise.
     */
    public T get(UUID id);

    /**
     * Adds the provided {@see T} instance to the database, will not be persisted until the owning {@see UnitOfWork}
     * {@see UnitOfWork#save()} method has been called.
     *
     * @param val the value to add to the database.
     */
    public void add(T val);

    /**
     * Deletes the provided {@see T} instance from the database, will not be persisted until the owning {@see UnitOfWork}
     * {@see UnitOfWork#save()} method has been called.
     *
     * @param val the value to delete from the database.
     */
    public void delete(T val);

    /**
     * Returns a {@see List} of all {@code T} entities in the database.
     *
     * @return a {@see List} of all {@code T} entities in the database.
     */
    public List<T> all();
}
