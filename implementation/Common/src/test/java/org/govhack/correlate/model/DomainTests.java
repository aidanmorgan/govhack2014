package org.govhack.correlate.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.govhack.correlate.persistence.RepositoryContext;
import org.govhack.correlate.persistence.UnitOfWork;
import org.govhack.correlate.persistence.UnitOfWorkFactory;
import org.govhack.correlate.persistence.fakes.IntegrationRepositoryContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @author Aidan Morgan
 */
public class DomainTests {
    private static final Log LOG = LogFactory.getLog(DomainTests.class);

    private RepositoryContext repositoryContext;
    private UnitOfWorkFactory uowFactory;

    @Before
    public void beforeTest() {
        repositoryContext = IntegrationRepositoryContext.create("Domain Tests");
        uowFactory = repositoryContext.createUnitOfWorkFactory();
    }

    @After
    public void afterTest() {
        ((IntegrationRepositoryContext) repositoryContext).clean();
        uowFactory = null;
    }

    @Test
    public void save() {
        Domain d = createDomain();
        saveDomain(d);
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void saveWithNoIdSet_shouldThrowPersistenceException() {
        Domain d = createDomain();
        d.setId(null);

        saveDomain(d);
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void saveWithNoDataIdSet_shouldThrowPersistenceException() {
        Domain d = createDomain();
        d.setDataId(null);

        saveDomain(d);
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void saveWithNoNameSet_shouldThrowPersistenceException() {
        Domain d = new Domain(null, DomainType.DATE_TIME);
        saveDomain(d);
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void saveWithNoDomainTypeSet_shouldThrowPersistenceException() {
        Domain d = new Domain("Test Domain", null);
        saveDomain(d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNullDomain_shouldThrowIllegalArgumentException() {
        saveDomain(null);
    }

    @Test(expected = IllegalStateException.class)
    public void saveDomainToReadOnlyRepository_shouldThrowIllegalStateException() {
        Domain d = createDomain();

        UnitOfWork uow = null;

        try {
            uow = uowFactory.createReadOnly();
            uow.getDomainRepository().add(d);
            uow.save();
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
    }

    @Test
    public void delete() {
        Domain d = createDomain();

        saveDomain(d);

        Domain loaded = loadDomain(d.getId());
        Assert.assertNotNull(loaded);

        deleteDomain(loaded);

        loaded = loadDomain(d.getId());
        Assert.assertNull(loaded);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteUnsavedDomain_shouldThrowIllegalArgumentException() {
        Domain d = createDomain();
        deleteDomain(d);
    }

    @Test(expected = IllegalStateException.class)
    public void deleteFromReadOnlyRepository_shouldThrowIllegalStateException() {
        Domain d = createDomain();
        saveDomain(d);

        UnitOfWork uow = null;

        try {
            uow = uowFactory.createReadOnly();
            uow.getDomainRepository().delete(d);
            uow.save();
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteNull_shouldThrowIllegalArgumentException() {
        deleteDomain(null);
    }

    @Test
    public void load() {
        Domain d = createDomain();
        saveDomain(d);

        Domain loaded = loadDomain(d.getId());

        Assert.assertEquals(d.getId(), loaded.getId());
        Assert.assertEquals(d.getDataId(), loaded.getDataId());
        Assert.assertEquals(d.getName(), loaded.getName());
        Assert.assertEquals(d.getType(), loaded.getType());
    }

    @Test
    public void loadUnknownDomain_shouldReturnNull() {
        Domain d = loadDomain(UUID.randomUUID().toString());
        Assert.assertNull(d);
    }

    @Test
    public void all() {
        List<Domain> domains = Arrays.asList(createDomain(), createDomain(), createDomain(), createDomain(), createDomain());

        for (Domain d : domains) {
            saveDomain(d);
        }

        UnitOfWork uow = null;

        try {
            uow = uowFactory.create();
            Assert.assertEquals(5, uow.getDomainRepository().all().size());

            for (Domain d : domains) {
                Assert.assertTrue(uow.getDomainRepository().all().contains(d));
            }
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
    }

    @Test
    public void allWithNoEntries_shouldReturnZero() {
        UnitOfWork uow = null;

        try {
            uow = uowFactory.create();
            Assert.assertEquals(0, uow.getDomainRepository().all().size());
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
    }

    // ----- HELPER METHODS -----
    private void deleteDomain(Domain d) {
        UnitOfWork uow = null;

        try {
            uow = uowFactory.create();

            uow.getDomainRepository().delete(d);
            uow.save();
        } finally {
            if (uow != null) {
                uow.close();
            }
        }

    }

    private Domain loadDomain(String id) {
        UnitOfWork uow = null;

        try {
            uow = uowFactory.createReadOnly();
            return uow.getDomainRepository().get(id);
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
    }

    private void saveDomain(Domain d) {
        UnitOfWork uow = null;

        try {
            uow = uowFactory.create();
            uow.getDomainRepository().add(d);
            uow.save();
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
    }

    private static Domain createDomain() {
        Domain d = new Domain("Test Domain", DomainType.DATE_TIME);
        d.setDataId(UUID.randomUUID().toString());

        return d;
    }
}
