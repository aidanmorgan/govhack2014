package org.govhack.correlate.model;

import java.util.UUID;

/**
 * @author Aidan Morgan
 */
public class Correlation {
    private UUID correlationId;

    private UUID pairOneId;
    private UUID pairTwoId;

    private double funnyScore;
    private double relevanceScore;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Correlation that = (Correlation) o;

        if (!correlationId.equals(that.correlationId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return correlationId.hashCode();
    }
}
