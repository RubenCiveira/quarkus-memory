package org.acme.features.market.merchant.domain.model;

import org.acme.features.market.merchant.domain.model.valueobject.MerchantUidVO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.With;

@Getter
@ToString
@RequiredArgsConstructor
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MerchantReference
    implements org.acme.features.market.merchant.domain.model.MerchantRef {

  /**
   * @autogenerated EntityReferenceImplGenerator
   * @param uid
   * @return
   */
  public static MerchantReference of(final String uid) {
    return new MerchantReference(MerchantUidVO.from(uid));
  }

  /**
   * A number to identify the db record
   *
   * @autogenerated EntityReferenceImplGenerator
   */
  @EqualsAndHashCode.Include
  @NonNull
  private MerchantUidVO uid;

  /**
   * @autogenerated EntityReferenceImplGenerator
   * @return
   */
  public String getUidValue() {
    return getUid().getValue();
  }

  /**
   * @autogenerated EntityReferenceImplGenerator
   * @param uid
   * @return
   */
  public MerchantReference withUidValue(final String uid) {
    return withUid(MerchantUidVO.from(uid));
  }
}
