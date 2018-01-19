package au.com.d2dcrc.ia.search.graph;

/**
 * Defines a single graph of implicit vertices and edges. This lightweight
 * representation is known as the <em>graph head</em>. <br>
 * Example JSON representation:
 * 
 * <pre>
 * {
      "id": "342D5869102440778B69FDF03756C858",
      "label": "iamagraph",
      "properties": {
          "key1" : "value1",
          "key2" : 42,
          "key3" : true
      }
 }
 * </pre>
 */
public interface EpgGraph extends EpgElement {

    /**
     * Obtains the unique identifier of the graph.
     * 
     * @return The graph identifier.
     */
    @Override
    String getId();

    /**
     * Obtains the label of the graph.
     * 
     * @return The graph label.
     */
    @Override
    String getLabel();

    /**
     * Obtains the properties of the graph. These should be treated as
     * immutable.
     * 
     * @return The graph properties.
     */
    @Override
    EpgProperties getProperties();

}
