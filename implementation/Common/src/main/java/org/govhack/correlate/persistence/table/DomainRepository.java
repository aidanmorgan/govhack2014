package org.govhack.correlate.persistence.table;

import org.govhack.correlate.model.Domain;
import org.govhack.correlate.persistence.Repository;
import org.govhack.correlate.persistence.RepositoryContext;

import java.util.List;
import java.util.UUID;


/**
 * @author Aidan Morgan
 */
public class DomainRepository implements Repository<Domain> {
    public static final String TABLE_NAME = "Correlate-Domains";

    private RepositoryContext context;

    public DomainRepository(RepositoryContext context) {
        this.context = context;
    }

    @Override
    public Domain get(UUID id) {
        return null;
    }

    @Override
    public void save(Domain val) {

    }

    @Override
    public void delete(Domain val) {

    }

    @Override
    public List<Domain> all() {
        throw new UnsupportedOperationException("Cannot list data that is stored as a blob.");
    }
}
