package org.acme.features.fruit.domain.interaction.query;

import org.acme.common.action.Interaction;
import org.acme.features.fruit.domain.interaction.FruitFilter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
public class FruitRetrieve extends Interaction {
  private FruitFilter filter;
}
