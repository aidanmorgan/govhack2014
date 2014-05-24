package org.govhack.correlate.persistence;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.govhack.correlate.persistence.blob.DataRepository;
import org.govhack.correlate.persistence.table.JpaUnitOfWorkFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Aidan Morgan
 */
public class RepositoryContext {
    public static final String STORAGE_ACCOUNT_PROPERTY = "storageAccount";
    public static final String STORAGE_ACCOUNT_KEY_PROPERTY = "storageAccountKey";
    private static final String HIBERNATE_CONNECTION_URL_PROPERTY = "hibernate.connection.url";
    private static final String HIBERNATE_DDL_AUTO_PROPERTY = "hibernate.hbm2ddl.auto";

    private final Properties properties;

    /**
     * Helper method that will attempt to load a property value from the provided {@see Properties} instance,
     * throwing an {@see IllegalArgumentException} if the value is not specified, if the key value is {@code null} or empty or
     * if a {@code null} {@see Properties} instance is provided.
     *
     * @param prop the {@see Properties} instance to load the value for the provided key.
     * @param key  the key value to look up in the provided {@see Properties} instance.
     * @return the value for the provided key in the provided {@see Properties} instance.
     */
    private static String getProperty(Properties prop, String key) {
        if (prop == null) {
            throw new IllegalArgumentException("Cannot retrieve properties for a null Properties instance.");
        }

        if (StringUtils.isEmpty(key)) {
            throw new IllegalArgumentException("Cannot retrieve an unspecified key value from the Properties instance.");
        }

        String val = prop.getProperty(key, null);

        if (val == null) {
            throw new IllegalArgumentException("Cannot load key " + key + " from Properties.");
        }

        return val;
    }

    /**
     * Creates a new {@see RepositoryContext}, using the properties in the provided {@see InputStream} which must
     * be in {@see Properties} format.
     *
     * @param is the {@see InputStream} to load data from.
     * @return a configured {@see RepositoryContext} instance.
     * @throws IOException if a problem loading from the provided {@see InputStream} occurs.
     */
    public static RepositoryContext loadContext(InputStream is) throws IOException {
        Properties props = new Properties();
        try {
            props.load(is);

            return new RepositoryContext(props);
        } finally {
            IOUtils.closeQuietly(is);
        }

    }

    /**
     * Creates a new {@see RepositoryContext}, using the properties in the provided {@see File} which must
     * be in {@see Properties} format.
     *
     * @param f the {@see File} to load data from.
     * @return a configured {@see RepositoryContext} instance.
     * @throws IOException if a problem loading from the provided {@see InputStream} occurs.
     */
    public static RepositoryContext loadContext(File f) throws IOException {
        return loadContext(new FileInputStream(f));
    }

    public RepositoryContext(Properties props) {
        this.properties = props;
    }


    public DataRepository createDataRepository() {
        return new DataRepository(this);
    }

    /**
     * Helper method that will build the Azure connection string using the configured storage account name
     * and key.
     *
     * @return the Azure connection string to use for connection to Azure services.
     */
    private String createBlobStoreConnectionString() {
        StringBuilder buffer = new StringBuilder("DefaultEndpointsProtocol=http;");
        buffer.append("AccountName=");
        buffer.append(getProperty(properties, STORAGE_ACCOUNT_PROPERTY));
        buffer.append(";");
        buffer.append("AccountKey=");
        buffer.append(getProperty(properties, STORAGE_ACCOUNT_KEY_PROPERTY));

        return buffer.toString();
    }

    /**
     * Returns a new instance of the {@see CloudBlobClient} configured using the credentials in this context.
     *
     * @return a new instance of the {@see CloudBlobClient} configured using the credentials in this context.
     */
    public CloudBlobClient createBlobClient() {
        try {
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(createBlobStoreConnectionString());
            return storageAccount.createCloudBlobClient();
        } catch (URISyntaxException e) {
            throw new PersistenceExcepion(e);
        } catch (InvalidKeyException e) {
            throw new PersistenceExcepion(e);
        }
    }

    public UnitOfWorkFactory createUnitOfWorkFactory() {
        Map<String, String> connProps = new HashMap<String, String>();
        connProps.put("hibernate.connection.url", getProperty(properties, HIBERNATE_CONNECTION_URL_PROPERTY));

        return new JpaUnitOfWorkFactory(connProps);
    }
}
