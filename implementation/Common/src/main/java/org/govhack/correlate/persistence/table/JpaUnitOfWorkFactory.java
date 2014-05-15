package org.govhack.correlate.persistence.table;

import org.govhack.correlate.persistence.UnitOfWork;
import org.govhack.correlate.persistence.UnitOfWorkFactory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author Aidan Morgan
 */
public class JpaUnitOfWorkFactory implements UnitOfWorkFactory {

    public static final String PERSISTENCE_UNIT_NAME = "azure";
    private EntityManagerFactory entityManagerFactory;

    public JpaUnitOfWorkFactory() {
        this(Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME));
    }

    public JpaUnitOfWorkFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public UnitOfWork create() {
        return JpaUnitOfWork.create(entityManagerFactory, false);
    }

    @Override
    public UnitOfWork createReadOnly() {
        return JpaUnitOfWork.create(entityManagerFactory, true);
    }
}
