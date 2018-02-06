package au.com.d2dcrc.ia.search.ingestor;

import au.com.d2dcrc.ia.search.graph.EpgElement;
import javax.annotation.Nullable;

/**
 * Provides a queryable holder for a set of EPG elements of common type. Each
 * EPG element must have a unique identifier.
 */
public interface QueryableEpgElementStore<T extends EpgElement> extends EpgElementStore<T>, Iterable<T> {

    /**
     * Indicates whether or not an element has been added to the set.
     * 
     * @param id - The element identifier.
     * @return A value of true (or false) if the element is (or is not) present in the set.
     */
    boolean hasElement(String id);

    /**
     * Obtains the EPG element corresponding to the given identifier.
     * 
     * @param id - The EPG element identifier.
     * @return The EPG element, or a value of null if no such element is present.
     */
    @Nullable T getElement(String id);

}
