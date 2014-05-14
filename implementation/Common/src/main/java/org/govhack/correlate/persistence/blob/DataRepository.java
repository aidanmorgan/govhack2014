package org.govhack.correlate.persistence.blob;

import com.microsoft.windowsazure.services.blob.client.CloudBlobContainer;
import com.microsoft.windowsazure.services.blob.client.CloudBlockBlob;
import com.microsoft.windowsazure.services.core.storage.StorageException;
import org.codehaus.jackson.map.ObjectMapper;
import org.govhack.correlate.model.Data;
import org.govhack.correlate.persistence.PersistenceExcepion;
import org.govhack.correlate.persistence.Repository;
import org.govhack.correlate.persistence.RepositoryContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

/**
 * @author Aidan Morgan
 */
// helpful guide: http://azure.microsoft.com/en-us/documentation/articles/storage-java-how-to-use-blob-storage/
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

        try {
            CloudBlobContainer container = context.createBlobClient().getContainerReference(CONTAINER_NAME);
            CloudBlockBlob blob = container.getBlockBlobReference(id.toString());

            if (!blob.exists()) {
                throw new PersistenceExcepion("Cannot load Data instance with id " + id.toString() + " as no instance exists.");
            }

            return readData(blob);
        } catch (URISyntaxException e) {
            throw new PersistenceExcepion(e);
        } catch (StorageException e) {
            throw new PersistenceExcepion(e);
        } catch (IOException e) {
            throw new PersistenceExcepion(e);
        }
    }

    @Override
    public void save(Data val) {
        if (val == null) {
            throw new IllegalArgumentException("Cannot save a null Data instance.");
        }

        if (val.getId() == null) {
            throw new IllegalArgumentException("Cannot save a Data instance with no id specified.");
        }

        try {
            CloudBlobContainer container = context.createBlobClient().getContainerReference(CONTAINER_NAME);
            container.createIfNotExist();

            CloudBlockBlob blob = container.getBlockBlobReference(val.getId().toString());
            writeData(val, blob);
        } catch (URISyntaxException e) {
            throw new PersistenceExcepion(e);
        } catch (StorageException e) {
            throw new PersistenceExcepion(e);
        } catch (IOException e) {
            throw new PersistenceExcepion(e);
        }
    }


    @Override
    public void delete(Data val) {
        if (val == null) {
            throw new IllegalArgumentException("Cannot delete a null Data instance.");
        }

        if (val.getId() == null) {
            throw new IllegalArgumentException("Cannot delete a Data instance with a null key.");
        }

        try {
            CloudBlobContainer container = context.createBlobClient().getContainerReference(CONTAINER_NAME);
            CloudBlockBlob blob = container.getBlockBlobReference(val.getId().toString());

            blob.deleteIfExists();
        } catch (URISyntaxException e) {
            throw new PersistenceExcepion(e);
        } catch (StorageException e) {
            throw new PersistenceExcepion(e);
        }
    }

    @Override
    public List<Data> all() {
        throw new UnsupportedOperationException("Cannot enumerate a Blob storage Repository.");
    }

    private static void writeData(Data val, CloudBlockBlob blob) throws StorageException, IOException {
        OutputStream os = null;

        try {
            os = blob.openOutputStream();
            toJson(val, os);

        } finally {
            os.close();
        }
    }


    private static Data readData(CloudBlockBlob blob) throws StorageException, IOException {
        InputStream is = null;

        try {
            is = blob.openInputStream();
            return fromJson(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    private static Data fromJson(InputStream is) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.reader(Data.class).readValue(is);
    }

    private static void toJson(Data d, OutputStream os) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writer().writeValue(os, d);
    }
}
