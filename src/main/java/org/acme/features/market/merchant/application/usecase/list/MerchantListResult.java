package org.acme.features.market.merchant.application.usecase.list;

import java.time.OffsetDateTime;
import java.util.List;

import org.acme.features.market.merchant.application.MerchantDto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MerchantListResult {

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final List<MerchantDto> merchants;

  /**
   * The result content list
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final MerchantListQuery query;

  /**
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final OffsetDateTime since;
}
