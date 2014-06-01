package org.govhack.correlate.model;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

/**
 * A {@see DataSetPair} contains a {@see Domain} and a {@see Range} instance that are loaded from a specific
 * {@see DataSet}.
 * <p/>
 * This class allows one {@see DataSet} to share the {@see Domain} and {@see Range} instances, providing the ability to
 * combine {@see Domain} and {@see Range}s in different permutations.
 * <p/>
 * It is expected that typical use of this class will see one {@see Domain} instance be associated with multiple
 * {@see Range} instances.
 *
 * @author Aidan Morgan
 */
@javax.persistence.Entity
public class DataSetPair extends AbstractTableEntity {
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private Domain domain;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    private Range range;

    public DataSetPair() {

    }

    public DataSetPair(String name, Domain domain, Range range) {
        setName(name);
        setDomain(domain);
        setRange(range);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }
}
