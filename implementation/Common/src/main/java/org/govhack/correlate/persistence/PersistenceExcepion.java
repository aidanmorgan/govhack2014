package org.govhack.correlate.persistence;

/**
 * @author Aidan Morgan
 */
public class PersistenceExcepion extends  RuntimeException {
    public PersistenceExcepion() {
        super();
    }

    public PersistenceExcepion(String message) {
        super(message);
    }

    public PersistenceExcepion(String message, Throwable cause) {
        super(message, cause);
    }

    public PersistenceExcepion(Throwable cause) {
        super(cause);
    }
}
