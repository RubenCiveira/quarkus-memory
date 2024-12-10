package org.acme.common.action;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class Pipeline<T, K> {
  private final Map<K, UnaryOperator<T>> suppliersByActionType;

  public Pipeline(K[] values, List<? extends Pipe<T, K>> rules) {
    this(values, rules, null);
  }
  public Pipeline(K[] values, List<? extends Pipe<T, K>> rules, Comparator<Pipe<T, K>> comparator) {
    List<? extends Pipe<T, K>> ruleList = new ArrayList<>( rules );
    if( null != comparator ) {
      ruleList.sort(comparator);
    }
    ruleList.forEach(rd -> {
      System.out.println("OOO: " + rd.getClass() );
    });
    Map<K, List<Pipe<T, K>>> rulesByActionType = ruleList.stream()
        .flatMap(rule -> Arrays.stream(values).filter(rule::supports)
            .map(type -> new AbstractMap.SimpleEntry<>(type, rule)))
        .collect(Collectors.groupingBy(Map.Entry::getKey,
            Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    this.suppliersByActionType = new HashMap<>();
    rulesByActionType.forEach((key, applyRules) -> {
      suppliersByActionType.put(key, (input) -> {
        UnaryOperator<T> pipeline = (from) -> from;
        for (int i = applyRules.size() - 1; i >= 0; i--) {
          Pipe<T, K> rule = applyRules.get(i);
          if (rule.supports(key)) {
            UnaryOperator<T> currentPipeline = pipeline;
            pipeline = (from) -> rule.apply(from, key, currentPipeline);
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
