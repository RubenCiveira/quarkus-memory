package org.acme.features.market.merchant.application;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.acme.features.market.merchant.domain.model.Merchant;
import org.acme.features.market.merchant.domain.model.Merchant.MerchantBuilder;
import org.acme.features.market.merchant.domain.model.valueobject.MerchantEnabledVO;
import org.acme.features.market.merchant.domain.model.valueobject.MerchantKeyVO;
import org.acme.features.market.merchant.domain.model.valueobject.MerchantNameVO;
import org.acme.features.market.merchant.domain.model.valueobject.MerchantUidVO;
import org.acme.features.market.merchant.domain.model.valueobject.MerchantVersionVO;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MerchantDto {

  /**
   * A dto with the entity data
   *
   * @autogenerated EntityGenerator
   * @param merchant The entity with the values
   * @return A dto with the entity data
   */
  public static MerchantDto from(final Merchant merchant) {
    return MerchantDto.builder().uid(merchant.getUid()).name(merchant.getName())
        .enabled(merchant.getEnabled()).key(merchant.getKey()).version(merchant.getVersion())
        .build();
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  private static Map<String, BiConsumer<MerchantDto, Merchant>> buildToFix() {
    Map<String, BiConsumer<MerchantDto, Merchant>> map = new HashMap<>();
    map.put("uid", (target, source) -> target.uid = source == null ? null : source.getUid());
    map.put("name", (target, source) -> target.name = source == null ? null : source.getName());
    map.put("enabled",
        (target, source) -> target.enabled = source == null ? null : source.getEnabled());
    map.put("key", (target, source) -> target.key = source == null ? null : source.getKey());
    map.put("version",
        (target, source) -> target.version = source == null ? null : source.getVersion());
    return map;
  }

  /**
   * @autogenerated EntityGenerator
   * @return
   */
  private static Map<String, Consumer<MerchantDto>> buildToHide() {
    Map<String, Consumer<MerchantDto>> map = new HashMap<>();
    map.put("uid", dto -> dto.uid = null);
    map.put("name", dto -> dto.name = null);
    map.put("enabled", dto -> dto.enabled = null);
    map.put("key", dto -> dto.key = null);
    map.put("version", dto -> dto.version = null);
    return map;
  }

  /**
   * @autogenerated EntityGenerator
   */
  private MerchantEnabledVO enabled;

  /**
   * @autogenerated EntityGenerator
   */
  private MerchantKeyVO key;

  /**
   * @autogenerated EntityGenerator
   */
  private MerchantNameVO name;

  /**
   * @autogenerated EntityGenerator
   */
  private MerchantUidVO uid;

  /**
   * @autogenerated EntityGenerator
   */
  private MerchantVersionVO version;

  /**
   * Hide a field value for the dto (setting as null)
   *
   * @autogenerated EntityGenerator
   * @param field The field to hide
   * @param merchant The field to hide
   */
  public void fixField(final String field, final Merchant merchant) {
    Map<String, BiConsumer<MerchantDto, Merchant>> toFix = buildToFix();
    if (toFix.containsKey(field)) {
      toFix.get(field).accept(this, merchant);
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
    Map<String, Consumer<MerchantDto>> toHide = buildToHide();
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
  public MerchantBuilder toEntityBuilder(final Optional<Merchant> original) {
    MerchantBuilder builder = Merchant.builder();
    builder = setUidOrDefault(builder, original);
    builder = setNameOrDefault(builder, original);
    builder = setEnabledOrDefault(builder, original);
    builder = setKeyOrDefault(builder, original);
    builder = setVersionOrDefault(builder, original);
    return builder;
  }

  /**
   * @autogenerated EntityGenerator
   * @param builder
   * @param original
   * @return
   */
  private Merchant.MerchantBuilder setEnabledOrDefault(final MerchantBuilder builder,
      final Optional<Merchant> original) {
    MerchantEnabledVO value =
        null == enabled ? original.map(Merchant::getEnabled).orElse(null) : enabled;
    return null == value ? builder : builder.enabled(value);
  }

  /**
   * @autogenerated EntityGenerator
   * @param builder
   * @param original
   * @return
   */
  private Merchant.MerchantBuilder setKeyOrDefault(final MerchantBuilder builder,
      final Optional<Merchant> original) {
    MerchantKeyVO value = null == key ? original.map(Merchant::getKey).orElse(null) : key;
    return null == value ? builder : builder.key(value);
  }

  /**
   * @autogenerated EntityGenerator
   * @param builder
   * @param original
   * @return
   */
  private Merchant.MerchantBuilder setNameOrDefault(final MerchantBuilder builder,
      final Optional<Merchant> original) {
    MerchantNameVO value = null == name ? original.map(Merchant::getName).orElse(null) : name;
    return null == value ? builder : builder.name(value);
  }

  /**
   * @autogenerated EntityGenerator
   * @param builder
   * @param original
   * @return
   */
  private Merchant.MerchantBuilder setUidOrDefault(final MerchantBuilder builder,
      final Optional<Merchant> original) {
    MerchantUidVO value = null == uid ? original.map(Merchant::getUid).orElse(null) : uid;
    return null == value ? builder : builder.uid(value);
  }

  /**
   * @autogenerated EntityGenerator
   * @param builder
   * @param original
   * @return
   */
  private Merchant.MerchantBuilder setVersionOrDefault(final MerchantBuilder builder,
      final Optional<Merchant> original) {
    MerchantVersionVO value =
        null == version ? original.map(Merchant::getVersion).orElse(null) : version;
    return null == value ? builder : builder.version(value);
  }
}
