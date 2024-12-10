package org.acme.common.action;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Pipeline<T, K> {
  private final Map<K, UnaryOperator<T>> suppliersByActionType;

  public Pipeline(K[] values, List<? extends Pipe<T, K>> ruleList) {
    Map<K, List<Pipe<T, K>>> rulesByActionType = ruleList.stream()
        .flatMap(rule -> Arrays.stream(values).filter(rule::supports)
            .map(type -> new AbstractMap.SimpleEntry<>(type, rule)))
        .collect(Collectors.groupingBy(Map.Entry::getKey,
            Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    this.suppliersByActionType = new HashMap<>();
    rulesByActionType.forEach((key, rules) -> {
      suppliersByActionType.put(key, (input) -> {
        UnaryOperator<T> pipeline = (from) -> from;
        for (int i = rules.size() - 1; i >= 0; i--) {
          Pipe<T, K> rule = rules.get(i);
          if (rule.supports(key)) {
            UnaryOperator<T> currentPipeline = pipeline;
            pipeline = (from) -> rule.apply(input, key, currentPipeline);
          }
        }
        return pipeline.apply(input);
      });
    });
  }

  public T apply(K type, T initial) {
    return suppliersByActionType.getOrDefault(type, UnaryOperator.identity()).apply(initial);
  }
}
