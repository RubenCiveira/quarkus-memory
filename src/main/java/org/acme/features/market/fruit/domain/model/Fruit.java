package org.acme.features.market.fruit.domain.model;

import org.acme.features.market.fruit.domain.model.valueobject.FruitNameVO;
import org.acme.features.market.fruit.domain.model.valueobject.FruitUidVO;
import org.acme.features.market.fruit.domain.model.valueobject.FruitVersionVO;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@ToString
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Fruit extends FruitRef {
  /**
   * @autogenerated EntityGenerator
   */
  public static class FruitBuilder {
    /**
     * @autogenerated EntityGenerator
     * @param name The valueo to assign
     * @return
     */
    public FruitBuilder uidValue(final String uid) {
      return uid(FruitUidVO.from(uid));
    }
    /**
     * @autogenerated EntityGenerator
     * @param name The valueo to assign
     * @return
     */
    public FruitBuilder nameValue(final String name) {
      return name(FruitNameVO.from(name));
    }

    /**
     * @autogenerated EntityGenerator
     * @param version The valueo to assign
     * @return
     */
    public FruitBuilder versionValue(final Integer version) {
      return version(FruitVersionVO.from(version));
    }
  }
  
  @Builder
  public Fruit(FruitUidVO uid, FruitNameVO name, FruitVersionVO version) {
    super(uid);
    this.name = name;
    this.version = version;
  }

  /**
   * El name de fruit
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private final FruitNameVO name;

  /**
   * Campo con el número de version de fruit para controlar bloqueos optimistas
   *
   * @autogenerated EntityGenerator
   */
  @Builder.Default
  private FruitVersionVO version = FruitVersionVO.empty();

}
