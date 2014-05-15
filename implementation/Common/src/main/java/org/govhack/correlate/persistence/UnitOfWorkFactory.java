package org.govhack.correlate.persistence;

/**
 * @author Aidan Morgan
 */
public interface UnitOfWorkFactory {
    public UnitOfWork create();

    public UnitOfWork createReadOnly();
}
