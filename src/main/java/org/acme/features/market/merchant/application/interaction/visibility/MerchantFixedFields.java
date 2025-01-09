package org.acme.features.market.merchant.application.interaction.visibility;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.merchant.domain.model.MerchantRef;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
public class MerchantFixedFields extends Interaction {

  /**
   * @autogenerated FixedFieldsGenerator
   */
  private final MerchantRef merchant;

  /**
   * The list of fixed fields
   *
   * @autogenerated FixedFieldsGenerator
   */
  @NonNull
  private CompletionStage<Set<String>> fixed;

  /**
   * @autogenerated FixedFieldsGenerator
   * @return
   */
  public Optional<MerchantRef> getMerchant() {
    return Optional.ofNullable(merchant);
  }
}
