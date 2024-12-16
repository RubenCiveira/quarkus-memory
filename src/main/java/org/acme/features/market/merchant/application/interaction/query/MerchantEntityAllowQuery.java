package org.acme.features.market.merchant.application.interaction.query;

import java.util.Optional;

import org.acme.common.action.Interaction;
import org.acme.features.market.merchant.domain.model.MerchantRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class MerchantEntityAllowQuery extends Interaction {

  /**
   * The filter to select the results to retrieve
   *
   * @autogenerated EntityGenerator
   */
  private final MerchantRef reference;

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public Optional<MerchantRef> getReference() {
    return Optional.ofNullable(reference);
  }
}
