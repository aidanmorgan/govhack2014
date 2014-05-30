package org.govhack.correlate.model;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.govhack.correlate.persistence.RepositoryContext;
import org.govhack.correlate.persistence.UnitOfWork;
import org.govhack.correlate.persistence.UnitOfWorkFactory;
import org.govhack.correlate.persistence.fakes.IntegrationRepositoryContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        repositoryContext = IntegrationRepositoryContext.create();
        uowFactory = repositoryContext.createUnitOfWorkFactory();
    }

    @After
    public void afterTest() {
        ((IntegrationRepositoryContext) repositoryContext).clean();
        uowFactory = null;
    }

    @Test
    public void save() {
        Domain d = new Domain("Test Domain", DomainType.DATE_TIME);
        d.setDataId(UUID.randomUUID());

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
}
