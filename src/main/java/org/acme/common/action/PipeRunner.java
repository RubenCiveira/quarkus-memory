package org.acme.common.action;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

class PipeRunner<T, K> {

  private final Map<K, BiFunction<T, Object[], T>> suppliersByActionType;

  public PipeRunner(K[] values, List<? extends RunnablePipe<T, K>> rules,
      Comparator<? extends RunnablePipe<T, K>> comparator) {
    List<? extends RunnablePipe<T, K>> ruleList;
    if (null != comparator) {
      ruleList = new ArrayList<>(rules);
      ruleList.sort(adaptComparator(comparator));
      System.out.println("ORDERED:");
      System.out.println("====");
      ruleList.forEach(act -> {
        System.out.println("\tTENGO ORDENADO A " + act.getClass());
      });
      System.out.println("====");
    } else {
      ruleList = rules;
    }
    Map<K, List<RunnablePipe<T, K>>> rulesByActionType = ruleList.stream()
        .flatMap(rule -> Arrays.stream(values).filter(rule::supports)
            .map(type -> new AbstractMap.SimpleEntry<>(type, rule)))
        .collect(Collectors.groupingBy(Map.Entry::getKey,
            Collectors.mapping(Map.Entry::getValue, Collectors.toList())));
    suppliersByActionType = new HashMap<>();
    rulesByActionType.forEach((key, applyRules) -> {
      suppliersByActionType.put(key, (input, arr) -> {
        UnaryOperator<T> pipeline = (from) -> from;
        for (int i = applyRules.size() - 1; i >= 0; i--) {
          RunnablePipe<T, K> rule = applyRules.get(i);
          if (rule.supports(key)) {
            UnaryOperator<T> currentPipeline = pipeline;
            pipeline = (from) -> rule.apply(key, from, currentPipeline, arr);
          }
        }
        return pipeline.apply(input);
      });
    });
  }

  @SuppressWarnings("unchecked")
  private static <T, K> Comparator<? super RunnablePipe<T, K>> adaptComparator(
      @SuppressWarnings("rawtypes") Comparator comparator) {
    return (o1, o2) -> {
      // Hacer un cast explícito de RunnablePipe a Pipe, ya que sabemos que es seguro
      return comparator.compare(o1, o2);
    };
  }

  public T apply(K type, T value, Object[] params) {
    return suppliersByActionType.getOrDefault(type, (a, b) -> a).apply(value,
        null == params ? new Object[0] : params);
  }
}
