package au.com.d2dcrc.ia.search.management;

import io.swagger.annotations.ApiModelProperty;
import java.net.URI;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public class EpgReferenceModel {

    @ApiModelProperty(value = "A URI to graphs", required = true, example = "file:/foo/graphs.json")
    @NotNull
    private final URI graphs;
    @ApiModelProperty(value = "A URI to vertices", required = true, example = "file:/foo/vertices.json")
    @NotNull
    private final URI vertices;
    @ApiModelProperty(value = "A URI to edges", required = true, example = "file:/foo/edges.json")
    @NotNull
    private final URI edges;
    @ApiModelProperty(value = "An optional schema describing the EPG")
    @Valid
    private final EpgSchema epgSchema;

    /**
     * An EPG Reference Model encapsulating URI references to resources describing an EPG.
     *
     * @param graphs the URI to the graphs of the EPG
     * @param vertices the URI to the vertices of the EPG
     * @param edges the URI to the edges of the EPG
     * @param epgSchema the graph schema of the EPG
     */
    public EpgReferenceModel(
        final URI graphs,
        final URI vertices,
        final URI edges,
        final EpgSchema epgSchema
    ) {
        this.graphs = graphs;
        this.vertices = vertices;
        this.edges = edges;
        this.epgSchema = epgSchema;
    }

    public URI getGraphs() {
        return graphs;
    }

    public URI getVertices() {
        return vertices;
    }

    public URI getEdges() {
        return edges;
    }

    public EpgSchema getEpgSchema() {
        return epgSchema;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EpgReferenceModel that = (EpgReferenceModel) o;

        if (graphs != null ? !graphs.equals(that.graphs) : that.graphs != null) {
            return false;
        }
        if (vertices != null ? !vertices.equals(that.vertices) : that.vertices != null) {
            return false;
        }
        return edges != null ? edges.equals(that.edges) : that.edges == null;
    }

    @Override
    public int hashCode() {
        int result = graphs != null ? graphs.hashCode() : 0;
        result = 31 * result + (vertices != null ? vertices.hashCode() : 0);
        result = 31 * result + (edges != null ? edges.hashCode() : 0);
        return result;
    }
}
