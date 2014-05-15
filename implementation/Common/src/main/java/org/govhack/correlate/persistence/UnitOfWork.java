package org.govhack.correlate.persistence;

import org.govhack.correlate.model.DataSet;
import org.govhack.correlate.model.Domain;
import org.govhack.correlate.model.Range;

/**
 * @author Aidan Morgan
 */
public interface UnitOfWork {
    public Repository<DataSet> getDataSetRepository();
    public Repository<Domain> getDomainRepository();
    public Repository<Range> getRangeRepository();

    public void save();
    public void close();
}
