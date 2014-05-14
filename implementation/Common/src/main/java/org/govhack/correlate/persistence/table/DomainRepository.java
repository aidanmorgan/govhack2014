package org.govhack.correlate.persistence.table;

import com.microsoft.windowsazure.services.core.storage.StorageException;
import com.microsoft.windowsazure.services.table.client.CloudTable;
import com.microsoft.windowsazure.services.table.client.TableOperation;
import org.govhack.correlate.model.Domain;
import org.govhack.correlate.persistence.PersistenceExcepion;
import org.govhack.correlate.persistence.Repository;
import org.govhack.correlate.persistence.RepositoryContext;

import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;


/**
 * @author Aidan Morgan
 */

// http://azure.microsoft.com/en-us/documentation/articles/storage-java-how-to-use-table-storage/
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
        if (val == null) {
            throw new IllegalArgumentException("Cannot save a Domain which is null.");
        }

        if (val.getId() == null) {
            throw new IllegalArgumentException("Cannot save a Domainw with no id value set.");
        }

        try {
            CloudTable table = context.createTableClient().getTableReference(TABLE_NAME);

            if (!table.exists()) {
                table.createIfNotExist();
            }

            TableOperation op = TableOperation.insert(val);
            context.createTableClient().execute(TABLE_NAME, op);
        } catch (URISyntaxException e) {
            throw new PersistenceExcepion(e);
        } catch (StorageException e) {
            throw new PersistenceExcepion(e);
        }
    }

    @Override
    public void delete(Domain val) {

    }

    @Override
    public List<Domain> all() {
        throw new UnsupportedOperationException("Cannot list data that is stored as a blob.");
    }
}
