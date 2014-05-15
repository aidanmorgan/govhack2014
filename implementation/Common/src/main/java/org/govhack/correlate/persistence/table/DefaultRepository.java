package org.govhack.correlate.persistence.table;

import org.govhack.correlate.model.Entity;
import org.govhack.correlate.persistence.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.UUID;

/**
 * @author Aidan Morgan
 */
public class DefaultRepository<T extends Entity> implements Repository<T> {
    private final JpaUnitOfWork unitOfWork;
    private final EntityManager entityManager;
    private final Class<T> clazz;

    public DefaultRepository(JpaUnitOfWork uow, EntityManager mgr, Class<T> clazz) {
        this.unitOfWork = uow;
        this.entityManager = mgr;
        this.clazz = clazz;
    }

    @Override
    public T get(UUID id) {
        return entityManager.find(clazz, id);
    }

    @Override
    public void add(T val) {
        if(unitOfWork.isReadOnly()) {
            throw new IllegalStateException("Cannot call add() on a read only UnitOfWork.");
        }

        entityManager.persist(val);
    }

    @Override
    public void delete(T val) {
        if(unitOfWork.isReadOnly()) {
            throw new IllegalStateException("Cannot call delete() on a read only UnitOfWork.");
        }

        entityManager.remove(val);
    }

    @Override
    public List<T> all() {
        CriteriaQuery<T> criteria = entityManager.getCriteriaBuilder().createQuery(clazz);
        criteria.select(criteria.from(clazz));

        return entityManager.createQuery(criteria).getResultList();
    }
}
