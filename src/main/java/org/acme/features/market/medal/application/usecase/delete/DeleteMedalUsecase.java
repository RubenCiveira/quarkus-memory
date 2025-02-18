package org.acme.features.market.medal.application.usecase.delete;

import java.util.Optional;

import org.acme.common.action.Interaction;
import org.acme.common.batch.BatchIdentificator;
import org.acme.common.batch.BatchProgress;
import org.acme.common.batch.BatchService;
import org.acme.common.batch.ExecutorByDeferSteps;
import org.acme.common.batch.ExecutorPlan;
import org.acme.common.exception.NotAllowedException;
import org.acme.common.exception.NotFoundException;
import org.acme.common.security.Allow;
import org.acme.features.market.medal.application.MedalDto;
import org.acme.features.market.medal.application.service.MedalsVisibilityService;
import org.acme.features.market.medal.domain.Medals;
import org.acme.features.market.medal.domain.gateway.MedalCacheGateway;
import org.acme.features.market.medal.domain.gateway.MedalWriteRepositoryGateway;
import org.acme.features.market.medal.domain.model.Medal;
import org.acme.features.market.medal.domain.model.MedalRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class DeleteMedalUsecase {

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Medals aggregate;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final BatchService batch;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final MedalCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Event<MedalDeleteAllowProposal> deleteAllow;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Event<MedalDeleteEvent> eventEmitter;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final MedalWriteRepositoryGateway gateway;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Event<MedalDeleteProposal> proposalEmitter;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final MedalsVisibilityService visibility;

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public Allow allow(final Interaction query, final MedalRef reference) {
    return MedalDeleteAllowProposal.resolveWith(deleteAllow, query, Optional.of(reference), true,
        "Allowed by default");
  }

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param query
   * @return
   */
  public Allow allow(final Interaction query) {
    return MedalDeleteAllowProposal.resolveWith(deleteAllow, query, Optional.empty(), true,
        "Allowed by default");
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @return The slide with some values
   */
  public BatchProgress checkProgress(final MedalCheckBatchDeleteStatusQuery query) {
    return query.getActor().getName()
        .flatMap(name -> batch.retrieve(query.getTaskId(), query.getConnection().getLocale(), name))
        .orElseThrow(() -> new NotFoundException());
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public MedalDto delete(final MedalDeleteCommand command) {
    Allow detail = allow(command, command.getReference());
    if (!detail.isAllowed()) {
      throw new NotAllowedException(detail.getDescription());
    }
    Medal original = visibility.retrieveVisible(command, command.getReference().getUidValue())
        .orElseThrow(() -> new NotFoundException(""));
    Medal deleted = delete(command, original);
    flush();
    return visibility.copyWithHidden(command, deleted);
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param command a filter to retrieve only matching values
   * @return The slide with some values
   */
  public BatchIdentificator delete(final MedalDeleteAllInBatchCommand command) {
    Allow detail = allow(command);
    if (!detail.isAllowed()) {
      throw new NotAllowedException(detail.getDescription());
    }
    return batch.start(command.getActor().getName().orElse("-"), ExecutorPlan
        .<MedalDeleteAllInBatchCommand>builder().params(command).name("delete-color")
        .executor(
            ExecutorByDeferSteps.<Medal, Medal, MedalDeleteAllInBatchCommand, DeleteMedalsInBatchExecutor.MedalPaginableBatch>builder()
                .initializer(DeleteMedalsInBatchExecutor.class)
                .counter(DeleteMedalsInBatchExecutor.class)
                .descriptor(DeleteMedalsInBatchExecutor.class)
                .reader(DeleteMedalsInBatchExecutor.class)
                .processor(DeleteMedalsInBatchExecutor.class)
                .writer(DeleteMedalsInBatchExecutor.class)
                .finalizer(DeleteMedalsInBatchExecutor.class).build())
        .build());
  }

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param interaction
   * @param original
   * @return
   */
  Medal delete(final Interaction interaction, final Medal original) {
    Medal clean = aggregate.clean(original);
    MedalRef modified = MedalDeleteProposal.resolveWith(proposalEmitter, interaction, clean);
    Medal result = gateway.delete(gateway.enrich(modified));
    MedalDeleteEvent.notifyWith(eventEmitter, interaction, result);
    return result;
  }

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  void flush() {
    cache.evict();
  }
}
