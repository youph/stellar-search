package au.com.d2dcrc.ia.search.management;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

@Validated
@ApiModel
public class GraphSchemaClass {

    @ApiModelProperty
    @NotNull
    private final String name;

    @ApiModelProperty
    @NotNull
    private final Map<String, String> properties;

    /**
     * Constructor.
     *
     * @param name the class name (node label)
     * @param properties the map of properties
     */
    public GraphSchemaClass(
        @JsonProperty("name") final String name,
        @JsonProperty("props") final Map<String, String> properties
    ) {
        this.name = name;
        this.properties = properties;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
