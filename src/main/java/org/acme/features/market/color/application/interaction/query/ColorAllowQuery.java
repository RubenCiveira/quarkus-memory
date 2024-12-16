package org.acme.features.market.color.application.interaction.query;

import org.acme.common.action.Interaction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class ColorAllowQuery extends Interaction {
}
