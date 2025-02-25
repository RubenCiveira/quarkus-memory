package org.acme.features.market.color.domain.model.rule;

import java.util.Iterator;

import org.acme.features.market.color.domain.model.Color;

public interface ColorDeleteRulePipeline {

  /**
   * @autogenerated AggregateGenerator
   * @param rules
   * @param color
   * @return
   */
  public static Color fire(Iterator<ColorDeleteRulePipeline> rules, Color color) {
    Color result = color;
    while (rules.hasNext()) {
      result = rules.next().deleting(result);
    }
    return result;
  }

  /**
   * @autogenerated AggregateGenerator
   * @param color
   * @return
   */
  Color deleting(Color color);
}
