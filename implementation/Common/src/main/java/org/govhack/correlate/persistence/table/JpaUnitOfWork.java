package org.govhack.correlate.persistence.table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.govhack.correlate.model.*;
import org.govhack.correlate.persistence.Repository;
import org.govhack.correlate.persistence.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * An implementation of the {@see UnitOfWork} interface that uses {@code Hibernate} as the object-relational
 * mapper.
 *
 * @author Aidan Morgan
 */
public class JpaUnitOfWork implements UnitOfWork {
    private static Log s_log = LogFactory.getLog(JpaUnitOfWork.class);

    private final boolean isReadOnly;
    private EntityManager entityManager;

    /**
     * Static factory method for creating new instances of the {@see JpaUnitOfWork}.
     *
     * @param entityManagerFactory the {@see EntityManagerFactory} to use for establishing database connections.
     * @param isReadOnly           {@code true} if this {@see UnitOfWork} should be read-only, {@code false} otherwise.
     * @return a new {@see UnitOfWork} instance, configured to connect to the database using a new {@see EntityManager} instance
     * returned from the provided {@see EntityManagerFactory}.
     */
    public static UnitOfWork create(EntityManagerFactory entityManagerFactory, boolean isReadOnly) {
        EntityManager manager = entityManagerFactory.createEntityManager();

        if (!isReadOnly) {
            manager.getTransaction().begin();
        }

        return new JpaUnitOfWork(manager, isReadOnly);
    }

    private JpaUnitOfWork(EntityManager manager, boolean isReadOnly) {
        this.entityManager = manager;
        this.isReadOnly = isReadOnly;
    }

    private DefaultRepository<DataSet> dataSetRepository;

    @Override
    public Repository<DataSet> getDataSetRepository() {
        if (dataSetRepository == null) {
            dataSetRepository = new DefaultRepository<DataSet>(this, entityManager, DataSet.class);
        }

        return dataSetRepository;
    }

    private DefaultRepository<Range> rangeRepository;

    @Override
    public Repository<Range> getRangeRepository() {
        if (rangeRepository == null) {
            rangeRepository = new DefaultRepository<Range>(this, entityManager, Range.class);
        }

        return rangeRepository;
    }

    private DefaultRepository<Domain> domainRepository;

    @Override
    public Repository<Domain> getDomainRepository() {
        if (domainRepository == null) {
            domainRepository = new DefaultRepository<Domain>(this, entityManager, Domain.class);
        }

        return domainRepository;
    }

    private DefaultRepository<Correlation> correlationRepository;

    @Override
    public Repository<Correlation> getCorrelationRepository() {
        if (correlationRepository == null) {
            correlationRepository = new DefaultRepository<Correlation>(this, entityManager, Correlation.class);
        }

        return correlationRepository;
    }

    private DefaultRepository<DataSetPair> dataSetPairRepository;

    @Override
    public Repository<DataSetPair> getDataSetPairRepository() {
        if (dataSetPairRepository == null) {
            dataSetPairRepository = new DefaultRepository<DataSetPair>(this, entityManager, DataSetPair.class);
        }

        return dataSetPairRepository;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    @Override
    public void save() {
        if (isReadOnly()) {
            throw new IllegalStateException("Cannot call save on a read only UnitOfWork!");
        }

        try {
            entityManager.getTransaction().commit();
        } catch (Throwable e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            if (s_log.isFatalEnabled()) {
                s_log.fatal("Exception thrown during save().", e);
            }
        }
    }

    @Override
    public void close() {
        if (!isReadOnly() && entityManager.getTransaction().isActive()) {
            entityManager.getTransaction().rollback();
        }

        entityManager.close();
    }
}
