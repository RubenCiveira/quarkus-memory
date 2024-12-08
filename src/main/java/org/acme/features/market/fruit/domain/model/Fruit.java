package org.acme.features.market.fruit.domain.model;

import org.acme.features.market.fruit.domain.model.valueobject.FruitNameVO;
import org.acme.features.market.fruit.domain.model.valueobject.FruitVersionVO;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class Fruit extends FruitRef {

  public abstract static class FruitBuilder<C extends Fruit, B extends FruitBuilder<C, B>>
      extends FruitRefBuilder<C, B> {
  }

  /**
   * @autogenerated EntityGenerator
   */
  public static class FruitEntityBuilder extends FruitBuilder<Fruit, FruitEntityBuilder> {

    /**
     * @autogenerated EntityGenerator
     * @return
     */
    public Fruit build() {
      return new Fruit(this);
    }

    /**
     * @autogenerated EntityGenerator
     * @return
     */
    public FruitEntityBuilder self() {
      return this;
    }
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  public static FruitEntityBuilder builder() {
    return new FruitEntityBuilder();
  }

  /**
   * El name de fruit
   *
   * @autogenerated EntityGenerator
   */
  @NonNull
  private FruitNameVO name;

  /**
   * Campo con el número de version de fruit para controlar bloqueos optimistas
   *
   * @autogenerated EntityGenerator
   */
  @Builder.Default
  private FruitVersionVO version = FruitVersionVO.empty();
}
