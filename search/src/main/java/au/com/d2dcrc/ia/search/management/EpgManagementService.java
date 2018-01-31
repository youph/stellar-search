package au.com.d2dcrc.ia.search.management;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

/**
 * This is a Stub EPG Management Service, it has no business logic currently other than
 * populate the metadata repo.
 */
@Service
public class EpgManagementService {

    private final RestHighLevelClient client;
    private final EpgMetaRepository metaRepo;
    private final EntityManager entityManager;

    /**
     * Constructs an EPG management service.
     *
     * @param client the elasticsearch rest client
     * @param metaRepo the metadata repo of EPGs
     * @param entityManager the JPA entity manager
     */
    @Inject
    public EpgManagementService(
        final RestHighLevelClient client,
        final EpgMetaRepository metaRepo,
        final EntityManager entityManager
    ) {
        this.client = client;
        this.metaRepo = metaRepo;
        this.entityManager = entityManager;
    }

    /**
     * Returns a list of EPG meta data views.
     *
     * @return a list of EPG meata data views
     */
    public List<EpgMetaView> findAllEpgs() {
        return metaRepo.findAll()
            .stream()
            .map(EpgMetaEntity::toView)
            .collect(Collectors.toList());
    }

    /**
     * Creates an EPG Index with the given name and EPG resource reference.
     *
     * @param name the name of the EPG to create
     * @param epgReference the reference to graphs, nodes and vertices to index
     * @return a view of meta data the newly created EPG index
     * @throws EntityExistsException if an EPG with name already exists
     */
    public EpgMetaView createEpg(
        final String name,
        final EpgReferenceModel epgReference
    ) throws EntityExistsException {

        if (metaRepo.exists(name)) {
            throw new EntityExistsException("An EPG with name '" + name + "' already exists");
        }

        // todo utilise optimistic concurrency to avoid race condition
        // between check and save as an alternative to database locking

        final EpgMetaEntity metaEntity = new EpgMetaEntity(
            name,
            Instant.now(),
            EpgStatus.INDEXING,
            epgReference.getGraphs(),
            epgReference.getVertices(),
            epgReference.getEdges()
        );

        return metaRepo.saveAndFlush(metaEntity)
            .toView();

    }

    /**
     * Finds an EPG index with the specified name or null if one does not exist.
     *
     * @param name the name fo the EPG index to find
     * @return a view of the metadata of the EPG or null if none exists
     */
    public EpgMetaView findEpg(final String name) {
        final EpgMetaEntity metaEntity = metaRepo.findOne(name);
        return  (metaEntity == null) ?  null : metaEntity.toView();
    }

    /**
     * Deletes the specified EPG index.
     * @param name the name of the EPG index
     * @throws EntityNotFoundException if no index with name could be found
     */
    public void deleteEpg(String name) throws EntityNotFoundException {

        if (!metaRepo.exists(name)) {
            throw new EntityNotFoundException("Cannot find EPG with name " + name);
        }

        metaRepo.delete(name);
    }
}
