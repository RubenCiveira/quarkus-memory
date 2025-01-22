package org.acme.features.market.medal.application.service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

import org.acme.common.action.Interaction;
import org.acme.common.validation.AbstractFailList;
import org.acme.common.validation.ConstraintFail;
import org.acme.features.market.medal.application.MedalDto;
import org.acme.features.market.medal.application.MedalPopulateResult;
import org.acme.features.market.medal.domain.gateway.MedalCursor;
import org.acme.features.market.medal.domain.gateway.MedalFilter;
import org.acme.features.market.medal.domain.model.valueobject.MedalNameVO;

import jakarta.enterprise.context.RequestScoped;
import lombok.RequiredArgsConstructor;

@RequestScoped
@RequiredArgsConstructor
public class MedalsPopulateService {

  /**
   * @autogenerated PopulateServiceGenerator
   */
  private final MedalsVisibilityService medalVisibles;

  /**
   * @autogenerated PopulateServiceGenerator
   * @param interaction
   * @param map
   * @param fails
   * @return
   */
  public CompletionStage<Optional<MedalPopulateResult>> populateInto(Interaction interaction,
      Map<String, String> map, AbstractFailList fails) {
    AbstractFailList constraints = new AbstractFailList();
    MedalFilter.MedalFilterBuilder filterBuilder = MedalFilter.builder();
    Optional<String> hasName = Optional.ofNullable(map.get("name"));
    if (hasName.isPresent()) {
      filterBuilder = filterBuilder.name(hasName.get());
    } else {
      constraints.add(new ConstraintFail("name-required", "The name is required", null));
    }
    return medalVisibles.listCachedVisibles(interaction, filterBuilder.build(),
        MedalCursor.builder().limit(1).build()).thenApply(results -> {
          MedalDto.MedalDtoBuilder builder = MedalDto.builder();
          Optional.ofNullable(map.get("Name")).map(value -> MedalNameVO.tryFrom(value, constraints))
              .ifPresent(builder::name);
          if (constraints.isEmpty()) {
            return Optional.of(MedalPopulateResult.builder().original(results.first().orElse(null))
                .dto(builder.build()).build());
          } else {
            fails.add(constraints);
            return Optional.empty();
          }
        });
  }
}
