package org.govhack.correlate.model;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aidan Morgan
 */
@javax.persistence.Entity
public class DataSet extends AbstractTableEntity {
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<DataSetPair> pairs = new ArrayList<DataSetPair>();

    public DataSet() {
        super();
    }

    public DataSet(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DataSetPair> getPairs() {
        return pairs;
    }

    public void setPairs(List<DataSetPair> pairs) {
        this.pairs = pairs;
    }
}
