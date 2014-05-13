package org.govhack.correlate.persistence.blob;

import org.govhack.correlate.model.Data;
import org.govhack.correlate.persistence.Repository;
import org.govhack.correlate.persistence.RepositoryContext;

import java.util.List;
import java.util.UUID;

/**
 * @author Aidan Morgan
 */
public class DataRepository implements Repository<Data> {
    public static final String CONTAINER_NAME = "Correlator-Data";

    private RepositoryContext context;

    public DataRepository(RepositoryContext context) {
        this.context = context;
    }

    @Override
    public Data get(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot get Data with a null UUID.");
        }

        // TODO : implement me
        return null;
    }

    @Override
    public void save(Data val) {
        if (val == null) {
            throw new IllegalArgumentException("Cannot save a null Data instance.");
        }

        if (val.getId() == null) {
            throw new IllegalArgumentException("Cannot save a Data instance with no id specified.");
        }

        // TODO : implement me
    }

    @Override
    public void delete(Data val) {
        if (val == null) {
            throw new IllegalArgumentException("Cannot delete a null Data instance.");
        }

        if (val.getId() == null) {
            throw new IllegalArgumentException("Cannot delete a Data instance with a null key.");
        }

        // TODO : implement me
    }


    @Override
    public List<Data> all() {
        throw new UnsupportedOperationException("Cannot enumerate a Blob storage Repository.");
    }
}
