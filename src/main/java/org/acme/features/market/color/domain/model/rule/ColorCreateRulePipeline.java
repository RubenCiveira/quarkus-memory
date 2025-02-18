package org.acme.features.market.color.domain.model.rule;

import java.util.Iterator;

import org.acme.features.market.color.domain.model.Color;

public interface ColorCreateRulePipeline {

  /**
   * @autogenerated AggregateGenerator
   * @param rules
   * @param color
   * @return
   */
  public static Color fire(Iterator<ColorCreateRulePipeline> rules, Color color) {
    Color result = color;
    while (rules.hasNext()) {
      result = rules.next().creating(result);
    }
    return result;
  }

  /**
   * @autogenerated AggregateGenerator
   * @param color
   * @return
   */
  Color creating(Color color);
}
