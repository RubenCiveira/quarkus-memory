package org.acme.features.market.color.application.usecase.delete;

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
import org.acme.features.market.color.application.service.ColorsVisibilityService;
import org.acme.features.market.color.domain.gateway.ColorCursor;
import org.acme.features.market.color.domain.model.Color;

import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.RequestScoped;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Unremovable
@RequestScoped
@RequiredArgsConstructor
class DeleteColorsInBatchExecutor implements
    StepCounter<ColorDeleteAllInBatchCommand, DeleteColorsInBatchExecutor.ColorPaginableBatch>,
    StepFinalizer<ColorDeleteAllInBatchCommand, DeleteColorsInBatchExecutor.ColorPaginableBatch>,
    StepInitializer<ColorDeleteAllInBatchCommand, DeleteColorsInBatchExecutor.ColorPaginableBatch>,
    ItemReader<Color, ColorDeleteAllInBatchCommand, DeleteColorsInBatchExecutor.ColorPaginableBatch>,
    ItemWriter<Color, ColorDeleteAllInBatchCommand, DeleteColorsInBatchExecutor.ColorPaginableBatch>,
    ItemProcessor<Color, Color, ColorDeleteAllInBatchCommand, DeleteColorsInBatchExecutor.ColorPaginableBatch>,
    ItemDescriptor<Color, ColorDeleteAllInBatchCommand, DeleteColorsInBatchExecutor.ColorPaginableBatch> {

  /**
   * @autogenerated EntityGenerator
   */
  @Data
  public static class ColorPaginableBatch {

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
  private final DeleteColorUsecase usecase;

  /**
   * @autogenerated EntityGenerator
   */
  private final ColorsVisibilityService visibility;

  /**
   * @autogenerated EntityGenerator
   * @param context
   * @return
   */
  @Override
  public long approximatedItems(
      final StepContext<ColorDeleteAllInBatchCommand, ColorPaginableBatch> context) {
    return visibility.countVisibles(context.getParams(), context.getParams().getFilter());
  }

  /**
   * @autogenerated EntityGenerator
   * @param context
   */
  @Override
  public void finish(final StepContext<ColorDeleteAllInBatchCommand, ColorPaginableBatch> context) {
    usecase.flush();
  }

  /**
   * @autogenerated EntityGenerator
   * @param context
   */
  @Override
  public void init(final StepContext<ColorDeleteAllInBatchCommand, ColorPaginableBatch> context) {
    context.setState(new ColorPaginableBatch());
  }

  /**
   * @autogenerated EntityGenerator
   * @param item
   * @param context
   * @return
   */
  @Override
  public String itemDescription(final Color item,
      final StepContext<ColorDeleteAllInBatchCommand, ColorPaginableBatch> context) {
    return item.getUidValue();
  }

  /**
   * @autogenerated EntityGenerator
   * @param item
   * @param context
   * @return
   */
  @Override
  public Color process(final Color item,
      final StepContext<ColorDeleteAllInBatchCommand, ColorPaginableBatch> context) {
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
  public List<Color> read(
      final StepContext<ColorDeleteAllInBatchCommand, ColorPaginableBatch> context) {
    List<Color> page = visibility.listVisibles(context.getParams(), context.getParams().getFilter(),
        ColorCursor.builder().limit(size).sinceUid(context.getState().getSince()).build());
    context.getState().setSince(page.isEmpty() ? null : page.get(page.size() - 1).getUidValue());
    return page;
  }

  /**
   * @autogenerated EntityGenerator
   * @param items
   * @param context
   */
  @Override
  public void write(final List<Color> items,
      final StepContext<ColorDeleteAllInBatchCommand, ColorPaginableBatch> context) {
    items.forEach(item -> usecase.delete(context.getParams(), item));
  }
}
