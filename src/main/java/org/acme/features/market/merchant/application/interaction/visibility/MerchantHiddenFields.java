package org.acme.features.market.merchant.application.interaction.visibility;

import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.merchant.domain.model.MerchantRef;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class MerchantHiddenFields extends Interaction {

  /**
   * The list of hidden fields
   *
   * @autogenerated HiddenFieldsGenerator
   */
  @NonNull
  private CompletionStage<Set<String>> hidden;

  /**
   * @autogenerated HiddenFieldsGenerator
   */
  private MerchantRef merchant;

  /**
   * @autogenerated HiddenFieldsGenerator
   * @return
   */
  public Optional<MerchantRef> getMerchant() {
    return Optional.ofNullable(merchant);
  }
}
