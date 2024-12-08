package org.acme.features.market.fruit.domain.interaction.query;

import org.acme.common.action.Interaction;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@RegisterForReflection
public class FruitRetrieveQuery extends Interaction {

    /**
     * The filter to select the results to retrieve
     *
     * @autogenerated EntityGenerator
     */
    @NonNull
    private final String uid;
}
