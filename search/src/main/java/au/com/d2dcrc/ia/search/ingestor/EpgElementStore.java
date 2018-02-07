package au.com.d2dcrc.ia.search.ingestor;

import au.com.d2dcrc.ia.search.graph.EpgElement;
import au.com.d2dcrc.ia.search.ingestor.error.EpgElementExistsException;

/**
 * Allows EPG elements of the specified type to be stored.
 *
 * @param <T> The type of EPG element to store.
 */
public interface EpgElementStore<T extends EpgElement> {

    /**
     * Adds an EPG element.
     * 
     * @param elem - The element to be added.
     * @throws EpgElementExistsException if an element with the same id already exists.
     */
    void addElement(T elem) throws EpgElementExistsException;

}
