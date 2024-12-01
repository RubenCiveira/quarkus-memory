package org.acme.features.market.fruit.domain.interaction;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FruitCursor {

  /**
   * max number of result
   *
   * @autogenerated CursorGenerator
   */
  private Integer limit;

  /**
   * If these value is set start retrieving for those whos uid is greater than these value
   *
   * @autogenerated CursorGenerator
   */
  private String sinceUid;
}
