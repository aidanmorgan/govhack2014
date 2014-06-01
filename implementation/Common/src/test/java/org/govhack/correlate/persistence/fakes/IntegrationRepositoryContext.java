package org.govhack.correlate.persistence.fakes;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
    private static final Log LOG = LogFactory.getLog(IntegrationRepositoryContext.class);

    public static final String DRIVER_CLASS_PROPERTY = "hibernate.connection.driver_class";
    public static final String DIALECT_PROPERTY = "hibernate.dialect";
    public static final String DDL_AUTOCREATE_PROPERTY = "hibernate.hbm2ddl.auto";

    public static final String BASE_CONNECTION_STRING = "jdbc:h2:file:";

    public static IntegrationRepositoryContext create() {
        return create("Govhack2014-IntegrationTest");
    }

    public static IntegrationRepositoryContext create(String prefix) {
        try {
            File tempFile = File.createTempFile(prefix, ".db");
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
        map.put(DDL_AUTOCREATE_PROPERTY, "create-drop");

        return new JpaUnitOfWorkFactory(map);
    }

    public void clean() {
        try {
            FileUtils.forceDelete(databaseFile);
        } catch (IOException e) {
            if (LOG.isInfoEnabled()) {
                LOG.info("IOException thrown attempting to delete File \"" + getFileName(databaseFile) + "\".", e);
            }
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
