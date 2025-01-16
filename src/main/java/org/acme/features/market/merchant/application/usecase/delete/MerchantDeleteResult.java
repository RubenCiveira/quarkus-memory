package org.acme.features.market.merchant.application.usecase.delete;

import java.util.Optional;

import org.acme.common.action.Interaction;
import org.acme.features.market.merchant.application.MerchantDto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MerchantDeleteResult {

  /**
   * The source interaction
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final Interaction command;

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final Optional<MerchantDto> merchant;
}
