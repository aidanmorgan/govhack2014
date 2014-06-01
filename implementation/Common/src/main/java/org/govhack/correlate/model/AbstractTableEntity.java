package org.govhack.correlate.model;


import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.proxy.HibernateProxy;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author Aidan Morgan
 */
@MappedSuperclass()
public class AbstractTableEntity implements Entity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    public AbstractTableEntity() {

    }

    public final String getId() {
        return id;
    }

    public final void setId(String uuid) {
        this.id = uuid;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != getClass(o)) return false;

        AbstractTableEntity that = (AbstractTableEntity) o;

        EqualsBuilder builder = new EqualsBuilder();
        builder.append(getId(that), getId(this));

        return builder.isEquals();
    }

    @Override
    public final int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(getId(this));

        return builder.toHashCode();
    }

    /**
     * Added to provide support for JPA lazy-loading semantics.
     * <p/>
     * There can be a proxy class which will not return the correct Class
     * for a basic == comparison. This method will check to see if there is a JPA proxy, and if so determine the correct
     * underlying Class type.
     *
     * @param entity the Object to get the Class type for.
     * @return the Class of the Object, resolving through the JPA proxy if necessary.
     */
    private static Class getClass(Object entity) {
        if (isProxy(entity)) {
            return ((HibernateProxy) entity).getHibernateLazyInitializer().getPersistentClass();
        }
        return entity.getClass();
    }

    /**
     * Added to provide support for JPA lazy-loading semantics.
     * <p/>
     * There can be a proxy in plance in the case of a lazy-loading relationship which will not load the id from the database
     * for a basic == comparison. This method will check to see if there is a JPA proxy, and if so force-load the identifier from the database..
     *
     * @param entity the Object to get the primary key for.
     * @return the primary key of the Object, resolving through the JPA proxy if necessary.
     */
    private static String getId(Object entity) {
        if (isProxy(entity)) {
            return (String) ((HibernateProxy) entity).getHibernateLazyInitializer().getIdentifier();
        }

        return ((AbstractTableEntity) entity).getId();
    }

    /**
     * Returns {@code true} if the provided Object is a JPA proxy, {@code false} otherwise.
     *
     * @param entity the Object to check.
     * @return {@code true} if the provided Object is a JPA proxy, {@code false} otherwise.
     */
    private static boolean isProxy(Object entity) {
        return entity instanceof HibernateProxy;
    }
}
