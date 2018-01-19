package au.com.d2dcrc.ia.search.graph;

import java.util.Collection;

/**
 * Defines a vertex (or node) in zero, one or more graphs. Example JSON
 * representation:
 * 
 * <pre>
 * {
      "id": "342D5869102440778B69FDF03756C853",
      "label": "Person",
      "properties": {
          "name" : "Slow Fred",
          "IQ" : 42
      },
      "graphs": [ "342D5869102440778B69FDF03756C858" ]
 }
 * </pre>
 */
public interface EpgVertex extends EpgElement {

    /**
     * Obtains the unique identifier of the vertex.
     * 
     * @return The vertex identifier.
     */
    @Override
    String getId();

    /**
     * Obtains the label of the vertex.
     * 
     * @return The vertex label.
     */
    @Override
    String getLabel();

    /**
     * Obtains the properties of the vertex. These should be treated as
     * immutable.
     * 
     * @return The vertex properties.
     */
    @Override
    EpgProperties getProperties();

    /**
     * Obtains the identifiers of the graphs of which the vertex is a member.
     * These should be treated as immutable.
     * 
     * @return A (possibly empty) collection of graph identifiers.
     */
    Collection<String> getGraphIds();

}
