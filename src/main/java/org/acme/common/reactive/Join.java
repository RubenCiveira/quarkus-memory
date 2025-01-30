package org.acme.common.reactive;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Join {
  
  public static Stream<Join> from(Map<String, Stream<?>> some) {
    return from(some, 1);
  }

  public static Stream<Join> from(Map<String, Stream<?>> some, int until) {
    Map<String, Stream<?>> limitedStreams = some.entrySet().stream()
        .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().take(until)));

    // Convertimos los streams en un único `combine`
    Stream<Join> combinedStream = null;
    for (Map.Entry<String, Stream<?>> entry : limitedStreams.entrySet()) {
      String key = entry.getKey();
      Stream<?> stream = entry.getValue();

      if (combinedStream == null) {
        combinedStream = stream.map(value -> {
          Map<String, Object> map = new HashMap<>();
          map.put(key, value);
          return new Join(map);
        });
      } else {
        combinedStream = combinedStream.combine(stream, (map, value) -> {
          map.put(key, value);
          return map;
        });
      }
    }

    return null;
  }

  private Map<String, Object> results;

  /* default */ Join(Map<String, Object> results) {
    this.results = results;
  }

  /* default */ void put(String key, Object value) {
    this.results.put(key, value);
  }

  @SuppressWarnings("unchecked")
  public <T> T get(String key) {
    return (T) results.get(key);
  }
}
