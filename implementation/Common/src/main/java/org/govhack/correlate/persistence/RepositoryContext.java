package org.govhack.correlate.persistence;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Aidan Morgan
 */
public class RepositoryContext {
    public static final String STORAGE_ACCOUNT_PROPERTY = "storageAccount";
    public static final String STORAGE_ACCOUNT_KEY_PROPERTY = "storageAccountKey";

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

            return new RepositoryContext(getProperty(props, STORAGE_ACCOUNT_PROPERTY), getProperty(props, STORAGE_ACCOUNT_KEY_PROPERTY));
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // ignore !
                }
            }
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

    private String storageAccount;
    private String storageAccountKey;

    public RepositoryContext(String storageAccount, String storageAccountKey) {
        this.storageAccount = storageAccount;
        this.storageAccountKey = storageAccountKey;
    }

    /**
     * Helper method that will build the Azure connection string using the configured storage account name
     * and key.
     *
     * @return the Azure connection string to use for connection to Azure services.
     */
    private String createConnectionString() {
        StringBuilder buffer = new StringBuilder("DefaultEndpointsProtocol=http;");
        buffer.append("AccountName=");
        buffer.append(storageAccount);
        buffer.append(";");
        buffer.append("AccountKey=");
        buffer.append(storageAccountKey);

        return buffer.toString();
    }

    /**
     * Returns a new instance of the {@see CloudTableClient} configured using the credentials in this context.
     *
     * @return a new instance of the {@see CloudTableClient} configured using the credentials in this context.
     */
    public CloudTableClient createTableClient() {
        CloudStorageAccount storageAccount = CloudStorageAccount.parse(createConnectionString());
        return storageAccount.createCloudTableClient();
    }

    /**
     * Returns a new instance of the {@see CloudBlobClient} configured using the credentials in this context.
     *
     * @return a new instance of the {@see CloudBlobClient} configured using the credentials in this context.
     */
    public CloudBlobClient getBlobClient() {
        CloudStorageAccount storageAccount = CloudStorageAccount.parse(createConnectionString());
        return storageAccount.createCloudBlobClient();
    }
}
