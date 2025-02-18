package org.acme.features.market.place.application.usecase.delete;

import java.util.List;

import org.acme.common.batch.stepper.ItemDescriptor;
import org.acme.common.batch.stepper.ItemProcessor;
import org.acme.common.batch.stepper.ItemReader;
import org.acme.common.batch.stepper.ItemWriter;
import org.acme.common.batch.stepper.StepContext;
import org.acme.common.batch.stepper.StepCounter;
import org.acme.common.batch.stepper.StepFinalizer;
import org.acme.common.batch.stepper.StepInitializer;
import org.acme.common.exception.ExecutionException;
import org.acme.common.security.Allow;
import org.acme.features.market.place.application.service.PlacesVisibilityService;
import org.acme.features.market.place.domain.gateway.PlaceCursor;
import org.acme.features.market.place.domain.model.Place;

import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.RequestScoped;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Unremovable
@RequestScoped
@RequiredArgsConstructor
class DeletePlacesInBatchExecutor implements
    StepCounter<PlaceDeleteAllInBatchCommand, DeletePlacesInBatchExecutor.PlacePaginableBatch>,
    StepFinalizer<PlaceDeleteAllInBatchCommand, DeletePlacesInBatchExecutor.PlacePaginableBatch>,
    StepInitializer<PlaceDeleteAllInBatchCommand, DeletePlacesInBatchExecutor.PlacePaginableBatch>,
    ItemReader<Place, PlaceDeleteAllInBatchCommand, DeletePlacesInBatchExecutor.PlacePaginableBatch>,
    ItemWriter<Place, PlaceDeleteAllInBatchCommand, DeletePlacesInBatchExecutor.PlacePaginableBatch>,
    ItemProcessor<Place, Place, PlaceDeleteAllInBatchCommand, DeletePlacesInBatchExecutor.PlacePaginableBatch>,
    ItemDescriptor<Place, PlaceDeleteAllInBatchCommand, DeletePlacesInBatchExecutor.PlacePaginableBatch> {

  /**
   * @autogenerated EntityGenerator
   */
  @Data
  public static class PlacePaginableBatch {

    /**
     * @autogenerated EntityGenerator
     */
    private String since;
  }

  /**
   * @autogenerated EntityGenerator
   */
  private final int size = 10;

  /**
   * @autogenerated EntityGenerator
   */
  private final DeletePlaceUsecase usecase;

  /**
   * @autogenerated EntityGenerator
   */
  private final PlacesVisibilityService visibility;

  /**
   * @autogenerated EntityGenerator
   * @param context
   * @return
   */
  @Override
  public long approximatedItems(
      final StepContext<PlaceDeleteAllInBatchCommand, PlacePaginableBatch> context) {
    return visibility.countVisibles(context.getParams(), context.getParams().getFilter());
  }

  /**
   * @autogenerated EntityGenerator
   * @param context
   */
  @Override
  public void finish(final StepContext<PlaceDeleteAllInBatchCommand, PlacePaginableBatch> context) {
    usecase.flush();
  }

  /**
   * @autogenerated EntityGenerator
   * @param context
   */
  @Override
  public void init(final StepContext<PlaceDeleteAllInBatchCommand, PlacePaginableBatch> context) {
    context.setState(new PlacePaginableBatch());
  }

  /**
   * @autogenerated EntityGenerator
   * @param item
   * @param context
   * @return
   */
  @Override
  public String itemDescription(final Place item,
      final StepContext<PlaceDeleteAllInBatchCommand, PlacePaginableBatch> context) {
    return item.getUidValue();
  }

  /**
   * @autogenerated EntityGenerator
   * @param item
   * @param context
   * @return
   */
  @Override
  public Place process(final Place item,
      final StepContext<PlaceDeleteAllInBatchCommand, PlacePaginableBatch> context) {
    Allow allowed = usecase.allow(context.getParams(), item);
    if (!allowed.isAllowed()) {
      throw new ExecutionException("not-allowed", null);
    }
    return item;
  }

  /**
   * @autogenerated EntityGenerator
   * @param context
   * @return
   */
  @Override
  public List<Place> read(
      final StepContext<PlaceDeleteAllInBatchCommand, PlacePaginableBatch> context) {
    List<Place> page = visibility.listVisibles(context.getParams(), context.getParams().getFilter(),
        PlaceCursor.builder().limit(size).sinceUid(context.getState().getSince()).build());
    context.getState().setSince(page.isEmpty() ? null : page.get(page.size() - 1).getUidValue());
    return page;
  }

  /**
   * @autogenerated EntityGenerator
   * @param items
   * @param context
   */
  @Override
  public void write(final List<Place> items,
      final StepContext<PlaceDeleteAllInBatchCommand, PlacePaginableBatch> context) {
    items.forEach(item -> usecase.delete(context.getParams(), item));
  }
}
