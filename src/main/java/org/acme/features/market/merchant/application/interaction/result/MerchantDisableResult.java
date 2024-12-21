package org.acme.features.market.merchant.application.interaction.result;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.features.market.merchant.application.interaction.MerchantDto;
import org.acme.features.market.merchant.application.interaction.command.MerchantDisableCommand;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MerchantDisableResult {

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final MerchantDisableCommand command;

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final CompletionStage<Optional<MerchantDto>> merchant;
}