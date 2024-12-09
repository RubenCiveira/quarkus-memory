package org.acme.features.market.fruit.domain.model;

import org.acme.features.market.fruit.domain.model.valueobject.FruitUidVO;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.With;

@Getter
@ToString
@With
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FruitReference implements FruitRef {

  /**
   * A number to identify the db record
   *
   * @autogenerated EntityReferenceGenerator
   */
  @EqualsAndHashCode.Include
  @NonNull
  private FruitUidVO uid;
}
