package au.com.d2dcrc.ia.search.management;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
@ApiModel
public class GraphSchema {

    @ApiModelProperty
    @NotNull
    @Valid
    private final List<GraphSchemaClass> classes;

    @ApiModelProperty
    @NotNull
    @Valid
    private final List<GraphSchemaClassLink> classLinks;

    /**
     * Constructor.
     *
     * @param classes list of classes
     * @param classLinks list of class links
     */
    public GraphSchema(
        final List<GraphSchemaClass> classes,
        final List<GraphSchemaClassLink> classLinks
    ) {
        this.classes = classes;
        this.classLinks = classLinks;
    }

    public List<GraphSchemaClass> getClasses() {
        return classes;
    }

    public List<GraphSchemaClassLink> getClassLinks() {
        return classLinks;
    }
}
