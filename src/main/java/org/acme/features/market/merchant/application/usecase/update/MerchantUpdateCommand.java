package org.acme.features.market.merchant.application.usecase.update;

import org.acme.common.action.Interaction;
import org.acme.features.market.merchant.application.MerchantDto;
import org.acme.features.market.merchant.domain.model.MerchantRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class MerchantUpdateCommand extends Interaction {

  /**
   * The filter to select the results to retrieve
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final MerchantDto dto;

  /**
   * The filter to select the results to retrieve
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final MerchantRef reference;
}
