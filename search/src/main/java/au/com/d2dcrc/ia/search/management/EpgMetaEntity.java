package au.com.d2dcrc.ia.search.management;

import au.com.d2dcrc.ia.search.user.User;

import java.net.URI;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name = "epg_meta")
public class EpgMetaEntity {

    @Id
    private String name;

    @Column(nullable = false)
    private Instant created;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EpgStatus status;

    @OneToOne
    private User createdBy;

    @Column(nullable = false)
    private URI graphs;

    @Column(nullable = false)
    private URI vertices;

    @Column(nullable = false)
    private URI edges;

    /**
     * No-arg constructor needed for Hibernate for reading a task from persistence.
     */
    @SuppressWarnings("unused")
    private EpgMetaEntity() {
    }

    /**
     * Constructs a new task for persisting in a repository.
     * @param name name of EPG Index
     * @param created the instant the EPG index was created
     * @param status the status of the task
     * @param createdBy the user who created the task
     * @param graphs URI to graph resources of EPG
     * @param vertices URI to vertices resources of EPG
     * @param edges URI to edges resources of EPG
     */
    public EpgMetaEntity(
        final String name,
        final Instant created,
        final EpgStatus status,
        final User createdBy,
        final URI graphs,
        final URI vertices,
        final URI edges
    ) {
        this.name = name;
        this.created = created;
        this.status = status;
        this.createdBy = createdBy;
        this.graphs = graphs;
        this.vertices = vertices;
        this.edges = edges;
    }

    /**
     * Converts this JPA entity to a Task view.
     *
     * @return the view
     */
    public EpgMetaView toView() {
        return new EpgMetaView(
            this.name,
            this.created,
            this.createdBy.getUsername(),
            this.status.name(),
            new EpgMetaView.ResourceReference(
                this.graphs,
                this.vertices,
                this.edges
            )
        );
    }

}
