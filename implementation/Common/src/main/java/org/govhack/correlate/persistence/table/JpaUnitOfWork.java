package org.govhack.correlate.persistence.table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.govhack.correlate.model.DataSet;
import org.govhack.correlate.model.Domain;
import org.govhack.correlate.model.Range;
import org.govhack.correlate.persistence.Repository;
import org.govhack.correlate.persistence.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 * @author Aidan Morgan
 */
public class JpaUnitOfWork implements UnitOfWork {
    private static Log s_log = LogFactory.getLog(JpaUnitOfWork.class);

    private final boolean isReadOnly;
    private EntityManager entityManager;

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
        if(dataSetRepository == null) {
            dataSetRepository = new DefaultRepository<DataSet>(entityManager, DataSet.class);
        }
        
        return dataSetRepository;
    }

    private DefaultRepository<Range> rangeRepository;

    @Override
    public Repository<Range> getRangeRepository() {
        if(rangeRepository == null) {
            rangeRepository = new DefaultRepository<Range>(entityManager, Range.class);
        }

        return rangeRepository;
    }

    private DefaultRepository<Domain> domainRepository;

    @Override
    public Repository<Domain> getDomainRepository() {
        if(domainRepository == null) {
            domainRepository = new DefaultRepository<Domain>(entityManager, Domain.class);
        }

        return domainRepository;
    }

    public boolean isReadOnly() {
        return isReadOnly;
    }

    @Override
    public void save() {
        if (isReadOnly()) {
            throw new IllegalStateException("Cannot call add on a read only UnitOfWork!");
        }

        try {
            entityManager.getTransaction().commit();
        } catch (Throwable e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }

            if (s_log.isFatalEnabled()) {
                s_log.fatal("Exception thrown during add().", e);
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
