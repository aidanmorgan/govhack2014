package org.govhack.correlate.persistence;

import org.govhack.correlate.model.*;

/**
 * A {@see UnitOfWork} provides a facade of a database transaction which exposes access to the
 * aggregate roots of the application through a {@code Repository}.
 *
 * @author Aidan Morgan
 */
public interface UnitOfWork {
    /**
     * Returns the {@see Repository} instance for managing the {@see DataSet} aggregate root.
     * <p/>
     * If the method has not been called before, a new instance will be created.
     * <p/>
     * If the method has been called before then the same instance is returned.
     * <p/>
     * This method is <b>NOT</b> thread safe.
     *
     * @return the {@see Repository} instance for managing the {@see DataSet} aggregate root.
     */
    public Repository<DataSet> getDataSetRepository();

    /**
     * Returns the {@see Repository} instance for managing the {@see DataSetPair} aggregate root.
     * <p/>
     * If the method has not been called before, a new instance will be created.
     * <p/>
     * If the method has been called before then the same instance is returned.
     * <p/>
     * This method is <b>NOT</b> thread safe.
     *
     * @return the {@see Repository} instance for managing the {@see DataSetPair} aggregate root.
     */
    public Repository<DataSetPair> getDataSetPairRepository();

    /**
     * Returns the {@see Repository} instance for managing the {@see Correlation} aggregate root.
     * <p/>
     * If the method has not been called before, a new instance will be created.
     * <p/>
     * If the method has been called before then the same instance is returned.
     * <p/>
     * This method is <b>NOT</b> thread safe.
     *
     * @return the {@see Repository} instance for managing the {@see Correlation} aggregate root.
     */
    public Repository<Correlation> getCorrelationRepository();

    /**
     * Returns the {@see Repository} instance for managing the {@see Diomain} aggregate root.
     * <p/>
     * If the method has not been called before, a new instance will be created.
     * <p/>
     * If the method has been called before then the same instance is returned.
     * <p/>
     * This method is <b>NOT</b> thread safe.
     *
     * @return the {@see Repository} instance for managing the {@see Domain} aggregate root.
     */
    public Repository<Domain> getDomainRepository();

    /**
     * Returns the {@see Repository} instance for managing the {@see Range} aggregate root.
     * <p/>
     * If the method has not been called before, a new instance will be created.
     * <p/>
     * If the method has been called before then the same instance is returned.
     * <p/>
     * This method is <b>NOT</b> thread safe.
     *
     * @return the {@see Repository} instance for managing the {@see Range} aggregate root.
     */
    public Repository<Range> getRangeRepository();

    /**
     * Will flush the current in-memory state of all objects modified (that have been loaded through this
     * instance) to the database.
     * <p/>
     * If this {@see UnitOfWork} is in {@code read only} mode, this method will throw an {@see java.lang.IllegalStateException}.
     */
    public void save();

    /**
     * Closes this {@see UnitOfWork} instance, releasing all resources that are associated with it.
     */
    public void close();
}
