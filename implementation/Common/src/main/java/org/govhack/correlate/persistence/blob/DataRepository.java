package org.govhack.correlate.persistence.blob;

import org.govhack.correlate.model.Data;
import org.govhack.correlate.persistence.RepositoryContext;

/**
 * @author Aidan Morgan
 */
public class DataRepository extends AbstractBlobRepository<Data> {
    public static final String CONTAINER_NAME = "Correlate-Data";

    public DataRepository(RepositoryContext context) {
        super(context, Data.class, CONTAINER_NAME);
    }
}
