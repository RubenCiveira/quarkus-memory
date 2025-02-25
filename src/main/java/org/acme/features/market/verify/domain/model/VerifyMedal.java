package org.acme.features.market.verify.domain.model;

import java.util.Optional;

import org.acme.common.exception.ConstraintException;
import org.acme.common.validation.ConstraintFail;
import org.acme.common.validation.ConstraintFailList;
import org.acme.features.market.medal.domain.model.MedalRef;
import org.acme.features.market.verify.domain.model.valueobject.VerifyMedalMedalVO;
import org.acme.features.market.verify.domain.model.valueobject.VerifyMedalUidVO;
import org.acme.features.market.verify.domain.model.valueobject.VerifyMedalVersionVO;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.With;

@Getter
@ToString
@Builder(toBuilder = true)
@With
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VerifyMedal {

  /**
   * @autogenerated UnionGenerator
   */
  public static class VerifyMedalBuilder {

    /**
     * @autogenerated UnionGenerator
     * @return
     */
    public VerifyMedal buildValid() {
      ConstraintFailList list = new ConstraintFailList();
      if (null == uid) {
        list.add(new ConstraintFail("REQUIRED", "uid", null));
      }
      if (null == medal) {
        list.add(new ConstraintFail("REQUIRED", "medal", null));
      }
      if (list.hasErrors()) {
        throw new ConstraintException(list);
      }
      return build();
    }

    /**
     * @autogenerated UnionGenerator
     * @param medal
     * @return
     */
    public VerifyMedalBuilder medalReferenceValue(final String medal) {
      return medal(VerifyMedalMedalVO.fromReference(medal));
    }

    /**
     * @autogenerated UnionGenerator
     * @param medal
     * @return
     */
    public VerifyMedalBuilder medalValue(final MedalRef medal) {
      return medal(VerifyMedalMedalVO.from(medal));
    }

    /**
     * @autogenerated UnionGenerator
     * @param uid
     * @return
     */
    public VerifyMedalBuilder uidValue(final String uid) {
      return uid(VerifyMedalUidVO.from(uid));
    }

    /**
     * @autogenerated UnionGenerator
     * @param version
     * @return
     */
    public VerifyMedalBuilder versionValue(final Integer version) {
      return version(VerifyMedalVersionVO.from(version));
    }
  }

  /**
   * El medal de verify medal
   *
   * @autogenerated UnionGenerator
   */
  @NonNull
  private VerifyMedalMedalVO medal;

  /**
   * A number to identify the db record
   *
   * @autogenerated UnionGenerator
   */
  @NonNull
  private VerifyMedalUidVO uid;

  /**
   * Campo con el número de version de verify medal para controlar bloqueos optimistas
   *
   * @autogenerated UnionGenerator
   */
  @NonNull
  @Builder.Default
  private VerifyMedalVersionVO version = VerifyMedalVersionVO.empty();

  /**
   * @autogenerated UnionGenerator
   * @return
   */
  public String getMedalReferenceValue() {
    return getMedal().getReferenceValue();
  }

  /**
   * @autogenerated UnionGenerator
   * @return
   */
  public MedalRef getMedalValue() {
    return getMedal().getValue();
  }

  /**
   * @autogenerated UnionGenerator
   * @return
   */
  public String getUidValue() {
    return getUid().getValue();
  }

  /**
   * @autogenerated UnionGenerator
   * @return
   */
  public Optional<Integer> getVersionValue() {
    return getVersion().getValue();
  }

  /**
   * @autogenerated UnionGenerator
   * @return
   */
  public VerifyMedal withEmptyVersion() {
    return withVersion(VerifyMedalVersionVO.empty());
  }

  /**
   * @autogenerated UnionGenerator
   * @param medal
   * @return
   */
  public VerifyMedal withMedalReferenceValue(final String medal) {
    return withMedal(VerifyMedalMedalVO.fromReference(medal));
  }

  /**
   * @autogenerated UnionGenerator
   * @param medal
   * @return
   */
  public VerifyMedal withMedalValue(final MedalRef medal) {
    return withMedal(VerifyMedalMedalVO.from(medal));
  }

  /**
   * @autogenerated UnionGenerator
   * @param uid
   * @return
   */
  public VerifyMedal withUidValue(final String uid) {
    return withUid(VerifyMedalUidVO.from(uid));
  }

  /**
   * @autogenerated UnionGenerator
   * @param version
   * @return
   */
  public VerifyMedal withVersionValue(final Integer version) {
    return withVersion(VerifyMedalVersionVO.from(version));
  }

  /**
   * @autogenerated UnionGenerator
   * @param version
   * @return
   */
  public VerifyMedal withVersionValue(final Optional<Integer> version) {
    return version.isPresent() ? withVersion(VerifyMedalVersionVO.from(version.get()))
        : withEmptyVersion();
  }
}
