package org.govhack.correlate.model;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

/**
 * @author Aidan Morgan
 */
@javax.persistence.Entity
public class DataSetPair extends AbstractTableEntity {
    private String name;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Domain domain;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Range range;

    public DataSetPair() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
