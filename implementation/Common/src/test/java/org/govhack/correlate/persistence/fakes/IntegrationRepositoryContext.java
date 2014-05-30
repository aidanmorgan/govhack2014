package org.govhack.correlate.persistence.fakes;

import org.apache.commons.io.FileUtils;
import org.govhack.correlate.persistence.RepositoryContext;
import org.govhack.correlate.persistence.UnitOfWorkFactory;
import org.govhack.correlate.persistence.table.JpaUnitOfWorkFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * @author Aidan Morgan
 */
public class IntegrationRepositoryContext extends RepositoryContext {
    public static final String DRIVER_CLASS_PROPERTY = "hibernate.connection.driver_class";
    public static final String DIALECT_PROPERTY = "hibernate.dialect";
    public static final String BASE_CONNECTION_STRING = "jdbc:h2:file:";

    public static IntegrationRepositoryContext create() {
        try {
            File tempFile = File.createTempFile("Govhack2014-IntegrationTest", ".db");
            tempFile.deleteOnExit();

            return new IntegrationRepositoryContext(tempFile);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not create temporary file for integration testing.");
        }
    }

    public static IntegrationRepositoryContext create(File temp) {
        IntegrationRepositoryContext context = new IntegrationRepositoryContext(temp);
        return context;
    }

    private final File databaseFile;

    private IntegrationRepositoryContext(File f) {
        this(f, new Properties());
    }

    public IntegrationRepositoryContext(File f, Properties props) {
        super(props);
        this.databaseFile = f;
    }

    @Override
    protected UnitOfWorkFactory createUnitOfWorkFactory(Map<String, String> map) {
        map.put(CONNECTION_URL_PROPERTY, BASE_CONNECTION_STRING + getFileName(databaseFile));

        map.put(DRIVER_CLASS_PROPERTY, "org.h2.Driver");
        map.put(DIALECT_PROPERTY, "org.hibernate.dialect.H2Dialect");

        return new JpaUnitOfWorkFactory(map);
    }

    public void clean() {
        try {
            FileUtils.forceDelete(databaseFile);
        } catch (IOException e) {
            // going to ignore this
        }
    }

    private static String getFileName(File f) {
        try {
            return f.getCanonicalPath();
        } catch (IOException e) {
            return f.getAbsolutePath();
        }
    }
}
