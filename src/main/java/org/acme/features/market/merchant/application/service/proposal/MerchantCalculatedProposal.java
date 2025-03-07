package org.acme.features.market.merchant.application.service.proposal;

import java.util.Optional;
import java.util.function.Consumer;

import org.acme.common.action.Interaction;
import org.acme.features.market.merchant.application.MerchantDto;
import org.acme.features.market.merchant.domain.model.Merchant;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MerchantCalculatedProposal {

  /**
   * @autogenerated CalculatorBuilderProposalGenerator
   */
  @NonNull
  private final Interaction interaction;

  /**
   * @autogenerated CalculatorBuilderProposalGenerator
   */
  @NonNull
  private MerchantDto dto;

  /**
   * @autogenerated CalculatorBuilderProposalGenerator
   */
  private Merchant original;

  /**
   * @autogenerated CalculatorBuilderProposalGenerator
   * @return
   */
  public Optional<Merchant> getOriginal() {
    return Optional.ofNullable(original);
  }

  /**
   * @autogenerated CalculatorBuilderProposalGenerator
   * @param mapper
   */
  public void peek(Consumer<MerchantDto> mapper) {
    mapper.accept(dto);
  }
}
