package org.acme.features.market.medal.application.usecase.delete;

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
import org.acme.features.market.medal.application.service.MedalsVisibilityService;
import org.acme.features.market.medal.domain.gateway.MedalCursor;
import org.acme.features.market.medal.domain.model.Medal;

import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.RequestScoped;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Unremovable
@RequestScoped
@RequiredArgsConstructor
class DeleteMedalsInBatchExecutor implements
    StepCounter<MedalDeleteAllInBatchCommand, DeleteMedalsInBatchExecutor.MedalPaginableBatch>,
    StepFinalizer<MedalDeleteAllInBatchCommand, DeleteMedalsInBatchExecutor.MedalPaginableBatch>,
    StepInitializer<MedalDeleteAllInBatchCommand, DeleteMedalsInBatchExecutor.MedalPaginableBatch>,
    ItemReader<Medal, MedalDeleteAllInBatchCommand, DeleteMedalsInBatchExecutor.MedalPaginableBatch>,
    ItemWriter<Medal, MedalDeleteAllInBatchCommand, DeleteMedalsInBatchExecutor.MedalPaginableBatch>,
    ItemProcessor<Medal, Medal, MedalDeleteAllInBatchCommand, DeleteMedalsInBatchExecutor.MedalPaginableBatch>,
    ItemDescriptor<Medal, MedalDeleteAllInBatchCommand, DeleteMedalsInBatchExecutor.MedalPaginableBatch> {

  /**
   * @autogenerated EntityGenerator
   */
  @Data
  public static class MedalPaginableBatch {

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
  private final DeleteMedalUsecase usecase;

  /**
   * @autogenerated EntityGenerator
   */
  private final MedalsVisibilityService visibility;

  /**
   * @autogenerated EntityGenerator
   * @param context
   * @return
   */
  @Override
  public long approximatedItems(
      final StepContext<MedalDeleteAllInBatchCommand, MedalPaginableBatch> context) {
    return visibility.countVisibles(context.getParams(), context.getParams().getFilter());
  }

  /**
   * @autogenerated EntityGenerator
   * @param context
   */
  @Override
  public void finish(final StepContext<MedalDeleteAllInBatchCommand, MedalPaginableBatch> context) {
    usecase.flush();
  }

  /**
   * @autogenerated EntityGenerator
   * @param context
   */
  @Override
  public void init(final StepContext<MedalDeleteAllInBatchCommand, MedalPaginableBatch> context) {
    context.setState(new MedalPaginableBatch());
  }

  /**
   * @autogenerated EntityGenerator
   * @param item
   * @param context
   * @return
   */
  @Override
  public String itemDescription(final Medal item,
      final StepContext<MedalDeleteAllInBatchCommand, MedalPaginableBatch> context) {
    return item.getUidValue();
  }

  /**
   * @autogenerated EntityGenerator
   * @param item
   * @param context
   * @return
   */
  @Override
  public Medal process(final Medal item,
      final StepContext<MedalDeleteAllInBatchCommand, MedalPaginableBatch> context) {
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
  public List<Medal> read(
      final StepContext<MedalDeleteAllInBatchCommand, MedalPaginableBatch> context) {
    List<Medal> page = visibility.listVisibles(context.getParams(), context.getParams().getFilter(),
        MedalCursor.builder().limit(size).sinceUid(context.getState().getSince()).build());
    context.getState().setSince(page.isEmpty() ? null : page.get(page.size() - 1).getUidValue());
    return page;
  }

  /**
   * @autogenerated EntityGenerator
   * @param items
   * @param context
   */
  @Override
  public void write(final List<Medal> items,
      final StepContext<MedalDeleteAllInBatchCommand, MedalPaginableBatch> context) {
    items.forEach(item -> usecase.delete(context.getParams(), item));
  }
}
