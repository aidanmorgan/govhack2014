package org.govhack.correlate.model;

import org.govhack.correlate.persistence.UnitOfWork;

import java.util.UUID;

/**
 * @author Aidan Morgan
 */
public class CorrelationJob {
    private String pairOneId;
    private String pairTwoId;

    public CorrelationJob(String pairOneId, String pairTwoId) {
        this.pairOneId = pairOneId;
        this.pairTwoId = pairTwoId;
    }

    public String getPairOneId() {
        return pairOneId;
    }

    public String getPairTwoId() {
        return pairTwoId;
    }

    public DataSetPair getPairOne(UnitOfWork uow) {
        return uow.getDataSetPairRepository().get(UUID.fromString(pairOneId));
    }

    public DataSetPair getPairTwo(UnitOfWork uow) {
        return uow.getDataSetPairRepository().get(UUID.fromString(pairTwoId));
    }

    public Correlation createCorrelation(UnitOfWork uow, double relevance) {
        Correlation c = new Correlation();
        c.setPairOne(getPairOne(uow));
        c.setPairTwo(getPairTwo(uow));

        c.setCorrelationScore(relevance);
        c.setRelevanceScore(0.0);
        c.setFunnyScore(0.0);

        uow.getCorrelationRepository().add(c);

        return c;
    }
}
