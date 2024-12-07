package org.acme.features.market.fruit.domain.model;

import org.acme.features.market.fruit.domain.model.valueobject.FruitUidVO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@ToString
public class FruitRef {

  /**
   * @autogenerated EntityReferenceGenerator
   */
  public abstract static class FruitRefBuilder<C extends FruitRef, B extends FruitRefBuilder<C, B>> {

    /**
     * @autogenerated EntityReferenceGenerator
     * @param uid The valueo to assign
     * @return
     */
    public B uidValue(final String uid) {
      return uid(FruitUidVO.from(uid));
    }
  }

  /**
   * A number to identify the db record
   *
   * @autogenerated EntityReferenceGenerator
   */
  @EqualsAndHashCode.Include
  @NonNull
  private FruitUidVO uid;

  /**
   * @autogenerated EntityReferenceGenerator
   * @param uid
   */
  public void setUid(final String uid) {
    this.uid = FruitUidVO.from(uid);
  }
}
