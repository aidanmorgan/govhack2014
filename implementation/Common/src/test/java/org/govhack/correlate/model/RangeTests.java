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
public class RangeTests {
    private static final Log LOG = LogFactory.getLog(RangeTests.class);

    private RepositoryContext repositoryContext;
    private UnitOfWorkFactory uowFactory;

    @Before
    public void beforeTest() {
        repositoryContext = IntegrationRepositoryContext.create("RangeTests");
        uowFactory = repositoryContext.createUnitOfWorkFactory();
    }

    @After
    public void afterTest() {
        ((IntegrationRepositoryContext) repositoryContext).clean();
        uowFactory = null;
    }

    @Test
    public void save() {
        Range d = createRange();
        saveRange(d);
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void saveWithNoIdSet_shouldThrowPersistenceException() {
        Range d = createRange();
        d.setId(null);

        saveRange(d);
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void saveWithNoDataIdSet_shouldThrowPersistenceException() {
        Range d = createRange();
        d.setDataId(null);

        saveRange(d);
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void saveWithNoNameSet_shouldThrowPersistenceException() {
        Range d = new Range(null, RangeType.NUMERICAL, InterpolationMode.createRepeatLast());
        saveRange(d);
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void saveWithNoRangeTypeSet_shouldThrowPersistenceException() {
        Range d = new Range("Test Range", null, InterpolationMode.createDefaultValue(0.1));
        saveRange(d);
    }

    @Test(expected = javax.persistence.PersistenceException.class)
    public void saveWithNoInterpolationModeSet_shouldThrowPersistenceException() {
        Range d = new Range("Test Range", RangeType.NUMERICAL, null);
        saveRange(d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveNullRange_shouldThrowIllegalArgumentException() {
        saveRange(null);
    }

    @Test(expected = IllegalStateException.class)
    public void saveRangeToReadOnlyRepository_shouldThrowIllegalStateException() {
        Range d = createRange();

        UnitOfWork uow = null;

        try {
            uow = uowFactory.createReadOnly();
            uow.getRangeRepository().add(d);
            uow.save();
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
    }

    @Test
    public void delete() {
        Range d = createRange();

        saveRange(d);

        Range loaded = loadRange(d.getId());
        Assert.assertNotNull(loaded);

        deleteRange(loaded);

        loaded = loadRange(d.getId());
        Assert.assertNull(loaded);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteUnsavedRange_shouldThrowIllegalArgumentException() {
        Range d = createRange();
        deleteRange(d);
    }

    @Test(expected = IllegalStateException.class)
    public void deleteFromReadOnlyRepository_shouldThrowIllegalStateException() {
        Range d = createRange();
        saveRange(d);

        UnitOfWork uow = null;

        try {
            uow = uowFactory.createReadOnly();
            uow.getRangeRepository().delete(d);
            uow.save();
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteNull_shouldThrowIllegalArgumentException() {
        deleteRange(null);
    }

    @Test
    public void load() {
        Range d = createRange();
        saveRange(d);

        Range loaded = loadRange(d.getId());

        Assert.assertEquals(d.getId(), loaded.getId());
        Assert.assertEquals(d.getDataId(), loaded.getDataId());
        Assert.assertEquals(d.getName(), loaded.getName());
        Assert.assertEquals(d.getInterpolationMode(), loaded.getInterpolationMode());
        Assert.assertEquals(d.getType(), loaded.getType());
    }

    @Test
    public void loadUnknownRange_shouldReturnNull() {
        Range d = loadRange(UUID.randomUUID().toString());
        Assert.assertNull(d);
    }

    @Test
    public void all() {
        List<Range> domains = Arrays.asList(createRange(), createRange(), createRange(), createRange(), createRange());

        for (Range d : domains) {
            saveRange(d);
        }

        UnitOfWork uow = null;

        try {
            uow = uowFactory.create();
            Assert.assertEquals(5, uow.getRangeRepository().all().size());

            for (Range d : domains) {
                Assert.assertTrue(uow.getRangeRepository().all().contains(d));
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
            Assert.assertEquals(0, uow.getRangeRepository().all().size());
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
    }

    // ----- HELPER METHODS -----
    private void deleteRange(Range d) {
        UnitOfWork uow = null;

        try {
            uow = uowFactory.create();

            uow.getRangeRepository().delete(d);
            uow.save();
        } finally {
            if (uow != null) {
                uow.close();
            }
        }

    }

    private Range loadRange(String id) {
        UnitOfWork uow = null;

        try {
            uow = uowFactory.createReadOnly();
            return uow.getRangeRepository().get(id);
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
    }

    private void saveRange(Range d) {
        UnitOfWork uow = null;

        try {
            uow = uowFactory.create();
            uow.getRangeRepository().add(d);
            uow.save();
        } finally {
            if (uow != null) {
                uow.close();
            }
        }
    }

    private static Range createRange() {
        Range d = new Range("Test Range", RangeType.NUMERICAL, InterpolationMode.createSpline());
        d.setDataId(UUID.randomUUID().toString());

        return d;
    }
}
