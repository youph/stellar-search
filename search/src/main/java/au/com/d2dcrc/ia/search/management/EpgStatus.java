package au.com.d2dcrc.ia.search.management;

/**
 * Status of an EPG index.
 */
public enum EpgStatus {

    /**
     * A request to create an EPG is in progress.
     */
    INDEXING,

    /**
     * A EPG index is ready.
     */
    COMPLETE,

    /**
     * A request to delete an EPG index is in progress.
     */
    DELETING

}
