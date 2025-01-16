package org.acme.features.market.verify.domain.gateway;

import java.util.Optional;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VerifyCursor {

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

  /**
   * @autogenerated CursorGenerator
   * @return
   */
  public Optional<Integer> getLimit() {
    return Optional.ofNullable(limit);
  }

  /**
   * @autogenerated CursorGenerator
   * @return
   */
  public Optional<String> getSinceUid() {
    return Optional.ofNullable(sinceUid);
  }
}
