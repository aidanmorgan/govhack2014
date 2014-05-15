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
    private final EntityManager mgr;
    private final Class<T> clazz;

    public DefaultRepository(EntityManager mgr, Class<T> clazz) {
        this.mgr = mgr;
        this.clazz = clazz;
    }

    @Override
    public T get(UUID id) {
        return mgr.find(clazz, id);
    }

    @Override
    public void add(T val) {
        mgr.persist(val);
    }

    @Override
    public void delete(T val) {
        mgr.remove(val);
    }

    @Override
    public List<T> all() {
        CriteriaQuery<T> criteria = mgr.getCriteriaBuilder().createQuery(clazz);
        criteria.select(criteria.from(clazz));

        return mgr.createQuery(criteria).getResultList();
    }
}
