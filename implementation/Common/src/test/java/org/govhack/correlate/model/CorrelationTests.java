package org.govhack.correlate.model;

import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.govhack.correlate.persistence.RepositoryContext;
import org.govhack.correlate.persistence.UnitOfWork;
import org.govhack.correlate.persistence.UnitOfWorkFactory;
import org.govhack.correlate.persistence.fakes.IntegrationRepositoryContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.PersistenceException;
import java.util.UUID;

/**
 * @author Aidan Morgan
 */
public class CorrelationTests {
    private static final Log LOG = LogFactory.getLog(CorrelationTests.class);

    private RepositoryContext repositoryContext;
    private UnitOfWorkFactory uowFactory;

    @Before
    public void beforeTest() {
        repositoryContext = IntegrationRepositoryContext.create("Correlation Tests");
        uowFactory = repositoryContext.createUnitOfWorkFactory();
    }

    @After
    public void afterTest() {
        ((IntegrationRepositoryContext) repositoryContext).clean();
        uowFactory = null;
    }

    @Test
    public void save() {
        Correlation c = new Correlation(createDataSetPair(), createDataSetPair());

        saveCorrelation(c);
    }

    @Test(expected = PersistenceException.class)
    public void saveWithNoDatasetPairOne_shouldThrowPersistenceException() {
        Correlation c = new Correlation(null, createDataSetPair());
        saveCorrelation(c);
    }

    @Test(expected = PersistenceException.class)
    public void saveWithNoDatasetPairTwo_shouldThrowPersistenceException() {
        Correlation c = new Correlation(createDataSetPair(), null);
        saveCorrelation(c);
    }

    @Test(expected = PersistenceException.class)
    public void saveWithDatasetWithNoDomain_shouldThrowPersistenceException() {
        DataSetPair one = createDataSetPair();
        one.setDomain(null);

        Correlation c = new Correlation(one, createDataSetPair());
        saveCorrelation(c);
    }

    @Test(expected = PersistenceException.class)
    public void saveWithDatasetWithNoRange_shouldThrowPersistenceException() {
        DataSetPair one = createDataSetPair();
        one.setRange(null);

        Correlation c = new Correlation(one, createDataSetPair());
        saveCorrelation(c);
    }

    @Test
    public void load() {
        Correlation c = new Correlation(createDataSetPair(), createDataSetPair());
        saveCorrelation(c);

        UnitOfWork uow = null;

        try {
            uow = uowFactory.create();
            Correlation loaded = uow.getCorrelationRepository().get(c.getId());

            Assert.assertEquals(c.getId(), loaded.getId());

            Assert.assertEquals(c.getPairOne(), loaded.getPairOne());
            Assert.assertEquals(c.getPairTwo(), loaded.getPairTwo());
        } finally {
            if (uow != null) {
                uow.close();
            }
        }

    }

    // ----- HELPER METHODS -----
    private DataSetPair createDataSetPair() {
        return new DataSetPair("Test DataSetPair", createDefaultDomain(), createDefaultRange());
    }

    private Domain createDefaultDomain() {
        return new Domain("Domain", DomainType.DATE, UUID.randomUUID().toString());
    }

    private Range createDefaultRange() {
        return new Range("Range", RangeType.NUMERICAL, InterpolationMode.createDefaultValue(1.0), UUID.randomUUID().toString());
    }

    private void saveCorrelation(Correlation c) {
        UnitOfWork uow = null;

        try {
            uow = uowFactory.create();
            uow.getCorrelationRepository().add(c);
            uow.save();
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
    }
}
