package org.acme.features.market.area.application.usecase.delete;

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
import org.acme.features.market.area.application.AreaDto;
import org.acme.features.market.area.application.service.AreasVisibilityService;
import org.acme.features.market.area.domain.Areas;
import org.acme.features.market.area.domain.gateway.AreaCacheGateway;
import org.acme.features.market.area.domain.gateway.AreaWriteRepositoryGateway;
import org.acme.features.market.area.domain.model.Area;
import org.acme.features.market.area.domain.model.AreaRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class DeleteAreaUsecase {

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Areas aggregate;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final BatchService batch;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final AreaCacheGateway cache;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Event<AreaDeleteAllowProposal> deleteAllow;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Event<AreaDeleteEvent> eventEmitter;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final AreaWriteRepositoryGateway gateway;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated DeleteUsecaseGenerator
   */
  private final Event<AreaDeleteProposal> proposalEmitter;

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  private final AreasVisibilityService visibility;

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param query
   * @param reference
   * @return
   */
  public Allow allow(final Interaction query, final AreaRef reference) {
    return AreaDeleteAllowProposal.resolveWith(deleteAllow, query, Optional.of(reference), true,
        "Allowed by default");
  }

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param query
   * @return
   */
  public Allow allow(final Interaction query) {
    return AreaDeleteAllowProposal.resolveWith(deleteAllow, query, Optional.empty(), true,
        "Allowed by default");
  }

  /**
   * The slide with some values
   *
   * @autogenerated DeleteUsecaseGenerator
   * @param query a filter to retrieve only matching values
   * @return The slide with some values
   */
  public BatchProgress checkProgress(final AreaCheckBatchDeleteStatusQuery query) {
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
  public AreaDto delete(final AreaDeleteCommand command) {
    Allow detail = allow(command, command.getReference());
    if (!detail.isAllowed()) {
      throw new NotAllowedException(detail.getDescription());
    }
    Area original = visibility.retrieveVisible(command, command.getReference().getUidValue())
        .orElseThrow(() -> new NotFoundException(""));
    Area deleted = delete(command, original);
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
  public BatchIdentificator delete(final AreaDeleteAllInBatchCommand command) {
    Allow detail = allow(command);
    if (!detail.isAllowed()) {
      throw new NotAllowedException(detail.getDescription());
    }
    return batch.start(command.getActor().getName().orElse("-"), ExecutorPlan
        .<AreaDeleteAllInBatchCommand>builder().params(command).name("delete-color")
        .executor(
            ExecutorByDeferSteps.<Area, Area, AreaDeleteAllInBatchCommand, DeleteAreasInBatchExecutor.AreaPaginableBatch>builder()
                .initializer(DeleteAreasInBatchExecutor.class)
                .counter(DeleteAreasInBatchExecutor.class)
                .descriptor(DeleteAreasInBatchExecutor.class)
                .reader(DeleteAreasInBatchExecutor.class)
                .processor(DeleteAreasInBatchExecutor.class)
                .writer(DeleteAreasInBatchExecutor.class)
                .finalizer(DeleteAreasInBatchExecutor.class).build())
        .build());
  }

  /**
   * @autogenerated DeleteUsecaseGenerator
   * @param interaction
   * @param original
   * @return
   */
  Area delete(final Interaction interaction, final Area original) {
    Area clean = aggregate.clean(original);
    AreaRef modified = AreaDeleteProposal.resolveWith(proposalEmitter, interaction, clean);
    Area result = gateway.delete(gateway.enrich(modified));
    AreaDeleteEvent.notifyWith(eventEmitter, interaction, result);
    return result;
  }

  /**
   * @autogenerated DeleteUsecaseGenerator
   */
  void flush() {
    cache.evict();
  }
}
