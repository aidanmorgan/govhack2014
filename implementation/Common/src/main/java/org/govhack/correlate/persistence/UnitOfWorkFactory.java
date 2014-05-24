package org.govhack.correlate.persistence;

/**
 * A {@see UnitOfWorkFactory} is responsible for creating configured {@see UnitOfWork} instances.
 *
 * @author Aidan Morgan
 */
public interface UnitOfWorkFactory {
    /**
     * Creates a new {@see UnitOfWork} instance that can be used to modify the database state.
     * <p/>
     * If there is no need to update the database, then use {@see createReadOnly} instead, as it does not
     * have the database transaction overhead.
     *
     * @return a new {@see UnitOfWork} instance.
     */
    public UnitOfWork create();

    /**
     * Creates a {@see UnitOfWork} instance that only allows querying, any attempt to modify or save data
     * will result in an {@see java.lang.IllegalStateException} being thrown.
     *
     * @return a new {@see UnitOfWork} instance.
     */
    public UnitOfWork createReadOnly();
}
