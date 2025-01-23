package org.acme.features.market.verify.application;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.acme.features.market.verify.domain.model.Verify;
import org.acme.features.market.verify.domain.model.Verify.VerifyBuilder;
import org.acme.features.market.verify.domain.model.valueobject.VerifyMedalsVO;
import org.acme.features.market.verify.domain.model.valueobject.VerifyNameVO;
import org.acme.features.market.verify.domain.model.valueobject.VerifyUidVO;
import org.acme.features.market.verify.domain.model.valueobject.VerifyVersionVO;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.With;

@Data
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VerifyDto {

  /**
   * The callbacks to copy
   *
   * @autogenerated EntityGenerator
   */
  private static final Map<String, BiConsumer<VerifyDto, Verify>> toFix = Map.of("uid",
      (target, source) -> target.uid = source == null ? null : source.getUid(), "name",
      (target, source) -> target.name = source == null ? null : source.getName(), "medals",
      (target, source) -> target.medals = source == null ? null : source.getMedals(), "version",
      (target, source) -> target.version = source == null ? null : source.getVersion());

  /**
   * The callbacks to hide
   *
   * @autogenerated EntityGenerator
   */
  private static final Map<String, Consumer<VerifyDto>> toHide =
      Map.of("uid", dto -> dto.uid = null, "name", dto -> dto.name = null, "medals",
          dto -> dto.medals = null, "version", dto -> dto.version = null);

  /**
   * A dto with the entity data
   *
   * @autogenerated EntityGenerator
   * @param verify The entity with the values
   * @return A dto with the entity data
   */
  public static VerifyDto from(final Verify verify) {
    return VerifyDto.builder().uid(verify.getUid()).name(verify.getName())
        .medals(verify.getMedals()).version(verify.getVersion()).build();
  }

  /**
   * @autogenerated EntityGenerator
   */
  private VerifyMedalsVO medals;

  /**
   * @autogenerated EntityGenerator
   */
  private VerifyNameVO name;

  /**
   * @autogenerated EntityGenerator
   */
  private VerifyUidVO uid;

  /**
   * @autogenerated EntityGenerator
   */
  private VerifyVersionVO version;

  /**
   * Hide a field value for the dto (setting as null)
   *
   * @autogenerated EntityGenerator
   * @param field The field to hide
   * @param verify The field to hide
   */
  public void fixField(final String field, final Verify verify) {
    if (toFix.containsKey(field)) {
      toFix.get(field).accept(this, verify);
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
  public VerifyBuilder toEntityBuilder(final Optional<Verify> original) {
    VerifyBuilder builder = Verify.builder();
    builder = setUidOrDefault(builder, original);
    builder = setNameOrDefault(builder, original);
    builder = setMedalsOrDefault(builder, original);
    builder = setVersionOrDefault(builder, original);
    return builder;
  }

  /**
   * @autogenerated EntityGenerator
   * @param builder
   * @param original
   * @return
   */
  private Verify.VerifyBuilder setMedalsOrDefault(final VerifyBuilder builder,
      final Optional<Verify> original) {
    VerifyMedalsVO value = null == medals ? original.map(Verify::getMedals).orElse(null) : medals;
    return null == value ? builder : builder.medals(value);
  }

  /**
   * @autogenerated EntityGenerator
   * @param builder
   * @param original
   * @return
   */
  private Verify.VerifyBuilder setNameOrDefault(final VerifyBuilder builder,
      final Optional<Verify> original) {
    VerifyNameVO value = null == name ? original.map(Verify::getName).orElse(null) : name;
    return null == value ? builder : builder.name(value);
  }

  /**
   * @autogenerated EntityGenerator
   * @param builder
   * @param original
   * @return
   */
  private Verify.VerifyBuilder setUidOrDefault(final VerifyBuilder builder,
      final Optional<Verify> original) {
    VerifyUidVO value = null == uid ? original.map(Verify::getUid).orElse(null) : uid;
    return null == value ? builder : builder.uid(value);
  }

  /**
   * @autogenerated EntityGenerator
   * @param builder
   * @param original
   * @return
   */
  private Verify.VerifyBuilder setVersionOrDefault(final VerifyBuilder builder,
      final Optional<Verify> original) {
    VerifyVersionVO value =
        null == version ? original.map(Verify::getVersion).orElse(null) : version;
    return null == value ? builder : builder.version(value);
  }
}
