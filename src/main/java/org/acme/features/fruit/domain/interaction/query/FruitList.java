package org.acme.features.fruit.domain.interaction.query;

import org.acme.features.fruit.domain.interaction.FruitCursor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
public class FruitList extends FruitRetrieve {
  private FruitCursor cursor;
}
