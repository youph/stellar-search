package au.com.d2dcrc.ia.search.management;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
@ApiModel
public class GraphSchemaClassLink {

    @ApiModelProperty
    @NotNull
    private final String name;

    @ApiModelProperty
    @NotNull
    private final String source;

    @ApiModelProperty
    @NotNull
    private final String target;

    /**
     * Constructor.
     *
     * @param name the class link name (edge label)
     * @param source the source class name (node label)
     * @param target the target class name (node label)
     */
    public GraphSchemaClassLink(
        final String name,
        final String source,
        final String target
    ) {
        this.name = name;
        this.source = source;
        this.target = target;
    }

    public String getName() {
        return name;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }
}
