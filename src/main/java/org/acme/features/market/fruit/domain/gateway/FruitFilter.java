package org.acme.features.market.fruit.domain.gateway;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FruitFilter {

  /**
   * The filter to select the results to retrieve
   *
   * @autogenerated FilterGenerator
   */
  private String uid;

  /**
   * @autogenerated FilterGenerator
   * @return
   */
  public Optional<String> getUid() {
    return Optional.ofNullable(uid);
  }
}
