package org.acme.features.market.color.application.usecase.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.features.market.color.application.interaction.ColorDto;
import org.acme.features.market.color.application.interaction.visibility.ColorFixedFields;
import org.acme.features.market.color.application.interaction.visibility.ColorHiddenFields;
import org.acme.features.market.color.application.interaction.visibility.ColorListableContent;
import org.acme.features.market.color.application.interaction.visibility.ColorVisibleContent;
import org.acme.features.market.color.application.interaction.visibility.ColorVisibleFilter;
import org.acme.features.market.color.domain.Colors;
import org.acme.features.market.color.domain.gateway.ColorFilter;
import org.acme.features.market.color.domain.model.Color;
import org.acme.features.market.color.domain.model.ColorRef;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class ColorsVisibilityService {

  /**
   * @autogenerated VisibilityServiceGenerator
   */
  private final Colors aggregate;

  /**
   * Event source for maniputale the fix over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<ColorFixedFields> fireFix;

  /**
   * Event source for maniputale the hide over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<ColorHiddenFields> fireHide;

  /**
   * Event source for maniputale the visible list
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<ColorListableContent> fireListableList;

  /**
   * Event source for maniputale the visibility filter over the entity fields
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<ColorVisibleFilter> fireVisibleFilter;

  /**
   * Event source for maniputale the visible list
   *
   * @autogenerated VisibilityServiceGenerator
   */
  private final Event<ColorVisibleContent> fireVisibleList;

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param original The source interaction
   * @param source The source interaction
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<ColorDto> copyWithFixed(Interaction prev, Color original,
      ColorDto source) {
    return fieldsToFix(prev, original).getFixed().thenApply(fixeds -> {
      fixeds.forEach(field -> source.fix(field, original));
      return source;
    });
  }

  /**
   * The input entity with the copy values without hidden
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param source The source interaction
   * @return The input entity with the copy values without hidden
   */
  public CompletionStage<ColorDto> copyWithFixed(Interaction prev, ColorDto source) {
    return fieldsToFix(prev).getFixed().thenApply(fixeds -> {
      fixeds.forEach(field -> source.fix(field));
      return source;
    });
  }

  /**
   * initialsFixFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @return initialsFixFields
   */
  public ColorFixedFields fieldsToFix(Interaction prev) {
    CompletionStage<Set<String>> fields = fieldsToHide(prev).getHidden().thenApply(hidden -> {
      Set<String> set = new HashSet<>(aggregate.calcultadFields());
      set.addAll(hidden);
      return set;
    });
    ColorFixedFields value = ColorFixedFields.builder().fixed(fields).build(prev);
    fireFix.fire(value);
    return value;
  }

  /**
   * initialsFixFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param color
   * @return initialsFixFields
   */
  public ColorFixedFields fieldsToFix(Interaction prev, ColorRef color) {
    CompletionStage<Set<String>> fields = fieldsToFix(prev).getFixed()
        .thenCombine(fieldsToHide(prev, color).getHidden(), (set1, set2) -> {
          Set<String> set = new HashSet<>(set1);
          set.addAll(set2);
          return set;
        });
    ColorFixedFields value = ColorFixedFields.builder().fixed(fields).color(color).build(prev);
    fireFix.fire(value);
    return value;
  }

  /**
   * initialsHideFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @return initialsHideFields
   */
  public ColorHiddenFields fieldsToHide(Interaction prev) {
    CompletionStage<Set<String>> fields = CompletableFuture.completedFuture(Set.of());
    ColorHiddenFields value = ColorHiddenFields.builder().hidden(fields).build(prev);
    fireHide.fire(value);
    return value;
  }

  /**
   * initialsHideFields
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param color
   * @return initialsHideFields
   */
  public ColorHiddenFields fieldsToHide(Interaction prev, ColorRef color) {
    CompletionStage<Set<String>> fields = fieldsToHide(prev).getHidden();
    ColorHiddenFields value = ColorHiddenFields.builder().hidden(fields).color(color).build(prev);
    fireHide.fire(value);
    return value;
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param color
   * @return The input dto with hidden values
   */
  public CompletionStage<ColorDto> hide(Interaction prev, Color color) {
    return fieldsToHide(prev, color).getHidden().thenApply(hidden -> {
      ColorDto target = ColorDto.from(color);
      hidden.forEach(target::hide);
      return target;
    });
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param colorRefs The source interaction
   * @return The input dto with hidden values
   */
  public CompletionStage<List<Color>> listableFilter(Interaction prev, List<Color> colorRefs) {
    return visibleFilter(prev, colorRefs).thenCompose(visibles -> {
      ColorListableContent list = ColorListableContent.builder()
          .listables(CompletableFuture.completedFuture(visibles)).build(prev);
      fireListableList.fire(list);
      return list.getListables();
    });
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param colorRefs The source interaction
   * @return The input dto with hidden values
   */
  public CompletionStage<List<Color>> visibleFilter(Interaction prev, List<Color> colorRefs) {
    ColorVisibleContent list = ColorVisibleContent.builder()
        .visibles(CompletableFuture.completedFuture(new ArrayList<>(colorRefs))).build(prev);
    fireVisibleList.fire(list);
    return list.getVisibles();
  }

  /**
   * The input dto with hidden values
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param colorRef The source interaction
   * @return The input dto with hidden values
   */
  public CompletionStage<Optional<Color>> visibleFilter(Interaction prev, Color colorRef) {
    return visibleFilter(prev, List.of(colorRef))
        .thenApply(list -> list.isEmpty() ? Optional.empty() : Optional.of(list.get(0)));
  }

  /**
   * The self filter modified with the prepared values.
   *
   * @autogenerated VisibilityServiceGenerator
   * @param prev The source interaction
   * @param filter The filter to retrieve values
   * @return The self filter modified with the prepared values.
   */
  public CompletionStage<ColorFilter> visibleFilter(Interaction prev, ColorFilter filter) {
    ColorVisibleFilter visible =
        ColorVisibleFilter.builder().filter(CompletableFuture.completedFuture(filter)).build(prev);
    fireVisibleFilter.fire(visible);
    return visible.getFilter();
  }
}
