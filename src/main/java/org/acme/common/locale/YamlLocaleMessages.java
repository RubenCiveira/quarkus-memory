/* @autogenerated */
package org.acme.common.locale;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.yaml.snakeyaml.Yaml;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class YamlLocaleMessages {
  private static Yaml yaml = new Yaml();

  public static YamlLocaleMessages load(String name, Locale locale) {
    return new YamlLocaleMessages(name, locale,
        path -> YamlLocaleMessages.class.getResourceAsStream(path));
  }

  private final Map<String, String> loaded;

  /* default */ YamlLocaleMessages(String name, Locale locale,
      Function<String, InputStream> provider) {
    loaded = new HashMap<>();
    String language = locale.getLanguage();
    String country = locale.getCountry();
    load(name, loaded, provider);
    load(name + "." + language, loaded, provider);
    if (StringUtils.isEmpty(country)) {
      load(name + "." + language + "-" + country, loaded, provider);
    }
  }

  public boolean contains(String pattern) {
    return loaded.containsKey(pattern);
  }

  public List<String> keys(String preffix) {
    return loaded.keySet().stream().filter(key -> key.startsWith(preffix + "."))
        .map(key -> key.substring(preffix.length() + 1)).toList();
  }

  public String get(String pattern, Object... arguments) {
    return loaded.containsKey(pattern) ? MessageFormat.format(loaded.get(pattern), arguments)
        : pattern;
  }

  private void load(String name, Map<String, String> flattenedTranslations,
      Function<String, InputStream> provider) {
    // Locales:
    try (InputStream inputStream = provider.apply(name + ".yaml")) {
      if (inputStream != null) {
        Map<Object, Object> translations = yaml.load(inputStream);
        if (null != translations) {
          flattenMap("", translations, flattenedTranslations);
        }
      }
    } catch (IOException e) {
      log.error("Fail loading messages from file {}", name, e);
    }
  }

  @SuppressWarnings("unchecked")
  private void flattenMap(String prefix, Map<Object, Object> map, Map<String, String> result) {
    for (Map.Entry<Object, Object> entry : map.entrySet()) {
      String key =
          prefix.isEmpty() ? String.valueOf(entry.getKey()) : prefix + "." + entry.getKey();
      Object value = entry.getValue();
      if (value instanceof Map) {
        // Recursive call for nested maps
        flattenMap(key, (Map<Object, Object>) value, result);
      } else {
        // Base case: add the flattened key-value pair
        result.put(key, value.toString());
      }
    }
  }
}