package au.com.d2dcrc.ia.search.management;

import io.swagger.annotations.ApiModelProperty;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
public class EpgSchema {

    @ApiModelProperty
    @NotNull
    private final List<URI> sources;

    @ApiModelProperty
    @NotNull
    @Valid
    private final GraphSchema graphSchema;

    // The graph schema also contains mapping information that we probably don't care about?

    /**
     * Constructor.
     *
     * @param sources the list of source files used to create this EPG
     * @param graphSchema graph schema information
     */
    public EpgSchema(
        final List<URI> sources,
        final GraphSchema graphSchema
    ) {
        this.sources = sources;
        this.graphSchema = graphSchema;
    }

    public List<URI> getSources() {
        return sources;
    }

    public GraphSchema getGraphSchema() {
        return graphSchema;
    }
}

