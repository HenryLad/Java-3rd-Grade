package htl._014contactmanager.model;

/**
 * Enumeration representing the different types of contacts
 */
public enum ContactType {
    BUSINESS,
    PRIVATE,
    NONE;
    
    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}