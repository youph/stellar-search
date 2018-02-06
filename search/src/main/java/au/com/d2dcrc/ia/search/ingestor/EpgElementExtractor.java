package au.com.d2dcrc.ia.search.ingestor;

import au.com.d2dcrc.ia.search.graph.EpgElement;

/**
 * Allows the repeated extraction of EPG elements of the specified type from 
 * textual JSON representations.
 *
 * @param <T> The type of EPG element to extract.
 */
public interface EpgElementExtractor<T extends EpgElement> {

    /**
     * Extracts an EPG element of the pre-specified type from the JSON representation.
     * 
     * @param json - The textual JSON representation of the EPG element.
     * @return The EPG element.
     * @throws EpgSyntaxException if the JSON representation does not obey the EPG element syntax.
     */
    T extract(String json) throws EpgSyntaxException;

}
