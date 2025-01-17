package org.acme.features.market.color.application;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.acme.features.market.color.domain.model.Color;
import org.acme.features.market.color.domain.model.Color.ColorBuilder;
import org.acme.features.market.color.domain.model.valueobject.ColorMerchantVO;
import org.acme.features.market.color.domain.model.valueobject.ColorNameVO;
import org.acme.features.market.color.domain.model.valueobject.ColorUidVO;
import org.acme.features.market.color.domain.model.valueobject.ColorVersionVO;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ColorDto {

  /**
   * The callbacks to copy
   *
   * @autogenerated EntityGenerator
   */
  private static final Map<String, BiConsumer<ColorDto, Color>> toFix = Map.of("uid",
      (target, source) -> target.uid = source == null ? null : source.getUid(), "name",
      (target, source) -> target.name = source == null ? null : source.getName(), "merchant",
      (target, source) -> target.merchant = source == null ? null : source.getMerchant(), "version",
      (target, source) -> target.version = source == null ? null : source.getVersion());

  /**
   * The callbacks to hide
   *
   * @autogenerated EntityGenerator
   */
  private static final Map<String, Consumer<ColorDto>> toHide =
      Map.of("uid", dto -> dto.uid = null, "name", dto -> dto.name = null, "merchant",
          dto -> dto.merchant = null, "version", dto -> dto.version = null);

  /**
   * A dto with the entity data
   *
   * @autogenerated EntityGenerator
   * @param color The entity with the values
   * @return A dto with the entity data
   */
  public static ColorDto from(final Color color) {
    return ColorDto.builder().uid(color.getUid()).name(color.getName())
        .merchant(color.getMerchant()).version(color.getVersion()).build();
  }

  /**
   * @autogenerated EntityGenerator
   */
  private ColorMerchantVO merchant;

  /**
   * @autogenerated EntityGenerator
   */
  private ColorNameVO name;

  /**
   * @autogenerated EntityGenerator
   */
  private ColorUidVO uid;

  /**
   * @autogenerated EntityGenerator
   */
  private ColorVersionVO version;

  /**
   * Hide a field value for the dto (setting as null)
   *
   * @autogenerated EntityGenerator
   * @param field The field to hide
   * @param color The field to hide
   */
  public void fixField(final String field, final Color color) {
    if (toFix.containsKey(field)) {
      toFix.get(field).accept(this, color);
    }
  }

  /**
   * Hide a field value for the dto (setting as null)
   *
   * @autogenerated EntityGenerator
   * @param field The field to hide
   */
  public void fixField(final String field) {
    fixField(field, null);
  }

  /**
   * Hide a field value for the dto (setting as null)
   *
   * @autogenerated EntityGenerator
   * @param field The field to hide
   */
  public void hideField(final String field) {
    if (toHide.containsKey(field)) {
      toHide.get(field).accept(this);
    }
  }

  /**
   * The entity param with the new values
   *
   * @autogenerated EntityGenerator
   * @param original
   * @return The entity param with the new values
   */
  public ColorBuilder toEntityBuilder(final Optional<Color> original) {
    ColorBuilder builder = Color.builder();
    builder = setUidOrDefault(builder, original);
    builder = setNameOrDefault(builder, original);
    builder = setMerchantOrDefault(builder, original);
    builder = setVersionOrDefault(builder, original);
    return builder;
  }

  /**
   * @autogenerated EntityGenerator
   * @param builder
   * @param original
   * @return
   */
  private Color.ColorBuilder setMerchantOrDefault(final ColorBuilder builder,
      final Optional<Color> original) {
    ColorMerchantVO value =
        null == merchant ? original.map(Color::getMerchant).orElse(null) : merchant;
    return null == value ? builder : builder.merchant(value);
  }

  /**
   * @autogenerated EntityGenerator
   * @param builder
   * @param original
   * @return
   */
  private Color.ColorBuilder setNameOrDefault(final ColorBuilder builder,
      final Optional<Color> original) {
    ColorNameVO value = null == name ? original.map(Color::getName).orElse(null) : name;
    return null == value ? builder : builder.name(value);
  }

  /**
   * @autogenerated EntityGenerator
   * @param builder
   * @param original
   * @return
   */
  private Color.ColorBuilder setUidOrDefault(final ColorBuilder builder,
      final Optional<Color> original) {
    ColorUidVO value = null == uid ? original.map(Color::getUid).orElse(null) : uid;
    return null == value ? builder : builder.uid(value);
  }

  /**
   * @autogenerated EntityGenerator
   * @param builder
   * @param original
   * @return
   */
  private Color.ColorBuilder setVersionOrDefault(final ColorBuilder builder,
      final Optional<Color> original) {
    ColorVersionVO value = null == version ? original.map(Color::getVersion).orElse(null) : version;
    return null == value ? builder : builder.version(value);
  }
}
