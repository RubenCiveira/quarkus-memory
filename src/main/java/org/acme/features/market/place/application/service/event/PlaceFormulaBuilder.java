package org.acme.features.market.place.application.service.event;

import java.util.Optional;
import java.util.concurrent.CompletionStage;
import java.util.function.Consumer;

import org.acme.common.action.Interaction;
import org.acme.features.market.place.application.PlaceDto;
import org.acme.features.market.place.domain.model.Place;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class PlaceFormulaBuilder extends Interaction {

  /**
   * @autogenerated VisibleContentGenerator
   */
  Place original;

  /**
   * @autogenerated VisibleContentGenerator
   */
  @NonNull
  private CompletionStage<PlaceDto> dto;

  /**
   * @autogenerated VisibleContentGenerator
   * @return
   */
  public Optional<Place> getOriginal() {
    return Optional.ofNullable(original);
  }

  /**
   * @autogenerated VisibleContentGenerator
   * @param mapper
   */
  public void tap(Consumer<PlaceDto> mapper) {
    dto = dto.thenApply(dto -> {
      mapper.accept(dto);
      return dto;
    });
  }
}
