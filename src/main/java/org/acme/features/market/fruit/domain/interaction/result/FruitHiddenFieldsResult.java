package org.acme.features.market.fruit.domain.interaction.result;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.acme.common.action.Interaction;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@ToString
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class FruitHiddenFieldsResult extends Interaction {

  /**
   * The list of hidden fields
   *
   * @autogenerated VisibilityTraitGenerator
   */
  private Set<String> hidden;

  /**
   * Add another field to the hidden list
   *
   * @autogenerated VisibilityTraitGenerator
   * @param field A field to add to hidden
   */
  public void add(final String field) {
    hiddenEditable().add(field);
  }

  /**
   * The self instance to chain other calls
   *
   * @autogenerated VisibilityTraitGenerator
   * @param field A field to add to hidden
   * @return The self instance to chain other calls
   */
  public FruitHiddenFieldsResult and(final String field) {
    hiddenEditable().add(field);
    return this;
  }

  /**
   * The iterable stream of hidden fields
   *
   * @autogenerated VisibilityTraitGenerator
   * @return The iterable stream of hidden fields
   */
  public Stream<String> getHidden() {
    return hidden.stream();
  }

  /**
   * The self instance to chain other calls
   *
   * @autogenerated VisibilityTraitGenerator
   * @param field A field to remote from hidden
   * @return The self instance to chain other calls
   */
  public FruitHiddenFieldsResult not(final String field) {
    hiddenEditable().remove(field);
    return this;
  }

  /**
   * Remove one field from the hidden list
   *
   * @autogenerated VisibilityTraitGenerator
   * @param field A field to remote from hidden
   */
  public void remove(final String field) {
    hiddenEditable().remove(field);
  }

  /**
   * The list ensured as an editable list
   *
   * @autogenerated VisibilityTraitGenerator
   * @return The list ensured as an editable list
   */
  private Set<String> hiddenEditable() {
    if (!(hidden instanceof LinkedHashSet)) {
      hidden = new LinkedHashSet<>(hidden);
    }
    return hidden;
  }
}