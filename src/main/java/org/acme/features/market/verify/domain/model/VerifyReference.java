package org.acme.features.market.verify.domain.model;

import org.acme.features.market.verify.domain.model.valueobject.VerifyUidVO;

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
public class VerifyReference implements org.acme.features.market.verify.domain.model.VerifyRef {

  /**
   * @autogenerated EntityReferenceImplGenerator
   * @param uid
   * @return
   */
  public static VerifyReference of(final String uid) {
    return new VerifyReference(VerifyUidVO.from(uid));
  }

  /**
   * A number to identify the db record
   *
   * @autogenerated EntityReferenceImplGenerator
   */
  @EqualsAndHashCode.Include
  @NonNull
  private VerifyUidVO uid;

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
  public VerifyReference withUidValue(final String uid) {
    return withUid(VerifyUidVO.from(uid));
  }
}
