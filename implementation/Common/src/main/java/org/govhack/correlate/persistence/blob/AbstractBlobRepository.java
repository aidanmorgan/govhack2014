package org.govhack.correlate.persistence.blob;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.storage.StorageException;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;
import org.govhack.correlate.model.Entity;
import org.govhack.correlate.persistence.PersistenceExcepion;
import org.govhack.correlate.persistence.Repository;
import org.govhack.correlate.persistence.RepositoryContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author Aidan Morgan
 */
// helpful guide: http://azure.microsoft.com/en-us/documentation/articles/storage-java-how-to-use-blob-storage/
public abstract class AbstractBlobRepository<T extends Entity> implements Repository<T> {
    private final RepositoryContext context;
    private final Class<T> blobClass;
    private final String containerName;

    public AbstractBlobRepository(RepositoryContext context, Class<T> clazz, String containerName) {
        this.context = context;
        this.blobClass = clazz;
        this.containerName = containerName;
    }

    @Override
    public T get(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Cannot get Data with a null UUID.");
        }

        try {
            CloudBlobContainer container = context.createBlobClient().getContainerReference(containerName);
            CloudBlockBlob blob = container.getBlockBlobReference(id);

            if (!blob.exists()) {
                throw new PersistenceExcepion("Cannot load Data instance with id " + id + " as no instance exists.");
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
    public void add(T val) {
        if (val == null) {
            throw new IllegalArgumentException("Cannot add a null Data instance.");
        }

        if (val.getId() == null) {
            throw new IllegalArgumentException("Cannot add a Data instance with no id specified.");
        }

        try {
            CloudBlobContainer container = context.createBlobClient().getContainerReference(containerName);
            container.createIfNotExists();

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
    public void delete(T val) {
        if (val == null) {
            throw new IllegalArgumentException("Cannot delete a null Data instance.");
        }

        if (val.getId() == null) {
            throw new IllegalArgumentException("Cannot delete a Data instance with a null key.");
        }

        try {
            CloudBlobContainer container = context.createBlobClient().getContainerReference(containerName);
            CloudBlockBlob blob = container.getBlockBlobReference(val.getId().toString());

            blob.deleteIfExists();
        } catch (URISyntaxException e) {
            throw new PersistenceExcepion(e);
        } catch (StorageException e) {
            throw new PersistenceExcepion(e);
        }
    }


    @Override
    public List<T> all() {
        throw new UnsupportedOperationException("Cannot enumerate a Blob storage Repository.");
    }

    private void writeData(T val, CloudBlockBlob blob) throws StorageException, IOException {
        OutputStream os = null;

        try {
            os = blob.openOutputStream();
            toJson(val, os);

        } finally {
            os.close();
        }
    }


    private T readData(CloudBlockBlob blob) throws StorageException, IOException {
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

    private T fromJson(InputStream is) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.reader(blobClass).readValue(is);
    }

    private void toJson(T d, OutputStream os) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writer().writeValue(os, d);
    }
}
