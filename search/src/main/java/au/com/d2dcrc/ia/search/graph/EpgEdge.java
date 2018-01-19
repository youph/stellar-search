package au.com.d2dcrc.ia.search.graph;

import java.util.Collection;

/**
 * Defines an edge (or arc or link) in zero, one or more graphs. Example JSON
 * representation:
 * 
 * <pre>
 * {
      "id": "342D5869102440778B69FDF03756C850",
      "label": "isRelatedTo",
      "properties": {
          "relationship" : "sibling"
      },
      "source": "342D5869102440778B69FDF03756C853",
      "target": "342D5869102440778B69FDF03756C851",
      "graphs": [ "342D5869102440778B69FDF03756C858" ]
 }
 * </pre>
 */
public interface EpgEdge extends EpgElement {

    /**
     * Obtains the unique identifier of the edge.
     * 
     * @return The edge identifier.
     */
    @Override
    String getId();

    /**
     * Obtains the label of the edge.
     * 
     * @return The edge label.
     */
    @Override
    String getLabel();

    /**
     * Obtains the properties of the edge. These should be treated as immutable.
     * 
     * @return The edge properties.
     */
    @Override
    EpgProperties getProperties();

    /**
     * Obtains the identifiers of the graphs of which the edge is a member.
     * These should be treated as immutable.
     * 
     * @return A (possibly empty) collection of graph identifiers.
     */
    Collection<String> getGraphIds();

    /**
     * Obtains the identifier of the vertex from which the edge originates.
     * 
     * @return The source vertex identifier.
     */
    String getSourceId();

    /**
     * Obtains the identifier of the vertex to which the edge is directed.
     * 
     * @return The target vertex identifier.
     */
    String getTargetId();

}
