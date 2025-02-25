package org.acme.features.market.merchant.application.usecase.delete;

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
import org.acme.features.market.merchant.application.service.MerchantsVisibilityService;
import org.acme.features.market.merchant.domain.gateway.MerchantCursor;
import org.acme.features.market.merchant.domain.model.Merchant;

import io.quarkus.arc.Unremovable;
import jakarta.enterprise.context.RequestScoped;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Unremovable
@RequestScoped
@RequiredArgsConstructor
class DeleteMerchantsInBatchExecutor implements
    StepCounter<MerchantDeleteAllInBatchCommand, DeleteMerchantsInBatchExecutor.MerchantPaginableBatch>,
    StepFinalizer<MerchantDeleteAllInBatchCommand, DeleteMerchantsInBatchExecutor.MerchantPaginableBatch>,
    StepInitializer<MerchantDeleteAllInBatchCommand, DeleteMerchantsInBatchExecutor.MerchantPaginableBatch>,
    ItemReader<Merchant, MerchantDeleteAllInBatchCommand, DeleteMerchantsInBatchExecutor.MerchantPaginableBatch>,
    ItemWriter<Merchant, MerchantDeleteAllInBatchCommand, DeleteMerchantsInBatchExecutor.MerchantPaginableBatch>,
    ItemProcessor<Merchant, Merchant, MerchantDeleteAllInBatchCommand, DeleteMerchantsInBatchExecutor.MerchantPaginableBatch>,
    ItemDescriptor<Merchant, MerchantDeleteAllInBatchCommand, DeleteMerchantsInBatchExecutor.MerchantPaginableBatch> {

  /**
   * @autogenerated EntityGenerator
   */
  @Data
  public static class MerchantPaginableBatch {

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
  private final DeleteMerchantUsecase usecase;

  /**
   * @autogenerated EntityGenerator
   */
  private final MerchantsVisibilityService visibility;

  /**
   * @autogenerated EntityGenerator
   * @param context
   * @return
   */
  @Override
  public long approximatedItems(
      final StepContext<MerchantDeleteAllInBatchCommand, MerchantPaginableBatch> context) {
    return visibility.countVisibles(context.getParams(), context.getParams().getFilter());
  }

  /**
   * @autogenerated EntityGenerator
   * @param context
   */
  @Override
  public void finish(
      final StepContext<MerchantDeleteAllInBatchCommand, MerchantPaginableBatch> context) {
    usecase.flush();
  }

  /**
   * @autogenerated EntityGenerator
   * @param context
   */
  @Override
  public void init(
      final StepContext<MerchantDeleteAllInBatchCommand, MerchantPaginableBatch> context) {
    context.setState(new MerchantPaginableBatch());
  }

  /**
   * @autogenerated EntityGenerator
   * @param item
   * @param context
   * @return
   */
  @Override
  public String itemDescription(final Merchant item,
      final StepContext<MerchantDeleteAllInBatchCommand, MerchantPaginableBatch> context) {
    return item.getUidValue();
  }

  /**
   * @autogenerated EntityGenerator
   * @param item
   * @param context
   * @return
   */
  @Override
  public Merchant process(final Merchant item,
      final StepContext<MerchantDeleteAllInBatchCommand, MerchantPaginableBatch> context) {
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
  public List<Merchant> read(
      final StepContext<MerchantDeleteAllInBatchCommand, MerchantPaginableBatch> context) {
    List<Merchant> page =
        visibility.listVisibles(context.getParams(), context.getParams().getFilter(),
            MerchantCursor.builder().limit(size).sinceUid(context.getState().getSince()).build());
    context.getState().setSince(page.isEmpty() ? null : page.get(page.size() - 1).getUidValue());
    return page;
  }

  /**
   * @autogenerated EntityGenerator
   * @param items
   * @param context
   */
  @Override
  public void write(final List<Merchant> items,
      final StepContext<MerchantDeleteAllInBatchCommand, MerchantPaginableBatch> context) {
    items.forEach(item -> usecase.delete(context.getParams(), item));
  }
}
