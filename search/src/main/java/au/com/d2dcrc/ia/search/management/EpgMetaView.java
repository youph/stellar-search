package au.com.d2dcrc.ia.search.management;

import org.springframework.validation.annotation.Validated;

import java.net.URI;
import java.time.Instant;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import io.swagger.annotations.ApiModelProperty;

@Validated
public class EpgMetaView {

    @ApiModelProperty(value = "Name of EPG index", required = true, example = "imdb")
    @NotEmpty
    private final String name;

    @ApiModelProperty(value = "When the index was created", required = true, example = "2018-01-17T23:56:07.812Z")
    @PastOrPresent
    @NotNull
    private final Instant created;

    @ApiModelProperty(value = "Who created the index", required = true, example = "test-user")
    @NotEmpty
    private final String createdBy;

    @ApiModelProperty(value = "The status of the index", required = true, example = "INDEXING")
    @NotEmpty
    private final String status;

    @ApiModelProperty(
        value = "The reference resources used to create the index at the time of request", required = true
    )
    @NotNull
    private final ResourceReference createdFrom;

    /**
     * Constructs a view of an EPG meta data.
     *
     * @param name the name of the EPG
     * @param created the time the EPG was created
     * @param createdBy the principal who created the EPG
     * @param status the status of the EPG
     * @param createdFrom a triple of graphs, vertexes and edges resources used to create the EPG
     */
    public EpgMetaView(
        final String name,
        final Instant created,
        final String createdBy,
        final String status,
        final ResourceReference createdFrom
    ) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(created);
        Objects.requireNonNull(createdBy);
        Objects.requireNonNull(status);
        Objects.requireNonNull(createdFrom);
        this.name = name;
        this.created = created;
        this.createdBy = createdBy;
        this.status = status;
        this.createdFrom = createdFrom;
    }

    public String getName() {
        return name;
    }

    public Instant getCreated() {
        return created;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getStatus() {
        return status;
    }

    public ResourceReference getCreatedFrom() {
        return createdFrom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EpgMetaView that = (EpgMetaView) o;

        if (!name.equals(that.name)) {
            return false;
        }
        if (!created.equals(that.created)) {
            return false;
        }
        if (!createdBy.equals(that.createdBy)) {
            return false;
        }
        if (!status.equals(that.status)) {
            return false;
        }
        return createdFrom.equals(that.createdFrom);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + created.hashCode();
        result = 31 * result + createdBy.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + createdFrom.hashCode();
        return result;
    }

    public static class ResourceReference {

        @ApiModelProperty(
            value = "The URI to graphs used in creation",
            required = true,
            example = "file:/foo/graphs.json"
        )
        @NotNull
        private final URI graphs;

        @ApiModelProperty(
            value = "The URI to vertices used in creation",
            required = true,
            example = "file:/foo/vertices.json"
        )
        @NotNull
        private final URI vertices;

        @ApiModelProperty(
            value = "The URI to edges used in creation",
            required = true,
            example = "file:/foo/edges.json"
        )
        @NotNull
        private final URI edges;

        /**
         * Constructs a triple of URI to graphs vertices and edges resources used to construct
         * the index. These were the URI at the time of construction and may no longer be valid.
         *
         * @param graphs the URI reference to the graphs of the EPG
         * @param vertices the URI reference to the vertices of the EPG
         * @param edges the URI reference to the edges of the EPG
         */
        public ResourceReference(
            final URI graphs,
            final URI vertices,
            final URI edges
        ) {
            this.graphs = graphs;
            this.vertices = vertices;
            this.edges = edges;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            ResourceReference that = (ResourceReference) o;

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
}
