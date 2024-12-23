package org.acme.features.market.fruit.application.interaction;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.acme.features.market.fruit.domain.model.Fruit;
import org.acme.features.market.fruit.domain.model.valueobject.FruitNameVO;
import org.acme.features.market.fruit.domain.model.valueobject.FruitUidVO;
import org.acme.features.market.fruit.domain.model.valueobject.FruitVersionVO;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.With;

@Data
@With
@Builder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FruitDto {

  /**
   * The callbacks to copy
   *
   * @autogenerated EntityGenerator
   */
  private static final Map<String, BiConsumer<FruitDto, Fruit>> toFix = Map.of("uid",
      (target, source) -> target.uid = source == null ? null : source.getUid().getValue(), "name",
      (target, source) -> target.name = source == null ? null : source.getName().getValue(),
      "version", (target, source) -> target.version =
          source == null ? null : source.getVersion().getValue().orElse(null));

  /**
   * The callbacks to hide
   *
   * @autogenerated EntityGenerator
   */
  private static final Map<String, Consumer<FruitDto>> toHide = Map.of("uid", dto -> dto.uid = null,
      "name", dto -> dto.name = null, "version", dto -> dto.version = null);

  /**
   * A dto with the entity data
   *
   * @autogenerated EntityGenerator
   * @param fruit The entity with the values
   * @return A dto with the entity data
   */
  public static FruitDto from(final Fruit fruit) {
    return FruitDto.builder().uid(fruit.getUid().getValue()).name(fruit.getName().getValue())
        .version(fruit.getVersion().getValue().orElse(null)).build();
  }

  /**
   * El name de fruit
   *
   * @autogenerated EntityGenerator
   */
  private String name;

  /**
   * A number to identify the db record
   *
   * @autogenerated EntityGenerator
   */
  private String uid;

  /**
   * Campo con el número de version de fruit para controlar bloqueos optimistas
   *
   * @autogenerated EntityGenerator
   */
  private Integer version;

  /**
   * Hide a field value for the dto (setting as null)
   *
   * @autogenerated EntityGenerator
   * @param field The field to hide
   * @param fruit The field to hide
   */
  public void fix(final String field, final Fruit fruit) {
    if (toFix.containsKey(field)) {
      toFix.get(field).accept(this, fruit);
    }
  }

  /**
   * Hide a field value for the dto (setting as null)
   *
   * @autogenerated EntityGenerator
   * @param field The field to hide
   */
  public void fix(final String field) {
    fix(field, null);
  }

  /**
   * Hide a field value for the dto (setting as null)
   *
   * @autogenerated EntityGenerator
   * @param field The field to hide
   */
  public void hide(final String field) {
    if (toHide.containsKey(field)) {
      toHide.get(field).accept(this);
    }
  }

  /**
   * The entity param with the new values
   *
   * @autogenerated EntityGenerator
   * @return The entity param with the new values
   */
  public Fruit.FruitBuilder toEntityBuilder() {
    return Fruit.builder().uid(FruitUidVO.from(uid)).name(FruitNameVO.from(name))
        .version(FruitVersionVO.from(version));
  }
}
