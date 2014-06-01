package org.govhack.correlate.model;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

/**
 * A {@see Correlation} stores the correlation coefficient that has resulted from comparing the two
 * {@see DataSetPair} instances.
 *
 * @author Aidan Morgan
 */
@javax.persistence.Entity
public class Correlation extends AbstractTableEntity {

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private DataSetPair pairOne;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private DataSetPair pairTwo;

    private double correlationScore;

    private double funnyScore;
    private double relevanceScore;

    public Correlation() {
    }

    public Correlation(DataSetPair one, DataSetPair two) {
        setPairOne(one);
        setPairTwo(two);
    }

    public DataSetPair getPairOne() {
        return pairOne;
    }

    public void setPairOne(DataSetPair pairOne) {
        this.pairOne = pairOne;
    }

    public DataSetPair getPairTwo() {
        return pairTwo;
    }

    public void setPairTwo(DataSetPair pairTwo) {
        this.pairTwo = pairTwo;
    }

    public double getFunnyScore() {
        return funnyScore;
    }

    public void setFunnyScore(double funnyScore) {
        this.funnyScore = funnyScore;
    }

    public double getRelevanceScore() {
        return relevanceScore;
    }

    public void setRelevanceScore(double relevanceScore) {
        this.relevanceScore = relevanceScore;
    }

    public double getCorrelationScore() {
        return correlationScore;
    }

    public void setCorrelationScore(double correlationScore) {
        this.correlationScore = correlationScore;
    }
}
