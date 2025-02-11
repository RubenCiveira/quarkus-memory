package org.acme.features.market.merchant.application.usecase.enable;

import java.util.Optional;

import org.acme.common.action.Interaction;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.exception.NotFoundException;
import org.acme.common.security.Allow;
import org.acme.features.market.merchant.application.MerchantDto;
import org.acme.features.market.merchant.application.service.MerchantsVisibilityService;
import org.acme.features.market.merchant.domain.Merchants;
import org.acme.features.market.merchant.domain.gateway.MerchantCacheGateway;
import org.acme.features.market.merchant.domain.gateway.MerchantWriteRepositoryGateway;
import org.acme.features.market.merchant.domain.model.Merchant;
import org.acme.features.market.merchant.domain.model.MerchantRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class EnableMerchantUsecase {

  /**
   * @autogenerated ChActionUsecaseGenerator
   */
  private final Merchants aggregate;

  /**
   * @autogenerated ChActionUsecaseGenerator
   */
  private final MerchantCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated ChActionUsecaseGenerator
   */
  private final Event<MerchantEnableAllowProposal> enableAllow;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated ChActionUsecaseGenerator
   */
  private final Event<MerchantEnableEvent> eventEmitter;

  /**
   * @autogenerated ChActionUsecaseGenerator
   */
  private final MerchantWriteRepositoryGateway gateway;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated ChActionUsecaseGenerator
   */
  private final Event<MerchantEnableProposal> proposalEmitter;

  /**
   * @autogenerated ChActionUsecaseGenerator
   */
  private final MerchantsVisibilityService visibility;

  /**
   * @autogenerated ChActionUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public Allow allow(final Interaction query, final MerchantRef reference) {
    MerchantEnableAllowProposal base = MerchantEnableAllowProposal.build(query,
        Optional.of(reference), true, "Allowed by default");
    enableAllow.fire(base);
    return base.getDetail();
  }

  /**
   * @autogenerated ChActionUsecaseGenerator
   * @param query
   * @return
   */
  public Allow allow(final Interaction query) {
    MerchantEnableAllowProposal base =
        MerchantEnableAllowProposal.build(query, Optional.empty(), true, "Allowed by default");
    enableAllow.fire(base);
    return base.getDetail();
  }

  /**
   * The slide with some values
   *
   * @autogenerated ChActionUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public MerchantDto enable(final MerchantEnableCommand command) {
    Allow detail = allow(command, command.getReference());
    if (!detail.isAllowed()) {
      throw new NotAllowedException(detail.getDescription());
    }
    Merchant original = visibility.retrieveVisible(command, command.getReference().getUidValue())
        .orElseThrow(() -> new NotFoundException(""));
    MerchantDto filled = MerchantDto.from(aggregate.enable(original));
    MerchantDto modified =
        MerchantEnableProposal.resolveWith(proposalEmitter, command, filled, original);
    Merchant saved =
        gateway.update(original, modified.toEntityBuilder(Optional.of(original)).buildValid());
    cache.update(saved);
    MerchantEnableEvent.notifyWith(eventEmitter, command, saved);
    return visibility.copyWithHidden(command, saved);
  }
}
