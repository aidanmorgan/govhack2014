package org.govhack.correlate.persistence;

import org.govhack.correlate.model.*;

/**
 * @author Aidan Morgan
 */
public interface UnitOfWork {
    public Repository<DataSet> getDataSetRepository();
    public Repository<DataSetPair> getDataSetPairRepository();
    public Repository<Correlation> getCorrelationRepository();

    public Repository<Domain> getDomainRepository();
    public Repository<Range> getRangeRepository();

    public void save();
    public void close();
}
